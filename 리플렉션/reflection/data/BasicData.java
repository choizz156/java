package reflection.data;

public class BasicData {
	public String publicField;
	private int privateField;

	public BasicData(){
		System.out.println("BasicData.BasicData");
	}

	private BasicData(String data) {
		System.out.println("data = " + data);
	}

	public void call() {
		System.out.println("BasicData.call");
	}

	public String hello(String string) {
		System.out.println("BasicData.hello");
		return string + "hello";
	}

	private  void privateMethod() {
		System.out.println("BasicData.privateMethod");
	}

	protected void protectedMethod() {
		System.out.println("BasicData.protectedMethod");
	}

}

