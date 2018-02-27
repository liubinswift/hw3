package com.viewscenes.util;

import java.io.*;

import org.jdom.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.zip.*;

/**
 * 
 * <p>
 * 名称: 文件操作工具
 * </p>
 * <p>
 * 目的: 提供一些常用的文件读些功能
 * </p>
 * <p>
 * 已知问题: 出于性能的考虑，本工具的文件读写功能没有使用同步机制 在多线程的应用中调用此工具写入同一文件时，必须使用同步机制，否则有可能发生冲突
 * </p>
 * <p>
 * 修改历史: 2005-9 陈刚 创建
 * </p>
 * 备注:
 * 
 */
public class FileTool {
	final static String DEFAULT_CHAR_CODE = "GB2312";

	public FileTool() {
	}

	/**
	 * 写入字符串到文件，使用追加写的方式，即若文件已经存在，则将数据添加到文件末尾。 若指定的文件及目录不存在，本函数会自动创建相关文件和目录
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param fileString
	 *            要写入文件的内容
	 * @throws UtilException
	 *             若写入错误抛出此异常
	 */
	public static void writeFile(String fileName, String fileString)
			throws UtilException {
		try {
			byte[] buffer = fileString.getBytes(DEFAULT_CHAR_CODE);
			writeFile(fileName, buffer, 0, buffer.length, false);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}

	public static Object file2Object(String fileName) {
		FileInputStream in = null;
		ObjectInputStream objIn = null;
		Object obj = null;
		try {
			in = new FileInputStream(fileName);
			objIn = new ObjectInputStream(in);
			obj = objIn.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public static void writeFile(String fileName, Object obj)
			throws UtilException {
		FileOutputStream out = null;
		// ZipOutputStream zipOut = null;
		ObjectOutputStream objOut = null;

		try {
			out = new FileOutputStream(fileName);
			// zipOut = new ZipOutputStream(out);
			objOut = new ObjectOutputStream(out);
			objOut.writeObject(obj);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				objOut.close();
			} catch (IOException ex1) {
			}
			/*
			 * try { zipOut.close(); } catch (IOException ex1) { }
			 */try {
				out.close();
			} catch (IOException ex1) {
			}
		}
	}

	/**
	 * 写入二进制数组到文件中，使用追加写的方式，即若文件已经存在，则将数据添加到文件末尾 若指定的文件及目录不存在，本函数会自动创建相关文件和目录
	 * 
	 * @param fileName
	 *            文件全路径名
	 * @param buffer
	 *            二进制数组
	 * @throws UtilException
	 *             若写入错误抛出此异常
	 */
	public static void writeFile(String fileName, byte[] buffer)
			throws UtilException {
		writeFile(fileName, buffer, 0, buffer.length, true);
	}

	/**
	 * 将字符串更新到文件，若文件已经存在则使用新数据覆盖原有数据。
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param fileString
	 *            要写入文件的内容
	 * @throws UtilException
	 *             写入失败抛出此异常
	 */
	public static void updateFile(String fileName, String fileString)
			throws UtilException {

		try {
			// byte[] buffer = fileString.getBytes(DEFAULT_CHAR_CODE);
			byte[] buffer = fileString.getBytes("UTF-8");
			writeLog(fileName, buffer, 0, buffer.length, true);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 用二进制数据更新文件，若文件已经存在则使用新数据覆盖原有数据。
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param buffer
	 *            要写入文件的内容
	 * @throws UtilException
	 *             写入失败抛出此异常
	 */
	public static void updateFile(String fileName, byte[] buffer)
			throws UtilException {
		writeFile(fileName, buffer, 0, buffer.length, true);
	}

	/**
	 * 快速写String到文件中,采取追加写的方式，该方法在记录日志中使用
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param buffer
	 *            要写入文件的内容
	 * @param off
	 *            数组写入的起始位置
	 * @param len
	 *            写入的长度
	 * @param bAppend
	 *            是否追加写
	 * @throws UtilException
	 *             写入文件发生错误抛出异常
	 */
	public static void writeLog(String fileName, byte[] buffer, int off,
			int len, boolean bAppend) throws UtilException {
		FileOutputStream fos = null;
		try {
			File file = new File(fileName);

			// 若文件不存在则需要先创建文件所在目录
			if (!file.exists()) {
				// 用来删除超过90天前的日志。
				Date d = new Date();
				d.setDate(d.getDate() - 90);

				File allFile = file.getParentFile().getParentFile();
				if (allFile.exists()) {
					File[] listFile = allFile.listFiles();
					for (int i = 0; i < listFile.length; i++) {
						File deleFile = listFile[i];
						if (deleFile.lastModified() < d.getTime()) {
							if (deleFile.isDirectory()) {
								try {

									deleteFolder(deleFile);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}
					}
				}

				makeDirectory(file.getParent());
			}
			// 写入文件
			fos = new FileOutputStream(fileName, bAppend);

			fos.write(buffer, off, len);
		} catch (IOException e) {
			throw new UtilException("写文件错误:" + e.getMessage(), e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				throw new UtilException("无法关闭FileOutputStream"
						+ ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 快速以流方式写到文件,采取追加写的方式,对写入的文件大小无限制
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param buffer
	 *            要写入文件的内容
	 * @param off
	 *            数组写入的起始位置
	 * @param len
	 *            写入的长度
	 * @param bAppend
	 *            是否追加写
	 * @throws UtilException
	 *             写入文件发生错误抛出异常
	 */
	public static void writeFile(String fileName, byte[] buffer, int off,
			int len, boolean bAppend) throws UtilException {
		FileOutputStream fos = null;
		try {
			File file = new File(fileName);

			// 若文件不存在则需要先创建文件所在目录
			if (!file.exists()) {
				makeDirectory(file.getParent());
			}
			// 写入文件
			fos = new FileOutputStream(fileName, bAppend);

			fos.write(buffer, off, len);
			
		} catch (IOException e) {
			throw new UtilException("写文件错误:" + e.getMessage(), e);
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException ex) {
				throw new UtilException("无法关闭FileOutputStream"
						+ ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 删除目录方法，递归调用
	 */
	private static void deleteFolder(File dir) {
		File fileList[] = dir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				deleteFolder(fileList[i]);
			} else {
				fileList[i].delete();
			}
		}
		dir.delete();
	}

	/**
	 * 递归创建文件目录
	 * 
	 * @param pathName
	 *            完整路径名称
	 */
	private static void makeDirectory(String pathName) {
		if (pathName == null)
			return;
		File file = new File(pathName);
		if (!file.exists()) {
			if (file.getParent() != null)
				makeDirectory(file.getParent());
			file.mkdirs();
		}
	}

	/**
	 * 快速写String和Exception的StackTrace到文件中,采取追加写的方式
	 * 
	 * @param fileName
	 *            要写入信息的文件名
	 * @param fileString
	 *            要写入文件的内容
	 * @param exp
	 *            要写入文件的异常
	 * @throws UtilException
	 */

	public static void writeStackTrace(String fileName, String fileString,
			Exception exp) throws UtilException {

		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			fileString += "\r\n";
			File file = new File(fileName);
			if (!file.exists()) {
				if (file.getParent() != null)
					new File(file.getParent()).mkdirs();
			}
			fos = new FileOutputStream(fileName, true); // 设置为追加写的方式
			byte[] b = fileString.getBytes("GB2312");
			fos.write(b);
			pw = new PrintWriter(fos);
			exp.printStackTrace(pw);
		} catch (IOException e) {
			throw new UtilException("记录丢掉的消息时写文件错误", e);
		} finally {
			try {
				if (pw != null)
					pw.close();
				fos.close();
			} catch (IOException ex) {
				throw new UtilException("无法关闭FileOutputStream", ex);
			}
		}
	}

	/**
	 * 将文件读取到byte数组中
	 * 
	 * @param fileName
	 *            文件全路经名
	 * @return 存放文件二进制内容的byte数组
	 * @throws UtilException
	 *             读取文件发生错误抛出异常
	 */

	public static byte[] readFile(String fileName) throws UtilException {

		byte buffer[] = null;
		FileInputStream fin = null;

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				throw new UtilException("读取文件失败，文件" + fileName + "不存在");
			}

			long len = file.length();
			buffer = new byte[(int) len];
			fin = new FileInputStream(file);

			int r = fin.read(buffer);
			if (r != len) {
				throw new UtilException("无法读取全部文件, 读取的长度" + r + " != 文件的长度"
						+ len);
			}
		} catch (IOException ex) {
			throw new UtilException("读取文件失败" + ex.getMessage(), ex);
		}

		finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException ex) {
				throw new UtilException("无法关闭FileInputtStream:"
						+ ex.getMessage(), ex);
			}
		}
		return buffer;
	}

	/**
	 * 读取文本文件到字符串，默认字符编码为GB2312
	 * 
	 * @param fileName
	 *            文件全路经名
	 * @return 文本字符串
	 * @throws UtilException
	 *             若读取失败抛出此异常
	 */
	public static String FileToString(String fileName) {

		String str = null;
		try {
			byte buffer[] = readFile(fileName);
			str = new String(buffer, DEFAULT_CHAR_CODE);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	/**
	 * 获得文件最近更改时间
	 * 
	 * @param fileName
	 *            文件路径名
	 * @return 更改时间
	 */
	public static long getFileModifyTime(String fileName) {
		if (fileName == null)
			return 0;
		File file = new File(fileName);
		return file.lastModified();
	}

	/**
	 * 获得文件创建时间 采用window系统命令来完成该功能
	 * 
	 * @param fileName
	 *         文件路径名
	 * @return 更改时间
	 */
	public static Date getFileCreateTime(File file) {
		if (file == null)
			return null;
        try
        {
	        Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir " + file.getAbsolutePath() + " /tc");
	        DataInputStream in = new DataInputStream(ls_proc.getInputStream());
	        for (int i = 0; i < 5; i++ )
	        {
	            in.readLine();
	        }
	        String stuff = in.readLine();
	        StringTokenizer st = new StringTokenizer(stuff);
	        String dateC = st.nextToken();
	        String  time=st.nextToken();
	        in.close();
	        String d = dateC.replaceAll("/", "-") +" "+time+":00";
	        return StringTool.stringToDate(d);
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
		
	}
	
	
	/**
	 * 
	 * load xml file
	 * 
	 * @param fileName
	 * 
	 * @return
	 * 
	 * @throws UtilException
	 */

	public static Element loadXMLFile(String fileName) throws UtilException {

		String str_msg = null;

		str_msg = FileTool.FileToString(fileName);

		if (str_msg == null) {

			throw new UtilException("Load xml file failed! "

			+ "Can't load file content from "

			+ fileName);

		}

		Element root = null;

		// parse xml config file;

		try {

			StringReader read = new StringReader(str_msg);

			org.jdom.input.SAXBuilder builder = new org.jdom.input.SAXBuilder(
					false);

			Document doc = builder.build(read);

			// get root element

			root = doc.getRootElement();

		}

		catch (JDOMException ex) {

			throw new UtilException("Parse xml error:" + ex.getMessage(), ex);

		}

		return root;

	}

	public static void appendFile(String fileName, String appendFileName,
			int off) throws UtilException {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			File file = new File(fileName);

			// 若文件不存在则需要先创建文件所在目录
			if (!file.exists()) {
				makeDirectory(file.getParent());
			}
			// 写入文件
			fos = new FileOutputStream(fileName, true);
			fis = new FileInputStream(appendFileName);
			byte[] buffer = new byte[40960];
			int nRead = fis.read(buffer, 0, 40960);
			while (nRead > -1) {
				fos.write(buffer, off, nRead - off);
				nRead = fis.read(buffer);
				off = 0;
			}
		} catch (IOException e) {
			throw new UtilException("读写文件错误:" + e.getMessage(), e);
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				throw new UtilException("无法关闭文件流" + ex.getMessage(), ex);
			}
		}
	}

}
