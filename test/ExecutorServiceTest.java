import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExecutorServiceTest {

	private ExecutorService executorService;

	@BeforeEach
	void setUpEach() {
		executorService = Executors.newFixedThreadPool(1);
	}

	@AfterEach
	void tearDown() {
		executorService.shutdown();
	}

	@DisplayName("execute runnable test")
	@Test
	void test1() {

		Runnable runnable = () -> {
			System.out.println(Thread.currentThread().getName());
		};

		executorService.execute(runnable);

	}

	@DisplayName("executor submit test Callable")
	@Test
	void test2() throws Exception {
		//given
		Callable<String> callable = () -> {
			System.out.println(Thread.currentThread().getName());
			return "Hello World";
		};
		//when
		Future<String> future = executorService.submit(callable);
		String result = future.get();

		//then
		assertThat(result).isEqualTo("Hello World");
	}

	interface Callback<T> {
		void onCompleted(T result);
	}

	class MyCallback implements Callback<String> {

		@Override
		public void onCompleted(String result) {
			System.out.println(result);
		}
	}

	@DisplayName("Callback Test non-block")
	@Test
	void test3() throws Exception {
		executorService.execute(() -> {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			String result = "Hello World";
			Callback<String> myCallback = new MyCallback();
			myCallback.onCompleted(result);
		});

		System.out.println(Thread.currentThread().getName());
		Thread.sleep(1000);
	}

	@DisplayName("future + callable")
	@Test
	void test4() throws Exception {

		Callable<String> callable = () -> {
			System.out.println(Thread.currentThread().getName());
			return "Hello World";
		};

		Future<String> future = executorService.submit(callable);

		new Thread(() -> {
			String result;
			try {
				result = future.get();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + " " + result);
			new MyCallback().onCompleted(result);
		}).start();

	}
}
