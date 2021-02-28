package com.xnktyu.utils;

public class TextUtils
{
	public static boolean isEmpty(CharSequence s)
	{
		if (s == null)
			return true;
		else
			return s.length() == 0;
	}
}
