package com.lisyx.tap.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lisyx.tap.R;
import com.lisyx.tap.activity.CaptureScreen;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.utils.SysUtils;

public class MenuPanel extends RelativeLayout implements View.OnClickListener
{
	public interface OnListener
	{
		void onClick(int operateType);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
//		private View core;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.core = findViewById(R.id.core);
	}

	public MenuPanel(Context context)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_menu_panel, this, true);

		initHolder();

		findViewById(R.id.bg).setOnClickListener(this);

		findViewById(R.id.optDyYingLiu).setOnClickListener(this);
		findViewById(R.id.optDyYangHao).setOnClickListener(this);
		findViewById(R.id.optDyQuGuan).setOnClickListener(this);
		findViewById(R.id.optDyBothGuan).setOnClickListener(this);
		findViewById(R.id.optDyFenGuan).setOnClickListener(this);
		findViewById(R.id.optDyShiXing).setOnClickListener(this);
		findViewById(R.id.optDyPingXing).setOnClickListener(this);
		findViewById(R.id.optDyLive).setOnClickListener(this);
		findViewById(R.id.optQQShiXing).setOnClickListener(this);
		findViewById(R.id.optDummy001).setOnClickListener(this);
		findViewById(R.id.optDummy002).setOnClickListener(this);
		findViewById(R.id.optDummy003).setOnClickListener(this);
		findViewById(R.id.optDummy004).setOnClickListener(this);
		findViewById(R.id.optDummy005).setOnClickListener(this);
		findViewById(R.id.optDummy006).setOnClickListener(this);
		findViewById(R.id.optDummy007).setOnClickListener(this);
		findViewById(R.id.optDummy008).setOnClickListener(this);
		findViewById(R.id.optDummy009).setOnClickListener(this);
		findViewById(R.id.optDummy010).setOnClickListener(this);
		findViewById(R.id.optDummy011).setOnClickListener(this);
		findViewById(R.id.optDummy012).setOnClickListener(this);
		findViewById(R.id.optDummy013).setOnClickListener(this);
		findViewById(R.id.optDummy014).setOnClickListener(this);
		findViewById(R.id.optDummy015).setOnClickListener(this);

		findViewById(R.id.optDyYingLiu).setVisibility(SysUtils.DyYingLiu_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyYangHao).setVisibility(SysUtils.DyYangHao_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyQuGuan).setVisibility(SysUtils.DyQuGuan_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyBothGuan).setVisibility(SysUtils.DyBothGuan_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyFenGuan).setVisibility(SysUtils.DyFenGuan_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyShiXing).setVisibility(SysUtils.DyShiXing_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyPingXing).setVisibility(SysUtils.DyPingXing_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDyLive).setVisibility(SysUtils.DyLive_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optQQShiXing).setVisibility(SysUtils.QQShiXing_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy001).setVisibility(SysUtils.Dummy001_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy002).setVisibility(SysUtils.Dummy002_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy003).setVisibility(SysUtils.Dummy003_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy004).setVisibility(SysUtils.Dummy004_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy005).setVisibility(SysUtils.Dummy005_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy006).setVisibility(SysUtils.Dummy006_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy007).setVisibility(SysUtils.Dummy007_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy008).setVisibility(SysUtils.Dummy008_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy009).setVisibility(SysUtils.Dummy009_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy010).setVisibility(SysUtils.Dummy010_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy011).setVisibility(SysUtils.Dummy011_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy012).setVisibility(SysUtils.Dummy012_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy013).setVisibility(SysUtils.Dummy013_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy014).setVisibility(SysUtils.Dummy014_enable ? View.VISIBLE : View.GONE);
		findViewById(R.id.optDummy015).setVisibility(SysUtils.Dummy015_enable ? View.VISIBLE : View.GONE);

		findViewById(R.id.test1).setOnClickListener(this);
		findViewById(R.id.test2).setOnClickListener(this);

		if (SysUtils.test_enable)
		{
			findViewById(R.id.test1).setVisibility(View.VISIBLE);
			findViewById(R.id.test2).setVisibility(View.VISIBLE);
		}
		else
		{
			findViewById(R.id.test1).setVisibility(View.GONE);
			findViewById(R.id.test2).setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(final View view)
	{
		if (view.getId() == R.id.bg)
		{
			hidePanel();
		}
		else if (view.getId() == R.id.optDyYingLiu)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyYingLiu);
		}
		else if (view.getId() == R.id.optDyYangHao)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyYangHao);
		}
		else if (view.getId() == R.id.optDyQuGuan)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyQuGuan);
		}
		else if (view.getId() == R.id.optDyBothGuan)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyBothGuan);
		}
		else if (view.getId() == R.id.optDyFenGuan)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyFenGuan);
		}
		else if (view.getId() == R.id.optDyShiXing)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyShiXing);
		}
		else if (view.getId() == R.id.optDyPingXing)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyPingXing);
		}
		else if (view.getId() == R.id.optDyLive)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_DyLive);
		}
		else if (view.getId() == R.id.optQQShiXing)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_QQShiXing);
		}
		else if (view.getId() == R.id.optDummy001)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy001);
		}
		else if (view.getId() == R.id.optDummy002)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy002);
		}
		else if (view.getId() == R.id.optDummy003)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy003);
		}
		else if (view.getId() == R.id.optDummy004)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy004);
		}
		else if (view.getId() == R.id.optDummy005)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy005);
		}
		else if (view.getId() == R.id.optDummy006)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy006);
		}
		else if (view.getId() == R.id.optDummy007)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy007);
		}
		else if (view.getId() == R.id.optDummy008)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy008);
		}
		else if (view.getId() == R.id.optDummy009)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy009);
		}
		else if (view.getId() == R.id.optDummy010)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy010);
		}
		else if (view.getId() == R.id.optDummy011)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy011);
		}
		else if (view.getId() == R.id.optDummy012)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy012);
		}
		else if (view.getId() == R.id.optDummy013)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy013);
		}
		else if (view.getId() == R.id.optDummy014)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy014);
		}
		else if (view.getId() == R.id.optDummy015)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_Dummy015);
		}
		else if (view.getId() == R.id.test1)
		{
			hidePanel();
			new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					Intent intent = new Intent();
					intent.setAction(DyAccessibilityService.Action_test(context));
					context.sendBroadcast(intent);
				}
			}, 100);
		}
		else if (view.getId() == R.id.test2)
		{
			hidePanel();
			new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					CaptureScreen.start(context);
				}
			}, 100);
		}
	}

	//--------------------------------------

	private static Context context = null;

	private static WindowManager windowManager = null;
	private static WindowManager.LayoutParams layoutParams = null;
	private static MenuPanel menuPanelView = null;

	public static boolean isShow()
	{
		return menuPanelView != null;
	}

	public static void showPanel(Context ctx, OnListener listener)
	{
		if (menuPanelView == null)
		{
			context = ctx.getApplicationContext();
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			layoutParams = new WindowManager.LayoutParams();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
				layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
			else
				layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
			layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
			layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
			layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
			layoutParams.x = 0;
			layoutParams.y = 0;
			layoutParams.format = PixelFormat.RGBA_8888;

			menuPanelView = new MenuPanel(context);
			menuPanelView.setListener(listener);
			try
			{
				windowManager.addView(menuPanelView, layoutParams);
			}
			catch (Exception e)
			{
				try
				{
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
						layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
					else
						layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
					windowManager.addView(menuPanelView, layoutParams);
				}
				catch (Exception e1)
				{
				}
			}
		}
	}

	public static void hidePanel()
	{
		if (menuPanelView != null)
		{
			windowManager.removeView(menuPanelView);
			menuPanelView = null;
		}
	}

}
