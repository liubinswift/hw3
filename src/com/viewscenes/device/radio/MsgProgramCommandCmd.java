package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:设备站点命令类。功能如：版本查询，设备重启，校时，查询程序状态。
 * </p>
 * 
 * <p>
 * Description: 支持v8版本。
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Viewscenes
 * </p>
 * 
 * @author 刘斌
 * @version 1.0
 */
public class MsgProgramCommandCmd extends BaseMessageCommand {
	private Collection urls;
	private String type;
	private String comName;
	private int v_nReviseTime;
	private String programInfoQuery;
	private String command;
	private String reviseTime;
	private String aliveQuery;

	public MsgProgramCommandCmd() {
		this.v_nReviseTime = 0;
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setParams(String type, Collection urls) {
		this.type = type;
		this.urls = urls;
	}

	public void setAllAttr(String programInfoQuery, String command,
			String reviseTime, String aliveQuery) {
		this.programInfoQuery = programInfoQuery;
		this.command = command;
		this.reviseTime = reviseTime;
		this.aliveQuery = aliveQuery;
	}

	public void setCommandName(String name) {
		this.comName = name;
		this.command = name;
	}

	public void setAliveQuery(String name) {
		this.aliveQuery = name;
	}

	/** @deprecated */
	public void setCommandName(int p_nReviseTime) {
		this.v_nReviseTime = p_nReviseTime;
		this.reviseTime = String.valueOf(p_nReviseTime);
	}


	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			List list = new ArrayList();
			if (command != null) {
				Map attrs = new LinkedHashMap();
				attrs.put("Name", command);
				list.add(new MessageElement("Command", attrs, null));
			}
			if (reviseTime != null) {
				list.add(new MessageElement("ReviseTime", null, null));
			}
			if (programInfoQuery != null) {
				list.add(new MessageElement("ProgramInfoQuery", null, null));
			}
			if (aliveQuery != null) {
				list.add(new MessageElement("AliveQuery", null, null));
			}

			return new MessageElement("ProgramCommand", null, list);
		}
	}

}
