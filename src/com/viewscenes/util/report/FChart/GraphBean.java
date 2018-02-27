package com.viewscenes.util.report.FChart;

public class GraphBean {
	//flash背景参数：
	private String bgColor=null;
	private String bgAlpha=null;
	private String bgSWF=null;
	private String bgRatio=null;
	private String bgAngle=null;
	
	//图表背景参数：
	private String canvasBgColor=null;
	private String canvasBaseColor=null;
	private String canvasBaseDepth=null;
	private String canvasBgDepth=null;
	private String showCanvasBg=null;
	private String showCanvasBase=null;
	//图表和轴的标题
	private String caption=null; // 图表上方的标题
	private String subCaption= null; // 图表上方的副标题
	private String xAxisName= null; // X轴的名字
	private String yAxisName= null; // y轴的名字
	//图表数量值的限制
	private String yAxisMinValue=null;// y轴最小值
	private String yAxisMaxValue=null;// y轴最大值
	//通用参数
	private String shownames=null; // 设置是否在x轴下显示set里指定的name
	private String showValues=null; // 设置是否在柱型图或饼型图上显示数据的值
	private String showLimits=null; // 设置是否在图表的y轴坐标上显示最大最小的数据值
	private String rotateNames=null; // 设置X轴下的name 是水平显示还是垂直显示
	private String rotateYAxisName=null; // 设置Y轴下的name 是水平显示还是垂直显示
	private String animation=null; // 设置显示是否是动画显示
	private String showBorder=null; 
	
	//字体属性
	private String baseFont=null; // 设置字体样式
	private String baseFontSize=null; // 设置字体大小
	private String baseFontColor=null; // 设置字体颜色
	private String outCnvBaseFont = null; // 设置图表外侧的字体样式
	private String outCnvBaseFontSze=null; // 设置图表外侧的字体大小
	private String outCnvBaseFontColor=null;// 设置图表外侧的字体颜色
	//数字格式选项
	private String numberPrefix=null; // 设置数据值的前缀
	private String numberSuffix=null ;// 设置数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	private String formatNumber=null; // 设置是否格式化数据
	private String formatNumberScale=null; // 设置是否用"K"来代表千，"M"来代表百万
	private String decimalSeparator=null; // 用指定的字符来代替小数点
	private String thousandSeparator=null; // 用指定的字符来代替千位分隔符
	private String decimalPrecision=null; // 设置柱或线上数值的小数位数
	private String divLineDecimalPrecision=null;// 设置y轴数值的小数位数
	private String limitsDecimalPrecision=null ; // 设置y轴的最大最小值的小数位数
	//水平分隔线
	private String numdivlines=null; // 设置水平分隔线的数量
	private String divlinecolor=null; // 设置水平分隔线的颜色
	private String divLineThickness=null; // 设置水平分隔线的宽度
	private String divLineAlpha=null; // 设置水平分隔线的透明度
	private String showDivLineValue=null; // 设置是否显示水平分隔线的数值
	//鼠标旋停参数
	private String showhovercap=null; // 显示是否激活鼠标旋停效果
	private String hoverCapBgColor=null; // 设置鼠标旋停效果的背景颜色
	private String hoverCapBorderColor=null; // 设置鼠标旋停效果的边框颜色
	private String hoverCapSepChar=null; // 设置鼠标旋停后显示的文本中的分隔符号
	//图表边距的设置
	private String chartLeftMargin=null; // 设置图表左边距
	private String chartRightMargin=null; // 设置图表右边距
	private String chartTopMargin=null; // 设置图表上边距
	private String chartBottomMargin=null; // 设置图表下边距
	//Zero Plane
	//The zero plane is a 3D plane that signifies the 0 position on the chart. If there are no negative numbers on the chart, you won’t see a visible zero plane.
	private String zeroPlaneShowBorder=null; // Whether the border of a 3D zero plane would be plotted or not.
	private String zeroPlaneBorderColor=null; // If the border is to be plotted, this attribute sets the border color for the plane.
	private String zeroPlaneColor=null; // The intended color for the zero plane.
	private String zeroPlaneAlpha=null; // The intended transparency for the zero plane. 

	
	/**
	 * 取得flash的背景颜色
	 */
	public String getBgColor() {
		return bgColor;
	}
	/**
	 * 设置flash的背景颜色
	 * @param bgColor HexColorCode
	 * 例: ffffff
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	/**
	 * 取得背景的透明度
	 */
	public String getBgAlpha() {
		return bgAlpha;
	}
	
