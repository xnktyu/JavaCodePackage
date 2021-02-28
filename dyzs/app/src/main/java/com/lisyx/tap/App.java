package com.lisyx.tap;

import android.app.Application;

import com.lisyx.tap.utils.CrashHandler;
import com.lisyx.tap.utils.LOG;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class App extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		LOG.v("========================================================= start app : " + this);
		UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
		MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
		new CrashHandler().init(this);
	}
}
