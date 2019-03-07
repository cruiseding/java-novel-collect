package com.wp.novel.impl.chapter;

import java.util.List;

import com.wp.novel.entity.Chapter;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.impl.AbstractNovelSpider;
import com.wp.novel.interfaces.INovelChapterSpider;

/**
 * @author dingpeng
 * @date 2016��9��17��
 */
public abstract class AbstractNovelChapterSpider extends AbstractNovelSpider implements INovelChapterSpider {

	protected String comment;

	/**
	 * @param url Ҫ��ȡ�½��б�Ķ�url
	 * @return
	 */
	public abstract List<Chapter> getChapters(String url) throws NovelSpiderException;

	public boolean verify(Object obj) {
		Chapter vo = (Chapter) obj;
		if (vo.getHref() != null && !vo.getHref().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
