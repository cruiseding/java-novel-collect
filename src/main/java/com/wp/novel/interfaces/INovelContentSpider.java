package com.wp.novel.interfaces;

import com.wp.novel.entity.Content;
import com.wp.novel.exceptions.NovelSpiderException;

/**
 * @author dingpeng
 * @date 2016Äê9ÔÂ19ÈÕ
 */
public interface INovelContentSpider {

	public Content getContent(String url) throws NovelSpiderException;

}
