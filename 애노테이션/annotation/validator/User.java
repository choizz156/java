package annotation.validator;

public class User {

	@NotEmpty
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
