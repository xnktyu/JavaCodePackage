package com.lisyx.tap.fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lisyx.tap.R;
import com.lisyx.tap.activity.ActivityDevice;
import com.lisyx.tap.activity.ActivityMain;
import com.lisyx.tap.dialog.DialogAlert;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.Protocol;
import com.lisyx.tap.utils.SPHelper;
import com.lisyx.tap.utils.SysUtils;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TapActive;
import com.lys.protobuf.SRequest_TapWatchCode;
import com.lys.protobuf.SResponse_TapActive;
import com.lys.protobuf.SResponse_TapStartup;
import com.lys.protobuf.SResponse_TapWatchCode;
import com.lys.protobuf.STapActiveState;
import com.lys.protobuf.STapRole;

import java.util.List;

public class FragmentMainMine extends BaseFragment implements View.OnClickListener
{
	public boolean isServiceON(String className)
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(100);
		for (int i = 0; i < runningServices.size(); i++)
		{
			ComponentName service = runningServices.get(i).service;
			if (service.getClassName().contains(className))
			{
				return true;
			}
		}
		return false;
	}

	private class Holder
	{
		private TextView toActive;
		private TextView setAccessibilityPermission;

		private ViewGroup deviceCon;

		private EditText info2;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.toActive = view.findViewById(R.id.toActive);
		holder.setAccessibilityPermission = view.findViewById(R.id.setAccessibilityPermission);

		holder.deviceCon = view.findViewById(R.id.deviceCon);

		holder.info2 = view.findViewById(R.id.info2);
	}

	private ActivityMain getMainActivity()
	{
		return (ActivityMain) getActivity();
	}

	private View rootView = null;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getBaseActivity().setStatusBarColor(0xff242424, false);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		if (rootView == null)
		{
			rootView = inflater.inflate(R.layout.fragment_main_mine, container, false);
			initHolder(rootView);

			LOG.v("onCreateView : " + this);

			rootView.findViewById(R.id.deviceCon).setVisibility(View.GONE);
			rootView.findViewById(R.id.toActive).setVisibility(View.GONE);
			rootView.findViewById(R.id.findDevice).setVisibility(View.GONE);

			holder.info2.setText(String.format("%s", SysUtils.getOnlyId(context)));

			rootView.findViewById(R.id.copyCode2).setOnClickListener(this);
			rootView.findViewById(R.id.toActive).setOnClickListener(this);
			rootView.findViewById(R.id.setBallPermission).setOnClickListener(this);
			rootView.findViewById(R.id.setAccessibilityPermission).setOnClickListener(this);
			rootView.findViewById(R.id.findDevice).setOnClickListener(this);

			boolean dyIsOn = isServiceON(DyAccessibilityService.class.getName());
			LOG.v("dyIsOn : " + dyIsOn);

			if (!dyIsOn)
			{
				holder.setAccessibilityPermission.setText("开启无障碍服务");
				holder.setAccessibilityPermission.setTextColor(Color.BLACK);
				showAccessibilitySetting();
			}
			else
			{
				holder.setAccessibilityPermission.setText("无障碍服务已开启");
				holder.setAccessibilityPermission.setTextColor(0xff28a745);
			}

			if (getMainActivity().response != null)
				bindData(getMainActivity().response);
		}
		return rootView;
	}

	private void showAccessibilitySetting()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示信息");
		builder.setMessage("无障碍服务未开启，请单击【开启】按钮前往设置中心开启无障碍服务。");
		builder.setNeutralButton("取消", null);
		builder.setPositiveButton("开启", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int which)
			{
				Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		builder.show();
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		LOG.v("onDestroyView : " + this);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.copyCode2)
		{
			copyDeviceId();
		}
		else if (view.getId() == R.id.toActive)
		{
			DialogAlert.showInput(context, "输入激活码", null, new DialogAlert.OnInputListener()
			{
				@Override
				public void onInput(String text)
				{
					if (!TextUtils.isEmpty(text))
					{
						text = text.trim();
						if (!TextUtils.isEmpty(text))
						{
							gotoActive(text);
						}
					}
				}
			});
		}
		else if (view.getId() == R.id.setBallPermission)
		{
			Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setData(Uri.parse("package:" + context.getPackageName()));
			startActivity(intent);
		}
		else if (view.getId() == R.id.setAccessibilityPermission)
		{
			Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else if (view.getId() == R.id.findDevice)
		{
			DialogAlert.showInput(context, "输入对方ID(查看成功将消耗次数)", null, new DialogAlert.OnInputListener()
			{
				@Override
				public void onInput(String text)
				{
					if (!TextUtils.isEmpty(text))
					{
						text = text.trim();
						if (!TextUtils.isEmpty(text))
						{
							SRequest_TapWatchCode request = new SRequest_TapWatchCode();
							request.deviceId = text;
							request.watchActor = SysUtils.getOnlyId(context);
							Protocol.doPost(context, SHandleId.TapWatchCode, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code == 200)
									{
										SResponse_TapWatchCode response = SResponse_TapWatchCode.load(data);
										if (response.success)
										{
											Intent intent = new Intent(context, ActivityDevice.class);
											intent.putExtra("tap", response.tap.saveToStr());
											intent.putExtra("watch", response.watch.saveToStr());
											startActivity(intent);
										}
										Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show();
									}
								}
							});
						}
					}
				}
			});
		}
	}

	private void copyDeviceId()
	{
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("text", SysUtils.getOnlyId(context));
		clipboard.setPrimaryClip(clip);
		Toast.makeText(context, "已拷贝到剪贴板", Toast.LENGTH_SHORT).show();
	}

	private void gotoActive(String code)
	{
		SRequest_TapActive request = new SRequest_TapActive();
		request.deviceId = SysUtils.getOnlyId(context);
		request.activeCode = code;
		Protocol.doPost(context, SHandleId.TapActive, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_TapActive response = SResponse_TapActive.load(data);
					if (response.success)
					{
						SPHelper.putBoolean(context, ActivityMain.SP_Key_active, true);
						holder.deviceCon.setVisibility(View.GONE);
						holder.toActive.setVisibility(View.GONE);
					}
					Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void bindData(SResponse_TapStartup response)
	{
		if (response.tap.activeState == STapActiveState.Try)
		{
			if (response.tryPast)
			{
				holder.deviceCon.setVisibility(View.VISIBLE);
				holder.toActive.setVisibility(View.VISIBLE);
				holder.toActive.setText("试用已过期，请购买激活码");
				holder.toActive.setTextColor(0xfff1315d);
			}
			else
			{
				if (response.tryLeftTime < 24 * 3600 * 1000)
				{
					holder.deviceCon.setVisibility(View.VISIBLE);
					holder.toActive.setVisibility(View.VISIBLE);
					holder.toActive.setText("当前为试用版本，点击激活");
					holder.toActive.setTextColor(0xfff1315d);
				}
			}
		}

		if (response.tap.role == STapRole.Master || response.tap.role == STapRole.Root)
		{
			rootView.findViewById(R.id.findDevice).setVisibility(View.VISIBLE);
		}
	}

	public void tryPast()
	{
		holder.deviceCon.setVisibility(View.VISIBLE);
		holder.toActive.setVisibility(View.VISIBLE);
		holder.toActive.setText("试用已过期，请购买激活码");
		holder.toActive.setTextColor(0xfff1315d);
	}

	public void setConnected(boolean isConnected)
	{
		if (isConnected)
		{
			holder.setAccessibilityPermission.setText("无障碍服务已开启");
			holder.setAccessibilityPermission.setTextColor(0xff28a745);
		}
		else
		{
			holder.setAccessibilityPermission.setText("开启无障碍服务");
			holder.setAccessibilityPermission.setTextColor(Color.BLACK);
		}
	}

}
