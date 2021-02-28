package com.lisyx.tap.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

public class CommonUtils
{
	public static String getIndentStr(int indent)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++)
		{
			sb.append("\t");
		}
		return sb.toString();
	}

	public static String formatSize(double size)
	{
		return formatSize(size, 1024);
	}

	public static String formatSize(double size, long block)
	{
		if (size < block)
		{
			return (int) size + " 字节";
		}
		else if (size < block * block)
		{
			String str = String.format("%.1f", size / block);
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " KB";
		}
		else if (size < block * block * block)
		{
			String str = String.format("%.1f", size / (block * block));
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " MB";
		}
		else
		{
			String str = String.format("%.1f", size / (block * block * block));
			if (str.endsWith(".0"))
				str = str.substring(0, str.length() - 2);
			return str + " GB";
		}
	}

	public static String base64Encode(byte[] bytes)
	{
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public static byte[] base64Decode(String str)
	{
		return Base64.decode(str, Base64.DEFAULT);
	}

	public static String filterEmoji(String str, String place)
	{
		return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", place);
	}

	public static String md5(byte[] bytes)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // MD5,SHA1,SHA256
			md5.update(bytes);
			byte[] data = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++)
			{
				int d = data[i];
				if (d < 0)
					d += 256;
				if (d < 16)
					sb.append("0");
				sb.append(Integer.toHexString(d));
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String md5(String text)
	{
		byte[] bytes = text.getBytes(Charset.forName("utf-8"));
		return md5(bytes);
	}

	public static String md5(File file)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // MD5,SHA1,SHA256

			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 16];
			int len = 0;
			while ((len = fis.read(buffer)) > 0)
			{
				md5.update(buffer, 0, len);
			}
			fis.close();

			byte[] data = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < data.length; i++)
			{
				int d = data[i];
				if (d < 0)
					d += 256;
				if (d < 16)
					sb.append("0");
				sb.append(Integer.toHexString(d));
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