	/**
	 * 设置背景的透明度
	 * @param bgAlpha Numerical Value
	 * 例: 0~100
	 */
	public void setBgAlpha(String bgAlpha) {
		this.bgAlpha = bgAlpha;
	}
	/**
	 * 取得一个外部的Flash 为flash的背景
	 */
	public String getBgSWF() {
		return bgSWF;
	}
	/**
	 * 设置一个外部的Flash 为flash的背景
	 * @param bgSWF Path of SWF File
	 * 例: C:\demo.swf
	 */
	public void setBgSWF(String bgSWF) {
		this.bgSWF = bgSWF;
	}
	/**
	 * 取得背景色的渐变比率 
	 */
	public String getBgRatio() {
		return bgRatio;
	}
	/**
	 * 设置背景色的渐变比率 
	 * @param bgRatio  Numerical Value
	 * 例: 0~100
	 */
	public void setBgRatio(String bgRatio) {
		this.bgRatio = bgRatio;
	}
	/**
	 * 取得背景色的波动角度
	 */
	public String getBgAngle() {
		return bgAngle;
	}
	/**
	 * 设置背景色的波动角度
	 * @param bgAngle Numerical Value
	 * 例: 0~360
	 */
	public void setBgAngle(String bgAngle) {
		this.bgAngle = bgAngle;
	}
	/**
	 * 取得图表背景的颜色
	 */
	public String getCanvasBgColor() {
		return canvasBgColor;
	}
	/**
	 * 设置图表背景的颜色
	 * @param canvasBgColor HexColorCode
	 * 例: ffffff
	 */
	public void setCanvasBgColor(String canvasBgColor) {
		this.canvasBgColor = canvasBgColor;
	}
	/**
	 * 取得图表基部的颜色
	 */
	public String getCanvasBaseColor() {
		return canvasBaseColor;
	}
	/**
	 * 设置图表基部的颜色
	 * @param canvasBaseColor HexColorCode
	 * 例: ffffff
	 */
	public void setCanvasBaseColor(String canvasBaseColor) {
		this.canvasBaseColor = canvasBaseColor;
	}
	/**
	 * 取得图表背景的深度
	 */
	public String getCanvasBaseDepth() {
		return canvasBaseDepth;
	}
	/**
	 * 设置图表背景的深度
	 * @param canvasBaseDepth Numerical Value
	 * 例: 10
	 */
	public void setCanvasBaseDepth(String canvasBaseDepth) {
		this.canvasBaseDepth = canvasBaseDepth;
	}
	/**
	 * 取得图表背景的深度
	 */
	public String getCanvasBgDepth() {
		return canvasBgDepth;
	}
	/**
	 * 设置图表背景的深度
	 * @param canvasBgDepth Numerical Value
	 * 例: 10
	 */
	public void setCanvasBgDepth(String canvasBgDepth) {
		this.canvasBgDepth = canvasBgDepth;
	}
	/**
	 * 取得是否显示图表背景
	 */
	public String getShowCanvasBg() {
		return showCanvasBg;
	}
	/**
	 * 设置是否显示图表背景
	 * @param showCanvasBg boolean
	 * 例: 1/0
	 */
	public void setShowCanvasBg(String showCanvasBg) {
		this.showCanvasBg = showCanvasBg;
	}
	/**
	 * 取得是否显示图表基部
	 */
	public String getShowCanvasBase() {
		return showCanvasBase;
	}
	/**
	 * 设置是否显示图表基部
	 * @param showCanvasBase boolean
	 * 例: 1/0
	 */
	public void setShowCanvasBase(String showCanvasBase) {
		this.showCanvasBase = showCanvasBase;
	}
	/**
	 * 取得图表上方的标题
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * 设置图表上方的标题
	 * @param caption String
	 * 例: 频次不合格
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * 取得图表上方的副标题
	 */
	public String getSubCaption() {
		return subCaption;
	}
	/**
	 * 设置图表上方的标题
	 * @param subCaption String
	 * 例: 2009-06-01~2009-06-07
	 */
	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}
	/**
	 * 取得X轴的名字
	 */
	public String getXAxisName() {
		return xAxisName;
	}
	/**
	 * 设置X轴的名字
	 * @param axisName String
	 * 例: 日期
	 */
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}
	/**
	 * 取得Y轴的名字
	 */
	public String getYAxisName() {
		return yAxisName;
	}
	/**
	 * 设置取得Y轴的名字
	 * @param axisName
	 */
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}
	/**
	 * 取得Y轴最小值
	 */
	public String getYAxisMinValue() {
		return yAxisMinValue;
	}
	/**
	 * 设置Y轴最小值
	 * @param axisMinValue value
	 * 例: 90
	 */
	public void setYAxisMinValue(String axisMinValue) {
		yAxisMinValue = axisMinValue;
	}
	/**
	 * 取得Y轴最大值
	 */
	public String getYAxisMaxValue() {
		return yAxisMaxValue;
	}
	/**
	 * 设置Y轴最大值
	 * @param axisMaxValue value
	 * 例: 100
	 */
	public void setYAxisMaxValue(String axisMaxValue) {
		yAxisMaxValue = axisMaxValue;
	}
	/**
	 * 取得是否在x轴下显示<set>里指定的name
	 */
	public String getShownames() {
		return shownames;
	}
	/**
	 * 设置是否在x轴下显示<set>里指定的name
	 * @param shownames boolean
	 * 例: 1/0
	 */
	public void setShownames(String shownames) {
		this.shownames = shownames;
	}
	/**
	 * 取得是否在柱型图或饼型图上显示数据的值
	 */
	public String getShowValues() {
		return showValues;
	}
	/**
	 * 设置是否在柱型图或饼型图上显示数据的值
	 * @param showValues boolean
	 * 例: 1/0
	 */
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	/**
	 * 取得是否在图表的y轴坐标上显示最大最小的数据值
	 */
	public String getShowLimits() {
		return showLimits;
	}
	/**
	 * 设置是否在图表的y轴坐标上显示最大最小的数据值
	 * @param showLimits boolean
	 * 例: 1/0
	 */
	public void setShowLimits(String showLimits) {
		this.showLimits = showLimits;
	}
	/**
	 * 取得Y轴的名字 是水平显示还是垂直显示
	 */
	public String getRotateYAxisName() {
		return rotateYAxisName;
	}
	/**
	 * 设置Y轴的名字 是水平显示还是垂直显示
	 * @param rotateNames boolean
	 * 例: 1/0
	 */
	public void setRotateYAxisName(String rotateYAxisName) {
		this.rotateYAxisName = rotateYAxisName;
	}
	/**
	 * 取得x轴下的name 是水平显示还是垂直显示
	 */
	public String getRotateNames() {
		return rotateNames;
	}
	/**
	 * 设置x轴下的name 是水平显示还是垂直显示
	 * @param rotateNames boolean
	 * 例: 1/0
	 */
	public void setRotateNames(String rotateNames) {
		this.rotateNames = rotateNames;
	}
	/**
	 * 取得柱型图的显示是否是动画显示
	 */
	public String getAnimation() {
		return animation;
	}
	/**
	 * 设置柱型图的显示是否是动画显示
	 * @param animation boolean
	 * 例: 1/0
	 */
	public void setAnimation(String animation) {
		this.animation = animation;
	}
	/**
	 * 取得边框大小
	 */
	public String getShowBorder() {
		return showBorder;
	}
	/**
	 * 设置边框大小
	 * @param showBorder Numerical Value
	 * 例: 1~10
	 */
	public void setShowBorder(String showBorder) {
		this.showBorder = showBorder;
	}
	/**
	 * 取得字体样式
	 */
	public String getBaseFont() {
		return baseFont;
	}
	/**
	 * 设置字体样式
	 * @param baseFont FontName
	 * 例: 宋体
	 */
	public void setBaseFont(String baseFont) {
		this.baseFont = baseFont;
	}
	/**
	 * 取得字体大小
	 */
	public String getBaseFontSize() {
		return baseFontSize;
	}
	/**
	 * 设置字体大小
	 * @param baseFontSize FontSize
	 * 例: 14
	 */
	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}
	/**
	 * 取得字体颜色
	 */
	public String getBaseFontColor() {
		return baseFontColor;
	}
	/**
	 * 设置字体颜色
	 * @param baseFontColor HexColorCode
	 * 例: 000000
	 */
	public void setBaseFontColor(String baseFontColor) {
		this.baseFontColor = baseFontColor;
	}
	/**
	 * 取得图表外侧的字体样式
	 */
	public String getOutCnvBaseFont() {
		return outCnvBaseFont;
	}
	/**
	 * 设置图表外侧的字体样式
	 * @param outCnvBaseFont FontName
	 * 例: 宋体
	 */
	public void setOutCnvBaseFont(String outCnvBaseFont) {
		this.outCnvBaseFont = outCnvBaseFont;
	}
	/**
	 *  取得图表外侧的字体大小
	 */
	public String getOutCnvBaseFontSze() {
		return outCnvBaseFontSze;
	}
	/**
	 *  设置图表外侧的字体大小
	 * @param outCnvBaseFontSze FontSize
	 * 例: 14
	 */
	public void setOutCnvBaseFontSze(String outCnvBaseFontSze) {
		this.outCnvBaseFontSze = outCnvBaseFontSze;
	}
	/**
	 * 取得图表外侧的字体颜色
	 */
	public String getOutCnvBaseFontColor() {
		return outCnvBaseFontColor;
	}
	/**
	 * 设置图表外侧的字体颜色
	 * @param outCnvBaseFontColor HexColorCode
	 * 例: 000000
	 */
	public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
		this.outCnvBaseFontColor = outCnvBaseFontColor;
	}
	/**
	 * 取得数据值的前缀
	 */
	public String getNumberPrefix() {
		return numberPrefix;
	}
	/**
	 * 设置数据值的前缀
	 * @param numberPrefix Sting 
	 * 例: ￥
	 */
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}
	/**
	 * 取得数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	 */
	public String getNumberSuffix() {
		return numberSuffix;
	}
	/**
	 * 设置数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	 * @param numberSuffix String
	 * 例: %BD%F0%CA%AF%CD%FE%CA%D3 (金石威视)
	 */
	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}
	/**
	 * 取得是否格式化数据
	 */
	public String getFormatNumber() {
		return formatNumber;
	}
	/**
	 * 设置是否格式化数据
	 * @param formatNumber boolean
	 * 例: 1/0
	 */
	public void setFormatNumber(String formatNumber) {
		this.formatNumber = formatNumber;
	}
	/**
	 * 取得是否用“K”来代表千，“M”来代表百万
	 */
	public String getFormatNumberScale() {
		return formatNumberScale;
	}
	/**
	 * 设置是否用“K”来代表千，“M”来代表百万
	 * @param formatNumberScale boolean
	 * 例: 1/0
	 */
	public void setFormatNumberScale(String formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}
	/**
	 * 取得用指定的字符来代替小数点
	 */
	public String getDecimalSeparator() {
		return decimalSeparator;
	}
	/**
	 * 设置用指定的字符来代替小数点
	 * @param decimalSeparator String
	 * 例: .
	 */
	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}
	/**
	 * 用指定的字符来代替千位分隔符
	 */
	public String getThousandSeparator() {
		return thousandSeparator;
	}
	/**
	 * 设置用指定的字符来代替千位分隔符
	 * @param thousandSeparator String
	 * 例: ,
	 */
	public void setThousandSeparator(String thousandSeparator) {
		this.thousandSeparator = thousandSeparator;
	}
	/**
	 * 取得设置柱或线上数值的小数位数
	 */
	public String getDecimalPrecision() {
		return decimalPrecision;
	}
	/**
	 * 设置柱或线上数值的小数位数
	 * @param decimalPrecision String
	 * 例: 2
	 */
	public void setDecimalPrecision(String decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}
	/**
	 * 取得y轴数值的小数位数
	 */
	public String getDivLineDecimalPrecision() {
		return divLineDecimalPrecision;
	}
	/**
	 * 设置y轴数值的小数位数
	 * @param divLineDecimalPrecision String
	 * 例: 0
	 */
	public void setDivLineDecimalPrecision(String divLineDecimalPrecision) {
		this.divLineDecimalPrecision = divLineDecimalPrecision;
	}
	/**
	 * 取得y轴的最大最小值的小数位数
	 */
	public String getLimitsDecimalPrecision() {
		return limitsDecimalPrecision;
	}
	/**
	 * 设置y轴的最大最小值的小数位数
	 * @param limitsDecimalPrecision String
	 * 例: 0
	 */
	public void setLimitsDecimalPrecision(String limitsDecimalPrecision) {
		this.limitsDecimalPrecision = limitsDecimalPrecision;
	}
	/**
	 * 取得水平分隔线的数量
	 */
	public String getNumdivlines() {
		return numdivlines;
	}
	/**
	 * 设置水平分隔线的数量
	 * @param numdivlines String 
	 * 例: 5
	 */
	public void setNumdivlines(String numdivlines) {
		this.numdivlines = numdivlines;
	}
	/**
	 * 取得水平分隔线的颜色
	 */
	public String getDivlinecolor() {
		return divlinecolor;
	}
	/**
	 * 设置水平分隔线的颜色
	 * @param divlinecolor HexColorCode
	 * 例: ffffff
	 */
	public void setDivlinecolor(String divlinecolor) {
		this.divlinecolor = divlinecolor;
	}
	/**
	 * 取得水平分隔线的宽度
	 */
	public String getDivLineThickness() {
		return divLineThickness;
	}
	/**
	 * 设置水平分隔线的宽度
	 * @param divLineThickness String
	 * 例: 5
	 */
	public void setDivLineThickness(String divLineThickness) {
		this.divLineThickness = divLineThickness;
	}
	/**
	 * 取得水平分隔线的透明度
	 */
	public String getDivLineAlpha() {
		return divLineAlpha;
	}
	/**
	 * 设置水平分隔线的透明度
	 * @param divLineAlpha Numerical Value 
	 * 例: 0~100
	 */
	public void setDivLineAlpha(String divLineAlpha) {
		this.divLineAlpha = divLineAlpha;
	}
	/**
	 * 取得是否显示水平分隔线的数值
	 */
	public String getShowDivLineValue() {
		return showDivLineValue;
	}
	/**
	 * 设置是否显示水平分隔线的数值
	 * @param showDivLineValue boolean
	 * 例: 1/0
	 */
	public void setShowDivLineValue(String showDivLineValue) {
		this.showDivLineValue = showDivLineValue;
	}
	/**
	 * 取得显示是否激活鼠标旋停效果
	 */
	public String getShowhovercap() {
		return showhovercap;
	}
	/**
	 * 设置显示是否激活鼠标旋停效果
	 * @param showhovercap boolean
	 * 例: 1/0
	 */
	public void setShowhovercap(String showhovercap) {
		this.showhovercap = showhovercap;
	}
	/**
	 * 取得鼠标旋停效果的背景颜色
	 */
	public String getHoverCapBgColor() {
		return hoverCapBgColor;
	}
	/**
	 * 设置鼠标旋停效果的背景颜色
	 * @param hoverCapBgColor HexColorCode
	 * 例: ffffff
	 */
	public void setHoverCapBgColor(String hoverCapBgColor) {
		this.hoverCapBgColor = hoverCapBgColor;
	}
	/**
	 * 取得鼠标旋停效果的边框颜色
	 */
	public String getHoverCapBorderColor() {
		return hoverCapBorderColor;
	}
	/**
	 * 设置鼠标旋停效果的边框颜色
	 * @param hoverCapBorderColor HexColorCode
	 * 例: ffffff
	 */
	public void setHoverCapBorderColor(String hoverCapBorderColor) {
		this.hoverCapBorderColor = hoverCapBorderColor;
	}
	/**
	 * 取得鼠标旋停后显示的文本中的分隔符号
	 */
	public String getHoverCapSepChar() {
		return hoverCapSepChar;
	}
	/**
	 * 设置鼠标旋停后显示的文本中的分隔符号
	 * @param hoverCapSepChar Char
	 * 例: ;
	 */
	public void setHoverCapSepChar(String hoverCapSepChar) {
		this.hoverCapSepChar = hoverCapSepChar;
	}
	/**
	 * 取得图表左边距
	 */
	public String getChartLeftMargin() {
		return chartLeftMargin;
	}
	/**
	 * 设置图表左边距
	 * @param chartLeftMargin String
	 * 例: 1
	 */
	public void setChartLeftMargin(String chartLeftMargin) {
		this.chartLeftMargin = chartLeftMargin;
	}
	/**
	 * 取得图表右边距
	 */
	public String getChartRightMargin() {
		return chartRightMargin;
	}
	/**
	 * 设置图表右边距
	 * @param chartRightMargin String
	 * 例: 1
	 */
	public void setChartRightMargin(String chartRightMargin) {
		this.chartRightMargin = chartRightMargin;
	}
	/**
	 * 取得图表上边距
	 */
	public String getChartTopMargin() {
		return chartTopMargin;
	}
	/**
	 * 设置图表上边距
	 * @param chartTopMargin String
	 * 例: 1
	 */
	public void setChartTopMargin(String chartTopMargin) {
		this.chartTopMargin = chartTopMargin;
	}
	/**
	 * 取得图表下边距
	 */
	public String getChartBottomMargin() {
		return chartBottomMargin;
	}
	/**
	 * 设置图表下边距
	 * @param chartBottomMargin String
	 * 例: 1
	 */
	public void setChartBottomMargin(String chartBottomMargin) {
		this.chartBottomMargin = chartBottomMargin;
	}
	/**
	 * 水平边框是否一定要显示
	 */
	public String getZeroPlaneShowBorder() {
		return zeroPlaneShowBorder;
	}
	/**
	 * 在3D图表中 水平(0位置)边框是否一定要显示
	 * @param zeroPlaneShowBorder boolean
	 * 例: 1/0
	 */
	public void setZeroPlaneShowBorder(String zeroPlaneShowBorder) {
		this.zeroPlaneShowBorder = zeroPlaneShowBorder;
	}
	/**
	 * 取得水平(0位置)边框颜色
	 * @return
	 */
	public String getZeroPlaneBorderColor() {
		return zeroPlaneBorderColor;
	}
	/**
	 * 设置水平(0位置)边框颜色
	 * @param zeroPlaneBorderColor HexColorCode
	 * 例: ffffff
	 */
	public void setZeroPlaneBorderColor(String zeroPlaneBorderColor) {
		this.zeroPlaneBorderColor = zeroPlaneBorderColor;
	}
	/**
	 * 取得水平(0位置)柱或饼颜色
	 */
	public String getZeroPlaneColor() {
		return zeroPlaneColor;
	}
	/**
	 * 设置水平(0位置)柱或饼颜色
	 * @param zeroPlaneColor HexColorCode
	 * 例: ffffff
	 */
	public void setZeroPlaneColor(String zeroPlaneColor) {
		this.zeroPlaneColor = zeroPlaneColor;
	}
	/**
	 * 取得水平(0位置)柱或饼透明度
	 */
	public String getZeroPlaneAlpha() {
		return zeroPlaneAlpha;
	}
	/**
	 * 设置水平(0位置)柱或饼透明度
	 * @param zeroPlaneAlpha Numerical Value 
	 * 例: 0~100
	 */
	public void setZeroPlaneAlpha(String zeroPlaneAlpha) {
		this.zeroPlaneAlpha = zeroPlaneAlpha;
	}
}
