
package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author raghavk
 */

public class FuzzballGame {
	/**
	 * Number of strikes causing a player to be out.
	 */
	public static final int MAX_STRIKES = 2;

	/**
	 * Number of balls causing a player to walk.
	 */
	public static final int MAX_BALLS = 5;

	/**
	 * Number of outs before the teams switch.
	 */
	public static final int MAX_OUTS = 3;

	// TODO: EVERTHING ELSE
	// Note that this code will not compile until you have put in stubs for all
	// the required methods.
	/**
	 * keeps track of the number of innings played
	 */
	private int numInnings;
	/**
	 * keeps track of the maximum number of innings in the game
	 */
	private int maxInnings;
	/**
	 * keeps track of whether it is the top of the inning or the bottom
	 */
	private boolean isTopOfInning;
	/**
	 * keeps track of the number of balls
	 */
	private int numBalls;
	/**
	 * keeps track of the number of strikes
	 */
	private int numStrikes;
	/**
	 * keeps track of the number of outs
	 */
	private int numOuts;
	/**
	 * keeps track of team0's score
	 */
	private int team0Score;
	/**
	 * keeps track of team1's score
	 */
	private int team1Score;
	/**
	 * keeps track of imaginary runner on base 1
	 */
	private boolean runnerBase1;
	/**
	 * keeps track of imaginary runner on base 2
	 */
	private boolean runnerBase2;
	/**
	 * keeps track of imaginary runner on base 3
	 */
	private boolean runnerBase3;

