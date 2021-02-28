package com.lisyx.tap.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.lisyx.tap.R;
import com.lisyx.tap.utils.CommonUtils;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.SysUtils;

import java.lang.reflect.Field;

public class ActivityInfos extends BaseActivity
{
	private TextView info;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infos);

		TextView info = findViewById(R.id.info);

		StringBuilder sb = new StringBuilder();

		sb.append(String.format("屏幕大小：(%d, %d)\n", SysUtils.screenWidth(context), SysUtils.screenHeight(context)));
		sb.append("screenWidth(dp)：" + SysUtils.px2dp(SysUtils.screenWidth(context)) + "\n");
		sb.append("screenWidth(sp)：" + SysUtils.px2sp(SysUtils.screenWidth(context)) + "\n");

		sb.append("\n");

		long total = Environment.getExternalStorageDirectory().getTotalSpace();
		long free = Environment.getExternalStorageDirectory().getFreeSpace();
		sb.append(String.format("存储空间：总共：%s，剩余：%s\n", CommonUtils.formatSize(total), CommonUtils.formatSize(free)));

		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(memoryInfo);
		sb.append(String.format("内存：总共：%s，剩余：%s\n", CommonUtils.formatSize(memoryInfo.totalMem, 1000), CommonUtils.formatSize(memoryInfo.availMem, 1000)));

		sb.append("\n");

		sb.append("厂商：" + Build.BRAND + "\n");
		sb.append("型号：" + Build.MODEL + "\n");
		sb.append("版本号：" + Build.DISPLAY + "\n");
		sb.append("Android 版本：" + Build.VERSION.RELEASE + "\n");
		sb.append("SDK 版本：" + Build.VERSION.SDK_INT + "\n");

		sb.append("Serial(设备号)：" + Build.SERIAL + "\n");
		sb.append("IMEI：" + SysUtils.getIMEI(context) + "\n");
		sb.append("ANDROID_ID：" + SysUtils.getAndroidId(context) + "\n");

		sb.append("getMacDefault：" + SysUtils.getMacDefault(context) + "\n");
		sb.append("getMacAddress：" + SysUtils.getMacAddress(context) + "\n");
		sb.append("getMacFromHardware：" + SysUtils.getMacFromHardware(context) + "\n");
		sb.append("getMac：" + SysUtils.getMac(context) + "\n");

		sb.append("\n");

		collectBuildInfo(sb);

		info.setText(sb.toString());
		LOG.v(sb.toString());
	}

	private void collectBuildInfo(StringBuilder sb)
	{
		Field[] fields = Build.class.getFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				field.setAccessible(true);
				try
				{
					sb.append(field.getName()).append(" = ").append(field.get(null).toString()).append("\n");
				}
				catch (IllegalAccessException e)
				{
					sb.append(field.getName()).append(" = ").append("???").append("\n");
				}
			}
		}
	}

}
