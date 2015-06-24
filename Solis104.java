public class Solis104 extends Classroom {

	public static void main(String[] args) throws Exception {
		Solis104 room = new Solis104(11, 14);
		room.buildClassroom();
		room.read("15lsectionC00.csv", "exceptions.csv");
		room.assign(6);
		room.printSorted("Solis 104 Sorted Order Section C00 - 15L Final 3pm - June 10th");
		room.printByRow("Solis 104 Row Order Section C00 - 15L Final 3pm - June 10th");
		room.sendEmails("SectionC00Emails");
	}

	public Solis104(int row, int col) {
		super(row, col);
	}

	public void buildClassroom() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				assignCol(j, false, false);
			}
		}

		//The following do not exist
		grid[0][0].setGhost(true); //L1
		grid[0][13].setGhost(true); //L14
		grid[9][13].setGhost(true); //B14
		grid[10][12].setGhost(true); //A13
		grid[10][13].setGhost(true); //A14
	}

	public void debugOutput() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j].seatPos + " ");
			}
			System.out.println();
		}
	}

	public void assignCol(int col, boolean isLeft, boolean isGhost) {
		char c = 'L';
		int index = col + 1;
		for(int i = 0; i < grid.length; i++) {
			String name = (isGhost) ? "Ghost" : c + "" + index;
			grid[i][col] = new Seat(name, isGhost, isLeft);
			c--;
			if(c=='I') { c--; }
		}
	}
}
