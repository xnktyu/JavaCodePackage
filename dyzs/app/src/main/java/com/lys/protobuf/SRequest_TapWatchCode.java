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
import com.lys.protobuf.ProtocolTap.Request_TapWatchCode;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapWatchCode extends SPTData<Request_TapWatchCode>
{
	private static final SRequest_TapWatchCode DefaultInstance = new SRequest_TapWatchCode();

	public String deviceId = null;
	public String watchActor = null;

	public static SRequest_TapWatchCode create(String deviceId, String watchActor)
	{
		SRequest_TapWatchCode obj = new SRequest_TapWatchCode();
		obj.deviceId = deviceId;
		obj.watchActor = watchActor;
		return obj;
	}

	public SRequest_TapWatchCode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapWatchCode _other_)
	{
		this.deviceId = _other_.deviceId;
		this.watchActor = _other_.watchActor;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("watchActor"))
			watchActor = _json_.getString("watchActor");
	}

	public static SRequest_TapWatchCode load(String str)
	{
		try
		{
			SRequest_TapWatchCode obj = new SRequest_TapWatchCode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapWatchCode load(JSONObject json)
	{
		try
		{
			SRequest_TapWatchCode obj = new SRequest_TapWatchCode();
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
			if (watchActor != null)
				_json_.put("watchActor", watchActor);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapWatchCode> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapWatchCode> list = new ArrayList<SRequest_TapWatchCode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapWatchCode item = SRequest_TapWatchCode.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapWatchCode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapWatchCode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapWatchCode _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasWatchActor())
			watchActor = _proto_.getWatchActor();
	}

	public static SRequest_TapWatchCode load(byte[] bytes)
	{
		try
		{
			SRequest_TapWatchCode obj = new SRequest_TapWatchCode();
			obj.parse(Request_TapWatchCode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapWatchCode load(Request_TapWatchCode proto)
	{
		try
		{
			SRequest_TapWatchCode obj = new SRequest_TapWatchCode();
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
	public Request_TapWatchCode saveToProto()
	{
		Request_TapWatchCode.Builder _builder_ = Request_TapWatchCode.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapWatchCode.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (watchActor != null && !watchActor.equals(Request_TapWatchCode.getDefaultInstance().getWatchActor()))
			_builder_.setWatchActor(watchActor);
		return _builder_.build();
	}
}
