package com.wp.novel.exceptions;

/**
 * @author dingpeng
 * @date 2016年9月17日
 */
public class CrawlException extends NovelSpiderException {

	private static final long serialVersionUID = 3216067273308070227L;

	public CrawlException() {
	}

	public CrawlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CrawlException(String message, Throwable cause) {
		super(message, cause);
	}

	public CrawlException(String message) {
		super(message);
	}

	public CrawlException(Throwable cause) {
		super(cause);
	}

}
