package  org.jmask.web.controller.user;

public class userManagerListener extends Thread {
	private userManager um;
	public userManagerListener(userManager um){
		super();
		this.um=um;
	}
	public void run(){
		System.out.println("异步监听启动");
		while(true){
			try {
				sleep(30*60*1000);//半小时处理一次;
				um.processFlag=true;
				sleep(500);//保证已经在执行的执行完毕;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//处理用户信息
			System.out.println("开始处理用户信息");
			java.util.Date sDate = new java.util.Date();
		    long sTime = sDate.getTime();
			
		    um.processFlag=false;
			synchronized(um){
				System.out.println("用户信息处理完毕");
				um.notifyAll();
			}
		}
	}
}
