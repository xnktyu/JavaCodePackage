package com.lisyx.tap.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.lisyx.tap.R;
import com.lisyx.tap.utils.SPHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityQQShiXing extends BaseActivity implements View.OnClickListener
{
	private static final String SP_Key_root = "QQShiXing";

	private class Holder
	{
		private EditText shixingCount;
		private EditText runSpeed;
		private EditText qunCode;
		//		private EditText qunIndex;
		private EditText memberIndex;

		private ArrayList<EditText> msgs = new ArrayList<>();
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.shixingCount = findViewById(R.id.shixingCount);
		holder.runSpeed = findViewById(R.id.runSpeed);
		holder.qunCode = findViewById(R.id.qunCode);
//		holder.qunIndex = findViewById(R.id.qunIndex);
		holder.memberIndex = findViewById(R.id.memberIndex);

		holder.msgs.add((EditText) findViewById(R.id.msg0));
		holder.msgs.add((EditText) findViewById(R.id.msg1));
		holder.msgs.add((EditText) findViewById(R.id.msg2));
		holder.msgs.add((EditText) findViewById(R.id.msg3));
		holder.msgs.add((EditText) findViewById(R.id.msg4));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qq_shi_xing);
		initHolder();
		initView();
	}

	private void initView()
	{
		holder.shixingCount.setText("" + readShiXing(context));
		holder.runSpeed.setText("" + readRunSpeed(context));
		holder.qunCode.setText("" + readQunCode(context));
//		holder.qunIndex.setText("" + readQunIndex(context));
		holder.memberIndex.setText("" + readMemberIndex(context));
		for (int i = 0; i < SP_Key_msgs.length; i++)
		{
			String SP_Key_msg = SP_Key_msgs[i];
			EditText msg = holder.msgs.get(i);
			msg.setText(readMsg(context, SP_Key_msg));
		}

		holder.shixingCount.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				try
				{
					SPHelper.putInt(context, SP_Key_shixing, Integer.valueOf(s.toString()));
				}
				catch (Exception e)
				{
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		holder.runSpeed.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				try
				{
					SPHelper.putInt(context, SP_Key_runSpeed, Integer.valueOf(s.toString()));
				}
				catch (Exception e)
				{
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		holder.qunCode.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				SPHelper.putString(context, SP_Key_qunCode, s.toString());
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

//		holder.qunIndex.addTextChangedListener(new TextWatcher()
//		{
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after)
//			{
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count)
//			{
//				try
//				{
//					SPHelper.putInt(context, SP_Key_qunIndex, Integer.valueOf(s.toString()));
//				}
//				catch (Exception e)
//				{
//				}
//			}
//
//			@Override
//			public void afterTextChanged(Editable s)
//			{
//			}
//		});

		holder.memberIndex.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				try
				{
					SPHelper.putInt(context, SP_Key_memberIndex, Integer.valueOf(s.toString()));
				}
				catch (Exception e)
				{
				}
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		for (int i = 0; i < SP_Key_msgs.length; i++)
		{
			final String SP_Key_msg = SP_Key_msgs[i];
			EditText msg = holder.msgs.get(i);
			msg.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					SPHelper.putString(context, SP_Key_msg, s.toString());
				}

				@Override
				public void afterTextChanged(Editable s)
				{
				}
			});
		}
	}

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.copyCode)
//		{
//		}
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_shixing = SP_Key_root + "shixing";

	public static Integer readShiXing(Context context)
	{
		return Math.max(0, SPHelper.getInt(context, SP_Key_shixing, 100));
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_runSpeed = SP_Key_root + "runSpeed";

	public static Integer readRunSpeed(Context context)
	{
		return Math.max(1, SPHelper.getInt(context, SP_Key_runSpeed, 1));
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_qunCode = SP_Key_root + "qunCode";

	public static String readQunCode(Context context)
	{
		return SPHelper.getString(context, SP_Key_qunCode, "").trim();
	}

	//----------------------------------------------------------------------------------------

//	private static final String SP_Key_qunIndex = SP_Key_root + "qunIndex";
//
//	public static Integer readQunIndex(Context context)
//	{
//		return Math.max(0, SPHelper.getInt(context, SP_Key_qunIndex, 0));
//	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_memberIndex = SP_Key_root + "memberIndex";

	public static Integer readMemberIndex(Context context)
	{
		return Math.max(0, SPHelper.getInt(context, SP_Key_memberIndex, 0));
	}

	public static void addMemberIndex(Context context)
	{
		SPHelper.putInt(context, SP_Key_memberIndex, readMemberIndex(context) + 1);
	}

	//----------------------------------------------------------------------------------------

	private static final String[] SP_Key_msgs = { //
			SP_Key_root + "msg0", //
			SP_Key_root + "msg1", //
			SP_Key_root + "msg2", //
			SP_Key_root + "msg3", //
			SP_Key_root + "msg4"};

	private static String readMsg(Context context, String SP_Key_msg)
	{
		String text = SPHelper.getString(context, SP_Key_msg, null);
		if (!TextUtils.isEmpty(text))
		{
			text = text.trim();
			if (!TextUtils.isEmpty(text))
			{
				return text;
			}
		}
		return "";
	}

	public static String randomReadMsg(Context context)
	{
		ArrayList<String> lineList = new ArrayList<>();
		for (String SP_Key_msg : SP_Key_msgs)
		{
			String text = readMsg(context, SP_Key_msg);
			if (!TextUtils.isEmpty(text))
			{
				lineList.add(text);
			}
		}
		if (lineList.size() > 0)
		{
			Collections.shuffle(lineList);
			return lineList.get(0);
		}
		else
		{
			return "";
		}
	}

}
