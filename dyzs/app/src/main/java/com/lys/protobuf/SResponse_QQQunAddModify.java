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
import com.lys.protobuf.ProtocolTap.Response_QQQunAddModify;

public class SResponse_QQQunAddModify extends SPTData<Response_QQQunAddModify>
{
	private static final SResponse_QQQunAddModify DefaultInstance = new SResponse_QQQunAddModify();


	public static SResponse_QQQunAddModify create()
	{
		SResponse_QQQunAddModify obj = new SResponse_QQQunAddModify();
		return obj;
	}

	public SResponse_QQQunAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_QQQunAddModify _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_QQQunAddModify load(String str)
	{
		try
		{
			SResponse_QQQunAddModify obj = new SResponse_QQQunAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunAddModify load(JSONObject json)
	{
		try
		{
			SResponse_QQQunAddModify obj = new SResponse_QQQunAddModify();
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

	public static List<SResponse_QQQunAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_QQQunAddModify> list = new ArrayList<SResponse_QQQunAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_QQQunAddModify item = SResponse_QQQunAddModify.load(jo);
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

	public static JSONArray saveList(List<SResponse_QQQunAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_QQQunAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_QQQunAddModify _proto_)
	{
	}

	public static SResponse_QQQunAddModify load(byte[] bytes)
	{
		try
		{
			SResponse_QQQunAddModify obj = new SResponse_QQQunAddModify();
			obj.parse(Response_QQQunAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_QQQunAddModify load(Response_QQQunAddModify proto)
	{
		try
		{
			SResponse_QQQunAddModify obj = new SResponse_QQQunAddModify();
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
	public Response_QQQunAddModify saveToProto()
	{
		Response_QQQunAddModify.Builder _builder_ = Response_QQQunAddModify.newBuilder();
		return _builder_.build();
	}
}
