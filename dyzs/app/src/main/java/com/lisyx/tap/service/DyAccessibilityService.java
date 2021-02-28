package com.lisyx.tap.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityMain;
import com.lisyx.tap.tools.DEFAULT.OperateDummy001;
import com.lisyx.tap.tools.DEFAULT.OperateDummy002;
import com.lisyx.tap.tools.DEFAULT.OperateDummy003;
import com.lisyx.tap.tools.DEFAULT.OperateDummy004;
import com.lisyx.tap.tools.DEFAULT.OperateDummy005;
import com.lisyx.tap.tools.DEFAULT.OperateDummy006;
import com.lisyx.tap.tools.DEFAULT.OperateDummy007;
import com.lisyx.tap.tools.DEFAULT.OperateDummy008;
import com.lisyx.tap.tools.DEFAULT.OperateDummy009;
import com.lisyx.tap.tools.DEFAULT.OperateDummy010;
import com.lisyx.tap.tools.DEFAULT.OperateDummy011;
import com.lisyx.tap.tools.DEFAULT.OperateDummy012;
import com.lisyx.tap.tools.DEFAULT.OperateDummy013;
import com.lisyx.tap.tools.DEFAULT.OperateDummy014;
import com.lisyx.tap.tools.DEFAULT.OperateDummy015;
import com.lisyx.tap.tools.DEFAULT.OperateDyBothGuan;
import com.lisyx.tap.tools.DEFAULT.OperateDyFenGuan;
import com.lisyx.tap.tools.DEFAULT.OperateDyLive;
import com.lisyx.tap.tools.DEFAULT.OperateDyPingXing;
import com.lisyx.tap.tools.DEFAULT.OperateDyQuGuan;
import com.lisyx.tap.tools.DEFAULT.OperateDyShiXing;
import com.lisyx.tap.tools.DEFAULT.OperateDyYangHao;
import com.lisyx.tap.tools.DEFAULT.OperateDyYingLiu;
import com.lisyx.tap.tools.DEFAULT.OperateGuangYu;
import com.lisyx.tap.tools.DEFAULT.OperateQQShiXing;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.utils.Protocol;
import com.lisyx.tap.utils.SPHelper;
import com.lisyx.tap.utils.SysUtils;
import com.lisyx.tap.view.Ball;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TapTryPast;
import com.lys.protobuf.SResponse_TapTryPast;

import java.util.HashMap;
import java.util.List;

// https://blog.csdn.net/lancelots/article/details/84067414
// https://blog.csdn.net/qq_35928566/article/details/86592100?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
// https://blog.csdn.net/weimingjue/article/details/82744146
public class DyAccessibilityService extends AccessibilityService
{
	public static long lastCheckTime = 0;

	public static void check(final Context context)
	{
//		if (SPHelper.getBoolean(context, ActivityMain.SP_Key_active, false))
//			return;
		if (Math.abs(System.currentTimeMillis() - lastCheckTime) > 10 * 60 * 1000)
		{
			lastCheckTime = System.currentTimeMillis();
			SRequest_TapTryPast request = new SRequest_TapTryPast();
			request.deviceId = SysUtils.getOnlyId(context);
			Protocol.doPost(context, SHandleId.TapTryPast, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_TapTryPast response = SResponse_TapTryPast.load(data);
						if (response.past)
						{
							SPHelper.putBoolean(context, ActivityMain.SP_Key_tryPast, true);
							Toast.makeText(context, "试用已过期，请购买激活码", Toast.LENGTH_LONG).show();
							Ball.stopRunning();
							Intent intent = new Intent(context, ActivityMain.class);
							intent.putExtra("past", true);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(intent);
						}
					}
				}
			});
		}
	}

	public static String Action_test(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".test";
	}

	public static String Action_screen(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".screen";
	}

	public static String Action_Start(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".start";
	}

	public static String Action_Stop(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".stop";
	}

	public static String Action_Click(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".click";
	}

	public static String Action_Smooth(Context context)
	{
		return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".smooth";
	}

	// 当系统成功连接到该AccessibilityService时,将调用此方法。主要用与一次性配置或调整的代码
	@Override
	protected void onServiceConnected()
	{
		LOG.v("--------------------------- onServiceConnected : " + getClass().getSimpleName());
		super.onServiceConnected();

		Intent intent = new Intent();
		intent.setAction(ActivityMain.Action_Connected(this));
		sendBroadcast(intent);

		IntentFilter filter = new IntentFilter();
		filter.addAction(DyAccessibilityService.Action_test(this));
		filter.addAction(DyAccessibilityService.Action_screen(this));
		filter.addAction(DyAccessibilityService.Action_Start(this));
		filter.addAction(DyAccessibilityService.Action_Stop(this));
		filter.addAction(DyAccessibilityService.Action_Click(this));
		filter.addAction(DyAccessibilityService.Action_Smooth(this));
		registerReceiver(mReceiver, filter);
	}

	// 当系统监测到相匹配的AccessibilityEvent事件时,将调用此方法,在整个Service的生命周期中,该方法将被多次调用
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event)
	{
//		LOG.v(this.getClass().getSimpleName() + " onAccessibilityEvent");
		if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED || event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED)
		{
			LOG.v("------- onAccessibilityEvent : " + event.toString());
		}
