package com.lisyx.tap.tools.DEFAULT;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDyLive;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;

public class OperateDyLive extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDyLive(DyAccessibilityService service)
	{
		this.service = service;
	}

	private long runSpeed;

	@Override
	public void start()
	{
		runSpeed = ActivityDyLive.readRunSpeed(context);
		LOG.v(String.format("runSpeed = %s", runSpeed));
		if (!TextUtils.isEmpty(ActivityDyLive.randomReadMsg(context)))
			startOne();
		else
			Toast.makeText(context, "请先设置直播引流话术", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		clickInput();
	}

	private void clickInput()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("说点什么...", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("说点什么...");
					}
				}, null);
				if (nodeFind != null)
				{
					inputText();
				}
			}
		});
	}

	private void inputText()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("EditText", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText");
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo nodeInput, String des)
					{
						String msg = ActivityDyLive.randomReadMsg(context);
						LOG.v("输入 " + msg);
						service.setNodeText(nodeInput, msg);
						clickSend(nodeInput);
					}
				});
			}
		});
	}

	private void clickSend(final AccessibilityNodeInfo nodeInput)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne(nodeInput.getParent(), "发送...", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.Button") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("发送");
					}
				}, null);
				if (nodeFind != null)
				{
					willStartOne();
				}
			}
		});
	}

	private void willStartOne()
	{
		service.wait(ActivityDyLive.readRunSpeed(context), new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				startOne();
			}
		});
	}
}
