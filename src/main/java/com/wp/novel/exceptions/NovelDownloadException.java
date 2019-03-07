package com.wp.novel.exceptions;

/**
 * С˵�����쳣
 * 
 * @author dingpeng
 * @date 2016��9��26��
 */
public class NovelDownloadException extends NovelSpiderException {
	private static final long serialVersionUID = -1141885897383743245L;

	public NovelDownloadException() {
	}

	public NovelDownloadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NovelDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public NovelDownloadException(String message) {
		super(message);
	}

	public NovelDownloadException(Throwable cause) {
		super(cause);
	}

}
