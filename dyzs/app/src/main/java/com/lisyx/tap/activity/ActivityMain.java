package com.lisyx.tap.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.lisyx.tap.R;
import com.lisyx.tap.fragment.FragmentMainHome;
import com.lisyx.tap.fragment.FragmentMainHot;
import com.lisyx.tap.fragment.FragmentMainMine;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.utils.CommonUtils;
import com.lisyx.tap.utils.FsUtils;
import com.lisyx.tap.utils.HttpUtils;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.Protocol;
import com.lisyx.tap.utils.SPHelper;
import com.lisyx.tap.utils.SysUtils;
import com.lisyx.tap.view.Ball;
import com.lys.protobuf.SApp;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TapStartup;
import com.lys.protobuf.SResponse_TapStartup;
import com.lys.protobuf.STapActiveState;
import com.lys.protobuf.STapDevice;

import java.io.File;
import java.util.Random;

public class ActivityMain extends BaseActivity implements View.OnClickListener
{
	public static final String SP_Key_active = "active";
	public static final String SP_Key_tryPast = "tryPast";

	public static String Action_Connected(Context context)
	{
		return context.getPackageName() + "." + ActivityMain.class.getName() + ".connected";
	}

	public static String Action_Unbind(Context context)
	{
		return context.getPackageName() + "." + ActivityMain.class.getName() + ".unbind";
	}

	private String getChannel()
	{
		String channel = "";
		try
		{
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			channel = appInfo.metaData.getString("UMENG_CHANNEL");
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return channel;
	}

	private class Holder
	{
		private ImageView navHome;
		private ImageView navHot;
		private ImageView navMine;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.navHome = findViewById(R.id.navHome);
		holder.navHot = findViewById(R.id.navHot);
		holder.navMine = findViewById(R.id.navMine);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LOG.v("--------------------------- onCreate : " + getClass().getSimpleName());
		setStatusBarColor(0xff231715, false);
		setContentView(R.layout.activity_main);
		initHolder();
		findViewById(R.id.logoPage).setVisibility(View.VISIBLE);
		requestPermission();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		LOG.v("--------------------------- onNewIntent : " + getClass().getSimpleName());
		if (intent.getBooleanExtra("past", false))
		{
			if (fragmentMainMine != null)
				fragmentMainMine.tryPast();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		LOG.v("--------------------------- onDestroy : " + getClass().getSimpleName());
		try
		{
			unregisterReceiver(mReceiver);
		}
		catch (Exception e)
		{
		}
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();
		gogogo();
	}

	@Override
	public void permissionFail()
	{
		super.permissionFail();
	}

	private void gogogo()
	{
		findViewById(R.id.logoPage).setVisibility(View.GONE);

		findViewById(R.id.navHome).setOnClickListener(this);
		findViewById(R.id.navHot).setOnClickListener(this);
		findViewById(R.id.navMine).setOnClickListener(this);

		SPHelper.putBoolean(context, SP_Key_tryPast, true);
		startup();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ActivityMain.Action_Connected(this));
		filter.addAction(ActivityMain.Action_Unbind(this));
		registerReceiver(mReceiver, filter);

		setupMainHome();

		Ball.showBall(context);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.navHome)
		{
			setupMainHome();
		}
		else if (view.getId() == R.id.navHot)
		{
			setupMainHot();
		}
		else if (view.getId() == R.id.navMine)
		{
			setupMainMine();
		}
	}

	private FragmentMainHome fragmentMainHome = null;

	private void setupMainHome()
	{
		Fragment fragmentExist = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
		if (fragmentExist instanceof FragmentMainHome)
		{
			LOG.v("fragmentExist : " + fragmentExist);
		}
		else
		{
			if (fragmentMainHome == null)
				fragmentMainHome = new FragmentMainHome();
			setup(fragmentMainHome);
		}
	}

	private FragmentMainHot fragmentMainHot = null;

	private void setupMainHot()
	{
		Fragment fragmentExist = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
		if (fragmentExist instanceof FragmentMainHot)
		{
			LOG.v("fragmentExist : " + fragmentExist);
		}
		else
		{
			if (fragmentMainHot == null)
				fragmentMainHot = new FragmentMainHot();
			setup(fragmentMainHot);
		}
	}

	private FragmentMainMine fragmentMainMine = null;

	private void setupMainMine()
	{
		Fragment fragmentExist = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
		if (fragmentExist instanceof FragmentMainMine)
		{
			LOG.v("fragmentExist : " + fragmentExist);
		}
		else
		{
			if (fragmentMainMine == null)
				fragmentMainMine = new FragmentMainMine();
			setup(fragmentMainMine);
		}
	}

