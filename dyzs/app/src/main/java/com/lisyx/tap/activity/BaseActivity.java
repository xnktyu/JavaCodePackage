package com.lisyx.tap.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.SysUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity
{
	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		context = this;
	}

	public void setStatusBarColor(int color, boolean fullscreen)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().setStatusBarColor(color);
			if (ColorUtils.calculateLuminance(color) >= 0.5)
			{
				if (fullscreen)
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				else
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
			else
			{
				if (fullscreen)
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
				else
					getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == INSTALL_PERMISS_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				SysUtils.openFile(context, toInstallApk);
			}
		}
	}

	//------------- 安装权限 ---------------

	private int INSTALL_PERMISS_CODE = 202;
	private File toInstallApk = null;

	public void installApk(File file)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			//先判断是否有安装未知来源应用的权限
			boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
			if (!haveInstallPermission)
			{
				toInstallApk = file;
				//弹框提示用户手动打开
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("安装权限");
				builder.setMessage("需要打开允许来自此来源，请去设置中开启此权限");
				builder.setNeutralButton("取消", null);
				builder.setPositiveButton("设置", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialogInterface, int which)
					{
						Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
						intent.setData(Uri.parse("package:" + getPackageName()));
						startActivityForResult(intent, INSTALL_PERMISS_CODE);
					}
				});
				builder.show();
			}
			else
			{
				SysUtils.openFile(context, file);
			}
		}
		else
		{
			SysUtils.openFile(context, file);
		}
	}

	//------------- 动态权限 ---------------

	private int mPermissionRequestCode = 101;

	// 过滤权限
	private String[] filterPermission(String... permissions)
	{
		List<String> needPermissions = new ArrayList<>();
		for (String permission : permissions)
		{
			if (!permission.equals(Manifest.permission.SYSTEM_ALERT_WINDOW) && //
					!permission.equals("android.permission.SYSTEM_OVERLAY_WINDOW") && //
					!permission.equals(Manifest.permission.REQUEST_INSTALL_PACKAGES) && //
					!permission.equals(Manifest.permission.READ_LOGS) && //
					!permission.equals(Manifest.permission.CHANGE_CONFIGURATION) && //
					!permission.equals(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) && //
					!permission.equals(Manifest.permission.BIND_ACCESSIBILITY_SERVICE) && //
					!permission.equals("android.permission.INTERACT_ACROSS_USERS_FULL") && //
					!permission.equals("com.mediatek.permission.CTA_ENABLE_WIFI") && //
					!permission.equals(Manifest.permission.WRITE_SETTINGS))
			{
				needPermissions.add(permission);
			}
		}
		return needPermissions.toArray(new String[needPermissions.size()]);
	}

	// 请求权限
	public void requestPermission()
	{
		try
		{
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
			requestPermission(filterPermission(packageInfo.requestedPermissions));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	// 请求权限
	public void requestPermission(String... permissions)
	{
		if (checkPermissions(permissions))
		{
			permissionSuccess();
		}
		else
		{
			List<String> needPermissions = getDeniedPermissions(permissions);
			if (needPermissions.size() > 0)
				ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), mPermissionRequestCode);
			else
				permissionFail();
		}
	}

	// 验证权限
	public boolean checkPermissions()
	{
		try
		{
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
			return checkPermissions(filterPermission(packageInfo.requestedPermissions));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	// 验证权限
	public boolean checkPermissions(String... permissions)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
			return true;
//		for (String permission : permissions)
//		{
//			int state = ContextCompat.checkSelfPermission(this, permission);
//			boolean show = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
//			LOG.v(String.format("%s : state = %d, show = %b", permission, state, show));
//		}
		for (String permission : permissions)
		{
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
			{
				return false;
			}
		}
		return true;
	}

	// 获取需要集中申请权限的列表
	private List<String> getDeniedPermissions(String... permissions)
	{
		List<String> needRequestPermissionList = new ArrayList<>();
		for (String permission : permissions)
		{
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
			{
				needRequestPermissionList.add(permission);
			}
		}
		return needRequestPermissionList;
	}

	// 系统请求权限回调
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == mPermissionRequestCode)
		{
			if (verifyPermissions(grantResults))
			{
				permissionSuccess();
			}
			else
			{
				permissionFail();
			}
		}
	}

	// 确认所有的权限是否都已授权
	private boolean verifyPermissions(int[] grantResults)
	{
		for (int grantResult : grantResults)
		{
			if (grantResult != PackageManager.PERMISSION_GRANTED)
			{
				return false;
			}
		}
		return true;
	}

	// 获取权限成功
	public void permissionSuccess()
	{
		LOG.v("获取权限成功=");
	}

	// 权限获取失败
	public void permissionFail()
	{
		LOG.v("获取权限失败=");
		showTipsDialog();
	}

	// 显示提示对话框
	private void showTipsDialog()
	{
		List<String> needPermissions = null;
		try
		{
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
			needPermissions = getDeniedPermissions(filterPermission(packageInfo.requestedPermissions));
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		if (needPermissions != null)
		{
			for (String permission : needPermissions)
			{
				sb.append("\n" + permission);
			}
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示信息");
		builder.setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【设置】按钮前往设置中心进行权限授权。" + sb.toString());
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.setData(Uri.parse("package:" + getPackageName()));
				startActivity(intent);
			}
		});
		builder.show();
	}
}
