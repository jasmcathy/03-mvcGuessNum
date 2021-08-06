package Model;

public class GuessGameLogic {
	private int theNumber;
	private int remainder = 5;

	public GuessGameLogic(int startNumber, int endNumber) {
		this.theNumber = generateRandomNumber(startNumber, endNumber);
	}

	public GuessGameLogic(int startNumber, int endNumber, int remainder) {
		this.remainder = remainder;
		this.theNumber = generateRandomNumber(startNumber, endNumber);
	}

	public boolean isCorrectGuess(int guess) {
		if (guess == theNumber) {
			return true;
		} else {
			remainder--;
			return false;
		}
	}

	private int generateRandomNumber(int startNumber, int endNumber) {
		double range = (double) (endNumber - startNumber + 1);
		return startNumber + (int) (Math.random() * range);
	}

	public int getRemainder() {
		return remainder;
	}
}