	/**
	 * @param givenNumInnings
	 * Constructs a game that has the given number of innings and starts at the top of inning 1
	 */
	public FuzzballGame(int givenNumInnings) {
		maxInnings = givenNumInnings;
		numInnings = 1;
		isTopOfInning = true;
		numBalls = 0;
		numStrikes = 0;
		numOuts = 0;
		runnerBase1 = false;
		runnerBase2 = false;
		runnerBase3 = false;
	}
	/**
	 * @return true if the game is over, false otherwise. checks if number of innings is greater than the maximum number of innings.
	 */
	public boolean gameEnded() {
		if (numInnings > maxInnings) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @param which
	 * @return true if there is a runner on the indicated base, false otherwise.
	 */
	public boolean runnerOnBase(int which) {
		if (which == 1) {
			return runnerBase1;
		} else if (which == 2) {
			return runnerBase2;
		} else if (which == 3) {
			return runnerBase3;
		} else {
			return false;
		}
	}
	/**
	 * @return the current inning.
	 */
	public int whichInning() {
		return numInnings;
	}
	/**
	 * @return true if it's the first half of the inning (team 0 is at bat).
	 */
	public boolean isTopOfInning() {
		return isTopOfInning;
	}

	/**
	 * @return the number of outs for the team currently at bat.
	 */
	public int getCurrentOuts() {
		return numOuts;
	}

	/**
	 * @return the number of called strikes for the current batter.
	 */
	public int getCalledStrikes() {
		return numStrikes;
	}

	/**
	 * @return the count of balls for the current batter.
	 */
	public int getBallCount() {
		return numBalls;
	}

	/**
	 * @return the score for team 0.
	 */
	public int getTeam0Score() {
		
		return team0Score;
	}

	/**
	 * @return the score for team 1.
	 */
	public int getTeam1Score() {
		return team1Score;
	}

	/**
	 * @param swung
	 * Method called to indicate a strike for the current batter.
	 */
	public void strike(boolean swung) {
		if (!(gameEnded())) {
			if (swung == true) {
				numOuts += 1;
				numBalls = 0;
				numStrikes = 0;
			} else {
				numStrikes += 1;
				if (numStrikes == MAX_STRIKES) {
					numStrikes = 0;
					numOuts += 1;
					numBalls = 0;
				}
			}
			if (numOuts == MAX_OUTS) {
				isTopOfInning = !(isTopOfInning);
				if (isTopOfInning) {
					numInnings += 1;
				}
				numOuts = 0;
				runnerBase1 = false;
				runnerBase2 = false;
				runnerBase3 = false;
				numBalls = 0;
			}
		}
	}

	/**
	 * Method called to indicate that the batter is out due to a caught fly.
	 */
	public void caughtFly() {
		strike(true);
	}

	/**
	 * 	Method called to indicate a bad pitch at which the batter did not swing.
	 */
	public void ball() {
		if (!(gameEnded())) {
			numBalls += 1;
			if (numBalls == 5) {
				walk(); // call to method walk
				numBalls = 0;
				numStrikes = 0;
			}
		}
	}

	/**
	 * @param distance
	 * Method called to indicate that the batter hit the ball.
	 */
	public void hit(int distance) {
		if (!(gameEnded())) {
			if (distance < 15) {
				strike(true);
			} else if ((distance >= 15) && (distance < 150)) {
				shiftRunners(1);
			} else if ((distance >= 150) && (distance < 200)) {
				shiftRunners(2);
			} else if ((distance >= 200) && (distance < 250)) {
				shiftRunners(3);
			} else if (distance >= 250) {
				shiftRunners(4);
			}
			numBalls = 0;
			numStrikes = 0;
		}
	}

	/**
	 * @param numRuns
	 * shifts runners and calls update score based on number of runs (taken as a parameter)
	 */
	private void shiftRunners(int numRuns) {
		if (numRuns == 1) {
			if (!(runnerBase1 || runnerBase2 || runnerBase3)) {
				runnerBase1 = true;
			} else if (!(runnerBase1 || runnerBase2) && runnerBase3) {
				runnerBase1 = true;
				runnerBase3 = false;
				updateScore(1);
			} else if (!(runnerBase2 || runnerBase3) && runnerBase1) {
				runnerBase2 = true;
			} else if (!(runnerBase1 || runnerBase3) && runnerBase2) {
				runnerBase3 = true;
				runnerBase1 = true;
				runnerBase2 = false;
			} else if (!(runnerBase1) && (runnerBase2 && runnerBase3)) {
				runnerBase1 = true;
				runnerBase2 = false;
				runnerBase3 = true;
				updateScore(1);
			} else if (!(runnerBase2) && (runnerBase1 && runnerBase3)) {
				runnerBase2 = true;
				runnerBase3 = false;
				updateScore(1);
			} else if (!(runnerBase3) && (runnerBase1 && runnerBase2)) {
				runnerBase3 = true;
			} else if (runnerBase1 && runnerBase2 && runnerBase3) {
				updateScore(1);
			}
		} else if (numRuns == 2) {
			if (!(runnerBase1 || runnerBase2 || runnerBase3)) {
				runnerBase2 = true;
			} else if (!(runnerBase1 || runnerBase2) && runnerBase3) {
				runnerBase2 = true;
				runnerBase3 = false;
				updateScore(1);
			} else if (!(runnerBase2 || runnerBase3) && runnerBase1) {
				runnerBase1 = false;
				runnerBase2 = true;
				runnerBase3 = true;
			} else if (!(runnerBase1 || runnerBase3) && runnerBase2) {
				updateScore(1);
			} else if (!(runnerBase1) && (runnerBase2 && runnerBase3)) {
				runnerBase3 = false;
				updateScore(2);
			} else if (!(runnerBase2) && (runnerBase1 && runnerBase3)) {
				runnerBase2 = true;
				runnerBase1 = false;
				updateScore(1);
			} else if (!(runnerBase3) && (runnerBase1 && runnerBase2)) {
				runnerBase1 = false;
				runnerBase3 = true;
				updateScore(1);
			} else if (runnerBase1 && runnerBase2 && runnerBase3) {
				runnerBase1 = false;
				runnerBase3 = false;
				updateScore(2);
			}
		} else if (numRuns == 3) {
			if (!(runnerBase1 || runnerBase2 || runnerBase3)) {
				runnerBase3 = true;
			} else if (!(runnerBase1 || runnerBase2) && runnerBase3) {
				updateScore(1);
			} else if (!(runnerBase2 || runnerBase3) && runnerBase1) {
				runnerBase3 = true;
				runnerBase1 = false;
				updateScore(1);
			} else if (!(runnerBase1 || runnerBase3) && runnerBase2) {
				runnerBase3 = true;
				runnerBase2 = false;
				updateScore(1);
			} else if (!(runnerBase1) && (runnerBase2 && runnerBase3)) {
				runnerBase2 = false;
				updateScore(2);
			} else if (!(runnerBase2) && (runnerBase1 && runnerBase3)) {
				runnerBase1 = false;
				updateScore(2);
			} else if (!(runnerBase3) && (runnerBase1 && runnerBase2)) {
				runnerBase1 = false;
				runnerBase2 = false;
				runnerBase3 = true;
				updateScore(2);
			} else if (runnerBase1 && runnerBase2 && runnerBase3) {
				runnerBase1 = false;
				runnerBase2 = false;
				updateScore(3);
			}
		} else if (numRuns == 4) {

			if (runnerBase1 && runnerBase2 && runnerBase3) {
				updateScore(4);
			} else if ((runnerBase1 && runnerBase2 && !(runnerBase3)) || (runnerBase2 && runnerBase3 && !(runnerBase1))
					|| (runnerBase1 && runnerBase3 && !(runnerBase2))) {
				updateScore(3);
			} else if (!(runnerBase1 || runnerBase2 || runnerBase3)) {
				updateScore(1);
			} else if ((runnerBase1 && !(runnerBase2 || runnerBase3)) || (runnerBase2 && !(runnerBase1 || runnerBase3))
					|| (runnerBase3 && !(runnerBase1 || runnerBase2))) {
				updateScore(2);
			}
		}
	}

	/**
	 * performs a walk when 5 balls are pitched
	 */
	private void walk() {
		if (runnerBase1 && runnerBase2 && runnerBase3) {
			updateScore(1);
		} else if ((runnerBase1 && runnerBase2) && !(runnerBase3)) {
			runnerBase3 = true;
		} else if (!(runnerBase1) && (runnerBase2 && runnerBase3)) {
			runnerBase1 = true;
		} else if (!(runnerBase2) && (runnerBase1 && runnerBase3)) {
			runnerBase2 = true;
		} else if (!(runnerBase1 || runnerBase3) && runnerBase2) {
			runnerBase1 = true;
		} else if (!(runnerBase2 || runnerBase3) && runnerBase1) {
			runnerBase2 = true;
		} else if (!(runnerBase1 || runnerBase2) && runnerBase3) {
			runnerBase1 = true;
		} else if (!(runnerBase1 || runnerBase2 || runnerBase3)) {
			runnerBase1 = true;
		}
	}

	/**
	 * @param score
	 *  updates the score of team0 or team1 based on the call from the hit method
	 */
	private void updateScore(int score) {
		if (isTopOfInning) {
			team0Score += score;
		} else {
			team1Score += score;
		}
	}

	// The methods below are provided for you and you should not modify them.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.
	/**
	 * Returns a three-character string representing the players on base, in the
	 * order first, second, and third, where 'X' indicates a player is present and
	 * 'o' indicates no player. For example, the string "oXX" means that there are
	 * players on second and third but not on first.
	 * 
	 * @return three-character string showing players on base
	 */
	public String getBases() {
		return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o") + (runnerOnBase(3) ? "X" : "o");
	}

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * 
	 * <pre>
	 *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
	 * </pre>
	 * 
	 * The first three characters represent the players on base as returned by the
	 * <code>getBases()</code> method. The 'T' after the inning number indicates
	 * it's the top of the inning, and a 'B' would indicate the bottom. The score
	 * always shows team 0 first.
	 * 
	 * @return a single line string representation of the state of the game
	 */
	public String toString() {
		String bases = getBases();
		String topOrBottom = (isTopOfInning() ? "T" : "B");
		String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
		return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(), getTeam1Score(), getBallCount(),
				getCalledStrikes(), getCurrentOuts());
	}
}