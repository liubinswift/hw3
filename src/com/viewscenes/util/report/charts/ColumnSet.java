package com.viewscenes.util.report.charts;

/**
 * <p>2D柱状图<Column2D>,3D柱状图<Column3D>数据源<set> </p>
 * @author 张士鑫
 * @version 1.0
 */
public class ColumnSet {
	
	
	private String label   = null;			//设置数据节点的标签
	private String value    = null;			//设置数据节点的值
	private String displayValue	= null;		//设置自定义数据节点的值
	private String color    = null;			//设置数据节点的颜色
	private String link    = null;			//设置数据节点的链接
	private String toolText    = null;		//设置数据节点的文本
	private String showLabel    = null;		//设置是否显示数据节点的标签
	private String showValue    = null;		//设置是否显示数据节点的值
	private String dashed   = null;			//设置数据节点的边界是否用虚线
	private String alpha    = null;			//设置数据节点的透明度
	
	
	

	/**
	 * 生成图表的数据节点属性XML
	 * @return 图表的数据节点属性XML
	 */
	public String BuildSetXML(){
		StringBuffer setXML = new StringBuffer();
		setXML.append("<set ");
		
		
		//设置标签的名称
		if(this.getLabel()!=null){
			setXML.append(" label = '" +this.label + "' \t");
		}
		//设置标签的值
		if(this.getValue()!=null){
			setXML.append(" value = '" +this.value + "' \t");
		}
		//设置显示自定义的值
		if(this.getDisplayValue()!=null){
			setXML.append(" displayValue = '" +this.displayValue + "' \t");
		}
		//设置标签的颜色
		if(this.getColor()!=null){
			setXML.append(" color = '" +this.color + "' \t");
		}
		//设置标签的链接
		if(this.getLink()!=null){
			setXML.append(" link = '" +this.link + "' \t");
		}
		//设置显示标签的文本
		if(this.getToolText()!=null){
			setXML.append(" toolText = '" +this.toolText + "' \t");
		}
		//设置是否显示标签
		if(this.getShowLabel()!=null){
			setXML.append(" showLabel = '" +this.showLabel + "' \t");
		}
		//设置是否显示值
		if(this.getShowValue()!=null){
			setXML.append(" showValue = '" +this.showValue + "' \t");
		}
		//设置数据条边界是否用虚线
		if(this.getDashed()!=null){
			setXML.append(" dashed = '" +this.dashed + "' \t");
		}
		//设置标签的透明度
		if(this.getAlpha()!=null){
			setXML.append(" alpha = '" +this.alpha + "' \t");
		}
		
		
		
		
		setXML.append("/> \n");
		return setXML.toString();
	}
	
	
	
	/**
	 * 获取数据节点的标签
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * 设置数据节点的标签
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * 获取设置数据节点的值
	 * @return
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置设置数据节点的值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取自定义数据节点的值
	 * @return
	 */
	public String getDisplayValue() {
		return displayValue;
	}
	/**
	 * 设置自定义数据节点的值
	 * @param displayValue
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	/**
	 * 获取数据节点的颜色
	 * @return
	 */
	public String getColor() {
		return color;
	}
	/**
	 * 设置数据节点的颜色
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * 获取数据节点的链接
	 * @return
	 */
	public String getLink() {
		return link;
	}
	/**
	 * 设置数据节点的链接
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * 获取数据节点的文本
	 * @return
	 */
	public String getToolText() {
		return toolText;
	}
	/**
	 * 设置数据节点的文本
	 * @param toolText
	 */
	public void setToolText(String toolText) {
		this.toolText = toolText;
	}
	/**
	 * 获取是否显示数据节点的标签
	 * @return
	 */
	public String getShowLabel() {
		return showLabel;
	}
	/**
	 * 设置是否显示数据节点的标签
	 * @param showLabel
	 */
	public void setShowLabel(String showLabel) {
		this.showLabel = showLabel;
	}
	/**
	 * 获取是否显示数据节点的值
	 * @return
	 */
	public String getShowValue() {
		return showValue;
	}
	/**
	 * 设置是否显示数据节点的值
	 * @param showValue
	 */
	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}
	/**
	 * 获取数据节点的边界是否用虚线
	 * @return
	 */
	public String getDashed() {
		return dashed;
	}
	/**
	 * 设置数据节点的边界是否用虚线
	 * @param dashed
	 */
	public void setDashed(String dashed) {
		this.dashed = dashed;
	}
	/**
	 * 获取数据节点的透明度
	 * @return
	 */
	public String getAlpha() {
		return alpha;
	}
	/**
	 * 设置数据节点的透明度
	 * @param alpha
	 */
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
}
