package com.lisyx.tap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lisyx.tap.R;
import com.lisyx.tap.activity.ActivityFullPlay;
import com.lisyx.tap.utils.LOG;

public class FragmentMainHot extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
//		private TextView time;
//		private TextView date;
//		private TextView week;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
//		holder.time = view.findViewById(R.id.time);
//		holder.date = view.findViewById(R.id.date);
//		holder.week = view.findViewById(R.id.week);
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
			rootView = inflater.inflate(R.layout.fragment_main_hot, container, false);
			initHolder(rootView);

			rootView.findViewById(R.id.teachOpenBall).setOnClickListener(this);
			rootView.findViewById(R.id.teachOpenService).setOnClickListener(this);

			LOG.v("onCreateView : " + this);
		}
		return rootView;
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
		if (view.getId() == R.id.teachOpenBall)
		{
			Intent intent = new Intent(context, ActivityFullPlay.class);
			intent.putExtra("url", "https://qyqy-file.oss-cn-hangzhou.aliyuncs.com/teach/openball.mp4");
			startActivity(intent);
		}
		else if (view.getId() == R.id.teachOpenService)
		{
			Intent intent = new Intent(context, ActivityFullPlay.class);
			intent.putExtra("url", "https://qyqy-file.oss-cn-hangzhou.aliyuncs.com/teach/openservice.mp4");
			startActivity(intent);
		}
	}


}
