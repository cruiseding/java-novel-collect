package com.wp.novel.impl.download;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.wp.novel.ContentFactory;
import com.wp.novel.entity.Chapter;
import com.wp.novel.entity.Content;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelDownloadException;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.interfaces.INovelContentSpider;
import com.wp.novel.interfaces.INovelDownloader;

/**
 * @author dingpeng
 * @date 2016年9月27日
 */
public class DefaultNovelDownloader implements INovelDownloader {

	@Override
	public void download(String path, NovelSiteEnum novelSiteEnum, List<Chapter> chapters)
			throws NovelDownloadException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(path, "UTF-8");
			assert chapters == null || chapters.isEmpty() : "章节列表不应为null或为size=0";
			for (Chapter chapter : chapters) {
				System.out.println("正在下载，" + chapter.getText() + ", 第" + chapter.getNumber() + "章");
				Content content = null;
				try {
					content = tryAndDownload(novelSiteEnum, chapter.getHref());
				} catch (NovelSpiderException e) {
					// 尝试多次下载失败了
					content = new Content();
					content.setTitle(chapter.getText());
					content.setContent("很抱歉，" + e.getMessage());
				}
				out.println(content.getTitle());
				out.println(content.getContent());
				try {
					Thread.sleep(1_000);// 下载不要太频繁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw new NovelDownloadException(e);
		} finally {
			out.close();
		}
	}

	/**
	 * 多次尝试下载
	 * 
	 * @param novelSiteEnum
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public Content tryAndDownload(NovelSiteEnum novelSiteEnum, String url) throws NovelSpiderException {
		INovelContentSpider contentSpider = ContentFactory.getContentSpider(novelSiteEnum);
		Content content = null;
		for (int i = 1; i <= TRY_TIME; i++) {
			try {
				content = contentSpider.getContent(url);
				break; // 下载成功
			} catch (NovelSpiderException e) {
				;// 重试
			}
		}
		if (content != null) {
			return content;
		} else {
			throw new NovelSpiderException("尝试" + TRY_TIME + "次下载均失败了！");
		}
	}
}
