package reflection;

public class FieldsV4 {

	public static void main(String[] args) throws IllegalAccessException {
		User user = new User(null, null, "id1");
		Team team1 = new Team(null, "team1");
		System.out.println("===before");
		System.out.println("user = " + user);
		System.out.println("team1 = " + team1);

		FieldUtil.nullFieldToDefault(user);
		FieldUtil.nullFieldToDefault(team1);

		System.out.println("===== after =====");
		System.out.println("user = " + user);
		System.out.println("team = " + team1);

	}

}
