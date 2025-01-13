package annotation.validator;

import java.lang.reflect.Field;

public class Validator {

	public static void validate(Object object) throws IllegalAccessException {
		Field[] declaredFields = object.getClass().getDeclaredFields();
		for (Field declaredField : declaredFields) {
			declaredField.setAccessible(true);
			if(declaredField.isAnnotationPresent(NotEmpty.class)){
				String value = (String)declaredField.get(object);
				NotEmpty annotation = declaredField.getAnnotation(NotEmpty.class);
				if ( value == null || value.isEmpty()) {
					throw new RuntimeException(annotation.message());
				}
			}
		}
	}
}
