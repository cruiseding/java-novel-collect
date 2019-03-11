package com.wp.novel.impl.chapter;

import java.util.List;

import com.wp.novel.entity.Chapter;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.impl.AbstractNovelSpider;
import com.wp.novel.interfaces.INovelChapterSpider;

/**
 * @author dingpeng
 */
public abstract class AbstractNovelChapterSpider extends AbstractNovelSpider implements INovelChapterSpider {

	protected String comment;

	/**
	 * @param url
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
