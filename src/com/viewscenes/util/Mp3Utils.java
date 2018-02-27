package com.viewscenes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

/**
 * mp3文件处理工具包
 * @author thinkpad
 *
 */
public class Mp3Utils {

	/**
	 * 获取mp3文件的播音持续时间,单位：秒
	 * <p>class/function:com.viewscenes.util
	 * <p>explain:
	 * <p>author-date:谭长伟 2012-9-15
	 * @param:
	 * @return:
	 */
	public static int getMp3Duration(File mp3File){
		int sec  =0;
		if (mp3File == null)
			return sec;
		
		String name = mp3File.getName();
		if (!name.endsWith(".mp3"))
			return sec;
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(mp3File);
			int b=fis.available();
			Bitstream bt=new Bitstream(fis);
			Header h=bt.readFrame();
			int time=(int) h.total_ms(b);
//			sec = time/1000;
			//可能是由于系统产生的mp3文件有压缩，所以46秒的录音在解析的时候只能解析成23秒，暂时没有什么好的解决办法
			//502 是根据time和查询录音时间计算得出，如果是一个正常的mp3音乐time/1000反回的时间是正确的
			sec = time/502;
			
			return sec;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BitstreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sec;
		
	}
}
