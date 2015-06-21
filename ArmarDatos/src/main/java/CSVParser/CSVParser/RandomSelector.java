package CSVParser.CSVParser;

import java.util.Random;

public class RandomSelector {
	
	Double porbabilityOfSelection = 0.0;
	Random randomGenerator = null;
	
	
	public RandomSelector (Double probability) {
		if ((probability.doubleValue() > 1.0) || (probability.doubleValue() < 0.0)) {
			this.porbabilityOfSelection = 0.0;
		} else {
			this.porbabilityOfSelection = probability;
		}
		Random seedGenerator = new Random();
		randomGenerator = new Random(seedGenerator.nextLong());
	}
	
	public boolean chosen() {
		
		if (randomGenerator == null) {
			return false;
		}
		if (randomGenerator.nextDouble() < this.porbabilityOfSelection.doubleValue()) {
			return true;
		}
		return false;
		
	}

}
