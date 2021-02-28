package com.lisyx.tap.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SProtocol;

public class Protocol
{
	public interface OnCallback
	{
		void onResponse(int code, String data, String msg);
	}

	private static String api = null;

	public static void doPost(final Context context, final int handleId, final String data, final OnCallback callback)
	{
		if (!TextUtils.isEmpty(api))
		{
			doPost(context, api, handleId, data, callback);
		}
		else
		{
			HostHelper.requestHost(context, new HostHelper.OnHostCallback()
			{
				@Override
				public void onResult(String host)
				{
					if (SysUtils.isDebug())
						LOG.v("host : " + host);
					if (!TextUtils.isEmpty(host))
					{
						api = host;
						doPost(context, api, handleId, data, callback);
					}
				}
			});
		}
	}

	private static void doPost(final Context context, String api, final int handleId, String data, final OnCallback callback)
	{
		SProtocol trans = new SProtocol();
		trans.handleId = handleId;
		trans.data = data;
		trans.userId = SysUtils.getOnlyId(context);
		if (SysUtils.isDebug())
		{
			LOG.v("上行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------" + data.length() + "---------");
//			LOGJson.log(trans.saveToStr());
		}
		HttpUtils.doHttpPost(context, api, trans.saveToStr(), new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String jsonStr)
			{
				if (!TextUtils.isEmpty(jsonStr))
				{
					SProtocol trans = null;
					try
					{
						if (SysUtils.isDebug())
						{
							LOG.v("下行--------" + SHandleId.name(handleId) + "---------" + handleId + "---------" + jsonStr.length() + "---------");
//							LOGJson.log(jsonStr);
						}
						trans = SProtocol.load(jsonStr);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					if (trans != null)
					{
						if (trans.code == 200)
						{
							if (callback != null)
								callback.onResponse(trans.code, trans.data, trans.msg);
						}
						else
						{
							if (callback != null)
								callback.onResponse(trans.code, null, trans.msg);
						}
					}
				}
				else
				{
					if (callback != null)
						callback.onResponse(-100, null, "网络异常");
				}
			}
		});
	}


}
