package com.lisyx.tap.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lisyx.tap.R;
import com.lisyx.tap.activity.ActivityMain;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.SPHelper;
import com.lisyx.tap.utils.SysUtils;

public class Ball extends RelativeLayout implements View.OnClickListener
{
	private class Holder
	{
		private View core;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.core = findViewById(R.id.core);
	}

	public Ball(Context context)
	{
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_ball, this, true);

		initHolder();

//		findViewById(R.id.ballClose).setOnClickListener(this);
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		LOG.v("--------------------------- onAttachedToWindow : " + getClass().getSimpleName());
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		LOG.v("--------------------------- onDetachedFromWindow : " + getClass().getSimpleName());
	}

	@Override
	public void onClick(final View view)
	{
//		if (view.getId() == R.id.chat1)
//		{
//		}
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		checkPos();
	}

	private void checkPos()
	{
		LOG.v("checkPos");
		layoutParams.x = -SysUtils.screenWidth(getContext()) / 2 + OFFSET_X;
		int minY = -SysUtils.screenHeight(getContext()) / 4;
		int maxY = SysUtils.screenHeight(getContext()) / 4;
		layoutParams.y = Math.max(layoutParams.y, minY);
		layoutParams.y = Math.min(layoutParams.y, maxY);
		windowManager.updateViewLayout(ballView, layoutParams);
	}

	private boolean isRunning = false;

	private void setRunning(boolean isRunning)
	{
//		LOG.v("setRunning : " + isRunning);
		this.isRunning = isRunning;
		if (isRunning)
		{
			holder.core.setBackgroundResource(R.drawable.ball_core_run);
			setKeepScreenOn(true);
		}
		else
		{
			holder.core.setBackgroundResource(R.drawable.ball_core_stop);
			setKeepScreenOn(false);
		}
	}

	//--------------------------------------

	private static final int OFFSET_X = SysUtils.dp2px(25);

	private static Context context = null;

	private static WindowManager windowManager = null;
	private static WindowManager.LayoutParams layoutParams = null;
	private static Ball ballView = null;

