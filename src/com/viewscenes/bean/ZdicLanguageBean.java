package com.viewscenes.bean;

/**
 * 语言字典表对象类
 * @author thinkpad
 *
 */
public class ZdicLanguageBean extends BaseBean {
	private String language_id;
	private String language_name;
	private String is_delete;

	public String getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(String languageId) {
		language_id = languageId;
	}

	public String getLanguage_name() {
		return language_name;
	}

	public void setLanguage_name(String languageName) {
		language_name = languageName;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String isDelete) {
		is_delete = isDelete;
	}

}
