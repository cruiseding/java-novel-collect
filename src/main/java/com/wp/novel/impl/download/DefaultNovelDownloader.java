package com.wp.novel.impl.download;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.wp.novel.ContentFactory;
import com.wp.novel.entity.Chapter;
import com.wp.novel.entity.Content;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelDownloadException;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.interfaces.INovelContentSpider;
import com.wp.novel.interfaces.INovelDownloader;

/**
 * @author dingpeng
 * @date 2016��9��27��
 */
public class DefaultNovelDownloader implements INovelDownloader {

	@Override
	public void download(String path, NovelSiteEnum novelSiteEnum, List<Chapter> chapters)
			throws NovelDownloadException {
		PrintWriter out = null;
		try {
			out = new PrintWriter(path, "UTF-8");
			assert chapters == null || chapters.isEmpty() : "�½��б�ӦΪnull��Ϊsize=0";
			for (Chapter chapter : chapters) {
				System.out.println("�������أ�" + chapter.getText() + ", ��" + chapter.getNumber() + "��");
				Content content = null;
				try {
					content = tryAndDownload(novelSiteEnum, chapter.getHref());
				} catch (NovelSpiderException e) {
					// ���Զ������ʧ����
					content = new Content();
					content.setTitle(chapter.getText());
					content.setContent("�ܱ�Ǹ��" + e.getMessage());
				}
				out.println(content.getTitle());
				out.println(content.getContent());
				try {
					Thread.sleep(1_000);// ���ز�Ҫ̫Ƶ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw new NovelDownloadException(e);
		} finally {
			out.close();
		}
	}

	/**
	 * ��γ�������
	 * 
	 * @param novelSiteEnum
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public Content tryAndDownload(NovelSiteEnum novelSiteEnum, String url) throws NovelSpiderException {
		INovelContentSpider contentSpider = ContentFactory.getContentSpider(novelSiteEnum);
		Content content = null;
		for (int i = 1; i <= TRY_TIME; i++) {
			try {
				content = contentSpider.getContent(url);
				break; // ���سɹ�
			} catch (NovelSpiderException e) {
				;// ����
			}
		}
		if (content != null) {
			return content;
		} else {
			throw new NovelSpiderException("����" + TRY_TIME + "�����ؾ�ʧ���ˣ�");
		}
	}
}
