package com.xnktyu;

import com.xnktyu.image2pdf.Image2Pdf;
import com.xnktyu.utils.LOG;

import java.io.File;
import java.io.FileFilter;

public class test
{
	public static void genPdf(File dir)
	{
		dir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isDirectory() && !file.getName().equals("pdf") && !file.getName().equals("table"))
				{
					LOG.v(file);
					Image2Pdf.convert(file, 0);
				}
				return false;
			}
		});
	}

	public static void main(String args[])
	{
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\计算机教材.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\多媒体数据通信.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\程序设计.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\计算机理论与教程.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\软件系统.txt"));
//		CatchBookTable.uploadRecord(new File("D:\\wangzhiting\\work\\local\\book\\table\\硬件.txt"));

//		CatchBookDir.catchDir(new File("D:\\wangzhiting\\work\\local\\book\\tmp.html"), new File("D:\\wangzhiting\\work\\local\\book"));
		genPdf(new File("D:\\wangzhiting\\work\\local\\book"));
	}
}
