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
import com.lys.protobuf.ProtocolTap.Request_QQQunMemberSendMsgOver;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_QQQunMemberSendMsgOver extends SPTData<Request_QQQunMemberSendMsgOver>
{
	private static final SRequest_QQQunMemberSendMsgOver DefaultInstance = new SRequest_QQQunMemberSendMsgOver();

	public String quncode = null;
	public String qqcode = null;
	public String msg = null;
	public Integer result = 0; // 1:success  2:未找到，可能已退群  3:消息已经发送过  4:未找到发送按钮

	public static SRequest_QQQunMemberSendMsgOver create(String quncode, String qqcode, String msg, Integer result)
	{
		SRequest_QQQunMemberSendMsgOver obj = new SRequest_QQQunMemberSendMsgOver();
		obj.quncode = quncode;
		obj.qqcode = qqcode;
		obj.msg = msg;
		obj.result = result;
		return obj;
	}

	public SRequest_QQQunMemberSendMsgOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_QQQunMemberSendMsgOver _other_)
	{
		this.quncode = _other_.quncode;
		this.qqcode = _other_.qqcode;
		this.msg = _other_.msg;
		this.result = _other_.result;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("quncode"))
			quncode = _json_.getString("quncode");
		if (_json_.containsKey("qqcode"))
			qqcode = _json_.getString("qqcode");
		if (_json_.containsKey("msg"))
			msg = _json_.getString("msg");
		if (_json_.containsKey("result"))
			result = _json_.getInteger("result");
	}

	public static SRequest_QQQunMemberSendMsgOver load(String str)
	{
		try
		{
			SRequest_QQQunMemberSendMsgOver obj = new SRequest_QQQunMemberSendMsgOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberSendMsgOver load(JSONObject json)
	{
		try
		{
			SRequest_QQQunMemberSendMsgOver obj = new SRequest_QQQunMemberSendMsgOver();
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
			if (qqcode != null)
				_json_.put("qqcode", qqcode);
			if (msg != null)
				_json_.put("msg", msg);
			if (result != null)
				_json_.put("result", result);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_QQQunMemberSendMsgOver> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_QQQunMemberSendMsgOver> list = new ArrayList<SRequest_QQQunMemberSendMsgOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_QQQunMemberSendMsgOver item = SRequest_QQQunMemberSendMsgOver.load(jo);
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

	public static JSONArray saveList(List<SRequest_QQQunMemberSendMsgOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_QQQunMemberSendMsgOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_QQQunMemberSendMsgOver _proto_)
	{
		if (_proto_.hasQuncode())
			quncode = _proto_.getQuncode();
		if (_proto_.hasQqcode())
			qqcode = _proto_.getQqcode();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
		if (_proto_.hasResult())
			result = _proto_.getResult();
	}

	public static SRequest_QQQunMemberSendMsgOver load(byte[] bytes)
	{
		try
		{
			SRequest_QQQunMemberSendMsgOver obj = new SRequest_QQQunMemberSendMsgOver();
			obj.parse(Request_QQQunMemberSendMsgOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberSendMsgOver load(Request_QQQunMemberSendMsgOver proto)
	{
		try
		{
			SRequest_QQQunMemberSendMsgOver obj = new SRequest_QQQunMemberSendMsgOver();
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
	public Request_QQQunMemberSendMsgOver saveToProto()
	{
		Request_QQQunMemberSendMsgOver.Builder _builder_ = Request_QQQunMemberSendMsgOver.newBuilder();
		if (quncode != null && !quncode.equals(Request_QQQunMemberSendMsgOver.getDefaultInstance().getQuncode()))
			_builder_.setQuncode(quncode);
		if (qqcode != null && !qqcode.equals(Request_QQQunMemberSendMsgOver.getDefaultInstance().getQqcode()))
			_builder_.setQqcode(qqcode);
		if (msg != null && !msg.equals(Request_QQQunMemberSendMsgOver.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		if (result != null && !result.equals(Request_QQQunMemberSendMsgOver.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		return _builder_.build();
	}
}
