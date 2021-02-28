package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityQQShiXing;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.view.Ball;

import java.util.List;

public class OperateQQShiXing extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateQQShiXing(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int allCount;
	private int currCount;
	private long runSpeed;

	private String qunCode;
//	private int qunIndex;

	@Override
	public void start()
	{
		allCount = ActivityQQShiXing.readShiXing(context);
		currCount = 0;
		runSpeed = ActivityQQShiXing.readRunSpeed(context) * 1000;
		qunCode = ActivityQQShiXing.readQunCode(context);
//		qunIndex = ActivityQQShiXing.readQunIndex(context);
		if (TextUtils.isEmpty(qunCode))
		{
			Toast.makeText(context, "请先设置群名字", Toast.LENGTH_SHORT).show();
			return;
		}
		LOG.v(String.format("allCount = %s, runSpeed = %s, qunCode = %s", allCount, runSpeed / 1000, qunCode));
		if (TextUtils.isEmpty(ActivityQQShiXing.randomReadMsg(context)))
		{
			Toast.makeText(context, "请先设置私信话术", Toast.LENGTH_SHORT).show();
			return;
		}
		startOne();
	}

	@Override
	public void startOne()
	{
		LOG.v("---------startOne");
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
					clickSearch();
//					clickQunLiao();
				}
			}
		});
	}

	private void clickSearch()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("搜索…", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("搜索");
					}
				}, null);
				if (nodeFind != null)
				{
					inputSearch();
				}
			}
		});
	}

	private void inputSearch()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				service.clickIfFindOne("搜索…", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.EditText") && //
								!TextUtils.isEmpty(text) && text.equals("搜索");
					}
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo nodeInput, String des)
					{
						LOG.v("输入 " + qunCode);
						service.setNodeText(nodeInput, qunCode);
						backSearch();
					}
				});
			}
		});
	}

	private void backSearch()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("backSearch");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				findSearch();
			}
		});
	}

	private void findSearch()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("findSearch", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals("(" + qunCode + ")");
					}
				}, null);
				if (nodeFind != null)
				{
					clickMenu();
				}
			}
		});
	}

//	private void clickQunLiao()
//	{
//		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
//		{
//			@Override
//			public void process()
//			{
//				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("群聊", new NodeHelper.OnFindCallback()
//				{
//					@Override
//					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
//					{
//						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
//								!TextUtils.isEmpty(text) && text.equals("群聊");
//					}
//				}, null);
//				if (nodeFind != null)
//				{
//					clickQun(qunName, qunIndex);
//				}
//			}
//		});
//	}
//
//	private void clickQun(final String qunName, final int qunIndex)
//	{
//		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
//		{
//			@Override
//			public void process()
//			{
//				AccessibilityNodeInfo nodeFind = service.clickByIndex(qunName, qunIndex, new NodeHelper.OnFindCallback()
//				{
//					@Override
//					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
//					{
//						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
//								!TextUtils.isEmpty(text) && text.equals(qunName);
//					}
//				}, null);
//				if (nodeFind != null)
//				{
//					clickMenu();
//				}
//			}
//		});
//	}

	private void clickMenu()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("群聊设置", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.ImageView") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("群聊设置");
					}
				}, null);
				if (nodeFind != null)
				{
					gotoChenYuanList();
				}
			}
		});
	}

	private void gotoChenYuanList()
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("共XX人", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.matches("共\\d+人");
					}
				}, null);
				if (nodeFind != null)
				{
					findChenYuanList(-1, ActivityQQShiXing.readMemberIndex(context), 0);
				}
			}
		});
	}

	private void findChenYuanList(final int currIndex, final int targetIndex, final int tryTimes)
	{
		if (tryTimes < 100)
		{
			LOG.v("findChenYuanList targetIndex : " + targetIndex + " tryTimes : " + tryTimes);
			service.wait(runSpeed - 500, new DyAccessibilityService.OnWaitCallback()
			{
				@Override
				public void process()
				{
					AccessibilityNodeInfo nodeFind = service.clickIfFindOne("AbsListView", new NodeHelper.OnFindCallback()
					{
						@Override
						public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
						{
							return !TextUtils.isEmpty(className) && className.equals("android.widget.AbsListView");
						}
					}, new DyAccessibilityService.OnClickFunction()
					{
						@Override
						public void click(AccessibilityNodeInfo node, String des)
						{
							findChenYuan(node, currIndex, targetIndex);
						}
					});
					if (nodeFind == null)
					{
						findChenYuanList(currIndex, targetIndex, tryTimes + 1);
					}
				}
			});
		}
	}

	private boolean checkMember(AccessibilityNodeInfo node)
	{
		List<AccessibilityNodeInfo> nodes = NodeHelper.findNodes(node, new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
						!TextUtils.isEmpty(text) && (text.equals("群主") || text.equals("管理员"));
			}
		});
		return nodes.size() == 0;
	}

	private void findChenYuan(final AccessibilityNodeInfo listNode, int currIndex, final int targetIndex)
	{
		boolean findIt = false;
		for (int i = 0; i < listNode.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = listNode.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				if (checkMember(child))
				{
					AccessibilityNodeInfo groupNode = NodeHelper.getChildNode(child, 0);
					if (groupNode != null)
					{
						String className = groupNode.getClassName() != null ? groupNode.getClassName().toString() : null;
						if (!TextUtils.isEmpty(className) && className.equals("android.widget.FrameLayout"))
						{
							currIndex++;
							if (targetIndex == currIndex)
							{
								findIt = true;
								// to click
								groupNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
								clickFaXiaoXi(targetIndex);
							}
						}
					}
				}
//				else
//				{
//					AccessibilityNodeInfo groupNode = NodeHelper.getChildNode(child, 0);
//					if (groupNode != null)
//					{
//						String className = groupNode.getClassName() != null ? groupNode.getClassName().toString() : null;
//						String text = groupNode.getText() != null ? groupNode.getText().toString() : null;
//						findStart = !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
//								!TextUtils.isEmpty(text) && text.matches("[A-Z#]\\(\\d+人\\)");
//					}
//				}
			}
		}
		if (!findIt)
		{
			listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
			findChenYuanList(currIndex, targetIndex, 0);
		}
	}

	private void clickFaXiaoXi(final int targetIndex)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				AccessibilityNodeInfo nodeFind = service.clickIfFindOne("发消息", new NodeHelper.OnFindCallback()
				{
					@Override
					public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
					{
						return !TextUtils.isEmpty(className) && className.equals("android.widget.Button") && //
								!TextUtils.isEmpty(text) && text.equals("发消息") && //
								!TextUtils.isEmpty(contentDescription) && contentDescription.equals("发消息");
					}
				}, null);
				if (nodeFind != null)
				{
					checkMsg(targetIndex);
				}
				else
				{
					back(4);
				}
			}
		});
	}

	private void checkMsg(final int targetIndex)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				final String msg = ActivityQQShiXing.randomReadMsg(context);
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
					clickInput(targetIndex, msg);
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
							formatTime(totalTime), formatTime(totalTime / currCount), currCount, allCount > 0 ? allCount : "不限"), Toast.LENGTH_LONG).show();
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
					if (allCount > 0 && currCount >= allCount)
					{
						Ball.stopRunning();
						Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
					}
					else
					{
						ActivityQQShiXing.addMemberIndex(context);
						startOne();
					}
				}
			}
		});
	}


}
