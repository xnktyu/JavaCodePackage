package com.lisyx.tap.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lisyx.tap.R;
import com.lisyx.tap.utils.LOG;


public class ActivityFullPlay extends BaseActivity implements View.OnClickListener
{
	private class Holder
	{
		private ProgressBar loading;

		private ViewGroup controller;
		private ImageView control;
		private TextView pos;
		private SeekBar progress;
		private TextView duration;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.loading = findViewById(R.id.loading);

		holder.controller = findViewById(R.id.controller);
		holder.control = findViewById(R.id.control);
		holder.pos = findViewById(R.id.pos);
		holder.progress = findViewById(R.id.progress);
		holder.duration = findViewById(R.id.duration);
	}

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setStatusBarColor(0xff242424, false);
		setContentView(R.layout.activity_full_play);
		initHolder();
		start();
	}

	private void start()
	{
		url = getIntent().getStringExtra("url");

		findViewById(R.id.videoMask).setOnClickListener(this);

		initVideo();

		playVideo(url);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		tickHandler.removeCallbacks(tickRunnable);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.videoMask)
		{
			if (isPrepared)
			{
				if (holder.controller.getVisibility() == View.VISIBLE)
					holder.controller.setVisibility(View.GONE);
				else
					holder.controller.setVisibility(View.VISIBLE);
			}
		}
		else if (view.getId() == R.id.control)
		{
			if (videoView.isPlaying())
			{
				videoView.pause();
				holder.control.setImageResource(R.drawable.img_video_pause);
			}
			else
			{
				videoView.start();
				holder.control.setImageResource(R.drawable.img_video_play);
			}
		}
	}

	//------------------- 播放相关 --------------------------

	private VideoView videoView;
//	private MediaController mController;

	private boolean isPrepared = false;
	private int videoWidth = 0;
	private int videoHeight = 0;

	private boolean mainProcessInControl = false; // 主进度是否在操控中

	private void initVideo()
	{
		videoView = findViewById(R.id.videoView);

		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mp)
			{
				LOG.v("onPrepared " + mp.getVideoWidth() + "," + mp.getVideoHeight());
				isPrepared = true;
				videoWidth = mp.getVideoWidth();
				videoHeight = mp.getVideoHeight();
				holder.loading.setVisibility(View.GONE);
//				holder.controller.setVisibility(View.VISIBLE);
			}
		});

		videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				LOG.v("onCompletion");
				videoView.seekTo(0);
			}
		});

		holder.controller.setVisibility(View.GONE);

		findViewById(R.id.control).setOnClickListener(this);

		holder.progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
//				LOG.v("onProgressChanged : " + fromUser);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStartTrackingTouch");
				mainProcessInControl = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
//				LOG.v("onStopTrackingTouch");
				mainProcessInControl = false;
				videoView.seekTo(holder.progress.getProgress() * videoView.getDuration() / 1000);
			}
		});
	}

	public void playVideo(String url)
	{
		videoView.setVideoURI(Uri.parse(url));

//		mController = new MediaController(context);
//		videoView.setMediaController(mController);
//		mController.setMediaPlayer(videoView);

		videoView.start();

		tickHandler.post(tickRunnable);

		holder.loading.setVisibility(View.VISIBLE);
	}

	public void stopVideo()
	{
		tickHandler.removeCallbacks(tickRunnable);
		videoView.pause();
		videoView.stopPlayback();
	}

	private Handler tickHandler = new Handler();
	private Runnable tickRunnable = new Runnable()
	{
		@Override
		public void run()
		{
//			LOG.v("tick video");
			try
			{
				if (videoView.isPlaying())
					holder.control.setImageResource(R.drawable.img_video_play);
				else
					holder.control.setImageResource(R.drawable.img_video_pause);
				holder.pos.setText(formatTime(videoView.getCurrentPosition()));
				holder.duration.setText(formatTime(videoView.getDuration()));
				if (!mainProcessInControl)
					holder.progress.setProgress(videoView.getCurrentPosition() * 1000 / videoView.getDuration());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			tickHandler.postDelayed(this, 1000);
		}
	};

	private static String formatTime(long ms)
	{
		int second = (int) (ms / 1000);
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%02d:%02d", minute, second);
		else
			return String.format("%02d:%02d:%02d", hour, minute, second);
	}

}
