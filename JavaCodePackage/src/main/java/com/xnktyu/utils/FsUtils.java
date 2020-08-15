package com.xnktyu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;


public class FsUtils
{
	public static byte[] readBytes(File file)
	{
		if (file.exists() && file.isFile())
		{
			byte[] buffer = null;
			try
			{
				FileInputStream fis = new FileInputStream(file);
				buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return buffer;
		}
		else
		{
			return null;
		}
	}

	public static String readText(File file)
	{
		byte[] buffer = readBytes(file);
		if (buffer != null)
			return new String(buffer, 0, buffer.length, Charset.forName("UTF-8"));
		else
			return null;
	}

	public static void writeBytes(File file, byte[] bytes)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes, 0, bytes.length);
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeText(File file, String text)
	{
		writeBytes(file, text.getBytes(Charset.forName("UTF-8")));
	}
}
