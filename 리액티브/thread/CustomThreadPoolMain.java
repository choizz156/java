package thread;


public class CustomThreadPoolMain {

	public static void main(String[] args) {
		CustomThreadPool customThreadPool = new CustomThreadPool(3);
		for (int i = 0; i < 10; i++) {
			int id = i;
			customThreadPool.submit(() -> {
				System.out.println("id = " + id +" ing...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("id = " + id +" end...");
			});
		}

		customThreadPool.terminate();
	}
}