package com.wp.novel.interfaces;

import com.wp.novel.entity.Content;
import com.wp.novel.exceptions.NovelSpiderException;

/**
 * @author dingpeng
 * @date 2016年9月19日
 */
public interface INovelContentSpider {

	public Content getContent(String url) throws NovelSpiderException;

}
