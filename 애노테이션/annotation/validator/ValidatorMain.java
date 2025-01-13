package annotation.validator;

public class ValidatorMain {

	public static void main(String[] args) throws IllegalAccessException {
		User user1 = new User(null);

		Validator.validate(user1);
	}
}
