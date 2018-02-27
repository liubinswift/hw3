package com.viewscenes.util.report.printtxt;

import java.io.*;


/** *//**
 * 
 * ��������������TXT�ļ������ж���д���޸Ĳ���
 *      
 * @author 
 * @version 1.0 
 * Creation date: 
 */
public class ReadWriteFile {
    public static BufferedReader bufread;
    //ָ���ļ�·��������
    private static String path = "D:/suncity.txt";
    private static  File filename = new File(path);
    private static String readStr ="";


    /** *//**
     * �����ı��ļ�.
     * @throws IOException 
     * 
     */
    public static void creatTxtFile() throws IOException{
        if (!filename.exists()) {
            filename.createNewFile();
            System.err.println(filename + "�Ѵ�����");
        }
    }
    
    /** *//**
     * ��ȡ�ı��ļ�.
     * 
     */
    public static String readTxtFile(){
        String read;
        FileReader fileread;
        try {
            fileread = new FileReader(filename);
            bufread = new BufferedReader(fileread);
            try {
                while ((read = bufread.readLine()) != null) {
                    readStr = readStr + read+ "\r\n";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("�ļ�������:"+ "\r\n" + readStr);
        return readStr;
    }
    
    /** *//**
     * д�ļ�.
     * 
     */
    public static void writeTxtFile(String newStr,String filename) throws IOException{
        //�ȶ�ȡԭ���ļ����ݣ�Ȼ�����д�����
        String filein = newStr;
        RandomAccessFile mm = null;
        try {
            mm = new RandomAccessFile(filename, "rw");
//            mm.writeBytes(filein);
            byte[] buffer = filein.getBytes("UTF-8");
            mm.write(buffer);
        } catch (IOException e1) {
            // TODO �Զ����� catch ��
            e1.printStackTrace();
        } finally {
            if (mm != null) {
                try {
                    mm.close();
                } catch (IOException e2) {
                    // TODO �Զ����� catch ��
                    e2.printStackTrace();
                }
            }
        }
    }
    
    /** *//**
     * ���ļ���ָ�����ݵĵ�һ���滻Ϊ��������.
     * 
     * @param oldStr
     *            ��������
     * @param replaceStr
     *            �滻����
     */
    public static void replaceTxtByStr(String oldStr,String replaceStr) {
        String temp = "";
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // �������ǰ�������
            for (int j = 1; (temp = br.readLine()) != null
                    && !temp.equals(oldStr); j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }

            // �����ݲ���
            buf = buf.append(replaceStr);

            // ������к��������
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }

            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** *//**
     * main��������
     * @param s
     * @throws IOException
     */
    public static void main(String[] s) throws IOException {
//        ReadWriteFile.creatTxtFile();
//        ReadWriteFile.readTxtFile();
//        ReadWriteFile.writeTxtFile("kkkwww","d:/test.txt");
//        ReadWriteFile.replaceTxtByStr("ken", "zhang");
    }
}


