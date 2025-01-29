package thread;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadPoolTest {

	private final int threadNum;
	private final Queue<Runnable> taskQueue;
	private final Thread[] threads;
	private volatile boolean isShutdown;

	public ThreadPoolTest(int threadNum) {
		this.threadNum = threadNum;
		this.taskQueue = new LinkedList<>();
		this.threads = new Thread[threadNum];
		this.isShutdown = false;

		for (int i = 0; i < threadNum; i++) {
			threads[i] = new WorkerThread();
			threads[i].start();
		}
	}

	public void submit(Runnable task) {
		if(!isShutdown) {
			synchronized (taskQueue) {
				taskQueue.offer(task); // 작업 큐에 작업 추가
				taskQueue.notifyAll(); // 대기 중인 스레드에게 작업이 추가되었음을 알림
			}
		}
	}

	public void shutdown() {
		isShutdown = true;
		synchronized (taskQueue) {
			taskQueue.notifyAll();
		}

		for (Thread thread : threads) {
			try{
				thread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class WorkerThread extends Thread {

		@Override
		public void run() {
			while (!isShutdown) {
				Runnable task;
				synchronized (taskQueue) {
					while (taskQueue.isEmpty() && !isShutdown) {
						try {
							taskQueue.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					if(!taskQueue.isEmpty()) {
						task = taskQueue.poll();
					}else{
						continue; //
					}
				}
				try {
					task.run(); //작업실행
				}catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}



