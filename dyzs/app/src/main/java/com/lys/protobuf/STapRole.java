package com.lys.protobuf;

public class STapRole
{
	public static final int Normal = 0;
	public static final int Master = 1;
	public static final int Root = 2;

	public static String name(int value)
	{
		return ProtocolTap.TapRole.valueOf(value).name().substring("TapRole_".length());
	}
}
