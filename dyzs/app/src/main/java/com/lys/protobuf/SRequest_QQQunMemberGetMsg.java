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
import com.lys.protobuf.ProtocolTap.Request_QQQunMemberGetMsg;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_QQQunMemberGetMsg extends SPTData<Request_QQQunMemberGetMsg>
{
	private static final SRequest_QQQunMemberGetMsg DefaultInstance = new SRequest_QQQunMemberGetMsg();

	public String quncode = null;

	public static SRequest_QQQunMemberGetMsg create(String quncode)
	{
		SRequest_QQQunMemberGetMsg obj = new SRequest_QQQunMemberGetMsg();
		obj.quncode = quncode;
		return obj;
	}

	public SRequest_QQQunMemberGetMsg clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_QQQunMemberGetMsg _other_)
	{
		this.quncode = _other_.quncode;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("quncode"))
			quncode = _json_.getString("quncode");
	}

	public static SRequest_QQQunMemberGetMsg load(String str)
	{
		try
		{
			SRequest_QQQunMemberGetMsg obj = new SRequest_QQQunMemberGetMsg();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberGetMsg load(JSONObject json)
	{
		try
		{
			SRequest_QQQunMemberGetMsg obj = new SRequest_QQQunMemberGetMsg();
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
			if (quncode != null)
				_json_.put("quncode", quncode);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_QQQunMemberGetMsg> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_QQQunMemberGetMsg> list = new ArrayList<SRequest_QQQunMemberGetMsg>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_QQQunMemberGetMsg item = SRequest_QQQunMemberGetMsg.load(jo);
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

	public static JSONArray saveList(List<SRequest_QQQunMemberGetMsg> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_QQQunMemberGetMsg item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_QQQunMemberGetMsg _proto_)
	{
		if (_proto_.hasQuncode())
			quncode = _proto_.getQuncode();
	}

	public static SRequest_QQQunMemberGetMsg load(byte[] bytes)
	{
		try
		{
			SRequest_QQQunMemberGetMsg obj = new SRequest_QQQunMemberGetMsg();
			obj.parse(Request_QQQunMemberGetMsg.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberGetMsg load(Request_QQQunMemberGetMsg proto)
	{
		try
		{
			SRequest_QQQunMemberGetMsg obj = new SRequest_QQQunMemberGetMsg();
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
	public Request_QQQunMemberGetMsg saveToProto()
	{
		Request_QQQunMemberGetMsg.Builder _builder_ = Request_QQQunMemberGetMsg.newBuilder();
		if (quncode != null && !quncode.equals(Request_QQQunMemberGetMsg.getDefaultInstance().getQuncode()))
			_builder_.setQuncode(quncode);
		return _builder_.build();
	}
}
