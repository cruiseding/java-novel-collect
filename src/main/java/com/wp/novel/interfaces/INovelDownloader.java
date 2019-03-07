package com.wp.novel.interfaces;

import java.util.List;

import com.wp.novel.entity.Chapter;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelDownloadException;

/**
 * С˵�½�������
 * 
 * @author dingpeng
 * @date 2016��9��27��
 */
public interface INovelDownloader {
	/** ����ʧ��ʱ���ԵĴ��� */
	public static final int TRY_TIME = 3;

	/**
	 * @param path          �����ļ���·��
	 * @param novelSiteEnum
	 * @param chapters      Ҫ���ص��½�
	 * @throws Exception
	 */
	public void download(String path, NovelSiteEnum novelSiteEnum, List<Chapter> chapters)
			throws NovelDownloadException;
}
