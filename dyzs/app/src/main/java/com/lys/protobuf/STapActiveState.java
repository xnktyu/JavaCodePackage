package com.lys.protobuf;

public class STapActiveState
{
	public static final int Normal = 0;
	public static final int Try = 1;
	public static final int Vip = 2;

	public static String name(int value)
	{
		return ProtocolTap.TapActiveState.valueOf(value).name().substring("TapActiveState_".length());
	}
}
