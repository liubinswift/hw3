package com.viewscenes.util.report.charts;
/**
 * <p>Pie3D 3D饼图 </p>
 * <p>对应 FusionCharts for Flex > XML Reference > Pie 3D</p>
 * @author 张士鑫
 * @version 1.0
 */
public class Pie3D {
	
	
	//节点结束标签
	public static String BuildChartEndXML = "\n</chart>";
	
	//Chart Objects  图表对象以及动作,动画对象
	public static String BACKGROUND   = "BACKGROUND";
	public static String CAPTION   = "CAPTION";
	public static String DATALABELS   = "DATALABELS";
	public static String DATAPLOT   = "DATAPLOT";
	public static String SUBCAPTION   = "SUBCAPTION";
	public static String TOOLTIP   = "TOOLTIP ";
	
	//<chart> element Attributes 元素属性 -----------------------------------------
	//Functional Attributes  功能属性
	private String animation  = null;			//设置是否显示动画
	private String palette   = null;			//设置调色板编号1-5
	private String paletteColors  = null;		//设置调色板的颜色,以逗号分割
	private String showAboutMenuItem  = null;	//设置是否显示关于菜单
	private String aboutMenuItemLabel  = null;	//设置关于菜单的标题
	private String aboutMenuItemLink  = null;	//设置关于菜单的链接
	private String showLabels  = null;			//设置是否显示X轴标签
	private String showValues   = null;			//设置是否显示Y轴的值
	private String clickURL    = null;			//设置点击链接
	private String defaultAnimation    = null;	//设置默认的动画的样式
	private String showZeroPies   = null;		//设置是否显示0值
	private String showPercentValues = null;	//设置饼图标签的值是否以百分比形式显示
	private String showPercentInToolTip = null;	//设置饼图提示的值是否以百分比形式显示
	private String labelSepChar   = null;		//设置标签和数据值的分隔符
	private String exportEnabled = null;			//设置是否启用导出功能
	private String exportShowMenuItem = null;		//设置是否显示导出图片右菜单
	private String exportFileName = null;			//设置导出图片的名称
	private String exportFormats = null;			//设置导出图片的分割显示(检查API再使用)
	private String showExportDataMenuItem = null; 	//设置是否显示图表的XML菜单
	//Chart Titles and Axis Names 图表标题和表名称
	private String caption = null;				//设置图表的主标题
	private String subCaption  = null;			//设置图表的副标题
	
	//Chart Cosmetics 图形美化
	private String bgColor  = null;				//设置背景色<可渐变>
	private String bgAlpha   = null;			//设置透明度<可渐变>
	private String bgRatio   = null;			//设置背景渐变比例<可渐变>
	private String bgAngle   = null;			//设置背景色填充角度<可渐变>
	private String bgSWF   = null;				//设置背景图片,SWF地址
	private String bgSWFAlpha   = null;			//设置背景图片,SWF的透明度
	private String showBorder  = null;			//设置是否显示边框
	private String borderColor   = null;		//设置边框的颜色
	private String borderThickness   = null;	//设置边框的厚度,向里方向
	private String borderAlpha   = null;		///设置边框透明度
	private String logoURL = null;				//设置LOGO的路径
	private String logoPosition  = null;		//设置LOGO的相对位置	
	private String logoAlpha  = null;			//设置LOGO的透明度
	private String logoScale  = null;			//设置LOGO的缩放比例
	private String logoLink  = null;			//设置LOGO的链接
	
	//Data Plot Cosmetics  数据图块美化
	private String showPlotBorder = null;		//设置是否显示图块的边框
	private String plotBorderColor   = null;	//设置图块边框的颜色
	private String plotBorderThickness  = null;	//设置图块边框的厚度
	private String plotBorderAlpha  = null;		//设置图块边框的透明度
	private String plotFillAlpha = null;		//设置图块填充色的透明度
	private String use3DLighting    = null;		//设置是否显示动画<3D>
	
	//Pie / Doughnut Properties 饼图属性
	private String slicingDistance  = null;		//设置切片到图表的距离
	private String pieRadius  = null;			//设置饼图的半径
	private String startingAngle  = null;		//设置旋转的起始度数
	private String enableRotation  = null;		//设置是否默认旋转?
	private String pieInnerFaceAlpha= null;		//设置饼图内侧的透明度<3D>
	private String pieOuterFaceAlpha = null;	//设置饼图外侧的透明度<3D>
	private String pieYScale     = null;		//设置饼图的可见角度<3D>
	private String pieSliceDepth     = null;	//设置饼图的厚度<3D>

	//Smart Lines & Labels 饼图连接线设置
	private String enableSmartLabels  = null;	//设置饼图是否显示链接线	
	private String skipOverlapLabels  = null;	//设置饼图是否显示链接线	
	private String isSmartLineSlanted  = null;	//设置饼图是否显示链接线	
	private String smartLineColor  = null;	//设置饼图是否显示链接线	
	private String smartLineThickness  = null;	//设置饼图是否显示链接线	
	private String smartLineAlpha  = null;	//设置饼图是否显示链接线	
	private String labelDistance  = null;	//设置饼图是否显示链接线	
	private String smartLabelClearance  = null;	//设置饼图是否显示链接线	
	
	//Number Formatting 数字格式化
	private String formatNumber  = null;				//设置是否数字格式化				
	private String formatNumberScale  = null;			//设置是否用"K"来代表千，"M"来代表百万			
	private String defaultNumberScale  = null;			//设置默认的数字格式化的后缀单位			
	private String numberScaleUnit  = null;				//设置数字格式化的后缀单位,如'K,M,B' 		
	private String numberScaleValue  = null;			//设置数字格式化的样式,如'1000,1000,1000		
	private String numberPrefix = null;					//设置数据值的前缀
	private String numberSuffix = null;					//设置数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	private String decimalSeparator  = null;			//设置指定的字符来代替小数点			
	private String thousandSeparator  = null;			//设置指定的字符来代替千位分隔符				
	private String inDecimalSeparator   = null;			//设置小数点转义符			
	private String inThousandSeparator   = null;		//设置千分位转义符				
	private String decimals = null;						//设置精确到小数点后几位
	private String forceDecimals   = null;				//设置小数点位数不足是否补零		

	
	//Font Properties 字体属性
	private String baseFont = null;						//设置Value标签的字体
	private String baseFontSize = null;					//设置Value标签的字体大小
	private String baseFontColor = null;				//设置Value标签的字体颜色
	
	
	//Tool-tip 工具提示	---------------------------------------------
	private String showToolTip  = null;					//设置是否显示提示
	private String toolTipBgColor   = null;				//设置提示背景的颜色
	private String toolTipBorderColor   = null;			//设置提示背景框的颜色
	private String toolTipSepChar   = null;				//设置提示分隔符
	private String showToolTipShadow  = null;			//设置是否显示提示背景框的阴影
	
