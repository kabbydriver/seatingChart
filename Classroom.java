import java.util.*;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;


public abstract class Classroom {
	public Seat[][] grid;	
	public int roomSize = 0;
	public int row;
	public int col;
	public ArrayList<Student> sortedStudentList = new ArrayList<Student>();

	public void printByCol(String pdfName) throws Exception {

		ArrayList<Student> columnList = new ArrayList<Student>();

		for(int i = 0; i < grid[0].length; i++) {
			for(int j = 0; j < grid.length; j++) {
				if(grid[j][i].isGhostSeat) continue;
				columnList.add(grid[j][i].student);
			}
		}
		Printer.generate(pdfName, columnList, getRoomSize());
	}

	public void printBySection(String pdfName, int[] sectionBreak) throws Exception {

		ArrayList<Student> sectionList = new ArrayList<Student>();

		int prevBreak = 0;
		int currBreak = 0;
		for(int i = 0; i < sectionBreak.length; i++) {

			prevBreak = currBreak;
			currBreak = sectionBreak[i];
			boolean flag = true;
			
			for(int j = 0; j < grid.length; j++) {
				int start = prevBreak;
				int end = currBreak;

				if(flag) {
					for(int k = start; k < end; k ++) {
						if(!grid[j][k].isGhostSeat) {
							sectionList.add(grid[j][k].student);
						}
					}
				} else {
					for(int k = end - 1; k >= start; k--) {
						if(!grid[j][k].isGhostSeat) {
							sectionList.add(grid[j][k].student);
						}
					}
				}

				flag = !flag;

			}
		}
		Printer.generate(pdfName, sectionList, getRoomSize());
	}

	public void debugSectionOrder(String pdfName, int[] sectionBreak) {
		int prevBreak = 0;
		int currBreak = 0;
		for(int i = 0; i < sectionBreak.length; i++) {

			prevBreak = currBreak;
			currBreak = sectionBreak[i];

			for(int j = 0; j < grid.length; j++) {
				for(int k = prevBreak; k < currBreak; k++) {
					if(!grid[j][k].isGhostSeat) {
						System.out.print(grid[j][k] + " ");
					} else {
						System.out.print("EMP ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public void printByRow(String pdfName) throws Exception {
		ArrayList<Student> rowList = new ArrayList<Student>();
		for(int i = grid.length - 1; i >= 0; i--) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j].isGhostSeat || grid[i][j].isEmpty) continue;
				rowList.add(grid[i][j].student);
			}
		}
		Printer.generate(pdfName, rowList, getRoomSize());
	}

	public void printSorted(String pdfName) throws Exception {
		Printer.generate(pdfName, sortedStudentList, getRoomSize());
	}

	public abstract void buildClassroom(); //fills out grid with seats

	public Classroom(int row, int col) {
		this.row = row;
		this.col = col;
		grid = new Seat[row][];
		for(int i = 0; i < row; i++) {
			grid[i] = new Seat[col];
		}
	}

	/*
	 * Read in roster.csv
	 */
	public void read(String roster, String exceptionsFile) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(roster));
		String input;
		String[] inputArray;
		int count = 0;
		while(in.ready()) {
			input = in.readLine();
			inputArray = input.split(",");
			String lastName = inputArray[0].replace("\"", "");
			String firstName = inputArray[1].replace("\"", "").split(" ")[0];
			String email = inputArray[2];
			String strID = inputArray[3];
			//int id = Integer.parseInt(strID);
			count++;
			sortedStudentList.add(new Student(firstName, lastName, email, count));
			System.out.println(sortedStudentList.get(sortedStudentList.size()-1));
		}

		handleExceptions(exceptionsFile);
	}

	public void handleExceptions(String filename) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String input;
		String[] inputArray;
		while(in.ready()) {
			input = in.readLine();
			inputArray = input.split(",");
			String lastName = inputArray[0].replace("\"", "");
			String firstName = inputArray[1].replace("\"", "");
			String email = inputArray[2];
			String exception = inputArray[3];
			Student tempStudent = new Student(firstName, lastName, email, 0);

			for(Student s : sortedStudentList) {
				if(s.equals(tempStudent)) {
					s.isOSD = true;
				}
			}

		}

	}

	/*
	 * Assign students to seats in the grid
	 */ 
	public void assign(int seed) {

		ArrayList<Student> tempList = new ArrayList<Student>(sortedStudentList);

		if(seed != 0) {
			Collections.shuffle(tempList, new Random(seed));
		}
		int index = 0;
		//assign all the odd columns
		for(int i = 1; i < grid[0].length; i+=2) {
			for(int j = grid.length - 1; j >= 0; j--) {
				Seat seat = grid[j][i]; 

				if(seat.isGhostSeat) {continue;}
				Student tempStudent = tempList.get(index++);
				seat.student = tempStudent;
				tempStudent.seat = seat;
				System.out.println("FILLING: " + seat);
			}
		}

		//assign all the even columns
		for(int i = 0; i < grid[0].length; i+=2) {
			for(int j = grid.length - 1; j >= 0; j--) {
				Seat seat = grid[j][i]; 
				if(seat.isGhostSeat) {continue;}
				if(index >= tempList.size()) { continue; }
				Student tempStudent = tempList.get(index++);
				seat.student = tempStudent;
				tempStudent.seat = seat;
				System.out.println("FILLING: " + seat);

			}
		}

		for(Seat[] row : grid) {
			for(Seat s : row) {
				if(s.isGhostSeat) { continue; }
				if(s.student == null) { 
					Student emptyStudent = new Student("EMPTY", "EMPTY", "", 0);
					s.isEmpty = true;
					emptyStudent.seat = s;
					s.assignStudent(emptyStudent); 
					sortedStudentList.add(new Student(emptyStudent));
				}
			}
		}

		for(Seat[] row : grid) {
			for(Seat s : row) {
				if(s.isGhostSeat) { continue; }
				if(s.student == null) { System.out.println(s + " is null"); }
			}
		}
	}

	public int getRoomSize() {
		roomSize = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(!grid[i][j].isGhostSeat) {
					roomSize++;
				}  
			}
		}
		return roomSize;
	}

	public void clear() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j] = null;
			}
		}

		sortedStudentList = new ArrayList<Student>();
	}

	public void sendEmails(String filename) throws Exception {

		String writeStr = "";
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {

				if(grid[i][j].student == null) { continue; }
				if(grid[i][j].isEmpty || grid[i][j].isGhostSeat) {continue; }
				String str = grid[i][j].student.sendMail();
				writeStr += str + "\n";

			}
		}

		File file = new File(filename);
		if(!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(writeStr);
		bw.close();
	}

}