//		LOG.v("getEventType : " + event.getEventType());
//		LOG.v("getPackageName : " + event.getPackageName());
//		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//		String name = nodeInfo.getViewIdResourceName();

//		if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED)
//		{
//			AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//			printNode(nodeInfo, 0);
//		}

//		List<AccessibilityWindowInfo> windowInfos = getWindows();
	}

	// 系统需要中断AccessibilityService反馈时,将调用此方法。AccessibilityService反馈包括服务发起的震动、音频等行为
	@Override
	public void onInterrupt()
	{
		LOG.v("------- onInterrupt : " + getClass().getSimpleName());
	}

	// 系统要关闭该服务是,将调用此方法。主要用来释放资源
	@Override
	public boolean onUnbind(Intent intent)
	{
		LOG.v("--------------------------- onUnbind : " + getClass().getSimpleName());

		Intent i = new Intent();
		i.setAction(ActivityMain.Action_Unbind(this));
		sendBroadcast(i);

		unregisterReceiver(mReceiver);

		return super.onUnbind(intent);
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			LOG.v("Action:" + intent.getAction());
			processReceiver(context, intent);
		}
	};

	private void processReceiver(Context context, Intent intent)
	{
		if (intent.getAction().equals(DyAccessibilityService.Action_test(context)))
		{
			NodeHelper.printNode(getRootInActiveWindow());
//			AccessibilityNodeInfo nodeInfoGuanZhu = NodeHelper.findOneNode(nodeInfo, "android.widget.Button", null, "关注", null);
//			if (nodeInfoGuanZhu != null)
//			{
//				nodeInfoGuanZhu.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//			}
		}
		else if (intent.getAction().equals(DyAccessibilityService.Action_screen(context)))
		{
			LOG.toast(context, "截屏返回");
		}
		else if (intent.getAction().equals(DyAccessibilityService.Action_Start(context)))
		{
			int operateType = intent.getIntExtra("operateType", 0);
			start(operateType);
		}
		else if (intent.getAction().equals(DyAccessibilityService.Action_Stop(context)))
		{
			stop();
		}
		else if (intent.getAction().equals(DyAccessibilityService.Action_Click(context)))
		{
			int x = intent.getIntExtra("x", 0);
			int y = intent.getIntExtra("y", 0);
			click(x, y, null);
		}
		else if (intent.getAction().equals(DyAccessibilityService.Action_Smooth(context)))
		{
			int xfrom = intent.getIntExtra("xfrom", 0);
			int yfrom = intent.getIntExtra("yfrom", 0);
			int xto = intent.getIntExtra("xto", 0);
			int yto = intent.getIntExtra("yto", 0);
			int duration = intent.getIntExtra("duration", 0);
			smooth(xfrom, yfrom, xto, yto, duration, null);
		}
	}

	//----------------------------------------------------------------------


	public static final int OperateType_DyYingLiu = 1;
	public static final int OperateType_DyYangHao = 2;
	public static final int OperateType_DyQuGuan = 3;
	public static final int OperateType_DyBothGuan = 4;
	public static final int OperateType_DyFenGuan = 5;
	public static final int OperateType_DyShiXing = 6;
	public static final int OperateType_DyPingXing = 7;
	public static final int OperateType_DyLive = 8;
	public static final int OperateType_QQShiXing = 25;
	public static final int OperateType_Dummy001 = 9;
	public static final int OperateType_Dummy002 = 10;
	public static final int OperateType_Dummy003 = 11;
	public static final int OperateType_Dummy004 = 12;
	public static final int OperateType_Dummy005 = 13;
	public static final int OperateType_Dummy006 = 14;
	public static final int OperateType_Dummy007 = 15;
	public static final int OperateType_Dummy008 = 16;
	public static final int OperateType_Dummy009 = 17;
	public static final int OperateType_Dummy010 = 18;
	public static final int OperateType_Dummy011 = 19;
	public static final int OperateType_Dummy012 = 20;
	public static final int OperateType_Dummy013 = 21;
	public static final int OperateType_Dummy014 = 22;
	public static final int OperateType_Dummy015 = 23;
	public static final int OperateType_GuangYu = 24;


	//	public long smallWaitTime = 600;
