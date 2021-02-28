package com.lisyx.tap.tools.DEFAULT;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDummy003;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.CommonUtils;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.LOGJson;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.utils.Protocol;
import com.lisyx.tap.view.Ball;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SQQQun;
import com.lys.protobuf.SRequest_QQQunAddModify;
import com.lys.protobuf.SResponse_QQQunAddModify;

public class OperateDummy003 extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDummy003(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int runCount;
	private int currCount;
	private long runSpeed;
	private String runTarget;

	@Override
	public void start()
	{
		runCount = ActivityDummy003.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDummy003.readRunSpeed(context);
		runTarget = ActivityDummy003.readRunTarget(context);
//		if (TextUtils.isEmpty(runTarget))
//		{
//			Toast.makeText(context, "请先设置运行目标", Toast.LENGTH_SHORT).show();
//			return;
//		}
		LOG.v(String.format("runCount = %s, runSpeed = %s, runTarget = %s", runCount, runSpeed, runTarget));
//		if (TextUtils.isEmpty(ActivityDummy003.randomReadMsg(context)))
//		{
//			Toast.makeText(context, "请先设置话术", Toast.LENGTH_SHORT).show();
//			return;
//		}
		startOne();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		startCatch();
	}

	private void startCatch()
	{
		SQQQun qun = new SQQQun();
		catchQunInfo(qun);
		LOGJson.log(qun.saveToStr());
		if (!TextUtils.isEmpty(qun.code))
		{
			SRequest_QQQunAddModify request = new SRequest_QQQunAddModify();
			request.qun = qun;
			Protocol.doPost(context, SHandleId.QQQunAddModify, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						final SResponse_QQQunAddModify response = SResponse_QQQunAddModify.load(data);
						Ball.stopRunning();
						Toast.makeText(context, "抓取成功", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		else
		{
			Ball.stopRunning();
			Toast.makeText(context, "抓取失败", Toast.LENGTH_SHORT).show();
		}
	}

	private void catchQunInfo(final SQQQun qun)
	{
		qun.onlineCount = -1;

		service.clickIfFindOne("quncode", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/k1s");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.code = str;
				}
				catch (Exception e)
				{
				}
			}
		});

		service.clickIfFindOne("qunname", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/k0i");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.name = str;
				}
				catch (Exception e)
				{
				}
			}
		});

		service.clickIfFindOne("qunrencount", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/info");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.renCount = Integer.valueOf(str.substring(0, str.length() - 1));
				}
				catch (Exception e)
				{
				}
			}
		});

		service.clickIfFindOne("qunlevel", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/jzl");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.level = str;
				}
				catch (Exception e)
				{
				}
			}
		});

		service.clickIfFindOne("qundes", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/bzm");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.des = CommonUtils.filterEmoji(str, "");
				}
				catch (Exception e)
				{
				}
			}
		});
	}
}
