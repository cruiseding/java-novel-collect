package com.wp.novel;

import com.wp.novel.entity.BxwxBook;
import com.wp.novel.impl.book.BxwxBookSpider;
import com.wp.novel.interfaces.INovelBookSpider;

/**
 * 书列表爬虫工厂
 * 
 * @author dingpeng
 * @date 2016年10月2日
 */
public final class BookFactory {

	private BookFactory() {
	}

	/**
	 * 获取一个笔下文学站点的小说爬虫
	 * 
	 * @return
	 */
	public static INovelBookSpider<BxwxBook> getBxwxBookSpider() {
		return new BxwxBookSpider();
	}
}
