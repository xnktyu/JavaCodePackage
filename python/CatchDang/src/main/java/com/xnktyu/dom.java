package com.xnktyu;

import java.io.File;

import com.xnktyu.dangdang.CatchBookDir;
import com.xnktyu.utils.LOG;

public class dom
{
	public static void main(String args[])
	{
		CatchBookDir.catchDir(new File("G:/wangzhiting/python/output/dom.txt"));
		LOG.v("------------- over -------------------");
	}
}
