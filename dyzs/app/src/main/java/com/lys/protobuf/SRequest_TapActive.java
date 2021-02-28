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
import com.lys.protobuf.ProtocolTap.Request_TapActive;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapActive extends SPTData<Request_TapActive>
{
	private static final SRequest_TapActive DefaultInstance = new SRequest_TapActive();

	public String deviceId = null;
	public String activeCode = null;

	public static SRequest_TapActive create(String deviceId, String activeCode)
	{
		SRequest_TapActive obj = new SRequest_TapActive();
		obj.deviceId = deviceId;
		obj.activeCode = activeCode;
		return obj;
	}

	public SRequest_TapActive clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapActive _other_)
	{
		this.deviceId = _other_.deviceId;
		this.activeCode = _other_.activeCode;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("activeCode"))
			activeCode = _json_.getString("activeCode");
	}

	public static SRequest_TapActive load(String str)
	{
		try
		{
			SRequest_TapActive obj = new SRequest_TapActive();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapActive load(JSONObject json)
	{
		try
		{
			SRequest_TapActive obj = new SRequest_TapActive();
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
			if (activeCode != null)
				_json_.put("activeCode", activeCode);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapActive> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapActive> list = new ArrayList<SRequest_TapActive>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapActive item = SRequest_TapActive.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapActive> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapActive item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapActive _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasActiveCode())
			activeCode = _proto_.getActiveCode();
	}

	public static SRequest_TapActive load(byte[] bytes)
	{
		try
		{
			SRequest_TapActive obj = new SRequest_TapActive();
			obj.parse(Request_TapActive.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapActive load(Request_TapActive proto)
	{
		try
		{
			SRequest_TapActive obj = new SRequest_TapActive();
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
	public Request_TapActive saveToProto()
	{
		Request_TapActive.Builder _builder_ = Request_TapActive.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapActive.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (activeCode != null && !activeCode.equals(Request_TapActive.getDefaultInstance().getActiveCode()))
			_builder_.setActiveCode(activeCode);
		return _builder_.build();
	}
}
