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
import com.lys.protobuf.ProtocolTap.Request_QQQunMemberAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_QQQunMemberAddModify extends SPTData<Request_QQQunMemberAddModify>
{
	private static final SRequest_QQQunMemberAddModify DefaultInstance = new SRequest_QQQunMemberAddModify();

	public SQQQunMember member = null;

	public static SRequest_QQQunMemberAddModify create(SQQQunMember member)
	{
		SRequest_QQQunMemberAddModify obj = new SRequest_QQQunMemberAddModify();
		obj.member = member;
		return obj;
	}

	public SRequest_QQQunMemberAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_QQQunMemberAddModify _other_)
	{
		this.member = _other_.member;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("member"))
			member = SQQQunMember.load(_json_.getJSONObject("member"));
	}

	public static SRequest_QQQunMemberAddModify load(String str)
	{
		try
		{
			SRequest_QQQunMemberAddModify obj = new SRequest_QQQunMemberAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberAddModify load(JSONObject json)
	{
		try
		{
			SRequest_QQQunMemberAddModify obj = new SRequest_QQQunMemberAddModify();
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

	public static List<SRequest_QQQunMemberAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_QQQunMemberAddModify> list = new ArrayList<SRequest_QQQunMemberAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_QQQunMemberAddModify item = SRequest_QQQunMemberAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_QQQunMemberAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_QQQunMemberAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_QQQunMemberAddModify _proto_)
	{
		if (_proto_.hasMember())
			member = SQQQunMember.load(_proto_.getMember());
	}

	public static SRequest_QQQunMemberAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_QQQunMemberAddModify obj = new SRequest_QQQunMemberAddModify();
			obj.parse(Request_QQQunMemberAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunMemberAddModify load(Request_QQQunMemberAddModify proto)
	{
		try
		{
			SRequest_QQQunMemberAddModify obj = new SRequest_QQQunMemberAddModify();
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
	public Request_QQQunMemberAddModify saveToProto()
	{
		Request_QQQunMemberAddModify.Builder _builder_ = Request_QQQunMemberAddModify.newBuilder();
		if (member != null)
			_builder_.setMember(member.saveToProto());
		return _builder_.build();
	}
}
