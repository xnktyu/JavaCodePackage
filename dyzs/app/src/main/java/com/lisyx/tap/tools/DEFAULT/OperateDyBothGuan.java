package com.lisyx.tap.tools.DEFAULT;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDyBothGuan;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.view.Ball;

public class OperateDyBothGuan extends OperateBase
{
	private DyAccessibilityService service;

	public OperateDyBothGuan(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int allCount;
	private int currCount;
	private long runSpeed;

	@Override
	public void start()
	{
		allCount = ActivityDyBothGuan.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDyBothGuan.readRunSpeed(context);
		LOG.v(String.format("allCount = %s, runSpeed = %s", allCount, runSpeed));
		startOne();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		findGuanZhu();
	}

	private void findGuanZhu()
	{
		service.wait(runSpeed , new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && (className.equals("android.support.v7.widget.RecyclerView") || className.equals("androidx.recyclerview.widget.RecyclerView")) && theNode.isScrollable();
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo recyclerNode, String des)
					{
						clickGuanZhu(recyclerNode, 0);
					}
				});
			}
		});
	}

	private void clickGuanZhu(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		if (index < recyclerNode.getChildCount())
		{
			service.wait(runSpeed , new DyAccessibilityService.OnWaitCallback()
			{
				@Override
				public void process()
				{
					AccessibilityNodeInfo nodeFind = service.clickIfFindOne(recyclerNode.getChild(index), "关注", new NodeHelper.OnFindCallback()
					{
						@Override
						public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
						{
							return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
									!TextUtils.isEmpty(text) && text.equals("关注");
						}
					}, null);
					if (nodeFind != null)
					{
						currCount++;
						if (currCount % 10 == 0)
						{
							long totalTime = System.currentTimeMillis() - startTime;
							Toast.makeText(context, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", //
									formatTime(totalTime), formatTime(totalTime / currCount), currCount, allCount > 0 ? allCount : "不限"), Toast.LENGTH_LONG).show();
						}
					}
					if (allCount > 0 && currCount >= allCount)
					{
						Ball.stopRunning();
						Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
					}
					else
					{
						clickGuanZhu(recyclerNode, index + 1);
					}
				}
			});
		}
		else
		{
			swipeNext(recyclerNode);
		}
	}

	private void swipeNext(final AccessibilityNodeInfo recyclerNode)
	{
		service.wait(runSpeed , new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("swipeNext");
				recyclerNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
				willStartOne();
			}
		});
	}

	private void willStartOne()
	{
		startOne();
	}
}
