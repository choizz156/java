public class ThreadLifeCycle {

	public static void main(String[] args) throws InterruptedException {
		//new
		Thread thread = new Thread(() -> {
		});
		System.out.println(thread.getState());

		Thread runnableThread = new Thread(() -> {
			while (true) {

			}
		});
		runnableThread.start();

		Thread timedWaitingThread = new Thread(() -> {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		timedWaitingThread.start();

		Object lock = new Object();
		Thread waitingThread = new Thread(() -> {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		waitingThread.start();
		Thread.sleep(100);


		Thread runningThread = new Thread(() -> {
			synchronized (lock) {
				while (true) {
					// 무한 루프로 lock 을 계속 점유
				}
			}
		});
		runningThread.start();
		Thread.sleep(100);

		Thread blockedThread = new Thread(() -> {
			synchronized (lock) {
			}
		});
		blockedThread.start();
		Thread.sleep(100);

		thread.start();
		thread.join();

		System.out.println(thread.getState());
		System.out.println(timedWaitingThread.getState());
		System.out.println(waitingThread.getState());
		System.out.println(blockedThread.getState());
		System.out.println(thread.getState());
	}
}
