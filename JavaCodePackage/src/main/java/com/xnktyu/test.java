package com.xnktyu;

import com.xnktyu.dangdang.CatchBookDir;
import com.xnktyu.image2pdf.Image2Pdf;

import java.io.File;

public class test
{
	public static void main(String args[])
	{
		CatchBookDir.catchDir(new File("D:\\wangzhiting\\work\\local\\book\\tmp.html"), new File("D:\\wangzhiting\\work\\local\\book"));
//		Image2Pdf.convert(new File("D:\\wangzhiting\\work\\local\\book\\深入理解Android内核设计思想(第2版)(上下册)"), 0);
	}
}
