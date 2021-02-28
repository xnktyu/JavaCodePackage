package com.xnktyu;

import java.io.File;

import com.xnktyu.dangdang.CatchBookDir;
import com.xnktyu.utils.LOG;

public class command
{
	public static void dummy(String rootDir, String endStr)
	{
		LOG.v(String.format("dummy. rootDir = %s, endStr = %s", rootDir, endStr));
	}

	public static void catchDir(String domFile)
	{
		CatchBookDir.catchDir(new File(domFile));
	}

	public static void genPdf(String dir, String pdfDir)
	{
		test.genPdf(new File(dir), new File(pdfDir));
	}
}
