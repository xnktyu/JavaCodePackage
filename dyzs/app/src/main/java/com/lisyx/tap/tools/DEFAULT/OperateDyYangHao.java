package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lisyx.tap.activity.ActivityDyYangHao;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;

import java.util.List;

public class OperateDyYangHao extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDyYangHao(DyAccessibilityService service)
	{
		this.service = service;
	}

	private long runSpeed;

	@Override
	public void start()
	{
		runSpeed = ActivityDyYangHao.readRunSpeed(context);
		LOG.v(String.format("runSpeed = %s", runSpeed));
		startOne();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
		whetherClickHeart();
	}

	private void clickGuanZhu()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("关注", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.Button") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("关注");
					}
				}, null);
				whetherClickHeart();
			}
		});
	}

	private void whetherClickHeart()
	{
		if (Math.random() * 100 < ActivityDyYangHao.readRunCount(context))
			clickHeart();
		else
			whetherClickPingLun();
	}

	private void clickHeart()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("喜欢", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.startsWith("未选中，喜欢");
					}
				}, null);
				whetherClickPingLun();
			}
		});
	}

	private void whetherClickPingLun()
	{
		if (Math.random() < 0.5 && !TextUtils.isEmpty(ActivityDyYangHao.randomReadMsg(context)))
			clickPingLun();
		else
			swipeNext();
	}

	private void clickPingLun()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("评论", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.startsWith("评论");
					}
				}, null);
				clickInput();
			}
		});
	}

	private void clickInput()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
								!TextUtils.isEmpty(text) && text.equals("留下你的精彩评论吧");
					}
				}, null);
				inputText();
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
				final String atTarget = ActivityDyYangHao.readRunTarget(context);
				if (TextUtils.isEmpty(atTarget))
				{
					service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback()
					{
						@Override
						public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
						{
							return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
									!TextUtils.isEmpty(text) && text.equals("留下你的精彩评论吧");
						}
					}, new DyAccessibilityService.OnClickFunction()
					{
						@Override
						public void click(AccessibilityNodeInfo nodeInput, String des)
						{
							String msg = ActivityDyYangHao.randomReadMsg(context);
							LOG.v("输入 " + msg);
							service.setNodeText(nodeInput, msg);
							clickSend(nodeInput);
						}
					});
				}
				else
				{
					service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback()
					{
						@Override
						public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
						{
							return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
									!TextUtils.isEmpty(text) && text.equals("留下你的精彩评论吧");
						}
					}, new DyAccessibilityService.OnClickFunction()
					{
						@Override
						public void click(final AccessibilityNodeInfo nodeInput, String des)
						{
							String msg = atTarget;
							if (!msg.startsWith("@"))
								msg = "@" + msg;
							LOG.v("输入 " + msg);
							service.inputNode(nodeInput, msg);
							clickTarget(nodeInput);
						}
					});
				}
			}
		});
	}

	private void clickTarget(final AccessibilityNodeInfo nodeInput)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("TargetList", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && (className.equals("android.support.v7.widget.RecyclerView") || className.equals("androidx.recyclerview.widget.RecyclerView")) && theNode.isScrollable();
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo node, String des)
					{
						if (node.getChildCount() > 0)
						{
							LOG.v("click first target");
							node.getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);

							service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
							{
								@Override
								public void process()
								{
									String msg = ActivityDyYangHao.randomReadMsg(context);
									LOG.v("输入 " + msg);
									service.inputNode(nodeInput, msg);
									clickSend(nodeInput);
								}
							});
						}
					}
				});
				if (nodeFind == null)
				{
					service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
					{
						@Override
						public void process()
						{
							String msg = ActivityDyYangHao.randomReadMsg(context);
							LOG.v("输入 " + msg);
							service.setNodeText(nodeInput, msg);
							clickSend(nodeInput);
						}
					});
				}
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
				List<AccessibilityNodeInfo> nodes = NodeHelper.findNodes(nodeInput.getParent(), new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return indent == 1 && !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView");
					}
				});
				if (nodes.size() == 3)
				{
					AccessibilityNodeInfo nodeSend = nodes.get(nodes.size() - 1);
					LOG.v("click send");
					nodeSend.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					clickClose();
				}
			}
		});
	}

	private void clickClose()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("back");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				swipeNext();
			}
		});
	}

	private void swipeNext()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("NEXT", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && (className.equals("android.support.v4.view.ViewPager") || className.equals("androidx.viewpager.widget.ViewPager")) && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("视频");
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo node, String des)
					{
						LOG.v("swipeNext");
						node.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
					}
				});
				willStartOne();
			}
		});
	}

	private void willStartOne()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				startOne();
			}
		});
	}
}
