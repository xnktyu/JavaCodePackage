package com.lys.protobuf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.protobuf.ByteString;

import com.lys.base.utils.SPTData;
import com.lys.protobuf.ProtocolTap.Request_TapConfig;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapConfig extends SPTData<Request_TapConfig>
{
	private static final SRequest_TapConfig DefaultInstance = new SRequest_TapConfig();


	public static SRequest_TapConfig create()
	{
		SRequest_TapConfig obj = new SRequest_TapConfig();
		return obj;
	}

	public SRequest_TapConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapConfig _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SRequest_TapConfig load(String str)
	{
		try
		{
			SRequest_TapConfig obj = new SRequest_TapConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapConfig load(JSONObject json)
	{
		try
		{
			SRequest_TapConfig obj = new SRequest_TapConfig();
			obj.parse(json);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject saveToJson()
	{
		try
		{
			JSONObject _json_ = new JSONObject(true);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapConfig> list = new ArrayList<SRequest_TapConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapConfig item = SRequest_TapConfig.load(jo);
				if (item == null)
					return null;
				list.add(item);
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray saveList(List<SRequest_TapConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapConfig _proto_)
	{
	}

	public static SRequest_TapConfig load(byte[] bytes)
	{
		try
		{
			SRequest_TapConfig obj = new SRequest_TapConfig();
			obj.parse(Request_TapConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapConfig load(Request_TapConfig proto)
	{
		try
		{
			SRequest_TapConfig obj = new SRequest_TapConfig();
			obj.parse(proto);
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Request_TapConfig saveToProto()
	{
		Request_TapConfig.Builder _builder_ = Request_TapConfig.newBuilder();
		return _builder_.build();
	}
}
