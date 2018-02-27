package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

public class MsgEquipmentAlarmParamSetCmd extends BaseMessageCommand {
	private String _batteryLevelDownThreshold;
	private String _shutdownDelayTime;
	private String _inputLineLevelDownThreshold;
	private String _UPSStatus;
	private String _levelAbnormityLength;
	private String _inputLineLevelUpThreshold;
	private String _equAbnormityLength;
	private String _amCardAbnormityLength;
	private String _voiceCardAbnormityLength;
	private String _fmCardAbnormityLength;
	private String _offsetAbnormityLength;
	private String _DSCardAbnormityLength;
	private String _YsAbnormityLength;

	public MsgEquipmentAlarmParamSetCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setLevelParam(String batteryLevelDownThreshold,
			String shutdownDelayTime, String inputLineLevelUpThreshold,
			String inputLineLevelDownThreshold, String UPSStatus,
			String levelAbnormityLength) {
		this._batteryLevelDownThreshold = batteryLevelDownThreshold;
		this._shutdownDelayTime = shutdownDelayTime;
		this._inputLineLevelDownThreshold = inputLineLevelDownThreshold;
		this._inputLineLevelUpThreshold = inputLineLevelUpThreshold;
		this._UPSStatus = UPSStatus;
		this._levelAbnormityLength = levelAbnormityLength;
	}

	public void setEquParam(String abnormityLength) {
		this._equAbnormityLength = abnormityLength;
	}

	public void setAmCardParam(String abnormityLength) {
		this._amCardAbnormityLength = abnormityLength;
	}

	public void setFmCardParam(String abnormityLength) {
		this._fmCardAbnormityLength = abnormityLength;
	}

	public void setVoiceCardParam(String abnormityLength) {
		this._voiceCardAbnormityLength = abnormityLength;
	}

	public void setOffsetParam(String abnormityLength) {
		this._offsetAbnormityLength = abnormityLength;
	}

	public void setDsCardParam(String abnormityLength) {
		this._DSCardAbnormityLength = abnormityLength;
	}

	public void setYsParam(String abnormityLength) {
		this._YsAbnormityLength = abnormityLength;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Collection alarmParams = new ArrayList();
			if (MsgEquipmentAlarmParamSetCmd.this._levelAbnormityLength != null) {
				alarmParams.add(makeLevel());
			}
			if (MsgEquipmentAlarmParamSetCmd.this._equAbnormityLength != null) {
				alarmParams.add(makeEqu());
			}
			if (MsgEquipmentAlarmParamSetCmd.this._amCardAbnormityLength != null) {
				alarmParams.add(makeAmCard());
			}
			if (MsgEquipmentAlarmParamSetCmd.this._voiceCardAbnormityLength != null) {
				alarmParams.add(makeVoiceCard());
			}
			if (MsgEquipmentAlarmParamSetCmd.this._fmCardAbnormityLength != null) {
				alarmParams.add(makeFmCard());
			}
			if (MsgEquipmentAlarmParamSetCmd.this._offsetAbnormityLength != null) {
				alarmParams.add(makeOffset());
			}

			Collection children = new ArrayList();
			children.add(new MessageElement("EquipmentAlarmParam", null,
					alarmParams));
			return new MessageElement("EquipmentAlarmParamSet", null, children);
		}

		protected MessageElement makeLevel() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "InputLineLevelUpThreshold");
			nv
					.put(
							"Value",
							MsgEquipmentAlarmParamSetCmd.this._inputLineLevelUpThreshold);
			params.add(new MessageElement("Param", nv, null));

			nv = new LinkedHashMap(2);
			nv.put("Name", "InputLineLevelDownThreshold");
			nv
					.put(
							"Value",
							MsgEquipmentAlarmParamSetCmd.this._inputLineLevelDownThreshold);
			params.add(new MessageElement("Param", nv, null));

			nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv.put("Value",
					MsgEquipmentAlarmParamSetCmd.this._levelAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "1");
			attrs.put("Desc", "供电异常");
			return new MessageElement("AlarmParam", attrs, params);
		}

		protected MessageElement makeEqu() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv.put("Value",
					MsgEquipmentAlarmParamSetCmd.this._equAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "2");
			attrs.put("Desc", "接收机异常");
			return new MessageElement("AlarmParam", attrs, params);
		}

		protected MessageElement makeOffset() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv.put("Value",
					MsgEquipmentAlarmParamSetCmd.this._offsetAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "6");
			attrs.put("Desc", "频偏卡异常");
			return new MessageElement("AlarmParam", attrs, params);
		}

		protected MessageElement makeAmCard() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv.put("Value",
					MsgEquipmentAlarmParamSetCmd.this._amCardAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "4");
			attrs.put("Desc", "调幅度卡异常报警");
			return new MessageElement("AlarmParam", attrs, params);
		}

		protected MessageElement makeVoiceCard() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv
					.put(
							"Value",
							MsgEquipmentAlarmParamSetCmd.this._voiceCardAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "5");
			attrs.put("Desc", "语音压缩卡异常报警");
			return new MessageElement("AlarmParam", attrs, params);
		}

		protected MessageElement makeFmCard() {
			Collection params = new ArrayList();
			Map nv = new LinkedHashMap(2);
			nv.put("Name", "AbnormityLength");
			nv.put("Value",
					MsgEquipmentAlarmParamSetCmd.this._fmCardAbnormityLength);
			params.add(new MessageElement("Param", nv, null));

			Map attrs = new LinkedHashMap(2);
			attrs.put("Type", "3");
			attrs.put("Desc", "调制度卡异常报警");
			return new MessageElement("AlarmParam", attrs, params);
		}
	}

}
