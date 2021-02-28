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
import com.lys.protobuf.ProtocolTap.Response_TapAddWatchCount;

public class SResponse_TapAddWatchCount extends SPTData<Response_TapAddWatchCount>
{
	private static final SResponse_TapAddWatchCount DefaultInstance = new SResponse_TapAddWatchCount();


	public static SResponse_TapAddWatchCount create()
	{
		SResponse_TapAddWatchCount obj = new SResponse_TapAddWatchCount();
		return obj;
	}

	public SResponse_TapAddWatchCount clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SResponse_TapAddWatchCount _other_)
	{
	}

	@Override
	public void parse(JSONObject _json_)
	{
	}

	public static SResponse_TapAddWatchCount load(String str)
	{
		try
		{
			SResponse_TapAddWatchCount obj = new SResponse_TapAddWatchCount();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapAddWatchCount load(JSONObject json)
	{
		try
		{
			SResponse_TapAddWatchCount obj = new SResponse_TapAddWatchCount();
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

	public static List<SResponse_TapAddWatchCount> loadList(JSONArray ja)
	{
		try
		{
			List<SResponse_TapAddWatchCount> list = new ArrayList<SResponse_TapAddWatchCount>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SResponse_TapAddWatchCount item = SResponse_TapAddWatchCount.load(jo);
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

	public static JSONArray saveList(List<SResponse_TapAddWatchCount> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SResponse_TapAddWatchCount item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(Response_TapAddWatchCount _proto_)
	{
	}

	public static SResponse_TapAddWatchCount load(byte[] bytes)
	{
		try
		{
			SResponse_TapAddWatchCount obj = new SResponse_TapAddWatchCount();
			obj.parse(Response_TapAddWatchCount.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SResponse_TapAddWatchCount load(Response_TapAddWatchCount proto)
	{
		try
		{
			SResponse_TapAddWatchCount obj = new SResponse_TapAddWatchCount();
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
	public Response_TapAddWatchCount saveToProto()
	{
		Response_TapAddWatchCount.Builder _builder_ = Response_TapAddWatchCount.newBuilder();
		return _builder_.build();
	}
}
