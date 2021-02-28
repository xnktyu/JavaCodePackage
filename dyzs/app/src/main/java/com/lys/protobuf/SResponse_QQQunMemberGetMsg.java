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
import com.lys.protobuf.ProtocolTap.Response_QQQunMemberGetMsg;

public class SResponse_QQQunMemberGetMsg extends SPTData<Response_QQQunMemberGetMsg>
{
	private static final SResponse_QQQunMemberGetMsg DefaultInstance = new SResponse_QQQunMemberGetMsg();

	public SQQQunMember member = null;

	public static SResponse_QQQunMemberGetMsg create(SQQQunMember member)
	{
		SResponse_QQQunMemberGetMsg obj = new SResponse_QQQunMemberGetMsg();
		obj.member = member;
		return obj;
	}

	public SResponse_QQQunMemberGetMsg clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_QQQunMemberGetMsg _other_)
	{
		this.member = _other_.member;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("member"))
			member = SQQQunMember.load(_json_.getJSONObject("member"));
	}

	public static SResponse_QQQunMemberGetMsg load(String str)
	{
		try
		{
			SResponse_QQQunMemberGetMsg obj = new SResponse_QQQunMemberGetMsg();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberGetMsg load(JSONObject json)
	{
		try
		{
			SResponse_QQQunMemberGetMsg obj = new SResponse_QQQunMemberGetMsg();
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
			if (member != null)
				_json_.put("member", member.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SResponse_QQQunMemberGetMsg> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_QQQunMemberGetMsg> list = new ArrayList<SResponse_QQQunMemberGetMsg>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_QQQunMemberGetMsg item = SResponse_QQQunMemberGetMsg.load(jo);
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

	public static JSONArray saveList(List<SResponse_QQQunMemberGetMsg> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_QQQunMemberGetMsg item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_QQQunMemberGetMsg _proto_)
	{
		if (_proto_.hasMember())
			member = SQQQunMember.load(_proto_.getMember());
	}

	public static SResponse_QQQunMemberGetMsg load(byte[] bytes)
	{
		try
		{
			SResponse_QQQunMemberGetMsg obj = new SResponse_QQQunMemberGetMsg();
			obj.parse(Response_QQQunMemberGetMsg.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberGetMsg load(Response_QQQunMemberGetMsg proto)
	{
		try
		{
			SResponse_QQQunMemberGetMsg obj = new SResponse_QQQunMemberGetMsg();
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
	public Response_QQQunMemberGetMsg saveToProto()
	{
		Response_QQQunMemberGetMsg.Builder _builder_ = Response_QQQunMemberGetMsg.newBuilder();
		if (member != null)
			_builder_.setMember(member.saveToProto());
		return _builder_.build();
	}
}
