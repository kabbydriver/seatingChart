public class Pepper106 extends Classroom {

	public static void main(String[] args) throws Exception {
		Pepper106 room = new Pepper106(11, 19);
		int[] sectionBreaks = {4,13, 19};

		room.buildClassroom();
		room.read("110roster.csv", "exceptions.csv");
		room.assign(10);
		room.printByRow("Pepper Canyon 106 Row Order - Final Thursday, June 11th - 7pm");
		room.printSorted("Pepper Canyon 106 Sorted Order - Final Thursday, June 11th - 7pm");
		room.printBySection("Pepper 106 Section Order - Final Thursday, June 11th - 7pm", sectionBreaks);
		room.sendEmails("110emails");

		if(1==1) { return; }
		room.buildClassroom();
		//room.read("110roster.csv");
		room.assign(4);
		room.printByCol("Pepper Canyon 106 Column Order - Quiz 2 Thursday, April 23rd - 5pm");
		room.printSorted("Pepper Canyon 106 Sorted Order - Quiz 2 Thursday, April 23rd - 5pm");
		room.printByRow("Pepper Canyon 106 Row Order - Quiz 2 Thursday, April 23rd - 5pm");
		room.printBySection("Pepper 106 Section Order - Quiz 2 Thursday, April 23rd - 5pm", sectionBreaks);
		//room.sendEmails();
		room.buildClassroom();
		room.read("15lsectionA00.csv", "exceptions.csv");
		room.buildClassroom();
		room.assign(10);
		room.printSorted("Pepper Canyon 106 Sorted Order Section A00 - 15L Final 3:00pm - June 11th");
		room.printByRow("Pepper Canyon 106 Row Order Section A00 - 15L Final 3:00pm - June 11th");
		room.printBySection("Pepper 106 Section Order Section A00 - 15L Final 3:00pm - June 11th", sectionBreaks);
		room.sendEmails("SectionA00Emails");

		room.read("15lsectionB00.csv", "exceptions.csv");
		room.buildClassroom();
		room.assign(10);
		room.printSorted("Pepper Canyon 106 Sorted Order Section B00 - 15L Final 7pm - June 12th");
		room.printByRow("Pepper Canyon 106 Row Order Section B00 - 15L Final 7pm - June 12th");
		room.printBySection("Pepper 106 Section Order Section B00 - 15L Final 7pm - June 12th", sectionBreaks);
		room.sendEmails("SectionB00Emails");
	}


	public void make(String roster, String fileName, int seed, String emailFile) throws Exception {
		buildClassroom();
		read(roster , "exceptions.csv");
		assign(seed);
		printSorted(fileName);
		printByRow(fileName);

		sendEmails(emailFile);
		clear();
	}

	/* Initialize the grid */
	public Pepper106(int row, int col) {
		super(row, col);
	}

	/*
	 * This method will fill the grid in with seats to represent the classroom
	 */
	public void buildClassroom() {

		char c = 'A';
		int index = 1;
		boolean isLeft = false;
		boolean isGhost = false;

		/* first column */
		assignCol(0, isLeft, isGhost);

		/* second column */
		assignCol(1, isLeft, isGhost);
		grid[10][1].setGhost(true);

		/* third column */
		assignCol(2, isLeft, isGhost);
		grid[10][2].setGhost(true);

		/*fourth column */
		assignCol(3, !isLeft, isGhost);
		grid[10][3].seatPos = "A2";

		/* fifth column */
		assignCol(4, isLeft, isGhost);
		grid[10][4].seatPos = "A3";

		/* sixth column */
		assignCol(5, isLeft, isGhost);
		grid[10][5].seatPos = "A4";

		/* seventh column */
		assignCol(6, isLeft, isGhost);
		grid[10][6].seatPos = "A5";

		/* eigth column */
		assignCol(7, isLeft, isGhost);
		grid[10][7].setGhost(true);

		/* ninth column */
		assignCol(8, isLeft, isGhost);
		grid[10][8].setGhost(true);	

		/* tenth column */
		assignCol(9, isLeft, isGhost);
		grid[10][9].setGhost(true);	

		/* eleventh column */
		assignCol(10, !isLeft, isGhost);
		grid[10][10].seatPos = "A6";	

		/* twelfth column */
		assignCol(11, isLeft, isGhost);
		grid[10][11].seatPos = "A7";

		/* thirteenth column */
		assignCol(12, !isLeft, isGhost);
		grid[9][12].setGhost(true);
		grid[10][12].setGhost(true);

		/* fourteenth column */
		assignCol(13, !isLeft, isGhost);
		grid[9][13].seatPos = "B13";
		grid[10][13].seatPos = "A8";

		/* fifteenth column */
		assignCol(14, !isLeft, isGhost);		
		grid[9][14].seatPos = "B14";
		grid[10][14].setGhost(true);

		/* sixteenth column */
		assignCol(15, !isLeft, isGhost);		
		grid[9][15].seatPos = "B15";
		grid[10][15].setGhost(true);

		/* seventeenth column */
		assignCol(16, !isLeft, isGhost);	
		grid[9][16].seatPos = "B16";
		grid[10][16].seatPos = "A9";

		/* eighteenth column */
		assignCol(17, isLeft, isGhost);
		grid[0][17].setGhost(true);
		grid[9][17].seatPos = "B17";
		grid[10][17].seatPos = "A10";

		/* nineteenth column */
		assignCol(18, isLeft, !isGhost);
		grid[7][18].seatPos = "D19";
		grid[7][18].setGhost(false);
		grid[8][18].seatPos = "C19";
		grid[8][18].setGhost(false);
		grid[9][18].seatPos = "B18";
		grid[9][18].setGhost(false);
		grid[10][18].seatPos = "A11";
		grid[10][18].setGhost(false);
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
