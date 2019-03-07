package com.wp.novel.interfaces;

import com.wp.novel.exceptions.NovelSpiderException;

/**
 * @author dingpeng
 * @date 2016��9��17��
 */
public interface INovelSpider {
	/**
	 * ��ȡҳ�沢������ȡ�������ȡʧ���׳��쳣
	 * 
	 * @param url     Ҫ��ȡ��url��������������
	 * @param charset ��ҳ�����ʽ
	 */
	public String crawl(String url, String charset) throws NovelSpiderException;

	/**
	 * ��֤ץȡ���������Ƿ����Ҫ��
	 * 
	 * @param t
	 */
	public boolean verify(Object obj);

	/**
	 * ����Ҫ��ȡ������
	 * 
	 * @param domain
	 */
	public void setDomain(String domain);

	/**
	 * @return ��ȡ��ȡ������
	 */
	public String getDomain();
}
