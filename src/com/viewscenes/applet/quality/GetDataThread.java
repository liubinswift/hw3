package com.viewscenes.applet.quality;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GetDataThread extends Thread {
    public boolean bRun = true;
    boolean bSendCmd = false;
    public String ip = "";
    public int port = 0;

    public chartApplet m_parent = null;

    Socket devSoc = null;
    OutputStream os = null;
    InputStream is = null;
    byte[] buffer = new byte[81920];
    byte[] sendBuffer = null;

    public void setCommand(byte[] sendBuffer){
        this.sendBuffer = sendBuffer;
        bSendCmd = true;
    }

    public GetDataThread() {
    }

    public void run(){
        try {
            //m_parent.setStatusMsg("��������ǰ���豸["+ip+","+port+"]......");
            devSoc = new Socket(ip, port);
            os = devSoc.getOutputStream();
            is = devSoc.getInputStream();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            //m_parent.setStatusMsg("ǰ���豸["+ip+","+port+"]�޷�����");
             bRun = false;

        } catch (IOException ex) {
            //m_parent.setStatusMsg("��ǰ���豸["+ip+","+port+"]ͨѶ�������ϣ�������ֹ");
            ex.printStackTrace();
            bRun = false;
        }

        if (bRun){
            try {
                //m_parent.setStatusMsg("���ڽ���ǰ���豸["+ip+","+port+"]Ƶ�ײ�������");
                int nRead=0;
                while (nRead>=0&&bRun){
                    //ÿ��ѭ���ȿ��Ƿ�������Ҫ����
                    if(bSendCmd){
                        try {

                            os.write(sendBuffer, 0, sendBuffer.length);
                            os.flush();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        bSendCmd = false;
                    }


                    nRead = is.read(buffer);
                    if (nRead>1){
                        char ch1 = (char) buffer[0];
                        char ch2 = (char) buffer[1];
                        if (!(ch1 == 'o' && ch2 == 'k')) {
                            System.out.println("device send quality data");
                            QualityData data = new QualityData();
                            data.parseData(buffer);
                            m_parent.addNewValue(data);
                            /*for (int i=0;i<data.dataLength;i++){
                                m_parent.addNewValue(data.freqs[i],data.values[i]);
                            }*/
                        }
                        else{
                            System.out.println("device send 'ok'");
                        }
                    }
                }
            } catch (IOException ex) {
                //m_parent.setStatusMsg("��ǰ���豸["+ip+","+port+"]ͨѶ�������ϣ�������ֹ");
                ex.printStackTrace();
                bRun = false;
            }
            finally{
                buffer = null;
                try {
                    os.close();
                } catch (IOException ex1) {
                }
                try {
                    is.close();
                } catch (IOException ex2) {
                }
                try {
                    devSoc.close();
                } catch (IOException ex3) {
                }
            }
        }
    }





}