	private void setup(Fragment fragment)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.mainContiner, fragment);
		ft.commitAllowingStateLoss();

		holder.navHome.setImageResource(fragment instanceof FragmentMainHome ? R.drawable.img_nav_home_light : R.drawable.img_nav_home_gray);
		holder.navHot.setImageResource(fragment instanceof FragmentMainHot ? R.drawable.img_nav_hot_light : R.drawable.img_nav_hot_gray);
		holder.navMine.setImageResource(fragment instanceof FragmentMainMine ? R.drawable.img_nav_mine_light : R.drawable.img_nav_mine_gray);
	}

	private long lastPressBackTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (System.currentTimeMillis() - lastPressBackTime > 800)
			{
				lastPressBackTime = System.currentTimeMillis();
				LOG.toast(context, "再按一次退出");
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	//------------------------------------

	public SResponse_TapStartup response = null;

	private void startup()
	{
		SRequest_TapStartup request = new SRequest_TapStartup();
		request.tap = new STapDevice();
		request.tap.deviceId = SysUtils.getOnlyId(context);
		request.tap.pkgName = getPackageName();
		request.tap.channel = getChannel();
		request.tap.versionCode = SysUtils.getVersionCode(context);
		request.tap.versionName = SysUtils.getVersionName(context);
		request.tap.deviceInfo = SysUtils.getDeviceInfo(context).toString();
		Protocol.doPost(context, SHandleId.TapStartup, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					response = SResponse_TapStartup.load(data);

//					DyAccessibilityService.lastCheckTime = System.currentTimeMillis();

					boolean localActive = SPHelper.getBoolean(context, SP_Key_active, false);
					boolean remoteActive = (response.tap.activeState == STapActiveState.Vip);
					if (localActive != remoteActive)
					{
						SPHelper.putBoolean(context, SP_Key_active, remoteActive);
					}

					if (response.tap.activeState == STapActiveState.Try)
					{
						SPHelper.putBoolean(context, SP_Key_tryPast, response.tryPast);
					}

					if (fragmentMainMine != null)
						fragmentMainMine.bindData(response);

					if (response.app != null)
					{
						if (response.app.versionCode != SysUtils.getVersionCode(context))
						{
							toUpdate(response.app, true);
						}
						else if (!response.app.versionName.equals(SysUtils.getVersionName(context)))
						{
							toUpdate(response.app, false);
						}
					}
				}
			}
		});
	}

	private void toUpdate(SApp app, boolean force)
	{
		if (new Random().nextFloat() < app.probability)
		{
			showUpdate(app, force);
		}
	}

	private static AlertDialog mAlertDialog = null;

	private void showUpdate(final SApp app, boolean force)
	{
		String localPath = String.format("%s/%s_%s.apk", FsUtils.SD_CARD, app.pkgName, app.channel);
		final File file = new File(localPath);
		if (file.exists())
		{
			PackageInfo packageInfo = SysUtils.getApkPackageInfo(context, localPath);
			if (packageInfo != null && packageInfo.versionName.equals(app.versionName) && packageInfo.versionCode == app.versionCode)
			{
				if ((mAlertDialog == null || !mAlertDialog.isShowing()) && !isDownloading(file))
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("新版本已下载完成，是否安装？");
					if (force)
					{
						builder.setCancelable(false);
					}
					else
					{
						builder.setNeutralButton("暂不安装", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialogInterface, int which)
							{
							}
						});
					}
					builder.setPositiveButton("安装", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialogInterface, int which)
						{
							installApk(file);
						}
					});
					mAlertDialog = builder.show();
				}
				return;
			}
		}
		if ((mAlertDialog == null || !mAlertDialog.isShowing()) && !isDownloading(file))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(String.format("发现新版本（%s），是否更新？（%s）", app.versionName, CommonUtils.formatSize(app.size)));
			if (force)
			{
				builder.setCancelable(false);
			}
			else
			{
				builder.setNeutralButton("暂不更新", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialogInterface, int which)
					{
					}
				});
			}
			builder.setPositiveButton("更新", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialogInterface, int which)
				{
					doUpdate(app, file);
				}
			});
			mAlertDialog = builder.show();
		}
	}

	private boolean isDownloading(File file)
	{
		return HttpUtils.getRunningDownloadInfo(file) != null || HttpUtils.getWaitDownloadInfo(file) != null;
	}

	private void doUpdate(final SApp app, final File file)
	{
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMax((int) (long) app.size);
		progressDialog.setTitle("准备下载。。。");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		HttpUtils.download(context, app.apkUrl, file, new HttpUtils.OnDownloadListener()
		{
			@Override
			public void onWait()
			{
				progressDialog.setTitle("等待中。。。");
			}

			@Override
			public void onFail()
			{
				LOG.toast(context, "下载失败");
				progressDialog.dismiss();
			}

			@Override
			public void onProgress(int alreadyDownloadSize)
			{
				progressDialog.setTitle("下载中。。。");
				progressDialog.setProgress(alreadyDownloadSize);
			}

			@Override
			public void onSuccess()
			{
				progressDialog.dismiss();
				installApk(file);
			}
		});
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(ActivityMain.Action_Connected(context)))
			{
				if (fragmentMainMine != null)
					fragmentMainMine.setConnected(true);
			}
			else if (intent.getAction().equals(ActivityMain.Action_Unbind(context)))
			{
				if (fragmentMainMine != null)
					fragmentMainMine.setConnected(false);
			}
		}
	};

}
