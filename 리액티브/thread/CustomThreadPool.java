package thread;

import java.util.LinkedList;
import java.util.Queue;

public class CustomThreadPool {

	private final int poolSize;
	private final Queue<Runnable> workQueue;
	private final Thread[] workerThreads;
	private volatile boolean shutdownRequested;

	public CustomThreadPool(int poolSize) {
		this.poolSize = poolSize;
		this.workQueue = new LinkedList<>();
		this.workerThreads = new Thread[poolSize];
		this.shutdownRequested = false;

		for (int i = 0; i < poolSize; i++) {
			workerThreads[i] = new TaskWorker();
			workerThreads[i].start();
		}
	}

	public void execute(Runnable task) {
		if (!shutdownRequested) {
			synchronized (workQueue) {
				workQueue.add(task);
				workQueue.notifyAll();
			}
		}
	}

	public void terminate() {
		shutdownRequested = true;
		synchronized (workQueue) {
			workQueue.notifyAll(); // 대기 중인 모든 작업자 스레드 깨우기
		}

		for (Thread worker : workerThreads) {
			try {
				worker.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class TaskWorker extends Thread {

		@Override
		public void run() {
			while (!shutdownRequested || !workQueue.isEmpty()) {
				Runnable currentTask;
				synchronized (workQueue) {
					while (workQueue.isEmpty() && !shutdownRequested) {
						try {
							workQueue.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					currentTask = workQueue.poll();
				}

				if (currentTask != null) {
					try {
						currentTask.run();
					} catch (RuntimeException e) {
						System.err.println("Task execution failed: " + e.getMessage());
					}
				}
			}
		}
	}
}