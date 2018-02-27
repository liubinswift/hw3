package com.viewscenes.bean;

import com.viewscenes.bean.runplan.GJTRunplanBean;

import flex.messaging.io.amf.ASObject;

/**
 * ∑÷“≥≤È—Ø
 * @author leeo
 *
 */
public class BaseBean { 
	
	private int startRow=0;
	private int endRow=0;
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	public static void main(String[] args) {
		GJTRunplanBean gjbean = new GJTRunplanBean();
		BaseBean basebean = new BaseBean();
		ASObject asobj = new ASObject();
		Object obj1 = gjbean;
		Object obj2 = basebean;
		Object obj3 = asobj;
		System.out.println(obj1 instanceof ASObject);
		System.out.println(obj2 instanceof ASObject);
		System.out.println(obj3 instanceof ASObject);
		System.out.println("============================");
		System.out.println(obj1 instanceof GJTRunplanBean);
		System.out.println(obj2 instanceof GJTRunplanBean);
		System.out.println(obj3 instanceof GJTRunplanBean);
		System.out.println("============================");
		System.out.println(obj1 instanceof BaseBean);
		System.out.println(obj2 instanceof BaseBean);
		System.out.println(obj3 instanceof BaseBean);
	}
}
