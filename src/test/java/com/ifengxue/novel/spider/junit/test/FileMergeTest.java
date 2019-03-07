package com.ifengxue.novel.spider.junit.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FileMergeTest {

	@Test
	public void merge() {
		try {
			FileUtils.writeLines(new File("C:\\Users\\peng.ding\\Desktop\\\\aaa.txt"), "GBK",
					FileUtils.readLines(new File("C:\\Users\\peng.ding\\Desktop\\ddd.txt"), "GBK"), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
