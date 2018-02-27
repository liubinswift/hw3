package com.viewscenes.web.sysmgr.user;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.dao.database.DbException;
import com.viewscenes.web.sysmgr.user.PubRoleBean;
import com.viewscenes.web.sysmgr.user.PubUserBean;
import com.viewscenes.pub.GDSet;
import com.viewscenes.pub.GDSetException;
import com.viewscenes.sys.MD5;
import com.viewscenes.util.StringTool;

public class PubUserManagerServiceDao {
	
private static MD5 md5;
	
	static{
		md5 = new MD5();
	}

	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:查询角色列表信息,role为空查询所有,不为空查询指定角色
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static GDSet getRoleSet(String roleId) throws DbException{

		GDSet gdSet = null;
		
		StringBuffer sql = new StringBuffer(" select * from sec_role_tab t  ");

		if (roleId != null && roleId.equals(""))
			
			sql.append("  where t.role_id = "+roleId+" ");
		
		sql.append("  order by name asc ");
			
		gdSet = DbComponent.Query(sql.toString());
			
		return gdSet;

	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:添加一个角色,添加成功返回true,否则返回false
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static boolean addRole(PubRoleBean pubRoleBean) throws DbException{
		
		boolean ret = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" insert into sec_role_tab(role_id, name, description, priority) ");
		
		sql.append(" values (SEC_SEQ.nextval, '"+pubRoleBean.getName()+"', '"+pubRoleBean.getDescription()+"', '"+pubRoleBean.getPriority()+"') ");
		
		ret = DbComponent.exeUpdate(sql.toString());
		
		return ret;  
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:修改角色信息,成功返回true,否则返回false
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static boolean updateRole(PubRoleBean pubRoleBean) throws DbException{
		
		boolean ret = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update sec_role_tab ");
		
		sql.append(" set name        = '"+pubRoleBean.getName()+"', ");
		
		sql.append(" description = '"+pubRoleBean.getDescription()+"', ");
		
		sql.append(" priority    = '"+pubRoleBean.getPriority()+"' ");
		
		sql.append(" where role_id = "+pubRoleBean.getRole_id()+" ");
	
		ret = DbComponent.exeUpdate(sql.toString());
		       
		return ret;      
		       
		 
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:删除一个指定ID的角色
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static int[] delRoleById(String roleId) throws DbException{
		
		int ret[];
		
		StringBuffer sqlR = new StringBuffer();
		StringBuffer sqlRU = new StringBuffer();
		StringBuffer sqlRF = new StringBuffer();
		StringBuffer sqlU = new StringBuffer();
		
		//1.删除角色
		sqlR.append(" delete from  sec_role_tab  where role_id = "+roleId+" ");
		
		//2.删除用户
		sqlU.append(" delete from sec_user_tab u where u.user_id in (select user_id from sec_user_role_rel_tab rel where role_id = "+roleId+") ");
		
		//3.删除与用户的关联关系
		sqlRU.append(" delete from  sec_user_role_rel_tab where role_id = "+roleId+" ");
		
		//4.删除与权限的关联关系
		sqlRF.append(" delete from sec_role_operation_rel_tab  where role_id = "+roleId+" ");
		
		//注意删除顺序2不能与3颠倒
		
		String[] sqls = new String[4];
		sqls[0] = sqlR.toString();
		sqls[1] = sqlU.toString();
		sqls[2] = sqlRU.toString();
		sqls[3] = sqlRF.toString();

		ret = DbComponent.exeBatch(sqls);
		
		return ret;
		
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:查询指定的角色是否存在
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 * @throws GDSetException 
	 */
	public static boolean isExistRole(PubRoleBean roleBean) throws DbException, GDSetException{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from sec_role_tab t where t.name = '"+roleBean.getName()+"' ");
		
		GDSet set = null;
		
		set = DbComponent.Query(sql.toString());
		
		String role_id = "";
		
		if (set.getRowCount()>0){
			
			role_id = set.getString(0, "role_id");
			
			return role_id.equals(roleBean.getRole_id())?false:true;
			
		}else{
			return false;
		}
		
	}
	
	
	
	
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:根据角色ID查询用户列表
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static GDSet getUserSetByRoleId(String roleId) throws DbException {
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select u.*,r.role_id,r.name roleName,r.priority ");
		
		sql.append(" from sec_user_tab  u, sec_user_role_rel_tab rel,sec_role_tab r ");
		
		sql.append(" where rel.user_id = u.user_id ");
		
		sql.append(" and rel.role_id = "+roleId+" ");
		
		sql.append(" and rel.role_id = r.role_id ");
			
		GDSet gdSet = null;
	
		gdSet = DbComponent.Query(sql.toString());
			
		
		return gdSet;
		
	}
	/**
	 * 超级管理员直接取得全部权限
	 * @detail  
	 * @method  
	 * @param broadcast_type
	 * @return
	 * @throws DbException 
	 * @return  GDSet  
	 * @author  zhaoyahui
	 * @version 2013-3-14 上午10:38:45
	 */
	public static GDSet getFuncSetByAdmin(String... broadcast_type) throws DbException{
		
		StringBuffer sql = new StringBuffer();
		String typeid = "0";
		if(broadcast_type.length>0){
			typeid = broadcast_type[0];
        }
		sql.append(" select f.* from sec_operation_tab f ");
		sql.append(" where f.broadcast_type = "+typeid  );
		sql.append(" and f.show_flag = 1  and  operation_id not in(16,17,19,20) "  );
		sql.append(" order by f.OPERATION_ID");
		
		GDSet gdSet = null;
		
		gdSet = DbComponent.Query(sql.toString());
		
		return gdSet;
 
	}
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:返回指定角色ID的权限列表
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static GDSet getFuncSetByRoleId(String roleId,String... broadcast_type) throws DbException{
		if(roleId.equals("323")){
			return getHeadSet();
		}
		StringBuffer sql = new StringBuffer();
		String typeid = "0";
		if(broadcast_type.length>0){
			typeid = broadcast_type[0];
        }
		sql.append(" select f.* from sec_operation_tab f,sec_role_operation_rel_tab rf,sec_role_tab r ");
		sql.append(" where f.OPERATION_ID = rf.OPERATION_ID ");
		sql.append(" and rf.role_id = r.role_id ");
		sql.append(" and f.broadcast_type = "+typeid  );
		sql.append(" and f.show_flag = 1 "  );
		sql.append(" and r.role_id = "+roleId+"  order by f.OPERATION_ID");
		
		GDSet gdSet = null;
		
		gdSet = DbComponent.Query(sql.toString());
		
		return gdSet;
 
	}
	public static GDSet getFuncSetSpical() throws DbException{
		String sql="select * from sec_operation_tab  where operation_id in(16,17,19,20) order by operation_id";
		GDSet gdSet=DbComponent.Query(sql);
		return gdSet;
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:返回指定角色ID的权限列表
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static GDSet getCustByUserId(String user_id) throws DbException{
	
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select f.* from sec_operation_tab f,sec_user_operation_custom_tab sf,sec_user_tab s ");
		sql.append(" where f.OPERATION_ID = sf.OPERATION_ID ");
		sql.append(" and sf.user_id = s.user_id ");
		sql.append(" and s.user_id = "+user_id+"  order by f.OPERATION_ID");
		
		GDSet gdSet = null;
		
		gdSet = DbComponent.Query(sql.toString());
		
		return gdSet;
 
	}
	public static GDSet getHeadCustByUserId(String user_id)throws DbException{
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select f.* from " );
		sql.append("(select 101 as OPERATION_ID, '采集点' as OPERATION_NAME, 0 as PARENT_OPERATION_ID  from dual");
		sql.append(" union select 102 as OPERATION_ID, '遥控站' as OPERATION_NAME, 0 as PARENT_OPERATION_ID from dual");
		sql.append(" union select h.head_id OPERATION_ID,h.shortname OPERATION_NAME,h.type_id PARENT_OPERATION_ID  from res_headend_tab h , sec_role_operation_rel_tab rf  where h.is_delete = 0 ");
		sql.append(") f,sec_user_operation_custom_tab sf,sec_user_tab s ");
		sql.append(" where f.OPERATION_ID = sf.OPERATION_ID ");
		sql.append(" and sf.user_id = s.user_id ");
		sql.append(" and s.user_id = "+user_id+"  order by f.OPERATION_ID");
		
		GDSet gdSet = null;
		
		gdSet = DbComponent.Query(sql.toString());
		
		return gdSet;
	}
	/**
	 * 
	 * <p>class/function:com.viewscenes.database.service.usermanager
	 * <p>explain:取得添加用户时用户的序列
	 * <p>author-date:张文2012-7-23
	 * @param:
	 * @return:
	 */
	private static String getUserNextVal() throws DbException, GDSetException{
		
		String sql = " select SEC_SEQ.nextval val from dual ";
		
		GDSet set = DbComponent.Query(sql);
		
		String val = set.getString(0, "val");
		
		return val;
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:添加一个用户，如果用户添加成功返回true,否则返回false;
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:添加用户是否成功
	 * @throws DbException 
	 */
	public static int[] addUser(PubUserBean pubUserBean) throws Exception{
		
		int[] ret ;
		
		
		String val = getUserNextVal();
		
		StringBuffer sql = new StringBuffer();
		
		StringBuffer sqlUR = new StringBuffer();
		
		sqlUR.append(" insert into sec_user_role_rel_tab(user_id,role_id) values("+val+","+pubUserBean.getRoleId()+")");
		
		sql.append(" insert into sec_user_tab(user_id, user_code, user_name, user_password, sex, telephone, address, email, description, age, mobilephone, post) ");
		// start_time, end_time, priority
		sql.append(" values("+val+", '"+pubUserBean.getUserCode()+"', '"+pubUserBean.getUserName()+"', '"+md5.getMD5ofStr(pubUserBean.getUserPassword())+"', "+pubUserBean.getSex()+", '"+pubUserBean.getTelephone()+"'," +
				" '"+pubUserBean.getAddress()+"', '"+pubUserBean.getEmail()+"', '"+pubUserBean.getDescription()+"', '"+pubUserBean.getAge()+"', '"+pubUserBean.getMobilephone()+"', '"+pubUserBean.getPost()+"') ");		
		
		//, to_date('"+pubUserBean.getStart_time()+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+pubUserBean.getEnd_time()+"','yyyy-mm-dd HH24:mi:ss'), '"+pubUserBean.getPriority()+"'
		String[] sqls = new String[2];
		
		sqls[0] = sqlUR.toString();
		sqls[1] = sql.toString();
		
		ret = DbComponent.exeBatch(sqls);
		int[] res = new int[1];
		res[0] = Integer.parseInt(val);
		return res;
		  
		
		  

	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:修改一个用户信息，如果用户修改成功返回true,否则返回false
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:修改用户是否成功
	 * @throws DbException 
	 */
	public static boolean updateUser(PubUserBean pubUserBean) throws DbException{
		
		boolean ret = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" update sec_user_tab ");
		sql.append(" set user_code = '"+pubUserBean.getUserCode()+"', ");
		sql.append(" user_name = '"+pubUserBean.getUserName()+"', ");
		sql.append(" user_password = '"+md5.getMD5ofStr(pubUserBean.getUserPassword())+"', ");
		sql.append(" sex = "+pubUserBean.getSex()+", ");
		sql.append(" telephone = '"+pubUserBean.getTelephone()+"', ");
		sql.append(" address = '"+pubUserBean.getAddress()+"', ");
		sql.append(" email = '"+pubUserBean.getEmail()+"', ");
//		sql.append(" start_time = to_date('"+pubUserBean.getStart_time()+"','yyyy-mm-dd hh24:mi:ss'), ");
//		sql.append(" end_time = to_date('"+pubUserBean.getEnd_time()+"','yyyy-mm-dd hh24:mi:ss'), ");
		sql.append(" description = '"+pubUserBean.getDescription()+"', ");
		sql.append(" age = '"+pubUserBean.getAge()+"', ");
		sql.append(" mobilephone = '"+pubUserBean.getMobilephone()+"', ");
		sql.append(" post = '"+pubUserBean.getPost()+"' ");
		sql.append(" where user_id = "+pubUserBean.getUserId()+" ");

		ret = DbComponent.exeUpdate(sql.toString());       
		       
		return ret;      
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:删除一个用户信息，如果删除成功返回true,否则返回false
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:删除用户是否成功
	 * @throws DbException 
	 */
	public static boolean delUserById(String userId) throws DbException{
		boolean ret = false;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" delete from  sec_user_tab  where user_id = "+userId+" ");
		
		ret = DbComponent.exeUpdate(sql.toString()); 
		
		return ret;
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:查询指定的用户是否已存在,存在返回true,否则返回false
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:用户是否存在
	 * @throws DbException 
	 * @throws GDSetException 
	 */
	public static boolean isExistUser(PubUserBean pubUserBean) throws DbException, GDSetException{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from sec_user_tab    t where t.user_name = '"+pubUserBean.getUserName()+"' ");
		
		GDSet set = null;
		
		set = DbComponent.Query(sql.toString());
		
		String user_id = "";
		
		if (set.getRowCount()>0){
			
			user_id = set.getString(0, "user_id");
			
			return user_id.equals(pubUserBean.getUserId())?false:true;
			
		}else{
			return false;
		}
		
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:取得所有权限结果集
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 */
	public static GDSet getAllFuncSet() throws DbException{
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select * from sec_operation_tab where broadcast_type = 0 and show_flag = 1  order by OPERATION_ID ");
		
		GDSet set = null;
		
		set = DbComponent.Query(sql.toString());
		
		return set;
	}
	
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:为用户添加模块
	 * <p>author-date:张文 2012-8-19
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static int[] addUserCust(String user_id,List<String> funcList) throws DbException{

		int[] ret ;
		
		StringBuffer delSql = new StringBuffer(" delete from sec_user_operation_custom_tab where user_id = "+user_id+" ");

		String[] sqls = new String[funcList.size()+1];

		int i = 0;
		
		sqls[i] = delSql.toString();
		
		for(Iterator<String> it=funcList.iterator(); it.hasNext();){
			
			String nodeText = (String)it.next();
			
			StringBuffer inSql = new StringBuffer();
			
			inSql.append(" insert into sec_user_operation_custom_tab(user_id, OPERATION_ID)  ");
			
			inSql.append(" values("+user_id+", "+nodeText+")  ");
			
			sqls[++i] = inSql.toString();
		}
			
		ret = DbComponent.exeBatch(sqls);
	
		return ret;
	
	}
	
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:获取角色权限树
	 * <p>author-date:张文2012-2-28
	 * @param:
	 * @return:
	 * @throws Exception 
	 */
	public static String getCustTreeByUserId(String user_id,String ...role_id) throws Exception{
		
		String result = "";
		GDSet allFuncSet=null;
		GDSet roleFuncSet=null;
		if(role_id.length>0){
			allFuncSet = getFuncSetByRoleId(role_id[0]);
			if(role_id[0].equals("323")){
				roleFuncSet =  getHeadCustByUserId(user_id);
			}else{
				roleFuncSet =  getCustByUserId(user_id);
			}
		}else{
			allFuncSet=getAllFuncSet();
			roleFuncSet =  getCustByUserId(user_id);
		}
		
		
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();

		Element rootNode = doc.getRootElement();
		
		Element root = new Element("tree");
		root.addAttribute(new Attribute("text",""));
		
		Element title = new Element("node");
		title.addAttribute(new Attribute("text","定制功能列表"));
		
		ArrayList rootList = new ArrayList();
		
		HashMap nodeMap = new HashMap();
		HashMap relMap = new HashMap();
		
		for(int i=0 ; i<roleFuncSet.getRowCount(); i++){
			String funcId = roleFuncSet.getString(i, "OPERATION_ID");
			relMap.put(funcId, "1");
		}
		
		for(int i=0 ; i<allFuncSet.getRowCount(); i++){
			Element node = new Element("node");
			
			node.addAttribute(new Attribute("text",allFuncSet.getString(i, "OPERATION_NAME")));
			
			String funcId = allFuncSet.getString(i, "OPERATION_ID");
			node.addAttribute(new Attribute("id",funcId));
			
			String val = (String)relMap.get(funcId);
			if(val == null){
				val = "0";
			}
			 node.addAttribute(new Attribute("data",val));
			 
			 nodeMap.put(funcId, node);
			 
			 String PARENT_OPERATION_ID = allFuncSet.getString(i, "PARENT_OPERATION_ID");
			 if(PARENT_OPERATION_ID.equals("0")){
				 rootList.add(node);
			 }else{
				 String parentFuncId = allFuncSet.getString(i, "PARENT_OPERATION_ID");
				 Element parentNode = (Element)nodeMap.get(parentFuncId);
				 if(parentNode != null){
					 List childList = parentNode.getChildren();
					 childList.add(node);
				 }
			 }
		}
		
		for(int i=0; i<rootList.size(); i++){
			Element treeNode = (Element)rootList.get(i);
			CheckFunctionNodeData(treeNode);
		}
		
		title.setChildren(rootList);
		ArrayList tList = new ArrayList();
		tList.add(title);
		root.setChildren(tList);
		
		list.add(root);
		
		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();
		
		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}
		result = sw.toString();
//		}catch(Exception e){
//			e.printStackTrace();
//			return new EXEException("", "获取权限列表失败,"+e.getMessage(), "");
//		}
		
		

		return result;
	}
	
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:为指定角色进行授权
	 * <p>author-date:张文2012-7-27
	 * @param:
	 * @return:
	 * @throws DbException 
	 */
	public static int[] grantFuncByRoleId(String roleId,List<String> funcList) throws DbException{

		int[] ret ;
		
		StringBuffer delSql = new StringBuffer("");
		if(roleId.equals("323")){
			delSql.append(" delete sec_role_operation_rel_tab where Role_id= 323");
		}else{
			delSql.append("  delete sec_role_operation_rel_tab where Role_id='"+roleId+"' " +
          	" and sec_role_operation_rel_tab.operation_id in " +
          	"(select sec_operation_tab.operation_id from sec_operation_tab where sec_operation_tab.broadcast_type=0) ");
		}

		String[] sqls = new String[funcList.size()+1];

		int i = 0;
		
		sqls[i] = delSql.toString();
		
		for(Iterator<String> it=funcList.iterator(); it.hasNext();){
			
			String nodeText = (String)it.next();
			
			StringBuffer inSql = new StringBuffer();
			
			inSql.append(" insert into sec_role_operation_rel_tab(role_id, OPERATION_ID)  ");
			
			inSql.append(" values("+roleId+", "+nodeText+")  ");
			
			sqls[++i] = inSql.toString();
		}
			
		ret = DbComponent.exeBatch(sqls);
	
		return ret;
	
	}
	
	/**
	 * <p>class/function:com.viewscenes.database.service.pub
	 * <p>explain:获取角色权限树
	 * <p>author-date:张文2012-2-28
	 * @param:
	 * @return:
	 * @throws Exception 
	 */
	public static String getFuncTreeByRoleId(String roleId) throws Exception{
		String result = "";
		
		GDSet allFuncSet =null;
		if(roleId.equals("323")){
			allFuncSet= getAllHeadSet();
		}else{
			allFuncSet=getAllFuncSet();
		}
		
		GDSet roleFuncSet = getFuncSetByRoleId(roleId);
		
		ArrayList list = new ArrayList();

		Document doc = StringTool.getXmlMsg();

		Element rootNode = doc.getRootElement();
		
		Element root = new Element("tree");
		root.addAttribute(new Attribute("text",""));
		
		Element title = new Element("node");
		title.addAttribute(new Attribute("text","功能列表"));
		
		ArrayList rootList = new ArrayList();
		
		HashMap nodeMap = new HashMap();
		HashMap relMap = new HashMap();
		
		for(int i=0 ; i<roleFuncSet.getRowCount(); i++){
			String funcId = roleFuncSet.getString(i, "OPERATION_ID");
			relMap.put(funcId, "1");
		}
		
		for(int i=0 ; i<allFuncSet.getRowCount(); i++){
			Element node = new Element("node");
			
			node.addAttribute(new Attribute("text",allFuncSet.getString(i, "OPERATION_NAME")));
			
			String funcId = allFuncSet.getString(i, "OPERATION_ID");
			node.addAttribute(new Attribute("id",funcId));
			
			String val = (String)relMap.get(funcId);
			if(val == null){
				val = "0";
			}
			 node.addAttribute(new Attribute("data",val));
			 
			 nodeMap.put(funcId, node);
			 
			 String PARENT_OPERATION_ID = allFuncSet.getString(i, "PARENT_OPERATION_ID");
			 if(PARENT_OPERATION_ID.equals("0")){
				 rootList.add(node);
			 }else{
				 String parentFuncId = allFuncSet.getString(i, "PARENT_OPERATION_ID");
				 Element parentNode = (Element)nodeMap.get(parentFuncId);
				 if(parentNode != null){
					 List childList = parentNode.getChildren();
					 childList.add(node);
				 }
			 }
		}
		
		for(int i=0; i<rootList.size(); i++){
			Element treeNode = (Element)rootList.get(i);
			CheckFunctionNodeData(treeNode);
		}
		
		title.setChildren(rootList);
		ArrayList tList = new ArrayList();
		tList.add(title);
		root.setChildren(tList);
		
		list.add(root);
		
		rootNode.setChildren(list);

		XMLOutputter out = new XMLOutputter("  ", true, "GB2312");

		StringWriter sw = new StringWriter();
		
		try {

			out.output(doc, sw);

		} catch (IOException ex2) {

			ex2.printStackTrace();
		}
		result = sw.toString();
//		}catch(Exception e){
//			e.printStackTrace();
//			return new EXEException("", "获取权限列表失败,"+e.getMessage(), "");
//		}
		
		

		return result;
	}
	private static GDSet getAllHeadSet()throws DbException{
		StringBuffer sql = new StringBuffer();
		sql.append("select 101 as OPERATION_ID, '采集点' as OPERATION_NAME, 0 as PARENT_OPERATION_ID  from dual union ");
		sql.append(" select 102 as OPERATION_ID, '遥控站' as OPERATION_NAME, 0 as PARENT_OPERATION_ID  from dual union");
		sql.append(" select h.head_id OPERATION_ID,h.shortname OPERATION_NAME,h.type_id PARENT_OPERATION_ID  from res_headend_tab h , sec_role_operation_rel_tab rf ");
		sql.append(" where h.is_delete = 0 order by OPERATION_ID ");	
		GDSet gdSet = null;		
		gdSet = DbComponent.Query(sql.toString());		
		return gdSet;
	}
	private static GDSet getHeadSet() throws DbException{
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from (");
		sql.append(" select 101 as OPERATION_ID, '采集点' as OPERATION_NAME, 0 as PARENT_OPERATION_ID ,1 as show_flag from dual union ");
		sql.append(" select 102 as OPERATION_ID, '遥控站' as OPERATION_NAME, 0 as PARENT_OPERATION_ID,1 as show_flag  from dual union");
		sql.append(" select h.head_id OPERATION_ID,h.shortname OPERATION_NAME,h.type_id PARENT_OPERATION_ID ,1 as show_flag from res_headend_tab h ");
		sql.append(" where h.is_delete = 0) f, sec_role_operation_rel_tab rf  where f.OPERATION_ID=rf.OPERATION_ID ");
		sql.append(" and rf.role_id = "+323+"  order by f.OPERATION_ID");		
		GDSet gdSet = null;		
		gdSet = DbComponent.Query(sql.toString());		
		return gdSet;
	}
    private static void CheckFunctionNodeData(Element funcNode) {
        List children = funcNode.getChildren();

        if (children.size() == 0){
        	String dataValue = funcNode.getAttributeValue("data");
        	if(Integer.parseInt(dataValue) == 1){
        		Attribute attr = funcNode.getAttribute("checked");
                if(attr != null){
                	 attr.setValue("1");
                }else{
            		funcNode.addAttribute("checked","1");
                }
        	}else if(Integer.parseInt(dataValue) == 0){
        		Attribute attr = funcNode.getAttribute("checked");
        		if(attr != null){
               	 attr.setValue("0");
               }else{
           		funcNode.addAttribute("checked","0");
               }
        	}
        	else{
        		Attribute attr = funcNode.getAttribute("checked");
        		if(attr != null){
               	 attr.setValue("2");
               }else{
           		funcNode.addAttribute("checked","2");
               }
	    	}
            return;
        }

        for (int i = 0; i < children.size(); i++) {

            Element node = (Element) children.get(i);

            CheckFunctionNodeData(node);
        }
        int enableCount = 0;

        int disableCount = 0;

        for (int i = 0; i < children.size(); i++) {

            Element node = (Element) children.get(i);

            String data = node.getAttributeValue("checked");

            if (data.equals("1"))

                enableCount++;

            else if (data.equals("0"))

                disableCount++;
        }
        if (enableCount == children.size()) {

            Attribute attr = funcNode.getAttribute("checked");
            if(attr != null){
            	 attr.setValue("1");
            }else{
        		funcNode.addAttribute("checked","1");
            }

        } else if (disableCount == children.size()) {

            Attribute attr = funcNode.getAttribute("checked");
            if(attr != null){
            	attr.setValue("0");
            }else{
         		funcNode.addAttribute("checked","0");
            }
        } else {
            Attribute attr = funcNode.getAttribute("checked");
            if(attr != null){
            	attr.setValue("2");
            }else{
        		funcNode.addAttribute("checked","2");
            }
        }


    }
    
    /**
     * 
     * <p>class/function:com.viewscenes.database.service.usermanager/getUserByUserName
     * <p>explain:根据用户名获取用户所有信息
     * <p>时间：2012-3-1
     * <p>作者：wxb
     * @param userName
     * @throws DbException 
     */
    public static GDSet getUserByUserName(String userName) throws DbException{
    	String sql = "select u.*,r.role_id,r.name role_name,r.priority " +
    			" from sec_user_tab u, sec_user_role_rel_tab rel,sec_role_tab r " +
    			" where rel.user_id = u.user_id " +
    			" and rel.role_id = r.role_id " +
    			" and u.user_name='"+userName+"'";
    	
    	return DbComponent.Query(sql);
    	
    	
    }

	

}
