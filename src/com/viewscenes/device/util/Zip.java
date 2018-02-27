package com.viewscenes.device.util;

/**
 * <p>Title:��ѹ�ϱ�������zip�ļ�. </p>
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

import sun.net.TelnetInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import sun.net.ftp.FtpClient;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.viewscenes.sys.SystemConfig;

/**
 * ���ܣ� 1 ��ʵ�ְ�ָ���ļ����µ������ļ�ѹ��Ϊָ���ļ�����ָ�� zip �ļ� 2 ��ʵ�ְ�ָ���ļ����µ� zip �ļ���ѹ��ָ��Ŀ¼��
 * 
 * @author
 * 
 */

public class Zip {

//	public static void main(String[] args) {
//
//		unZip("ftp://10.10.6.51:21/upload/1274593724.zip");
//	}

	/**
	 * ���ܣ��� sourceDir Ŀ¼�µ������ļ����� zip ��ʽ��ѹ��������Ϊָ�� zip �ļ� create date:2009- 6- 9
	 * author:Administrator
	 * 
	 * @param sourceDir
	 * @param zipFile
	 *            ��ʽ�� E:\\stu \\zipFile.zip ע�⣺���� zipFile ���Ǵ�����ַ���ֵ�� ��
	 *            "E:\\stu \\" ���� "E:\\stu " ��� E ���Ѿ����� stu ����ļ��еĻ�����ô�ͻ����
	 *            java.io.FileNotFoundException: E:\stu ( �ܾ����ʡ� )
	 *            ����쳣������Ҫע����ȷ���ε��ñ�����Ŷ
	 * 
	 */
	//
	// public static void zip(String sourceDir, String zipFile) {
	//
	// OutputStream os;
	//
	// try {
	//
	// os = new FileOutputStream(zipFile);
	// BufferedOutputStream bos = new BufferedOutputStream(os);
	// ZipOutputStream zos = new ZipOutputStream(bos);
	//
	// File file = new File(sourceDir);
	//
	// String basePath = null ;
	//
	// if (file.isDirectory()) {
	// basePath = file.getPath();
	// } else {
	// basePath = file.getParent();
	// }
	//
	// zipFile (file, basePath, zos);
	// zos.closeEntry();
	// zos.close();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// /**
	// *
	// * create date:2009- 6- 9 author:Administrator
	// *
	// * @param source
	// * @param basePath
	// * @param zos
	// * @throws IOException
	// */
	//
	// private static void zipFile(File source, String basePath,
	//
	// ZipOutputStream zos) {
	//
	// File[] files = new File[0];
	// if (source.isDirectory()) {
	// files = source.listFiles();
	//
	// } else {
	// files = new File[1];
	// files[0] = source;
	//
	// }
	//
	//
	//
	// String pathName;
	//
	// byte [] buf = new byte [1024];
	//
	// int length = 0;
	//
	// try {
	//
	// for (File file : files) {
	//
	// if (file.isDirectory()) {
	//
	// pathName = file.getPath().substring(basePath.length() + 1)
	//
	// + "/" ;
	//
	// zos.putNextEntry( new ZipEntry(pathName));
	//
	// zipFile (file, basePath, zos);
	//
	// } else {
	//
	// pathName = file.getPath().substring(basePath.length() + 1);
	//
	// InputStream is = new FileInputStream(file);
	//
	// BufferedInputStream bis = new BufferedInputStream(is);
	//
	// zos.putNextEntry( new ZipEntry(pathName));
	//
	// while ((length = bis.read(buf)) > 0) {
	//
	// zos.write(buf, 0, length);
	//
	// }
	//
	// is.close();
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	//
	// // TODO Auto-generated catch block
	//
	// e.printStackTrace();
	//
	// }
	//
	//
	//
	// }

	/**
	 * 
	 * ��ѹ zip �ļ���ע�ⲻ�ܽ�ѹ rar �ļ�Ŷ��ֻ�ܽ�ѹ zip �ļ� ��ѹ rar �ļ� ����� java.io.IOException:
	 * Negative
	 * 
	 * seek offset �쳣 create date:2009- 6- 9 author:Administrator
	 * 
	 * 
	 * 
	 * @param zipfile
	 * 
	 *            zip �ļ���ע��Ҫ�����ڵ� zip �ļ�Ŷ�������ǰ� rar ��ֱ�Ӹ�Ϊ zip ���������
	 *            java.io.IOException:
	 * 
	 *            Negative seek offset �쳣
	 * 
	 * @param destDir
	 * 
	 * @throws IOException
	 */
	static final int BUFFER = 512;

