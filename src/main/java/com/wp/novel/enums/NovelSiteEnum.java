package com.wp.novel.enums;

import java.io.Serializable;

/**
 * �Ѿ���֧�ֵ�С˵վ��ö��
 * 
 * @author dingpeng
 * @date 2016��9��27��
 */
public enum NovelSiteEnum implements Serializable {

	BiQuGe(1, "biquge.tw"), BiXiaWenXue(2, "bxwx9.org"), DingDianXiaoShuo(3, "23wx.com");

	private int id;
	private String comment;

	private NovelSiteEnum(int id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static NovelSiteEnum getEnumByComment(String comment) {
		if (comment == null)
			throw new IllegalArgumentException("comment ����Ϊnull");
		for (NovelSiteEnum novelSiteEnum : values()) {
			if (comment.equalsIgnoreCase(novelSiteEnum.getComment())) {
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("��֧�ֵ���վ��" + comment);
	}

	/**
	 * @param url ������һ��������url��Ҳ������ֻ����������url
	 * @return
	 */
	public static NovelSiteEnum getEnumByUrl(String url) {
		if (url == null)
			throw new IllegalArgumentException("url ����Ϊnull");
		for (NovelSiteEnum novelSiteEnum : values()) {
			if (url.contains(novelSiteEnum.getComment())) {
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("��֧�ֵ���վ��" + url);
	}

	public static NovelSiteEnum getEnumById(int id) {
		for (NovelSiteEnum novelSiteEnum : values()) {
			if (novelSiteEnum.getId() == id) {
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("��֧�ֵ���վid��" + id);
	}
}
