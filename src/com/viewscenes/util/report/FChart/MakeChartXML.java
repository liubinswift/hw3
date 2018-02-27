package com.viewscenes.util.report.FChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetTool;
import com.viewscenes.util.report.util.DateStyle;
import com.viewscenes.util.report.util.MathRound;
import com.viewscenes.util.report.util.TimeHelper;
/**
 * 2009-06-05 第一版
 * @author liqing
 * 
 */
public class MakeChartXML {
	public static String endstrXML ="</chart>";
	/**
	 * 为了避免每次写画图代码时都要进行对图片的处理,
	 * 写一个简单的类似与CSS的样式,
	 * 如果想个性表现图表请在这个方法运行之后再写个人的代码!
	 * 此基本样式.某位同志要修改其中的值这也算个例子
	 * 
	 * @param gbean
	 * @return GraphBean
	 */
	public GraphBean BuildingGbean(GraphBean gbean){
		gbean.setBaseFontSize("12");
		gbean.setDecimalPrecision("2");
		gbean.setShowValues("0");
		gbean.setFormatNumberScale("0");
		//gbean.setShowBorder("1");
//		gbean.setRotateNames("1");
		gbean.setRotateYAxisName("1");
//		gbean.setNumdivlines("4");
		gbean.setDivLineDecimalPrecision("1");
		gbean.setLimitsDecimalPrecision("1");
		gbean.setAnimation("0");
		gbean.setThousandSeparator("");
//		gbean.setXAxisName("日期");
		return gbean;
	}
	/**
	 * 简单的存放SetBean中
	 * @deprecated
	 * @author liqing
	 * @param gd
	 * @param gbean
	 * @return
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(GDSet gd,GraphBean gbean) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		for (int i = 0; i < gd.getRowCount(); i++) {
			sbean = new SetBean();
			sbean.setLabel(gd.getCell(i, 0));
//			sbean.setHoverText(gd.getCell(i, 0));
			sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
			sbean.setHoverText(gd.getCell(i, 0)+","+sbean.getValue());
			list.add(sbean);
		}
		return list;
	}
	/**
	 * 简单的存放SetBean中
	 * @deprecated
	 * @author liqing
	 * @param gd
	 * @param gbean
	 * @param DateStyle
	 * @return
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(GDSet gd,GraphBean gbean,String datestyle) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		TimeHelper time=null;
		for (int i = 0; i < gd.getRowCount(); i++) {
			sbean = new SetBean();
			time =new TimeHelper(gd.getCell(i,0));
			sbean.setLabel(time.toLocStdString(datestyle));
//			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
			sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
			list.add(sbean);
		}
		return list;
	}
	/**
	 * 把数据库中查询出来的结果.
	 * 简单的放到SetBean中,
	 * 再用BuildingSbean做具体的美化工作.
	 * @param gd
	 * @param gbean
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(GDSet gd,GraphBean gbean,int ReturnType) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		if(ReturnType==GDSetTool.Return_ArrayListCol||ReturnType==GDSetTool.Return_ArrayListRow){
		for (int i = 0; i < gd.getRowCount(); i++) {
			sbean = new SetBean();
			sbean.setLabel(gd.getCell(i, 0));
//			sbean.setHoverText(gd.getCell(i, 0));
			sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
			sbean.setHoverText(gd.getCell(i, 0)+","+sbean.getValue());
			list.add(sbean);
		}
		}else if(ReturnType==GDSetTool.Return_ArrayListColWithTitle||ReturnType==GDSetTool.Return_ArrayListRowWithTitle){
			for (int i = 1; i < gd.getRowCount(); i++) {
				sbean = new SetBean();
				sbean.setLabel(gd.getCell(i, 0));
//				sbean.setHoverText(gd.getCell(i, 0));
				sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
				sbean.setHoverText(gd.getCell(i, 0)+","+sbean.getValue());
				list.add(sbean);
			}
		}
		return list;
	}
	/**
	 * 把数据库中查询出来的结果.
	 * 简单的放到SetBean中,
	 * 再用BuildingSbean做具体的美化工作.
	 * @param gd
	 * @param gbean
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(GDSet gd,GraphBean gbean,String datestyle,int ReturnType) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		TimeHelper time=null;
		if(ReturnType==GDSetTool.Return_ArrayListCol||ReturnType==GDSetTool.Return_ArrayListRow){
		for (int i = 0; i < gd.getRowCount(); i++) {
			sbean = new SetBean();
			time =new TimeHelper(gd.getCell(i,0));
			sbean.setLabel(time.toLocStdString(datestyle));
//			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
			sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
			list.add(sbean);
		}
		}else if(ReturnType==GDSetTool.Return_ArrayListColWithTitle||ReturnType==GDSetTool.Return_ArrayListRowWithTitle){
			for (int i = 1; i < gd.getRowCount(); i++) {
				sbean = new SetBean();
				time =new TimeHelper(gd.getCell(i,0));
				sbean.setLabel(time.toLocStdString(datestyle));
//				sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
				sbean.setValue(MathRound.round(gd.getCell(i, 1),Integer.parseInt(gbean.getDecimalPrecision())));
				sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
				list.add(sbean);
			}
		}
		return list;
	}
	/**
	 * 把数据库中查询出来的结果.
	 * 简单的放到SetBean中,
	 * 再用BuildingSbean做具体的美化工作.
	 * @param datalist
	 * @param gbean
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(ArrayList datalist,GraphBean gbean,int ReturnType) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		//付费版需要修改setHoverText成Label+“,”+Value型，free版不要 Value
        if(ReturnType==GDSetTool.Return_ArrayListRow){
	        if (!(datalist == null || datalist.size() == 0)) {
	            ArrayList templist = (ArrayList) datalist.get(0);
	            if (templist == null || templist.size() == 0) {
	                
	            } else { //表示有数据
	            	for (int i = 0; i < datalist.size(); i++) {
	        			sbean = new SetBean();
	        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(i)).get(1).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
//	        			sbean.setLabel(((ArrayList)datalist.get(i)).get(0).toString()+"\n"+sbean.getValue()+"%25");
	        			sbean.setLabel(((ArrayList)datalist.get(i)).get(0).toString());
	        			sbean.setHoverText(((ArrayList)datalist.get(i)).get(0).toString()+","+sbean.getValue());
	        			list.add(sbean);
	        		}
	            }
	        }
        }else if(ReturnType==GDSetTool.Return_ArrayListRowWithTitle){
        	if (!(datalist == null || datalist.size() <= 1)) {
                ArrayList templist = (ArrayList) datalist.get(0);
                if (templist == null || templist.size() == 0) {
                    
                } else { //表示有数据
                	for (int i = 1; i < datalist.size(); i++) {
	        			sbean = new SetBean();
	        			sbean.setLabel(((ArrayList)datalist.get(i)).get(0).toString());
//	        			sbean.setHoverText(((ArrayList)datalist.get(i)).get(0).toString());
	        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(i)).get(1).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
	        			sbean.setHoverText(((ArrayList)datalist.get(i)).get(0).toString()+","+sbean.getValue());
	        			list.add(sbean);
                    }
                }
            }
	    }else if(ReturnType==GDSetTool.Return_ArrayListCol){
	        if (!(datalist == null || datalist.size() == 0)) {
	            ArrayList templist = (ArrayList) datalist.get(0);
	            if (templist == null || templist.size() == 0) {
	                
	            } else { //表示有数据
	                for (int i = 0; i < templist.size(); i++) {
	                	sbean = new SetBean();
	        			sbean.setLabel(((ArrayList)datalist.get(0)).get(i).toString());
//	        			sbean.setHoverText(((ArrayList)datalist.get(0)).get(i).toString());
	        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(1)).get(i).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
	        			sbean.setHoverText(((ArrayList)datalist.get(0)).get(i).toString()+","+sbean.getValue());
	        			list.add(sbean);
	                }
	            }
	        }
	    }else if(ReturnType==GDSetTool.Return_ArrayListColWithTitle){
	    	 if (!(datalist == null || datalist.size() == 0)) {
		            ArrayList templist = (ArrayList) datalist.get(0);
		            if (templist == null || templist.size() == 0) {
		                
		            } else { //表示有数据
		                for (int i = 1; i < list.size(); i++) {
		                	sbean = new SetBean();
		        			sbean.setLabel(((ArrayList)datalist.get(0)).get(i).toString());
//		        			sbean.setHoverText(((ArrayList)datalist.get(0)).get(i).toString());
		        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(1)).get(i).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
		        			sbean.setHoverText(((ArrayList)datalist.get(0)).get(i).toString()+","+sbean.getValue());
		        			list.add(sbean);
		                }
		            }
		        }
	    }
		return list;
	}
	/**
	 * 把数据库中查询出来的结果.
	 * 简单的放到SetBean中,
	 * 再用BuildingSbean做具体的美化工作.
	 * @param datalist
	 * @param gbean
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList BuildingGDSet(ArrayList datalist,GraphBean gbean,String datestyle,int ReturnType) throws Exception{
		ArrayList list =new ArrayList();
		SetBean sbean=null;
		TimeHelper time=null;
		if(ReturnType==GDSetTool.Return_ArrayListRow){
	        if (!(datalist == null || datalist.size() == 0)) {
	            ArrayList templist = (ArrayList) datalist.get(0);
	            if (templist == null || templist.size() == 0) {
	                
	            } else { //表示有数据
	            	for (int i = 0; i < datalist.size(); i++) {
	            		sbean = new SetBean();
	        			time =new TimeHelper(((ArrayList)datalist.get(i)).get(0).toString());
	        			sbean.setLabel(time.toLocStdString(datestyle));
//	        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
	        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(i)).get(1).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
	        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
	        			list.add(sbean);
	        		}
	            }
	        }
        }else if(ReturnType==GDSetTool.Return_ArrayListRowWithTitle){
        	if (!(datalist == null || datalist.size() <= 1)) {
                ArrayList templist = (ArrayList) datalist.get(0);
                if (templist == null || templist.size() == 0) {
                    
                } else { //表示有数据
                	for (int i = 1; i < datalist.size(); i++) {
                		sbean = new SetBean();
            			time =new TimeHelper(((ArrayList)datalist.get(i)).get(0).toString());
            			sbean.setLabel(time.toLocStdString(datestyle));
//            			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
            			sbean.setValue(MathRound.round(((ArrayList)datalist.get(i)).get(1).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
            			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
            			list.add(sbean);
                    }
                }
            }
	    }else if(ReturnType==GDSetTool.Return_ArrayListCol){
	        if (!(datalist == null || datalist.size() == 0)) {
	            ArrayList templist = (ArrayList) datalist.get(0);
	            if (templist == null || templist.size() == 0) {
	                
	            } else { //表示有数据
	                for (int i = 0; i < templist.size(); i++) {
	                	sbean = new SetBean();
	        			time =new TimeHelper(((ArrayList)datalist.get(0)).get(i).toString());
	        			sbean.setLabel(time.toLocStdString(datestyle));
//	        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
	        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(1)).get(i).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
	        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
	        			list.add(sbean);
	                }
	            }
	        }
	    }else if(ReturnType==GDSetTool.Return_ArrayListColWithTitle){
	    	 if (!(datalist == null || datalist.size() == 0)) {
		            ArrayList templist = (ArrayList) datalist.get(0);
		            if (templist == null || templist.size() == 0) {
		                
		            } else { //表示有数据
		                for (int i = 1; i < list.size(); i++) {
		                	sbean = new SetBean();
		        			time =new TimeHelper(((ArrayList)datalist.get(0)).get(i).toString());
		        			sbean.setLabel(time.toLocStdString(datestyle));
//		        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY));
		        			sbean.setValue(MathRound.round(((ArrayList)datalist.get(1)).get(i).toString(),Integer.parseInt(gbean.getDecimalPrecision())));
		        			sbean.setHoverText(time.toLocStdString(DateStyle.TO_DAY)+","+sbean.getValue());
		        			list.add(sbean);
		                }
		            }
		        }
	    }
		return list;
	}
	
	/**
	 * 柱状图区分颜色
	 * @return ArrayList
	 */
	public ArrayList BuildingSbean(ArrayList list){
		for (int i = 0; i < list.size(); i++) {
			if(SetBean.colorlist.size()-1>i){
				((SetBean)list.get(i)).setColor(SetBean.colorlist.get(i).toString());
			}else{
				((SetBean)list.get(i)).setColor("000000");
			}
		}
		return list;
	}
	/**
	 * 在X轴上只显示8个坐标
	 * 
	 * @author liqing
	 * @param list
	 * @param num
	 * @return
	 */
	public ArrayList BuildX7Sbean(ArrayList list,int num){
		if(list.size()>num){
			int step=Integer.parseInt(MathRound.round(list.size()/7,0));
			for (int i = 1; i < list.size()-1; i++) {
				if(!(step==i||step*2==i||step*3==i||step*4==i||step*5==i||step*6==i)){
					((SetBean)list.get(i)).setLabel("");
				}
			}
		}
		return list;
	}
	/**
	 * 当没数据时 补0
	 * @author liqing
	 * @param list
	 * @return
	 */
	public void Zero(ArrayList list,TimeHelper stime,TimeHelper etime,String datestyle){
		SetBean sbean=null;
		etime.addDay(1);
		boolean flag=true;
		int i=0;
		while(!stime.getThisDay().equals(etime.getThisDay())){
		for (int j = 0; j < list.size(); j++) {
			if(((SetBean)list.get(j)).getHoverText().startsWith(stime.toLocStdString(DateStyle.TO_DAY))){
				flag=false;
			}
		}
		if(flag){
			sbean = new SetBean();
			sbean.setLabel(stime.toLocStdString(datestyle));
//			sbean.setHoverText(stime.toLocStdString(DateStyle.TO_DAY));
			sbean.setValue(0+"");
			sbean.setHoverText(stime.toLocStdString(DateStyle.TO_DAY)+",0");
			sbean.setLink("javaScript%3Achangedate%28%22"+stime.toLocStdString(DateStyle.TO_DAY)+"%22%29%3B");
			list.add(i,sbean);
		}
		stime.addDay(1);
		flag=true;
		i++;
		}
		
	}
	/**
	 * 建立chart的XML字符串
	 * @param 标题
	 * @param X轴序列
	 * @param Y轴单位
	 * @param 是否显示值
	 * @param 保留几位小数
	 * @param 显示边框
	 * @param 结果集
	 * @return XML
	 */
	public String BuildGraphXML(GraphBean gbean) throws Exception {
		String strXML="";
		strXML +="<chart ";
		if(gbean.getRotateYAxisName()!=null){strXML+="rotateYAxisName='"+gbean.getRotateYAxisName ()+"' "; }
		if(gbean.getBgColor()!=null){strXML+="bgColor='"+gbean.getBgColor()+"' "; }
		if(gbean.getBgAlpha()!=null){strXML+="bgAlpha='"+gbean.getBgAlpha()+"' "; }
		if(gbean.getBgSWF()!=null){strXML+="bgSWF='"+gbean.getBgSWF()+"' "; }
		if(gbean.getCanvasBgColor()!=null){strXML+="canvasBgColor='"+gbean.getCanvasBgColor()+"' "; }
		if(gbean.getCanvasBaseColor()!=null){strXML+="canvasBaseColor='"+gbean.getCanvasBaseColor()+"' "; }
		if(gbean.getCanvasBaseDepth()!=null){strXML+="canvasBaseDepth='"+gbean.getCanvasBaseDepth()+"' "; }
		if(gbean.getCanvasBgDepth()!=null){strXML+="canvasBgDepth='"+gbean.getCanvasBgDepth()+"' "; }
		if(gbean.getShowCanvasBg()!=null){strXML+="showCanvasBg='"+gbean.getShowCanvasBg()+"' "; }
		if(gbean.getShowCanvasBase()!=null){strXML+="showCanvasBase='"+gbean.getShowCanvasBase()+"' "; }
		if(gbean.getCaption ()!=null){strXML+="caption='"+gbean.getCaption ()+"' "; }
		if(gbean.getSubCaption ()!=null){strXML+="subCaption='"+gbean.getSubCaption ()+"' "; }
		if(gbean.getXAxisName ()!=null){strXML+="xAxisName='"+gbean.getXAxisName ()+"' "; }
		if(gbean.getYAxisName ()!=null){strXML+="yAxisName='"+gbean.getYAxisName ()+"' "; }
		if(gbean.getYAxisMinValue()!=null){strXML+="yAxisMinValue='"+gbean.getYAxisMinValue()+"' "; }
		if(gbean.getYAxisMaxValue ()!=null){strXML+="yAxisMaxValue='"+gbean.getYAxisMaxValue ()+"' "; }
		if(gbean.getShownames  ()!=null){strXML+="shownames ='"+gbean.getShownames  ()+"' "; }
		if(gbean.getShowValues ()!=null){strXML+="showValues='"+gbean.getShowValues ()+"' "; }
		if(gbean.getShowLimits ()!=null){strXML+="showLimits='"+gbean.getShowLimits ()+"' "; }
		if(gbean.getRotateNames ()!=null){strXML+="rotateNames='"+gbean.getRotateNames ()+"' "; }
		if(gbean.getAnimation ()!=null){strXML+="animation='"+gbean.getAnimation ()+"' "; }
		if(gbean.getShowBorder ()!=null){strXML+="showBorder='"+gbean.getShowBorder ()+"' "; }
		if(gbean.getBaseFont ()!=null){strXML+="baseFont='"+gbean.getBaseFont ()+"' "; }
		if(gbean.getBaseFontSize ()!=null){strXML+="baseFontSize='"+gbean.getBaseFontSize ()+"' "; }
		if(gbean.getBaseFontColor  ()!=null){strXML+="baseFontColor='"+gbean.getBaseFontColor  ()+"' "; }
		if(gbean.getOutCnvBaseFont  ()!=null){strXML+="outCnvBaseFont='"+gbean.getOutCnvBaseFont  ()+"' "; }
		if(gbean.getOutCnvBaseFontSze()!=null){strXML+="outCnvBaseFontSze='"+gbean.getOutCnvBaseFontSze()+"' "; }
		if(gbean.getOutCnvBaseFontColor()!=null){strXML+="outCnvBaseFontColor='"+gbean.getOutCnvBaseFontColor()+"' "; }
		if(gbean.getNumberPrefix  ()!=null){strXML+="numberPrefix='"+gbean.getNumberPrefix  ()+"' "; }
		if(gbean.getNumberSuffix ()!=null){strXML+="numberSuffix='"+gbean.getNumberSuffix ()+"' "; }
		if(gbean.getFormatNumber  ()!=null){strXML+="formatNumber='"+gbean.getFormatNumber  ()+"' "; }
		if(gbean.getFormatNumberScale()!=null){strXML+="formatNumberScale='"+gbean.getFormatNumberScale()+"' "; }
		if(gbean.getDecimalSeparator ()!=null){strXML+="decimalSeparator='"+gbean.getDecimalSeparator ()+"' "; }
		if(gbean.getThousandSeparator()!=null){strXML+="thousandSeparator='"+gbean.getThousandSeparator()+"' "; }
		if(gbean.getDecimalPrecision ()!=null){strXML+="decimalPrecision='"+gbean.getDecimalPrecision ()+"' "; }
		if(gbean.getDivLineDecimalPrecision()!=null){strXML+="divLineDecimalPrecision='"+gbean.getDivLineDecimalPrecision()+"' "; }
		if(gbean.getLimitsDecimalPrecision()!=null){strXML+="limitsDecimalPrecision='"+gbean.getLimitsDecimalPrecision()+"' "; }
		if(gbean.getNumdivlines()!=null){strXML+="numdivlines='"+gbean.getNumdivlines()+"' "; }
		if(gbean.getDivlinecolor()!=null){strXML+="divlinecolor='"+gbean.getDivlinecolor()+"' "; }
		if(gbean.getDivLineThickness()!=null){strXML+="divLineThickness='"+gbean.getDivLineThickness()+"' "; }
		if(gbean.getDivLineAlpha()!=null){strXML+="divLineAlpha='"+gbean.getDivLineAlpha()+"' "; }
		if(gbean.getShowDivLineValue ()!=null){strXML+="showDivLineValue='"+gbean.getShowDivLineValue ()+"' "; }
		if(gbean.getShowhovercap()!=null){strXML+="showhovercap='"+gbean.getShowhovercap()+"' "; }
		if(gbean.getHoverCapBgColor()!=null){strXML+="hoverCapBgColor='"+gbean.getHoverCapBgColor()+"' "; }
		if(gbean.getHoverCapBorderColor()!=null){strXML+="hoverCapBorderColor='"+gbean.getHoverCapBorderColor()+"' "; }
		if(gbean.getHoverCapSepChar()!=null){strXML+="hoverCapSepChar='"+gbean.getHoverCapSepChar()+"' "; }
		if(gbean.getChartLeftMargin()!=null){strXML+="chartLeftMargin='"+gbean.getChartLeftMargin()+"' "; }
		if(gbean.getChartRightMargin ()!=null){strXML+="chartRightMargin='"+gbean.getChartRightMargin ()+"' "; }
		if(gbean.getChartTopMargin ()!=null){strXML+="chartTopMargin='"+gbean.getChartTopMargin ()+"' "; }
		if(gbean.getChartBottomMargin()!=null){strXML+="chartBottomMargin='"+gbean.getChartBottomMargin()+"' "; }
		if(gbean.getZeroPlaneShowBorder()!=null){strXML+="zeroPlaneShowBorder='"+gbean.getZeroPlaneShowBorder()+"' "; }
		if(gbean.getZeroPlaneBorderColor()!=null){strXML+="zeroPlaneBorderColor='"+gbean.getZeroPlaneBorderColor()+"' "; }
		if(gbean.getZeroPlaneColor ()!=null){strXML+="zeroPlaneColor='"+gbean.getZeroPlaneColor ()+"' "; }
		if(gbean.getZeroPlaneAlpha ()!=null){strXML+="zeroPlaneAlpha='"+gbean.getZeroPlaneAlpha ()+"' "; }
		if(gbean.getBgRatio()!=null){strXML+="bgRatio='"+gbean.getBgRatio ()+"' "; }
		if(gbean.getBgAngle()!=null){strXML+="bgAngle='"+gbean.getBgAngle ()+"' "; }
		strXML +=" >";
		return strXML;
	}
	public String BuildSetXML(ArrayList sbeanlist){
		String strXML="";
		if(sbeanlist.size()>24){
			int step=Integer.parseInt(MathRound.round(sbeanlist.size()/7,0));
			for (int i = 1; i < sbeanlist.size()-1; i++) {
				if(i==1){
					if(((SetBean)sbeanlist.get(1)).getLabel().equals("")) break;
				}
				if(!(step==i||step*2==i||step*3==i||step*4==i||step*5==i||step*6==i)){
//					((SetBean) sbeanlist.get(i))
//							.setHoverText(((SetBean) sbeanlist.get(i)).getLabel().equals("")
//									 ? ((SetBean) sbeanlist.get(i)).getHoverText()
//									 : ((SetBean) sbeanlist.get(i)).getLabel());
				((SetBean)sbeanlist.get(i)).setLabel("");
				}
			}
		}
		for (int i = 0; i < sbeanlist.size(); i++) {
			strXML +="<set ";
			strXML +="name='"+((SetBean)sbeanlist.get(i)).getLabel()+"' value='"+((SetBean)sbeanlist.get(i)).getValue()+"' ";
			if(((SetBean)sbeanlist.get(i)).getAlpha()!=null){strXML+="alpha='"+((SetBean)sbeanlist.get(i)).getAlpha()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getColor()!=null){strXML+="color='"+((SetBean)sbeanlist.get(i)).getColor()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getHoverText()!=null){strXML+="hoverText='"+((SetBean)sbeanlist.get(i)).getHoverText()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getLink()!=null){strXML+="link='"+((SetBean)sbeanlist.get(i)).getLink()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getShowName()!=null){strXML+="showName='"+((SetBean)sbeanlist.get(i)).getShowName()+"' "; }
			strXML +=" />";
		}
		return strXML;
	}
	public String BuildSetXMLNoX7(ArrayList sbeanlist){
		String strXML="";
		for (int i = 0; i < sbeanlist.size(); i++) {
			strXML +="<set ";
			strXML +="name='"+((SetBean)sbeanlist.get(i)).getLabel()+"' value='"+((SetBean)sbeanlist.get(i)).getValue()+"' ";
			if(((SetBean)sbeanlist.get(i)).getAlpha()!=null){strXML+="alpha='"+((SetBean)sbeanlist.get(i)).getAlpha()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getColor()!=null){strXML+="color='"+((SetBean)sbeanlist.get(i)).getColor()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getHoverText()!=null){strXML+="hoverText='"+((SetBean)sbeanlist.get(i)).getHoverText()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getLink()!=null){strXML+="link='"+((SetBean)sbeanlist.get(i)).getLink()+"' "; }
			if(((SetBean)sbeanlist.get(i)).getShowName()!=null){strXML+="showName='"+((SetBean)sbeanlist.get(i)).getShowName()+"' "; }
			strXML +=" />";
		}
		return strXML;
	}
	/**
	 * 建立多柱 图
	 * @author liqing
	 * @param datalist
	 * @return
	 * @throws Exception
	 */
	public ArrayList BuildingMsGDSet(GDSet gd,GraphBean gbean,int ReturnType) throws Exception{
		ArrayList list =new ArrayList();
		ArrayList templist =new ArrayList();
		String Zerotempstr="";
		String tempstr="";
		SetBean sbean=null;
		HashMap map =new HashMap();
		sbean = new SetBean();
		sbean.setDataset("");
		list.add(sbean);
		int intcolor=0;
		if(ReturnType==GDSetTool.Return_ArrayListCol||ReturnType==GDSetTool.Return_ArrayListRow){
			for (int i = 0; i < gd.getRowCount(); i++) {
				if (!map.containsKey(gd.getCell(i, 0))) {
					sbean = new SetBean();
					sbean.setLabel(gd.getCell(i, 0));
					list.add(sbean);
					templist.add(gd.getCell(i, 0));
					map.put(gd.getCell(i, 0), "");
				}
			}
			map.clear();
//			if(gd.getRowCount()>0) tempstr=gd.getCell(0, 1);
			for (int i = 0; i < gd.getRowCount(); i++) {
				if(!tempstr.equals(gd.getCell(i, 1))){
					for(int j = 0; j < templist.size(); j++){
						if (i != 0) {
							if (map.containsKey(templist.get(j).toString())) {
								list.add((SetBean) map.get(templist.get(j)
										.toString()));
								if(((SetBean) map.get(templist.get(j).toString())).getDataset()!=null) 
									Zerotempstr=((SetBean) map.get(templist.get(j).toString())).getDataset();
							} else {
								sbean = new SetBean();
								sbean.setLabel(templist.get(j).toString());
								sbean.setValue("0");
								sbean.setHoverText(Zerotempstr+","+templist.get(j) + ","
										+ sbean.getValue());
								list.add(sbean);
							}
						}
					}
					sbean = new SetBean();
					sbean.setDataset(gd.getCell(i, 1));
					sbean.setColor(SetBean.colorlist.get(intcolor++).toString());
					list.add(sbean);
					Zerotempstr=sbean.getDataset();
					map.clear();
				}
				for(int j = 0; j < templist.size(); j++){
					if(j==0){
						tempstr=gd.getCell(i, 1);
					}
					if(templist.get(j).equals(gd.getCell(i, 0))){
						if(!map.containsKey(templist.get(j).toString())){
							sbean = new SetBean();
							sbean.setLabel(templist.get(j).toString());
							sbean.setValue(gd.getCell(i, 2));
							sbean.setHoverText(tempstr+","+templist.get(j)+","+sbean.getValue());
							map.put(templist.get(j).toString(), sbean);
						}else{
							 ((SetBean)map.get(templist.get(j).toString())).setValue((""+(Double.parseDouble(gd.getCell(i, 2))+
									 Double.parseDouble( ((SetBean)map.get(templist.get(j).toString())).getValue()))).endsWith(".0")?
											 (""+(Double.parseDouble(gd.getCell(i, 2))+
													 Double.parseDouble( ((SetBean)map.get(templist.get(j).toString())).getValue()))).replace(".0", ""):
														 (""+(Double.parseDouble(gd.getCell(i, 2))+
																 Double.parseDouble( ((SetBean)map.get(templist.get(j).toString())).getValue()))));
							 ((SetBean)map.get(templist.get(j).toString())).setHoverText(gd.getCell(i, 1)+","+
									 ((SetBean)map.get(templist.get(j).toString())).getLabel()+","+sbean.getValue());
						}
//						System.out.println(gd.getCell(i, 0)+"---"+gd.getCell(i, 1)+"===="+gd.getCell(i, 2));
					}
				}
				tempstr=gd.getCell(i, 1);
				
			}
			for(int j = 0; j < templist.size(); j++){
					if (map.containsKey(templist.get(j).toString())) {
						list.add((SetBean) map.get(templist.get(j)
								.toString()));
						if(((SetBean) map.get(templist.get(j).toString())).getDataset()!=null) 
							Zerotempstr=((SetBean) map.get(templist.get(j).toString())).getDataset();
					} else {
						sbean = new SetBean();
						sbean.setLabel(templist.get(j).toString());
						sbean.setValue("0");
						sbean.setHoverText(Zerotempstr+","+templist.get(j) + ","
								+ sbean.getValue());//...
						list.add(sbean);
					}
				}
		}else if(ReturnType==GDSetTool.Return_ArrayListColWithTitle||ReturnType==GDSetTool.Return_ArrayListRowWithTitle){
		}
		return list;
	}
	
