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
import com.lys.protobuf.ProtocolTap.Request_TapTryUse;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapTryUse extends SPTData<Request_TapTryUse>
{
	private static final SRequest_TapTryUse DefaultInstance = new SRequest_TapTryUse();

	public String deviceId = null;

	public static SRequest_TapTryUse create(String deviceId)
	{
		SRequest_TapTryUse obj = new SRequest_TapTryUse();
		obj.deviceId = deviceId;
		return obj;
	}

	public SRequest_TapTryUse clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapTryUse _other_)
	{
		this.deviceId = _other_.deviceId;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
	}

	public static SRequest_TapTryUse load(String str)
	{
		try
		{
			SRequest_TapTryUse obj = new SRequest_TapTryUse();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapTryUse load(JSONObject json)
	{
		try
		{
			SRequest_TapTryUse obj = new SRequest_TapTryUse();
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

	public static List<SRequest_TapTryUse> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapTryUse> list = new ArrayList<SRequest_TapTryUse>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapTryUse item = SRequest_TapTryUse.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapTryUse> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapTryUse item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapTryUse _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
	}

	public static SRequest_TapTryUse load(byte[] bytes)
	{
		try
		{
			SRequest_TapTryUse obj = new SRequest_TapTryUse();
			obj.parse(Request_TapTryUse.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapTryUse load(Request_TapTryUse proto)
	{
		try
		{
			SRequest_TapTryUse obj = new SRequest_TapTryUse();
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
	public Request_TapTryUse saveToProto()
	{
		Request_TapTryUse.Builder _builder_ = Request_TapTryUse.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapTryUse.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		return _builder_.build();
	}
}
