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
import com.lys.protobuf.ProtocolTap.Response_TapActive;

public class SResponse_TapActive extends SPTData<Response_TapActive>
{
	private static final SResponse_TapActive DefaultInstance = new SResponse_TapActive();

	public Boolean success = false;
	public String msg = null;

	public static SResponse_TapActive create(Boolean success, String msg)
	{
		SResponse_TapActive obj = new SResponse_TapActive();
		obj.success = success;
		obj.msg = msg;
		return obj;
	}

	public SResponse_TapActive clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapActive _other_)
	{
		this.success = _other_.success;
		this.msg = _other_.msg;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("success"))
			success = _json_.getBoolean("success");
		if (_json_.containsKey("msg"))
			msg = _json_.getString("msg");
	}

	public static SResponse_TapActive load(String str)
	{
		try
		{
			SResponse_TapActive obj = new SResponse_TapActive();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapActive load(JSONObject json)
	{
		try
		{
			SResponse_TapActive obj = new SResponse_TapActive();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_TapActive> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapActive> list = new ArrayList<SResponse_TapActive>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapActive item = SResponse_TapActive.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapActive> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapActive item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapActive _proto_)
	{
		if (_proto_.hasSuccess())
			success = _proto_.getSuccess();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
	}

	public static SResponse_TapActive load(byte[] bytes)
	{
		try
		{
			SResponse_TapActive obj = new SResponse_TapActive();
			obj.parse(Response_TapActive.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapActive load(Response_TapActive proto)
	{
		try
		{
			SResponse_TapActive obj = new SResponse_TapActive();
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
	public Response_TapActive saveToProto()
	{
		Response_TapActive.Builder _builder_ = Response_TapActive.newBuilder();
		if (success != null && !success.equals(Response_TapActive.getDefaultInstance().getSuccess()))
			_builder_.setSuccess(success);
		if (msg != null && !msg.equals(Response_TapActive.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		return _builder_.build();
	}
}
