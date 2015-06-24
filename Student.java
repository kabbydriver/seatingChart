public class Student {
	public String firstName;
	public String lastName;
	public String email;
	public Seat seat;
	public int id;
	public boolean isOSD;
	public boolean isLeftHanded;

	public Student(String firstName, String lastName, String email, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.id = id;
	}

	public Student(Student s) {
		firstName = new String(s.firstName);
		lastName = new String(s.lastName);
		email = new String(s.email);
		seat = s.seat;
		id = s.id;

	}

	public String toString() {
		String idString = id + "";

		if(id < 10) idString = "00" + id;
		else if(id < 100) idString = "0" + id;

		return idString + "  _______  " + seat + "  " + lastName + ", " + firstName;
	}

	public String sendMail() {
		String idString = id + "";
		if(id == 0) idString = "   ";
		else if(id < 10) idString = "00" + id;
		else if(id < 100) idString = "0" + id;

		//replace spaces with dashes in name --> extra spaces messus up unix 'read command for email script
		String str = firstName.replace(" ", "-") + " " + lastName.replace(" ", "-") + " " + email + " " + seat.toString() + " " + idString;

		return str;

	}

	public boolean equals(Student s) {
		return this.email.equals(s.email);
	}
}

