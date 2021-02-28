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
import com.lys.protobuf.ProtocolTap.Request_QQQunAddModify;

// ---------------------- xxxxxxxxxxxxx --------------------------
public class SRequest_QQQunAddModify extends SPTData<Request_QQQunAddModify>
{
	private static final SRequest_QQQunAddModify DefaultInstance = new SRequest_QQQunAddModify();

	public SQQQun qun = null;

	public static SRequest_QQQunAddModify create(SQQQun qun)
	{
		SRequest_QQQunAddModify obj = new SRequest_QQQunAddModify();
		obj.qun = qun;
		return obj;
	}

	public SRequest_QQQunAddModify clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SRequest_QQQunAddModify _other_)
	{
		this.qun = _other_.qun;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("qun"))
			qun = SQQQun.load(_json_.getJSONObject("qun"));
	}

	public static SRequest_QQQunAddModify load(String str)
	{
		try
		{
			SRequest_QQQunAddModify obj = new SRequest_QQQunAddModify();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunAddModify load(JSONObject json)
	{
		try
		{
			SRequest_QQQunAddModify obj = new SRequest_QQQunAddModify();
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
			if (qun != null)
				_json_.put("qun", qun.saveToJson());
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SRequest_QQQunAddModify> loadList(JSONArray ja)
	{
		try
		{
			List<SRequest_QQQunAddModify> list = new ArrayList<SRequest_QQQunAddModify>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SRequest_QQQunAddModify item = SRequest_QQQunAddModify.load(jo);
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

	public static JSONArray saveList(List<SRequest_QQQunAddModify> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SRequest_QQQunAddModify item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Request_QQQunAddModify _proto_)
	{
		if (_proto_.hasQun())
			qun = SQQQun.load(_proto_.getQun());
	}

	public static SRequest_QQQunAddModify load(byte[] bytes)
	{
		try
		{
			SRequest_QQQunAddModify obj = new SRequest_QQQunAddModify();
			obj.parse(Request_QQQunAddModify.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SRequest_QQQunAddModify load(Request_QQQunAddModify proto)
	{
		try
		{
			SRequest_QQQunAddModify obj = new SRequest_QQQunAddModify();
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
	public Request_QQQunAddModify saveToProto()
	{
		Request_QQQunAddModify.Builder _builder_ = Request_QQQunAddModify.newBuilder();
		if (qun != null)
			_builder_.setQun(qun.saveToProto());
		return _builder_.build();
	}
}
