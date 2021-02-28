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
import com.lys.protobuf.ProtocolTap.Request_TapGetList;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_TapGetList extends SPTData<Request_TapGetList>
{
	private static final SRequest_TapGetList DefaultInstance = new SRequest_TapGetList();

	public String deviceId = null;
	public Long createTime = 0L;
	public Integer pageSize = 0;

	public static SRequest_TapGetList create(String deviceId, Long createTime, Integer pageSize)
	{
		SRequest_TapGetList obj = new SRequest_TapGetList();
		obj.deviceId = deviceId;
		obj.createTime = createTime;
		obj.pageSize = pageSize;
		return obj;
	}

	public SRequest_TapGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_TapGetList _other_)
	{
		this.deviceId = _other_.deviceId;
		this.createTime = _other_.createTime;
		this.pageSize = _other_.pageSize;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("pageSize"))
			pageSize = _json_.getInteger("pageSize");
	}

	public static SRequest_TapGetList load(String str)
	{
		try
		{
			SRequest_TapGetList obj = new SRequest_TapGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapGetList load(JSONObject json)
	{
		try
		{
			SRequest_TapGetList obj = new SRequest_TapGetList();
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
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (pageSize != null)
				_json_.put("pageSize", pageSize);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_TapGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_TapGetList> list = new ArrayList<SRequest_TapGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_TapGetList item = SRequest_TapGetList.load(jo);
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

	public static JSONArray saveList(List<SRequest_TapGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_TapGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_TapGetList _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasPageSize())
			pageSize = _proto_.getPageSize();
	}

	public static SRequest_TapGetList load(byte[] bytes)
	{
		try
		{
			SRequest_TapGetList obj = new SRequest_TapGetList();
			obj.parse(Request_TapGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_TapGetList load(Request_TapGetList proto)
	{
		try
		{
			SRequest_TapGetList obj = new SRequest_TapGetList();
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
	public Request_TapGetList saveToProto()
	{
		Request_TapGetList.Builder _builder_ = Request_TapGetList.newBuilder();
		if (deviceId != null && !deviceId.equals(Request_TapGetList.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (createTime != null && !createTime.equals(Request_TapGetList.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (pageSize != null && !pageSize.equals(Request_TapGetList.getDefaultInstance().getPageSize()))
			_builder_.setPageSize(pageSize);
		return _builder_.build();
	}
}
