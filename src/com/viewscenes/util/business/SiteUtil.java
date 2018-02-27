package com.viewscenes.util.business;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.util.StringTool;

public class SiteUtil {

	public static String RES_HEADEND_TAB = "res_headend_tab";
	private static String[][] CENTER_LIST = null;
	private static HashMap CENTER_MAP = null;

	/**
	 * 查找所有站点
	 * 
	 * @param
	 * @return head_code[0][i] = short_name[i] + "[" + code[i] + "]";
	 *         head_code[1][i] = code[i];
	 */
	public static String[][] head_code() {
		// 查找所有站点
		String[][] head_code = null;
		String[] short_name = null;
		String[] code = null;

		GDSet data2 = null;
		try {
			String sql = "select * from res_headend_tab t where t.is_delete=0  order by code ";
			DbComponent.Query(sql);
		} catch (Exception ex) {
		}
		int rowCount2 = data2.getRowCount();
		if (rowCount2 != 0) {
			head_code = new String[2][rowCount2];
			short_name = new String[rowCount2];
			code = new String[rowCount2];
			for (int i = 0; i < rowCount2; i++) {
				try {
					short_name[i] = data2.getString(i, "shortname");
					code[i] = data2.getString(i, "code");
				} catch (Exception e) {
				}
				head_code[0][i] = short_name[i] + "[" + code[i] + "]";
				head_code[1][i] = code[i];
			}

		}
		return head_code;
	}

	/**
	 * 查找中心列表,这里只取得下面有站点的中心
	 * 
	 * @param
	 * @return center_list[0][i] = data1.getString(i, "center_id");
	 *         center_list[1][i] = data1.getString(i, "name");
	 */
	public static String[][] get_center_list() {
		loadCenters();
		return CENTER_LIST;
	}

	public static String getCenNameById(String center_id) {
		loadCenters();
		return (String) CENTER_MAP.get(center_id);
	}

