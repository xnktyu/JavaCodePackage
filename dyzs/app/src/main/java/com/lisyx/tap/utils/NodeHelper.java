package com.lisyx.tap.utils;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class NodeHelper
{
	public interface OnEachCallback
	{
		void each(AccessibilityNodeInfo node, int indent);
	}

	private static void eachNode(AccessibilityNodeInfo node, int indent, OnEachCallback eachCallback)
	{
		if (eachCallback != null)
			eachCallback.each(node, indent);
		if (node != null && node.isVisibleToUser())
		{
			for (int i = 0; i < node.getChildCount(); i++)
			{
				eachNode(node.getChild(i), indent + 1, eachCallback);
			}
		}
	}

	public static void printNode(AccessibilityNodeInfo parentNode)
	{
		eachNode(parentNode, 0, new OnEachCallback()
		{
			@Override
			public void each(AccessibilityNodeInfo node, int indent)
			{
				if (node != null && node.isVisibleToUser())
				{
					if (node.isClickable())
					{
//						LOG.e(CommonUtils.getIndentStr(indent) + node.toString());
						LOG.e(CommonUtils.getIndentStr(indent) + String.format("%s, %s, %s, %s", node.getClassName(), node.getText(), node.getContentDescription(), node.getViewIdResourceName()));
					}
					else
					{
//						LOG.v(CommonUtils.getIndentStr(indent) + node.toString());
						LOG.v(CommonUtils.getIndentStr(indent) + String.format("%s, %s, %s, %s", node.getClassName(), node.getText(), node.getContentDescription(), node.getViewIdResourceName()));
					}
				}
//				else
//				{
//					LOG.v(CommonUtils.getIndentStr(indent) + "node is null or not visible");
//				}
			}
		});
	}

	public interface OnFindCallback
	{
		boolean find(AccessibilityNodeInfo theNode, int indent, String className, String text, String contentDescription, String viewId);
	}

	public static List<AccessibilityNodeInfo> findNodes(AccessibilityNodeInfo parentNode, final OnFindCallback findCallback)
	{
		final List<AccessibilityNodeInfo> nodes = new ArrayList<>();
		eachNode(parentNode, 0, new OnEachCallback()
		{
			@Override
			public void each(AccessibilityNodeInfo node, int indent)
			{
				if (node != null && node.isVisibleToUser())
				{
					if (findCallback.find(node, indent, node.getClassName() != null ? node.getClassName().toString() : null, //
							node.getText() != null ? node.getText().toString() : null, //
							node.getContentDescription() != null ? node.getContentDescription().toString() : null, //
							node.getViewIdResourceName()))
					{
						nodes.add(node);
					}
				}
			}
		});
//		if (nodes.size() == 1)
//			return nodes.get(0);
//		else
//			LOG.e(String.format("findOneNode error : size is %s", nodes.size()));
		return nodes;
	}

	public static int getChildCount(AccessibilityNodeInfo parent)
	{
		int count = 0;
		for (int i = 0; i < parent.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = parent.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				count++;
			}
		}
		return count;
	}

	public static int getIndex(AccessibilityNodeInfo node)
	{
		AccessibilityNodeInfo parent = node.getParent();
		int index = -1;
		for (int i = 0; i < parent.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = parent.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				index++;
				if (node.equals(child))
				{
					return index;
				}
			}
		}
		return -1;
	}

	public static AccessibilityNodeInfo getChildNode(AccessibilityNodeInfo parent, int idx)
	{
		int index = -1;
		for (int i = 0; i < parent.getChildCount(); i++)
		{
			AccessibilityNodeInfo child = parent.getChild(i);
			if (child != null && child.isVisibleToUser())
			{
				index++;
				if (idx == index)
				{
					return child;
				}
			}
		}
		return null;
	}

	public static AccessibilityNodeInfo brotherNodeIndex(AccessibilityNodeInfo node, int index)
	{
		return getChildNode(node.getParent(), index);
	}

	public static AccessibilityNodeInfo brotherNodeOffset(AccessibilityNodeInfo node, int offset)
	{
		return getChildNode(node.getParent(), getIndex(node) + offset);
	}

//	public static AccessibilityNodeInfo findOneNode(AccessibilityNodeInfo parentNode, final String findClassName, final String findText, final String findContentDescription, final String findViewId)
//	{
//		return findOneNode(parentNode, new OnFindCallback()
//		{
//			@Override
//			public boolean find(String className, String text, String contentDescription, String viewId)
//			{
//				return (TextUtils.isEmpty(findClassName) || findClassName.equals(className)) //
//						&& (TextUtils.isEmpty(findText) || findText.equals(text)) //
//						&& (TextUtils.isEmpty(findContentDescription) || findContentDescription.equals(contentDescription)) //
//						&& (TextUtils.isEmpty(findViewId) || findViewId.equals(viewId));
//			}
//		});
//	}
}
