package com.viewscenes.device.framework;

public class Test {

	public synchronized void execute() throws InterruptedException{
		System.out.println("start");
		
		synchronized(this){
			wait(10*1000);
		}
		
		System.out.println("end");
	}
	
//	public static void main(String[] args){
//		Test t = new Test();
//		
//		try {
//			t.execute();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

	public void exec() throws InterruptedException{
		execute();
	}
}
