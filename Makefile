Pepper106:
	javac -cp pdfbox-app-1.8.8.jar:. Printer.java
	javac -cp pdfbox-app-1.8.8.jar:. Classroom.java
	javac -cp pdfbox-app-1.8.8.jar:. Pepper106.java
	java -cp pdfbox-app-1.8.8.jar:. Pepper106

Solis104:
	javac -cp pdfbox-app-1.8.8.jar:. Printer.java
	javac -cp pdfbox-app-1.8.8.jar:. Classroom.java
	javac -cp pdfbox-app-1.8.8.jar:. Solis104.java
	java -cp pdfbox-app-1.8.8.jar:. Solis104


clean:
	rm *.pdf
	rm *.class

