package com.viewscenes.util.report.FChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;


public class SetBean {
	private String label=null;		//设置在图表中体现出来的名字
	private String value=null; 		//设置在图表中各个名字想对应的值
	private String color=null; 		//设置在图表中相对应的柱行图的颜色
	private String hoverText=null; 	//设置鼠标旋停在相对应的柱行图 上出现的文本内容
	private String link=null; 		//设置该柱行图的链接地址（需要URL Encode重编码）
	private String alpha=null; 		//设置在图表中相对应的柱行图的透明度
	private String showName=null; 	//设置在是否显示图表中相对应的柱行图的name
	private String dataset=null; 	//多轴图上使用
	
	public static ArrayList colorlist =new ArrayList();
	static {
		colorlist.add("AFD8F8");
		colorlist.add("F6BD0F");
		colorlist.add("8BBA00");
		colorlist.add("FF8E46");
		colorlist.add("008E8E");
		colorlist.add("D64646");
		colorlist.add("8E468E");
		colorlist.add("588526");
		colorlist.add("B3AA00");
		colorlist.add("008ED6");
		colorlist.add("9D080D");
		colorlist.add("A186BE");

	}
	/**
	 * 取得在图表中体现出来的名字
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * 设置在图表中体现出来的名字
	 * @param label String
	 * 例: 周一
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * 取得在图表中各个名字想对应的值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置在图表中各个名字想对应的值
	 * @param value String
	 * 例: 98.2319
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 取得在图表中相对应的柱行图的颜色
	 */
	public String getColor() {
		return color;
	}
	/**
	 * 设置在图表中相对应的柱行图的颜色
	 * @param color HexCode
	 * 例: 000000
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * 取得鼠标旋停在相对应的柱行图 上出现的文本内容
	 */
	public String getHoverText() {
		return hoverText;
	}
	/**
	 * 设置鼠标旋停在相对应的柱行图 上出现的文本内容
	 * @param hoverText String
	 * 例: 新疆地区
	 */
	public void setHoverText(String hoverText) {
		this.hoverText = hoverText;
	}
	/**
	 * 取得该柱行图的链接地址
	 */
	public String getLink() {
		return link;
	}
	/**
	 * 设置该柱行图的链接地址（需要URL Encode重编码）
	 * @param link String
	 * 例: /%BD%F0%CA%AF%CD%FE%CA%D3.html (/金石威视.html)
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * 取得在图表中相对应的柱行图的透明度
	 */
	public String getAlpha() {
		return alpha;
	}
	/**
	 * 设置在图表中相对应的柱行图的透明度
	 * @param alpha Numerical Value
	 * 例: 0~100
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	/**
	 * 取得在是否显示图表中相对应的柱行图的name
	 */
	public String getShowName() {
		return showName;
	}
	/**
	 * 设置在是否显示图表中相对应的柱行图的name
	 * @param showName boolean
	 * 例: 1/0
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	
	
	
}
