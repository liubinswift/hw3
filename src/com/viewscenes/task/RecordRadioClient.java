package com.viewscenes.task;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


import com.viewscenes.bean.RealtimeUrlCmdBean;
import com.viewscenes.bean.device.FileRetrieveResult;
import com.viewscenes.logic.autoupmess2db.Exception.UpMess2DBException;
import com.viewscenes.logic.autoupmess2db.MessProcess.v8.RadioStreamHistoryQuery;
import com.viewscenes.sys.SystemConfig;
import com.viewscenes.util.FileTool;
import com.viewscenes.util.StringTool;
import com.viewscenes.util.UtilException;
import com.viewscenes.web.common.Common;

import flex.messaging.io.ArrayList;
/**
 * 采用SOCKET进行录音的类
 * @author thinkpad
 *
 */
public class RecordRadioClient {

	/* 缓冲区大小 */
	private static int BLOCK = 8192;
	/* 接受数据缓冲区 */
	private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* 发送数据缓冲区 */
	private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	/* 服务器端地址 */
	private  InetSocketAddress SERVER_ADDRESS = null;
	
	/* 是否停止录音,默认开始 */
	private boolean stop = false;
	
	/* 首次读数据流的时候去掉前11个字符 */
	private static final int startPos = 10;
	
	/* 读取流的IP地址 */
	private  String ip = null;
	
	/* 读取流的端口地址  */
	private  int port = 21;
	
	/* 实时播放请求对象  */
	private RealtimeUrlCmdBean urlCmdBean = null;
	
	/* 文件名 该文件名只适用于用户手动录音，对45秒录音无效 */
	private String normalFileName = null;
	
