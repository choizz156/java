package thread;

public class MultiThreadJoin {
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(() -> {
			try {
				System.out.println("스레드 1");
				Thread.sleep(3000);
				System.out.println("스레드 1 완료");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				System.out.println("스레드 2");
				Thread.sleep(2000);
				System.out.println("스레드 2 완료");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		thread1.start();
		thread2.start();

		System.out.println("메인 스레드 대기");

		thread1.join();
		thread2.join();

		System.out.println("메인 스레드 진행");
	}
}
