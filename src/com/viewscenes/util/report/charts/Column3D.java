package com.viewscenes.util.report.charts;
/**
 * <p>Column3D 柱状图 </p>
 * <p>对应 FusionCharts for Flex > XML Reference > Column 3D</p>
 * @author 张士鑫
 * @version 1.0
 */
public class Column3D {
	
	public static String BuildChartEndXML = "\n</chart>";
	
	//Chart Objects  图表对象以及动作,动画对象
	public static String BACKGROUND = "BACKGROUND";
	public static String CANVAS  = "CANVAS";
	public static String CAPTION  = "CAPTION";
	public static String DATALABELS  = "DATALABELS";
	public static String DATAPLOT  = "DATAPLOT";
	public static String DATAVALUES  = "DATAVALUES";
	public static String DIVLINES  = "DIVLINES";
	public static String SUBCAPTION  = "SUBCAPTION";
	public static String TOOLTIP  = "TOOLTIP";
	public static String TRENDLINES  = "TRENDLINES";
	public static String VLINES   = "VLINES";
	public static String XAXISNAME    = "XAXISNAME";
	public static String YAXISNAME   = "YAXISNAME";
	public static String YAXISVALUES   = "YAXISVALUES";
	
	
	//<chart> element Attributes 元素属性 -----------------------------------------
	//Functional Attributes  功能属性
	private String animation  = null;			//设置是否显示动画
	private String palette   = null;			//设置调色板编号1-5
	private String paletteColors  = null;		//设置调色板的颜色,以逗号分割
	private String showAboutMenuItem  = null;	//设置是否显示关于菜单
	private String aboutMenuItemLabel  = null;	//设置关于菜单的标题
	private String aboutMenuItemLink  = null;	//设置关于菜单的链接
	private String showLabels  = null;			//设置是否显示X轴标签
	private String labelDisplay  = null;		//设置X轴标签的表现形式
	private String rotateLabels   = null;		//设置是否旋转X轴标签
	private String slantLabels   = null;		//设置是否倾斜X轴标签
	private String labelStep  = null;			//设置X轴标签的显示步长
	private String staggerLines   = null;		//设置X轴标签的错开数量(必须与labelDisplay ='STAGGER'结合使用)
	private String showValues   = null;			//设置是否显示Y轴的值
	private String rotateValues   = null;		//设置是否旋转Y轴的值
	private String placeValuesInside   = null;	//设置Value值是否在图形内部
	private String showYAxisValues   = null;	//设置是否显示Y轴的标签值
	private String showLimits   = null;			//设置是否限制Y轴最大值**(待定)
	private String showDivLineValues   = null;	//设置是否显示Y轴DIV线的值
	private String yAxisValuesStep    = null;	//设置Y轴标签的间隔
	private String showShadow    = null;		//设置是否显示阴影
	private String adjustDiv    = null;			//设置是否自动生成校准线
	private String rotateYAxisName    = null;	//设置是否旋转Y轴标题	
	private String yAxisNameWidth = null;		//设置Y轴标题的宽度
	private String clickURL    = null;			//设置点击链接
	private String defaultAnimation    = null;	//设置默认的动画的样式
	private String yAxisMinValue    = null;		//设置Y轴的最小值
	private String yAxisMaxValue    = null;		//设置Y轴的最大值
	private String setAdaptiveYMin = null;		//设置Y轴值的下限是否为0	
	private String maxColWidth    = null;		//设置允许的最大列宽<3D>
	private String use3DLighting    = null;		//设置是否显示动画<3D>
	
	
	private String exportEnabled = null;			//设置是否启用导出功能
	private String exportShowMenuItem = null;		//设置是否显示导出图片右菜单
	private String exportFileName = null;			//设置导出图片的名称
	private String exportFormats = null;			//设置导出图片的分割显示(检查API再使用)
	private String showExportDataMenuItem = null; 	//设置是否显示图表的XML菜单

	//Chart Titles and Axis Names 图表标题和表名称
	private String caption = null;				//设置图表的主标题
	private String subCaption  = null;			//设置图表的副标题
	private String xAxisName  = null;			//设置图表的X轴标签
	private String yAxisName  = null;			//设置图表的Y轴标签
	
	//Chart Cosmetics 图形美化
	
	private String bgColor  = null;				//设置背景色<可渐变>
	private String bgAlpha   = null;			//设置透明度<可渐变>
	private String bgRatio   = null;			//设置背景渐变比例<可渐变>
	private String bgAngle   = null;			//设置背景色填充角度<可渐变>
	private String bgSWF   = null;				//设置背景图片,SWF地址
	private String bgSWFAlpha   = null;			//设置背景图片,SWF的透明度
	private String canvasBgColor   = null;		//设置画布的背景色<可渐变>
	private String canvasBgAlpha  = null;		//设置画布渐变色透明度<可渐变>
	private String canvasBorderColor  = null;	//设置画布边框的颜色<2D>
	private String canvasBorderThickness= null;	//设置画布边框的厚度<2D>
	private String canvasBorderAlpha   = null;	//设置画布边框的透明度<2D>
	private String showBorder  = null;			//设置是否显示边框
	private String borderColor   = null;		//设置边框的颜色
	private String borderThickness   = null;	//设置边框的厚度,向里方向
	private String borderAlpha   = null;		///设置边框透明度
	private String showVLineLabelBorder = null;	//设置是否显示竖向趋势线的边框
	private String logoURL = null;				//设置LOGO的路径
	private String logoPosition  = null;		//设置LOGO的相对位置	
	private String logoAlpha  = null;			//设置LOGO的透明度
	private String logoScale  = null;			//设置LOGO的缩放比例
	private String logoLink  = null;			//设置LOGO的链接
	
