package com.viewscenes.sys.userlog;
import java.util.*;
import com.viewscenes.util.*;
import java.io.StringReader;
import org.jdom.Document;
import org.jdom.Element;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;
import com.viewscenes.util.StringTool;
import com.viewscenes.sys.userlog.SystemLogFunction;

public class UserLog {
//    private static String requestMsg = "";
//    private static String reponseMsg = "";
//    private static String userId = "";
//    private static String userPriority = "";
//    private static String functionCode = "";
//    private static String resultFlag = "";
//    private static String strDate = "";
//    private static String ip = "";
//    private static String dep = "";
//    private static String headCode = "";
//    private static String des = "";
//
//    public UserLog(String requestMsg,String reponseMsg,String ip){
//        this.requestMsg = requestMsg;
//        this.reponseMsg = reponseMsg;
//        this.strDate = StringTool.Date2String(null);
//        this.parseRequestMsg();
//        this.parseReponseMsg();
//        this.ip = ip;
//    }
//
//    public UserLog(String user_id,String priority,String functionCode,String headCode,String resultFlag,String user_ip,String des){
//        this.userId = user_id;
//        this.userPriority = priority;
//        this.functionCode = functionCode;
//        this.headCode = headCode;
//        this.resultFlag = resultFlag;
//        this.ip = user_ip;
//        this.des = des;
//    }
//
//    private void parseRequestMsg(){
//        try{
//            StringReader read = new StringReader(this.requestMsg);
//            org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
//            Document doc = builder.build(read);
//            Element root = doc.getRootElement();
//            this.userId = root.getChild("user_id").getText();
//            this.userPriority = root.getChild("priority").getText();
//            if(root.getChild("headCode")!=null)
//                this.headCode = root.getChild("headCode").getText();
//            this.functionCode = root.getAttributeValue("function");
//        }catch(Exception ex){
//            ex.printStackTrace();
//            LogTool.output("用户日志解析请求XML出错！"+ex);
//        }
//    }
//
//    private void parseReponseMsg(){
//        try{
//            StringReader read = new StringReader(this.reponseMsg);
//            org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(false);
//            Document doc = builder.build(read);
//            Element root = doc.getRootElement();
//            if(this.userId.equals("")){
//                if(root.getChild("user")!=null){
//                   Element userE = root.getChild("user");
//                   this.userId = userE.getAttributeValue("user_id");
//                   this.userPriority = userE.getAttributeValue("user_priority");
//                }
//            }
//            if(root.getChild("error")==null)
//                this.resultFlag = "1";//成功
//            else
//                this.resultFlag = "0";
//        }catch(Exception ex){
//            ex.printStackTrace();
//            LogTool.output("用户日志解析响应XML出错！"+ex);
//        }
//    }
//
//    public void insertUserLog(){
//        String user_sql = "select * from sso_user_tab where user_id = "+this.userId;
//        String org_sql = "select * from Info_organization_tab where system_code = '"+SystemConfig.localSystemCode+"'";
//        //String func_sql = "select * from sso_function_tab where code = '"+this.functionCode+"'";
//        try{
//            if((this.userId!=null)&&(!this.userId.equals(""))){
//                GDSet userSet = DbComponent.Query(user_sql);
//                GDSet orgSet = DbComponent.Query(org_sql);
//                if (SystemConfig.systemLogFunction.get(this.functionCode) != null) {
//                    SystemLogFunction slf = (SystemLogFunction) SystemConfig.
//                                            systemLogFunction.get(this.
//                            functionCode);
//                    String sql =
//                            "insert into sso_log_user_tab (log_id,user_id,user_name,user_ip,"
//                            +
//                            "org_id,org_name,system_code,function_id,function_name,"
//                            +
//                            "priority,result,log_time,description,headCode) values(seq_log.nextval,"
//                            + this.userId + ",'" + userSet.getString(0, "name") +
//                            "','" + this.ip + "',"
//                            + orgSet.getString(0, "org_id") + ",'" +
//                            orgSet.getString(0, "name") + "','"
//                            + SystemConfig.localSystemCode + "'," +
//                            slf.getFuncId() + ",'"
//                            + slf.getFuncName() + "','" + this.userPriority +
//                            "','"
//                            + this.resultFlag + "',to_date('" + this.strDate +
//                            "','yyyy-MM-dd HH24:MI:SS'),'"
//                            + this.des+"','"+getHeadNameByHeadCode(this.headCode)+"')";
//                    DbComponent.exeUpdate(sql);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            LogTool.output("用户日志写数据库出错！"+ex);
//        }
//    }
//
//    public String getHeadNameByHeadCode(String headCode){
//        String sql = "select head_id from info_headend_tab where code = '"+headCode+"'";
//        String head_id = "";
//        try{
//            GDSet gd = DbComponent.Query(sql);
//            head_id = gd.getString(0,"name");
//        }catch(Exception ex){
//
//        }
//        return head_id;
//    }


}



