package com.wp.novel;

import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.impl.chapter.BxwxNovelChapterSpider;
import com.wp.novel.impl.chapter.DefaultNovelChapterSpider;
import com.wp.novel.interfaces.INovelChapterSpider;

/**
 * ��Ӧƽ̨���½�ץȡ������
 * 
 * @author dingpeng
 * @date 2016��9��27��
 */
public final class ChapterFactory {

	private ChapterFactory() {
	}

	/**
	 * ��ȡʵ����INovelChapterSpider�ӿڵ���
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
			throw new RuntimeException("��δ֧�ֵ�С˵վ�㡣");
		}
	}
}