	private String canvasBaseColor  = null;		//设置画布底部的背景颜色<3D>
	private String showCanvasBg  = null;		//设置是否显示画布背景<3D>
	private String showCanvasBase  = null;		//设置是否显示画布底部<3D>
	private String canvasBaseDepth  = null;		//设置画布底部的深度<3D>
	private String canvasBgDepth  = null;		//设置画布的深度<3D>
	
	
	//Data Plot Cosmetics  数据图块美化
	private String showPlotBorder = null;		//设置是否显示图块的边框
	private String plotBorderColor   = null;	//设置图块边框的颜色
	private String plotBorderAlpha  = null;		//设置图块边框的透明度
	private String plotFillAlpha = null;		//设置图块填充色的透明度
	private String overlapColumns   = null;		//设置图形条是否重叠<3D>
	
	
	//Divisional Lines & Grids 分区线和网格
	private String numDivLines = null;					//设置较准横线的数量
	private String divLineColor = null;					//设置较准横线的颜色
	private String divLineThickness = null;				//设置较准横线的厚度
	private String divLineAlpha = null;					//设置较准横线的透明度
	private String divLineIsDashed = null;				//设置较准横线是否以虚线表示
	private String divLineDashLen = null;				//设置较准横线虚线的大小
	private String divLineDashGap = null;				//设置较准横线虚线的间隔
	private String zeroPlaneColor = null;				//设置零基线的颜色
	private String zeroPlaneAlpha = null;				//设置零基线的透明度 
	private String zeroPlaneShowBorder  = null;			//设置是否显示零平面的边界<3D>
	private String zeroPlaneBorderColor  = null;		//设置零平面边界线边框的颜色<3D>
	
	//Number Formatting 	数字格式化
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
	private String yAxisValueDecimals = null;			//设置较准横线数值的小数位数,需要将自动生成校准横线(adjustDiv)设置为0,并且自定义校准横线的最大值(yAxisMaxValue)和最小值(yAxisMinValue)以及校准横线的数量(numVDivLines)		
	
	//Font Properties 字体属性
	private String baseFont = null;						//设置Value标签的字体
	private String baseFontSize = null;					//设置Value标签的字体大小
	private String baseFontColor = null;				//设置Value标签的字体颜色
	private String outCnvBaseFont  = null;				//设置画布的字体(不包含Value标签)
	private String outCnvBaseFontSize  = null;			//设置画布字体的大小(不包含Value标签)
	private String outCnvBaseFontColor  = null;			//设置画布字体的颜色(不包含Value标签)
	
	//Tool-tip 工具提示	---------------------------------------------
	private String showToolTip  = null;					//设置是否显示提示
	private String toolTipBgColor   = null;				//设置提示背景的颜色
	private String toolTipBorderColor   = null;			//设置提示背景框的颜色
	private String toolTipSepChar   = null;				//设置
	private String showToolTipShadow  = null;			//设置是否显示提示背景框的阴影

	//Chart Padding & Margins 图表边距
	private String captionPadding   = null;				//设置标题距离图表的边距
	private String xAxisNamePadding    = null;			//设置图表距离X轴标题的边距
	private String yAxisNamePadding    = null;			//设置图表距离Y轴标题的边距
	private String yAxisValuesPadding    = null;		//设置图表距离Y轴标签的边距
	private String labelPadding    = null;				//设置图表距离X轴标签的边距
	private String valuePadding    = null;				//设置图表距离图表显示值的边距
	private String plotSpacePercent    = null;			//设置图表柱子之间的距离<2D柱图>
	private String chartLeftMargin    = null;			//设置图表距离背景的左边距
	private String chartRightMargin    = null;			//设置图表距离背景的右边距
	private String chartTopMargin    = null;			//设置图表距离背景的上边距
	private String chartBottomMargin    = null;			//设置图表距离背景的下边距
	private String canvasPadding     = null;			//设置图表数据条距离画布的左右边距<3D>
	
