public class ThreadTest {

	public static void main(String[] args) {
		//1
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("ThreadTest1.run");

			}
		});

		//2
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				System.out.println("ThreadTest2.run");
			}
		};

		//3
		MyThread myThread = new MyThread();
		myThread.start();

		//4
		MyThread2 myThread2 = new MyThread2();
		Thread thread3 = new Thread(myThread2);
		thread3.start();

		//5
		new Thread(() -> System.out.println("ThreadTest3.run")).start();

	}

}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println("MyThread.run");
	}
}

class MyThread2 implements Runnable {

	@Override
	public void run() {
		System.out.println("MyThread2.run");
	}
}