	public String BuildMsXML(ArrayList datalist)  throws Exception{
		String strXML="";
		boolean f=true;
		for (int i = 0; i < datalist.size(); i++) {
			if(((SetBean)datalist.get(i)).getDataset()!=null){
				if(((SetBean)datalist.get(i)).getDataset().equals("")){
					f=false;
					strXML+="<categories>";
				}else{
					f=true;
					if(strXML.indexOf("</categories>")<0){
						strXML+="</categories>";
					}else{
						strXML+="</dataset>";
					}
					strXML+="<dataset ";
					strXML+="seriesName='"+((SetBean)datalist.get(i)).getDataset()+"'";
					if(((SetBean)datalist.get(i)).getColor()!=null){strXML+=" color='"+((SetBean)datalist.get(i)).getColor()+"' "; }
					strXML+=">";

				}
			}else{
				if(f){
					strXML +="<set ";
					strXML +="label='"+((SetBean)datalist.get(i)).getLabel()+"' value='"+((SetBean)datalist.get(i)).getValue()+"' ";
					if(((SetBean)datalist.get(i)).getAlpha()!=null){strXML+="alpha='"+((SetBean)datalist.get(i)).getAlpha()+"' "; }
					if(((SetBean)datalist.get(i)).getColor()!=null){strXML+="color='"+((SetBean)datalist.get(i)).getColor()+"' "; }
					if(((SetBean)datalist.get(i)).getHoverText()!=null){strXML+="hoverText='"+((SetBean)datalist.get(i)).getHoverText()+"' "; }
					if(((SetBean)datalist.get(i)).getLink()!=null){strXML+="link='"+((SetBean)datalist.get(i)).getLink()+"' "; }
					else{ strXML+="link='S-"+((SetBean)datalist.get(i)).getLabel()+"' "; }
					if(((SetBean)datalist.get(i)).getShowName()!=null){strXML+="showName='"+((SetBean)datalist.get(i)).getShowName()+"' "; }
					strXML +=" />";
				}else{
					strXML +="<category ";
					strXML +="label='"+((SetBean)datalist.get(i)).getLabel()+"' ";
					if(((SetBean)datalist.get(i)).getAlpha()!=null){strXML+="alpha='"+((SetBean)datalist.get(i)).getAlpha()+"' "; }
					if(((SetBean)datalist.get(i)).getColor()!=null){strXML+="color='"+((SetBean)datalist.get(i)).getColor()+"' "; }
					if(((SetBean)datalist.get(i)).getHoverText()!=null){strXML+="hoverText='"+((SetBean)datalist.get(i)).getHoverText()+"' "; }
					if(((SetBean)datalist.get(i)).getLink()!=null){strXML+="link='"+((SetBean)datalist.get(i)).getLink()+"' "; }
					if(((SetBean)datalist.get(i)).getShowName()!=null){strXML+="showName='"+((SetBean)datalist.get(i)).getShowName()+"' "; }
					strXML +=" />";
				}
			}
		}
		strXML+="</dataset>";
		return strXML;
	}
	/**
	 * 建立chart的XML字符串
	 * @param GDSet 结果集
	 * @return XML
	 */
	public String BuildXML(GDSet gd)  throws Exception{
		String strXML="";
		strXML +="<graph caption='' xAxisName='' yAxisName='' showValues=''";
		strXML +=" formatNumberScale=''";
		strXML +=" showBorder=''";
		strXML +=">";
		
		for (int i = 0; i < gd.getRowCount(); i++) {
			strXML +="<set name='"+gd.getCell(i, 0)+"' value='"+MathRound.round(gd.getCell(i, 1),3)+"' />";
		}
		strXML +="</graph>";
		return "";
	}
}
