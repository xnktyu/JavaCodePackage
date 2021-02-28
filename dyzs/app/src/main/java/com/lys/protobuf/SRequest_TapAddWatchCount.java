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
import com.lys.protobuf.ProtocolTap.Request_TapAddWatchCount;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapAddWatchCount extends SPTData<Request_TapAddWatchCount>
{
	private static final SRequest_TapAddWatchCount DefaultInstance = new SRequest_TapAddWatchCount();

	public String deviceId = null;
	public Integer watchCount = 0;

	public static SRequest_TapAddWatchCount create(String deviceId, Integer watchCount)
	{
		SRequest_TapAddWatchCount obj = new SRequest_TapAddWatchCount();
		obj.deviceId = deviceId;
		obj.watchCount = watchCount;
		return obj;
	}

	public SRequest_TapAddWatchCount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapAddWatchCount _other_)
	{
		this.deviceId = _other_.deviceId;
		this.watchCount = _other_.watchCount;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("watchCount"))
			watchCount = _json_.getInteger("watchCount");
	}

	public static SRequest_TapAddWatchCount load(String str)
	{
		try
		{
			SRequest_TapAddWatchCount obj = new SRequest_TapAddWatchCount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapAddWatchCount load(JSONObject json)
	{
		try
		{
			SRequest_TapAddWatchCount obj = new SRequest_TapAddWatchCount();
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
			if (watchCount != null)
				_json_.put("watchCount", watchCount);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapAddWatchCount> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapAddWatchCount> list = new ArrayList<SRequest_TapAddWatchCount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapAddWatchCount item = SRequest_TapAddWatchCount.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapAddWatchCount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapAddWatchCount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapAddWatchCount _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasWatchCount())
			watchCount = _proto_.getWatchCount();
	}

	public static SRequest_TapAddWatchCount load(byte[] bytes)
	{
		try
		{
			SRequest_TapAddWatchCount obj = new SRequest_TapAddWatchCount();
			obj.parse(Request_TapAddWatchCount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapAddWatchCount load(Request_TapAddWatchCount proto)
	{
		try
		{
			SRequest_TapAddWatchCount obj = new SRequest_TapAddWatchCount();
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
	public Request_TapAddWatchCount saveToProto()
	{
		Request_TapAddWatchCount.Builder _builder_ = Request_TapAddWatchCount.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapAddWatchCount.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (watchCount != null && !watchCount.equals(Request_TapAddWatchCount.getDefaultInstance().getWatchCount()))
			_builder_.setWatchCount(watchCount);
		return _builder_.build();
	}
}
