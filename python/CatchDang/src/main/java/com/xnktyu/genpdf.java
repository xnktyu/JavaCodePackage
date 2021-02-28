package com.xnktyu;

import java.io.File;
import java.io.FileFilter;

import com.xnktyu.image2pdf.Image2Pdf;
import com.xnktyu.utils.LOG;

public class genpdf
{
	public static void genPdf(File dir, File pdfDir)
	{
		dir.listFiles(new FileFilter()
		{
			public boolean accept(File file)
			{
				if (file.isDirectory() && !file.getName().equals("tmp"))
				{
					LOG.v(file);
					Image2Pdf.convert(file, pdfDir, 0);
				}
				return false;
			}
		});
	}

	public static void main(String args[])
	{
		genPdf(new File("G:/wangzhiting/python/output"), new File("G:/wangzhiting/python/pdfs"));
		LOG.v("------------- over -------------------");
	}
}
