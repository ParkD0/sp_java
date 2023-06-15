package sp_java.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
	public static ReentrantLock lock  = new ReentrantLock();
	
	public static ConcurrentHashMap<Integer, String> mapReport = new ConcurrentHashMap<Integer, String>(); 
	public static ConcurrentHashMap<Integer, Thread> mapThread = new ConcurrentHashMap<Integer, Thread>();
	
	private static int num = 0;
	public static synchronized int getNum() {
		return num;
	}
	public static synchronized void setNum(int num) {
		Lock.num = num;
	}
	
	public static synchronized void lockFunc() {
		return;
	}
}

//usage
/*
public void fun() {
	lock.lock();
	code block~~~~
	lock.unlock();
}

concurrentHashMap, synchronized
Even if just use it, can only access it from one thread
 */