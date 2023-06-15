package sp_java.thread;

class ThreadRunnableSample implements Runnable{
	public void run() {
		System.out.println("threa id running");
	}
}

public class ThreadRunnable{
	public void runThread() {
		ThreadRunnableSample r = new ThreadRunnableSample();
		Thread t1 = new Thread(r);
		t1.start();
		try {
			t1.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}