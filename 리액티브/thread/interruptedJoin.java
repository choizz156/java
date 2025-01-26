package thread;

public class interruptedJoin {

	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();

		Thread thread1 = new Thread(() -> {
			try {
				for (int i = 0; i < 10; i++) {
					System.out.println("진행중");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				mainThread.interrupt();
				System.out.println("interrupted");
			}
		});

		thread1.start();

		Thread interruptedThread = new Thread(() -> {
			try {
				System.out.println("인터럽트 시도1");
				Thread.sleep(2000);
				System.out.println("인터럽트 시도2");
				thread1.interrupt();
				System.out.println("인터럽트 완료");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});


		interruptedThread.start();

		try {
			System.out.println("메인 스레드 작업완료 대기");
			thread1.join(); //메인 스레드를 대기시킴!! interruptedThread 아님!
			System.out.println("메인 스레드 완료");
		} catch (InterruptedException e) {
			System.out.println("main thread interrupted");
			throw new RuntimeException(e);
		}

	}
}
