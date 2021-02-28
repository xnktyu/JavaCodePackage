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
import com.lys.protobuf.ProtocolTap.TapDevice;

public class STapDevice extends SPTData<TapDevice>
{
	private static final STapDevice DefaultInstance = new STapDevice();

	public String deviceId = null;
	public String activeCode = null;
	public String watchActor = null;
	public String note = null;
	public Integer watchCount = 0;
	public /*STapRole*/ Integer role = TapDevice.getDefaultInstance().getRole().getNumber();
	public String pkgName = null;
	public String channel = null;
	public Integer versionCode = 0;
	public String versionName = null;
	public Integer startupCounter = 0;
	public Integer checkCounter = 0;
	public /*STapActiveState*/ Integer activeState = TapDevice.getDefaultInstance().getActiveState().getNumber();
	public Long tryTime = 0L;
	public Long createTime = 0L;
	public String deviceInfo = null;

	public static STapDevice create(String deviceId, String activeCode, String watchActor, String note, Integer watchCount, Integer role, String pkgName, String channel, Integer versionCode, String versionName, Integer startupCounter, Integer checkCounter, Integer activeState, Long tryTime, Long createTime, String deviceInfo)
	{
		STapDevice obj = new STapDevice();
		obj.deviceId = deviceId;
		obj.activeCode = activeCode;
		obj.watchActor = watchActor;
		obj.note = note;
		obj.watchCount = watchCount;
		obj.role = role;
		obj.pkgName = pkgName;
		obj.channel = channel;
		obj.versionCode = versionCode;
		obj.versionName = versionName;
		obj.startupCounter = startupCounter;
		obj.checkCounter = checkCounter;
		obj.activeState = activeState;
		obj.tryTime = tryTime;
		obj.createTime = createTime;
		obj.deviceInfo = deviceInfo;
		return obj;
	}

	public STapDevice clone()
	{
		return load(saveToBytes());
	}

	public void copyFrom(STapDevice _other_)
	{
		this.deviceId = _other_.deviceId;
		this.activeCode = _other_.activeCode;
		this.watchActor = _other_.watchActor;
		this.note = _other_.note;
		this.watchCount = _other_.watchCount;
		this.role = _other_.role;
		this.pkgName = _other_.pkgName;
		this.channel = _other_.channel;
		this.versionCode = _other_.versionCode;
		this.versionName = _other_.versionName;
		this.startupCounter = _other_.startupCounter;
		this.checkCounter = _other_.checkCounter;
		this.activeState = _other_.activeState;
		this.tryTime = _other_.tryTime;
		this.createTime = _other_.createTime;
		this.deviceInfo = _other_.deviceInfo;
	}

	@Override
	public void parse(JSONObject _json_)
	{
		if (_json_.containsKey("deviceId"))
			deviceId = _json_.getString("deviceId");
		if (_json_.containsKey("activeCode"))
			activeCode = _json_.getString("activeCode");
		if (_json_.containsKey("watchActor"))
			watchActor = _json_.getString("watchActor");
		if (_json_.containsKey("note"))
			note = _json_.getString("note");
		if (_json_.containsKey("watchCount"))
			watchCount = _json_.getInteger("watchCount");
		if (_json_.containsKey("role"))
			role = _json_.getInteger("role");
		if (_json_.containsKey("pkgName"))
			pkgName = _json_.getString("pkgName");
		if (_json_.containsKey("channel"))
			channel = _json_.getString("channel");
		if (_json_.containsKey("versionCode"))
			versionCode = _json_.getInteger("versionCode");
		if (_json_.containsKey("versionName"))
			versionName = _json_.getString("versionName");
		if (_json_.containsKey("startupCounter"))
			startupCounter = _json_.getInteger("startupCounter");
		if (_json_.containsKey("checkCounter"))
			checkCounter = _json_.getInteger("checkCounter");
		if (_json_.containsKey("activeState"))
			activeState = _json_.getInteger("activeState");
		if (_json_.containsKey("tryTime"))
			tryTime = _json_.getLong("tryTime");
		if (_json_.containsKey("createTime"))
			createTime = _json_.getLong("createTime");
		if (_json_.containsKey("deviceInfo"))
			deviceInfo = _json_.getString("deviceInfo");
	}

