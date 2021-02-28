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
import com.lys.protobuf.ProtocolTap.QQQun;

public class SQQQun extends SPTData<QQQun>
{
	private static final SQQQun DefaultInstance = new SQQQun();

	public String code = null;
	public String name = null;
	public String level = null;
	public Integer renCount = 0;
	public Integer onlineCount = 0;
	public String des = null;
	public Long createTime = 0L;

	public static SQQQun create(String code, String name, String level, Integer renCount, Integer onlineCount, String des, Long createTime)
	{
		SQQQun obj = new SQQQun();
		obj.code = code;
		obj.name = name;
		obj.level = level;
		obj.renCount = renCount;
		obj.onlineCount = onlineCount;
		obj.des = des;
		obj.createTime = createTime;
		return obj;
	}

	public SQQQun clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SQQQun _other_)
	{
		this.code = _other_.code;
		this.name = _other_.name;
		this.level = _other_.level;
		this.renCount = _other_.renCount;
		this.onlineCount = _other_.onlineCount;
		this.des = _other_.des;
		this.createTime = _other_.createTime;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("code"))
			code = _json_.getString("code");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("level"))
			level = _json_.getString("level");
		if (_json_.containsKey("renCount"))
			renCount = _json_.getInteger("renCount");
		if (_json_.containsKey("onlineCount"))
			onlineCount = _json_.getInteger("onlineCount");
		if (_json_.containsKey("des"))
			des = _json_.getString("des");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
	}

	public static SQQQun load(String str)
	{
		try
		{
			SQQQun obj = new SQQQun();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQun load(JSONObject json)
	{
		try
		{
			SQQQun obj = new SQQQun();
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
			if (code != null)
				_json_.put("code", code);
			if (name != null)
				_json_.put("name", name);
			if (level != null)
				_json_.put("level", level);
			if (renCount != null)
				_json_.put("renCount", renCount);
			if (onlineCount != null)
				_json_.put("onlineCount", onlineCount);
			if (des != null)
				_json_.put("des", des);
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SQQQun> loadList(JSONArray ja)
	{
		try
		{
			List<SQQQun> list = new ArrayList<SQQQun>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SQQQun item = SQQQun.load(jo);
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

	public static JSONArray saveList(List<SQQQun> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SQQQun item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(QQQun _proto_)
	{
		if (_proto_.hasCode())
			code = _proto_.getCode();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasLevel())
			level = _proto_.getLevel();
		if (_proto_.hasRenCount())
			renCount = _proto_.getRenCount();
		if (_proto_.hasOnlineCount())
			onlineCount = _proto_.getOnlineCount();
		if (_proto_.hasDes())
			des = _proto_.getDes();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
	}

	public static SQQQun load(byte[] bytes)
	{
		try
		{
			SQQQun obj = new SQQQun();
			obj.parse(QQQun.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQun load(QQQun proto)
	{
		try
		{
			SQQQun obj = new SQQQun();
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
	public QQQun saveToProto()
	{
		QQQun.Builder _builder_ = QQQun.newBuilder();
		if (code != null && !code.equals(QQQun.getDefaultInstance().getCode()))
			_builder_.setCode(code);
		if (name != null && !name.equals(QQQun.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (level != null && !level.equals(QQQun.getDefaultInstance().getLevel()))
			_builder_.setLevel(level);
		if (renCount != null && !renCount.equals(QQQun.getDefaultInstance().getRenCount()))
			_builder_.setRenCount(renCount);
		if (onlineCount != null && !onlineCount.equals(QQQun.getDefaultInstance().getOnlineCount()))
			_builder_.setOnlineCount(onlineCount);
		if (des != null && !des.equals(QQQun.getDefaultInstance().getDes()))
			_builder_.setDes(des);
		if (createTime != null && !createTime.equals(QQQun.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		return _builder_.build();
	}
}
