package com.xnktyu;

import com.xnktyu.dangdang.CatchBookDir;
import com.xnktyu.dangdang.CatchBookTable;
import com.xnktyu.image2pdf.Image2Pdf;

import java.io.File;

public class test
{
	public static void main(String args[])
	{
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\计算机教材.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\多媒体数据通信.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\程序设计.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\计算机理论与教程.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\软件系统.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\硬件.txt"));

		CatchBookDir.catchDir(new File("D:\\wangzhiting\\work\\local\\book\\tmp.html"), new File("D:\\wangzhiting\\work\\local\\book"));
//		Image2Pdf.convert(new File("D:\\wangzhiting\\work\\local\\book\\1901210261操作系统(第3版)"), 0);
	}
}
