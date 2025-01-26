package thread;

public class Join {

	public static void main(String[] args) {
		Thread thread = new Thread(() -> {
			try{
				System.out.println(Thread.currentThread().getName());
				System.out.println("join thread");
			}catch (Exception e){
				e.printStackTrace();
			}
		});
		thread.start();

		try {
			thread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Join.main");
	}
}
