package com.wp.novel.interfaces;

/**
 * ���۲���
 * 
 * @author dingpeng
 * @date 2016��9��26��
 */
public interface Observable {
	
	public void registerObserver(Observer observer);

	public void removeObserver(Observer observer);

	public void notifyObservers();

	public void notifyObservers(Object arg);
}
