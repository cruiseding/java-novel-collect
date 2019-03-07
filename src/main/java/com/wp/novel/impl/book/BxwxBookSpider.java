package com.wp.novel.impl.book;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wp.novel.entity.BxwxBook;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.impl.AbstractNovelSpiderV2;
import com.wp.novel.interfaces.INovelBookSpider;

/**
 * 笔下文学小说列表页爬虫
 * 
 * @author dingpeng
 * @date 2016年10月2日
 */
public class BxwxBookSpider extends AbstractNovelSpiderV2 implements INovelBookSpider<BxwxBook> {

	@Override
	public boolean verify(Object obj) {
		return true;
	}

	@Override
	public List<BxwxBook> getsBook(String url) throws NovelSpiderException {
		String html = super.crawl(url, "GBK");
		parseDoc = Jsoup.parse(html);
		parseDoc.setBaseUri("http://www." + NovelSiteEnum.BiXiaWenXue.getComment());
		Elements grids = parseDoc.getElementsByClass("grid");
		if (grids == null || grids.isEmpty())
			throw new NovelSpiderException("没有找到匹配class=grid的元素");

		Elements trs = grids.get(0).getElementsByTag("tr");
		if (trs == null || trs.isEmpty() || trs.size() == 1)
			throw new NovelSpiderException("没有找到匹配tag=tr的元素，或者元素长度只有1");

		List<BxwxBook> books = new ArrayList<>();
		for (int i = 1, size = trs.size(); i < size; i++) { // 第一个元素为标题，跳过
			BxwxBook book = parseElementAndCreateBook(trs, i);
			books.add(book);
		}
		return books;
	}

	/** 解析Tr元素并返回Book实体 */
	private BxwxBook parseElementAndCreateBook(Elements trs, int i) {
		BxwxBook book = new BxwxBook();
		Elements tds = trs.get(i).getElementsByTag("td");
		assert tds.size() == 6 : "长度不为6，你在逗我吗？";
		Element booknameTag = tds.get(0).getElementsByTag("a").first();
		book.setBookname(booknameTag.text());
		book.setUrl(booknameTag.absUrl("href"));
		book.setUrl(book.getUrl().replace("binfo", "b").replace(".htm", "")); // 替换小说主页面为章节页
		Element lastUpdateTag = tds.get(1).getElementsByTag("a").first();
		book.setLastUpdateChapter(lastUpdateTag == null ? null : lastUpdateTag.text());
		book.setLastUpdateChapterUrl(lastUpdateTag == null ? null : lastUpdateTag.absUrl("href")); // 貌似这个URL和小说章节列表页URL是一样的。。
		book.setAuthor(tds.get(2).text());
		book.setSize(tds.get(3).text());
		book.setLastUpdateTime(tds.get(4).text());
		book.setStatus(tds.get(5).text());
		return book;
	}

	@Override
	public boolean hasNextPage() {
		return parseDoc.select("#pagelink .next").first() != null; // 只要有下一页这个元素在，说明就没抓取到最后
	}
}
