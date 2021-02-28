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
import com.lys.protobuf.ProtocolTap.Response_TapStartup;

public class SResponse_TapStartup extends SPTData<Response_TapStartup>
{
	private static final SResponse_TapStartup DefaultInstance = new SResponse_TapStartup();

	public STapDevice tap = null;
	public Boolean tryPast = false;
	public Long tryLeftTime = 0L;
	public SApp app = null;

	public static SResponse_TapStartup create(STapDevice tap, Boolean tryPast, Long tryLeftTime, SApp app)
	{
		SResponse_TapStartup obj = new SResponse_TapStartup();
		obj.tap = tap;
		obj.tryPast = tryPast;
		obj.tryLeftTime = tryLeftTime;
		obj.app = app;
		return obj;
	}

	public SResponse_TapStartup clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapStartup _other_)
	{
		this.tap = _other_.tap;
		this.tryPast = _other_.tryPast;
		this.tryLeftTime = _other_.tryLeftTime;
		this.app = _other_.app;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("tap"))
			tap = STapDevice.load(_json_.getJSONObject("tap"));
		if (_json_.containsKey("tryPast"))
			tryPast = _json_.getBoolean("tryPast");
		if (_json_.containsKey("tryLeftTime"))
			tryLeftTime = _json_.getLong("tryLeftTime");
		if (_json_.containsKey("app"))
			app = SApp.load(_json_.getJSONObject("app"));
	}

	public static SResponse_TapStartup load(String str)
	{
		try
		{
			SResponse_TapStartup obj = new SResponse_TapStartup();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapStartup load(JSONObject json)
	{
		try
		{
			SResponse_TapStartup obj = new SResponse_TapStartup();
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
			if (tryPast != null)
				_json_.put("tryPast", tryPast);
			if (tryLeftTime != null)
				_json_.put("tryLeftTime", String.valueOf(tryLeftTime));
			if (app != null)
				_json_.put("app", app.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapStartup> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapStartup> list = new ArrayList<SResponse_TapStartup>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapStartup item = SResponse_TapStartup.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapStartup> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapStartup item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapStartup _proto_)
	{
		if (_proto_.hasTap())
			tap = STapDevice.load(_proto_.getTap());
		if (_proto_.hasTryPast())
			tryPast = _proto_.getTryPast();
		if (_proto_.hasTryLeftTime())
			tryLeftTime = _proto_.getTryLeftTime();
		if (_proto_.hasApp())
			app = SApp.load(_proto_.getApp());
	}

	public static SResponse_TapStartup load(byte[] bytes)
	{
		try
		{
			SResponse_TapStartup obj = new SResponse_TapStartup();
			obj.parse(Response_TapStartup.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapStartup load(Response_TapStartup proto)
	{
		try
		{
			SResponse_TapStartup obj = new SResponse_TapStartup();
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
	public Response_TapStartup saveToProto()
	{
		Response_TapStartup.Builder _builder_ = Response_TapStartup.newBuilder();
		if (tap != null)
			_builder_.setTap(tap.saveToProto());
		if (tryPast != null && !tryPast.equals(Response_TapStartup.getDefaultInstance().getTryPast()))
			_builder_.setTryPast(tryPast);
		if (tryLeftTime != null && !tryLeftTime.equals(Response_TapStartup.getDefaultInstance().getTryLeftTime()))
			_builder_.setTryLeftTime(tryLeftTime);
		if (app != null)
			_builder_.setApp(app.saveToProto());
		return _builder_.build();
	}
}
