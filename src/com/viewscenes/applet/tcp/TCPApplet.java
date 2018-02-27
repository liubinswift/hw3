package com.viewscenes.applet.tcp;

import java.applet.Applet;

public class TCPApplet extends Applet {
    int m_iCount = 0;
    public static int DEFAULT_PORT = 8010;
    public static int MAX_THREAD_NUM = 1;
    //Get a parameter value
    TCPDataThread dataThread = null;
    //Construct the applet
    public TCPApplet() {
    }

    //Initialize the applet
    public void init() {
        try {
            jbInit();
            initConnection();
            //TestThread thread = new TestThread(this);
            //thread.start();
            //int i = getConnection("10.1.2.141");
            //sendClientInfo(i,"real://10.1.2.141/0","10.1.2.146","chengang","1000001","90000X10");
            //closeConnection(i);
            //getConnection("10.1.2.143");
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initConnection(){
                    dataThread = new TCPDataThread(this,"",DEFAULT_PORT);
                    //setConnParam("10.1.2.143");
                    //sendClientInfo("real://10.1.2.143:5050/0","10.1.2.147","管理员","2","900000X20");
                    //dataThread.start();
    }



    public void closeConnection(){
        if(dataThread!=null){
            System.out.println("关闭客户端连接");
            dataThread.bRun = false;
            dataThread.closeConnection();
//            dataThread = null;
        }
    }
    public void setConnParam(String ip){
//        if(dataThread == null){
//            dataThread = new TCPDataThread(this, ip, DEFAULT_PORT);
//        }
        dataThread.ip = ip;
        dataThread.port = DEFAULT_PORT;
    }
    public void alertMsg(String msg){
        System.out.println("alertMsg:"+msg);
        //JSObject.getWindow(this).eval("alert('"+msg+"')");
    }


    public void sendClientInfo(String url,String ip,String userName,String priority,String centerCode,String msgType){
        if (url.startsWith("real")){
            int loc = url.lastIndexOf("/");
            int loc2 = url.lastIndexOf("/",loc-1);
            if (loc2>10)
                url = "real://"+url.substring(loc2+1);
        }
        else if (url.startsWith("maudio")){
            int loc = url.lastIndexOf("/");
            int loc2 = url.lastIndexOf("/",loc-1);
            if (loc2>10)
                url = "maudio://"+url.substring(loc2+1);
        }
        System.out.println("发送客户端信息：url="+url+",ip="+ip+",userName="+userName+",priority="+priority+",centerCode="+centerCode);
        if(msgType.equals("1")){
            byte[] cmdBuffer = MonTCPCommand.getClientInfoCommand(url, ip,
                    userName, priority, centerCode);
            dataThread.setCommand(cmdBuffer);
        }else if(msgType.equals("2")){
            byte[] cmdBuffer = MonTCPCommand.getRadioClientInfoCommand(url, ip,
                    userName, priority, centerCode);
            dataThread.setCommand(cmdBuffer);
        }else{
            byte[] cmdBuffer = MonTCPCommand.getClientInfoCommand(url, ip,
                    userName, priority, centerCode);
            dataThread.setCommand(cmdBuffer);
        }
        if (!dataThread.bRun){
            dataThread.bRun = true;
            dataThread.start();
        }
    }



    //Component initialization
    private void jbInit() throws Exception {
    }

    //Get Applet information
    public String getAppletInfo() {
        return "Applet Information";
    }

    //Get parameter info
    public String[][] getParameterInfo() {
        return null;
    }
}
