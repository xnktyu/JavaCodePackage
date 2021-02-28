package com.lisyx.tap.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import com.alibaba.fastjson.JSONObject;
import com.lisyx.tap.BuildConfig;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class SysUtils
{
	public static final boolean DyYingLiu_enable = true;
	public static final boolean DyYangHao_enable = true;
	public static final boolean DyQuGuan_enable = true;
	public static final boolean DyBothGuan_enable = true;
	public static final boolean DyFenGuan_enable = true;
	public static final boolean DyShiXing_enable = true;
	public static final boolean DyPingXing_enable = true;
	public static final boolean DyLive_enable = true;
	public static final boolean QQShiXing_enable = false;
	public static final boolean Dummy001_enable = false;
	public static final boolean Dummy002_enable = false;
	public static final boolean Dummy003_enable = false;
	public static final boolean Dummy004_enable = false;
	public static final boolean Dummy005_enable = false;
	public static final boolean Dummy006_enable = false;
	public static final boolean Dummy007_enable = false;
	public static final boolean Dummy008_enable = false;
	public static final boolean Dummy009_enable = false;
	public static final boolean Dummy010_enable = false;
	public static final boolean Dummy011_enable = false;
	public static final boolean Dummy012_enable = false;
	public static final boolean Dummy013_enable = false;
	public static final boolean Dummy014_enable = false;
	public static final boolean Dummy015_enable = false;

	public static final boolean guangyu_enable = false;
	public static final boolean test_enable = false;

	public static boolean isDebug()
	{
		return false;
//		return BuildConfig.DEBUG;
	}

	public static String buildType()
	{
		return "release";
//		return BuildConfig.BUILD_TYPE;
	}

	public static boolean isHUAWEIVNSAL00()
	{
		return "HUAWEI VNS-AL00".equals(Build.MODEL);
	}

	public static boolean isHUAWEIP7L07()
	{
		return "HUAWEI P7-L07".equals(Build.MODEL);
	}

	public static boolean isMI4LTE()
	{
		return "MI 4LTE".equals(Build.MODEL);
	}

	public static int dp2px(float dpValue)
	{
		float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dp(float pxValue)
	{
		float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(float spValue)
	{
		final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int px2sp(float pxValue)
	{
		final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int screenWidth(Context context)
	{
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
//		LOG.v("screenWidth:" + dm.widthPixels);
		return dm.widthPixels;
	}

	public static int screenHeight(Context context)
	{
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
//		LOG.v("screenHeight:" + dm.heightPixels);
		return dm.heightPixels;
	}

	public static String getAndroidId(Context context)
	{
		return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
	}

	public static String getIMEI(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
			return "";
		return telephonyManager.getDeviceId();
	}

	public static String getMacDefault(Context context)
	{
		try
		{
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null)
			{
				return info.getMacAddress();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String getMacAddress(Context context)
	{
		try
		{
			Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String str = input.readLine();
			if (str != null)
			{
				return str.trim();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String getMacFromHardware(Context context)
	{
		try
		{
			ArrayList<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all)
			{
				if (nif.getName() != null && nif.getName().toLowerCase().equals("wlan0"))
				{
					byte[] macBytes = nif.getHardwareAddress();
					if (macBytes != null)
					{
						StringBuilder sb = new StringBuilder();
						for (Byte b : macBytes)
						{
							sb.append(String.format("%02X:", b));
						}
						if (!TextUtils.isEmpty(sb))
						{
							sb.deleteCharAt(sb.length() - 1);
						}
						return sb.toString();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String getMac(Context context)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
			return getMacDefault(context);
		else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
			return getMacAddress(context);
		else
			return getMacFromHardware(context);
	}

	private static String onlyId = null;

	private static String getOnlyIdImpl(Context context)
	{
		if (!"unknown".equals(Build.SERIAL))
			return ("D" + Build.SERIAL).toUpperCase().trim();
		else if (!TextUtils.isEmpty(getIMEI(context)))
			return ("I" + getIMEI(context)).toUpperCase().trim();
		else
			return ("A" + getAndroidId(context)).toUpperCase().trim();
	}

	public static String getOnlyId(Context context)
	{
		if (TextUtils.isEmpty(onlyId))
			onlyId = getOnlyIdImpl(context);
		return onlyId;
	}

	public static void openFile(Context context, File file)
	{
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", file);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
		}
		else
		{
			intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(end));
		}
		context.startActivity(intent);
	}

	public static PackageInfo getApkPackageInfo(Context context, String absPath)
	{
		PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
		if (packageInfo != null)
		{
			return packageInfo;
		}
		return null;
	}

	public static PackageInfo getPackageInfo(Context context, String packageName)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(packageName, 0);
		}
		catch (PackageManager.NameNotFoundException e)
		{
//			e.printStackTrace();
		}
		return null;
	}

	public static PackageInfo getPackageInfo(Context context)
	{
		try
		{
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		}
		catch (PackageManager.NameNotFoundException e)
		{
//			e.printStackTrace();
		}
		return null;
	}

	public static int getVersionCode(Context context)
	{
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo != null)
			return packageInfo.versionCode;
		return 0;
	}

	public static String getVersionName(Context context)
	{
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo != null)
			return packageInfo.versionName;
		return "";
	}

	public static JSONObject getDeviceInfo(Context context)
	{
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(memoryInfo);

		JSONObject obj = new JSONObject(true);
		obj.put("screenWidth", SysUtils.screenWidth(context));
		obj.put("screenHeight", SysUtils.screenHeight(context));
		obj.put("px2dp", SysUtils.px2dp(SysUtils.screenWidth(context)));
		obj.put("px2sp", SysUtils.px2sp(SysUtils.screenWidth(context)));
		obj.put("totalSpace", Environment.getExternalStorageDirectory().getTotalSpace());
		obj.put("freeSpace", Environment.getExternalStorageDirectory().getFreeSpace());
		obj.put("totalMem", memoryInfo.totalMem);
		obj.put("availMem", memoryInfo.availMem);
		obj.put("BRAND", Build.BRAND);
		obj.put("MODEL", Build.MODEL);
		obj.put("DISPLAY", Build.DISPLAY);
		obj.put("AndroidVersion", Build.VERSION.RELEASE);
		obj.put("SDKVersion", Build.VERSION.SDK_INT);
		obj.put("Serial", Build.SERIAL);
		obj.put("IMEI", getIMEI(context));
		obj.put("AndroidId", getAndroidId(context));
		return obj;
	}
}