//	public long middleWaitTime = 1500;
//	public long bigWaitTime = 2000;
//	public long largeWaitTime = 4000;


	private HashMap<Integer, OperateBase> operateBaseMap = new HashMap<>();

	public OperateBase getOperateBase(int operateType)
	{
		if (!operateBaseMap.containsKey(operateType))
		{
			if (operateType == OperateType_DyYingLiu)
			{
				operateBaseMap.put(operateType, new OperateDyYingLiu(this));
			}
			else if (operateType == OperateType_DyYangHao)
			{
				operateBaseMap.put(operateType, new OperateDyYangHao(this));
			}
			else if (operateType == OperateType_DyQuGuan)
			{
				operateBaseMap.put(operateType, new OperateDyQuGuan(this));
			}
			else if (operateType == OperateType_DyBothGuan)
			{
				operateBaseMap.put(operateType, new OperateDyBothGuan(this));
			}
			else if (operateType == OperateType_DyFenGuan)
			{
				operateBaseMap.put(operateType, new OperateDyFenGuan(this));
			}
			else if (operateType == OperateType_DyShiXing)
			{
				operateBaseMap.put(operateType, new OperateDyShiXing(this));
			}
			else if (operateType == OperateType_DyPingXing)
			{
				operateBaseMap.put(operateType, new OperateDyPingXing(this));
			}
			else if (operateType == OperateType_DyLive)
			{
				operateBaseMap.put(operateType, new OperateDyLive(this));
			}
			else if (operateType == OperateType_QQShiXing)
			{
				operateBaseMap.put(operateType, new OperateQQShiXing(this));
			}
			else if (operateType == OperateType_Dummy001)
			{
				operateBaseMap.put(operateType, new OperateDummy001(this));
			}
			else if (operateType == OperateType_Dummy002)
			{
				operateBaseMap.put(operateType, new OperateDummy002(this));
			}
			else if (operateType == OperateType_Dummy003)
			{
				operateBaseMap.put(operateType, new OperateDummy003(this));
			}
			else if (operateType == OperateType_Dummy004)
			{
				operateBaseMap.put(operateType, new OperateDummy004(this));
			}
			else if (operateType == OperateType_Dummy005)
			{
				operateBaseMap.put(operateType, new OperateDummy005(this));
			}
			else if (operateType == OperateType_Dummy006)
			{
				operateBaseMap.put(operateType, new OperateDummy006(this));
			}
			else if (operateType == OperateType_Dummy007)
			{
				operateBaseMap.put(operateType, new OperateDummy007(this));
			}
			else if (operateType == OperateType_Dummy008)
			{
				operateBaseMap.put(operateType, new OperateDummy008(this));
			}
			else if (operateType == OperateType_Dummy009)
			{
				operateBaseMap.put(operateType, new OperateDummy009(this));
			}
			else if (operateType == OperateType_Dummy010)
			{
				operateBaseMap.put(operateType, new OperateDummy010(this));
			}
			else if (operateType == OperateType_Dummy011)
			{
				operateBaseMap.put(operateType, new OperateDummy011(this));
			}
			else if (operateType == OperateType_Dummy012)
			{
				operateBaseMap.put(operateType, new OperateDummy012(this));
			}
			else if (operateType == OperateType_Dummy013)
			{
				operateBaseMap.put(operateType, new OperateDummy013(this));
			}
			else if (operateType == OperateType_Dummy014)
			{
				operateBaseMap.put(operateType, new OperateDummy014(this));
			}
			else if (operateType == OperateType_Dummy015)
			{
				operateBaseMap.put(operateType, new OperateDummy015(this));
			}
			else if (operateType == OperateType_GuangYu)
			{
				operateBaseMap.put(operateType, new OperateGuangYu(this));
			}
		}
		return operateBaseMap.get(operateType);
	}


	public void start(int operateType)
	{
//		ArrayList<Long> speedList = ActivityMain.readSpeed(this);
//		smallWaitTime = speedList.get(0);
//		middleWaitTime = speedList.get(1);
//		bigWaitTime = speedList.get(2);
//		largeWaitTime = speedList.get(3);

//		LOG.v(String.format("speed : %s, %s, %s, %s", smallWaitTime, middleWaitTime, bigWaitTime, largeWaitTime));

		getOperateBase(operateType).startMode(this);
	}

	public void stop()
	{
		waitHandler.removeCallbacks(waitRunnable);
		LOG.toast(this, "已停止");
	}

	public interface OnWaitCallback
	{
		void process();
	}

	private OnWaitCallback callback = null;

	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
