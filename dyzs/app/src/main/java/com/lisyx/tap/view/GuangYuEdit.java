package com.lisyx.tap.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lisyx.tap.R;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.DEFAULT.OperateGuangYu;
import com.lisyx.tap.utils.SysUtils;

public class GuangYuEdit extends RelativeLayout implements View.OnClickListener
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
		private TextView guanyuClear;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.guanyuClear = findViewById(R.id.guanyuClear);
	}

	public GuangYuEdit(Context context)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_guang_yu_edit, this, true);

		initHolder();

		findViewById(R.id.aCenter).setOnClickListener(this);

		findViewById(R.id.aTop).setOnClickListener(this);
		findViewById(R.id.aBottom).setOnClickListener(this);
		findViewById(R.id.aLeft).setOnClickListener(this);
		findViewById(R.id.aRight).setOnClickListener(this);
		findViewById(R.id.aLeftTop).setOnClickListener(this);
		findViewById(R.id.aRightTop).setOnClickListener(this);
		findViewById(R.id.aLeftBottom).setOnClickListener(this);
		findViewById(R.id.aRightBottom).setOnClickListener(this);

		findViewById(R.id.guanyuClear).setOnClickListener(this);
		findViewById(R.id.guanyuRun).setOnClickListener(this);

		findViewById(R.id.jump).setOnClickListener(this);
		findViewById(R.id.test2).setOnClickListener(this);

		holder.guanyuClear.setText(String.format("清除(%s)", OperateGuangYu.readGuangyu().size()));

		initParams();
	}

	private int ax = 0;
	private int ay = 0;

	private int bx = 0;
	private int by = 0;

	private int len = 0;

	private int duration = 5000;

	private int jumpx = 0;
	private int jumpy = 0;

	private void initParams()
	{
		int screenWidth = SysUtils.screenWidth(context);
		int screenHeight = SysUtils.screenHeight(context);

		ax = screenWidth * 1 / 4;
		ay = screenHeight * 1 / 2;

		bx = screenWidth * 3 / 4;
		by = screenHeight * 1 / 2;

		len = screenWidth * 1 / 8;

		jumpx = screenWidth * 7 / 8;
		jumpy = screenHeight * 4 / 5;
	}

	private void sendsmooth(final int xfrom, final int yfrom, final int xto, final int yto, final int duration)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				OperateGuangYu.addGuangyuSmooth(xfrom, yfrom, xto, yto, duration);
				holder.guanyuClear.setText(String.format("清除(%s)", OperateGuangYu.readGuangyu().size()));
				DyAccessibilityService.sendsmooth(context, xfrom, yfrom, xto, yto, duration);
			}
		}, 100);
	}

	private void sendjump(final int x, final int y)
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				OperateGuangYu.addGuangyuClick(x, y);
				holder.guanyuClear.setText(String.format("清除(%s)", OperateGuangYu.readGuangyu().size()));
				DyAccessibilityService.sendclick(context, x, y);
			}
		}, 100);
	}

	@Override
	public void onClick(final View view)
	{
		if (view.getId() == R.id.aCenter)
		{
			hidePanel();
		}
		else if (view.getId() == R.id.aTop)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax, ay - len, duration);
		}
		else if (view.getId() == R.id.aBottom)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax, ay + len, duration);
		}
		else if (view.getId() == R.id.aLeft)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax - len, ay, duration);
		}
		else if (view.getId() == R.id.aRight)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax + len, ay, duration);
		}
		else if (view.getId() == R.id.aLeftTop)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax - len, ay - len, duration);
		}
		else if (view.getId() == R.id.aRightTop)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax + len, ay - len, duration);
		}
		else if (view.getId() == R.id.aLeftBottom)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax - len, ay + len, duration);
		}
		else if (view.getId() == R.id.aRightBottom)
		{
//			hidePanel();
			sendsmooth(ax, ay, ax + len, ay + len, duration);
		}
		else if (view.getId() == R.id.guanyuClear)
		{
			OperateGuangYu.clearGuangyu();
			holder.guanyuClear.setText(String.format("清除(%s)", OperateGuangYu.readGuangyu().size()));
		}
		else if (view.getId() == R.id.guanyuRun)
		{
			hidePanel();
			if (listener != null)
				listener.onClick(DyAccessibilityService.OperateType_GuangYu);
		}
		else if (view.getId() == R.id.jump)
		{
//			hidePanel();
			sendjump(jumpx, jumpy);
		}
		else if (view.getId() == R.id.test2)
		{
			hidePanel();
		}
	}

	//--------------------------------------

	private static Context context = null;

	private static WindowManager windowManager = null;
	private static WindowManager.LayoutParams layoutParams = null;
	private static GuangYuEdit guangYuEditView = null;

	public static boolean isShow()
	{
		return guangYuEditView != null;
	}

	public static void showPanel(Context ctx, OnListener listener)
	{
		if (guangYuEditView == null)
		{
			context = ctx.getApplicationContext();
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			layoutParams = new WindowManager.LayoutParams();
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
				layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
			else
				layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
			layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
			layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
			layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
			layoutParams.x = 0;
			layoutParams.y = 0;
			layoutParams.format = PixelFormat.RGBA_8888;

			guangYuEditView = new GuangYuEdit(context);
			guangYuEditView.setListener(listener);
			try
			{
				windowManager.addView(guangYuEditView, layoutParams);
			}
			catch (Exception e)
			{
				try
				{
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
						layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
					else
						layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
					windowManager.addView(guangYuEditView, layoutParams);
				}
				catch (Exception e1)
				{
				}
			}
		}
	}

	public static void hidePanel()
	{
		if (guangYuEditView != null)
		{
			windowManager.removeView(guangYuEditView);
			guangYuEditView = null;
		}
	}

}