	private String userRecDirs;
	/**
	 * @param ip 录音IP
	 * @param port 录音端口
	 */
	public RecordRadioClient(String fileName,RealtimeUrlCmdBean urlCmdBean,String ip,int port){
		this.ip = ip;
		this.port = port;
		this.urlCmdBean = urlCmdBean;
		this.normalFileName = fileName;
		SERVER_ADDRESS = new InetSocketAddress(ip, port);
		
		
		//保存用户录音文件位置的路径
		userRecDirs = "USER_REC\\"+StringTool.generateRecordDateTimeByDate(null).substring(0,8);
//		File dirs = new File(SystemConfig.getVideoPath() + userRecDirs);
//		if (!dirs.exists())
//			dirs.mkdirs();
	}
	
	
	/**
	 * 下载不定时录音操作
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 * @throws UtilException 
	 */
	public void startNormalRecord() throws IOException, UtilException{
		// TODO Auto-generated method stub
		// 打开socket通道
		SocketChannel socketChannel = SocketChannel.open();
		// 设置为非阻塞方式
		socketChannel.configureBlocking(false);
		// 打开选择器
		Selector selector = Selector.open();
		// 注册连接服务端socket动作
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// 连接
		socketChannel.connect(SERVER_ADDRESS);
		// 分配缓冲区大小内存

		
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		int count = 0;

		//第一次连接成功，取回数据需要把前11个字符去掉,这是标志位
		boolean header = true;
		 
		System.out.println("[手动录音]客户端正在准备接收服务器端["+ip+":"+port+"]的音频数据...");
		while (true) {
			
			//外部强制停止录音工作
			if (stop == true){
				socketChannel.close();
				selector.close();
				client.close();
				break;
			}
			
			// 选择一组键，其相应的通道已为 I/O 操作准备就绪。
			// 此方法执行处于阻塞模式的选择操作。
			selector.select();
			// 返回此选择器的已选择键集。
			selectionKeys = selector.selectedKeys();
			// System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect "+ip+":"+port);
					client = (SocketChannel) selectionKey.channel();
					// 判断此通道上是否正在进行连接操作。
					// 完成套接字通道的连接过程。
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("完成连接!");
						sendbuffer.clear();
						sendbuffer.put("Hello,Server".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					
					client = (SocketChannel) selectionKey.channel();

					// 读取服务器发送来的数据到缓冲区中
					count = client.read(receivebuffer);
					
//					if (count <= 11){
//						String receiveText = new String(receivebuffer.array(), 0,count);
//						if (receiveText.equalsIgnoreCase("I Love Dota"))
//							throw new IOException("该站点没有可读取的音频流");
//					}
					
					if (count > 0) {
//						receiveText = new String(receivebuffer.array(), 0,
//								count);
						
						
						if (header == true){
							receivebuffer.position(startPos);
							header = false;
						}
						
						receivebuffer.flip();
						
						byte [] content = new byte[receivebuffer.limit()];
						
						receivebuffer.get(content);

						// 将缓冲区清空以备下次读取
						receivebuffer.clear();
							
						String path = SystemConfig.getVideoPath();
						FileTool.writeFile(path + userRecDirs +File.separator +normalFileName, content);
							
						
					}

				}
			}
			selectionKeys.clear();
		}
	}

	
	
	/**
	 * 45秒录音方法
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 * @throws IOException 
	 * @throws UtilException 
	 */
	public void start45SecRecord() throws IOException, UtilException {
		// TODO Auto-generated method stub
		// 打开socket通道
		SocketChannel socketChannel;
		Selector selector = null;
		socketChannel = SocketChannel.open();
		// 设置为非阻塞方式
		socketChannel.configureBlocking(false);
		// 打开选择器
		selector = Selector.open();
		// 注册连接服务端socket动作
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// 连接
		socketChannel.connect(SERVER_ADDRESS);
		// 分配缓冲区大小内存

		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		int count = 0;

		boolean header = true;
		 
		System.out.println("[45秒录音]客户端正准备接收服务器端["+ip+":"+port+"]的音频数据...");
		
		//启动计时器
		startTimer();
		
		while (true) {
			
			//外部强制停止录音工作
			if (stop == true){
				socketChannel.close();
				selector.close();
				client.close();
				break;
			}
			
			// 选择一组键，其相应的通道已为 I/O 操作准备就绪。
			// 此方法执行处于阻塞模式的选择操作。
			selector.select();
			// 返回此选择器的已选择键集。
			selectionKeys = selector.selectedKeys();
			// System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect "+ip+":"+port);
					client = (SocketChannel) selectionKey.channel();
					// 判断此通道上是否正在进行连接操作。
					// 完成套接字通道的连接过程。
					
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("完成连接!");
						sendbuffer.clear();
						sendbuffer.put("Hello,Server".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					
					client = (SocketChannel) selectionKey.channel();
//					
					
					// 读取服务器发送来的数据到缓冲区中
					count = client.read(receivebuffer);
					
//					if (count <= 11){
//						String receiveText = new String(receivebuffer.array(), 0,count);
//						if (receiveText.equalsIgnoreCase("I Love Dota"))
//							throw new IOException("该站点没有可读取的音频流");
//					}
					
					if (count > 0) {
//						receiveText = new String(receivebuffer.array(), 0,
//								count);
						
						
						
						if (header == true){
							receivebuffer.position(startPos);
//							header = false;
						}
						
						receivebuffer.flip();
						
						byte [] content = new byte[receivebuffer.limit()];
						
						receivebuffer.get(content);

						// 将缓冲区清空以备下次读取
						receivebuffer.clear();
							
						String path = SystemConfig.getRecord45SecPath();
						String fileName = get45SecFileName(ip,port);
						
						String tmpFileName = path+"~"+fileName ;
						if (header == true){
//							File tmpFile = new File(path+"~"+fileName);
							File tmpFile = new File(tmpFileName);
							if (tmpFile.exists()){
								tmpFile.delete();
							}
							header = false;
						}
						
//						FileTool.writeFile(path+"~"+fileName, content);
						FileTool.writeFile(tmpFileName, content);
						if (sec > 45){
//							File tmpFile = new File(path+"~"+fileName);
							File tmpFile = new File(tmpFileName);
							if (tmpFile.exists()){
								File newFile = new File(path+fileName);
								if (newFile.exists())
									newFile.delete();
								tmpFile.renameTo(newFile);
								
								if (tmpFile.exists())
									tmpFile.delete();
							}
							sec = 0;
						}
							
						
					}

				}
			}
			selectionKeys.clear();
		}
	}
	
	
	private Timer timer  = new Timer();
	//秒
	private int sec = 0;
	
	private void startTimer(){
		
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				sec++;
			}
			
		}, 1000,1000);
		
	}
	
	
	
	/**
	 * 是否停止录音
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public boolean isStop() {
		return stop;
	}
	
	public void setStop(boolean s) {
		stop = s;
	}

	/**
	 * 是否停止用户手动录音的开关
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public String stopNormalRecord() {
		timer.cancel();
		this.stop = true;
		return normalRecordFinish();
	}
	
	
	/**
	 * 是否停止45秒录音的开关
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-26
	 * @param:
	 * @return:
	 */
	public void stop45SecRecord(){
		timer.cancel();
		this.stop = true;
	}
	
	/**
	 * 手动录音完成的收尾工作：1修改文件名称,2录音文件信息入库
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-7-27
	 * @param:
	 * @return:
	 */
	public String normalRecordFinish(){
		
		File file = new File(SystemConfig.getVideoPath() + userRecDirs+ File.separator + normalFileName);
		String startDateTime = normalFileName.substring(normalFileName.indexOf("_0_")+3, 23);
		String endDateTime = StringTool.generateRecordDateTimeByDate(null);
		String newFileName = "";
//		long fileSize = 0;
		if (file.exists()){
			
			newFileName = normalFileName.replaceAll("@", endDateTime);
			File newFile = new File(SystemConfig.getVideoPath() + userRecDirs + File.separator + newFileName);
			file.renameTo(newFile);
			
//			fileSize = newFile.length()/1024;
			
			String head_id = null;
			
			head_id = Common.getHeadendBeanByCode(urlCmdBean.getCode()).getHead_id();
			FileRetrieveResult shrBean = new FileRetrieveResult();
			
			shrBean.setEquCode(urlCmdBean.getEquCode());
			shrBean.setBand(urlCmdBean.getBand());
			shrBean.setFreq(urlCmdBean.getFreq());
			shrBean.setStartDatetime(StringTool.convertString2DateStr(startDateTime));
			shrBean.setEndDatetime(StringTool.convertString2DateStr(endDateTime));
			shrBean.setHead_id(head_id);
			shrBean.setFileName(newFileName);			
			shrBean.setHeadCode(urlCmdBean.getCode());
			shrBean.setIs_stored("0");
			shrBean.setMark_file_name(newFileName);
			shrBean.setReport_type("2");	//实时录音
			//upload这个路径没有实际意义，主要是为了保证该格式与主动上报的URL格式一致，方便入库解析
			String url = SystemConfig.getLocVideoUrl() + userRecDirs + File.separator + newFileName;
			url = url.replaceAll("\\\\", "/");
			
			shrBean.setFileUrl(url);
			
			ArrayList list = new ArrayList();
			list.add(shrBean);
			try {
				RadioStreamHistoryQuery.data2Db(list);
			} catch (UpMess2DBException e) {
				return  "失败:录音节目入库出错,文件名:"+newFileName;
			}
			
			return "成功:录音节目入库完成\n录音文件地址:"+url;
		}
		
		return "失败:录音失败,录音文件:"+file.getName()+"不存在";
	}
	
	/**
	 * 45秒录音根据URL生成文件名,主要目的是保证生成文件和获取文件时不出错
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-8-1
	 * @param:
	 * @return:
	 */
	public static String get45SecFileName(String ip,int port){
		String fileName = ip + "_" + port+".mp3";
		return fileName;
	}


	public String getUserRecDirs() {
		return userRecDirs;
	}
	
}
