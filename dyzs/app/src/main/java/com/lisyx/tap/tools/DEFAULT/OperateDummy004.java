package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDummy004;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.view.Ball;

import java.util.List;

public class OperateDummy004 extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDummy004(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int runCount;
	private int currCount;
	private long runSpeed;
	private String runTarget;

	private int qunIndex = -1;

	@Override
	public void start()
	{
		runCount = ActivityDummy004.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDummy004.readRunSpeed(context);
		runTarget = ActivityDummy004.readRunTarget(context);
//		if (TextUtils.isEmpty(runTarget))
//		{
//			Toast.makeText(context, "请先设置运行目标", Toast.LENGTH_SHORT).show();
//			return;
//		}
		LOG.v(String.format("runCount = %s, runSpeed = %s, runTarget = %s", runCount, runSpeed, runTarget));
		if (TextUtils.isEmpty(ActivityDummy004.randomReadMsg(context)))
		{
			Toast.makeText(context, "请先设置话术", Toast.LENGTH_SHORT).show();
			return;
		}
		startOne();
	}

	@Override
	public void startOne()
	{
		qunIndex++;
		LOG.v("---------startOne " + qunIndex);
		clickLianXiRen();
	}

	private void clickLianXiRen()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("联系人", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("联系人") && //
								!TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/kbi");
					}
				}, null);
				if (nodeFind != null)
				{
					clickQunLiao();
				}
			}
		});
	}

	private void clickQunLiao()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("群聊", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("群聊");
					}
				}, null);
				if (nodeFind != null)
				{
					clickQun();
				}
			}
		});
	}

	private void clickQun()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				List<AccessibilityNodeInfo> nodes = service.findNodes(new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/text1");
					}
				});
				LOG.v("find qun count : " + nodes.size());
				if (nodes.size() > 0)
				{
					int index = qunIndex % nodes.size();
					AccessibilityNodeInfo node = nodes.get(index);
					service.clickNode(node, "qun");
					checkJingYan();
				}
				else
				{
					Ball.stopRunning();
					Toast.makeText(context, "群列表为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void checkJingYan()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("全员禁言中…", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("全员禁言中");
					}
				}, null);
				if (nodeFind != null)
				{
					LOG.v("全员禁言中");
					back(1);
				}
				else
				{
					checkMsg();
				}
			}
		});
	}

	private void checkMsg()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				final String msg = ActivityDummy004.randomReadMsg(context);
				List<AccessibilityNodeInfo> nodes = service.findNodes(new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals(msg);
					}
				});
				if (nodes.size() == 0)
				{
					clickInput(0, msg);
				}
				else
				{
					back(1);
				}
			}
		});
	}

	private void clickInput(final int targetIndex, final String msg)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText");
					}
				}, null);
				if (nodeFind != null)
				{
					inputText(targetIndex, msg);
				}
			}
		});
	}

	private void inputText(final int targetIndex, final String msg)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback()
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
						LOG.v("输入 " + msg);
						service.setNodeText(nodeInput, msg);
						clickSend(targetIndex);
					}
				});
			}
		});
	}

	private void clickSend(final int targetIndex)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("发送", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.Button") && //
								!TextUtils.isEmpty(text) && text.equals("发送");
					}
				}, null);
				if (nodeFind != null)
				{
					currCount++;
					long totalTime = System.currentTimeMillis() - startTime;
					Toast.makeText(context, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", //
							formatTime(totalTime), formatTime(totalTime / currCount), currCount, runCount > 0 ? runCount : "不限"), Toast.LENGTH_LONG).show();
				}
				back(2);
			}
		});
	}

	private void back(final int level)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("back " + level);
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				if (level > 1)
				{
					back(level - 1);
				}
				else
				{
					if (runCount > 0 && currCount >= runCount)
					{
						Ball.stopRunning();
						Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
					}
					else
					{
						ActivityDummy004.addRunRecord(context);
						startOne();
					}
				}
			}
		});
	}

}