	//Chart Padding & Margins 图表边距
	private String captionPadding   = null;				//设置标题距离图表的边距
	private String chartLeftMargin    = null;			//设置图表距离背景的左边距
	private String chartRightMargin    = null;			//设置图表距离背景的右边距
	private String chartTopMargin    = null;			//设置图表距离背景的上边距
	private String chartBottomMargin    = null;			//设置图表距离背景的下边距
	
	
	
	/**
	 * 生成图表的主体属性XML
	 * @return 图表的主体属性XML
	 */
	public String BuildChartXML(){
		StringBuffer chartXML = new StringBuffer();
		chartXML.append("<chart ");
		//Functional Attributes  功能属性 *********************************************************************
		
		//设置是否显示动画
		if(this.getAnimation()!=null){
			chartXML.append(" animation = '" +this.animation + "' \n");
		}
		//设置调色板编号1-5
		if(this.getPalette()!=null){
			chartXML.append(" palette = '" +this.palette + "' \n");
		}
		//设置调色板的颜色
		if(this.getPaletteColors()!=null){
			chartXML.append(" paletteColors = '" +this.paletteColors + "' \n");
		}
		//设置是否显示关于菜单
		if(this.getShowAboutMenuItem()!=null){
			chartXML.append(" showAboutMenuItem = '" +this.showAboutMenuItem + "' \n");
		}
		//设置关于菜单的标题
		if(this.getAboutMenuItemLabel()!=null){
			chartXML.append(" aboutMenuItemLabel = '" +this.aboutMenuItemLabel + "' \n");
		}
		//设置关于菜单的链接
		if(this.getAboutMenuItemLink()!=null){
			chartXML.append(" aboutMenuItemLink = '" +this.aboutMenuItemLink + "' \n");
		}
		//设置是否显示X轴标签
		if(this.getShowLabels()!=null){
			chartXML.append(" showLabels = '" +this.showLabels + "' \n");
		}
		//设置是否显示Y轴的值
		if(this.getShowValues()!=null){
			chartXML.append(" showValues = '" +this.showValues + "' \n");
		}
		//设置点击链接
		if(this.getClickURL()!=null){
			chartXML.append(" clickURL = '" +this.clickURL + "' \n");
		}
		//设置显示默认的动画
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" defaultAnimation = '" +this.defaultAnimation + "' \n");
		}
		//设置是否显示0值
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" showZeroPies = '" +this.showZeroPies + "' \n");
		}
		//设置饼图标签的值是否以百分比形式显示
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" showPercentValues = '" +this.showPercentValues + "' \n");
		}
		//设置饼图提示的值是否以百分比形式显示
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" showPercentInToolTip = '" +this.showPercentInToolTip + "' \n");
		}
		//设置标签和数据值的分隔符
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" labelSepChar = '" +this.labelSepChar + "' \n");
		}
		//设置是否启用导出功能
		if(this.getExportEnabled()!=null){
			chartXML.append(" exportEnabled = '" +this.exportEnabled + "' \n");
		}
		//设置是否显示导出图片右菜单
		if(this.getExportShowMenuItem()!=null){
			chartXML.append(" exportShowMenuItem = '" +this.exportShowMenuItem + "' \n");
		}
		//设置导出图片的名称
		if(this.getExportFileName()!=null){
			chartXML.append(" exportFileName = '" +this.exportFileName + "' \n");
		}
		//设置导出图片的分割显示(检查API再使用)
		if(this.getExportFormats()!=null){
			chartXML.append(" exportFormats = '" +this.exportFormats + "' \n");
		}
		//设置是否显示图表的XML菜单
		if(this.getShowExportDataMenuItem()!=null){
			chartXML.append(" showExportDataMenuItem = '" +this.showExportDataMenuItem + "' \n");
		}
		//Chart Titles and Axis Names 图表标题和表名称*********************************************************************
		
		//设置图表的主标题
		if(this.getCaption()!=null){
			chartXML.append(" caption = '" +this.caption + "' \n");
		}
		//设置图表的副标题
		if(this.getSubCaption()!=null){
			chartXML.append(" subCaption = '" +this.subCaption + "' \n");
		}
		
		//Chart Cosmetics 图形美化*********************************************************************
		//设置背景色<可渐变>
		if(this.getBgColor()!=null){
			chartXML.append(" bgColor = '" +this.bgColor + "' \n");
		}
		//设置透明度<可渐变>
		if(this.getBgAlpha()!=null){
			chartXML.append(" bgAlpha = '" +this.bgAlpha + "' \n");
		}
		//设置背景渐变比例<可渐变>
		if(this.getBgRatio()!=null){
			chartXML.append(" bgRatio = '" +this.bgRatio + "' \n");
		}
		//设置背景色填充角度<可渐变>
		if(this.getBgAngle()!=null){
			chartXML.append(" bgAngle = '" +this.bgAngle + "' \n");
		}
		//设置背景图片,SWF地址
		if(this.getBgSWF()!=null){
			chartXML.append(" bgSWF = '" +this.bgSWF + "' \n");
		}
		//设置背景图片,SWF的透明度
		if(this.getBgSWFAlpha()!=null){
			chartXML.append(" bgSWFAlpha = '" +this.bgSWFAlpha + "' \n");
		}
		//设置是否显示边框
		if(this.getShowBorder()!=null){
			chartXML.append(" showBorder = '" +this.showBorder + "' \n");
		}
		//设置边框的颜色
		if(this.getBorderColor()!=null){
			chartXML.append(" borderColor = '" +this.borderColor + "' \n");
		}
		//设置边框的厚度,向里方向
		if(this.getBorderThickness()!=null){
			chartXML.append(" borderThickness = '" +this.borderThickness + "' \n");
		}
		///设置边框透明度
		if(this.getBorderAlpha()!=null){
			chartXML.append(" borderAlpha = '" +this.borderAlpha + "' \n");
		}
		//设置LOGO的路径
		if(this.getLogoURL()!=null){
			chartXML.append(" logoURL = '" +this.logoURL + "' \n");
		}
		//设置LOGO的相对位置	
		if(this.getLogoPosition()!=null){
			chartXML.append(" logoPosition = '" +this.logoPosition + "' \n");
		}
		//设置LOGO的透明度
		if(this.getLogoAlpha()!=null){
			chartXML.append(" logoAlpha = '" +this.logoAlpha + "' \n");
		}
		//设置LOGO的缩放比例
		if(this.getLogoScale()!=null){
			chartXML.append(" logoScale = '" +this.logoScale + "' \n");
		}
		//设置LOGO的链接
		if(this.getLogoLink()!=null){
			chartXML.append(" logoLink = '" +this.logoLink + "' \n");
		}
		//Data Plot Cosmetics  数据图块美化*********************************************************************
		//设置是否显示图块的边框
		if(this.getShowPlotBorder()!=null){
			chartXML.append(" showPlotBorder = '" +this.showPlotBorder + "' \n");
		}
		//设置图块边框的颜色
		if(this.getPlotBorderColor()!=null){
			chartXML.append(" plotBorderColor = '" +this.plotBorderColor + "' \n");
		}
		//设置图块边框的厚度
		if(this.getPlotBorderThickness()!=null){
			chartXML.append(" plotBorderThickness = '" +this.plotBorderThickness + "' \n");
		}
		//设置图块边框的透明度
		if(this.getPlotBorderAlpha()!=null){
			chartXML.append(" plotBorderAlpha = '" +this.plotBorderAlpha + "' \n");
		}
		//设置图块填充色的透明度
		if(this.getPlotFillAlpha()!=null){
			chartXML.append(" plotFillAlpha = '" +this.plotFillAlpha + "' \n");
		}
		//设置是否使用先进的动画效果
		if(this.getUse3DLighting()!=null){
			chartXML.append(" use3DLighting = '" +this.use3DLighting + "' \n");
		}
		
		//Pie / Doughnut Properties 饼图属性-----------------------------------
		//设置切片到图表的距离
		if(this.getSlicingDistance()!=null){
			chartXML.append(" slicingDistance = '" +this.slicingDistance + "' \n");
		}
		//设置饼图的半径
		if(this.getPieRadius()!=null){
			chartXML.append(" pieRadius = '" +this.pieRadius + "' \n");
		}
		//设置旋转的起始度数
		if(this.getStartingAngle()!=null){
			chartXML.append(" startingAngle = '" +this.startingAngle + "' \n");
		}
		//设置是否默认旋转
		if(this.getEnableRotation()!=null){
			chartXML.append(" enableRotation = '" +this.enableRotation + "' \n");
		}
		//设置饼图内侧的透明度<3D>
		if(this.getPieInnerFaceAlpha()!=null){
			chartXML.append(" pieInnerFaceAlpha = '" +this.pieInnerFaceAlpha + "' \n");
		}
		//设置饼图外侧的透明度<3D>
		if(this.getPieOuterFaceAlpha()!=null){
			chartXML.append(" pieOuterFaceAlpha = '" +this.pieOuterFaceAlpha + "' \n");
		}
		//设置饼图的可见角度<3D>
		if(this.getPieYScale()!=null){
			chartXML.append(" pieYScale = '" +this.pieYScale + "' \n");
		}
		//设置饼图的厚度<3D>
		if(this.getPieSliceDepth()!=null){
			chartXML.append(" pieSliceDepth = '" +this.pieSliceDepth + "' \n");
		}
		
		//Smart Lines & Labels 饼图连接线设置
		//饼图:设置饼图是否显示链接线	
		if(this.getEnableSmartLabels()!=null){
			chartXML.append(" enableSmartLabels='"+this.getEnableSmartLabels()+"' \n");
		}
		//饼图:设置饼图链接线的颜色
		if(this.getSmartLineColor()!=null){
			chartXML.append(" smartLineColor='"+this.getSmartLineColor()+"' \n");
		}
		//饼图:设置饼图链接线的厚度
		if(this.getSmartLineThickness()!=null){
			chartXML.append(" smartLineThickness='"+this.getSmartLineThickness()+"' \n");
		}
		//饼图:设置饼图链接线的透明度
		if(this.getSmartLineAlpha()!=null){
			chartXML.append(" smartLineAlpha='"+this.getSmartLineAlpha()+"' \n");
		}
		//饼图:设置饼图链接线是否是直角
		if(this.getIsSmartLineSlanted()!=null){
			chartXML.append(" isSmartLineSlanted='"+this.getIsSmartLineSlanted()+"' \n");
		}
		//饼图:属性未知		
		if(this.getLabelDistance()!=null){
			chartXML.append(" labelDistance='"+this.getLabelDistance()+"' \n");
		}
		//饼图:设置饼图距离标签的距离
		if(this.getSmartLabelClearance()!=null){
			chartXML.append(" smartLabelClearance='"+this.getSmartLabelClearance()+"' \n");
		}
		//饼图:设置饼图标签太多是否漏掉部分标签
		if(this.getSkipOverlapLabels()!=null){
			chartXML.append(" skipOverlapLabels='"+this.getSkipOverlapLabels()+"' \n");
		}
		
		//Number Formatting 数字格式化******************************************************************
		//设置是否数字格式化	
		if(this.getFormatNumber()!=null){
			chartXML.append(" formatNumber = '" +this.formatNumber + "' \n");
		}
		//设置是否用"K"来代表千，"M"来代表百万	
		if(this.getFormatNumberScale()!=null){
			chartXML.append(" formatNumberScale = '" +this.formatNumberScale + "' \n");
		}
		//设置默认的数字格式化的后缀单位	
		if(this.getDefaultNumberScale()!=null){
			chartXML.append(" defaultNumberScale = '" +this.defaultNumberScale + "' \n");
		}
		//设置数字格式化的后缀单位,如'K,M,B'
		if(this.getNumberScaleUnit()!=null){
			chartXML.append(" numberScaleUnit = '" +this.numberScaleUnit + "' \n");
		}
		//设置数字格式化的样式,如'1000,1000,1000	
		if(this.getNumberScaleValue()!=null){
			chartXML.append(" numberScaleValue = '" +this.numberScaleValue + "' \n");
		}
		//设置数据值的前缀
		if(this.getNumberPrefix()!=null){
			chartXML.append(" numberPrefix = '" +this.numberPrefix + "' \n");
		}
		//设置数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
		if(this.getNumberSuffix()!=null){
			chartXML.append(" numberSuffix = '" +this.numberSuffix + "' \n");
		}
		//设置指定的字符来代替小数点		
		if(this.getDecimalSeparator()!=null){
			chartXML.append(" decimalSeparator = '" +this.decimalSeparator + "' \n");
		}
		//设置指定的字符来代替千位分隔符	
		if(this.getThousandSeparator()!=null){
			chartXML.append(" thousandSeparator = '" +this.thousandSeparator + "' \n");
		}
		//设置小数点转义符			
		if(this.getInDecimalSeparator()!=null){
			chartXML.append(" inDecimalSeparator = '" +this.inDecimalSeparator + "' \n");
		}
		//设置千分位转义符				
		if(this.getInThousandSeparator()!=null){
			chartXML.append(" inThousandSeparator = '" +this.inThousandSeparator + "' \n");
		}
		//设置精确到小数点后几位
		if(this.getDecimals()!=null){
			chartXML.append(" decimals = '" +this.decimals + "' \n");
		}
		//设置小数点位数不足是否补零	
		if(this.getForceDecimals()!=null){
			chartXML.append(" forceDecimals = '" +this.forceDecimals + "' \n");
		}
		
		//Font Properties 字体属性---------------------------------------------
		//设置Value标签的字体
		if(this.getBaseFont()!=null){
			chartXML.append(" baseFont = '" +this.baseFont + "' \n");
		}
		//设置Value标签的字体大小
		if(this.getBaseFontSize()!=null){
			chartXML.append(" baseFontSize = '" +this.baseFontSize + "' \n");
		}
		//设置Value标签的字体颜色
		if(this.getBaseFontColor()!=null){
			chartXML.append(" baseFontColor = '" +this.baseFontColor + "' \n");
		}
		
		//Tool-tip 工具提示	---------------------------------------------
		//设置是否显示提示
		if(this.getShowToolTip()!=null){
			chartXML.append(" showToolTip = '" +this.showToolTip + "' \n");
		}
		//设置提示背景的颜色
		if(this.getToolTipBgColor()!=null){
			chartXML.append(" toolTipBgColor = '" +this.toolTipBgColor + "' \n");
		}
		//设置提示背景框的颜色
		if(this.getToolTipBorderColor()!=null){
			chartXML.append(" toolTipBorderColor = '" +this.toolTipBorderColor + "' \n");
		}
		//设置提示分隔符
		if(this.getToolTipSepChar()!=null){
			chartXML.append(" toolTipSepChar = '" +this.toolTipSepChar + "' \n");
		}
		//设置是否显示提示背景框的阴影
		if(this.getShowToolTipShadow()!=null){
			chartXML.append(" showToolTipShadow = '" +this.showToolTipShadow + "' \n");
		}
		
		//Chart Padding & Margins 图表边距 ---------------------------------------------
		
		//设置标题距离图表的边距
		if(this.getCaptionPadding()!=null){
			chartXML.append(" captionPadding = '" +this.captionPadding + "' \n");
		}
		//设置图表距离背景的左边距
		if(this.getChartLeftMargin()!=null){
			chartXML.append(" chartLeftMargin = '" +this.chartLeftMargin + "' \n");
		}
		//设置图表距离背景的右边距
		if(this.getChartRightMargin()!=null){
			chartXML.append(" chartRightMargin = '" +this.chartRightMargin + "' \n");
		}
		//设置图表距离背景的上边距
		if(this.getChartTopMargin()!=null){
			chartXML.append(" chartTopMargin = '" +this.chartTopMargin + "' \n");
		}
		//设置图表距离背景的下边距
		if(this.getChartBottomMargin()!=null){
			chartXML.append(" chartBottomMargin = '" +this.chartBottomMargin + "' \n");
		}
		chartXML.append(" > \n");
		return chartXML.toString();
	}
	
	
	//<chart> element Attributes 元素属性 -----------------------------------------
	//Functional Attributes  功能属性
	/**
	 * 获取是否显示动画
	 * @return 取值范围(1:true 0:false)
	 */
	public String getAnimation() {
		return animation;
	}
	/**
	 * 设置是否显示动画
	 * @param animation 取值范围(1:true 0:false)
	 */
	public void setAnimation(String animation) {
		this.animation = animation;
	}
	
	/**
	 * 获取调色板编号1-5
	 * @return 取值范围(1-5)
	 */
	public String getPalette() {
		return palette;
	}
	/**
	 * 设置调色板编号1-5
	 * @param palette 取值范围(1-5)
	 */
	public void setPalette(String palette) {
		this.palette = palette;
	}
	/**
	 * 获取调色板的颜色
	 * @return 取值范围(16进制颜色,以逗号分割)
	 */
	public String getPaletteColors() {
		return paletteColors;
	}
	/**
	 * 设置调色板的颜色
	 * @param paletteColors 取值范围(16进制颜色,以逗号分割)
	 */
	public void setPaletteColors(String paletteColors) {
		this.paletteColors = paletteColors;
	}
	/**
	 * 获取是否显示关于菜单
	 * @return 取值范围(1:true 0:false)
	 */
	public String getShowAboutMenuItem() {
		return showAboutMenuItem;
	}
	/**
	 * 设置是否显示关于菜单
	 * @param showAboutMenuItem 取值范围(1:true 0:false)
	 */
	public void setShowAboutMenuItem(String showAboutMenuItem) {
		this.showAboutMenuItem = showAboutMenuItem;
	}
	/**
	 * 获取关于菜单的标题
	 * @return 
	 */
	public String getAboutMenuItemLabel() {
		return aboutMenuItemLabel;
	}
	/**
	 * 设置关于菜单的标题
	 * @param aboutMenuItemLabel
	 */
	public void setAboutMenuItemLabel(String aboutMenuItemLabel) {
		this.aboutMenuItemLabel = aboutMenuItemLabel;
	}
	/**
	 * 获取关于菜单的链接
	 * @return 取值范围(URL)
	 */
	public String getAboutMenuItemLink() {
		return aboutMenuItemLink;
	}
	/**
	 * 设置关于菜单的链接
	 * @param aboutMenuItemLink 取值范围(URL)
	 */
	public void setAboutMenuItemLink(String aboutMenuItemLink) {
		this.aboutMenuItemLink = aboutMenuItemLink;
	}
	/**
	 * 获取是否显示X轴标签
	 * @return 取值范围(1:true 0:false)
	 */
	public String getShowLabels() {
		return showLabels;
	}
	/**
	 * 设置是否显示X轴标签
	 * @param showLabels 取值范围(1:true 0:false)
	 */
	public void setShowLabels(String showLabels) {
		this.showLabels = showLabels;
	}
	/**
	 * 获取是否显示Y轴的值
	 * @return	取值范围(1:true 0:false)
	 */
	public String getShowValues() {
		return showValues;
	}
	/**
	 * 设置是否显示Y轴的值
	 * @param showValues	取值范围(1:true 0:false)
	 */
	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}
	/**
	 * 获取点击链接
	 * @return
	 */
	public String getClickURL() {
		return clickURL;
	}
	/**
	 * 设置点击链接
	 * @param clickURL
	 */
	public void setClickURL(String clickURL) {
		this.clickURL = clickURL;
	}
	/**
	 * 获取默认的动画的样式
	 * @return	取值范围(0:无动画,style)
	 */
	public String getDefaultAnimation() {
		return defaultAnimation;
	}
	/**
	 * 设置默认的动画的样式
	 * @param defaultAnimation	取值范围(0:无动画,style)
	 */
	public void setDefaultAnimation(String defaultAnimation) {
		this.defaultAnimation = defaultAnimation;
	}
	/**
	 * 获取是否显示0值
	 * @return
	 */
	public String getShowZeroPies() {
		return showZeroPies;
	}
	/**
	 * 设置是否显示0值
	 * @param showZeroPies
	 */
	public void setShowZeroPies(String showZeroPies) {
		this.showZeroPies = showZeroPies;
	}
	/**
	 * 获取饼图标签的值是否以百分比形式显示
	 * @return
	 */
	public String getShowPercentValues() {
		return showPercentValues;
	}
	/**
	 * 设置饼图标签的值是否以百分比形式显示
	 * @param showPercentValues
	 */
	public void setShowPercentValues(String showPercentValues) {
		this.showPercentValues = showPercentValues;
	}
	/**
	 * 获取饼图提示的值是否以百分比形式显示
	 * @return
	 */
	public String getShowPercentInToolTip() {
		return showPercentInToolTip;
	}
	/**
	 * 设置饼图提示的值是否以百分比形式显示
	 * @param showPercentInToolTip
	 */
	public void setShowPercentInToolTip(String showPercentInToolTip) {
		this.showPercentInToolTip = showPercentInToolTip;
	}
	/**
	 * 获取标签和数据值的分隔符
	 * @return
	 */
	public String getLabelSepChar() {
		return labelSepChar;
	}
	/**
	 * 设置标签和数据值的分隔符
	 * @param labelSepChar
	 */
	public void setLabelSepChar(String labelSepChar) {
		this.labelSepChar = labelSepChar;
	}
	/**
	 * 获取是否启用导出功能
	 * @return
	 */
	public String getExportEnabled() {
		return exportEnabled;
	}
	/**
	 * 设置是否启用导出功能
	 * @param exportEnabled
	 */
	public void setExportEnabled(String exportEnabled) {
		this.exportEnabled = exportEnabled;
	}
	/**
	 * 获取是否显示导出图片右菜单
	 * @return
	 */
	public String getExportShowMenuItem() {
		return exportShowMenuItem;
	}
	/**
	 * 设置是否显示导出图片右菜单
	 * @param exportShowMenuItem
	 */
	public void setExportShowMenuItem(String exportShowMenuItem) {
		this.exportShowMenuItem = exportShowMenuItem;
	}
	/**
	 * 获取导出图片的名称
	 * @return
	 */
	public String getExportFileName() {
		return exportFileName;
	}
	/**
	 * 设置导出图片的名称
	 * @param exportFileName
	 */
	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}
	
	/**
	 * 获取导出图片的分割显示(检查API再使用)
	 * @return
	 */
	public String getExportFormats() {
		return exportFormats;
	}
	/**
	 * 设置导出图片的分割显示(检查API再使用)
	 * @param exportFormats
	 */
	public void setExportFormats(String exportFormats) {
		this.exportFormats = exportFormats;
	}
	/**
	 * 获取是否显示图表的XML菜单
	 * @return
	 */
	public String getShowExportDataMenuItem() {
		return showExportDataMenuItem;
	}
	/**
	 * 设置是否显示图表的XML菜单
	 * @param showExportDataMenuItem
	 */
	public void setShowExportDataMenuItem(String showExportDataMenuItem) {
		this.showExportDataMenuItem = showExportDataMenuItem;
	}
	//Chart Titles and Axis Names 图表标题和表名称 *******************************************
	/**
	 * 获取图表的主标题
	 * @return
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * 设置图表的主标题
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * 获取图表的副标题
	 * @return
	 */
	public String getSubCaption() {
		return subCaption;
	}
	/**
	 * 设置图表的副标题
	 * @param subCaption
	 */
	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}
	
	//Chart Cosmetics 图形美化----------------------------
	/**
	 * 获取图表的背景色<可渐变>
	 * @return Hex Color Code16
	 */
	public String getBgColor() {
		return bgColor;
	}
	/**
	 * 设置图表的背景色<可渐变>
	 * @param bgColor Hex Color Code16
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	/**
	 * 获取FC图表的背景透明度<可渐变>
	 * @return
	 */
	public String getBgAlpha() {
		return bgAlpha;
	}
	/**
	 * 设置图表背景透明度<可渐变>
	 * @param bgAlpha 取值范围(1-100)
	 */
	public void setBgAlpha(String bgAlpha) {
		this.bgAlpha = bgAlpha;
	}
	/**
	 * 获取图表背景渐变比例<可渐变>
	 * @return
	 */
	public String getBgRatio() {
		return bgRatio;
	}
	/**
	 * 设置图表背景渐变比例<可渐变>
	 * 两个数值之和为100,比如40,代表整个图表背景的40%的区域块
	 * @param bgRatio 例('40','60')
	 */
	public void setBgRatio(String bgRatio) {
		this.bgRatio = bgRatio;
	}
	/**
	 * 获取图表背景色填充角度<可渐变>
	 * @return
	 */
	public String getBgAngle() {
		return bgAngle;
	}
	/**
	 * 设置图表背景色填充角度<可渐变>
	 * @param bgAngle 取值范围(1-360)
	 */
	public void setBgAngle(String bgAngle) {
		this.bgAngle = bgAngle;
	}
	/**
	 * 获取图表背景图片,SWF地址
	 * @return
	 */
	public String getBgSWF() {
		return bgSWF;
	}
	/**
	 * 设置图表背景图片,SWF地址
	 * @param bgSWF ,可以是绝对路径或者相对路径
	 */
	public void setBgSWF(String bgSWF) {
		this.bgSWF = bgSWF;
	}
	/**
	 * 获取图表背景图片,SWF地址透明度
	 * @return
	 */
	public String getBgSWFAlpha() {
		return bgSWFAlpha;
	}
	/**
	 * 设置图表背景图片,SWF地址透明度
	 * @param bgSWFAlpha,取值范围(1-100)
	 */
	public void setBgSWFAlpha(String bgSWFAlpha) {
		this.bgSWFAlpha = bgSWFAlpha;
	}
	/**
	 * 获取是否显示图表边框
	 * @return
	 */
	public String getShowBorder() {
		return showBorder;
	}
	/**
	 * 设置是否显示图表边框
	 * @param showBorder,取值(0,1)
	 */
	public void setShowBorder(String showBorder) {
		this.showBorder = showBorder;
	}
	/**
	 * 获取图表边框的颜色
	 * @return Hex Color Code16
	 */
	public String getBorderColor() {
		return borderColor;
	}
	/**
	 * 设置图表边框的颜色
	 * @param borderColor Hex Color Code16
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	/**
	 * 获取图表边框的厚度
	 * @return
	 */
	public String getBorderThickness() {
		return borderThickness;
	}
	/**
	 * 设置图表边框的厚度
	 * @param borderThickness
	 */
	public void setBorderThickness(String borderThickness) {
		this.borderThickness = borderThickness;
	}
	/**
	 * 获取图表边框的透明度
	 * @return
	 */
	public String getBorderAlpha() {
		return borderAlpha;
	}
	/**
	 * 设置图表边框的透明度
	 * @param borderAlpha
	 */
	public void setBorderAlpha(String borderAlpha) {
		this.borderAlpha = borderAlpha;
	}
	/**
	 * 获取LOGO的路径
	 * @return 取值范围(相对路径或者绝对路径)
	 */
	public String getLogoURL() {
		return logoURL;
	}
	/**
	 * 设置LOGO的路径
	 * @param logoURL 取值范围(相对路径或者绝对路径)
	 */
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	/**
	 * 获取LOGO的相对位置
	 * 取值范围:
	 * TL - Top-left 
	 * TR - Top-right 
	 * BR - Bottom right 
	 * BL - Bottom left 
	 * CC - Center of screen 
	 * @return 
	 */
	public String getLogoPosition() {
		return logoPosition;
	}
	/**
	 * 设置LOGO的相对位置
	 * 取值范围:
	 * TL - Top-left 
	 * TR - Top-right 
	 * BR - Bottom right 
	 * BL - Bottom left 
	 * CC - Center of screen 
	 * @param logoPosition
	 */
	public void setLogoPosition(String logoPosition) {
		this.logoPosition = logoPosition;
	}
	/**
	 * 获取LOGO的透明度
	 * @return	取值范围(1~100)
	 */
	public String getLogoAlpha() {
		return logoAlpha;
	}
	/**
	 * 设置LOGO的透明度
	 * @param logoAlpha 取值范围(1~100)
	 */
	public void setLogoAlpha(String logoAlpha) {
		this.logoAlpha = logoAlpha;
	}
	/**
	 * 获取LOGO的缩放比例
	 * @return 取值范围(1~?)
	 */
	public String getLogoScale() {
		return logoScale;
	}
	/**
	 * 设置LOGO的缩放比例
	 * @param logoScale	取值范围(1~?)
	 */
	public void setLogoScale(String logoScale) {
		this.logoScale = logoScale;
	}
	/**
	 * 获取LOGO的链接
	 * @return	取值范围(http://??)
	 */
	public String getLogoLink() {
		return logoLink;
	}
	/**
	 * 设置LOGO的链接
	 * @param logoLink 取值范围(http://??)
	 */
	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}
	
	//Data Plot Cosmetics  数据图块美化 ********************************
	/**
	 * 获取是否显示图块的边框
	 * @return
	 */
	public String getShowPlotBorder() {
		return showPlotBorder;
	}
	/**
	 * 设置是否显示图块的边框,取值范围(0,1)
	 * @param showPlotBorder
	 */
	public void setShowPlotBorder(String showPlotBorder) {
		this.showPlotBorder = showPlotBorder;
	}
	/**
	 * 获取图块边框的颜色
	 * @return
	 */
	public String getPlotBorderColor() {
		return plotBorderColor;
	}
	/**
	 * 设置图块边框的颜色
	 * @param plotBorderColor
	 */
	public void setPlotBorderColor(String plotBorderColor) {
		this.plotBorderColor = plotBorderColor;
	}
	/**
	 * 获取图块边框的厚度
	 * @return
	 */
	public String getPlotBorderThickness() {
		return plotBorderThickness;
	}
	/**
	 * 设置图块边框的厚度
	 * @param plotBorderThickness
	 */
	public void setPlotBorderThickness(String plotBorderThickness) {
		this.plotBorderThickness = plotBorderThickness;
	}
	/**
	 * 获取图块边框的透明度
	 * @return
	 */
	public String getPlotBorderAlpha() {
		return plotBorderAlpha;
	}
	/**
	 * 设置图块边框的透明度
	 * @param plotBorderAlpha
	 */
	public void setPlotBorderAlpha(String plotBorderAlpha) {
		this.plotBorderAlpha = plotBorderAlpha;
	}
	/**
	 * 获取图块填充色的透明度
	 * @return
	 */
	public String getPlotFillAlpha() {
		return plotFillAlpha;
	}
	/**
	 * 设置图块填充色的透明度,取值范围(0~100)
	 * @param plotFillAlpha ,取值范围(0~100)
	 */
	public void setPlotFillAlpha(String plotFillAlpha) {
		this.plotFillAlpha = plotFillAlpha;
	}
	/**
	 * 获取是否使用先进的动画效果<3D>
	 * @return
	 */
	public String getUse3DLighting() {
		return use3DLighting;
	}
	/**
	 * 设置是否使用先进的动画效果<3D>
	 * @param use3DLighting
	 */
	public void setUse3DLighting(String use3DLighting) {
		this.use3DLighting = use3DLighting;
	}
	//Pie / Doughnut Properties 饼图属性-----------------------------------
	/**
	 * 获取切片到图表的距离
	 * @return
	 */
	public String getSlicingDistance() {
		return slicingDistance;
	}
	/**
	 * 设置切片到图表的距离
	 * @param slicingDistance
	 */
	public void setSlicingDistance(String slicingDistance) {
		this.slicingDistance = slicingDistance;
	}
	/**
	 * 获取饼图的半径
	 * @return
	 */
	public String getPieRadius() {
		return pieRadius;
	}
	/**
	 * 设置饼图的半径
	 * @param pieRadius
	 */
	public void setPieRadius(String pieRadius) {
		this.pieRadius = pieRadius;
	}
	/**
	 * 获取旋转的起始度数
	 * @return
	 */
	public String getStartingAngle() {
		return startingAngle;
	}
	/**
	 * 设置旋转的起始度数
	 * @param startingAngle
	 */
	public void setStartingAngle(String startingAngle) {
		this.startingAngle = startingAngle;
	}
	/**
	 * 获取是否默认旋转
	 * @return
	 */
	public String getEnableRotation() {
		return enableRotation;
	}
	/**
	 * 设置是否默认旋转
	 * @param enableRotation
	 */
	public void setEnableRotation(String enableRotation) {
		this.enableRotation = enableRotation;
	}
	/**
	 * 获取饼图内侧的透明度<3D>
	 * @return
	 */
	public String getPieInnerFaceAlpha() {
		return pieInnerFaceAlpha;
	}
	/**
	 * 设置饼图内侧的透明度<3D>
	 * @param pieInnerFaceAlpha
	 */
	public void setPieInnerFaceAlpha(String pieInnerFaceAlpha) {
		this.pieInnerFaceAlpha = pieInnerFaceAlpha;
	}
	/**
	 * 获取饼图外侧的透明度<3D>
	 * @return
	 */
	public String getPieOuterFaceAlpha() {
		return pieOuterFaceAlpha;
	}
	/**
	 * 设置饼图外侧的透明度<3D>
	 * @param pieOuterFaceAlpha
	 */
	public void setPieOuterFaceAlpha(String pieOuterFaceAlpha) {
		this.pieOuterFaceAlpha = pieOuterFaceAlpha;
	}
	/**
	 * 获取饼图的可见角度<3D>
	 * @return
	 */
	public String getPieYScale() {
		return pieYScale;
	}
	/**
	 * 设置饼图的可见角度<3D>
	 * @param pieYScale
	 */
	public void setPieYScale(String pieYScale) {
		this.pieYScale = pieYScale;
	}
	/**
	 * 获取饼图的厚度<3D>
	 * @return
	 */
	public String getPieSliceDepth() {
		return pieSliceDepth;
	}
	/**
	 * 设置饼图的厚度<3D>
	 * @param pieSliceDepth
	 */
	public void setPieSliceDepth(String pieSliceDepth) {
		this.pieSliceDepth = pieSliceDepth;
	}
	//------------------------------------ 饼图 ---------------------------------------
	/**
	 * 饼图:获取饼图是否显示链接线
	 * @return
	 */
	public String getEnableSmartLabels() {
		return enableSmartLabels;
	}
	/**
	 * 饼图:设置饼图是否显示链接线
	 * @param enableSmartLabels
	 */
	public void setEnableSmartLabels(String enableSmartLabels) {
		this.enableSmartLabels = enableSmartLabels;
	}
	/**
	 * 饼图:获取饼图链接线的颜色
	 * @return Hex Color Code16
	 */
	public String getSmartLineColor() {
		return smartLineColor;
	}
	/**
	 * 饼图:设置饼图链接线的颜色
	 * @param smartLineColor Hex Color Code16
	 */
	public void setSmartLineColor(String smartLineColor) {
		this.smartLineColor = smartLineColor;
	}
	/**
	 * 饼图:获取饼图链接线的厚度
	 * @return
	 */
	public String getSmartLineThickness() {
		return smartLineThickness;
	}
	/**
	 * 饼图:设置饼图链接线的厚度
	 * @param smartLineThickness
	 */
	public void setSmartLineThickness(String smartLineThickness) {
		this.smartLineThickness = smartLineThickness;
	}
	
	/**
	 * 饼图:获取饼图链接线的透明度
	 * @return
	 */
	public String getSmartLineAlpha() {
		return smartLineAlpha;
	}
	/**
	 * 饼图:设置饼图链接线的透明度
	 * @param smartLineAlpha
	 */
	public void setSmartLineAlpha(String smartLineAlpha) {
		this.smartLineAlpha = smartLineAlpha;
	}
	/**
	 * 饼图:获取饼图链接线是否是直角	
	 * @return
	 */
	public String getIsSmartLineSlanted() {
		return isSmartLineSlanted;
	}
	/**
	 * 饼图:设置饼图链接线是否是直角	
	 * @param isSmartLineSlanted 取值:(1:普通,0:直角)
	 */
	public void setIsSmartLineSlanted(String isSmartLineSlanted) {
		this.isSmartLineSlanted = isSmartLineSlanted;
	}	
	/**
	 * 饼图:属性未知,请勿随意使用
	 * @return
	 */
	public String getLabelDistance() {
		return labelDistance;
	}
	/**
	 * 饼图:属性未知,请勿随意使用
	 * @param labelDistance
	 */
	public void setLabelDistance(String labelDistance) {
		this.labelDistance = labelDistance;
	}
	/**
	 * 饼图:获取饼图距离标签的距离
	 * @return
	 */
	public String getSmartLabelClearance() {
		return smartLabelClearance;
	}
	/**
	 * 饼图:设置饼图距离标签的距离
	 * @param smartLabelClearance
	 */
	public void setSmartLabelClearance(String smartLabelClearance) {
		this.smartLabelClearance = smartLabelClearance;
	}
	/**
	 * 饼图:获取饼图标签太多是否漏掉部分标签
	 * @return
	 */
	public String getSkipOverlapLabels() {
		return skipOverlapLabels;
	}
	/**
	 * 饼图:设置饼图标签太多是否漏掉部分标签
	 * @param skipOverlapLabels
	 */
	public void setSkipOverlapLabels(String skipOverlapLabels) {
		this.skipOverlapLabels = skipOverlapLabels;
	}
	//Number Formatting 数字格式化
	/**
	 * 获取是否数字格式化
	 * @return
	 */
	public String getFormatNumber() {
		return formatNumber;
	}
	/**
	 * 设置是否数字格式化
	 * @param formatNumber
	 */
	public void setFormatNumber(String formatNumber) {
		this.formatNumber = formatNumber;
	}
	/**
	 * 获取是否用"K"来代表千，"M"来代表百万
	 * @return
	 */
	public String getFormatNumberScale() {
		return formatNumberScale;
	}
	/**
	 * 设置是否用"K"来代表千，"M"来代表百万
	 * @param formatNumberScale
	 */
	public void setFormatNumberScale(String formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}
	/**
	 * 获取默认的数字格式化的后缀单位	
	 * @return
	 */
	public String getDefaultNumberScale() {
		return defaultNumberScale;
	}
	/**
	 * 设置默认的数字格式化的后缀单位	
	 * @param defaultNumberScale
	 */
	public void setDefaultNumberScale(String defaultNumberScale) {
		this.defaultNumberScale = defaultNumberScale;
	}
	/**
	 * 获取数字格式化的后缀单位,如'K,M,B' 	
	 * @return
	 */
	public String getNumberScaleUnit() {
		return numberScaleUnit;
	}
	/**
	 * 设置数字格式化的后缀单位,如'K,M,B' 	
	 * @param numberScaleUnit
	 */
	public void setNumberScaleUnit(String numberScaleUnit) {
		this.numberScaleUnit = numberScaleUnit;
	}
	/**
	 * 获取数字格式化的样式,如'1000,1000,1000		
	 * @return
	 */
	public String getNumberScaleValue() {
		return numberScaleValue;
	}
	/**
	 * 设置数字格式化的样式,如'1000,1000,1000		
	 * @param numberScaleValue
	 */
	public void setNumberScaleValue(String numberScaleValue) {
		this.numberScaleValue = numberScaleValue;
	}
	/**
	 * 获取数据值的前缀
	 * @return
	 */
	public String getNumberPrefix() {
		return numberPrefix;
	}
	/**
	 * 设置数据值的前缀
	 * @param numberPrefix
	 */
	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}
	/**
	 * 获取数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	 * @return
	 */
	public String getNumberSuffix() {
		return numberSuffix;
	}
	/**
	 * 设置数据值的后缀（如果是特殊字符，需要使用URL Encode重编码）
	 * @param numberSuffix
	 */
	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}
	/**
	 * 获取指定的字符来代替小数点	
	 * @return
	 */
	public String getDecimalSeparator() {
		return decimalSeparator;
	}
	/**
	 * 设置指定的字符来代替小数点	
	 * @param decimalSeparator
	 */
	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}
	/**
	 * 获取指定的字符来代替千位分隔符		
	 * @return
	 */
	public String getThousandSeparator() {
		return thousandSeparator;
	}
	/**
	 * 设置指定的字符来代替千位分隔符		
	 * @param thousandSeparator
	 */
	public void setThousandSeparator(String thousandSeparator) {
		this.thousandSeparator = thousandSeparator;
	}
	/**
	 * 获取小数点转义符		
	 * @return
	 */
	public String getInDecimalSeparator() {
		return inDecimalSeparator;
	}
	/**
	 * 设置小数点转义符		
	 * @param inDecimalSeparator
	 */
	public void setInDecimalSeparator(String inDecimalSeparator) {
		this.inDecimalSeparator = inDecimalSeparator;
	}
	/**
	 * 获取千分位转义符		
	 * @return
	 */
	public String getInThousandSeparator() {
		return inThousandSeparator;
	}
	/**
	 * 设置千分位转义符		
	 * @param inThousandSeparator
	 */
	public void setInThousandSeparator(String inThousandSeparator) {
		this.inThousandSeparator = inThousandSeparator;
	}
	/**
	 * 获取精确到小数点后几位
	 * @return
	 */
	public String getDecimals() {
		return decimals;
	}
	/**
	 * 设置精确到小数点后几位
	 * @param decimals
	 */
	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}
	/**
	 * 获取小数点位数不足是否补零	
	 * @return
	 */
	public String getForceDecimals() {
		return forceDecimals;
	}
	/**
	 * 设置小数点位数不足是否补零	
	 * @param forceDecimals
	 */
	public void setForceDecimals(String forceDecimals) {
		this.forceDecimals = forceDecimals;
	}
	
	//Font Properties 字体属性---------------------------------------------
	/**
	 * 获取设置Value标签的字体
	 * @return
	 */
	public String getBaseFont() {
		return baseFont;
	}
	/**
	 * 设置设置Value标签的字体
	 * @param baseFont
	 */
	public void setBaseFont(String baseFont) {
		this.baseFont = baseFont;
	}
	/**
	 * 获取Value标签的字体大小
	 * @return
	 */
	public String getBaseFontSize() {
		return baseFontSize;
	}
	/**
	 * 设置Value标签的字体大小
	 * @param baseFontSize
	 */
	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}
	/**
	 * 获取Value标签的字体颜色
	 * @return
	 */
	public String getBaseFontColor() {
		return baseFontColor;
	}
	/**
	 * 设置Value标签的字体颜色
	 * @param baseFontColor
	 */
	public void setBaseFontColor(String baseFontColor) {
		this.baseFontColor = baseFontColor;
	}
	

	//Tool-tip 工具提示	---------------------------------------------
	/**
	 * 获取是否显示提示
	 * @return
	 */
	public String getShowToolTip() {
		return showToolTip;
	}
	/**
	 * 设置是否显示提示
	 * @param showToolTip
	 */
	public void setShowToolTip(String showToolTip) {
		this.showToolTip = showToolTip;
	}
	
	/**
	 * 获取是否显示提示背景框的阴影
	 * @return
	 */
	public String getShowToolTipShadow() {
		return showToolTipShadow;
	}
	/**
	 * 设置是否显示提示背景框的阴影
	 * @param showToolTipShadow
	 */
	public void setShowToolTipShadow(String showToolTipShadow) {
		this.showToolTipShadow = showToolTipShadow;
	}
	/**
	 * 获取提示背景框的颜色
	 * @return Hex Color Code16
	 */
	public String getToolTipBorderColor() {
		return toolTipBorderColor;
	}
	/**
	 * 设置提示背景框的颜色
	 * @param toolTipBorderColor Hex Color Code16
	 */
	public void setToolTipBorderColor(String toolTipBorderColor) {
		this.toolTipBorderColor = toolTipBorderColor;
	}
	/**
	 * 获取提示背景的颜色
	 * @return Hex Color Code16
	 */
	public String getToolTipBgColor() {
		return toolTipBgColor;
	}
	/**
	 * 设置提示背景的颜色
	 * @param toolTipBgColor Hex Color Code16
	 */
	public void setToolTipBgColor(String toolTipBgColor) {
		this.toolTipBgColor = toolTipBgColor;
	}
	/**
	 * 获取提示分隔符
	 * @return
	 */
	public String getToolTipSepChar() {
		return toolTipSepChar;
	}
	/**
	 * 设置提示分隔符
	 * @param toolTipSepChar
	 */
	public void setToolTipSepChar(String toolTipSepChar) {
		this.toolTipSepChar = toolTipSepChar;
	}
	
	//Chart Padding & Margins 图表边距 -------------------------------------
	/**
	 * 获取图表距离背景的左边距
	 * @return
	 */
	public String getChartLeftMargin() {
		return chartLeftMargin;
	}
	/**
	 * 设置图表距离背景的左边距
	 * @param chartLeftMargin
	 */
	public void setChartLeftMargin(String chartLeftMargin) {
		this.chartLeftMargin = chartLeftMargin;
	}
	/**
	 * 获取图表距离背景的右边距
	 * @return
	 */
	public String getChartRightMargin() {
		return chartRightMargin;
	}
	/**
	 * 设置图表距离背景的右边距
	 * @param chartRightMargin
	 */
	public void setChartRightMargin(String chartRightMargin) {
		this.chartRightMargin = chartRightMargin;
	}
	/**
	 * 获取图表距离背景的上边距
	 * @return
	 */
	public String getChartTopMargin() {
		return chartTopMargin;
	}
	/**
	 * 设置图表距离背景的上边距
	 * @param chartTopMargin
	 */
	public void setChartTopMargin(String chartTopMargin) {
		this.chartTopMargin = chartTopMargin;
	}
	/**
	 * 获取图表距离背景的下边距
	 * @return
	 */
	public String getChartBottomMargin() {
		return chartBottomMargin;
	}
	/**
	 * 设置图表距离背景的下边距
	 * @param chartBottomMargin
	 */
	public void setChartBottomMargin(String chartBottomMargin) {
		this.chartBottomMargin = chartBottomMargin;
	}
	/**
	 * 获取标题距离图表的边距
	 * @return
	 */
	public String getCaptionPadding() {
		return captionPadding;
	}
	/**
	 * 设置标题距离图表的边距
	 * @param captionPadding
	 */
	public void setCaptionPadding(String captionPadding) {
		this.captionPadding = captionPadding;
	}
}
