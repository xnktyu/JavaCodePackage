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
import com.lys.protobuf.ProtocolTap.Request_TapStartup;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapStartup extends SPTData<Request_TapStartup>
{
	private static final SRequest_TapStartup DefaultInstance = new SRequest_TapStartup();

	public STapDevice tap = null;

	public static SRequest_TapStartup create(STapDevice tap)
	{
		SRequest_TapStartup obj = new SRequest_TapStartup();
		obj.tap = tap;
		return obj;
	}

	public SRequest_TapStartup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapStartup _other_)
	{
		this.tap = _other_.tap;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tap"))
			tap = STapDevice.load(_json_.getJSONObject("tap"));
	}

	public static SRequest_TapStartup load(String str)
	{
		try
		{
			SRequest_TapStartup obj = new SRequest_TapStartup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapStartup load(JSONObject json)
	{
		try
		{
			SRequest_TapStartup obj = new SRequest_TapStartup();
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
			if (tap != null)
				_json_.put("tap", tap.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapStartup> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapStartup> list = new ArrayList<SRequest_TapStartup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapStartup item = SRequest_TapStartup.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapStartup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapStartup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapStartup _proto_)
	{
		if (_proto_.hasTap())
			tap = STapDevice.load(_proto_.getTap());
	}

	public static SRequest_TapStartup load(byte[] bytes)
	{
		try
		{
			SRequest_TapStartup obj = new SRequest_TapStartup();
			obj.parse(Request_TapStartup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapStartup load(Request_TapStartup proto)
	{
		try
		{
			SRequest_TapStartup obj = new SRequest_TapStartup();
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
	public Request_TapStartup saveToProto()
	{
		Request_TapStartup.Builder _builder_ = Request_TapStartup.newBuilder();
		if (tap != null)
			_builder_.setTap(tap.saveToProto());
		return _builder_.build();
	}
}
