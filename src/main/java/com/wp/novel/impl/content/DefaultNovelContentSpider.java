package com.wp.novel.impl.content;

import com.wp.novel.exceptions.NovelSpiderException;

/**
 * ͨ�õ�С˵�½�������ȡ��
 * 
 * @author dingpeng
 * @date 2016��9��18��
 */
public class DefaultNovelContentSpider extends AbstractNovelContentSpider {

	public DefaultNovelContentSpider(String comment) {
		this.domain = allRuleMap.get(comment).get("url").getText();
		this.simpleDomain = domain.substring(domain.indexOf(".") + 1);
	}

	public static void main(String[] args) throws NovelSpiderException {
		DefaultNovelContentSpider erSanSpider = new DefaultNovelContentSpider("23wx.com");
		System.out.println(erSanSpider.getContent("html/42/42377/18775689.html"));

		DefaultNovelContentSpider biQuGeSpider = new DefaultNovelContentSpider("biquge.tw");
		System.out.println(biQuGeSpider.getContent("0_5/909854.html"));

		DefaultNovelContentSpider bxwxSpider = new DefaultNovelContentSpider("bxwx8.org");
		System.out.println(bxwxSpider.getContent("b/70/70093/11969964.html"));
	}
}
