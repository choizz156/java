package thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(3);
		System.out.println("start");
		new Thread(()->{
			for (int i = 0; i < 3; i++) {
				countDownLatch.countDown();
				System.out.println(Thread.currentThread().getName() +":::"+countDownLatch.getCount());
			}

		}).start();

		countDownLatch.await();

		System.out.println("CountDownLatchTest.main");
	}
}
