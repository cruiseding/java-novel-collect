package com.wp.novel;

import com.wp.novel.entity.BxwxBook;
import com.wp.novel.impl.book.BxwxBookSpider;
import com.wp.novel.interfaces.INovelBookSpider;

/**
 * ���б����湤��
 * 
 * @author dingpeng
 * @date 2016��10��2��
 */
public final class BookFactory {

	private BookFactory() {
	}

	/**
	 * ��ȡһ��������ѧվ���С˵����
	 * 
	 * @return
	 */
	public static INovelBookSpider<BxwxBook> getBxwxBookSpider() {
		return new BxwxBookSpider();
	}
}
