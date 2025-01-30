package thread;

public class BlockedThread {
	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();

		Thread thread1 = new Thread(() -> {
			synchronized (lock) {
				while (true) {

				}
			}
		});

		Thread thread2 = new Thread(() -> {
			synchronized (lock) {
				System.out.println("BlockedThread.main");
			}
		});

		thread1.start();
		Thread.sleep(100);;
		thread2.start();
		Thread.sleep(100);

		System.out.println(thread2.getState());
	}
}
