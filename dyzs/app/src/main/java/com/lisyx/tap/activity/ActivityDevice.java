package com.lisyx.tap.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lisyx.tap.R;
import com.lys.protobuf.STapDevice;

public class ActivityDevice extends BaseActivity implements View.OnClickListener
{
	private class Holder
	{
		private TextView info;
		private EditText activeCode;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.info = findViewById(R.id.info);
		holder.activeCode = findViewById(R.id.activeCode);
	}

	public STapDevice tap = null;
	public STapDevice watch = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		initHolder();
		tap = STapDevice.load(getIntent().getStringExtra("tap"));
		watch = STapDevice.load(getIntent().getStringExtra("watch"));
		bindView();
	}

	private void bindView()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("设备号：" + tap.deviceId + "\n");
		sb.append("激活码：" + tap.activeCode + "\n");
		sb.append("\n");
		sb.append("剩余查看次数：" + watch.watchCount + "\n");
		holder.info.setText(sb.toString());

		holder.activeCode.setText(tap.activeCode);

		findViewById(R.id.copyCode).setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.copyCode)
		{
			ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("text", tap.activeCode);
			clipboard.setPrimaryClip(clip);
			Toast.makeText(context, "已拷贝到剪贴板", Toast.LENGTH_SHORT).show();
		}
	}

}
