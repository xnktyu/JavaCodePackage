package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDyPingXing;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.view.Ball;

public class OperateDyPingXing extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDyPingXing(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int allCount;
	private int currCount;
	private long runSpeed;

	@Override
	public void start()
	{
		allCount = ActivityDyPingXing.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDyPingXing.readRunSpeed(context);
		LOG.v(String.format("allCount = %s, runSpeed = %s", allCount, runSpeed));
		if (!TextUtils.isEmpty(ActivityDyPingXing.randomReadMsg(context)))
			startOne();
		else
			Toast.makeText(context, "请先设置私信话术", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		findGuanZhu();
	}

	private void findGuanZhu()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
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
			service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
			{
				@Override
				public void process()
				{
					AccessibilityNodeInfo nodeFind = service.clickIfFindOne(recyclerNode.getChild(index), "HEAD", new NodeHelper.OnFindCallback()
					{
						@Override
						public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
						{
							return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
									!TextUtils.isEmpty(viewId) && viewId.equals("com.ss.android.ugc.aweme:id/jt");
						}
					}, new DyAccessibilityService.OnClickFunction()
					{
						@Override
						public void click(AccessibilityNodeInfo node, String des)
						{
							Rect rect = new Rect();
							node.getBoundsInScreen(rect);
							if (service.click(rect.centerX(), rect.centerY(), null))
							{
								Toast.makeText(context, String.format("click %s / %s", index, recyclerNode.getChildCount()), Toast.LENGTH_SHORT).show();
								clickMore(recyclerNode, index);
							}
						}
					});
					if (nodeFind == null)
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

	private void clickMore(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("更多", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("更多");
					}
				}, null);
				if (nodeFind != null)
				{
					clickShiXing(recyclerNode, index);
				}
				else
				{
					back3(recyclerNode, index);
				}
			}
		});
	}

	private void clickShiXing(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("发私信", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("发私信");
					}
				}, null);
				if (nodeFind != null)
				{
					checkMsg(recyclerNode, index);
				}
			}
		});
	}

	private void checkMsg(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				final String msg = ActivityDyPingXing.randomReadMsg(context);
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("检查消息", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals(msg);
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo node, String des)
					{
					}
				});
				if (nodeFind == null)
				{
					clickInput(recyclerNode, index, msg);
				}
				else
				{
					back2(recyclerNode, index);
				}
			}
		});
	}

	private void clickInput(final AccessibilityNodeInfo recyclerNode, final int index, final String msg)
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
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
								!TextUtils.isEmpty(text) && text.equals("发送消息…");
					}
				}, null);
				if (nodeFind != null)
				{
					inputText(recyclerNode, index, msg);
				}
			}
		});
	}

	private void inputText(final AccessibilityNodeInfo recyclerNode, final int index, final String msg)
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
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
								!TextUtils.isEmpty(text) && text.equals("发送消息…");
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo nodeInput, String des)
					{
						LOG.v("输入 " + msg);
						service.setNodeText(nodeInput, msg);
						clickSend(recyclerNode, index);
					}
				});
			}
		});
	}

	private void clickSend(final AccessibilityNodeInfo recyclerNode, final int index)
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
						return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("发送");
					}
				}, null);
				if (nodeFind != null)
				{
					currCount++;
					long totalTime = System.currentTimeMillis() - startTime;
					Toast.makeText(context, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", //
							formatTime(totalTime), formatTime(totalTime / currCount), currCount, allCount > 0 ? allCount : "不限"), Toast.LENGTH_LONG).show();
				}
				back1(recyclerNode, index);
			}
		});
	}

	private void back1(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("back 1");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				back2(recyclerNode, index);
			}
		});
	}

	private void back2(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("back 2");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				back3(recyclerNode, index);
			}
		});
	}

	private void back3(final AccessibilityNodeInfo recyclerNode, final int index)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("back 3");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
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

	private void swipeNext(final AccessibilityNodeInfo recyclerNode)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
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
