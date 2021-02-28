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
import com.lys.protobuf.ProtocolTap.QQQunMember;

public class SQQQunMember extends SPTData<QQQunMember>
{
	private static final SQQQunMember DefaultInstance = new SQQQunMember();

	public String quncode = null;
	public SQQQun qun = null;
	public String qqcode = null;
	public String name = null;
	public String huoyue = null;
	public Integer zhan = 0;
	public String level = null;
	public Integer levelNum = 0;
	public String info = null;
	public String sex = null;
	public Integer age = 0;
	public String sign = null;
	public String addtime = null;
	public Long addtimeNum = 0L;
	public String qunlevel = null;
	public Integer qunlevelNum = 0;
	public String lastsay = null;
	public Long createTime = 0L;
	public String msg = null;
	public Integer sendMsgCount = 0;

	public static SQQQunMember create(String quncode, SQQQun qun, String qqcode, String name, String huoyue, Integer zhan, String level, Integer levelNum, String info, String sex, Integer age, String sign, String addtime, Long addtimeNum, String qunlevel, Integer qunlevelNum, String lastsay, Long createTime, String msg, Integer sendMsgCount)
	{
		SQQQunMember obj = new SQQQunMember();
		obj.quncode = quncode;
		obj.qun = qun;
		obj.qqcode = qqcode;
		obj.name = name;
		obj.huoyue = huoyue;
		obj.zhan = zhan;
		obj.level = level;
		obj.levelNum = levelNum;
		obj.info = info;
		obj.sex = sex;
		obj.age = age;
		obj.sign = sign;
		obj.addtime = addtime;
		obj.addtimeNum = addtimeNum;
		obj.qunlevel = qunlevel;
		obj.qunlevelNum = qunlevelNum;
		obj.lastsay = lastsay;
		obj.createTime = createTime;
		obj.msg = msg;
		obj.sendMsgCount = sendMsgCount;
		return obj;
	}

