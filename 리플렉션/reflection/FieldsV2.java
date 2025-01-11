package reflection;

import java.lang.reflect.Field;

public class FieldsV2 {
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		User user = new User("id1", "userA", 20);
		System.out.println("user = " + user);

		Class<? extends User> aClass = user.getClass();
		Field nameField = aClass.getDeclaredField("name");
		nameField.setAccessible(true);
		nameField.set(user, "userB");
		System.out.println("name = " + user.getName());
	}
}
