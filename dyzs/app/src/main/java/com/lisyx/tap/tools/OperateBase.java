package com.lisyx.tap.tools;

import android.content.Context;

public abstract class OperateBase
{
	protected Context context = null;

//	protected int screenWidth = 0;
//	protected int screenHeight = 0;
//
//	protected File screenFile = null;

	protected long startTime = 0;

	public void startMode(Context ctx)
	{
		context = ctx;
//		screenWidth = SysUtils.screenWidth(context);
//		screenHeight = SysUtils.screenHeight(context);
//		screenFile = new File(Environment.getExternalStorageDirectory(), "screen.png");
		startTime = System.currentTimeMillis();
		start();
	}

	public abstract void start();

	public abstract void startOne();

	protected String formatTime(long ms)
	{
		long second = ms / 1000;
		long minute = second / 60;
		second = second % 60;
		long hour = minute / 60;
		minute = minute % 60;
		if (hour > 0)
			return String.format("%d小时%d分钟", hour, minute);
		else if (minute > 0)
			return String.format("%d分钟%d秒", minute, second);
		else
			return String.format("%d秒", second);
	}
}