	public SQQQunMember clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(SQQQunMember _other_)
	{
		this.quncode = _other_.quncode;
		this.qun = _other_.qun;
		this.qqcode = _other_.qqcode;
		this.name = _other_.name;
		this.huoyue = _other_.huoyue;
		this.zhan = _other_.zhan;
		this.level = _other_.level;
		this.levelNum = _other_.levelNum;
		this.info = _other_.info;
		this.sex = _other_.sex;
		this.age = _other_.age;
		this.sign = _other_.sign;
		this.addtime = _other_.addtime;
		this.addtimeNum = _other_.addtimeNum;
		this.qunlevel = _other_.qunlevel;
		this.qunlevelNum = _other_.qunlevelNum;
		this.lastsay = _other_.lastsay;
		this.createTime = _other_.createTime;
		this.msg = _other_.msg;
		this.sendMsgCount = _other_.sendMsgCount;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("quncode"))
			quncode = _json_.getString("quncode");
		if (_json_.containsKey("qun"))
			qun = SQQQun.load(_json_.getJSONObject("qun"));
		if (_json_.containsKey("qqcode"))
			qqcode = _json_.getString("qqcode");
		if (_json_.containsKey("name"))
			name = _json_.getString("name");
		if (_json_.containsKey("huoyue"))
			huoyue = _json_.getString("huoyue");
		if (_json_.containsKey("zhan"))
			zhan = _json_.getInteger("zhan");
		if (_json_.containsKey("level"))
			level = _json_.getString("level");
		if (_json_.containsKey("levelNum"))
			levelNum = _json_.getInteger("levelNum");
		if (_json_.containsKey("info"))
			info = _json_.getString("info");
		if (_json_.containsKey("sex"))
			sex = _json_.getString("sex");
		if (_json_.containsKey("age"))
			age = _json_.getInteger("age");
		if (_json_.containsKey("sign"))
			sign = _json_.getString("sign");
		if (_json_.containsKey("addtime"))
			addtime = _json_.getString("addtime");
		if (_json_.containsKey("addtimeNum"))
			addtimeNum = _json_.getLong("addtimeNum");
		if (_json_.containsKey("qunlevel"))
			qunlevel = _json_.getString("qunlevel");
		if (_json_.containsKey("qunlevelNum"))
			qunlevelNum = _json_.getInteger("qunlevelNum");
		if (_json_.containsKey("lastsay"))
			lastsay = _json_.getString("lastsay");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("msg"))
			msg = _json_.getString("msg");
		if (_json_.containsKey("sendMsgCount"))
			sendMsgCount = _json_.getInteger("sendMsgCount");
	}

	public static SQQQunMember load(String str)
	{
		try
		{
			SQQQunMember obj = new SQQQunMember();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQunMember load(JSONObject json)
	{
		try
		{
			SQQQunMember obj = new SQQQunMember();
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
			if (qun != null)
				_json_.put("qun", qun.saveToJson());
			if (qqcode != null)
				_json_.put("qqcode", qqcode);
			if (name != null)
				_json_.put("name", name);
			if (huoyue != null)
				_json_.put("huoyue", huoyue);
			if (zhan != null)
				_json_.put("zhan", zhan);
			if (level != null)
				_json_.put("level", level);
			if (levelNum != null)
				_json_.put("levelNum", levelNum);
			if (info != null)
				_json_.put("info", info);
			if (sex != null)
				_json_.put("sex", sex);
			if (age != null)
				_json_.put("age", age);
			if (sign != null)
				_json_.put("sign", sign);
			if (addtime != null)
				_json_.put("addtime", addtime);
			if (addtimeNum != null)
				_json_.put("addtimeNum", String.valueOf(addtimeNum));
			if (qunlevel != null)
				_json_.put("qunlevel", qunlevel);
			if (qunlevelNum != null)
				_json_.put("qunlevelNum", qunlevelNum);
			if (lastsay != null)
				_json_.put("lastsay", lastsay);
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (msg != null)
				_json_.put("msg", msg);
			if (sendMsgCount != null)
				_json_.put("sendMsgCount", sendMsgCount);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<SQQQunMember> loadList(JSONArray ja)
	{
		try
		{
			List<SQQQunMember> list = new ArrayList<SQQQunMember>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				SQQQunMember item = SQQQunMember.load(jo);
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

	public static JSONArray saveList(List<SQQQunMember> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			SQQQunMember item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(QQQunMember _proto_)
	{
		if (_proto_.hasQuncode())
			quncode = _proto_.getQuncode();
		if (_proto_.hasQun())
			qun = SQQQun.load(_proto_.getQun());
		if (_proto_.hasQqcode())
			qqcode = _proto_.getQqcode();
		if (_proto_.hasName())
			name = _proto_.getName();
		if (_proto_.hasHuoyue())
			huoyue = _proto_.getHuoyue();
		if (_proto_.hasZhan())
			zhan = _proto_.getZhan();
		if (_proto_.hasLevel())
			level = _proto_.getLevel();
		if (_proto_.hasLevelNum())
			levelNum = _proto_.getLevelNum();
		if (_proto_.hasInfo())
			info = _proto_.getInfo();
		if (_proto_.hasSex())
			sex = _proto_.getSex();
		if (_proto_.hasAge())
			age = _proto_.getAge();
		if (_proto_.hasSign())
			sign = _proto_.getSign();
		if (_proto_.hasAddtime())
			addtime = _proto_.getAddtime();
		if (_proto_.hasAddtimeNum())
			addtimeNum = _proto_.getAddtimeNum();
		if (_proto_.hasQunlevel())
			qunlevel = _proto_.getQunlevel();
		if (_proto_.hasQunlevelNum())
			qunlevelNum = _proto_.getQunlevelNum();
		if (_proto_.hasLastsay())
			lastsay = _proto_.getLastsay();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasMsg())
			msg = _proto_.getMsg();
		if (_proto_.hasSendMsgCount())
			sendMsgCount = _proto_.getSendMsgCount();
	}

	public static SQQQunMember load(byte[] bytes)
	{
		try
		{
			SQQQunMember obj = new SQQQunMember();
			obj.parse(QQQunMember.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static SQQQunMember load(QQQunMember proto)
	{
		try
		{
			SQQQunMember obj = new SQQQunMember();
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
	public QQQunMember saveToProto()
	{
		QQQunMember.Builder _builder_ = QQQunMember.newBuilder();
		if (quncode != null && !quncode.equals(QQQunMember.getDefaultInstance().getQuncode()))
			_builder_.setQuncode(quncode);
		if (qun != null)
			_builder_.setQun(qun.saveToProto());
		if (qqcode != null && !qqcode.equals(QQQunMember.getDefaultInstance().getQqcode()))
			_builder_.setQqcode(qqcode);
		if (name != null && !name.equals(QQQunMember.getDefaultInstance().getName()))
			_builder_.setName(name);
		if (huoyue != null && !huoyue.equals(QQQunMember.getDefaultInstance().getHuoyue()))
			_builder_.setHuoyue(huoyue);
		if (zhan != null && !zhan.equals(QQQunMember.getDefaultInstance().getZhan()))
			_builder_.setZhan(zhan);
		if (level != null && !level.equals(QQQunMember.getDefaultInstance().getLevel()))
			_builder_.setLevel(level);
		if (levelNum != null && !levelNum.equals(QQQunMember.getDefaultInstance().getLevelNum()))
			_builder_.setLevelNum(levelNum);
		if (info != null && !info.equals(QQQunMember.getDefaultInstance().getInfo()))
			_builder_.setInfo(info);
		if (sex != null && !sex.equals(QQQunMember.getDefaultInstance().getSex()))
			_builder_.setSex(sex);
		if (age != null && !age.equals(QQQunMember.getDefaultInstance().getAge()))
			_builder_.setAge(age);
		if (sign != null && !sign.equals(QQQunMember.getDefaultInstance().getSign()))
			_builder_.setSign(sign);
		if (addtime != null && !addtime.equals(QQQunMember.getDefaultInstance().getAddtime()))
			_builder_.setAddtime(addtime);
		if (addtimeNum != null && !addtimeNum.equals(QQQunMember.getDefaultInstance().getAddtimeNum()))
			_builder_.setAddtimeNum(addtimeNum);
		if (qunlevel != null && !qunlevel.equals(QQQunMember.getDefaultInstance().getQunlevel()))
			_builder_.setQunlevel(qunlevel);
		if (qunlevelNum != null && !qunlevelNum.equals(QQQunMember.getDefaultInstance().getQunlevelNum()))
			_builder_.setQunlevelNum(qunlevelNum);
		if (lastsay != null && !lastsay.equals(QQQunMember.getDefaultInstance().getLastsay()))
			_builder_.setLastsay(lastsay);
		if (createTime != null && !createTime.equals(QQQunMember.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (msg != null && !msg.equals(QQQunMember.getDefaultInstance().getMsg()))
			_builder_.setMsg(msg);
		if (sendMsgCount != null && !sendMsgCount.equals(QQQunMember.getDefaultInstance().getSendMsgCount()))
			_builder_.setSendMsgCount(sendMsgCount);
		return _builder_.build();
	}
}
