package com.viewscenes.web.sysmgr.dicManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.jmask.web.controller.EXEException;

import com.viewscenes.bean.ZdicCityBean;
import com.viewscenes.dao.database.DbComponent;

import com.viewscenes.printexcel.JExcel;
import com.viewscenes.pub.GDSet;
import com.viewscenes.sys.TableInfoCache;
import com.viewscenes.util.StringTool;

import flex.messaging.io.amf.ASObject;
/**
 * 城市表维护类
 * @author 张文
 *
 */
public class CityTab {
	
	/**
	 * 查询信息
	 * @param bean
	 * @return
	 */
	public Object getCity(ZdicCityBean bean){
		
		String city = bean.getCity();
		String contry = bean.getContry();
		String capital = bean.getCapital();
		String ciraf = bean.getCiraf();

		ASObject resObj=null;
		ArrayList result;
		String sql="select * from res_city_tab t where 1=1 ";
		
		if(city!=null&&!city.equalsIgnoreCase("")){
			sql+=" and city like ('%"+city+"%')";
		}
		if(contry!=null&&!contry.equalsIgnoreCase("")){
			sql+=" and contry like ('%"+contry+"%')";
		}
		if(capital!=null&&!capital.equalsIgnoreCase("")){
			sql+=" and capital like ('%"+capital+"%')";
		}
		if(ciraf!=null&&!ciraf.equalsIgnoreCase("")){
			sql+=" and ciraf like ('%"+ciraf+"%')";
		}
		
		sql+=" order by id desc ";
		try {
			 resObj=StringTool.pageQuerySql(sql.toString(), bean);
			 ArrayList list =(ArrayList) resObj.get("resultList") ;//取出对象的结果集
			 String classpath = ZdicCityBean.class.getName();
			 result = StringTool.convertFlexToJavaList(list, classpath);//将对象类型转换成bean，再放回结果集中。
			 resObj.put("resultList", result);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","查询城市信息异常："+e.getMessage(),"");
		}
		return resObj;
	}
	/**
	 * 根据国家返回城市列表 (默认返回所有)
	 * @param contry
	 * @return
	 */
	public static Object  getCityByCountry(ASObject obj){
		String contry=(String)obj.get("country");
		ArrayList<Object> list=new ArrayList<Object>();
		String sql="select * from res_city_tab t where 1=1";
		if(contry!=null&&!contry.equalsIgnoreCase("")){
			sql+=" and contry ='"+contry+"'";
		}
		sql+=" order by city ";
		try {
			GDSet set=DbComponent.Query(sql);
			String classpath = ZdicCityBean.class.getName();
			list=StringTool.converGDSetToBeanList(set, classpath);
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","查询城市信息异常："+e.getMessage(),"");
		}
		return list;
		
	}
	/**
	 * 添加数据入库 
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	public Object addCity(ZdicCityBean bean){
		String res="添加城市信息成功!";
		String city = bean.getCity();
		String contry = bean.getContry();
		String capital = bean.getCapital();
		String continents_id = bean.getContinents_id();
		
		String longitude = bean.getLongitude();
		String latitude = bean.getLatitude();
		String ciraf = bean.getCiraf();
		String elevation = bean.getElevation();
		
		String default_language = bean.getDefault_language();
		String voltage = bean.getVoltage();
		String moveut = bean.getMoveut();
		String summer = bean.getSummer();
		String  sql="insert into res_city_tab (id,city,contry,capital,continents_id,longitude,latitude,ciraf,elevation," +
				"default_language,voltage,moveut,summer) values(SEC_SEQ.nextval,'"+city+"','"+contry+"','"+capital+"',"+continents_id+",'" 
				+longitude+"','"+latitude+"' ,'"+ciraf+"','"+elevation+"','"+default_language+"','"+voltage+"','"+moveut+"',"+summer+")";
		try {
			DbComponent.exeUpdate(sql);
			new TableInfoCache();
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","添加城市信息异常："+e.getMessage(),"");
		}
		
		return res;
	}

	
	/**
	 * 删除城市信息
	 * @param ids
	 * @return
	 */
	  
	public Object delCity(ASObject asObj){
		String dellist=(String)asObj.get("dellist");
		String message="删除城市信息成功!";
		String sql="delete from res_city_tab where id in("+dellist+")";
		try {
			DbComponent.exeUpdate(sql);
			new TableInfoCache();
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","删除城市异常"+e.getMessage(),"");
		}
		return message;
	}
	
