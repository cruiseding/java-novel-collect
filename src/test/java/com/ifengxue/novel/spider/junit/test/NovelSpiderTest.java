package com.ifengxue.novel.spider.junit.test;

import java.util.List;

import org.junit.Test;

import com.wp.novel.ChapterFactory;
import com.wp.novel.ContentFactory;
import com.wp.novel.entity.Chapter;
import com.wp.novel.entity.Content;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.impl.download.NovelDownloaderObserver;
import com.wp.novel.interfaces.INovelChapterSpider;
import com.wp.novel.interfaces.INovelContentSpider;
import com.wp.novel.util.NovelSpiderUtil;

/**
 * @author dingpeng
 * @date 2016年10月1日
 */
public class NovelSpiderTest {

	@Test
	public void getChapters() throws NovelSpiderException {
		NovelSpiderUtil.setRootPath("D:/project/novelspider");
		String url = "http://www.biquge.tw/0_5/";
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		INovelChapterSpider spider = ChapterFactory.getChapterSpider(novelSiteEnum);
		List<Chapter> chapters = spider.getChapters(NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url));
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}

	@Test
	public void getContent() throws NovelSpiderException {
		NovelSpiderUtil.setRootPath("D:/project/novelspider");
		String url = "http://www.biquge.tw/0_5/910022.html";
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		INovelContentSpider spider = ContentFactory.getContentSpider(novelSiteEnum);
		Content content = spider.getContent(NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url));
		System.out.println(content);
	}

	public static void main(String[] args) {
		// http://www.bxwx8.org/b/70/70093
		// http://www.biquge.tw/0_5/
		// http://www.23wx.com/html/42/42377/
		NovelSpiderUtil.setRootPath("E:\\workspace-qx\\java-novel-collect\\conf\\");
		String url = "https://www.bxwx9.org/b/70/70093/index.html";
		NovelDownloaderObserver observer = new NovelDownloaderObserver(url);
		observer.process();
		System.out.println("合并后的文件地址：" + NovelSpiderUtil.getNovelMergePath(url));
	}
}
