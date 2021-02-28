package com.lisyx.tap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lisyx.tap.R;
import com.lisyx.tap.activity.ActivityDummy001;
import com.lisyx.tap.activity.ActivityDummy002;
import com.lisyx.tap.activity.ActivityDummy003;
import com.lisyx.tap.activity.ActivityDummy004;
import com.lisyx.tap.activity.ActivityDummy005;
import com.lisyx.tap.activity.ActivityDummy006;
import com.lisyx.tap.activity.ActivityDummy007;
import com.lisyx.tap.activity.ActivityDummy008;
import com.lisyx.tap.activity.ActivityDummy009;
import com.lisyx.tap.activity.ActivityDummy010;
import com.lisyx.tap.activity.ActivityDummy011;
import com.lisyx.tap.activity.ActivityDummy012;
import com.lisyx.tap.activity.ActivityDummy013;
import com.lisyx.tap.activity.ActivityDummy014;
import com.lisyx.tap.activity.ActivityDummy015;
import com.lisyx.tap.activity.ActivityDyBothGuan;
import com.lisyx.tap.activity.ActivityDyFenGuan;
import com.lisyx.tap.activity.ActivityDyLive;
import com.lisyx.tap.activity.ActivityDyPingXing;
import com.lisyx.tap.activity.ActivityDyQuGuan;
import com.lisyx.tap.activity.ActivityDyShiXing;
import com.lisyx.tap.activity.ActivityDyYangHao;
import com.lisyx.tap.activity.ActivityDyYingLiu;
import com.lisyx.tap.activity.ActivityInfos;
import com.lisyx.tap.activity.ActivityQQShiXing;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.SysUtils;
import com.lisyx.tap.view.Ball;

