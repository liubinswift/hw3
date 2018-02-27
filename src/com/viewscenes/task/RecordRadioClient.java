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
 * ����SOCKET����¼������
 * @author thinkpad
 *
 */
public class RecordRadioClient {

	/* ��������С */
	private static int BLOCK = 8192;
	/* �������ݻ����� */
	private  ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* �������ݻ����� */
	private  ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	/* �������˵�ַ */
	private  InetSocketAddress SERVER_ADDRESS = null;
	
	/* �Ƿ�ֹͣ¼��,Ĭ�Ͽ�ʼ */
	private boolean stop = false;
	
	/* �״ζ���������ʱ��ȥ��ǰ11���ַ� */
	private static final int startPos = 10;
	
	/* ��ȡ����IP��ַ */
	private  String ip = null;
	
	/* ��ȡ���Ķ˿ڵ�ַ  */
	private  int port = 21;
	
	/* ʵʱ�����������  */
	private RealtimeUrlCmdBean urlCmdBean = null;
	
	/* �ļ��� ���ļ���ֻ�������û��ֶ�¼������45��¼����Ч */
	private String normalFileName = null;
	
	private String userRecDirs;
	/**
	 * @param ip ¼��IP
	 * @param port ¼���˿�
	 */
	public RecordRadioClient(String fileName,RealtimeUrlCmdBean urlCmdBean,String ip,int port){
		this.ip = ip;
		this.port = port;
		this.urlCmdBean = urlCmdBean;
		this.normalFileName = fileName;
		SERVER_ADDRESS = new InetSocketAddress(ip, port);
		
		
		//�����û�¼���ļ�λ�õ�·��
		userRecDirs = "USER_REC\\"+StringTool.generateRecordDateTimeByDate(null).substring(0,8);
//		File dirs = new File(SystemConfig.getVideoPath() + userRecDirs);
//		if (!dirs.exists())
//			dirs.mkdirs();
	}
	
	
	/**
	 * ���ز���ʱ¼������
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 * @throws UtilException 
	 */
	public void startNormalRecord() throws IOException, UtilException{
		// TODO Auto-generated method stub
		// ��socketͨ��
		SocketChannel socketChannel = SocketChannel.open();
		// ����Ϊ��������ʽ
		socketChannel.configureBlocking(false);
		// ��ѡ����
		Selector selector = Selector.open();
		// ע�����ӷ����socket����
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// ����
		socketChannel.connect(SERVER_ADDRESS);
		// ���仺������С�ڴ�

		
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		int count = 0;

		//��һ�����ӳɹ���ȡ��������Ҫ��ǰ11���ַ�ȥ��,���Ǳ�־λ
		boolean header = true;
		 
		System.out.println("[�ֶ�¼��]�ͻ�������׼�����շ�������["+ip+":"+port+"]����Ƶ����...");
		while (true) {
			
			//�ⲿǿ��ֹͣ¼������
			if (stop == true){
				socketChannel.close();
				selector.close();
				client.close();
				break;
			}
			
			// ѡ��һ���������Ӧ��ͨ����Ϊ I/O ����׼��������
			// �˷���ִ�д�������ģʽ��ѡ�������
			selector.select();
			// ���ش�ѡ��������ѡ�������
			selectionKeys = selector.selectedKeys();
			// System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect "+ip+":"+port);
					client = (SocketChannel) selectionKey.channel();
					// �жϴ�ͨ�����Ƿ����ڽ������Ӳ�����
					// ����׽���ͨ�������ӹ��̡�
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("�������!");
						sendbuffer.clear();
						sendbuffer.put("Hello,Server".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					
					client = (SocketChannel) selectionKey.channel();

					// ��ȡ�����������������ݵ���������
					count = client.read(receivebuffer);
					
//					if (count <= 11){
//						String receiveText = new String(receivebuffer.array(), 0,count);
//						if (receiveText.equalsIgnoreCase("I Love Dota"))
//							throw new IOException("��վ��û�пɶ�ȡ����Ƶ��");
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

						// ������������Ա��´ζ�ȡ
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
	 * 45��¼������
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 * @throws IOException 
	 * @throws UtilException 
	 */
	public void start45SecRecord() throws IOException, UtilException {
		// TODO Auto-generated method stub
		// ��socketͨ��
		SocketChannel socketChannel;
		Selector selector = null;
		socketChannel = SocketChannel.open();
		// ����Ϊ��������ʽ
		socketChannel.configureBlocking(false);
		// ��ѡ����
		selector = Selector.open();
		// ע�����ӷ����socket����
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		// ����
		socketChannel.connect(SERVER_ADDRESS);
		// ���仺������С�ڴ�

		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		int count = 0;

		boolean header = true;
		 
		System.out.println("[45��¼��]�ͻ�����׼�����շ�������["+ip+":"+port+"]����Ƶ����...");
		
		//������ʱ��
		startTimer();
		
		while (true) {
			
			//�ⲿǿ��ֹͣ¼������
			if (stop == true){
				socketChannel.close();
				selector.close();
				client.close();
				break;
			}
			
			// ѡ��һ���������Ӧ��ͨ����Ϊ I/O ����׼��������
			// �˷���ִ�д�������ģʽ��ѡ�������
			selector.select();
			// ���ش�ѡ��������ѡ�������
			selectionKeys = selector.selectedKeys();
			// System.out.println(selectionKeys.size());
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect "+ip+":"+port);
					client = (SocketChannel) selectionKey.channel();
					// �жϴ�ͨ�����Ƿ����ڽ������Ӳ�����
					// ����׽���ͨ�������ӹ��̡�
					
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("�������!");
						sendbuffer.clear();
						sendbuffer.put("Hello,Server".getBytes());
						sendbuffer.flip();
						client.write(sendbuffer);
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					
					client = (SocketChannel) selectionKey.channel();
//					
					
					// ��ȡ�����������������ݵ���������
					count = client.read(receivebuffer);
					
//					if (count <= 11){
//						String receiveText = new String(receivebuffer.array(), 0,count);
//						if (receiveText.equalsIgnoreCase("I Love Dota"))
//							throw new IOException("��վ��û�пɶ�ȡ����Ƶ��");
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

						// ������������Ա��´ζ�ȡ
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
	//��
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
	 * �Ƿ�ֹͣ¼��
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
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
	 * �Ƿ�ֹͣ�û��ֶ�¼���Ŀ���
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 */
	public String stopNormalRecord() {
		timer.cancel();
		this.stop = true;
		return normalRecordFinish();
	}
	
	
	/**
	 * �Ƿ�ֹͣ45��¼���Ŀ���
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-26
	 * @param:
	 * @return:
	 */
	public void stop45SecRecord(){
		timer.cancel();
		this.stop = true;
	}
	
	/**
	 * �ֶ�¼����ɵ���β������1�޸��ļ�����,2¼���ļ���Ϣ���
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-7-27
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
			shrBean.setReport_type("2");	//ʵʱ¼��
			//upload���·��û��ʵ�����壬��Ҫ��Ϊ�˱�֤�ø�ʽ�������ϱ���URL��ʽһ�£�����������
			String url = SystemConfig.getLocVideoUrl() + userRecDirs + File.separator + newFileName;
			url = url.replaceAll("\\\\", "/");
			
			shrBean.setFileUrl(url);
			
			ArrayList list = new ArrayList();
			list.add(shrBean);
			try {
				RadioStreamHistoryQuery.data2Db(list);
			} catch (UpMess2DBException e) {
				return  "ʧ��:¼����Ŀ������,�ļ���:"+newFileName;
			}
			
			return "�ɹ�:¼����Ŀ������\n¼���ļ���ַ:"+url;
		}
		
		return "ʧ��:¼��ʧ��,¼���ļ�:"+file.getName()+"������";
	}
	
	/**
	 * 45��¼������URL�����ļ���,��ҪĿ���Ǳ�֤�����ļ��ͻ�ȡ�ļ�ʱ������
	 * <p>class/function:com.viewscenes.task
	 * <p>explain:
	 * <p>author-date:̷��ΰ 2012-8-1
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
