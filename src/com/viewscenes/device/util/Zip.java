package com.viewscenes.device.util;

/**
 * <p>Title:解压上报上来的zip文件. </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author 刘斌
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
 * 功能： 1 、实现把指定文件夹下的所有文件压缩为指定文件夹下指定 zip 文件 2 、实现把指定文件夹下的 zip 文件解压到指定目录下
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
	 * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件 create date:2009- 6- 9
	 * author:Administrator
	 * 
	 * @param sourceDir
	 * @param zipFile
	 *            格式： E:\\stu \\zipFile.zip 注意：加入 zipFile 我们传入的字符串值是 ：
	 *            "E:\\stu \\" 或者 "E:\\stu " 如果 E 盘已经存在 stu 这个文件夹的话，那么就会出现
	 *            java.io.FileNotFoundException: E:\stu ( 拒绝访问。 )
	 *            这个异常，所以要注意正确传参调用本函数哦
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
	 * 解压 zip 文件，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现 java.io.IOException:
	 * Negative
	 * 
	 * seek offset 异常 create date:2009- 6- 9 author:Administrator
	 * 
	 * 
	 * 
	 * @param zipfile
	 * 
	 *            zip 文件，注意要是正宗的 zip 文件哦，不能是把 rar 的直接改为 zip 这样会出现
	 *            java.io.IOException:
	 * 
	 *            Negative seek offset 异常
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
		String str; // 输出信息字符串
		/**
		 * 和服务器建立连接
		 * ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
		 */
		FtpClient ftp = null;
		
		StringBuilder buf = new StringBuilder();
		try {

			ftp = new FtpClient(host); // 根据服务器ip建立连接

			str = ftp.getResponseString(); // 获得响应信息
			System.out.println("连接服务器:" + str);
			/**
			 * 登陆到Ftp服务器
			 */
			ftp.login("user", "user"); // 根据用户名和密码登录服务器
			str = ftp.getResponseString();
			System.out.println("登录:" + str);
			/**
			 * 打开并定位到服务器目录
			 */
			ftp.cd("upload"); // 打开服务器上的文件目录
			str = ftp.getResponseString();
			System.out.println("打开服务器目录:" + str);
			ftp.binary(); // 转化为二进制的文件
			TelnetInputStream ftpIn = ftp.get(filename); // 找到要读取的文件

			ZipInputStream zis = new ZipInputStream(ftpIn);

			ZipEntry entry;
			InputStreamReader ir = new InputStreamReader(zis);
			BufferedReader in = new BufferedReader(ir);
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				// 不是上报的xml压缩文件， 不进行解压。
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
			System.out.println("解压缩出现问题！！！" + zipfile);
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
	// com.viewscenes.util.LogTool.debug("logMsg","解压缩文件"+zipfile+"出现异常！");
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
	 *  fileUrl:要下载的文件ftp路径
	 *  user :ftp登录名
	 *  psw : ftp密码
	 *  
	 */
	public static FtpClient loginFtp(String fileUrl, String user,
			String psw) {
		FtpClient ftp = null;
		TelnetInputStream ftpIn = null;
		ZipEntry zipEntry;
		ZipInputStream zipIn = null;
		String video_location = "d:\\video_location\\";

		int is_null = 1; // 读取标志。读取FTP文件，如果读取不成功则循环读取
		int run_count = 1; // 循环读取次数。超过一定限度不再循环
		while (is_null == 1 && run_count < 30) {
			// 1. 读取FTP文件
			InputStream in = null; // 读取FTP文件的流
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
				String str; // 输出信息字符串

				ftp = new FtpClient(host); // 根据服务器ip建立连接

				str = ftp.getResponseString(); // 获得响应信息
				System.out.println("连接服务器:" + str);
				/**
				 * 登陆到Ftp服务器
				 */
				ftp.login("user", "user"); // 根据用户名和密码登录服务器
				str = ftp.getResponseString();
				System.out.println("登录:" + str);
				/**
				 * 打开并定位到服务器目录
				 */
				ftp.cd("upload"); // 打开服务器上的文件目录
				str = ftp.getResponseString();
				System.out.println("打开服务器目录:" + str);
				ftp.binary(); // 转化为二进制的文件
				ftpIn = ftp.get(filename); // 找到要读取的文件

				zipIn = new ZipInputStream(ftpIn);

			} catch (IOException ex2) { // 读取出错，说明文件尚未上载，重复读取
				ex2.printStackTrace();
				run_count++; // 循环次数加1
				try {
					Thread.currentThread().sleep(1000); // 延时1秒
				} catch (Exception ex1) {
				}
				if (zipIn != null) { // 关闭读取流
					try {
						zipIn.close();
					} catch (IOException e) {
					}
				}
				continue; // 跳过后续处理，再次读取
			}
		}
		return null;
	}
	
	
	/**
	 * 对上传到ftp上的文件进行解压缩
	 * <p>class/function:com.viewscenes.device.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-9-21
	 * @param:
	 * @return:
	 */
	public static  void unZip2Local(String fileUrl) {
		System.out.println("正在解压上传文件："+fileUrl);
		if (fileUrl.endsWith(".zip")) {
			FtpClient ftp = null;
			TelnetInputStream ftpIn = null;
			ZipEntry zipEntry;
			ZipInputStream zipIn = null;
			String video_location = SystemConfig.getVideoPath();

			int is_null = 1; // 读取标志。读取FTP文件，如果读取不成功则循环读取
			int run_count = 1; // 循环读取次数。超过一定限度不再循环
			while (is_null == 1 && run_count < 30) {
				// 1. 读取FTP文件
				InputStream in = null; // 读取FTP文件的流
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
					String str; // 输出信息字符串
					/**
					 * 和服务器建立连接 ftp://10.10.6.12:21//upload//
					 * R36D01_AutoReportFile_691359661.zip
					 */

					ftp = new FtpClient(host); // 根据服务器ip建立连接

					str = ftp.getResponseString(); // 获得响应信息
					System.out.println("连接服务器:" + str);
					/**
					 * 登陆到Ftp服务器
					 */
					ftp.login("user", "user"); // 根据用户名和密码登录服务器
					str = ftp.getResponseString();
					System.out.println("登录:" + str);
					/**
					 * 打开并定位到服务器目录
					 */
					ftp.cd("upload"); // 打开服务器上的文件目录
					str = ftp.getResponseString();
					System.out.println("打开服务器目录:" + str);
					ftp.binary(); // 转化为二进制的文件
					ftpIn = ftp.get(filename); // 找到要读取的文件

					zipIn = new ZipInputStream(ftpIn);

				} catch (IOException ex2) { // 读取出错，说明文件尚未上载，重复读取
					ex2.printStackTrace();
					run_count++; // 循环次数加1
					try {
						Thread.currentThread().sleep(1000); // 延时1秒
					} catch (Exception ex1) {
					}
					if (zipIn != null) { // 关闭读取流
						try {
							zipIn.close();
						} catch (IOException e) {
						}
					}
					continue; // 跳过后续处理，再次读取
				}

				// 2. FTP文件已经上载(但不一定全部上载完毕)，开始解压缩
				try {
					zipEntry = zipIn.getNextEntry(); // zip数据流单位为zipEntry
					if (zipEntry == null) {
						throw new IOException("找不到远程回收文件！");
					}
				} catch (IOException ex5) { // 说明FTP文件尚未完全上载成功，重复读取
					run_count = run_count++; // 循环次数加1
					try {
						Thread.currentThread().sleep(2000); // 延时1秒
					} catch (Exception ex1) {
					}

					if (zipIn != null) { // 关闭读取流
						try {
							zipIn.close();
						} catch (IOException e) {
						}
					}
					continue; // 跳过后续处理，再次读取
				}

				// 3.3 保存到指定的本地文件夹
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

					while ((readLen = zipIn.read(buf, 0, 1024)) != -1) { // 把输出的zip数据流以1024字节大小来判断进行解压，存放的本地的文件
						os.write(buf, 0, readLen);
					}
					System.out.println("解压缩文件："+zipName+"完成");
					// new File(url).delete();
				} catch (IOException ex6) {
					continue;
					// throw new ModuleException("转存文件出错");
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
	 * 删除ftp上的文件
	 * fileUrl格式：ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
	 * fileUrl格式：http://10.15.6.12:8000/video/OAF07A_42640_20120920143532_20120920143833_852_R1.mp3
	 * <p>class/function:com.viewscenes.device.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-9-21
	 * @param:
	 * @return:
	 */
	public static  boolean delFileFromFtp(String fileUrl) {
//		ftp://10.10.6.12:21//upload//R36D01_AutoReportFile_691359661.zip
		System.out.println("准备删除："+fileUrl);
		FtpClient ftp = null;
		String video_url = SystemConfig.getVideoUrl();

		if (fileUrl.indexOf(video_url) != -1)
			fileUrl = fileUrl.replace(video_url, SystemConfig.getFtpUrl());
		
		// 1. 读取FTP文件
		try {
			String[] ss = fileUrl.split("/");
			String filename = "";
			String host = "";
			String dir = "";	//去掉url中的ip、端口及文件名部分，中间的相对目录路径
			for (int ii = 0; ii < ss.length; ii++) {
				if (ss[ii].split("\\.").length == 4) {

					host = ss[ii].split(":")[0];

				}
				if (ss[ii].indexOf(".mp3") != -1) {
					filename = ss[ii];
				}
			}
			
			//计算从协议ftp后面第一个“/”的开始位置
			int start =fileUrl.indexOf("/",6);
			//计算文件名前面的第一个"/"的位置
			int end = fileUrl.lastIndexOf("/");
			
			dir = fileUrl.substring(start,end);
			
			String str; // 输出信息字符串
			/**
			 * 和服务器建立连接 
			 */

			ftp = new FtpClient(host); // 根据服务器ip建立连接

			str = ftp.getResponseString(); // 获得响应信息
			System.out.println("连接服务器:" + str);
			/**
			 * 登陆到Ftp服务器
			 */
			ftp.login("user", "user"); // 根据用户名和密码登录服务器
			str = ftp.getResponseString();
			System.out.println("登录:" + str);
			/**
			 * 打开并定位到服务器目录
			 */
			ftp.cd(dir); // 打开服务器上的文件目录
			str = ftp.getResponseString();
			System.out.println("打开服务器目录:" + str);
			ftp.binary(); // 转化为二进制的文件
			
			
			ftp.sendServer(" DELE "+filename +"\r\n");
			
			str = ftp.getResponseString();
			
			System.out.println("删除录音文件:"+filename+" "+str);

		} catch (IOException ex2) { // 读取出错，说明文件未删除成功
			ex2.printStackTrace();
			//run_count++; // 循环次数加1
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