//			LOG.v("------- wait run : " + isRunning());
			check(DyAccessibilityService.this);
			if (callback != null)
				callback.process();
		}
	};

	public void wait(long time, final OnWaitCallback callback)
	{
//		LOG.v("------- wait : " + time);
		this.callback = callback;
		waitHandler.postDelayed(waitRunnable, time);
	}

	public interface OnClickFunction
	{
		void click(AccessibilityNodeInfo node, String des);
	}

	public List<AccessibilityNodeInfo> findNodes(NodeHelper.OnFindCallback findCallback)
	{
		return NodeHelper.findNodes(getRootInActiveWindow(), findCallback);
	}

	public AccessibilityNodeInfo clickIfFindOne(String des, NodeHelper.OnFindCallback findCallback, OnClickFunction clickFunction)
	{
		return clickIfFindOne(getRootInActiveWindow(), des, findCallback, clickFunction);
	}

	public AccessibilityNodeInfo clickIfFindOne(AccessibilityNodeInfo parentNode, String des, NodeHelper.OnFindCallback findCallback, OnClickFunction clickFunction)
	{
		LOG.v("to find " + des);
		List<AccessibilityNodeInfo> nodes = NodeHelper.findNodes(parentNode, findCallback);
		if (nodes.size() == 1)
		{
			LOG.v("find " + des);
			AccessibilityNodeInfo node = nodes.get(0);
			if (clickFunction != null)
				clickFunction.click(node, des);
			else
				clickNode(node, des);
			return node;
		}
		else
		{
			LOG.e(String.format("find %s error : size is %s", des, nodes.size()));
		}
		return null;
	}

	public AccessibilityNodeInfo clickByIndex(String des, int index, NodeHelper.OnFindCallback findCallback, OnClickFunction clickFunction)
	{
		return clickByIndex(getRootInActiveWindow(), des, index, findCallback, clickFunction);
	}

	public AccessibilityNodeInfo clickByIndex(AccessibilityNodeInfo parentNode, String des, int index, NodeHelper.OnFindCallback findCallback, OnClickFunction clickFunction)
	{
		LOG.v("to find " + des);
		List<AccessibilityNodeInfo> nodes = NodeHelper.findNodes(parentNode, findCallback);
		if (nodes.size() > index)
		{
			LOG.v("find " + des + " at " + index);
			AccessibilityNodeInfo node = nodes.get(index);
			if (clickFunction != null)
				clickFunction.click(node, des);
			else
				clickNode(node, des);
			return node;
		}
		else
		{
			LOG.e(String.format("find %s error : size is %s", des, nodes.size()));
		}
		return null;
	}

	public void clickNode(AccessibilityNodeInfo node, String des)
	{
		if (node != null && node.isVisibleToUser())
		{
			if (node.isClickable())
			{
				LOG.v("click " + des);
				node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			}
			else
			{
				clickNode(node.getParent(), des);
			}
		}
	}

	public void setNodeText(AccessibilityNodeInfo node, String text)
	{
		Bundle arguments = new Bundle();
		arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
		node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
	}

	public void inputNode(AccessibilityNodeInfo node, String text)
	{
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("text", text);
		clipboard.setPrimaryClip(clip);
		node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
		node.performAction(AccessibilityNodeInfo.ACTION_PASTE);
	}

	public boolean click(int x, int y, GestureResultCallback callback)
	{
		LOG.v("click : " + x + ", " + y);
		try
		{
			Path path = new Path();
			path.moveTo(x, y);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0, 100)).build(), callback, null);
				return true;
			}
			else
			{
				Toast.makeText(this, String.format("系统版本太低"), Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean smooth(int xfrom, int yfrom, int xto, int yto, int duration, GestureResultCallback callback)
	{
		LOG.v("smooth : " + xfrom + ", " + yfrom + ", " + xto + ", " + yto + ", " + duration);
		try
		{
			Path path = new Path();
			path.moveTo(xfrom, yfrom);
			path.lineTo(xto, yto);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0, 500, true)).build(), callback, null);
				return true;
			}
			else
			{
				Toast.makeText(this, String.format("系统版本太低"), Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static void sendclick(Context context, int x, int y)
	{
		Intent intent = new Intent();
		intent.setAction(Action_Click(context));
		intent.putExtra("x", x);
		intent.putExtra("y", y);
		context.sendBroadcast(intent);
	}

	public static void sendsmooth(Context context, int xfrom, int yfrom, int xto, int yto, int duration)
	{
		Intent intent = new Intent();
		intent.setAction(Action_Smooth(context));
		intent.putExtra("xfrom", xfrom);
		intent.putExtra("yfrom", yfrom);
		intent.putExtra("xto", xto);
		intent.putExtra("yto", yto);
		intent.putExtra("duration", duration);
		context.sendBroadcast(intent);
	}

}
