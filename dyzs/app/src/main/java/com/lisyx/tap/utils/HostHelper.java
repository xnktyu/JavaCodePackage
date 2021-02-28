package com.lisyx.tap.utils;

import android.content.Context;
import android.text.TextUtils;

public class HostHelper
{
	private static String hostFileAddress(Context context)
	{
		return String.format("http://qyqy-file.oss-cn-hangzhou.aliyuncs.com/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
	}

	public interface OnHostCallback
	{
		void onResult(String host);
	}

	public static void requestHost(final Context context, final OnHostCallback callback)
	{
		String url = hostFileAddress(context);
		if (!TextUtils.isEmpty(url))
		{
			if (SysUtils.isDebug())
				LOG.v("requestHost : " + url);
			HttpUtils.doHttpGet(context, url, new HttpUtils.OnCallback()
			{
				@Override
				public void onResponse(String host)
				{
					if (!TextUtils.isEmpty(host))
					{
						if (callback != null)
							callback.onResult(host);
					}
					else
					{
						if (callback != null)
							callback.onResult(null);
					}
				}
			});
		}
		else
		{
			if (callback != null)
				callback.onResult(null);
		}
	}
}
