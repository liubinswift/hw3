package com.viewscenes.device.framework;

public class Test2 {

	public static void main(String[] args) throws InterruptedException{
		Test t = new Test();
		
		t.exec();
		synchronized(t){
			System.out.println("wait start");
			t.notify();
			System.out.println("wait end");
		}
		//Test2 t2 = new Test2();
		//t2._wait(t);
	}
	
	public void _wait(Test t) throws InterruptedException{
		
		System.out.println("wait start");
		
		//wait(5*1000);
		
		t.notify();
		
		System.out.println("wait end");
		
		
	}
}
