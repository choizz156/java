package reflection;

public class User {
	private String name;
	private Integer age;
	private String id;

	public User(String name, Integer age, String id) {
		this.name = name;
		this.age = age;
		this.id = id;
	}

	public User(String id1, String userA, Integer i) {
		this(id1,i,userA);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User{" +
			"name='" + name + '\'' +
			", age=" + age +
			", id='" + id + '\'' +
			'}';
	}
}
