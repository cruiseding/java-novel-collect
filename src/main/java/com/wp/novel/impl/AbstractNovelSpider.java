package com.wp.novel.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wp.novel.entity.Chapter;
import com.wp.novel.exceptions.CrawlException;
import com.wp.novel.exceptions.NovelSpiderException;
import com.wp.novel.interfaces.INovelSpider;
import com.wp.novel.util.NovelSpiderUtil;

/**
 * @author dingpeng
 * @date 2016��9��18��
 */
@SuppressWarnings("unchecked")
public abstract class AbstractNovelSpider implements INovelSpider {
	/** �½���ȡ���� */
	protected static final Map<String, Map<String, Element>> allRuleMap = new HashMap<>();
	protected String domain;
	protected List<Chapter> chapters = new ArrayList<>();;

	public AbstractNovelSpider() {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(NovelSpiderUtil.getRootPath() + "/Spider-Rule.xml"));
			Element root = doc.getRootElement();

			List<Element> novelSites = root.elements("Novel-Site");
			Map<String, Element> temp = null;
			for (Element novelSite : novelSites) {
				List<Element> childElements = novelSite.elements();
				temp = new HashMap<>();
				for (Element ele : childElements) {
					temp.put(ele.getName(), ele);
				}
				allRuleMap.put(novelSite.elementTextTrim("comment"), temp);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String crawl(String url, String charset) throws NovelSpiderException {
		String chapterUrl = domain + "/" + url;
		String crawlResult = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(chapterUrl);
		httpGet.setConfig(RequestConfig.custom().setConnectionRequestTimeout(2_000).setConnectTimeout(10_000)
				.setSocketTimeout(10_000).build());
		NovelSpiderUtil.setDefaultNovelSpiderHeader(httpGet);
		httpGet.setHeader("Host", domain.substring(domain.indexOf("/") + 2));
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				crawlResult = EntityUtils.toString(httpResponse.getEntity(), charset);
			} else {
				throw new CrawlException("ץȡʧ�ܣ�HTTP״̬�룺" + statusLine.getStatusCode());
			}
			httpResponse.close();
			httpClient.close();
			return crawlResult;
		} catch (IOException e) {
			throw new NovelSpiderException("ץȡʧ�ܣ�ʧ��ԭ��" + e.getMessage(), e);
		}
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getDomain() {
		return domain;
	}
}
