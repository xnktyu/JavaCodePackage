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
import com.lys.protobuf.ProtocolTap.Response_TapTryPast;

public class SResponse_TapTryPast extends SPTData<Response_TapTryPast>
{
	private static final SResponse_TapTryPast DefaultInstance = new SResponse_TapTryPast();

	public Boolean past = false;

	public static SResponse_TapTryPast create(Boolean past)
	{
		SResponse_TapTryPast obj = new SResponse_TapTryPast();
		obj.past = past;
		return obj;
	}

	public SResponse_TapTryPast clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapTryPast _other_)
	{
		this.past = _other_.past;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("past"))
			past = _json_.getBoolean("past");
	}

	public static SResponse_TapTryPast load(String str)
	{
		try
		{
			SResponse_TapTryPast obj = new SResponse_TapTryPast();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapTryPast load(JSONObject json)
	{
		try
		{
			SResponse_TapTryPast obj = new SResponse_TapTryPast();
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
			if (past != null)
				_json_.put("past", past);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapTryPast> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapTryPast> list = new ArrayList<SResponse_TapTryPast>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapTryPast item = SResponse_TapTryPast.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapTryPast> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapTryPast item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapTryPast _proto_)
	{
		if (_proto_.hasPast())
			past = _proto_.getPast();
	}

	public static SResponse_TapTryPast load(byte[] bytes)
	{
		try
		{
			SResponse_TapTryPast obj = new SResponse_TapTryPast();
			obj.parse(Response_TapTryPast.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapTryPast load(Response_TapTryPast proto)
	{
		try
		{
			SResponse_TapTryPast obj = new SResponse_TapTryPast();
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
	public Response_TapTryPast saveToProto()
	{
		Response_TapTryPast.Builder _builder_ = Response_TapTryPast.newBuilder();
		if (past != null && !past.equals(Response_TapTryPast.getDefaultInstance().getPast()))
			_builder_.setPast(past);
		return _builder_.build();
	}
}
