package com.lisyx.tap.tools.DEFAULT;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.lisyx.tap.activity.ActivityDummy002;
import com.lisyx.tap.service.DyAccessibilityService;
import com.lisyx.tap.tools.OperateBase;
import com.lisyx.tap.utils.LOG;
import com.lisyx.tap.utils.NodeHelper;
import com.lisyx.tap.utils.Protocol;
import com.lisyx.tap.view.Ball;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SQQQunMember;
import com.lys.protobuf.SRequest_QQQunMemberGetMsg;
import com.lys.protobuf.SRequest_QQQunMemberSendMsgOver;
import com.lys.protobuf.SResponse_QQQunMemberGetMsg;
import com.lys.protobuf.SResponse_QQQunMemberSendMsgOver;

import java.util.List;

public class OperateDummy002 extends OperateBase
{
	private DyAccessibilityService service = null;

	public OperateDummy002(DyAccessibilityService service)
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
		runCount = ActivityDummy002.readRunCount(context);
		currCount = 0;
		runSpeed = ActivityDummy002.readRunSpeed(context);
		runTarget = ActivityDummy002.readRunTarget(context);
		if (TextUtils.isEmpty(runTarget))
		{
			Toast.makeText(context, "请先设置运行目标", Toast.LENGTH_SHORT).show();
			return;
		}
		LOG.v(String.format("runCount = %s, runSpeed = %s, runTarget = %s", runCount, runSpeed, runTarget));
//		if (TextUtils.isEmpty(ActivityDummy002.randomReadMsg(context)))
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
					clickMenu();
				}
			}
		});
	}

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
					findChenYuanList(-1, ActivityDummy002.readRunRecord(context), 0);
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
							clickSearchMember();
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


	private void clickSearchMember()
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
				}, new DyAccessibilityService.OnClickFunction()
				{
					@Override
					public void click(AccessibilityNodeInfo node, String des)
					{
						Rect rect = new Rect();
						node.getBoundsInScreen(rect);
						service.click(rect.centerX(), rect.centerY(), null);
					}
				});
				if (nodeFind != null)
				{
					inputSearchMember();
				}
			}
		});
	}

	private void inputSearchMember()
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
					public void click(final AccessibilityNodeInfo nodeInput, String des)
					{
						SRequest_QQQunMemberGetMsg request = new SRequest_QQQunMemberGetMsg();
						request.quncode = runTarget;
						Protocol.doPost(context, SHandleId.QQQunMemberGetMsg, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_QQQunMemberGetMsg response = SResponse_QQQunMemberGetMsg.load(data);
									if (response.member != null)
									{
										String localMsg = ActivityDummy002.randomReadMsg(context);
										if (!TextUtils.isEmpty(localMsg))
										{
											response.member.msg = localMsg;
										}
										LOG.v("输入 " + response.member.qqcode);
										service.setNodeText(nodeInput, response.member.qqcode);
										backSearchMember(response.member);
									}
									else
									{
										Ball.stopRunning();
										Toast.makeText(context, "运行完毕", Toast.LENGTH_SHORT).show();
									}
								}
							}
						});
					}
				});
			}
		});
	}

	private void backSearchMember(final SQQQunMember member)
	{
		service.wait(runSpeed, new DyAccessibilityService.OnWaitCallback()
		{
			@Override
			public void process()
			{
				LOG.v("backSearchMember");
				service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
				findSearchMember(member);
			}
		});
	}

	private void findSearchMember(final SQQQunMember member)
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
						return !TextUtils.isEmpty(className) && className.equals("android.widget.FrameLayout") && //
								!TextUtils.isEmpty(viewId) && viewId.equals("com.tencent.mobileqq:id/jzt");
					}
				}, null);
				if (nodeFind != null)
				{
					clickFaXiaoXi(member);
				}
				else
				{
					service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

					SRequest_QQQunMemberSendMsgOver request = new SRequest_QQQunMemberSendMsgOver();
					request.quncode = member.quncode;
					request.qqcode = member.qqcode;
					request.msg = member.msg;
					request.result = 2;
					Protocol.doPost(context, SHandleId.QQQunMemberSendMsgOver, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								final SResponse_QQQunMemberSendMsgOver response = SResponse_QQQunMemberSendMsgOver.load(data);
								clickSearchMember();
							}
						}
					});
				}
			}
		});
	}


	private void clickFaXiaoXi(final SQQQunMember member)
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
					checkMsg(member);
				}
				else
				{
					back(4);
				}
			}
		});
	}

	private void checkMsg(final SQQQunMember member)
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
						return !TextUtils.isEmpty(className) && className.equals("android.widget.TextView") && //
								!TextUtils.isEmpty(text) && text.equals(member.msg);
					}
				});
				if (nodes.size() == 0)
				{
					clickInput(member);
				}
				else
				{
					SRequest_QQQunMemberSendMsgOver request = new SRequest_QQQunMemberSendMsgOver();
					request.quncode = member.quncode;
					request.qqcode = member.qqcode;
					request.msg = member.msg;
					request.result = 3;
					Protocol.doPost(context, SHandleId.QQQunMemberSendMsgOver, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								SResponse_QQQunMemberSendMsgOver response = SResponse_QQQunMemberSendMsgOver.load(data);
								back(1);
							}
						}
					});
				}
			}
		});
	}

	private void clickInput(final SQQQunMember member)
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
					inputText(member);
				}
			}
		});
	}

	private void inputText(final SQQQunMember member)
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
						LOG.v("输入 " + member.msg);
						service.setNodeText(nodeInput, member.msg);
						clickSend(member);
					}
				});
			}
		});
	}

	private void clickSend(final SQQQunMember member)
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

				SRequest_QQQunMemberSendMsgOver request = new SRequest_QQQunMemberSendMsgOver();
				request.quncode = member.quncode;
				request.qqcode = member.qqcode;
				request.msg = member.msg;
				if (nodeFind != null)
					request.result = 1;
				else
					request.result = 4;
				Protocol.doPost(context, SHandleId.QQQunMemberSendMsgOver, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_QQQunMemberSendMsgOver response = SResponse_QQQunMemberSendMsgOver.load(data);
							back(2);
						}
					}
				});

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
						ActivityDummy002.addRunRecord(context);
						startOne();
					}
				}
			}
		});
	}

}
