import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompletableFutureTest {

	@DisplayName("completableFuture 테스트")
	@Test
	void test1() throws Exception {
		Integer result = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("1단계");
			return 1;
		}).thenApplyAsync(result1 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("2step");
			return result1 + 1;
		}).thenApplyAsync(result2 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("3step");
			return result2 + 1;
		}).thenApplyAsync(result3 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("4step");
			return result3 + 1;
		}).thenApplyAsync(result4 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("5step");
			return result4 + 1;
		}).get();

		assertThat(result).isEqualTo(5);
		/**
		 * ForkJoinPool.commonPool-worker-1
		 * 1단계
		 * ForkJoinPool.commonPool-worker-1
		 * 2step
		 * ForkJoinPool.commonPool-worker-2 다른 스레드
		 * 3step
		 * ForkJoinPool.commonPool-worker-1
		 * 4step
		 * ForkJoinPool.commonPool-worker-3 다른 스레
		 * 5step
		 */
	}

	@DisplayName("completableFuture 동기 테스트")
	@Test
	void test2() throws Exception {
		Integer result = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("1단계");
			return 1;
		}).thenApply(result1 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("2step");
			return result1 + 1;
		}).thenApply(result2 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("3step");
			return result2 + 1;
		}).thenApply(result3 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("4step");
			return result3 + 1;
		}).thenApply(result4 -> {
			System.out.println(Thread.currentThread().getName());
			System.out.println("5step");
			return result4 + 1;
		}).get();

		assertThat(result).isEqualTo(5);
		/*
			ForkJoinPool.commonPool-worker-1
			1단계
			main
			2step
			main
			3step
			main
			4step
			main
			5step
		* */
	}

	@DisplayName("supplyAsync test")
	@Test
	void test3() throws Exception {
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
			// 여기 안은 같은 스레드가 순차적으로 작동, 여기 안도 비동기로 하려면 CompletableFuture를 써야함.
			RanNum ranNum = new RanNum();
			RanNum2 ranNum2 = new RanNum2();
			System.out.println(Thread.currentThread().getName());
			int result1 = ranNum.get();
			int result2 = ranNum2.get();
			int result3 = ranNum.get();
			int result4 = ranNum2.get();
			int result5 = ranNum.get();
			int result6 = ranNum2.get();
			int result7 = ranNum.get();

			return result1 + result2 + result3 + result4 + result5 + result6 + result7;
		});

		Integer actual = cf.get();
		assertThat(actual).isEqualTo(10);
	}

	@DisplayName("thenApply test")
	@Test
	void test4() throws Exception {

		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			return 1;
		}).thenApply(result -> {
			// System.out.println(Thread.currentThread().getName());\
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			RanNum ranNum = new RanNum();
			return result + ranNum.get();
			// return result + 1;
		});

		assertThat(cf.get()).isEqualTo(2);
	}

	static class RanNum {
		int get() {
			System.out.println(Thread.currentThread().getName());
			return 1;
		}
	}

	static class RanNum2 {
		int get()  {
			System.out.println(Thread.currentThread().getName());
			return 2;
		}
	}
}




