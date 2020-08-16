package com.xnktyu.utils;

import com.mysql.jdbc.Driver;

import java.sql.*;

public class DBHelper
{
	static
	{
		try
		{
			new Driver();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private final Object lock = new Object();

	private String host = null;
	private String user = null;
	private String password = null;
	private String db = null;
	private Connection connection = null;

	public DBHelper(String host, String user, String password, String db)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.db = db;
	}

	private Connection getConn()
	{
		if (connection == null)
		{
			try
			{
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/?serverTimezone=CST&characterEncoding=UTF-8", user, password);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			exeSqlImpl(connection, String.format("create database if not exists %s character set utf8;", db), null);
			exeSqlImpl(connection, String.format("use %s;", db), null);
		}
		return connection;
	}

	private static boolean exeSqlImpl(Connection conn, String sql, OnCallback callback, Object... params)
	{
//		LOG.v("sql : " + sql);
		boolean result = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = conn.prepareStatement(sql);
			if (params != null && params.length > 0)
			{
				for (int i = 0; i < params.length; i++)
				{
					Object param = params[i];
					stmt.setString(i + 1, param != null ? param.toString() : "");
				}
			}
			boolean hasResultSet = stmt.execute();
			if (hasResultSet)
			{
				rs = stmt.getResultSet();
				if (callback != null)
					callback.onResult(rs);
			}
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	public interface OnCallback
	{
		void onResult(ResultSet rs) throws SQLException;
	}

	public boolean exeSql(String sql, OnCallback callback, Object... params)
	{
		synchronized (lock)
		{
			return exeSqlImpl(getConn(), sql, callback, params);
		}
	}

	public int getRecordCount(String sql)
	{
		final int[] count = {0};
		exeSql(sql, new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				while (rs.next())
				{
					count[0]++;
				}
			}
		});
		return count[0];
	}

	public boolean hasRecord(String sql)
	{
		return getRecordCount(sql) > 0;
	}

	public static void showResultSet(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= columnCount; i++)
			{
				sb.append(String.format("%20s\t", rsmd.getColumnName(i)));
			}
			LOG.v(sb.toString());
		}
		while (rs.next())
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= columnCount; i++)
			{
				sb.append(String.format("%20s\t", rs.getString(i)));
			}
			LOG.v(sb.toString());
		}
	}

	public void showTables()
	{
		exeSql("show tables;", new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				showResultSet(rs);
			}
		});
	}

	public boolean hasTable(final String table)
	{
		final boolean[] result = {false};
		exeSql("show tables;", new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				while (rs.next())
				{
					if (rs.getString(1).toLowerCase().equals(table.toLowerCase()))
					{
						result[0] = true;
						break;
					}
				}
			}
		});
		return result[0];
	}

	public void showTableDesc(String table)
	{
		exeSql(String.format("desc %s;", table), new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				showResultSet(rs);
			}
		});
	}

	public boolean hasColumn(String table, final String field)
	{
		final boolean[] result = {false};
		exeSql(String.format("desc %s;", table), new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				while (rs.next())
				{
					if (rs.getString("field").toLowerCase().equals(field.toLowerCase()))
					{
						result[0] = true;
						break;
					}
				}
			}
		});
		return result[0];
	}

	public void addColumnFirst(String table, String field, String type)
	{
		if (!hasColumn(table, field))
		{
			exeSql(String.format("alter table %s add %s %s first;", table, field, type), null);
		}
	}

	public void addColumn(String table, String field, String type)
	{
		if (!hasColumn(table, field))
		{
			exeSql(String.format("alter table %s add %s %s;", table, field, type), null);
		}
	}

	public void addColumn(String table, String field, String type, String posField)
	{
		if (!hasColumn(table, field))
		{
			exeSql(String.format("alter table %s add %s %s after %s;", table, field, type, posField), null);
		}
	}

	public void dropColumn(String table, String field)
	{
		if (hasColumn(table, field))
		{
			exeSql(String.format("alter table %s drop %s;", table, field), null);
		}
	}

	public void modifyColumn(String table, String field, String newType)
	{
		if (hasColumn(table, field))
		{
			exeSql(String.format("alter table %s modify %s %s;", table, field, newType), null);
		}
	}

	public void changeColumn(String table, String oldField, String newField, String newType)
	{
		if (hasColumn(table, oldField))
		{
			exeSql(String.format("alter table %s change %s %s %s;", table, oldField, newField, newType), null);
		}
	}

	public void renameTable(String oldTable, String newTable)
	{
		if (hasTable(oldTable) && !hasTable(newTable))
		{
			exeSql(String.format("alter table %s rename to %s;", oldTable, newTable), null);
		}
	}

	// ---------------------------------------------------

	public boolean insert(Object table, Object... params)
	{
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();
		Object[] objs = new Object[params.length / 2];
		for (int i = 0; i < params.length; i += 2)
		{
			Object key = params[i];
			Object value = params[i + 1];
			if (i == 0)
			{
				keys.append(key);
				values.append("?");
			}
			else
			{
				keys.append(", " + key);
				values.append(", " + "?");
			}
			objs[i / 2] = value;
		}
		return exeSql(String.format("insert into %s(%s) values(%s);", table, keys, values), null, objs);
	}

	public boolean select(Object table, OnCallback callback, Object... params)
	{
		return exeSql(String.format("select * from %s%s;", table, where(params)), callback);
	}

	public int getRecordCount(Object table, Object... params)
	{
		final int[] count = {0};
		select(table, new OnCallback()
		{
			@Override
			public void onResult(ResultSet rs) throws SQLException
			{
				while (rs.next())
				{
					count[0]++;
				}
			}
		}, params);
		return count[0];
	}

	public boolean hasRecord(Object table, Object... params)
	{
		return getRecordCount(table, params) > 0;
	}

	public static StringBuilder set(Object... params)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.length; i += 2)
		{
			Object key = params[i];
			Object value = params[i + 1];
			if (i == 0)
				sb.append(String.format("%s = '%s'", key, value));
			else
				sb.append(String.format(", %s = '%s'", key, value));
		}
		return sb;
	}

	public static StringBuilder where(Object... params)
	{
		StringBuilder sb = new StringBuilder();
		if (params != null && params.length > 0)
		{
			for (int i = 0; i < params.length; i += 2)
			{
				Object key = params[i];
				Object value = params[i + 1];
				if (i == 0)
					sb.append(String.format(" where %s = '%s'", key, value));
				else
					sb.append(String.format(" and %s = '%s'", key, value));
			}
		}
		return sb;
	}

	public boolean update(Object table, StringBuilder setBuilder, Object... params)
	{
		return exeSql(String.format("update %s set %s%s;", table, setBuilder, where(params)), null);
	}

	public boolean delete(Object table, Object... params)
	{
		return exeSql(String.format("delete from %s%s;", table, where(params)), null);
	}
}
