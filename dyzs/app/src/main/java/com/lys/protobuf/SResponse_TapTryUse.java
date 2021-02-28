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
import com.lys.protobuf.ProtocolTap.Response_TapTryUse;

public class SResponse_TapTryUse extends SPTData<Response_TapTryUse>
{
	private static final SResponse_TapTryUse DefaultInstance = new SResponse_TapTryUse();

	public Boolean success = false;
	public String msg = null;

	public static SResponse_TapTryUse create(Boolean success, String msg)
	{
		SResponse_TapTryUse obj = new SResponse_TapTryUse();
		obj.success = success;
		obj.msg = msg;
		return obj;
	}

	public SResponse_TapTryUse clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapTryUse _other_)
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

	public static SResponse_TapTryUse load(String str)
	{
		try
		{
			SResponse_TapTryUse obj = new SResponse_TapTryUse();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapTryUse load(JSONObject json)
	{
		try
		{
			SResponse_TapTryUse obj = new SResponse_TapTryUse();
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

	public static List<SResponse_TapTryUse> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapTryUse> list = new ArrayList<SResponse_TapTryUse>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapTryUse item = SResponse_TapTryUse.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapTryUse> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapTryUse item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapTryUse _proto_)
	{
		if (_proto_.hasSuccess())
			success = _proto_.getSuccess();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
	}

	public static SResponse_TapTryUse load(byte[] bytes)
	{
		try
		{
			SResponse_TapTryUse obj = new SResponse_TapTryUse();
			obj.parse(Response_TapTryUse.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapTryUse load(Response_TapTryUse proto)
	{
		try
		{
			SResponse_TapTryUse obj = new SResponse_TapTryUse();
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
	public Response_TapTryUse saveToProto()
	{
		Response_TapTryUse.Builder _builder_ = Response_TapTryUse.newBuilder();
		if (success != null && !success.equals(Response_TapTryUse.getDefaultInstance().getSuccess()))
			_builder_.setSuccess(success);
		if (msg != null && !msg.equals(Response_TapTryUse.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		return _builder_.build();
	}
}
