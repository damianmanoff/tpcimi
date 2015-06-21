package process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
 
public class CSVProcess {
	
	
	private Integer lineCounter = 0;
	//9 for age range, 3 for gender & 4 for action type
	private ArrayList<Double> parametersY = new ArrayList<Double>();
	private ArrayList<Double> parametersN = new ArrayList<Double>();
	private ArrayList<Double> parametersTotal = new ArrayList<Double>();
	private ListIterator<Double> parametersItY = null;
	private ListIterator<Double> parametersItN = null;
	private ListIterator<Double> parametersItTotal = null;
	
 
  public static void main(String[] args) {
 
	CSVProcess obj = new CSVProcess();
	obj.run();
  }
 


public void run() {
 
	String csvFile = "C:\\Users\\Emperor\\Documents\\GitHub\\tpcimi - copia - copia\\results.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",,";
	
	
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
 
		        // use comma as separator
			String[] separetedLine = line.split(cvsSplitBy);
			if (separetedLine.length < 10) {
				continue;
			}
			parseLine(separetedLine);
			processLine(separetedLine);
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
	}
	print();
	System.out.println("Done");
  }

private void print() {
	PrintWriter outFile = null;
	try {
		outFile = new PrintWriter(new FileWriter("C:\\Users\\Emperor\\Documents\\GitHub\\tpcimi - copia - copia\\output.txt"));
		outFile.println("16 Values: 9 for age range, 3 for gender, 4 for action type.");
	    outFile.println("Positive Parameters:");
	    loopPrint (parametersItY, outFile);
	    outFile.println("Negative Parameters:");
	    loopPrint (parametersItN, outFile);
	    outFile.println("Total:");
	    loopPrint (parametersItTotal, outFile);
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (outFile != null) {
			outFile.close();
		}
	}
	
}

private void loopPrint(Iterator <Double> it, PrintWriter outFile) {
	while (it.hasNext()) {
    	outFile.print(it.next().doubleValue());
    	outFile.print("\t");
    }
    outFile.println("\n");
	
}



private void parseLine(String[] separetedLine) {
	
	for (Integer it = 0; it < separetedLine.length; it++) {
		separetedLine[it] = separetedLine[it].replaceAll("[\\s+,\\[\\]]","");
	}
	
}



private void processLine(String[] separetedLine) {
	
	lineCounter++;
	Integer label = (int) Math.round(Math.abs(Double.valueOf(separetedLine[DataConstants.LABELP])));
	
	if ((parametersY.isEmpty())||(parametersN.isEmpty())||(parametersTotal.isEmpty())) {
		initialize();
	}
	checkValue(Double.valueOf(separetedLine[DataConstants.AGERANGEP]), DataConstants.AGESIZE, label);
	checkValue(Double.valueOf(separetedLine[DataConstants.GENDERP]), DataConstants.GENDERSIZE, label);
	checkValue(Double.valueOf(separetedLine[DataConstants.ACTIONTYPE]), DataConstants.ACTIONSIZE, label);
	parametersItY = parametersY.listIterator();
	parametersItN = parametersN.listIterator();
	parametersItTotal = parametersTotal.listIterator();
}



private void checkValue(Double element, Double numberOfElements, Integer label) {
	
	Integer elementPosition = (int) Math.round(element*(numberOfElements-1.0));
	Integer lastPosition = (int) Math.round((numberOfElements-1) - elementPosition);
	
	for (Integer it=0; it < elementPosition; it++) {
		parametersItY.next();
		parametersItN.next();
		parametersItTotal.next();
	}
	if (label.intValue() == DataConstants.POSITIVE) {
		parametersItY.set(parametersItY.next().doubleValue() + 1.0);
		parametersItN.next();
	} else if (label.intValue() == DataConstants.NEGATIVE) {
		parametersItN.set(parametersItN.next().doubleValue() + 1.0);
		parametersItY.next();
	} else {
		parametersItY.next();
		parametersItN.next();
	}
	parametersItTotal.set(parametersItTotal.next().doubleValue() + 1.0);
	for (Integer it=0; it < lastPosition; it++) {
		parametersItY.next();
		parametersItN.next();
		parametersItTotal.next();
	}
}



private void initialize() {
	
	for (int it = 0; it < DataConstants.TOTALVALUES; it++) {
		parametersY.add(0.0);
		parametersN.add(0.0);
		parametersTotal.add(0.0);
	}
	parametersItY = parametersY.listIterator();
	parametersItN = parametersN.listIterator();
	parametersItTotal = parametersTotal.listIterator();
}
	



}
