package com.wp.novel.interfaces;

/**
 * 被观察者
 * 
 * @author dingpeng
 * @date 2016年9月26日
 */
public interface Observable {
	
	public void registerObserver(Observer observer);

	public void removeObserver(Observer observer);

	public void notifyObservers();

	public void notifyObservers(Object arg);
}