	public static StringBuilder unZip(String zipfile) {
		// ftp://10.10.6.12:21/upload/R36D01_AutoReportFile_759539193.zip
		String[] ss = zipfile.split("/");
		String filename = "";
		String host = "";
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].split("\\.").length == 4) {

				host = ss[i].split(":")[0];

			}
			if (ss[i].indexOf(".zip") != -1) {
				filename = ss[i];
			}
		}
		StringBuffer bmsg = null;
		// String filename=zipfile.substring(
		// zipfile.indexOf("upload")+7,zipfile.length());
		String str; // �����Ϣ�ַ���
		/**
		 * �ͷ�������������
		 * ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
		 */
		FtpClient ftp = null;
		
		StringBuilder buf = new StringBuilder();
		try {

			ftp = new FtpClient(host); // ���ݷ�����ip��������

			str = ftp.getResponseString(); // �����Ӧ��Ϣ
			System.out.println("���ӷ�����:" + str);
			/**
			 * ��½��Ftp������
			 */
			ftp.login("user", "user"); // �����û����������¼������
			str = ftp.getResponseString();
			System.out.println("��¼:" + str);
			/**
			 * �򿪲���λ��������Ŀ¼
			 */
			ftp.cd("upload"); // �򿪷������ϵ��ļ�Ŀ¼
			str = ftp.getResponseString();
			System.out.println("�򿪷�����Ŀ¼:" + str);
			ftp.binary(); // ת��Ϊ�����Ƶ��ļ�
			TelnetInputStream ftpIn = ftp.get(filename); // �ҵ�Ҫ��ȡ���ļ�

			ZipInputStream zis = new ZipInputStream(ftpIn);

			ZipEntry entry;
			InputStreamReader ir = new InputStreamReader(zis);
			BufferedReader in = new BufferedReader(ir);
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				// �����ϱ���xmlѹ���ļ��� �����н�ѹ��
				if (entry.getName().toLowerCase().indexOf(".xml") == -1) {

					return null;
				}

				String line = "";

				while ((line = in.readLine()) != null) {
					buf.append(line);
					// str.append("\n");
				}

			}

			// int count =0;
			// byte data[] = new byte[BUFFER];
			//
			// while ((count = zis.read(data))
			// != -1) {
			//
			// // System.out.println(s);
			// strAll.append(data);
			// }
			ir.close();
			in.close();
			zis.close();
			ftpIn.close();
			ftp.closeServer();
			// bmsg = new StringBuffer();
			//
			// String nmsg = sss.toString();
			// int j = 0;
			// for(int i=0; i<nmsg.length(); i++) {
			// if(nmsg.charAt(i) == '>') {
			// bmsg.append(nmsg.substring(j,i+1)+"\n");
			// j = i+1;
			// }
			// }
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("��ѹ���������⣡����" + zipfile);
			return null;
		}
		System.out.print(buf.toString().length());
		return buf;
	}

	// public static void unZip_bak(String zipfile) {
	//
	// ZipFile zipFile;
	// try {
	// zipFile = new ZipFile( new File(zipfile));
	//
	// Enumeration enumeration = zipFile.getEntries();
	//
	// ZipEntry zipEntry = null ;
	//
	// StringBuilder str=new StringBuilder();
	//
	// while (enumeration.hasMoreElements()) {
	//
	// zipEntry = (ZipEntry) enumeration.nextElement();
	//
	// InputStream inputStream = zipFile.getInputStream(zipEntry);
	//
	// InputStreamReader ir=new InputStreamReader(inputStream);
	// BufferedReader in=new BufferedReader(ir);
	// String line="";
	// while((line=in.readLine())!=null)
	// {
	// str.append(line);
	// str.append("\n");
	// }
	// }
	// com.viewscenes.util.LogTool.debug("logMsg",str.toString());
	// } catch (IOException e) {
	// com.viewscenes.util.LogTool.debug("logMsg","��ѹ���ļ�"+zipfile+"�����쳣��");
	// }
	// }
	//
	// public static void unZipNew(String zipfile) {
	//
	// ZipFile zipFile;
	// try {
	// FtpClient fc=new FtpClient("10.10.6.12");
	// fc.login("user","user");
	// //fc.cd("/upload/");
	// fc.binary();
	//
	// System.out.println(fc.welcomeMsg);
	// TelnetInputStream
	// fget=fc.get("//upload//R36D01_AutoReportFile_691359661.zip");
	// DataInputStream input=new DataInputStream(fget);
	//
	// String filename="c:\\"+zipfile.substring(
	// zipfile.indexOf("upload")+8,zipfile.length());
	// File f=new File(filename);
	// if(!f.exists())
	// {
	// f.createNewFile();
	// }
	// FileWriter out=new FileWriter(filename);
	// int ch=0;
	// while((ch=input.read())>=0)
	// {
	// out.write(ch);
	// }
	// input.close();
	// fget.close();
	// fc.closeServer();
	// out.close();
	// zipFile = new ZipFile( new File(filename));
	//
	// Enumeration enumeration = zipFile.getEntries();
	//
	// ZipEntry zipEntry = null ;
	//
	// StringBuilder str=new StringBuilder();
	//
	// while (enumeration.hasMoreElements()) {
	//
	// zipEntry = (ZipEntry) enumeration.nextElement();
	//
	// InputStream inputStream = zipFile.getInputStream(zipEntry);
	//
	// InputStreamReader ir=new InputStreamReader(inputStream);
	// BufferedReader in=new BufferedReader(ir);
	// String line="";
	// while((line=in.readLine())!=null)
	// {
	// str.append(line);
	// str.append("\n");
	// }
	// }
	// System.out.println(str);
	// com.viewscenes.util.LogTool.debug("logMsg",str.toString());
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	//
	// }
	// }

	/**
	 *  fileUrl:Ҫ���ص��ļ�ftp·��
	 *  user :ftp��¼��
	 *  psw : ftp����
	 *  
	 */
	public static FtpClient loginFtp(String fileUrl, String user,
			String psw) {
		FtpClient ftp = null;
		TelnetInputStream ftpIn = null;
		ZipEntry zipEntry;
		ZipInputStream zipIn = null;
		String video_location = "d:\\video_location\\";

		int is_null = 1; // ��ȡ��־����ȡFTP�ļ��������ȡ���ɹ���ѭ����ȡ
		int run_count = 1; // ѭ����ȡ����������һ���޶Ȳ���ѭ��
		while (is_null == 1 && run_count < 30) {
			// 1. ��ȡFTP�ļ�
			InputStream in = null; // ��ȡFTP�ļ�����
			try {
				String[] ss = fileUrl.split("/");
				String filename = "";
				String host = "";

				for (int ii = 0; ii < ss.length; ii++) {
					if (ss[ii].split("\\.").length == 4) {

						host = ss[ii].split(":")[0];

					}
					if (ss[ii].indexOf(".zip") != -1) {
						filename = ss[ii];
					}
				}
				StringBuffer bmsg = null;
				String str; // �����Ϣ�ַ���

				ftp = new FtpClient(host); // ���ݷ�����ip��������

				str = ftp.getResponseString(); // �����Ӧ��Ϣ
				System.out.println("���ӷ�����:" + str);
				/**
				 * ��½��Ftp������
				 */
				ftp.login("user", "user"); // �����û����������¼������
				str = ftp.getResponseString();
				System.out.println("��¼:" + str);
				/**
				 * �򿪲���λ��������Ŀ¼
				 */
				ftp.cd("upload"); // �򿪷������ϵ��ļ�Ŀ¼
				str = ftp.getResponseString();
				System.out.println("�򿪷�����Ŀ¼:" + str);
				ftp.binary(); // ת��Ϊ�����Ƶ��ļ�
				ftpIn = ftp.get(filename); // �ҵ�Ҫ��ȡ���ļ�

				zipIn = new ZipInputStream(ftpIn);

			} catch (IOException ex2) { // ��ȡ����˵���ļ���δ���أ��ظ���ȡ
				ex2.printStackTrace();
				run_count++; // ѭ��������1
				try {
					Thread.currentThread().sleep(1000); // ��ʱ1��
				} catch (Exception ex1) {
				}
				if (zipIn != null) { // �رն�ȡ��
					try {
						zipIn.close();
					} catch (IOException e) {
					}
				}
				continue; // �������������ٴζ�ȡ
			}
		}
		return null;
	}
	
	
	/**
	 * ���ϴ���ftp�ϵ��ļ����н�ѹ��
	 * <p>class/function:com.viewscenes.device.util
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-9-21
	 * @param:
	 * @return:
	 */
	public static  void unZip2Local(String fileUrl) {
		System.out.println("���ڽ�ѹ�ϴ��ļ���"+fileUrl);
		if (fileUrl.endsWith(".zip")) {
			FtpClient ftp = null;
			TelnetInputStream ftpIn = null;
			ZipEntry zipEntry;
			ZipInputStream zipIn = null;
			String video_location = SystemConfig.getVideoPath();

			int is_null = 1; // ��ȡ��־����ȡFTP�ļ��������ȡ���ɹ���ѭ����ȡ
			int run_count = 1; // ѭ����ȡ����������һ���޶Ȳ���ѭ��
			while (is_null == 1 && run_count < 30) {
				// 1. ��ȡFTP�ļ�
				InputStream in = null; // ��ȡFTP�ļ�����
				try {
					String[] ss = fileUrl.split("/");
					String filename = "";
					String host = "";

					for (int ii = 0; ii < ss.length; ii++) {
						if (ss[ii].split("\\.").length == 4) {

							host = ss[ii].split(":")[0];

						}
						if (ss[ii].indexOf(".zip") != -1) {
							filename = ss[ii];
						}
					}
					String str; // �����Ϣ�ַ���
					/**
					 * �ͷ������������� ftp://10.10.6.12:21//upload//
					 * R36D01_AutoReportFile_691359661.zip
					 */

					ftp = new FtpClient(host); // ���ݷ�����ip��������

					str = ftp.getResponseString(); // �����Ӧ��Ϣ
					System.out.println("���ӷ�����:" + str);
					/**
					 * ��½��Ftp������
					 */
					ftp.login("user", "user"); // �����û����������¼������
					str = ftp.getResponseString();
					System.out.println("��¼:" + str);
					/**
					 * �򿪲���λ��������Ŀ¼
					 */
					ftp.cd("upload"); // �򿪷������ϵ��ļ�Ŀ¼
					str = ftp.getResponseString();
					System.out.println("�򿪷�����Ŀ¼:" + str);
					ftp.binary(); // ת��Ϊ�����Ƶ��ļ�
					ftpIn = ftp.get(filename); // �ҵ�Ҫ��ȡ���ļ�

					zipIn = new ZipInputStream(ftpIn);

				} catch (IOException ex2) { // ��ȡ����˵���ļ���δ���أ��ظ���ȡ
					ex2.printStackTrace();
					run_count++; // ѭ��������1
					try {
						Thread.currentThread().sleep(1000); // ��ʱ1��
					} catch (Exception ex1) {
					}
					if (zipIn != null) { // �رն�ȡ��
						try {
							zipIn.close();
						} catch (IOException e) {
						}
					}
					continue; // �������������ٴζ�ȡ
				}

				// 2. FTP�ļ��Ѿ�����(����һ��ȫ���������)����ʼ��ѹ��
				try {
					zipEntry = zipIn.getNextEntry(); // zip��������λΪzipEntry
					if (zipEntry == null) {
						throw new IOException("�Ҳ���Զ�̻����ļ���");
					}
				} catch (IOException ex5) { // ˵��FTP�ļ���δ��ȫ���سɹ����ظ���ȡ
					run_count = run_count++; // ѭ��������1
					try {
						Thread.currentThread().sleep(2000); // ��ʱ1��
					} catch (Exception ex1) {
					}

					if (zipIn != null) { // �رն�ȡ��
						try {
							zipIn.close();
						} catch (IOException e) {
						}
					}
					continue; // �������������ٴζ�ȡ
				}

				// 3.3 ���浽ָ���ı����ļ���
				OutputStream os = null;
				try {
					String zipName = zipEntry.getName().substring(0, 8);
					if (zipName.equalsIgnoreCase("TempFile")) {
						zipName = zipEntry.getName().substring(9,
								zipEntry.getName().length());
					} else {
						zipName = zipEntry.getName();
					}

					// System.out.println(zipEntry.getName());
					os = new BufferedOutputStream(new FileOutputStream(
							video_location + zipName));

					int readLen = 0;
					byte[] buf = new byte[1024];

					while ((readLen = zipIn.read(buf, 0, 1024)) != -1) { // �������zip��������1024�ֽڴ�С���жϽ��н�ѹ����ŵı��ص��ļ�
						os.write(buf, 0, readLen);
					}
					System.out.println("��ѹ���ļ���"+zipName+"���");
					// new File(url).delete();
				} catch (IOException ex6) {
					continue;
					// throw new ModuleException("ת���ļ�����");
				} finally {
					if (zipIn != null) {
						try {
							zipIn.close();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
						}
					}
				}
				
				is_null = 0;
			}
			ftp.sendServer("QUIT\r\n");
			
		}
	}
	
	/**
	 * ɾ��ftp�ϵ��ļ�
	 * fileUrl��ʽ��ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
	 * fileUrl��ʽ��http://10.15.6.12:8000/video/OAF07A_42640_20120920143532_20120920143833_852_R1.mp3
	 * <p>class/function:com.viewscenes.device.util
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-9-21
	 * @param:
	 * @return:
	 */
	public static  boolean delFileFromFtp(String fileUrl) {
//		ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
		System.out.println("׼��ɾ����"+fileUrl);
		FtpClient ftp = null;
		String video_url = SystemConfig.getVideoUrl();

		if (fileUrl.indexOf(video_url) != -1)
			fileUrl = fileUrl.replace(video_url, SystemConfig.getFtpUrl());
		
		// 1. ��ȡFTP�ļ�
		try {
			String[] ss = fileUrl.split("/");
			String filename = "";
			String host = "";
			String dir = "";	//ȥ��url�е�ip���˿ڼ��ļ������֣��м�����Ŀ¼·��
			for (int ii = 0; ii < ss.length; ii++) {
				if (ss[ii].split("\\.").length == 4) {

					host = ss[ii].split(":")[0];

				}
				if (ss[ii].indexOf(".mp3") != -1) {
					filename = ss[ii];
				}
			}
			
			//�����Э��ftp�����һ����/���Ŀ�ʼλ��
			int start =fileUrl.indexOf("/",6);
			//�����ļ���ǰ��ĵ�һ��"/"��λ��
			int end = fileUrl.lastIndexOf("/");
			
			dir = fileUrl.substring(start,end);
			
			String str; // �����Ϣ�ַ���
			/**
			 * �ͷ������������� 
			 */

			ftp = new FtpClient(host); // ���ݷ�����ip��������

			str = ftp.getResponseString(); // �����Ӧ��Ϣ
			System.out.println("���ӷ�����:" + str);
			/**
			 * ��½��Ftp������
			 */
			ftp.login("user", "user"); // �����û����������¼������
			str = ftp.getResponseString();
			System.out.println("��¼:" + str);
			/**
			 * �򿪲���λ��������Ŀ¼
			 */
			ftp.cd(dir); // �򿪷������ϵ��ļ�Ŀ¼
			str = ftp.getResponseString();
			System.out.println("�򿪷�����Ŀ¼:" + str);
			ftp.binary(); // ת��Ϊ�����Ƶ��ļ�
			
			
			ftp.sendServer(" DELE "+filename +"\r\n");
			
			str = ftp.getResponseString();
			
			System.out.println("ɾ��¼���ļ�:"+filename+" "+str);

		} catch (IOException ex2) { // ��ȡ����˵���ļ�δɾ���ɹ�
			ex2.printStackTrace();
			//run_count++; // ѭ��������1
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		Zip zip = new Zip();
		zip.delFileFromFtp("http://10.15.6.12:8000/video/OAF07A_42640_20120920143532_20120920143833_852_R1.mp3");
		
		String str = "ftp://10.12.12.1:21/upload/sb/s/ss.mp3";
		
		int i =str.indexOf("/",6);
		int j = str.lastIndexOf("/");
		str = str.substring(i, j);
		System.out.println(str+"="+i+" "+j);
	}
}
