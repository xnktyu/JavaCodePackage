package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.FsUtils;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.view.Ball;
import com.lys.base.utils.JsonHelper;

import java.io.File;

public class OperateGuangYu extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateGuangYu(DyAccessibilityService service)
	{
		this.service = service;
	}


	public static final int Type_Smooth = 0;
	public static final int Type_Click = 1;


	private static File guangyuFile = new File(Environment.getExternalStorageDirectory(), "guangyu.txt");

	public static JSONArray readGuangyu()
	{
		String text = FsUtils.readText(guangyuFile);
		if (!TextUtils.isEmpty(text))
		{
			return JsonHelper.getJSONArray(text);
		}
		return new JSONArray();
	}

	private static void saveGuangyu(JSONArray ja)
	{
		FsUtils.writeText(guangyuFile, ja.toString());
	}

	public static void addGuangyuSmooth(int xfrom, int yfrom, int xto, int yto, int duration)
	{
		JSONObject obj = new JSONObject(true);
		obj.put("time", System.currentTimeMillis());
		obj.put("type", Type_Smooth);
		obj.put("xfrom", xfrom);
		obj.put("yfrom", yfrom);
		obj.put("xto", xto);
		obj.put("yto", yto);
		obj.put("duration", duration);

		JSONArray ja = readGuangyu();
		ja.add(obj);
		saveGuangyu(ja);
	}

	public static void addGuangyuClick(int x, int y)
	{
		JSONObject obj = new JSONObject(true);
		obj.put("time", System.currentTimeMillis());
		obj.put("type", Type_Click);
		obj.put("x", x);
		obj.put("y", y);

		JSONArray ja = readGuangyu();
		ja.add(obj);
		saveGuangyu(ja);
	}

	public static void clearGuangyu()
	{
		FsUtils.delete(guangyuFile);
	}

	private JSONArray guangyuArray;
	private int currIndex;
	private long runTime;
	private long recordTime;

	@Override
	public void start()
	{
		guangyuArray = readGuangyu();
		currIndex = 0;
		runTime = System.currentTimeMillis();
		LOG.v(String.format("size = %s", guangyuArray.size()));
		if (guangyuArray.size() > 0)
		{
			JSONObject obj = guangyuArray.getJSONObject(currIndex);
			recordTime = obj.getLong("time");
			startOne();
		}
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		runOne();
		tick();
	}

	private void tick()
	{
		service.wait(50, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				if (currIndex < guangyuArray.size() && Ball.isRunning())
				{
					JSONObject obj = guangyuArray.getJSONObject(currIndex);
					long time = obj.getLong("time");
					if (System.currentTimeMillis() - runTime > time - recordTime)
					{
						runOne();
					}
					tick();
				}
				else
				{
					Ball.stopRunning();
					Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void runOne()
	{
		if (currIndex < guangyuArray.size() && Ball.isRunning())
		{
			JSONObject obj = guangyuArray.getJSONObject(currIndex);
			if (obj.getInteger("type") == Type_Smooth)
			{
				service.smooth(obj.getInteger("xfrom"), obj.getInteger("yfrom"), obj.getInteger("xto"), obj.getInteger("yto"), obj.getInteger("duration"), new AccessibilityService.GestureResultCallback()
				{
					@Override
					public void onCompleted(GestureDescription gestureDescription)
					{
						super.onCompleted(gestureDescription);
						LOG.v("smooth onCompleted");
//						runOne(index + 1);
//						service.wait(2000, new DyAccessibilityService.OnWaitCallback()
//						{
//							@Override
//							public void process()
//							{
//								runOne(index + 1);
//							}
//						});
					}

					@Override
					public void onCancelled(GestureDescription gestureDescription)
					{
						super.onCancelled(gestureDescription);
						LOG.v("smooth onCancelled");
//						runOne(index + 1);
					}
				});
			}
			else if (obj.getInteger("type") == Type_Click)
			{
				service.click(obj.getInteger("x"), obj.getInteger("y"), new AccessibilityService.GestureResultCallback()
				{
					@Override
					public void onCompleted(GestureDescription gestureDescription)
					{
						super.onCompleted(gestureDescription);
						LOG.v("click onCompleted");
//						runOne(index + 1);
					}

					@Override
					public void onCancelled(GestureDescription gestureDescription)
					{
						super.onCancelled(gestureDescription);
						LOG.v("click onCancelled");
//						runOne(index + 1);
					}
				});
			}
			currIndex++;
		}
		else
		{
			Ball.stopRunning();
			Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
		}
	}

}
