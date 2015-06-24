public class Seat {

	public boolean isGhostSeat; //on 2d grid space with no seat is ghost
	public boolean isLeftHanded; //seat has a left handed desk
	public boolean isEmpty; //Currently no student in seat
	public Student student; //Student occupying seat
	public String seatPos; //[Letter][number] --> format for seat positions

	public Seat(String seatPos, boolean isGhostSeat, boolean isLeftHanded) {
		this.seatPos = seatPos;
		this.isGhostSeat = isGhostSeat;
		this.isLeftHanded = isLeftHanded;
		isEmpty = false;
		student = null;
	}

	public void assignStudent(Student s) {
		student = new Student(s);
	}

	public String toString() {
		return seatPos + ((seatPos.length() == 2) ? " " : "");
	}

	public void setGhost(boolean b) {
		if(b) {
			this.seatPos = "Ghost";
		}
		isGhostSeat = b;
		this.student = null;
	}

	public String debugOutput() {
		return "Seat: " + seatPos + " Ghost: " + isGhostSeat + " Left: " + isLeftHanded;
	}
}