	/**
	 * 生成图表的主体属性XML
	 * @return 图表的主体属性XML
	 */
	public String BuildChartXML(){
		StringBuffer chartXML = new StringBuffer();
		chartXML.append("<chart ");
			
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
		//设置X轴标签的表现形式
		if(this.getLabelDisplay()!=null){
			chartXML.append(" labelDisplay = '" +this.labelDisplay + "' \n");
		}
		//设置是否旋转X轴标签
		if(this.getRotateLabels()!=null){
			chartXML.append(" rotateLabels = '" +this.rotateLabels + "' \n");
		}
		//设置是否倾斜X轴标签
		if(this.getSlantLabels()!=null){
			chartXML.append(" slantLabels = '" +this.slantLabels + "' \n");
		}
		//设置X轴标签的显示步长
		if(this.getLabelStep()!=null){
			chartXML.append(" labelStep = '" +this.labelStep + "' \n");
		}
		//设置X轴标签的错开数量(必须与labelDisplay ='STAGGER'结合使用)
		if(this.getStaggerLines()!=null){
			chartXML.append(" staggerLines = '" +this.staggerLines + "' \n");
		}
		//设置是否显示Y轴的值
		if(this.getShowValues()!=null){
			chartXML.append(" showValues = '" +this.showValues + "' \n");
		}
		//设置是否旋转Y轴的值
		if(this.getRotateValues()!=null){
			chartXML.append(" rotateValues = '" +this.rotateValues + "' \n");
		}
		//设置Value值是否在图形内部
		if(this.getPlaceValuesInside()!=null){
			chartXML.append(" placeValuesInside = '" +this.placeValuesInside + "' \n");
		}
		//设置是否显示Y轴的标签值
		if(this.getShowYAxisValues()!=null){
			chartXML.append(" showYAxisValues = '" +this.showYAxisValues + "' \n");
		}
		//设置是否限制Y轴最大值**(待定)
		if(this.getShowLimits()!=null){
			chartXML.append(" showLimits = '" +this.showLimits + "' \n");
		}
		//设置是否显示Y轴DIV线的值
		if(this.getShowDivLineValues()!=null){
			chartXML.append(" showDivLineValues = '" +this.showDivLineValues + "' \n");
		}
		//设置Y轴标签的间隔
		if(this.getYAxisValuesStep()!=null){
			chartXML.append(" yAxisValuesStep = '" +this.yAxisValuesStep + "' \n");
		}
		//设置是否显示列的阴影
		if(this.getShowShadow()!=null){
			chartXML.append(" showShadow = '" +this.showShadow + "' \n");
		}
		//设置是否自动生成校准线
		if(this.getAdjustDiv()!=null){
			chartXML.append(" adjustDiv = '" +this.adjustDiv + "' \n");
		}
		//设置是否旋转Y轴标题
		if(this.getRotateYAxisName()!=null){
			chartXML.append(" rotateYAxisName = '" +this.rotateYAxisName + "' \n");
		}
		//设置Y轴标题的宽度
		if(this.getYAxisNameWidth()!=null){
			chartXML.append(" yAxisNameWidth = '" +this.yAxisNameWidth + "' \n");
		}
		//设置点击链接
		if(this.getClickURL()!=null){
			chartXML.append(" clickURL = '" +this.clickURL + "' \n");
		}
		//设置显示默认的动画
		if(this.getDefaultAnimation()!=null){
			chartXML.append(" defaultAnimation = '" +this.defaultAnimation + "' \n");
		}
		//设置Y轴的最小值
		if(this.getYAxisMinValue()!=null){
			chartXML.append(" yAxisMinValue = '" +this.yAxisMinValue + "' \n");
		}
		//设置Y轴的最大值
		if(this.getYAxisMaxValue()!=null){
			chartXML.append(" yAxisMaxValue = '" +this.yAxisMaxValue + "' \n");
		}
		//设置Y轴值的下限是否为0	
		if(this.getSetAdaptiveYMin()!=null){
			chartXML.append(" setAdaptiveYMin = '" +this.setAdaptiveYMin + "' \n");
		}
		//设置柱子的最大列宽
		if(this.getMaxColWidth()!=null){
			chartXML.append(" maxColWidth = '" +this.maxColWidth + "' \n");
		}
		//设置是否使用先进的动画效果
		if(this.getUse3DLighting()!=null){
			chartXML.append(" use3DLighting = '" +this.use3DLighting + "' \n");
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
		//设置图表的X轴标签
		if(this.getXAxisName()!=null){
			chartXML.append(" xAxisName = '" +this.xAxisName + "' \n");
		}
		//设置图表的Y轴标签
		if(this.getYAxisName()!=null){
			chartXML.append(" yAxisName = '" +this.yAxisName + "' \n");
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
		//设置画布的背景色<可渐变>
		if(this.getCanvasBgColor()!=null){
			chartXML.append(" canvasBgColor = '" +this.canvasBgColor + "' \n");
		}
		//设置画布渐变色透明度<可渐变>
		if(this.getCanvasBgAlpha()!=null){
			chartXML.append(" canvasBgAlpha = '" +this.canvasBgAlpha + "' \n");
		}
		//设置画布边框的颜色<2D>
		if(this.getCanvasBorderColor()!=null){
			chartXML.append(" canvasBorderColor = '" +this.canvasBorderColor + "' \n");
		}
		//设置画布边框的厚度<2D>
		if(this.getCanvasBorderThickness()!=null){
			chartXML.append(" canvasBorderThickness = '" +this.canvasBorderThickness + "' \n");
		}
		//设置画布边框的透明度<2D>
		if(this.getCanvasBorderAlpha()!=null){
			chartXML.append(" canvasBorderAlpha = '" +this.canvasBorderAlpha + "' \n");
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
		//设置是否显示竖向趋势线的边框
		if(this.getShowVLineLabelBorder()!=null){
			chartXML.append(" showVLineLabelBorder = '" +this.showVLineLabelBorder + "' \n");
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
		//设置图块边框的透明度
		if(this.getPlotBorderAlpha()!=null){
			chartXML.append(" plotBorderAlpha = '" +this.plotBorderAlpha + "' \n");
		}
		//设置图块填充色的透明度
		if(this.getPlotFillAlpha()!=null){
			chartXML.append(" plotFillAlpha = '" +this.plotFillAlpha + "' \n");
		}
		//设置图形条是否重叠
		if(this.getOverlapColumns()!=null){
			chartXML.append(" overlapColumns = '" +this.overlapColumns + "' \n");
		}
		
		//Divisional Lines & Grids 分区线和网格 -----------------------------------
		//设置较准横线的数量
		if(this.getNumDivLines()!=null){
			chartXML.append(" numDivLines = '" +this.numDivLines + "' \n");
		}
		//设置较准横线的颜色
		if(this.getDivLineColor()!=null){
			chartXML.append(" divLineColor = '" +this.divLineColor + "' \n");
		}
		//设置较准横线的厚度
		if(this.getDivLineThickness()!=null){
			chartXML.append(" divLineThickness = '" +this.divLineThickness + "' \n");
		}
		//设置较准横线的透明度
		if(this.getDivLineAlpha()!=null){
			chartXML.append(" divLineAlpha = '" +this.divLineAlpha + "' \n");
		}
		//设置较准横线是否以虚线表示
		if(this.getDivLineIsDashed()!=null){
			chartXML.append(" divLineIsDashed = '" +this.divLineIsDashed + "' \n");
		}
		//设置较准横线虚线的大小
		if(this.getDivLineDashLen()!=null){
			chartXML.append(" divLineDashLen = '" +this.divLineDashLen + "' \n");
		}
		//设置较准横线虚线的间隔
		if(this.getDivLineDashGap()!=null){
			chartXML.append(" divLineDashGap = '" +this.divLineDashGap + "' \n");
		}
		//设置零基线的颜色
		if(this.getZeroPlaneColor()!=null){
			chartXML.append(" zeroPlaneColor = '" +this.zeroPlaneColor + "' \n");
		}
		//设置零基线的透明度
		if(this.getZeroPlaneAlpha()!=null){
			chartXML.append(" zeroPlaneAlpha = '" +this.zeroPlaneAlpha + "' \n");
		}
		//设置是否显示零平面的边界
		if(this.getZeroPlaneShowBorder()!=null){
			chartXML.append(" zeroPlaneShowBorder = '" +this.zeroPlaneShowBorder + "' \n");
		}
		//设置零平面边界线边框的颜色
		if(this.getZeroPlaneBorderColor()!=null){
			chartXML.append(" zeroPlaneBorderColor = '" +this.zeroPlaneBorderColor + "' \n");
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
		//设置较准横线数值的小数位数,需要将自动生成校准横线(adjustDiv)设置为0,并且自定义校准横线的最大值(yAxisMaxValue)和最小值(yAxisMinValue)以及校准横线的数量(numVDivLines)
		if(this.getYAxisValueDecimals()!=null){
			chartXML.append(" yAxisValueDecimals = '" +this.yAxisValueDecimals + "' \n");
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
		//设置画布的字体(不包含Value标签)
		if(this.getOutCnvBaseFont()!=null){
			chartXML.append(" outCnvBaseFont = '" +this.outCnvBaseFont + "' \n");
		}
		//设置画布的字体(不包含Value标签)
		if(this.getOutCnvBaseFontSize()!=null){
			chartXML.append(" outCnvBaseFontSize = '" +this.outCnvBaseFontSize + "' \n");
		}
		//设置画布字体的颜色(不包含Value标签)
		if(this.getOutCnvBaseFontColor()!=null){
			chartXML.append(" outCnvBaseFontColor = '" +this.outCnvBaseFontColor + "' \n");
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
		//设置图表距离X轴标题的边距
		if(this.getXAxisNamePadding()!=null){
			chartXML.append(" xAxisNamePadding = '" +this.xAxisNamePadding + "' \n");
		}
		//设置图表距离Y轴标题的边距
		if(this.getYAxisNamePadding()!=null){
			chartXML.append(" yAxisNamePadding = '" +this.yAxisNamePadding + "' \n");
		}
		//设置图表距离Y轴标签的边距
		if(this.getYAxisValuesPadding()!=null){
			chartXML.append(" yAxisValuesPadding = '" +this.yAxisValuesPadding + "' \n");
		}
		//设置图表距离X轴标签的边距
		if(this.getLabelPadding()!=null){
			chartXML.append(" labelPadding = '" +this.labelPadding + "' \n");
		}
		//设置图表距离图表显示值的边距
		if(this.getValuePadding()!=null){
			chartXML.append(" valuePadding = '" +this.valuePadding + "' \n");
		}
		//设置图表柱子之间的距离<2D柱图>
		if(this.getPlotSpacePercent()!=null){
			chartXML.append(" plotSpacePercent = '" +this.plotSpacePercent + "' \n");
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
		//设置图表数据条距离画布的左右边距<3D>
		if(this.getCanvasPadding()!=null){
			chartXML.append(" canvasPadding = '" +this.canvasPadding + "' \n");
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
	 * 获取X轴标签的表现形式
	 * @return	取值范围(WRAP:,STAGGER:错开,ROTATE:旋转,NONE:无)
	 */
	public String getLabelDisplay() {
		return labelDisplay;
	}
	/**
	 * 设置X轴标签的表现形式
	 * @param labelDisplay	取值范围(WRAP:,STAGGER:错开,ROTATE:旋转,NONE:无)
	 */
	public void setLabelDisplay(String labelDisplay) {
		this.labelDisplay = labelDisplay;
	}
	/**
	 * 获取是否旋转X轴标签
	 * @return	取值范围(1:true 0:false)
	 */
	public String getRotateLabels() {
		return rotateLabels;
	}
	/**
	 * 设置是否旋转X轴标签
	 * @param rotateLabels	取值范围(1:true 0:false)
	 */
	public void setRotateLabels(String rotateLabels) {
		this.rotateLabels = rotateLabels;
	}
	/**
	 * 获取是否倾斜X轴标签
	 * @return	取值范围(1:true 0:false)
	 */
	public String getSlantLabels() {
		return slantLabels;
	}
	/**
	 * 设置是否倾斜X轴标签
	 * @param slantLabels	取值范围(1:true 0:false)
	 */
	public void setSlantLabels(String slantLabels) {
		this.slantLabels = slantLabels;
	}
	/**
	 * 获取X轴标签的显示步长
	 * @return	取值范围(大于等于1)
	 */
	public String getLabelStep() {
		return labelStep;
	}
	/**
	 * 设置X轴标签的显示步长
	 * @param labelStep	取值范围(大于等于1)
	 */
	public void setLabelStep(String labelStep) {
		this.labelStep = labelStep;
	}
	/**
	 * 获取X轴标签的错开数量
	 * 必须与labelDisplay ='STAGGER'结合使用
	 * @return	取值范围(大于等于2)
	 */
	public String getStaggerLines() {
		return staggerLines;
	}
	/**
	 * 设置X轴标签的错开数量
	 * 必须与labelDisplay ='STAGGER'结合使用
	 * @param staggerLines	取值范围(大于等于2)
	 */
	public void setStaggerLines(String staggerLines) {
		this.staggerLines = staggerLines;
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
	 *  获取是否旋转Y轴的值
	 * @return	取值范围(1:true 0:false)
	 */
	public String getRotateValues() {
		return rotateValues;
	}
	/**
	 * 设置是否旋转Y轴的值
	 * @param rotateValues	取值范围(1:true 0:false)
	 */
	public void setRotateValues(String rotateValues) {
		this.rotateValues = rotateValues;
	}
	
	/**
	 * 获取Value值是否在图形内部
	 * @return
	 */
	public String getPlaceValuesInside() {
		return placeValuesInside;
	}
	/**
	 * 设置Value值是否在图形内部
	 * @param placeValuesInside
	 */
	public void setPlaceValuesInside(String placeValuesInside) {
		this.placeValuesInside = placeValuesInside;
	}
	/**
	 * 获取是否显示Y轴的标签值
	 * @return
	 */
	public String getShowYAxisValues() {
		return showYAxisValues;
	}
	/**
	 * 设置是否显示Y轴的标签值
	 * @param showYAxisValues
	 */
	public void setShowYAxisValues(String showYAxisValues) {
		this.showYAxisValues = showYAxisValues;
	}
	/**
	 * 获取是否限制Y轴最大值**(待定)
	 * @return
	 */
	public String getShowLimits() {
		return showLimits;
	}
	/**
	 * 设置是否限制Y轴最大值**(待定)
	 * @param showLimits
	 */
	public void setShowLimits(String showLimits) {
		this.showLimits = showLimits;
	}
	/**
	 * 获取是否显示Y轴DIV线的值
	 * @return
	 */
	public String getShowDivLineValues() {
		return showDivLineValues;
	}
	/**
	 * 设置是否显示Y轴DIV线的值
	 * @param showDivLineValues
	 */
	public void setShowDivLineValues(String showDivLineValues) {
		this.showDivLineValues = showDivLineValues;
	}
	/**
	 * 获取Y轴标签的间隔
	 * @return
	 */
	public String getYAxisValuesStep() {
		return yAxisValuesStep;
	}
	/**
	 * 设置Y轴标签的间隔
	 * @param axisValuesStep
	 */
	public void setYAxisValuesStep(String axisValuesStep) {
		yAxisValuesStep = axisValuesStep;
	}
	/**
	 * 获取是否显示列的阴影
	 * @return
	 */
	public String getShowShadow() {
		return showShadow;
	}
	/**
	 * 设置是否显示列的阴影
	 * @param showShadow
	 */
	public void setShowShadow(String showShadow) {
		this.showShadow = showShadow;
	}
	/**
	 * 获取是否自动生成校准线
	 * @return
	 */
	public String getAdjustDiv() {
		return adjustDiv;
	}
	/**
	 * 设置是否自动生成校准线
	 * @param adjustDiv
	 */
	public void setAdjustDiv(String adjustDiv) {
		this.adjustDiv = adjustDiv;
	}
	/**
	 * 获取是否旋转Y轴数据,默认为1:不旋转
	 * @return
	 */
	public String getRotateYAxisName() {
		return rotateYAxisName;
	}
	/**
	 * 设置是否旋转Y轴数据,默认为1:不旋转;
	 * @param rotateYAxisName (1:不旋转,0:旋转)
	 */
	public void setRotateYAxisName(String rotateYAxisName) {
		this.rotateYAxisName = rotateYAxisName;
	}
	
	/**
	 * 获取Y轴标题的宽度
	 * @return
	 */
	public String getYAxisNameWidth() {
		return yAxisNameWidth;
	}
	/**
	 * 设置Y轴标题的宽度
	 * @param axisNameWidth
	 */
	public void setYAxisNameWidth(String axisNameWidth) {
		yAxisNameWidth = axisNameWidth;
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
	 * 获取Y轴的最小值
	 * @return
	 */
	public String getYAxisMinValue() {
		return yAxisMinValue;
	}
	/**
	 * 设置Y轴的最小值
	 * @param axisMinValue
	 */
	public void setYAxisMinValue(String axisMinValue) {
		yAxisMinValue = axisMinValue;
	}
	/**
	 * 获取Y轴的最大值
	 * @return
	 */
	public String getYAxisMaxValue() {
		return yAxisMaxValue;
	}
	/**
	 * 设置Y轴的最大值
	 * @param axisMaxValue
	 */
	public void setYAxisMaxValue(String axisMaxValue) {
		yAxisMaxValue = axisMaxValue;
	}
	/**
	 * 获取Y轴值的下限是否为0
	 * @return
	 */
	public String getSetAdaptiveYMin() {
		return setAdaptiveYMin;
	}
	/**
	 * 设置Y轴值的下限是否为0
	 * @param setAdaptiveYMin
	 */
	public void setSetAdaptiveYMin(String setAdaptiveYMin) {
		this.setAdaptiveYMin = setAdaptiveYMin;
	}
	/**
	 * 获取柱子的最大列宽<3D>
	 * @return
	 */
	public String getMaxColWidth() {
		return maxColWidth;
	}
	/**
	 * 设置柱子的最大列宽<3D>
	 * @param maxColWidth
	 */
	public void setMaxColWidth(String maxColWidth) {
		this.maxColWidth = maxColWidth;
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
	/**
	 * 获取图表的X轴标签
	 * @return
	 */
	public String getXAxisName() {
		return xAxisName;
	}
	/**
	 * 设置图表的X轴标签
	 * @param axisName
	 */
	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}
	/**
	 * 获取图表的Y轴标签
	 * @return
	 */
	public String getYAxisName() {
		return yAxisName;
	}
	/**
	 * 设置图表的Y轴标签
	 * @param axisName
	 */
	public void setYAxisName(String axisName) {
		yAxisName = axisName;
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
	 * 获取画布背景颜色<可渐变>
	 * @return Hex Color Code16
	 */
	public String getCanvasBgColor() {
		return canvasBgColor;
	}
	/**
	 * 设置画布背景颜色<可渐变>
	 * @param canvasBgColor Hex Color Code16
	 */
	public void setCanvasBgColor(String canvasBgColor) {
		this.canvasBgColor = canvasBgColor;
	}
	/**
	 * 获取画布背景颜色的透明度<可渐变>
	 * @return
	 */
	public String getCanvasBgAlpha() {
		return canvasBgAlpha;
	}
	/**
	 * 设置画布背景颜色的透明度<可渐变>
	 * @param canvasBgAlpha,取值范围(1-100)
	 */
	public void setCanvasBgAlpha(String canvasBgAlpha) {
		this.canvasBgAlpha = canvasBgAlpha;
	}
	/**
	 * 获取画布边框的颜色 <2D>
	 * @return Hex Color Code16
	 */
	public String getCanvasBorderColor() {
		return canvasBorderColor;
	}
	/**
	 * 设置画布边框的颜色 <2D>
	 * @param canvasBorderColor Hex Color Code16
	 */
	public void setCanvasBorderColor(String canvasBorderColor) {
		this.canvasBorderColor = canvasBorderColor;
	}
	/**
	 * 获取画布边框的厚度 <2D>
	 * @return
	 */
	public String getCanvasBorderThickness() {
		return canvasBorderThickness;
	}
	/**
	 * 设置画布边框的厚度 <2D>
	 * @param canvasBorderThickness
	 */
	public void setCanvasBorderThickness(String canvasBorderThickness) {
		this.canvasBorderThickness = canvasBorderThickness;
	}
	/**
	 * 获取画布边框的透明度 <2D>
	 * @return
	 */
	public String getCanvasBorderAlpha() {
		return canvasBorderAlpha;
	}
	/**
	 * 设置画布边框的透明度 <2D>
	 * @param canvasBorderAlpha
	 */
	public void setCanvasBorderAlpha(String canvasBorderAlpha) {
		this.canvasBorderAlpha = canvasBorderAlpha;
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
	 * 获取是否显示竖向趋势线的边框		
	 * @return
	 */	
	public String getShowVLineLabelBorder() {
		return showVLineLabelBorder;
	}
	/**
	 * 设置是否显示竖向趋势线的边框		
	 * @param showVLineLabelBorder
	 */
	public void setShowVLineLabelBorder(String showVLineLabelBorder) {
		this.showVLineLabelBorder = showVLineLabelBorder;
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
	/**
	 * 获取画布底部的背景颜色<3D>
	 * @return
	 */
	public String getCanvasBaseColor() {
		return canvasBaseColor;
	}
	/**
	 * 设置画布底部的背景颜色<3D>
	 * @param canvasBaseColor
	 */
	public void setCanvasBaseColor(String canvasBaseColor) {
		this.canvasBaseColor = canvasBaseColor;
	}
	/**
	 * 获取是否显示画布背景<3D>
	 * @return
	 */
	public String getShowCanvasBg() {
		return showCanvasBg;
	}
	/**
	 * 配置是否显示画布背景<3D>
	 * @param showCanvasBg
	 */
	public void setShowCanvasBg(String showCanvasBg) {
		this.showCanvasBg = showCanvasBg;
	}
	/**
	 * 获取是否显示画布底部<3D>
	 * @return
	 */
	public String getShowCanvasBase() {
		return showCanvasBase;
	}
	/**
	 * 配置是否显示画布底部<3D>
	 * @param showCanvasBase
	 */
	public void setShowCanvasBase(String showCanvasBase) {
		this.showCanvasBase = showCanvasBase;
	}
	/**
	 * 获取画布底部的深度<3D>
	 * @return
	 */
	public String getCanvasBaseDepth() {
		return canvasBaseDepth;
	}
	/**
	 * 配置画布底部的深度<3D>
	 * @param canvasBaseDepth
	 */
	public void setCanvasBaseDepth(String canvasBaseDepth) {
		this.canvasBaseDepth = canvasBaseDepth;
	}
	/**
	 * 获取画布的深度<3D>
	 * @return
	 */
	public String getCanvasBgDepth() {
		return canvasBgDepth;
	}
	/**
	 * 配置画布的深度<3D>
	 * @param canvasBgDepth
	 */
	public void setCanvasBgDepth(String canvasBgDepth) {
		this.canvasBgDepth = canvasBgDepth;
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
	 * 获取图形条是否重叠<3D>
	 * @return
	 */
	public String getOverlapColumns() {
		return overlapColumns;
	}
	/**
	 * 设置图形条是否重叠<3D>
	 * @param overlapColumns
	 */
	public void setOverlapColumns(String overlapColumns) {
		this.overlapColumns = overlapColumns;
	}
	
	//Divisional Lines & Grids 分区线和网格
	/**
	 * 获取校准横线的数量
	 * @return
	 */
	public String getNumDivLines() {
		return numDivLines;
	}
	/**
	 * 设置校准横线的数量
	 * @param numDivLines
	 */
	public void setNumDivLines(String numDivLines) {
		this.numDivLines = numDivLines;
	}
	/**
	 * 获取校准横线的颜色
	 * @return Hex Color Code16
	 */
	public String getDivLineColor() {
		return divLineColor;
	}
	/**
	 * 设置校准横线的颜色
	 * @param divLineColor Hex Color Code16
	 */
	public void setDivLineColor(String divLineColor) {
		this.divLineColor = divLineColor;
	}
	/**
	 * 获取校准横线的厚度
	 * @return
	 */
	public String getDivLineThickness() {
		return divLineThickness;
	}
	/**
	 * 设置校准横线的厚度
	 * @param divLineThickness
	 */
	public void setDivLineThickness(String divLineThickness) {
		this.divLineThickness = divLineThickness;
	}
	/**
	 * 获取校准横线的透明度
	 * @return
	 */
	public String getDivLineAlpha() {
		return divLineAlpha;
	}
	/**
	 * 设置校准横线的透明度
	 * @param divLineAlpha
	 */
	public void setDivLineAlpha(String divLineAlpha) {
		this.divLineAlpha = divLineAlpha;
	}

	/**
	 * 获取校准横线是否以虚线表示
	 * @return
	 */
	public String getDivLineIsDashed() {
		return divLineIsDashed;
	}
	/**
	 * 设置校准横线是否以虚线表示
	 * @param divLineIsDashed
	 */
	public void setDivLineIsDashed(String divLineIsDashed) {
		this.divLineIsDashed = divLineIsDashed;
	}
	/**
	 * 获取校准横线以虚线表示后,虚线的长度
	 * @return
	 */
	public String getDivLineDashLen() {
		return divLineDashLen;
	}
	/**
	 * 设置校准横线以虚线表示后,虚线的长度
	 * 需要配合设置校准横线是否以虚线表示(setDivLineIsDashed方法)使用
	 * @param divLineDashLen
	 */
	public void setDivLineDashLen(String divLineDashLen) {
		this.divLineDashLen = divLineDashLen;
	}
	/**
	 * 获取置校准横线以虚线表示后,虚线的间隔
	 * @return
	 */
	public String getDivLineDashGap() {
		return divLineDashGap;
	}
	/**
	 * 设置校准横线以虚线表示后,虚线的间隔
	 * 需要配合设置校准横线是否以虚线表示(setDivLineIsDashed方法)使用
	 * @param divLineDashGap
	 */
	public void setDivLineDashGap(String divLineDashGap) {
		this.divLineDashGap = divLineDashGap;
	}
	/**
	 * 获取零基线的颜色
	 * @return Hex Color Code16
	 */
	public String getZeroPlaneColor() {
		return zeroPlaneColor;
	}
	/**
	 * 设置零基线的颜色
	 * @param zeroPlaneColor Hex Color Code16
	 */
	public void setZeroPlaneColor(String zeroPlaneColor) {
		this.zeroPlaneColor = zeroPlaneColor;
	}
	/**
	 * 获取零基线的透明度
	 * @return
	 */
	public String getZeroPlaneAlpha() {
		return zeroPlaneAlpha;
	}
	/**
	 * 设置零基线的透明度
	 * @param zeroPlaneAlpha
	 */
	public void setZeroPlaneAlpha(String zeroPlaneAlpha) {
		this.zeroPlaneAlpha = zeroPlaneAlpha;
	}
	/**
	 * 获取是否显示零平面的边界<3D>
	 * @return
	 */
	public String getZeroPlaneShowBorder() {
		return zeroPlaneShowBorder;
	}
	/**
	 * 设置是否显示零平面的边界<3D>
	 * @param zeroPlaneShowBorder
	 */
	public void setZeroPlaneShowBorder(String zeroPlaneShowBorder) {
		this.zeroPlaneShowBorder = zeroPlaneShowBorder;
	}
	/**
	 * 获取零平面边界线边框的颜色<3D>
	 * @return
	 */
	public String getZeroPlaneBorderColor() {
		return zeroPlaneBorderColor;
	}
	/**
	 * 设置零平面边界线边框的颜色<3D>
	 * @param zeroPlaneBorderColor
	 */
	public void setZeroPlaneBorderColor(String zeroPlaneBorderColor) {
		this.zeroPlaneBorderColor = zeroPlaneBorderColor;
	}
	//Number Formatting 数字格式化 ---------------------------------------
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
	/**
	 * 获取较准横线数值的小数位数,
	 * 需要将自动生成校准横线(adjustDiv)设置为0,
	 * 并且自定义校准横线的最大值(yAxisMaxValue)和最小值(yAxisMinValue)
	 * 以及校准横线的数量(numVDivLines)
	 * @return
	 */
	public String getYAxisValueDecimals() {
		return yAxisValueDecimals;
	}
	/**
	 * 设置较准横线数值的小数位数,
	 * 需要将自动生成校准横线(adjustDiv)设置为0,
	 * 并且自定义校准横线的最大值(yAxisMaxValue)和最小值(yAxisMinValue)
	 * 以及校准横线的数量(numVDivLines)
	 * @param axisValueDecimals
	 */
	public void setYAxisValueDecimals(String axisValueDecimals) {
		yAxisValueDecimals = axisValueDecimals;
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
	/**
	 * 获取画布的字体(不包含Value标签)
	 * @return
	 */
	public String getOutCnvBaseFont() {
		return outCnvBaseFont;
	}
	/**
	 * 设置画布的字体(不包含Value标签)
	 * @param outCnvBaseFont
	 */
	public void setOutCnvBaseFont(String outCnvBaseFont) {
		this.outCnvBaseFont = outCnvBaseFont;
	}
	/**
	 * 获取画布字体的大小(不包含Value标签)
	 * @return
	 */
	public String getOutCnvBaseFontSize() {
		return outCnvBaseFontSize;
	}
	/**
	 * 设置画布字体的大小(不包含Value标签)
	 * @param outCnvBaseFontSize
	 */
	public void setOutCnvBaseFontSize(String outCnvBaseFontSize) {
		this.outCnvBaseFontSize = outCnvBaseFontSize;
	}
	/**
	 * 获取布字体的颜色(不包含Value标签)
	 * @return
	 */
	public String getOutCnvBaseFontColor() {
		return outCnvBaseFontColor;
	}
	/**
	 * 设置布字体的颜色(不包含Value标签)
	 * @param outCnvBaseFontColor
	 */
	public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
		this.outCnvBaseFontColor = outCnvBaseFontColor;
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
	/**
	 * 获取图表距离X轴标题的边距
	 * @return
	 */
	public String getXAxisNamePadding() {
		return xAxisNamePadding;
	}
	/**
	 * 设置图表距离X轴标题的边距
	 * @param axisNamePadding
	 */
	public void setXAxisNamePadding(String axisNamePadding) {
		xAxisNamePadding = axisNamePadding;
	}
	/**
	 * 获取图表距离X轴标签的边距
	 * @return
	 */
	public String getLabelPadding() {
		return labelPadding;
	}
	/**
	 * 设置图表距离X轴标签的边距
	 * @param labelPadding
	 */
	public void setLabelPadding(String labelPadding) {
		this.labelPadding = labelPadding;
	}
	/**
	 * 获取图表距离Y轴标题的边距
	 * @return
	 */
	public String getYAxisNamePadding() {
		return yAxisNamePadding;
	}
	/**
	 * 设置图表距离Y轴标题的边距
	 * @param axisNamePadding
	 */
	public void setYAxisNamePadding(String axisNamePadding) {
		yAxisNamePadding = axisNamePadding;
	}
	/**
	 * 获取图表距离Y轴标签的边距
	 * @return
	 */
	public String getYAxisValuesPadding() {
		return yAxisValuesPadding;
	}
	/**
	 * 设置图表距离Y轴标签的边距
	 * @param axisValuesPadding
	 */
	public void setYAxisValuesPadding(String axisValuesPadding) {
		yAxisValuesPadding = axisValuesPadding;
	}
	/**
	 * 获取图表距离图表显示值的边距
	 * @return
	 */
	public String getValuePadding() {
		return valuePadding;
	}
	/**
	 * 设置图表距离图表显示值的边距
	 * @param valuePadding
	 */
	public void setValuePadding(String valuePadding) {
		this.valuePadding = valuePadding;
	}
	/**
	 * 获取图表柱子之间的距离<2D柱图>
	 * @return
	 */
	public String getPlotSpacePercent() {
		return plotSpacePercent;
	}
	/**
	 * 设置图表柱子之间的距离<2D柱图>
	 * @param plotSpacePercent
	 */
	public void setPlotSpacePercent(String plotSpacePercent) {
		this.plotSpacePercent = plotSpacePercent;
	}
	/**
	 * 获取图表数据条距离画布的左右边距<3D>
	 * @return
	 */
	public String getCanvasPadding() {
		return canvasPadding;
	}
	/**
	 * 设置图表数据条距离画布的左右边距<3D>
	 * @param canvasPadding
	 */
	public void setCanvasPadding(String canvasPadding) {
		this.canvasPadding = canvasPadding;
	}
}
