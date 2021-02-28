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
import com.lys.protobuf.ProtocolTap.Response_TapWatchCode;

public class SResponse_TapWatchCode extends SPTData<Response_TapWatchCode>
{
	private static final SResponse_TapWatchCode DefaultInstance = new SResponse_TapWatchCode();

	public Boolean success = false;
	public String msg = null;
	public STapDevice tap = null;
	public STapDevice watch = null;

	public static SResponse_TapWatchCode create(Boolean success, String msg, STapDevice tap, STapDevice watch)
	{
		SResponse_TapWatchCode obj = new SResponse_TapWatchCode();
		obj.success = success;
		obj.msg = msg;
		obj.tap = tap;
		obj.watch = watch;
		return obj;
	}

	public SResponse_TapWatchCode clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapWatchCode _other_)
	{
		this.success = _other_.success;
		this.msg = _other_.msg;
		this.tap = _other_.tap;
		this.watch = _other_.watch;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("success"))
			success = _json_.getBoolean("success");
		if (_json_.containsKey("msg"))
			msg = _json_.getString("msg");
		if (_json_.containsKey("tap"))
			tap = STapDevice.load(_json_.getJSONObject("tap"));
		if (_json_.containsKey("watch"))
			watch = STapDevice.load(_json_.getJSONObject("watch"));
	}

	public static SResponse_TapWatchCode load(String str)
	{
		try
		{
			SResponse_TapWatchCode obj = new SResponse_TapWatchCode();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapWatchCode load(JSONObject json)
	{
		try
		{
			SResponse_TapWatchCode obj = new SResponse_TapWatchCode();
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
			if (success != null)
				_json_.put("success", success);
			if (msg != null)
				_json_.put("msg", msg);
			if (tap != null)
				_json_.put("tap", tap.saveToJson());
			if (watch != null)
				_json_.put("watch", watch.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapWatchCode> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapWatchCode> list = new ArrayList<SResponse_TapWatchCode>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapWatchCode item = SResponse_TapWatchCode.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapWatchCode> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapWatchCode item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapWatchCode _proto_)
	{
		if (_proto_.hasSuccess())
			success = _proto_.getSuccess();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
		if (_proto_.hasTap())
			tap = STapDevice.load(_proto_.getTap());
		if (_proto_.hasWatch())
			watch = STapDevice.load(_proto_.getWatch());
	}

	public static SResponse_TapWatchCode load(byte[] bytes)
	{
		try
		{
			SResponse_TapWatchCode obj = new SResponse_TapWatchCode();
			obj.parse(Response_TapWatchCode.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapWatchCode load(Response_TapWatchCode proto)
	{
		try
		{
			SResponse_TapWatchCode obj = new SResponse_TapWatchCode();
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
	public Response_TapWatchCode saveToProto()
	{
		Response_TapWatchCode.Builder _builder_ = Response_TapWatchCode.newBuilder();
		if (success != null && !success.equals(Response_TapWatchCode.getDefaultInstance().getSuccess()))
			_builder_.setSuccess(success);
		if (msg != null && !msg.equals(Response_TapWatchCode.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		if (tap != null)
			_builder_.setTap(tap.saveToProto());
		if (watch != null)
			_builder_.setWatch(watch.saveToProto());
		return _builder_.build();
	}
}
