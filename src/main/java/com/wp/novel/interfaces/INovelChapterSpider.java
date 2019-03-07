package com.wp.novel.interfaces;

import java.util.List;

import com.wp.novel.entity.Chapter;
import com.wp.novel.exceptions.NovelSpiderException;

/**
 * @author dingpeng
 * @date 2016��9��17��
 */
public interface INovelChapterSpider extends INovelSpider {
	/**
	 * ��ԭ���ӣ� http://www.aaa.com/xiaoshuo/bbb.html ��ôֻ��Ҫ��xiaoshuo/bbb.html����
	 * 
	 * @param url Ҫ��ȡ�½��б�Ķ�url
	 * @return
	 */
	public List<Chapter> getChapters(String url) throws NovelSpiderException;
}
