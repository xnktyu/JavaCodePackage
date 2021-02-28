package com.xnktyu;

import com.xnktyu.utils.InvokeHelper;

public class main
{
	public static void invoke(String[] args)
	{
		final String className = "com.xnktyu.command";

		Class[] pareTyple = new Class[args.length - 1];
		Object[] pareVaules = new Object[args.length - 1];
		for (int i = 1; i < args.length; i++)
		{
			pareTyple[i - 1] = String.class;
			pareVaules[i - 1] = args[i];
		}
		InvokeHelper.invokeStaticMethod(className, args[0], pareTyple, pareVaules);
	}

	public static void main(String args[])
	{
		// args = new String[] { "dummy", "str001", "str002" };

		if (args.length > 0)
		{
			String cmd = args[0];
			// LOG.v(String.format("cmd = <%s>", cmd));
			for (int i = 1; i < args.length; i++)
			{
				// LOG.v(String.format("param[%d] = <%s>", i, args[i]));
			}

			invoke(args);
		}
	}
}