	/**
	 * 修改数据入库 
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	public Object updatCity(ZdicCityBean bean){
		String res="修改城市信息成功!";
		String id = bean.getId();
		String city = bean.getCity();
		String contry = bean.getContry();
		String capital = bean.getCapital();
		String continents_id = bean.getContinents_id();
		
		String longitude = bean.getLongitude();
		String latitude = bean.getLatitude();
		String ciraf = bean.getCiraf();
		String elevation = bean.getElevation();
		
		String default_language = bean.getDefault_language();
		String voltage = bean.getVoltage();
		String moveut = bean.getMoveut();
		String summer = bean.getSummer();
		String  sql="update res_city_tab set city='"+city+"',contry = '"+contry+"',capital='"+capital+"',continents_id="+continents_id+"," +
				"longitude='"+longitude+"',latitude='"+latitude+"' ,ciraf='"+ciraf+"', elevation='"+elevation+"'," +
				"default_language='"+default_language+"',voltage='"+voltage+"',moveut='"+moveut+"',summer="+summer+"  where id ='"+id+"'";
		try {
			DbComponent.exeUpdate(sql);
			new TableInfoCache();
		} catch (Exception e) {
			e.printStackTrace();
			return new EXEException("","修改城市参数信息异常："+e.getMessage(),"");
		}
		
		return res;
	}
	/**
	 * 导出城市信息
	 * @param msg
	 * @param request
	 * @param response
	 */
	public void getCityExcel(String msg,HttpServletRequest request,HttpServletResponse response){
		Element root = StringTool.getXMLRoot(msg);
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("0", "亚洲");
		map.put("1", "欧洲");
		map.put("2", "非洲");
		map.put("3", "北美洲");
		map.put("4", "南美洲");
		map.put("5", "大洋洲");
		map.put("6", "南极洲");
		String fileName="城市信息";
		String downFileName = "城市信息";
		String city=root.getChildText("city");
		String contry=root.getChildText("contry");
		String ciraf=root.getChildText("ciraf");
		StringBuffer bf=new StringBuffer();
		bf.append("select * from res_city_tab t where 1=1 ");
		
		if(city!=null&&!city.equalsIgnoreCase("")){
			bf.append(" and city like ('%").append(city).append("%')");
		}
		if(contry!=null&&!contry.equalsIgnoreCase("")){
			bf.append(" and contry like ('%").append(contry).append("%')");
		}
		if(ciraf!=null&&!ciraf.equalsIgnoreCase("")){
			bf.append(" and ciraf like ('%").append(ciraf).append("%')");
		}
		bf.append(" order by id desc");
		try {
			JExcel jExcel=new JExcel();
			downFileName=jExcel.openDocument();
			jExcel.WorkBookGetSheet(0);
			GDSet gd=DbComponent.Query(bf.toString());
			int n=gd.getRowCount();
			if(n>0){
				for(int i=0;i<n;i++){
					jExcel.addDate(0, i+1,gd.getString(i, "city"),jExcel.dateCellFormat);
					jExcel.addDate(1, i+1,gd.getString(i, "contry"),jExcel.dateCellFormat);
					jExcel.addDate(2, i+1,gd.getString(i, "capital"),jExcel.dateCellFormat);
					jExcel.addDate(3, i+1,map.get(gd.getString(i, "continents_id")),jExcel.dateCellFormat);
					jExcel.addDate(4, i+1,gd.getString(i, "longitude"),jExcel.dateCellFormat);
					jExcel.addDate(5, i+1,gd.getString(i, "latitude"),jExcel.dateCellFormat);
					jExcel.addDate(6, i+1,gd.getString(i, "ciraf"),jExcel.dateCellFormat);
					jExcel.addDate(7, i+1,gd.getString(i, "elevation"),jExcel.dateCellFormat);
					jExcel.addDate(8, i+1,gd.getString(i, "default_language"),jExcel.dateCellFormat);
					jExcel.addDate(9, i+1,gd.getString(i, "voltage"),jExcel.dateCellFormat);
					jExcel.addDate(10, i+1,gd.getString(i, "moveut"),jExcel.dateCellFormat);
					jExcel.addDate(11, i+1,toSummer(gd.getString(i, "summer")),jExcel.dateCellFormat);					
				}
			}
			jExcel.getWorkSheet().setRowView(0, 600);
			jExcel.getWorkSheet().setColumnView(0, 30);
	    	jExcel.getWorkSheet().setColumnView(1, 30);
	    	jExcel.getWorkSheet().setColumnView(2, 30);	
	    	jExcel.getWorkSheet().setColumnView(3, 30);
	    	jExcel.getWorkSheet().setColumnView(4, 30);
	    	jExcel.getWorkSheet().setColumnView(5, 30);
			jExcel.getWorkSheet().setColumnView(6, 30);
	    	jExcel.getWorkSheet().setColumnView(7, 30);
	    	jExcel.getWorkSheet().setColumnView(8, 30);	
	    	jExcel.getWorkSheet().setColumnView(9, 30);
	    	jExcel.getWorkSheet().setColumnView(10, 30);
	    	jExcel.getWorkSheet().setColumnView(11, 30);
	    	
	    	
			jExcel.addDate(0, 0,"城市",jExcel.dateTITLEFormat);
			jExcel.addDate(1, 0,"国家",jExcel.dateTITLEFormat);
			jExcel.addDate(2, 0,"首都",jExcel.dateTITLEFormat);
			jExcel.addDate(3, 0,"大洲",jExcel.dateTITLEFormat);
			jExcel.addDate(4, 0,"经度",jExcel.dateTITLEFormat);
			jExcel.addDate(5, 0,"纬度",jExcel.dateTITLEFormat);
			jExcel.addDate(6, 0,"CIRAF区",jExcel.dateTITLEFormat);
			jExcel.addDate(7, 0,"海拔",jExcel.dateTITLEFormat);
			jExcel.addDate(8, 0,"默认语言",jExcel.dateTITLEFormat);
			jExcel.addDate(9, 0,"电压",jExcel.dateTITLEFormat);
			jExcel.addDate(10, 0,"时差",jExcel.dateTITLEFormat);
			jExcel.addDate(11, 0,"夏令",jExcel.dateTITLEFormat);
			
			jExcel.getWorkSheet().setName("城市信息");
			jExcel.saveDocument();
			
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Location", "Export.xls");
	        response.setHeader("Expires", "0");
	        response.setHeader("Content-Disposition", "attachment; filename="+new String((fileName).getBytes("GBK"),"ISO-8859-1")+".xls");
	        OutputStream outputStream = response.getOutputStream();
	        InputStream inputStream = new FileInputStream(downFileName);
	        byte[] buffer = new byte[1024];
	        int i = -1;
	        while ( (i = inputStream.read(buffer)) != -1) {
	          outputStream.write(buffer, 0, i);
	        }
	        outputStream.flush();
	        outputStream.close();
	        outputStream = null;
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String toSummer(String str){
		if(str.equals("1")){
			return "有夏令";
		}else{
			return "无夏令";
		}
	}
}
