package process;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	
	private String path;
	private boolean appendToFile = false;
	PrintWriter print_line = null;
	
	public WriteFile(String filePath) {
		path = filePath;
	}
	
	public void startPrinting() throws IOException {
		FileWriter write = new FileWriter(path, appendToFile);
		print_line = new PrintWriter(write);
	}
	
	public void writeToFile(String textLine) {
		if (print_line == null) {
			return;
		}
		print_line.printf("%s"+"%n",textLine);
	}
	
	public void stopPrinting(){
		print_line.close();
		print_line = null;
	}
		
}
