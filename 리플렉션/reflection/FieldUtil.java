package reflection;

import java.lang.reflect.Field;

public class FieldUtil {

	public static void nullFieldToDefault(Object target) throws IllegalAccessException {
		Class<?> aClass = target.getClass();
		Field[] declaredFields = aClass.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			declaredField.setAccessible(true);
			if (declaredField.get(target) != null) {
				continue;
			}

			if (declaredField.getType() == String.class) {
				declaredField.set(target, "");
			} else if (declaredField.getType() == Integer.class) {
				declaredField.set(target, 0);
			}
		}
	}
}
