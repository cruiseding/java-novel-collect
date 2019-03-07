package com.wp.novel;

import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.impl.content.DefaultNovelContentSpider;
import com.wp.novel.interfaces.INovelContentSpider;

/**
 * @author dingpeng
 * @date 2016��9��27��
 */
public final class ContentFactory {

	private ContentFactory() {
	}

	/**
	 * ��ȡʵ����INovelContentSpider�ӿڵ���
	 * 
	 * @param novelSiteEnum
	 * @return
	 */
	public static INovelContentSpider getContentSpider(NovelSiteEnum novelSiteEnum) {
		switch (novelSiteEnum) {
		case BiQuGe:
			return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		case BiXiaWenXue:
			return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		case DingDianXiaoShuo:
			return new DefaultNovelContentSpider(novelSiteEnum.getComment());
		default:
			throw new RuntimeException("��δ֧�ֵ�С˵վ�㡣");
		}
	}
}