	public static STapDevice load(String str)
	{
		try
		{
			STapDevice obj = new STapDevice();
			obj.parse(JsonHelper.getJSONObject(str));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STapDevice load(JSONObject json)
	{
		try
		{
			STapDevice obj = new STapDevice();
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
			if (deviceId != null)
				_json_.put("deviceId", deviceId);
			if (activeCode != null)
				_json_.put("activeCode", activeCode);
			if (watchActor != null)
				_json_.put("watchActor", watchActor);
			if (note != null)
				_json_.put("note", note);
			if (watchCount != null)
				_json_.put("watchCount", watchCount);
			if (role != null)
				_json_.put("role", role);
			if (pkgName != null)
				_json_.put("pkgName", pkgName);
			if (channel != null)
				_json_.put("channel", channel);
			if (versionCode != null)
				_json_.put("versionCode", versionCode);
			if (versionName != null)
				_json_.put("versionName", versionName);
			if (startupCounter != null)
				_json_.put("startupCounter", startupCounter);
			if (checkCounter != null)
				_json_.put("checkCounter", checkCounter);
			if (activeState != null)
				_json_.put("activeState", activeState);
			if (tryTime != null)
				_json_.put("tryTime", String.valueOf(tryTime));
			if (createTime != null)
				_json_.put("createTime", String.valueOf(createTime));
			if (deviceInfo != null)
				_json_.put("deviceInfo", deviceInfo);
			return _json_;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static List<STapDevice> loadList(JSONArray ja)
	{
		try
		{
			List<STapDevice> list = new ArrayList<STapDevice>();
			for (int i = 0; i < ja.size(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				STapDevice item = STapDevice.load(jo);
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

	public static JSONArray saveList(List<STapDevice> list)
	{
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++)
		{
			STapDevice item = list.get(i);
			JSONObject jo = item.saveToJson();
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public void parse(TapDevice _proto_)
	{
		if (_proto_.hasDeviceId())
			deviceId = _proto_.getDeviceId();
		if (_proto_.hasActiveCode())
			activeCode = _proto_.getActiveCode();
		if (_proto_.hasWatchActor())
			watchActor = _proto_.getWatchActor();
		if (_proto_.hasNote())
			note = _proto_.getNote();
		if (_proto_.hasWatchCount())
			watchCount = _proto_.getWatchCount();
		if (_proto_.hasRole())
			role = _proto_.getRole().getNumber();
		if (_proto_.hasPkgName())
			pkgName = _proto_.getPkgName();
		if (_proto_.hasChannel())
			channel = _proto_.getChannel();
		if (_proto_.hasVersionCode())
			versionCode = _proto_.getVersionCode();
		if (_proto_.hasVersionName())
			versionName = _proto_.getVersionName();
		if (_proto_.hasStartupCounter())
			startupCounter = _proto_.getStartupCounter();
		if (_proto_.hasCheckCounter())
			checkCounter = _proto_.getCheckCounter();
		if (_proto_.hasActiveState())
			activeState = _proto_.getActiveState().getNumber();
		if (_proto_.hasTryTime())
			tryTime = _proto_.getTryTime();
		if (_proto_.hasCreateTime())
			createTime = _proto_.getCreateTime();
		if (_proto_.hasDeviceInfo())
			deviceInfo = _proto_.getDeviceInfo();
	}

	public static STapDevice load(byte[] bytes)
	{
		try
		{
			STapDevice obj = new STapDevice();
			obj.parse(TapDevice.parseFrom(bytes));
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static STapDevice load(TapDevice proto)
	{
		try
		{
			STapDevice obj = new STapDevice();
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
	public TapDevice saveToProto()
	{
		TapDevice.Builder _builder_ = TapDevice.newBuilder();
		if (deviceId != null && !deviceId.equals(TapDevice.getDefaultInstance().getDeviceId()))
			_builder_.setDeviceId(deviceId);
		if (activeCode != null && !activeCode.equals(TapDevice.getDefaultInstance().getActiveCode()))
			_builder_.setActiveCode(activeCode);
		if (watchActor != null && !watchActor.equals(TapDevice.getDefaultInstance().getWatchActor()))
			_builder_.setWatchActor(watchActor);
		if (note != null && !note.equals(TapDevice.getDefaultInstance().getNote()))
			_builder_.setNote(note);
		if (watchCount != null && !watchCount.equals(TapDevice.getDefaultInstance().getWatchCount()))
			_builder_.setWatchCount(watchCount);
		if (role != null && TapDevice.getDefaultInstance().getRole().getNumber() != role)
			_builder_.setRole(ProtocolTap.TapRole.valueOf(role));
		if (pkgName != null && !pkgName.equals(TapDevice.getDefaultInstance().getPkgName()))
			_builder_.setPkgName(pkgName);
		if (channel != null && !channel.equals(TapDevice.getDefaultInstance().getChannel()))
			_builder_.setChannel(channel);
		if (versionCode != null && !versionCode.equals(TapDevice.getDefaultInstance().getVersionCode()))
			_builder_.setVersionCode(versionCode);
		if (versionName != null && !versionName.equals(TapDevice.getDefaultInstance().getVersionName()))
			_builder_.setVersionName(versionName);
		if (startupCounter != null && !startupCounter.equals(TapDevice.getDefaultInstance().getStartupCounter()))
			_builder_.setStartupCounter(startupCounter);
		if (checkCounter != null && !checkCounter.equals(TapDevice.getDefaultInstance().getCheckCounter()))
			_builder_.setCheckCounter(checkCounter);
		if (activeState != null && TapDevice.getDefaultInstance().getActiveState().getNumber() != activeState)
			_builder_.setActiveState(ProtocolTap.TapActiveState.valueOf(activeState));
		if (tryTime != null && !tryTime.equals(TapDevice.getDefaultInstance().getTryTime()))
			_builder_.setTryTime(tryTime);
		if (createTime != null && !createTime.equals(TapDevice.getDefaultInstance().getCreateTime()))
			_builder_.setCreateTime(createTime);
		if (deviceInfo != null && !deviceInfo.equals(TapDevice.getDefaultInstance().getDeviceInfo()))
			_builder_.setDeviceInfo(deviceInfo);
		return _builder_.build();
	}
}
