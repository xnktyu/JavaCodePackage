package com.lisyx.tap.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行shell脚本工具类
 *
 * @author Mountain
 */
public class CommandExecution
{
	public final static String COMMAND_SU = "su";
	public final static String COMMAND_SH = "sh";
	public final static String COMMAND_EXIT = "exit\n";
	public final static String COMMAND_LINE_END = "\n";

	/**
	 * Command执行结果
	 *
	 * @author Mountain
	 */
	public static class CommandResult
	{
		public int result = -1;
		public String errorMsg;
		public String successMsg;
	}

	/**
	 * 执行命令—单条
	 *
	 * @param command
	 * @param isRoot
	 * @return
	 */
	public static CommandResult execCommand(String command, boolean isRoot)
	{
//		String[] commands = {command};
		List<String> commands = new ArrayList<>();
		commands.add(command);
		CommandResult ret = execCommand(commands, isRoot);
		return ret;
	}

	/**
	 * 执行命令-多条
	 *
	 * @param commands
	 * @param isRoot
	 * @return
	 */
	public static CommandResult execCommand(List<String> commands, boolean isRoot)
	{
		CommandResult commandResult = new CommandResult();
		if (commands == null || commands.size() == 0)
			return commandResult;
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		DataOutputStream os = null;
//		BufferedReader successResult = null;
//		BufferedReader errorResult = null;
//		StringBuilder successMsg = null;
//		StringBuilder errorMsg = null;
		try
		{
//			LOG.v("---------- execCommand begin ----------");
			process = runtime.exec(isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands)
			{
				if (command != null)
				{
//					if (!command.startsWith("sendevent"))
//						LOG.v("----------:" + command);
					os.write(command.getBytes());
					os.writeBytes(COMMAND_LINE_END);
					os.flush();
				}
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();
//			LOG.v("-------- flush over");

			//获取错误信息
//			successMsg = new StringBuilder();
//			errorMsg = new StringBuilder();
//			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//			String s;
//			while ((s = successResult.readLine()) != null)
//				successMsg.append(s);
//			while ((s = errorResult.readLine()) != null)
//				errorMsg.append(s);
//			commandResult.successMsg = successMsg.toString();
//			commandResult.errorMsg = errorMsg.toString();

			// 注意：必须要取出ffmpeg在执行命令过程中产生的输出信息，如果不取的话当输出流信息填满jvm存储输出留信息的缓冲区时，线程就回阻塞住
			PrintStream errorStream = new PrintStream(process.getErrorStream(), true);
			PrintStream inputStream = new PrintStream(process.getInputStream(), false);
			errorStream.start();
			inputStream.start();


//			LOG.v("-------- before waitFor");
			commandResult.result = process.waitFor();
//			LOG.v("-------- after waitFor");

			errorStream.join();
			inputStream.join();

			commandResult.successMsg = inputStream.stringBuffer.toString();
			commandResult.errorMsg = errorStream.stringBuffer.toString();

//			LOG.v(commandResult.result + " | " + commandResult.successMsg + " | " + commandResult.errorMsg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (os != null)
					os.close();
//				if (successResult != null)
//					successResult.close();
//				if (errorResult != null)
//					errorResult.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				if (process != null)
				{
					ProcessKiller ffmpegKiller = new ProcessKiller(process);
					// JVM退出时，先通过钩子关闭FFmepg进程
					runtime.addShutdownHook(ffmpegKiller);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
//		LOG.v("---------- execCommand over ----------");
		System.gc();
		return commandResult;
	}

	private static class PrintStream extends Thread
	{
		private InputStream inputStream = null;
		private boolean isError = false;
		private BufferedReader bufferedReader = null;
		private StringBuffer stringBuffer = new StringBuffer();

		public PrintStream(InputStream inputStream, boolean isError)
		{
			this.inputStream = inputStream;
			this.isError = isError;
		}

		@Override
		public void run()
		{
			try
			{
				if (null == inputStream)
				{
					LOG.e("--- 读取输出流出错！因为当前输出流为空！---");
				}
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null)
				{
//					if (isError)
//						LOG.e(" --> " + line);
//					else
//						LOG.v(" --> " + line);
					stringBuffer.append(line + "\r\n");
				}
			}
			catch (Exception e)
			{
				LOG.e("--- 读取输入流出错了！--- 错误信息：" + e.getMessage());
			}
			finally
			{
				try
				{
					if (null != bufferedReader)
					{
						bufferedReader.close();
					}
					if (null != inputStream)
					{
						inputStream.close();
					}
				}
				catch (Exception e)
				{
					LOG.e("--- 调用PrintStream读取输出流后，关闭流时出错！---");
				}
			}
		}
	}

	private static class ProcessKiller extends Thread
	{
		private Process process;

		public ProcessKiller(Process process)
		{
			this.process = process;
		}

		@Override
		public void run()
		{
			this.process.destroy();
//			LOG.v("--- 已销毁FFmpeg进程 --- 进程名： " + process.toString());
		}
	}

}