package com.viewscenes.device.util;

import com.viewscenes.device.framework.IMessage;
/**
 *
 * <p>Title:��¼�·��ϱ���Ϣ </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author ����
 * @version 1.0
 */
public  class SaveDownUpMessage {
    public SaveDownUpMessage() {
    }

    public  void saveDownMsg(IMessage downMsg)
  {

//          /**
//          * �¼����·�������.
//          * author:����
//          *         MSGID
//                    OPERATION_CODE
//                    TYPE
//                    SRCCODE
//                    DSTCODE
//                    DATETIME
//                    DESCRIPTION
//                    OPERATION_NAME
//          */
//         String MSGID = downMsg.getMsgID();
//         String OPERATION_CODE = downMsg.getBody().getName();
//         String TYPE = (String) downMsg.getHeader().getAttributes().get("Type");
//         String SRCCODE = (String) downMsg.getHeader().getAttributes().get("SrcCode");
//         String DSTCODE = (String) downMsg.getHeader().getAttributes().get("DstCode");
//         String DATETIME = (String) downMsg.getHeader().getAttributes().get("DateTime");
//         String DESCRIPTION = (String) downMsg.getMessage();
//         String OPERATION_NAME = downMsg.getBody().getName();
//
//                 String sql ="insert into sys_xinxioperation_log_tab values(SYS_XINXIOPERATION_LOG_SEQ.nextval,"+MSGID
//                             +",'"+OPERATION_CODE+"','"+TYPE
//                             +"','"+SRCCODE+"','"+DSTCODE+"',"
//                             +"to_date('"+DATETIME+"','yyyy-MM-dd HH24:MI:SS')"
//                              +",to_clob('"+DESCRIPTION+"'),'"+OPERATION_NAME+"',"
//                             +"1)";
//
//             try {
//                 DbComponent.exeUpdate(sql);
//             } catch (DbException ex) {
//                 System.out.println("�·���Ϣ������ʧ��!");
//                 ex.printStackTrace();
//             }

  }
  public  void saveUpMsg(IMessage upMsg)
  {
          /**
          * �¼����ϱ�������.
          * author:����
          */
//        String opa="";
//       if(upMsg.getBody()==null)
//       {
//          opa= upMsg.getReturnEle().getAttributeValue("Type");
//       }else
//       {
//           opa=upMsg.getBody().getName();
//       }
//       String OPERATION_CODE = opa;
//       String TYPE = (String) upMsg.getHeader().getAttributes().get("Type");
//       String SRCCODE = (String) upMsg.getHeader().getAttributes().get("SrcCode");
//       String DSTCODE = (String) upMsg.getHeader().getAttributes().get("DstCode");
//       String DATETIME = (String) upMsg.getHeader().getAttributes().get("DateTime");
//       String MSGID = (String) upMsg.getHeader().getAttributes().get("ReplyID");
//       String flag="0";
//       if(MSGID.equals("-1"))
//       {
//          flag="-1";
//       }
//       String DESCRIPTION = (String) upMsg.getMessage();
//       String OPERATION_NAME = opa;
//
//
//                String sql ="insert into sys_xinxioperation_log_tab values(SYS_XINXIOPERATION_LOG_SEQ.nextval,"+MSGID
//                            +",'"+OPERATION_CODE+"','"+TYPE
//                            +"','"+SRCCODE+"','"+DSTCODE+"',"
//                            +"to_date('"+DATETIME+"','yyyy-MM-dd HH24:MI:SS')"
//                             +",to_clob('"+DESCRIPTION+"'),'"+OPERATION_NAME+"',"
//                            +flag+")";
//            try {
//                DbComponent.exeUpdate(sql);
//            } catch (DbException ex) {
//                System.out.println("�ϱ���Ϣ������ʧ��!");
//                ex.printStackTrace();
//            }


  }
    public  String codeToName(String  code)
    {
        String result="";
        if (code.equals("QualityAlarmHistoryQuery ")) {
            result = "ָ�걨����ʷ��ѯ";
        } else if (code.equals("QualityAlarmHistoryReport")) {
            result = "ָ�걨����ʷ��ѯ����";
        } else if (code.equals("QualityAlarmParamSet")) {
            result = "ָ�걨����������";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        } else if (code.equals("")) {
            result = "";
        }

        return result;
    }

}
