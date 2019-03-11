package com.wp.novel.interfaces;

import java.util.List;

import com.wp.novel.entity.Book;
import com.wp.novel.exceptions.NovelSpiderException;

/**
 * 爬取页面中的所有小说实体，并提供一个方法用来判断是否还有下一页可以爬取
 * 
 * @author dingpeng
 * @date 2016年10月2日
 * @param <E>
 */
public interface INovelBookSpider<E extends Book> {

	/**
	 * 爬取当前页面中所有的小说实体
	 * 
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public List<E> getsBook(String url) throws NovelSpiderException;

	/**
	 * 爬完这个页面之后是否还有下一页
	 * 
	 * @return
	 */
	public boolean hasNextPage();
}
