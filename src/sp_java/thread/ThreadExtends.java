package sp_java.thread;

public class ThreadExtends extends Thread{
	int num;
	
	@Override
	public void run() {
		System.out.println("Thread is running");
	}
}
