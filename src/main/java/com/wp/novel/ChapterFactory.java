package com.wp.novel;

import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.impl.chapter.BxwxNovelChapterSpider;
import com.wp.novel.impl.chapter.DefaultNovelChapterSpider;
import com.wp.novel.interfaces.INovelChapterSpider;

/**
 * 对应平台的章节抓取工厂类
 * 
 * @author dingpeng
 * @date 2016年9月27日
 */
public final class ChapterFactory {

	private ChapterFactory() {
	}

	/**
	 * 获取实现了INovelChapterSpider接口的类
	 * 
	 * @param novelSiteEnum
	 * @return
	 */
	public static INovelChapterSpider getChapterSpider(NovelSiteEnum novelSiteEnum) {
		switch (novelSiteEnum) {
		case BiQuGe:
			return new DefaultNovelChapterSpider(novelSiteEnum.getComment());
		case BiXiaWenXue:
			return new BxwxNovelChapterSpider(novelSiteEnum.getComment());
		case DingDianXiaoShuo:
			return new DefaultNovelChapterSpider(novelSiteEnum.getComment());
		default:
			throw new RuntimeException("尚未支持的小说站点。");
		}
	}
}
