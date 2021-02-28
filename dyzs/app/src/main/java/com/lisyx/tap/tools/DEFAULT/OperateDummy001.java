package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDummy001;
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
import com.lys.protobuf.SQQQunMember;
import com.lys.protobuf.SRequest_QQQunAddModify;
import com.lys.protobuf.SRequest_QQQunMemberAddModify;
import com.lys.protobuf.SResponse_QQQunAddModify;
import com.lys.protobuf.SResponse_QQQunMemberAddModify;

import java.util.List;

public class OperateDummy001 extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDummy001(DyAccessibilityService service)
	{
		this.service = service;
	}

	private int runCount;
	private int currCount;
	private long runSpeed;
	private String runTarget;

	private SQQQun mQun;

	@Override
	public void start()
	{
		runCount = ActivityDummy001.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDummy001.readRunSpeed(context);
		runTarget = ActivityDummy001.readRunTarget(context);
		if (TextUtils.isEmpty(runTarget))
		{
			Toast.makeText(context, "请先设置运行目标", Toast.LENGTH_SHORT).show();
			return;
		}
		LOG.v(String.format("runCount = %s, runSpeed = %s, runTarget = %s", runCount, runSpeed, runTarget));
//		if (TextUtils.isEmpty(ActivityDummy001.randomReadMsg(context)))
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
						LOG.v("输入 " + runTarget);
						service.setNodeText(nodeInput, runTarget);
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
								!TextUtils.isEmpty(text) && text.equals("(" + runTarget + ")");
					}
				}, null);
				if (nodeFind != null)
				{
					service.wait(2000, new DyAccessibilityService.OnWaitCallback()
					{
						@Override
						public void process()
						{
							SQQQun qun = new SQQQun();
							qun.code = runTarget;
							catchQunInfo(qun);
							clickMenu(qun);
						}
					});
				}
			}
		});
	}

	private void catchQunInfo(final SQQQun qun)
	{
		service.clickIfFindOne("qunonlinecount", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/title_sub");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.onlineCount = Integer.valueOf(str.substring(0, str.length() - 3));
				}
				catch (Exception e)
				{
				}
			}
		});
	}

	private void clickMenu(final SQQQun qun)
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
					service.wait(2000, new DyAccessibilityService.OnWaitCallback()
					{
						@Override
						public void process()
						{
							catchQunInfo2(qun);
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
											mQun = qun;
											gotoChenYuanList();
										}
									}
								});
							}
						}
					});
				}
			}
		});
	}

	private void catchQunInfo2(final SQQQun qun)
	{
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
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/eom");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					qun.renCount = Integer.valueOf(str.substring(1, str.length() - 1));
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
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/j5r");
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
					findMemberList(-1, 0, 0);
//					findChenYuanList(-1, ActivityDummy001.readRunRecord(context), 0);
				}
			}
		});
	}

	private void findChenYuanList(final int currIndex, final int targetIndex, final int tryTimes)
	{
		if (tryTimes < 100)
		{
			LOG.v("findChenYuanList targetIndex : " + targetIndex + " tryTimes : " + tryTimes);
			service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
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

	private void findChenYuan(final AccessibilityNodeInfo listNode, int currIndex, final int targetIndex)
	{
		boolean findIt = false;
		for (int i = 0; i < listNode.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = listNode.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				if (checkMember(child) && hasAddFriend(child))
				{
					AccessibilityNodeInfo groupNode = NodeHelper.getChildNode(child, 0);
					if (groupNode != null)
					{
						String className = groupNode.getClassName() != null ? groupNode.getClassName().toString() : null;
						if (!TextUtils.isEmpty(className) && className.equals("android.widget.FrameLayout"))
						{
							AccessibilityNodeInfo nodeFind = service.clickIfFindOne(groupNode, "name", new NodeHelper.OnFindCallback()
							{
								@Override
								public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
								{
									return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/tv_name");
								}
							}, new DyAccessibilityService.OnClickFunction()
							{
								@Override
								public void click(AccessibilityNodeInfo node, String des)
								{
								}
							});
							if (nodeFind != null)
							{
								try
								{
									String str = nodeFind.getText().toString().trim();
									String name = CommonUtils.filterEmoji(str, "");
									if ("无名".equals(name))
									{
										findIt = true;
										break;
									}
								}
								catch (Exception e)
								{
								}
							}
						}
					}
				}
			}
		}
		if (!findIt)
		{
			listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
			findChenYuanList(currIndex, targetIndex, 0);
		}
		else
		{
			findMemberList(-1, 0, 0);
		}
	}

	private void findMemberList(final int currIndex, final int targetIndex, final int tryTimes)
	{
		if (tryTimes < 100)
		{
			LOG.v("findChenYuanList targetIndex : " + targetIndex + " tryTimes : " + tryTimes);
			service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
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
							findMember(node, currIndex, targetIndex);
						}
					});
					if (nodeFind == null)
					{
						findMemberList(currIndex, targetIndex, tryTimes + 1);
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

	private boolean hasAddFriend(AccessibilityNodeInfo node)
	{
		List<AccessibilityNodeInfo> nodes = NodeHelper.findNodes(node, new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(className) && className.equals("android.widget.Button") && //
						!TextUtils.isEmpty(text) && text.equals("加好友");
			}
		});
		return nodes.size() == 1;
	}

	private void findMember(final AccessibilityNodeInfo listNode, int currIndex, final int targetIndex)
	{
		boolean findIt = false;
		for (int i = 0; i < listNode.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = listNode.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				if (checkMember(child) && hasAddFriend(child))
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
								break;
							}
						}
					}
				}
			}
		}
		if (!findIt)
		{
			listNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
			findMemberList(-1, 0, 0);
		}
	}

	private void clickFaXiaoXi(final int targetIndex)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				SQQQunMember member = new SQQQunMember();
				boolean success = catchDetailImpl(member);
				LOGJson.log(member.saveToStr());
				if (!TextUtils.isEmpty(member.qqcode))
				{
					if (success)
					{
						member.quncode = mQun.code;
						SRequest_QQQunMemberAddModify request = new SRequest_QQQunMemberAddModify();
						request.member = member;
						Protocol.doPost(context, SHandleId.QQQunMemberAddModify, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									final SResponse_QQQunMemberAddModify response = SResponse_QQQunMemberAddModify.load(data);
									backNext(targetIndex);
								}
							}
						});
					}
				}
				else
				{
					backNext(targetIndex);
				}
			}
		});
	}

	private boolean catchDetailImpl(final SQQQunMember member)
	{
		int counter = 0;

		AccessibilityNodeInfo nodeFind = service.clickIfFindOne("name", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/dkk");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.name = CommonUtils.filterEmoji(str, "");
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

		nodeFind = service.clickIfFindOne("huoyue", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/ar8");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.huoyue = str;
				}
				catch (Exception e)
				{
				}
			}
		});

		nodeFind = service.clickIfFindOne("zhan", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/l0g");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.zhan = Integer.valueOf(str);
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			member.zhan = -1;

		nodeFind = service.clickIfFindOne("qq", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/gmx");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.qqcode = str.substring(str.indexOf("：") + 1, str.indexOf("(")).trim();
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

		nodeFind = service.clickIfFindOne("level", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/gre");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getContentDescription().toString().trim();
					member.level = str;
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

		nodeFind = service.clickIfFindOne("info", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/jfb");
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.info = CommonUtils.filterEmoji(str.replaceAll("\n", ""), "");
				}
				catch (Exception e)
				{
				}
			}
		});

		nodeFind = service.clickIfFindOne("sign", new NodeHelper.OnFindCallback()
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
					member.sign = CommonUtils.filterEmoji(str, "");
				}
				catch (Exception e)
				{
				}
			}
		});

		nodeFind = service.clickIfFindOne("addtime", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/dq6") && indent == 3;
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.addtime = str;
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

		nodeFind = service.clickIfFindOne("qunlevel", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/dq6") && indent == 4 && TextUtils.isEmpty(contentDescription);
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.qunlevel = str;
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

		nodeFind = service.clickIfFindOne("lastsay", new NodeHelper.OnFindCallback()
		{
			@Override
			public boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId)
			{
				return !TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/dq6") && indent == 4 && !TextUtils.isEmpty(contentDescription);
			}
		}, new DyAccessibilityService.OnClickFunction()
		{
			@Override
			public void click(AccessibilityNodeInfo node, String des)
			{
				try
				{
					String str = node.getText().toString().trim();
					member.lastsay = str;
				}
				catch (Exception e)
				{
				}
			}
		});
		if (nodeFind == null)
			counter++;

//		if (counter > 0)
//		{
//			NodeHelper.printNode(service.getRootInActiveWindow());
//			return false;
//		}

		return true;
	}

	private void backNext(final int targetIndex)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("backNext");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				findMemberList(-1, targetIndex + 1, 0);
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
				final String msg = ActivityDummy001.randomReadMsg(context);
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
						ActivityDummy001.addRunRecord(context);
						startOne();
					}
				}
			}
		});
	}

}
