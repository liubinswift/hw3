package com.viewscenes.applet;

public class AppletUtil {
    public AppletUtil() {
    }

    public static int bytes2int(byte[] b,int pos)
    {
        byte[] b1 = new byte[4];
        System.arraycopy(b,pos,b1,0,4);
        byte[] b2 = invertBytes(b1);
             int mask=0xff;
             int temp=0;
            int res=0;
            for(int i=0;i<4;i++){
                res<<=8;
                temp=b2[i]&mask;
                res|=temp;
            }
           return res;
    }

    public static long bytes2long(byte[] b,int pos)
    {
        byte[] b1 = new byte[8];
        System.arraycopy(b,pos,b1,0,8);
        byte[] b2 = invertBytes(b1);

             long mask=0xff;
             long temp=0;
            long res=0;
            for(int i=0;i<8;i++){
                res<<=8;
                temp=b2[i]&mask;
                res|=temp;
            }
           return res;
    }

    public static int bytes2short(byte[] b,int pos)
    {
        byte[] b1 = new byte[2];
        System.arraycopy(b,pos,b1,0,2);
        byte[] b2 = invertBytes(b1);

             int mask=0xff;
             int temp=0;
            int res=0;
            for(int i=0;i<2;i++){
                res<<=8;
                temp=b2[i]&mask;
                res|=temp;
            }
           return res;
    }


    public static byte[] int2bytes(int num)
    {
           byte[] b=new byte[4];
           int mask=0xff;
           for(int i=0;i<4;i++){
                b[i]=(byte)(num>>>(24-i*8));
           }
           return invertBytes(b);
    }

    public static byte[] invertBytes(byte[] b){
        byte[] b2 = new byte[b.length];
        for (int i=0;i<b.length;i++){
            b2[b.length-1-i] = b[i];
        }
        return b2;
    }

    public static String replaceStr(String src, String oldStr, String newStr){

      if ((src==null)||(oldStr==null)||(newStr==null)) return "";

      StringBuffer buffer = new StringBuffer();

      int indexStart = 0;

      int indexEnd = src.indexOf(oldStr);

      while (indexEnd!=-1){

        buffer.append(src.substring(indexStart,indexEnd));

        buffer.append(newStr);

        indexStart = indexEnd+oldStr.length();

        indexEnd = src.indexOf(oldStr,indexStart);

      }

      buffer.append(src.substring(indexStart));

      return buffer.toString();

    }


}
