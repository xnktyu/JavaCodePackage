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
import com.lys.protobuf.ProtocolTap.Request_TapGet;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapGet extends SPTData<Request_TapGet>
{
	private static final SRequest_TapGet DefaultInstance = new SRequest_TapGet();

	public String deviceId = null;

	public static SRequest_TapGet create(String deviceId)
	{
		SRequest_TapGet obj = new SRequest_TapGet();
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_TapGet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapGet _other_)
	{
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_TapGet load(String str)
	{
		try
		{
			SRequest_TapGet obj = new SRequest_TapGet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapGet load(JSONObject json)
	{
		try
		{
			SRequest_TapGet obj = new SRequest_TapGet();
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
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapGet> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapGet> list = new ArrayList<SRequest_TapGet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapGet item = SRequest_TapGet.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapGet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapGet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapGet _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_TapGet load(byte[] bytes)
	{
		try
		{
			SRequest_TapGet obj = new SRequest_TapGet();
			obj.parse(Request_TapGet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapGet load(Request_TapGet proto)
	{
		try
		{
			SRequest_TapGet obj = new SRequest_TapGet();
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
	public Request_TapGet saveToProto()
	{
		Request_TapGet.Builder _builder_ = Request_TapGet.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapGet.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
