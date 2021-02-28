package com.lisyx.tap.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.lisyx.tap.R;
import com.lisyx.tap.utils.SPHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityDummy014 extends BaseActivity implements View.OnClickListener
{
	private static final String SP_Key_root = "Dummy";

	private class Holder
	{
		private EditText runCount;
		private EditText runSpeed;
		private EditText runTarget;
		private EditText runRecord;

		private ArrayList<EditText> msgTexts = new ArrayList<>();
		private ArrayList<CheckBox> msgEnables = new ArrayList<>();
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.runCount = findViewById(R.id.runCount);
		holder.runSpeed = findViewById(R.id.runSpeed);
		holder.runTarget = findViewById(R.id.runTarget);
		holder.runRecord = findViewById(R.id.runRecord);

		holder.msgTexts.add((EditText) findViewById(R.id.msgText0));
		holder.msgTexts.add((EditText) findViewById(R.id.msgText1));
		holder.msgTexts.add((EditText) findViewById(R.id.msgText2));
		holder.msgTexts.add((EditText) findViewById(R.id.msgText3));
		holder.msgTexts.add((EditText) findViewById(R.id.msgText4));

		holder.msgEnables.add((CheckBox) findViewById(R.id.msgEnable0));
		holder.msgEnables.add((CheckBox) findViewById(R.id.msgEnable1));
		holder.msgEnables.add((CheckBox) findViewById(R.id.msgEnable2));
		holder.msgEnables.add((CheckBox) findViewById(R.id.msgEnable3));
		holder.msgEnables.add((CheckBox) findViewById(R.id.msgEnable4));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dummy_014);
		initHolder();
		initView();
	}

	private void initView()
	{
		holder.runCount.setText("" + readRunCount(context));
		holder.runSpeed.setText("" + readRunSpeed(context));
		holder.runTarget.setText("" + readRunTarget(context));
		holder.runRecord.setText("" + readRunRecord(context));
		for (int i = 0; i < SP_Key_msgTexts.length; i++)
		{
			String spKey = SP_Key_msgTexts[i];
			EditText msgText = holder.msgTexts.get(i);
			msgText.setText(readMsgText(context, spKey));
		}
		for (int i = 0; i < SP_Key_msgEnables.length; i++)
		{
			String spKey = SP_Key_msgEnables[i];
			CheckBox msgEnable = holder.msgEnables.get(i);
			msgEnable.setChecked(readMsgEnable(context, spKey));
		}

		holder.runCount.addTextChangedListener(new TextWatcher()
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
					SPHelper.putInt(context, SP_Key_runCount, Integer.valueOf(s.toString()));
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

		holder.runTarget.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				SPHelper.putString(context, SP_Key_runTarget, s.toString());
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		holder.runRecord.addTextChangedListener(new TextWatcher()
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
					SPHelper.putInt(context, SP_Key_runRecord, Integer.valueOf(s.toString()));
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

		for (int i = 0; i < SP_Key_msgTexts.length; i++)
		{
			final String spKey = SP_Key_msgTexts[i];
			EditText msgText = holder.msgTexts.get(i);
			msgText.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					SPHelper.putString(context, spKey, s.toString());
				}

				@Override
				public void afterTextChanged(Editable s)
				{
				}
			});
		}

		for (int i = 0; i < SP_Key_msgEnables.length; i++)
		{
			final String spKey = SP_Key_msgEnables[i];
			CheckBox msgEnable = holder.msgEnables.get(i);
			msgEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					SPHelper.putBoolean(context, spKey, isChecked);
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

	private static final String SP_Key_runCount = SP_Key_root + "runCount";

	public static Integer readRunCount(Context context)
	{
		return Math.max(0, SPHelper.getInt(context, SP_Key_runCount, 0));
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_runSpeed = SP_Key_root + "runSpeed";

	public static Integer readRunSpeed(Context context)
	{
		return Math.max(100, SPHelper.getInt(context, SP_Key_runSpeed, 1000));
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_runTarget = SP_Key_root + "runTarget";

	public static String readRunTarget(Context context)
	{
		return SPHelper.getString(context, SP_Key_runTarget, "").trim();
	}

	//----------------------------------------------------------------------------------------

	private static final String SP_Key_runRecord = SP_Key_root + "runRecord";

	public static Integer readRunRecord(Context context)
	{
		return Math.max(0, SPHelper.getInt(context, SP_Key_runRecord, 0));
	}

	public static void addRunRecord(Context context)
	{
		SPHelper.putInt(context, SP_Key_runRecord, readRunRecord(context) + 1);
	}

	//----------------------------------------------------------------------------------------

	private static final String[] SP_Key_msgTexts = { //
			SP_Key_root + "msgText0", //
			SP_Key_root + "msgText1", //
			SP_Key_root + "msgText2", //
			SP_Key_root + "msgText3", //
			SP_Key_root + "msgText4"};

	private static final String[] SP_Key_msgEnables = { //
			SP_Key_root + "msgEnable0", //
			SP_Key_root + "msgEnable1", //
			SP_Key_root + "msgEnable2", //
			SP_Key_root + "msgEnable3", //
			SP_Key_root + "msgEnable4"};

	private static String readMsgText(Context context, String spKey)
	{
		String text = SPHelper.getString(context, spKey, null);
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

	private static boolean readMsgEnable(Context context, String spKey)
	{
		return SPHelper.getBoolean(context, spKey, true);
	}

	public static String randomReadMsg(Context context)
	{
		ArrayList<String> lineList = new ArrayList<>();
		for (int i = 0; i < SP_Key_msgTexts.length; i++)
		{
			String spKeyText = SP_Key_msgTexts[i];
			String spKeyEnable = SP_Key_msgEnables[i];
			String text = readMsgText(context, spKeyText);
			boolean enable = readMsgEnable(context, spKeyEnable);
			if (enable && !TextUtils.isEmpty(text))
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
