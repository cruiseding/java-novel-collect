package com.wp.novel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;

/**
 * 小说文本工具
 * 
 * @author dingpeng
 * @date 2016年9月30日
 */
public final class NovelTextUtil {

	private NovelTextUtil() {
	}

	/** 合并多个文本文件到一个文本文件 */
	public static void multiFileMerge(String path, String mergeName) {
		File file = new File(path);
		File[] files = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});

		if (files == null || files.length == 0) {
			try {
				FileUtils.writeLines(file, "UTF-8", FileUtils.readLines(new File(mergeName), "UTF-8"), true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String[] o1ChapterRange = getChapterRange(o1.getName());
				String[] o2ChapterRange = getChapterRange(o2.getName());
				assert o1ChapterRange.length == 2 : "小说章节名：" + o1.getName() + "格式不正确！";
				assert o2ChapterRange.length == 2 : "小说章节名：" + o2.getName() + "格式不正确！";
				int o1StartChpater = Integer.parseInt(o1ChapterRange[0]);
				int o2StartChapter = Integer.parseInt(o2ChapterRange[0]);
				return o1StartChpater - o2StartChapter;
			}
		});

		// 首先删除目标文件:即历史下载
		try {
			File f = new File(mergeName);
			if(f.exists()) {
				FileUtils.forceDelete(f);
			}
			for (File sourceFile : files) {
				FileUtils.writeLines(sourceFile, "UTF-8", FileUtils.readLines(new File(mergeName), "UTF-8"), true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 如文件名为10-100.txt则返回数组为["10","100"]
	 * 
	 * @param name
	 * @return
	 */
	private static String[] getChapterRange(String name) {
		name = name.substring(0, name.lastIndexOf('.'));
		String[] chapterRange = name.split("\\-");
		return chapterRange;
	}
}
