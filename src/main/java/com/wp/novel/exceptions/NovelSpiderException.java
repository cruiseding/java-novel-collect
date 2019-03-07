package com.wp.novel.exceptions;

/**
 * ��������ȡ�����е��쳣���з�װ
 * 
 * @author dingpeng
 * @date 2016��9��17��
 */
public class NovelSpiderException extends Exception {
	private static final long serialVersionUID = 6758225611864282799L;

	public NovelSpiderException() {
	}

	public NovelSpiderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NovelSpiderException(String message, Throwable cause) {
		super(message, cause);
	}

	public NovelSpiderException(String message) {
		super(message);
	}

	public NovelSpiderException(Throwable cause) {
		super(cause);
	}
}
