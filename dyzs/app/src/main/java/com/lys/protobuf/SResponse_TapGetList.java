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
import com.lys.protobuf.ProtocolTap.Response_TapGetList;

public class SResponse_TapGetList extends SPTData<Response_TapGetList>
{
	private static final SResponse_TapGetList DefaultInstance = new SResponse_TapGetList();

	public List<STapDevice> taps = new ArrayList<STapDevice>();

	public static SResponse_TapGetList create()
	{
		SResponse_TapGetList obj = new SResponse_TapGetList();
		return obj;
	}

	public SResponse_TapGetList clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapGetList _other_)
	{
		this.taps = _other_.taps;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("taps"))
			taps = STapDevice.loadList(_json_.getJSONArray("taps"));
	}

	public static SResponse_TapGetList load(String str)
	{
		try
		{
			SResponse_TapGetList obj = new SResponse_TapGetList();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapGetList load(JSONObject json)
	{
		try
		{
			SResponse_TapGetList obj = new SResponse_TapGetList();
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
			if (taps != null)
				_json_.put("taps", STapDevice.saveList(taps));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapGetList> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapGetList> list = new ArrayList<SResponse_TapGetList>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapGetList item = SResponse_TapGetList.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapGetList> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapGetList item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapGetList _proto_)
	{
		for (int i = 0; i < _proto_.getTapsCount(); i++)
			taps.add(STapDevice.load(_proto_.getTaps(i)));
	}

	public static SResponse_TapGetList load(byte[] bytes)
	{
		try
		{
			SResponse_TapGetList obj = new SResponse_TapGetList();
			obj.parse(Response_TapGetList.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapGetList load(Response_TapGetList proto)
	{
		try
		{
			SResponse_TapGetList obj = new SResponse_TapGetList();
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
	public Response_TapGetList saveToProto()
	{
		Response_TapGetList.Builder _builder_ = Response_TapGetList.newBuilder();
		if (taps != null)
			for (STapDevice _value_ : taps)
				_builder_.addTaps(_value_.saveToProto());
		return _builder_.build();
	}
}