public class FragmentMainHome extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
//		private ImageView notWar;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
//		holder.notWar = view.findViewById(R.id.notWar);
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
			rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
			initHolder(rootView);

			LOG.v("onCreateView : " + this);

			rootView.findViewById(R.id.funcDyYingLiu).setVisibility(SysUtils.DyYingLiu_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyYangHao).setVisibility(SysUtils.DyYangHao_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyQuGuan).setVisibility(SysUtils.DyQuGuan_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyBothGuan).setVisibility(SysUtils.DyBothGuan_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyFenGuan).setVisibility(SysUtils.DyFenGuan_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyShiXing).setVisibility(SysUtils.DyShiXing_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyPingXing).setVisibility(SysUtils.DyPingXing_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDyLive).setVisibility(SysUtils.DyLive_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcQQShiXing).setVisibility(SysUtils.QQShiXing_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy001).setVisibility(SysUtils.Dummy001_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy002).setVisibility(SysUtils.Dummy002_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy003).setVisibility(SysUtils.Dummy003_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy004).setVisibility(SysUtils.Dummy004_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy005).setVisibility(SysUtils.Dummy005_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy006).setVisibility(SysUtils.Dummy006_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy007).setVisibility(SysUtils.Dummy007_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy008).setVisibility(SysUtils.Dummy008_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy009).setVisibility(SysUtils.Dummy009_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy010).setVisibility(SysUtils.Dummy010_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy011).setVisibility(SysUtils.Dummy011_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy012).setVisibility(SysUtils.Dummy012_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy013).setVisibility(SysUtils.Dummy013_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy014).setVisibility(SysUtils.Dummy014_enable ? View.VISIBLE : View.GONE);
			rootView.findViewById(R.id.funcDummy015).setVisibility(SysUtils.Dummy015_enable ? View.VISIBLE : View.GONE);

			rootView.findViewById(R.id.funcDyYingLiu).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyYangHao).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyQuGuan).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyBothGuan).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyFenGuan).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyShiXing).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyPingXing).setOnClickListener(this);
			rootView.findViewById(R.id.funcDyLive).setOnClickListener(this);
			rootView.findViewById(R.id.funcQQShiXing).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy001).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy002).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy003).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy004).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy005).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy006).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy007).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy008).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy009).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy010).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy011).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy012).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy013).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy014).setOnClickListener(this);
			rootView.findViewById(R.id.funcDummy015).setOnClickListener(this);

			rootView.findViewById(R.id.openBall).setOnClickListener(this);
			rootView.findViewById(R.id.deviceInfo).setOnClickListener(this);

			if (SysUtils.test_enable)
			{
				rootView.findViewById(R.id.debugCon).setVisibility(View.VISIBLE);
			}
			else
			{
				rootView.findViewById(R.id.debugCon).setVisibility(View.GONE);
			}
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
		if (view.getId() == R.id.funcDyYingLiu)
		{
			startActivity(new Intent(context, ActivityDyYingLiu.class));
		}
		else if (view.getId() == R.id.funcDyYangHao)
		{
			startActivity(new Intent(context, ActivityDyYangHao.class));
		}
		else if (view.getId() == R.id.funcDyQuGuan)
		{
			startActivity(new Intent(context, ActivityDyQuGuan.class));
		}
		else if (view.getId() == R.id.funcDyBothGuan)
		{
			startActivity(new Intent(context, ActivityDyBothGuan.class));
		}
		else if (view.getId() == R.id.funcDyFenGuan)
		{
			startActivity(new Intent(context, ActivityDyFenGuan.class));
		}
		else if (view.getId() == R.id.funcDyShiXing)
		{
			startActivity(new Intent(context, ActivityDyShiXing.class));
		}
		else if (view.getId() == R.id.funcDyPingXing)
		{
			startActivity(new Intent(context, ActivityDyPingXing.class));
		}
		else if (view.getId() == R.id.funcDyLive)
		{
			startActivity(new Intent(context, ActivityDyLive.class));
		}
		else if (view.getId() == R.id.funcQQShiXing)
		{
			startActivity(new Intent(context, ActivityQQShiXing.class));
		}
		else if (view.getId() == R.id.funcDummy001)
		{
			startActivity(new Intent(context, ActivityDummy001.class));
		}
		else if (view.getId() == R.id.funcDummy002)
		{
			startActivity(new Intent(context, ActivityDummy002.class));
		}
		else if (view.getId() == R.id.funcDummy003)
		{
			startActivity(new Intent(context, ActivityDummy003.class));
		}
		else if (view.getId() == R.id.funcDummy004)
		{
			startActivity(new Intent(context, ActivityDummy004.class));
		}
		else if (view.getId() == R.id.funcDummy005)
		{
			startActivity(new Intent(context, ActivityDummy005.class));
		}
		else if (view.getId() == R.id.funcDummy006)
		{
			startActivity(new Intent(context, ActivityDummy006.class));
		}
		else if (view.getId() == R.id.funcDummy007)
		{
			startActivity(new Intent(context, ActivityDummy007.class));
		}
		else if (view.getId() == R.id.funcDummy008)
		{
			startActivity(new Intent(context, ActivityDummy008.class));
		}
		else if (view.getId() == R.id.funcDummy009)
		{
			startActivity(new Intent(context, ActivityDummy009.class));
		}
		else if (view.getId() == R.id.funcDummy010)
		{
			startActivity(new Intent(context, ActivityDummy010.class));
		}
		else if (view.getId() == R.id.funcDummy011)
		{
			startActivity(new Intent(context, ActivityDummy011.class));
		}
		else if (view.getId() == R.id.funcDummy012)
		{
			startActivity(new Intent(context, ActivityDummy012.class));
		}
		else if (view.getId() == R.id.funcDummy013)
		{
			startActivity(new Intent(context, ActivityDummy013.class));
		}
		else if (view.getId() == R.id.funcDummy014)
		{
			startActivity(new Intent(context, ActivityDummy014.class));
		}
		else if (view.getId() == R.id.funcDummy015)
		{
			startActivity(new Intent(context, ActivityDummy015.class));
		}
		else if (view.getId() == R.id.openBall)
		{
			if (Ball.isShow())
				Ball.hideBall();
			else
				Ball.showBall(context);
		}
		else if (view.getId() == R.id.deviceInfo)
		{
			startActivity(new Intent(context, ActivityInfos.class));
		}
	}

}