	/**
	 * 中心Center列表。
	 */
	public synchronized static void loadCenters() {
		if (CENTER_LIST == null || CENTER_MAP == null) {
			GDSet data1 = null;
			String localCenterId = SystemIdUtil.getLocalCenterId();
			String sql = "";
			if (localCenterId != null
					&& localCenterId.trim().equalsIgnoreCase("100")) {
				sql = "select center_id,name ,code from res_center_tab where center_id<>100"
						+ "  order by name asc";
			} else {
				sql = "select center_id,name,code from res_center_tab where center_id = "
						+ localCenterId;
			}
			try {
				data1 = DbComponent.Query(sql);
				int rowCount = data1.getRowCount();
				if (rowCount > 0) {
					CENTER_LIST = new String[3][rowCount];
					CENTER_MAP = new HashMap();
					String center_id = null;
					String center_name = null;
					String center_code = null;
					for (int i = 0; i < rowCount; i++) {
						center_id = data1.getString(i, "center_id");
						center_name = data1.getString(i, "name");
						center_code = data1.getString(i, "code");
						CENTER_LIST[0][i] = center_id;
						CENTER_LIST[1][i] = center_name;
						CENTER_LIST[2][i] = center_code;
						CENTER_MAP.put(center_id, center_name);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 查找center_id对应中心包含的所有站点
	 * 
	 * @param
	 * @return head_code[0][i] = short_name[i] + "[" + code[i] + "]";
	 *         head_code[1][i] = code[i];
	 */
	public static String[][] head_code(int x, String center_id)
			throws Exception {
		// 查找所有站点
		String[][] head_code = null;
		String[] short_name = null;
		String[] code = null;
		String[] is_online = null;

		GDSet data2 = null;
		StringBuffer buff = new StringBuffer();
		try {
			buff
					.append(" select shortname ,code ,is_online  from RES_HEADEND_TAB ");
			buff.append(" where  center_id='" + center_id + "' ");
			if (x >= 6) {
				buff.append(" and version >='V" + x + "' ");
			}
			buff.append(" and is_delete=0 ");
			buff.append("    order by is_online  ,shortname desc ");

			data2 = DbComponent.Query(buff.toString());
		} catch (Exception ex) {
			throw new Exception(
					"head_code(int x,String center_id)\u51FA\u9519", ex);
		}
		int rowCount2 = data2.getRowCount();
		if (rowCount2 != 0) {
			head_code = new String[3][rowCount2];
			short_name = new String[rowCount2];
			code = new String[rowCount2];
			is_online = new String[rowCount2];
			for (int i = 0; i < rowCount2; i++) {
				try {
					short_name[i] = data2.getString(i, "shortname");
					code[i] = data2.getString(i, "code");
					is_online[i] = data2.getString(i, "is_online");
				} catch (Exception e) {
					throw new Exception(
							"head_code\u83B7\u5F97\u65F6\u9519\u8BEF\u3002" + e);
				}
				head_code[0][i] = short_name[i] + "[" + code[i] + "]";
				head_code[1][i] = code[i];
				head_code[2][i] = is_online[i];
			}

		}
		return head_code;
	}

	/**
	 * 遥控站站点提取。
	 * 
	 * @param x
	 *            int
	 * @param center_id
	 *            String
	 * @throws Exception
	 * @return String[][]
	 */
	public static String[][] head_102code(int x, String center_id)
			throws Exception {
		// 查找所有站点
		String[][] head_code = null;
		String[] short_name = null;
		String[] code = null;
		String[] is_online = null;

		GDSet data2 = null;
		StringBuffer buff = new StringBuffer();
		try {
			buff
					.append("select shortname,code,is_online  from RES_HEADEND_TAB ");
			buff.append("  where center_id='" + center_id + "' ");

			if (x >= 6) {
				buff.append(" and version='V" + x + "' ");
			}
			buff.append(" and is_delete=0 ");
			buff.append(" and type_id='102' ");
			buff.append(" and type_id='102' ");
			buff.append("    order by is_online  ,shortname desc ");

			data2 = DbComponent.Query(buff.toString());
		} catch (Exception ex) {
			throw new Exception(
					"head_code(int x,String center_id)\u51FA\u9519", ex);
		}
		int rowCount2 = data2.getRowCount();
		if (rowCount2 != 0) {
			head_code = new String[3][rowCount2];
			short_name = new String[rowCount2];
			code = new String[rowCount2];
			is_online = new String[rowCount2];
			for (int i = 0; i < rowCount2; i++) {
				try {
					short_name[i] = data2.getString(i, "shortname");
					code[i] = data2.getString(i, "code");
					is_online[i] = data2.getString(i, "is_online");
				} catch (Exception e) {
					throw new Exception(
							"head_code\u83B7\u5F97\u65F6\u9519\u8BEF\u3002" + e);
				}
				head_code[0][i] = short_name[i] + "[" + code[i] + "]";
				head_code[1][i] = code[i];
				head_code[2][i] = is_online[i];
			}

		}
		return head_code;
	}

	/**
	 * 根据站点code得出站点类型
	 * 
	 * @param
	 * @return
	 */
	private String head_code2type(String head_code) {
		String type = "101";

		try {
			String sql = "select type_id from  res_headend_tab where is_delete=0 and code='"
					+ head_code + "'";

			GDSet result = DbComponent.Query(sql);
			type = result.getString(0, "type_id");
			return type;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return type;

	}

	public static String head_code_to_id(String head_code)
			throws Exception {
		String head_id = "";
		GDSet data2 = null;
		try {
			data2 = DbComponent
					.Query("select head_id from   RES_HEADEND_TAB where is_delete=0 and code='"
							+ head_code + "'");
		} catch (DbException ex) {
		}

		int rowCount2 = data2.getRowCount();
		if (rowCount2 != 0) {
			try {
				head_id = data2.getString(0, "head_id");
			} catch (Exception e) {
				throw new Exception("head_code_to_id\u9519\u8BEF\u3002"
						+ e);
			}
		}
		return head_id;
	}

	public static String getVersionFromCode(String head_code)
			throws Exception {
		String version = "V5";
		GDSet data2 = null;
		try {
			data2 = DbComponent
					.Query("select version from   RES_HEADEND_TAB where is_delete=0 and code='"
							+ head_code + "'");
		} catch (DbException ex) {
		}

		int rowCount2 = data2.getRowCount();
		if (rowCount2 != 0) {
			try {
				version = data2.getString(0, "version");
			} catch (Exception e) {
				throw new Exception(
						"getVersionFromCode\u9519\u8BEF\u3002" + e);
			}
		}
		return version;
	}

	/**
	 * 此方法是把二维的数组转化为传送到前台的xml. root 是xml的根节点. attrs是属性值数组.attrs[0]是主节点值.
	 * author:刘斌
	 */
	public static String StringArray2ToXml(String[][] resourceArray,
			String[] attrs) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		if (resourceArray != null) {
			for (int i = 0; i < resourceArray[1].length; i++) {
				Element info = new Element(attrs[0]);

				for (int j = 1; j < attrs.length; j++) {
					info.addAttribute(new Attribute(attrs[j],
							resourceArray[j - 1][i]));
				}
				list.add(info);
			}
		}
		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	/**
	 * 此方法是把一维的数组转化为传送到前台的xml.并且一维数组是上一个和下一个为不同的含义. root 是xml的根节点.
	 * attrs是属性值数组.attrs[0]是主节点值. author:刘斌
	 */
	public static String StringArray1ToXml(String[] resourceArray,
			String[] attrs) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		if (resourceArray != null) {
			for (int i = 0; i < resourceArray.length; i = i + 2) {
				Element info = new Element(attrs[0]);

				for (int j = 1; j < attrs.length; j++) {
					info.addAttribute(new Attribute(attrs[j], resourceArray[i
							+ j - 1]));
				}
				list.add(info);
			}
		}
		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	/**
	 * 此方法是把GDSet转化为传送到前台的xml. root 是xml的根节点. attrs是属性值数组.attrs[0]是主节点值.
	 * author:刘斌
	 */
	public static String GDSetToXml(String sql) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		try {
			GDSet set = DbComponent.Query(sql);
			if (set.getRowCount() != 0) {
				for (int i = 0; i < set.getRowCount(); i++) {
					Element info = new Element("root");

					for (int j = 0; j < set.getColumnCount(); j++) {
						info.addAttribute(new Attribute(set.getColumnName(j),
								set.getString(i, set.getColumnName(j))));
					}
					list.add(info);
				}

			}
		} catch (DbException ex) {
		} catch (GDSetException ex) {
			/** @todo Handle this exception */
		}

		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	/**
	 * 此方法是把GDSet转化为传送到前台的xml. root 是xml的根节点. attrs是属性值数组.attrs[0]是主节点值.
	 * author:刘斌
	 */
	public static String GDSetToXml(GDSet set) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		try {

			if (set.getRowCount() != 0) {
				for (int i = 0; i < set.getRowCount(); i++) {
					Element info = new Element("root");

					for (int j = 0; j < set.getColumnCount(); j++) {
						info.addAttribute(new Attribute(set.getColumnName(j),
								set.getString(i, set.getColumnName(j))));
					}
					list.add(info);
				}

			}
		} catch (GDSetException ex) {
			/** @todo Handle this exception */
		}

		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	/**
	 * 此方法是把GDSet转化为传送到前台的xml为表格形式. root 是xml的根节点. attrs是属性值数组.attrs[0]是主节点值.
	 * author:刘斌
	 */
	public static String GDSetToXmlTable(String sql) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		try {
			GDSet set = DbComponent.Query(sql);
			if (set.getRowCount() != 0) {
				for (int i = 0; i < set.getRowCount(); i++) {
					Element info = new Element("root");

					for (int j = 0; j < set.getColumnCount(); j++) {
						info.addAttribute(new Attribute(set.getColumnName(j),
								set.getString(i, set.getColumnName(j))));
					}
					list.add(info);
				}

			}
		} catch (DbException ex) {
		} catch (GDSetException ex) {
			/** @todo Handle this exception */
		}

		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	/**
	 * 此方法是把Vector的数组转化为传送到前台的xml.并且是上一个和下一个为不同的含义. root 是xml的根节点.
	 * attrs是属性值数组.attrs[0]是主节点值. author:刘斌
	 */
	public static String VectorToXml(Vector vec, String[] attrs) {
		String result = "";
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		if (vec != null) {
			for (int i = 0; i < vec.size(); i = i + 2) {
				Element info = new Element(attrs[0]);

				for (int j = 1; j < attrs.length; j++) {
					info.addAttribute(new Attribute(attrs[j], vec
							.get(i + j - 1).toString()));
				}
				list.add(info);
			}
		}
		rootNode.setChildren(list);
		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");
		StringWriter sw = new StringWriter();
		try {
			out.output(doc, sw);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
		result = sw.toString();

		return result;
	}

	public String getCenterList() throws DbException {
		String result = "";
		ArrayList list = new ArrayList();
		String sql = "select t.name name ,t.center_id id from res_center_tab t where center_id <> '100'";
		DbComponent dbComponent = new DbComponent();
		GDSet gdSet = null;
		gdSet = dbComponent.Query(sql);
		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		for (int i = 0; i < gdSet.getRowCount(); i++) {

			Element center = new Element("center");

			try {
				center.addAttribute(new Attribute("center_name", gdSet
						.getString(i, "name")));
				center.addAttribute(new Attribute("center_id", gdSet.getString(
						i, "id")));
			} catch (GDSetException ex1) {

				ex1.printStackTrace();
			}

			list.add(center);

		}

		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();

		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}

		result = sw.toString();
		System.out.println(result);
		return result;

	}

	public String getAllCenterList() throws DbException {
		String result = "";
		ArrayList list = new ArrayList();
		String sql = "select t.name name ,t.center_id id from res_center_tab t ";
		DbComponent dbComponent = new DbComponent();
		GDSet gdSet = null;
		gdSet = dbComponent.Query(sql);
		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		for (int i = 0; i < gdSet.getRowCount(); i++) {

			Element center = new Element("center");

			try {
				center.addAttribute(new Attribute("center_name", gdSet
						.getString(i, "name")));
				center.addAttribute(new Attribute("center_id", gdSet.getString(
						i, "id")));
			} catch (GDSetException ex1) {

				ex1.printStackTrace();
			}

			list.add(center);

		}

		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();

		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}

		result = sw.toString();
		System.out.println(result);
		return result;

	}

	public String getWebList(String id) throws DbException {
		ArrayList list = new ArrayList();
		String result = "";
		String sql = "select t.head_id headId,t.shortName name from res_headend_tab t where is_delete=0 and t.center_id='"
				+ id + "'";
		DbComponent dbComponent = new DbComponent();
		GDSet gdSet = null;
		gdSet = dbComponent.Query(sql);
		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		for (int i = 0; i < gdSet.getRowCount(); i++) {

			Element web = new Element("web");
			try {
				web.addAttribute(new Attribute("short_name", gdSet.getString(i,
						"name")));
				web.addAttribute(new Attribute("head_id", gdSet.getString(i,
						"headId")));
			} catch (GDSetException ex1) {

				ex1.printStackTrace();
			}

			list.add(web);

		}

		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();

		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}

		result = sw.toString();
		System.out.println(result);
		return result;
	}

	public String getHead_Status(String code) throws DbException {
		ArrayList list = new ArrayList();
		String result = "";
		String sql = "select t.head_id headId,t.shortName name,t.code code, t.is_online is_online from res_headend_tab t where is_delete =0 and  t.code='"
				+ code + "'";
		DbComponent dbComponent = new DbComponent();
		GDSet gdSet = null;
		gdSet = dbComponent.Query(sql);
		Document doc = StringTool.getXmlMsg();
		Element rootNode = doc.getRootElement();
		for (int i = 0; i < gdSet.getRowCount(); i++) {

			Element web = new Element("web");
			try {
				web.addAttribute(new Attribute("short_name", gdSet.getString(i,
						"name")));
				web.addAttribute(new Attribute("head_id", gdSet.getString(i,
						"headId")));
				web.addAttribute(new Attribute("code", gdSet.getString(i,
						"code")));
				web.addAttribute(new Attribute("is_online", gdSet.getString(i,
						"is_online")));
			} catch (GDSetException ex1) {

				ex1.printStackTrace();
			}

			list.add(web);

		}

		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();

		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}

		result = sw.toString();
		System.out.println(result);
		return result;
	}

	/**
	 * 查找centerId或者headeCode对应中心包含的所有在线站点
	 * 
	 * @param centerId
	 *            String 中心Id
	 * @param headeCode
	 *            String 中心代码
	 * @return String[][] String[0][] 名称 String[1][] code String[2][]
	 *         在线不在线（0不在线，1在线） String[3][] 站点类型 String[4][] 中心Id
	 */
	public static String[][] onlineHeadCode(String centerId, String headeCode) {
		// 查找所有站点
		String[][] head_code = null;
		String[] short_name = null;
		String[] code = null;
		String[] is_online = null;
		String[] type_id = null;
		String[] center_id = null;
		String[] version = null; // 版本

		GDSet data2 = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("select shortname,code,is_online,type_id,center_id,version from res_headend_tab where is_delete =0 ");
			/**
        *
        */
			if (centerId != null && !centerId.equals("")) {
				buffer.append(" and center_id='" + centerId + "' ");
			} else if (headeCode != null && !headeCode.equals("")) {
				// 字符格式转换
				headeCode = SiteVersionUtil.getSiteOriCode(headeCode);
				buffer.append(" and code='" + headeCode + "' ");

			}
			buffer.append(" order by type_id,is_online,shortname desc ");
			data2 = DbComponent.Query(buffer.toString());
		} catch (Exception ex) {
			System.out.println("head_code(int x,String center_id)出错" + ex);
		}

		int rowCount2 = data2.getRowCount();
		if (rowCount2 > 0) {
			head_code = new String[7][rowCount2]; // 0是名称，1是code，2是在线不在线（0不在线，1在线）
			short_name = new String[rowCount2];
			code = new String[rowCount2];
			is_online = new String[rowCount2];
			type_id = new String[rowCount2];
			center_id = new String[rowCount2];
			version = new String[rowCount2];
			for (int i = 0; i < rowCount2; i++) {
				try {
					short_name[i] = data2.getString(i, "shortname");
					code[i] = data2.getString(i, "code");
					is_online[i] = data2.getString(i, "is_online");
					type_id[i] = data2.getString(i, "type_id");
					center_id[i] = data2.getString(i, "center_id");
					version[i] = data2.getString(i, "version");
				} catch (Exception e) {
					System.out.println("head_code获得时错误。" + e);
				}
				head_code[0][i] = short_name[i] + "[" + code[i] + "]";
				head_code[1][i] = code[i];
				head_code[2][i] = is_online[i];
				head_code[3][i] = type_id[i];
				head_code[4][i] = center_id[i];
				head_code[5][i] = short_name[i];
				head_code[6][i] = version[i];
			}
		}

		// for(int i=0;i<rowCount2;i++)
		// {
		// for(int j=0;j<5;j++)
		// {
		// System.out.println("head_code["+j+"]["+i+"]="+head_code[j][i]);
		// }
		// }
		return head_code;
	}

	/**
	 * 查找headeCode对应中心Id，然后调用onlineHeadCode(Id,“”) 取得包含的所有在线站点
	 * 
	 * @param headeCode
	 *            String 中心代码
	 * @return String[][] String[0][] 名称 String[1][] code String[2][]
	 *         在线不在线（0不在线，1在线） String[3][] 站点类型 String[4][] 中心Id
	 */
	public static String[][] onlineHeadCode(String headeCode) {
		// 查找所有站点
		String[][] head_code = null;
		String center_id = null;

		if (headeCode != null && !headeCode.equals("")) {
			// 字符格式转换
			headeCode = SiteVersionUtil.getSiteOriCode(headeCode);
		}
		String sql = " select " + " distinct(center_id) "
				+ " from 	RES_HEADEND_TAB " + " where is_delete=0 and code='"
				+ headeCode + "'";

		try {

			GDSet gd_rh = DbComponent.Query(sql);
			if (gd_rh == null || gd_rh.getRowCount() == 0) {
				return head_code;
			}

			int rowCount = gd_rh.getRowCount();

			if (rowCount > 0) {
				center_id = gd_rh.getString(0, "center_id");
			}
			// System.out.println("center_id=" + center_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (center_id != null) {
			head_code = onlineHeadCode(center_id, "");
		}

		return head_code;
	}
}
