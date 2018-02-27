package com.viewscenes.applet.tcp;

import com.viewscenes.applet.freqscan.FreqScanData;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.*;

public class TCPDataThread extends Thread {
    public TCPDataThread(TCPApplet applet,String ip, int port) {
        super();
        this.parentApplet = applet;
        this.ip = ip;
        this.port = port;
    }
    public TCPApplet parentApplet;
    public boolean bRun = false;
    boolean bSendCmd = false;
    public String ip = "";
    public int port = 0;

    Socket devSoc = null;
    OutputStream os = null;
    InputStream is = null;
    byte[] buffer = new byte[81920];
    byte[] sendBuffer = null;

    public void setCommand(byte[] sendBuffer){
        this.sendBuffer = sendBuffer;
        bSendCmd = true;
    }

    public void setStatusCallBack(String callback){

    }

    public void init(String ip,int port){
        System.out.println("��ʼ����������");
        this.ip = ip;
        this.port = port;
    }

    public void deinit(){
        bRun = false;
        try {
            if (devSoc!=null){
                devSoc.shutdownOutput();
                devSoc.shutdownInput();
                closeConnection();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("������������");

    }


    public void setStatusMsg(String status){
        System.out.println(status);
    }

    public boolean createConnection(){
        try {
            setStatusMsg("��������ǰ���豸["+ip+","+port+"]......");
            devSoc = new Socket(ip, port);
            os = devSoc.getOutputStream();
            is = devSoc.getInputStream();
            setStatusMsg("ǰ���豸["+ip+","+port+"]���ӳɹ�");
        } catch (UnknownHostException ex) {
            setStatusMsg("ǰ���豸["+ip+","+port+"]�޷�����");
           return false;
        } catch (IOException ex) {
            setStatusMsg("��ǰ���豸["+ip+","+port+"]ͨѶ�������ϣ�������ֹ");
            return false;
        }
        return true;
    }

    public void closeConnection(){
        System.out.println("�ر�TCP���ӣ�");
        try {
            if (os!=null)
            os.close();
        } catch (IOException ex1) {
        }
        try {
            if (is!=null)
            is.close();
        } catch (IOException ex2) {
        }
        try {
            if (devSoc!=null)
            devSoc.close();
        } catch (IOException ex3) {
        }
    }
    public void sendCommand(){
        try {
            setStatusMsg("����ָ�ǰ���豸["+ip+","+port+"]");
            os.write(sendBuffer, 0, sendBuffer.length);
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int recviveData(){
        int nRead = 0;
        try {
            nRead = is.read(buffer);
            if (nRead>0){
                System.out.println("�յ�ǰ������:");
                for (int i=0;i<nRead;i++){
                    System.out.print(buffer[i]);
                    System.out.print(" ");
                }
                System.out.println("");
            }
        } catch (IOException ex) {
            setStatusMsg("��ǰ���豸["+ip+","+port+"]ͨѶ�������ϣ�������ֹ");
            bRun = false;
            return -1;
        }
        if (nRead>1){
            char ch1 = (char) buffer[0];
            char ch2 = (char) buffer[1];
            if (!(ch1 == 'o' && ch2 == 'k')) {
                System.out.println("device send data");
                MonTCPData data = new MonTCPData();
                data.parseData(buffer);
                if (data.dataLength>0&&data.msgDescLen>0){
                    parentApplet.alertMsg(data.msgDesc);
                }
            }
            else{
                System.out.println("device send 'ok'");
            }
        }
        return nRead;
    }

    public void run(){

            if (!createConnection()) {
                bRun = false;
            }

            while (bRun) {
                //setStatusMsg("���ڽ���ǰ���豸[" + ip + "," + port + "]������");
                int nRead = 0;
                while (nRead >= 0 && bRun) {
                    //ÿ��ѭ���ȿ��Ƿ�������Ҫ����
                    if (bSendCmd) {
                        sendCommand();
                        bSendCmd = false;
                    }
                    nRead = recviveData();
                }
            }
            closeConnection();
            System.out.println("�̹߳���ѭ������");
        }



}
