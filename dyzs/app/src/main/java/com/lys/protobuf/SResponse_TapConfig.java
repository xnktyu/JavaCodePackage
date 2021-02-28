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
import com.lys.protobuf.ProtocolTap.Response_TapConfig;

public class SResponse_TapConfig extends SPTData<Response_TapConfig>
{
	private static final SResponse_TapConfig DefaultInstance = new SResponse_TapConfig();

	public String weiXin = null;

	public static SResponse_TapConfig create(String weiXin)
	{
		SResponse_TapConfig obj = new SResponse_TapConfig();
		obj.weiXin = weiXin;
		return obj;
	}

	public SResponse_TapConfig clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapConfig _other_)
	{
		this.weiXin = _other_.weiXin;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("weiXin"))
			weiXin = _json_.getString("weiXin");
	}

	public static SResponse_TapConfig load(String str)
	{
		try
		{
			SResponse_TapConfig obj = new SResponse_TapConfig();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapConfig load(JSONObject json)
	{
		try
		{
			SResponse_TapConfig obj = new SResponse_TapConfig();
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
			if (weiXin != null)
				_json_.put("weiXin", weiXin);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapConfig> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapConfig> list = new ArrayList<SResponse_TapConfig>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapConfig item = SResponse_TapConfig.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapConfig> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapConfig item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapConfig _proto_)
	{
		if (_proto_.hasWeiXin())
			weiXin = _proto_.getWeiXin();
	}

	public static SResponse_TapConfig load(byte[] bytes)
	{
		try
		{
			SResponse_TapConfig obj = new SResponse_TapConfig();
			obj.parse(Response_TapConfig.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapConfig load(Response_TapConfig proto)
	{
		try
		{
			SResponse_TapConfig obj = new SResponse_TapConfig();
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
	public Response_TapConfig saveToProto()
	{
		Response_TapConfig.Builder _builder_ = Response_TapConfig.newBuilder();
		if (weiXin != null && !weiXin.equals(Response_TapConfig.getDefaultInstance().getWeiXin()))
			_builder_.setWeiXin(weiXin);
		return _builder_.build();
	}
}
