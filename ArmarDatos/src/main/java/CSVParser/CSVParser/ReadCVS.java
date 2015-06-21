package CSVParser.CSVParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import CSVParser.CSVParser.DataConstants;
 
public class ReadCVS {
	
	
	private static final Double ROUNDCONST = 10000.0;
	private Integer[] maxID = new Integer[10];
	private Integer[] minID = new Integer[10];
	
	private Boolean processed = false;
	private boolean testingFile = false;
	private RandomSelector randomSelector = new RandomSelector(DataConstants.SELECTORPROBABILITY);
 
  public static void main(String[] args) {
 
	ReadCVS obj = new ReadCVS();
	obj.testing();
	obj.run();
	obj.run();
  }
 
  public void testing() {
	  testingFile  = true;
}

public void run() {
 
	String csvFile = "C:\\Users\\Emperor\\Downloads\\data_format2\\data_format2\\test_format2.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	WriteFile fileW = new WriteFile("C:\\Users\\Emperor\\Downloads\\data_format2\\data_format2\\test_format2P2.csv");
	
	
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		if (processed) {
			fileW.startPrinting();
		}
		while ((line = br.readLine()) != null) {
 
		        // use comma as separator
			String[] separetedLine = line.split(cvsSplitBy);
			if (separetedLine.length < 5) {
				continue;
			}
			if ((separetedLine[DataConstants.LABELP.intValue()].equals("-1"))||(separetedLine[DataConstants.LABELP.intValue()].equals("label"))) {
				//Discard
			} else {
				//Process
				if (!this.processed) {
					process(separetedLine);
				} else {
					if (randomSelector.chosen()) {
						createLine(separetedLine, fileW);
					}
				}
			}
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (processed) {
			fileW.stopPrinting();
		}
		processed = !processed;
		this.maxID[4] = 1;
	}
	print();
	System.out.println("Done");
  }

private void print() {
	PrintWriter outFile = null;
	try {
		outFile = new PrintWriter(new FileWriter("C:\\Users\\Emperor\\Downloads\\data_format2\\data_format2\\outputMaxMinT.txt"));
		outFile.println("Maximum and minimum values from the data fields.");
	    outFile.print("Round Constant: ");
	    outFile.println(ROUNDCONST);
	    outFile.println("Maximum values (default data order):");
	    for(Integer element : maxID) {
	    	outFile.print(element);
	    	outFile.print("\t");
	    }
	    outFile.println("\n");
	    outFile.println("Minimum values (default data order):");
	    for(Integer element : minID) {
	    	outFile.print(element);
	    	outFile.print("\t");
	    }
	    outFile.println("\n");
	    
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (outFile != null) {
			outFile.close();
		}
	}
	
}

private void process(String[] separetedLine) {
	String firstSplitter = "#";
	String subline = "";
	String secondSplitter = ":";
	String[] newLine;
	String[] oldLine;
	if (separetedLine.length > 5) {
		subline = separetedLine [DataConstants.ACTIVITYLOGP.intValue()];
	} else {
		return;
	}
	String[] transactionDataLine = subline.split(firstSplitter);
	oldLine = Arrays.copyOf(separetedLine, separetedLine.length - 1);
	for (int it = 0; it < transactionDataLine.length; it++) {
		newLine = transactionDataLine[it].split(secondSplitter);
		newLine = ArrayUtils.addAll(oldLine, newLine);
		checkForNulls(newLine);
		checkMaxMin(newLine);
	}
}

private void checkForNulls(String[] newLine) {
	
	for (int it = 0; it < 10; it++) {
		if (newLine[it].equals("") && it == DataConstants.AGERANGEP.intValue()) {
			newLine[it] = "2";
		} else if (newLine[it].equals("")) {
			newLine[it] = "0";
		}
	}
	
}

private void checkMaxMin(String[] newLine) {
	Integer value = 0;
	
	for (int it = 0; it < 10; it++) {
		value = Integer.parseInt(newLine [it].toString());
		if (this.maxID[it] == null) {
			this.maxID[it] = 0;
		}
		if (this.minID[it] == null) {
			this.minID[it] = 0;
		}
		if (this.maxID[it].intValue() < value.intValue()) {
			this.maxID[it] = value.intValue();
		}
		if (this.minID[it].intValue() > value.intValue()) {
			this.minID[it] = value.intValue();			
		}
	}
	
}

private void createLine(String[] separetedLine, WriteFile fileW) {
	String firstSplitter = "#";
	String subline = "";
	String secondSplitter = ":";
	String[] newLine;
	String[] oldLine;
	String joinedLine;
	if (separetedLine.length > 5) {
		subline = separetedLine [DataConstants.ACTIVITYLOGP.intValue()];
	} else {
		return;
	}
	String[] transactionDataLine = subline.split(firstSplitter);
	oldLine = Arrays.copyOf(separetedLine, separetedLine.length - 1);
	for (int it = 0; it < transactionDataLine.length; it++) {
		newLine = transactionDataLine[it].split(secondSplitter);
		newLine = ArrayUtils.addAll(oldLine, newLine);
		checkForNulls(newLine);
		normalizeLine(newLine);
		checkTesting(newLine);
		moveToEnd(newLine, DataConstants.LABELP.intValue());
		joinedLine = String.join(",", newLine);
		fileW.writeToFile(joinedLine);
	}
}

private void checkTesting(String[] line) {
	if (testingFile) {
		line [DataConstants.LABELP.intValue()] = "";
	}
	
}

private void moveToEnd(String[] line, int position) {
	
	String temp = line[position];

	for (int i = (position + 1); i < (line.length); i++) {                
	    line[i-1] = line[i];
	}

	line[line.length - 1] = temp;
	
}

private void normalizeLine(String[] newLine) {
	Double value = 0.0;
	
	for (int it = 0; it < 10; it++) {
		value = Double.parseDouble(newLine [it].toString());
		value = (value - minID[it])/(maxID[it] - minID[it]);
		value = Math.round(value*ROUNDCONST)/ROUNDCONST;
		newLine[it] = String.valueOf(value.doubleValue());
	}
	
}
 
}
