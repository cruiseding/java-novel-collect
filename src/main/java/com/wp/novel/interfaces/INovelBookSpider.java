package com.wp.novel.interfaces;

import java.util.List;

import com.wp.novel.entity.Book;
import com.wp.novel.exceptions.NovelSpiderException;

/**
 * ��ȡҳ���е�����С˵ʵ�壬���ṩһ�����������ж��Ƿ�����һҳ������ȡ
 * 
 * @author dingpeng
 * @date 2016��10��2��
 * @param <E>
 */
public interface INovelBookSpider<E extends Book> {

	/**
	 * ��ȡ��ǰҳ�������е�С˵ʵ��
	 * 
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public List<E> getsBook(String url) throws NovelSpiderException;

	/**
	 * �������ҳ��֮���Ƿ�����һҳ
	 * 
	 * @return
	 */
	public boolean hasNextPage();
}
