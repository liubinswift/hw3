package com.viewscenes.device.radio;

import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 站点升级接口
 * @author Administrator
 *
 */
public class MsgUpgradeEquipmentCmd extends BaseMessageCommand {
	private String url = "";
	private String username = "";
	private String password = "";
	
	public MsgUpgradeEquipmentCmd(){
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}
	
	public void setParams(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {

		}

		protected MessageElement buildBody() {
			Map attrs = new LinkedHashMap(3);
			attrs.put("URL", MsgUpgradeEquipmentCmd.this.url);
			attrs.put("Username", MsgUpgradeEquipmentCmd.this.username);
			attrs.put("Password", MsgUpgradeEquipmentCmd.this.password);
			return new MessageElement("UpgradeEquipment", attrs, null);
		}
	}
}
