package reflection;

public class FieldsV3 {

	public static void main(String[] args) {
		User user = new User(null, null, "id1");
		Team team1 = new Team(null, "team1");
		System.out.println("===before");
		System.out.println("user = " + user);
		System.out.println("team1 = " + team1);

		if (user.getId() == null) {
			user.setId("");
		}
		if (user.getName() == null) {
			user.setName("");
		}
		if (user.getAge() == null) {
			user.setAge(0);
		}
		if (team1.getId() == null) {
			team1.setId("");
		}
		if (team1.getName() == null) {
			team1.setName("");
		}
		System.out.println("===== after =====");
		System.out.println("user = " + user);
		System.out.println("team = " + team1);

	}

}
