package com.lisyx.tap.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

// 崩溃处理
public class CrashHandler implements Thread.UncaughtExceptionHandler
{
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	public void init(Context context)
	{
		mContext = context.getApplicationContext();

		// 获取默认异常处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		// 将此类设为默认异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable)
	{
		handleException(throwable);
		if (mDefaultHandler != null)
		{
			mDefaultHandler.uncaughtException(thread, throwable);
		}
	}

	// 是否人为捕获异常
	private void handleException(Throwable throwable)
	{
		if (throwable == null)
		{
			return;
		}

//		new Thread(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				Looper.prepare();
//				Toast.makeText(mContext, "捕获到异常", Toast.LENGTH_SHORT).show();
//				Looper.loop();
//			}
//		}).start();

		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		throwable.printStackTrace(pw);
		Throwable cause = throwable.getCause();
		// 循环取出Cause
		while (cause != null)
		{
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();
		String result = writer.toString();

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			PackageInfo packageInfo = null;
			try
			{
				packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

				String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
				String fileName = String.format("%s_%s[%s][%s]_%s.txt", time, mContext.getPackageName(), packageInfo.versionCode, packageInfo.versionName, SysUtils.getOnlyId(mContext));

				File dir = new File(Environment.getExternalStorageDirectory(), "crash");
				if (!dir.exists())
					dir.mkdirs();

				FileOutputStream fos = null;
				try
				{
					fos = new FileOutputStream(new File(dir, fileName));
					fos.write(result.getBytes());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (fos != null)
					{
						try
						{
							fos.close();
						}
						catch (IOException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
			catch (PackageManager.NameNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
}
