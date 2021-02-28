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
import com.lys.protobuf.ProtocolTap.Response_QQQunMemberAddModify;

public class SResponse_QQQunMemberAddModify extends SPTData<Response_QQQunMemberAddModify>
{
	private static final SResponse_QQQunMemberAddModify DefaultInstance = new SResponse_QQQunMemberAddModify();


	public static SResponse_QQQunMemberAddModify create()
	{
		SResponse_QQQunMemberAddModify obj = new SResponse_QQQunMemberAddModify();
		return obj;
	}

	public SResponse_QQQunMemberAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_QQQunMemberAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_QQQunMemberAddModify load(String str)
	{
		try
		{
			SResponse_QQQunMemberAddModify obj = new SResponse_QQQunMemberAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberAddModify load(JSONObject json)
	{
		try
		{
			SResponse_QQQunMemberAddModify obj = new SResponse_QQQunMemberAddModify();
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

	public static List<SResponse_QQQunMemberAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_QQQunMemberAddModify> list = new ArrayList<SResponse_QQQunMemberAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_QQQunMemberAddModify item = SResponse_QQQunMemberAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_QQQunMemberAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_QQQunMemberAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_QQQunMemberAddModify _proto_)
	{
	}

	public static SResponse_QQQunMemberAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_QQQunMemberAddModify obj = new SResponse_QQQunMemberAddModify();
			obj.parse(Response_QQQunMemberAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunMemberAddModify load(Response_QQQunMemberAddModify proto)
	{
		try
		{
			SResponse_QQQunMemberAddModify obj = new SResponse_QQQunMemberAddModify();
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
	public Response_QQQunMemberAddModify saveToProto()
	{
		Response_QQQunMemberAddModify.Builder _builder_ = Response_QQQunMemberAddModify.newBuilder();
		return _builder_.build();
	}
}
