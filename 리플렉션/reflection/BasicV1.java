package reflection;

import reflection.data.BasicData;

public class BasicV1 {

	public static void main(String[] args) throws ClassNotFoundException {
		Class<BasicData> basicDataClass = BasicData.class;
		System.out.println("basicDataClass = " + basicDataClass);

		BasicData basicInstance = new BasicData();
		Class<? extends BasicData> basicDataClass2 = basicInstance.getClass();
		System.out.println("basicDataClass2 = " + basicDataClass2);

		String className = "reflection.data.BasicData";
		Class<?> basicDataClass3 = Class.forName(className);
		System.out.println("basicDataClass3 = " + basicDataClass3);
	}
}
