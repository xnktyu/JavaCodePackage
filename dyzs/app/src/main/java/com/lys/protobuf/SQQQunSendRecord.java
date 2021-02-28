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
import com.lys.protobuf.ProtocolTap.QQQunSendRecord;

public class SQQQunSendRecord extends SPTData<QQQunSendRecord>
{
	private static final SQQQunSendRecord DefaultInstance = new SQQQunSendRecord();

	public String quncode = null;
	public String qqcode = null;
	public String msg = null;
	public Integer result = 0; // 1:success  2:未找到，可能已退群  3:消息已经发送过  4:未找到发送按钮

	public static SQQQunSendRecord create(String quncode, String qqcode, String msg, Integer result)
	{
		SQQQunSendRecord obj = new SQQQunSendRecord();
		obj.quncode = quncode;
		obj.qqcode = qqcode;
		obj.msg = msg;
		obj.result = result;
		return obj;
	}

	public SQQQunSendRecord clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SQQQunSendRecord _other_)
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

	public static SQQQunSendRecord load(String str)
	{
		try
		{
			SQQQunSendRecord obj = new SQQQunSendRecord();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQunSendRecord load(JSONObject json)
	{
		try
		{
			SQQQunSendRecord obj = new SQQQunSendRecord();
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

	public static List<SQQQunSendRecord> loadList(JSONArray ja)
	{
		try
		{
			List<SQQQunSendRecord> list = new ArrayList<SQQQunSendRecord>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SQQQunSendRecord item = SQQQunSendRecord.load(jo);
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

	public static JSONArray saveList(List<SQQQunSendRecord> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SQQQunSendRecord item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(QQQunSendRecord _proto_)
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

	public static SQQQunSendRecord load(byte[] bytes)
	{
		try
		{
			SQQQunSendRecord obj = new SQQQunSendRecord();
			obj.parse(QQQunSendRecord.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQunSendRecord load(QQQunSendRecord proto)
	{
		try
		{
			SQQQunSendRecord obj = new SQQQunSendRecord();
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
	public QQQunSendRecord saveToProto()
	{
		QQQunSendRecord.Builder _builder_ = QQQunSendRecord.newBuilder();
		if (quncode != null && !quncode.equals(QQQunSendRecord.getDefaultInstance().getQuncode()))
			_builder_.setQuncode(quncode);
		if (qqcode != null && !qqcode.equals(QQQunSendRecord.getDefaultInstance().getQqcode()))
			_builder_.setQqcode(qqcode);
		if (msg != null && !msg.equals(QQQunSendRecord.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		if (result != null && !result.equals(QQQunSendRecord.getDefaultInstance().getResult()))
			_builder_.setResult(result);
		return _builder_.build();
	}
}
