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
import com.lys.protobuf.ProtocolTap.Response_TapGet;

public class SResponse_TapGet extends SPTData<Response_TapGet>
{
	private static final SResponse_TapGet DefaultInstance = new SResponse_TapGet();

	public STapDevice tap = null;

	public static SResponse_TapGet create(STapDevice tap)
	{
		SResponse_TapGet obj = new SResponse_TapGet();
		obj.tap = tap;
		return obj;
	}

	public SResponse_TapGet clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapGet _other_)
	{
		this.tap = _other_.tap;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tap"))
			tap = STapDevice.load(_json_.getJSONObject("tap"));
	}

	public static SResponse_TapGet load(String str)
	{
		try
		{
			SResponse_TapGet obj = new SResponse_TapGet();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapGet load(JSONObject json)
	{
		try
		{
			SResponse_TapGet obj = new SResponse_TapGet();
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

	public static List<SResponse_TapGet> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapGet> list = new ArrayList<SResponse_TapGet>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapGet item = SResponse_TapGet.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapGet> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapGet item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapGet _proto_)
	{
		if (_proto_.hasTap())
			tap = STapDevice.load(_proto_.getTap());
	}

	public static SResponse_TapGet load(byte[] bytes)
	{
		try
		{
			SResponse_TapGet obj = new SResponse_TapGet();
			obj.parse(Response_TapGet.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapGet load(Response_TapGet proto)
	{
		try
		{
			SResponse_TapGet obj = new SResponse_TapGet();
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
	public Response_TapGet saveToProto()
	{
		Response_TapGet.Builder _builder_ = Response_TapGet.newBuilder();
		if (tap != null)
			_builder_.setTap(tap.saveToProto());
		return _builder_.build();
	}
}
