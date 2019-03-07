package com.wp.novel.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpMessage;
import org.jsoup.nodes.Element;

import com.wp.novel.entity.Chapter;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.exceptions.ParseRuleException;

/**
 * @author dingpeng
 * @date 2016�?9�?17�?
 */
public final class NovelSpiderUtil {

	/** 爬虫的请求头 */
	private static final Map<String, String> DEFAULT_NOVEL_SPIDER_HEADER = new HashMap<>();

	/** 多线程下载的分片文件�?后合并后的文件路径和文件�? */
	public static final String MERGE_PATH_AND_FILENAME = "/merge/merge.txt";

	/** 小说下载时保存的根路径，配置文件暂时也放在这个路径下 */
	private static String rootPath = "download";

	/** 每个线程�?多下载的章节数量 */
	private static final int MAX_DOWNLOAD_SIZE = 100;

	private NovelSpiderUtil() {
	}

	static {
		DEFAULT_NOVEL_SPIDER_HEADER.put("Accept", "*/*");
		DEFAULT_NOVEL_SPIDER_HEADER.put("Accept-Encoding", "gzip, deflate");
		DEFAULT_NOVEL_SPIDER_HEADER.put("Accept-Language", "zh-CN,zh;q=0.8");
		DEFAULT_NOVEL_SPIDER_HEADER.put("User-Agent", "NovelSpider(1.0.0)");
	}

	/**
	 * 设置默认请求�?
	 * 
	 * @param message
	 */
	public static void setDefaultNovelSpiderHeader(HttpMessage message) {
		Set<String> keySet = DEFAULT_NOVEL_SPIDER_HEADER.keySet();
		for (String str : keySet) {
			message.setHeader(str, DEFAULT_NOVEL_SPIDER_HEADER.get(str));
		}
	}

	/**
	 * 转换html元素为章节实�?
	 * 
	 * @param element
	 * @return
	 */
	public static Chapter parseElement2Chapter(Element element, String url) {
		Chapter vo = new Chapter();
		vo.setText(element.text());
		String href = element.attr("href");
		if (href.startsWith("/")) { // 绝对路径
			vo.setHref(href);
		} else { // 相对路径，需要路径补�?
			if (url.endsWith(".html")) {
				url = url.substring(0, url.lastIndexOf('/'));
			}
			vo.setHref("/" + url + "/" + element.attr("href"));
		}
		return vo;
	}

	/**
	 * 替换特殊字符�?
	 * 
	 * @return
	 * @throws ParseRuleException
	 */
	public static String replaceSpecifyString(String content, String specifyString) throws ParseRuleException {
		if (specifyString != null && specifyString.startsWith("#{")) {
			switch (specifyString) {
			case "#{space}":
				return content.replaceAll("#\\{space\\}", " ");
			case "#{line-break}":
				return content.replaceAll("#\\{line-break\\}", "\n");
			default:
				throw new ParseRuleException(content + "不是合法的表达式�?");
			}
		} else {
			return content.replaceAll(specifyString, "");
		}
	}

	/**
	 * 完整URL解析成对应站点枚�?
	 * 
	 * @param url
	 * @return
	 * @throws NovelSpiderException
	 */
	public static NovelSiteEnum parseUrl(String url) throws NovelSpiderException {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url; // What?
		}
		URL u = null; // 首先得是合法的url
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			throw new NovelSpiderException(e);
		}
		String host = u.getHost();
		return NovelSiteEnum.getEnumByUrl(host);

	}

	/**
	 * 将完整URL解析成相对路�?
	 * 
	 * @param novelSiteEnum
	 * @param url
	 * @return
	 */
	public static String getRelativeUrl(NovelSiteEnum novelSiteEnum, String url) {
		String shortUrl = url
				.substring(url.indexOf(novelSiteEnum.getComment()) + novelSiteEnum.getComment().length() + 1);
		if (shortUrl.endsWith("/")) { // 去掉末尾多余�?/
			return shortUrl.substring(0, shortUrl.length() - 1);
		} else {
			return shortUrl;
		}
	}

	/**
	 * 进行每个线程的任务分�?
	 * 
	 * @param chapters
	 * @return
	 */
	public static Map<String, List<Chapter>> downloadTaskAlloc(List<Chapter> chapters) {
		int taskCount = (int) Math.ceil(chapters.size() * 1.0 / MAX_DOWNLOAD_SIZE);
		Map<String, List<Chapter>> taskAlloc = new TreeMap<String, List<Chapter>>();
		for (int i = 0; i < taskCount; i++) {
			// i * (MAX_DOWNLOAD_SIZE + 1) i * (MAX_DOWNLOAD_SIZE + 1) + MAX_DOWNLOAD_SIZE
			// i = 0 0-100
			// i = 1 101-201
			// i = 2 202-301
			int start = i * (MAX_DOWNLOAD_SIZE + 1);
			int end = i == (taskCount - 1) ? chapters.size() - 1 : i * (MAX_DOWNLOAD_SIZE + 1) + MAX_DOWNLOAD_SIZE;
			taskAlloc.put(start + "-" + end, chapters.subList(start, end));
		}
		return taskAlloc;
	}

	/** 返回小说下载的根路径 */
	public static String getRootPath() {
		return rootPath;
	}

	/** 设置小说下载的根路径 */
	public static void setRootPath(String rootPath) {
		NovelSpiderUtil.rootPath = rootPath;
	}

	/**
	 * 获取小说保存的根路径
	 * 
	 * @param url
	 * @return
	 */
	public static String getNovelSavePath(String url) {
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		String relativeUrl = NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url);
		try {
			return NovelSpiderUtil.getRootPath() + "/" + novelSiteEnum.getComment() + "/"
					+ Base64.encodeBase64URLSafeString(relativeUrl.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取小说保存时的文件夹名，这也是小说在对应网站下的唯�?标识�?
	 * 
	 * @param url
	 * @return
	 */
	public static String getNovelSaveName(String url) {
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		String relativeUrl = NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url);
		try {
			return Base64.encodeBase64URLSafeString(relativeUrl.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取分片下载的小说最后全本保存的文件路径（完整的路径�?
	 * 
	 * @param url
	 * @return
	 */
	public static String getNovelMergePath(String url) {
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		String relativeUrl = NovelSpiderUtil.getRelativeUrl(novelSiteEnum, url);
		try {
			return NovelSpiderUtil.getRootPath() + "/" + novelSiteEnum.getComment() + "/"
					+ Base64.encodeBase64URLSafeString(relativeUrl.getBytes("GBK")) + MERGE_PATH_AND_FILENAME;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将页面中解析到的前一章，下一章的章节地址解析为绝对路�? <br>
	 * 如果relaticeUrl本身就是绝对路径，则原样返回�?
	 * 
	 * @param url         当前章节的完整url地址:http://www.biquge.tw//0_5/1373.html
	 * @param relativeUrl 1374.html
	 * @return /0_5/1374.html
	 */
	public static String relativeUrl2FullUrl(String url, String relativeUrl) {
		if (relativeUrl.startsWith("/"))
			return relativeUrl;
		NovelSiteEnum novelSiteEnum = NovelSiteEnum.getEnumByUrl(url);
		String newUrl = url.replace("http://www.", "").replace(novelSiteEnum.getComment(), "");
		return newUrl.substring(0, newUrl.lastIndexOf('/') + 1) + relativeUrl;
	}
}