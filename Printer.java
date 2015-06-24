import java.util.*;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

/*This class is a wrapper for writing PDFs*/

public class Printer {

	public static PDDocument document = null;
	public static PDFont fontReg = PDType1Font.HELVETICA;

	public static int headerOffset_Y = 20;
	public static int topLeft_Y = 750;
	public static int topLeft_X = 12;  
	public static int rowGap_Y = -15;
	public static int colGap_X = 150;
	public static int bottomLeft_Y = 10;
	public static int removedStudents = 0;

	
	public static void generate(String pdfName, ArrayList<Student> list, int roomSize) throws Exception {
		PDPageContentStream stream = makeStream();
		writeAll(stream, pdfName, list, roomSize);
		saveDocument(stream, pdfName);
	}
	/*
	 * creates a stream and is ready for writing
	 * Sets font, moves cursor and returns a content stream
	 */
	public static PDPageContentStream makeStream() throws Exception{
		document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.stroke();
		contentStream.beginText();
		contentStream.setFont(fontReg, 7);
		contentStream.moveTextPositionByAmount(topLeft_X, topLeft_Y);
		return contentStream;
	}


	public static void writeAll(PDPageContentStream contentStream, String pdfName, ArrayList<Student> list, int roomSize) throws Exception{
		int listLength = list.size();
		writeHeader(contentStream, pdfName);
		writeList(contentStream, list, roomSize);
		printStats(contentStream, listLength, roomSize);
	}

	public static void writeHeader(PDPageContentStream contentStream, String pdfName) throws Exception {
		contentStream.setFont(fontReg, 12);
		contentStream.moveTextPositionByAmount(125, headerOffset_Y);
		contentStream.drawString(pdfName);
		contentStream.moveTextPositionByAmount(-125, -1 * headerOffset_Y);
		contentStream.setFont(fontReg, 7);
	}

	public static void writeList(PDPageContentStream contentStream, ArrayList<Student> list, int roomSize) throws Exception {
		removedStudents = 0;
		int currentYPos = topLeft_Y;	
		int currentXPos = topLeft_X;
		int listSize = list.size();
		
		while(!list.isEmpty()) {
			Student student = list.remove(0);
			if(student == null) { 
				continue;
			}
			if(student.id == 0) {
				removedStudents++;
			}

			if(student.isOSD) {
				student = new Student(student); //reassign the reference
				student.firstName = "EMPTY";
				student.lastName = "EMPTY";
				student.id = 0;
				removedStudents++;
			}


			contentStream.drawString(student.toString());

			contentStream.moveTextPositionByAmount(0, rowGap_Y);
			currentYPos += rowGap_Y;

			if(currentYPos <=  bottomLeft_Y) {
				contentStream.moveTextPositionByAmount(colGap_X, topLeft_Y - currentYPos);
				currentYPos = topLeft_Y;
				currentXPos += colGap_X;
			}
		}
	}

	public static void printStats(PDPageContentStream contentStream, int listSize, int roomSize) throws Exception {
		contentStream.moveTextPositionByAmount(0, rowGap_Y);
		contentStream.drawString(new String("Total Students: ") + (listSize - removedStudents));
		contentStream.moveTextPositionByAmount(0, rowGap_Y);
		contentStream.drawString(new String("Total Seats: ") + roomSize);
		contentStream.moveTextPositionByAmount(0, rowGap_Y);
		contentStream.drawString(new String("Expected Empty Seats: " + (roomSize - listSize + removedStudents)));
		contentStream.moveTextPositionByAmount(0, rowGap_Y);
		contentStream.drawString(new String("Actual Empty Seats: _____"));
	}

	public static void saveDocument(PDPageContentStream stream, String pdfName) throws Exception {
		stream.endText();
		stream.close();
		document.save(pdfName + ".pdf");
		document.close();
	}

}