//	private static OperateHelper operate = new OperateHelper();

	public static boolean isShow()
	{
		return ballView != null;
	}

	public static void showBall(final Context ctx)
	{
		if (ballView == null)
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
			layoutParams.x = -SysUtils.screenWidth(context) / 2 + OFFSET_X;
			layoutParams.y = 0;
			layoutParams.format = PixelFormat.RGBA_8888;

			ballView = new Ball(context);
			ballView.setOnTouchListener(new OnTouchListener()
			{
				private PointF touchBegin = null;
				private int initX = 0;
				private int initY = 0;
				private boolean hasMove = false;

				@Override
				public boolean onTouch(View view, MotionEvent event)
				{
					if (event.getAction() == MotionEvent.ACTION_DOWN)
					{
						PointF point = new PointF(event.getRawX(), event.getRawY());
						touchBegin = point;
						initX = layoutParams.x;
						initY = layoutParams.y;
						hasMove = false;
					}
					else if (event.getAction() == MotionEvent.ACTION_MOVE)
					{
						PointF point = new PointF(event.getRawX(), event.getRawY());
						int offsetX = (int) (point.x - touchBegin.x);
						int offsetY = (int) (point.y - touchBegin.y);
//						LOG.v("offset:" + offsetX + "," + offsetY);
						if (!hasMove && !isRunning())
						{
							if (Math.abs(offsetX) > 4 || Math.abs(offsetY) > 4)
							{
								hasMove = true;
							}
						}
						if (hasMove)
						{
							layoutParams.x = initX + offsetX;
							layoutParams.y = initY + offsetY;
							windowManager.updateViewLayout(ballView, layoutParams);
						}
					}
					else if (event.getAction() == MotionEvent.ACTION_UP)
					{
						if (hasMove)
						{
							ballView.checkPos();
						}
						else
						{
							LOG.v("click ball");
							clickBall();
						}
					}
					return true;
				}
			});
			try
			{
				windowManager.addView(ballView, layoutParams);
				LOG.toast(ctx, "打开成功");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				LOG.toast(ctx, "打开失败");
				try
				{
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
						layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
					else
						layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
					windowManager.addView(ballView, layoutParams);
					LOG.toast(ctx, "打开成功2");
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					LOG.toast(ctx, "打开失败2");
					AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
					builder.setTitle("提示信息");
					builder.setMessage("悬浮窗权限未打开，请单击【设置】按钮前往设置中心进行权限授权。");
					builder.setNeutralButton("取消", null);
					builder.setPositiveButton("设置", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialogInterface, int which)
						{
							Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
							intent.setData(Uri.parse("package:" + ctx.getPackageName()));
							ctx.startActivity(intent);
						}
					});
					builder.show();
				}
			}
		}
	}

	public static boolean isRunning()
	{
		if (ballView != null)
			return ballView.isRunning;
		return false;
	}

	public static void hideBall()
	{
		if (ballView != null)
		{
			windowManager.removeView(ballView);
			ballView = null;
		}
	}

	private static void stopRunningImpl()
	{
		Intent intent = new Intent();
		intent.setAction(DyAccessibilityService.Action_Stop(context));
		context.sendBroadcast(intent);

		ballView.setRunning(false);
	}

	public static void stopRunning()
	{
		if (isShow())
		{
			if (isRunning())
			{
				stopRunningImpl();
			}
		}
	}

	private static void clickBall()
	{
		if (isShow())
		{
			if (isRunning())
			{
				stopRunningImpl();
			}
			else
			{
				if (true)
				{
					if (SysUtils.guangyu_enable)
					{
						if (GuangYuEdit.isShow())
						{
							GuangYuEdit.hidePanel();
						}
						else
						{
							GuangYuEdit.showPanel(context, new GuangYuEdit.OnListener()
							{
								@Override
								public void onClick(int operateType)
								{
									if (SPHelper.getBoolean(context, ActivityMain.SP_Key_active, false) || //
											!SPHelper.getBoolean(context, ActivityMain.SP_Key_tryPast, true))
									{
										Intent intent = new Intent();
										intent.setAction(DyAccessibilityService.Action_Start(context));
										intent.putExtra("operateType", operateType);
										context.sendBroadcast(intent);

										ballView.setRunning(true);
									}
									else
									{
										Toast.makeText(context, "试用已过期，请购买激活码", Toast.LENGTH_LONG).show();
									}
								}
							});
						}
					}
					else
					{
						if (MenuPanel.isShow())
						{
							MenuPanel.hidePanel();
						}
						else
						{
							MenuPanel.showPanel(context, new MenuPanel.OnListener()
							{
								@Override
								public void onClick(int operateType)
								{
									if (SPHelper.getBoolean(context, ActivityMain.SP_Key_active, false) || //
											!SPHelper.getBoolean(context, ActivityMain.SP_Key_tryPast, true))
									{
										Intent intent = new Intent();
										intent.setAction(DyAccessibilityService.Action_Start(context));
										intent.putExtra("operateType", operateType);
										context.sendBroadcast(intent);

										ballView.setRunning(true);
									}
									else
									{
										Toast.makeText(context, "试用已过期，请购买激活码", Toast.LENGTH_LONG).show();
									}
								}
							});
						}
					}
				}
				else
				{
//					DialogMenu.Builder builder = new DialogMenu.Builder(context);
//					builder.setMenu("Mode1", new DialogMenu.OnClickMenuListener()
//					{
//						@Override
//						public void onClick()
//						{
//							if (operate.start(context, OperateBase.OperateType_Mode1))
//								ballView.setRunning(true);
//						}
//					});
//					builder.setMenu("Mode2", new DialogMenu.OnClickMenuListener()
//					{
//						@Override
//						public void onClick()
//						{
//							if (operate.start(context, OperateBase.OperateType_Mode2))
//								ballView.setRunning(true);
//						}
//					});
//					builder.setMenu("Mode3", new DialogMenu.OnClickMenuListener()
//					{
//						@Override
//						public void onClick()
//						{
//							if (operate.start(context, OperateBase.OperateType_Mode3))
//								ballView.setRunning(true);
//						}
//					});
//					builder.setMenu("Mode4", new DialogMenu.OnClickMenuListener()
//					{
//						@Override
//						public void onClick()
//						{
//							if (operate.start(context, OperateBase.OperateType_Mode4))
//								ballView.setRunning(true);
//						}
//					});
//					builder.setMenu("Mode5", new DialogMenu.OnClickMenuListener()
//					{
//						@Override
//						public void onClick()
//						{
//							if (operate.start(context, OperateBase.OperateType_Mode5))
//								ballView.setRunning(true);
//						}
//					});
//					builder.show();
				}
			}
		}
	}

}
