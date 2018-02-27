package com.viewscenes.device.radio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

/**
 * 
 * <p>
 * Title:任务执行状态查询
 * </p>
 * 
 * <p>
 * Description:支持广播网v8版本
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
public class MsgTaskStatusQueryCmd extends BaseMessageCommand {
	private Collection _params;

	public MsgTaskStatusQueryCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setAttrs(Collection taskIDs) {
		this._params = taskIDs;
	}

	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Collection children = new ArrayList(
					MsgTaskStatusQueryCmd.this._params.size());
			for (Iterator pit = MsgTaskStatusQueryCmd.this._params.iterator(); pit
					.hasNext();) {
				Map values = new LinkedHashMap(1);
				values.put("TaskID", pit.next());
				children.add(new MessageElement("Task", values, null));
			}
			return new MessageElement("TaskStatusQuery", null, children);
		}
	}

}
