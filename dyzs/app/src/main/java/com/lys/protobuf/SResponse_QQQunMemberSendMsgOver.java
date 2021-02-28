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
import com.lys.protobuf.ProtocolTap.Response_QQQunMemberSendMsgOver;

public class SResponse_QQQunMemberSendMsgOver extends SPTData<Response_QQQunMemberSendMsgOver>
{
	private static final SResponse_QQQunMemberSendMsgOver DefaultInstance = new SResponse_QQQunMemberSendMsgOver();


	public static SResponse_QQQunMemberSendMsgOver create()
	{
		SResponse_QQQunMemberSendMsgOver obj = new SResponse_QQQunMemberSendMsgOver();
		return obj;
	}

	public SResponse_QQQunMemberSendMsgOver clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_QQQunMemberSendMsgOver _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_QQQunMemberSendMsgOver load(String str)
	{
		try
		{
			SResponse_QQQunMemberSendMsgOver obj = new SResponse_QQQunMemberSendMsgOver();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberSendMsgOver load(JSONObject json)
	{
		try
		{
			SResponse_QQQunMemberSendMsgOver obj = new SResponse_QQQunMemberSendMsgOver();
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
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_QQQunMemberSendMsgOver> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_QQQunMemberSendMsgOver> list = new ArrayList<SResponse_QQQunMemberSendMsgOver>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_QQQunMemberSendMsgOver item = SResponse_QQQunMemberSendMsgOver.load(jo);
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

	public static JSONArray saveList(List<SResponse_QQQunMemberSendMsgOver> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_QQQunMemberSendMsgOver item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_QQQunMemberSendMsgOver _proto_)
	{
	}

	public static SResponse_QQQunMemberSendMsgOver load(byte[] bytes)
	{
		try
		{
			SResponse_QQQunMemberSendMsgOver obj = new SResponse_QQQunMemberSendMsgOver();
			obj.parse(Response_QQQunMemberSendMsgOver.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberSendMsgOver load(Response_QQQunMemberSendMsgOver proto)
	{
		try
		{
			SResponse_QQQunMemberSendMsgOver obj = new SResponse_QQQunMemberSendMsgOver();
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
	public Response_QQQunMemberSendMsgOver saveToProto()
	{
		Response_QQQunMemberSendMsgOver.Builder _builder_ = Response_QQQunMemberSendMsgOver.newBuilder();
		return _builder_.build();
	}
}
