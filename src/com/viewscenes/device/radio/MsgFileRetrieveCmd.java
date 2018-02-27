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
 * Title:�ļ���ѯ�ӿ�
 * </p>
 * 
 * <p>
 * Description:֧�ֹ㲥v8�汾��
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
 * @author ����
 * @version 1.0
 */
public class MsgFileRetrieveCmd extends BaseMessageCommand {
	private Collection _params;

	public MsgFileRetrieveCmd() {
		setBuilders(new IMessageBuilder[] { new BuilderV8Radio() });
	}

	public void setAttrs(Collection fileNames) {
		this._params = fileNames;
	}


	protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {
		protected BuilderV8Radio() {
		}

		protected MessageElement buildBody() {
			Collection children = new ArrayList(MsgFileRetrieveCmd.this._params
					.size());
			for (Iterator pit = MsgFileRetrieveCmd.this._params.iterator(); pit
					.hasNext();) {
				Map values = new LinkedHashMap(1);
				values.put("Name", pit.next());
				children.add(new MessageElement("File", values, null));
			}
			return new MessageElement("FileRetrieve", null, children);
		}
	}

}
