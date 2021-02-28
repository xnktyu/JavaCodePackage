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
import com.lys.protobuf.ProtocolTap.Request_TapTryPast;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapTryPast extends SPTData<Request_TapTryPast>
{
	private static final SRequest_TapTryPast DefaultInstance = new SRequest_TapTryPast();

	public String deviceId = null;

	public static SRequest_TapTryPast create(String deviceId)
	{
		SRequest_TapTryPast obj = new SRequest_TapTryPast();
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_TapTryPast clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapTryPast _other_)
	{
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_TapTryPast load(String str)
	{
		try
		{
			SRequest_TapTryPast obj = new SRequest_TapTryPast();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapTryPast load(JSONObject json)
	{
		try
		{
			SRequest_TapTryPast obj = new SRequest_TapTryPast();
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

	public static List<SRequest_TapTryPast> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapTryPast> list = new ArrayList<SRequest_TapTryPast>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapTryPast item = SRequest_TapTryPast.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapTryPast> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapTryPast item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapTryPast _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_TapTryPast load(byte[] bytes)
	{
		try
		{
			SRequest_TapTryPast obj = new SRequest_TapTryPast();
			obj.parse(Request_TapTryPast.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapTryPast load(Request_TapTryPast proto)
	{
		try
		{
			SRequest_TapTryPast obj = new SRequest_TapTryPast();
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
	public Request_TapTryPast saveToProto()
	{
		Request_TapTryPast.Builder _builder_ = Request_TapTryPast.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapTryPast.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
