package com.wp.novel.impl.download;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wp.novel.entity.Chapter;
import com.wp.novel.enums.NovelSiteEnum;
import com.wp.novel.exceptions.NovelDownloadException;
import com.wp.novel.interfaces.INovelDownloader;
import com.wp.novel.interfaces.Observable;
import com.wp.novel.interfaces.Observer;

/**
 * @author dingpeng
 * @date 2016年9月27日
 */
public class NovelDownloaderObservable implements Observable, Runnable {
	/** 已经注册的观察者列表 */
	protected List<Observer> observers = new ArrayList<>();
	protected INovelDownloader downloader;
	protected String path;
	protected NovelSiteEnum novelSiteEnum;
	protected List<Chapter> chapters;

	public NovelDownloaderObservable(INovelDownloader downloader, String path, NovelSiteEnum novelSiteEnum,
			List<Chapter> chapters) {
		this.downloader = downloader;
		this.path = path;
		this.novelSiteEnum = novelSiteEnum;
		this.chapters = chapters;
	}

	@Override
	public void run() {
		try {
			downloader.download(path, novelSiteEnum, chapters);
		} catch (NovelDownloadException e) {
			e.printStackTrace();
		} finally {
			notifyObservers();
		}
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		Iterator<Observer> iterator = observers.iterator();
		while (iterator.hasNext()) {
			Observer current = iterator.next();
			if (current.equals(observer)) {
				iterator.remove(); // 移除当前元素
			}
		}
	}

	@Override
	public void notifyObservers() {
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(Object arg) {
		for (Observer observer : observers) {
			observer.update(arg);
		}
	}
}
