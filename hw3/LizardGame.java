package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;

/**
 * @author raghavkaashyap 
 * Class that models a game.
 */
public class LizardGame {
	
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/**
	 * Array list of lizards
	 */
	private ArrayList<Lizard> lizards;
	/**
	 * Grid of cells
	 */
	private Cell[][] c;
	/**
	 * width of grid
	 */
	private int width;
	/**
	 * height of grid
	 */
	private int height;

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		this.width = width;
		this.height = height;
		lizards = new ArrayList<Lizard>();
		c = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				c[i][j] = new Cell(j, i);
			}
		}
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		wall.getCell().placeWall(wall);
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		exit.getCell().placeExit(exit);
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return lizards;
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		lizards.add(lizard);
		scoreListener.updateScore(lizards.size());
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) {
		ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
		lizards.remove(lizard);
		segments = lizard.getSegments();
		for (int i = 0; i < segments.size(); i++) {
			segments.get(i).getCell().removeLizard();
		}
		scoreListener.updateScore(lizards.size());
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		if (col < 0 || col >= width || row >= height || row < 0) {
			return null;
		}
		return c[row][col];
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {
		if (col >= 0 && col < width && row < height && row >= 0) {
			if (dir == UP && row > 0) {
				return c[row - 1][col];
			} else if (dir == DOWN && row < height - 1) {
				return c[row + 1][col];
			} else if (dir == LEFT && col > 0) {
				return c[row][col - 1];
			} else if (dir == RIGHT && col < width - 1) {
				return c[row][col + 1];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width headCol height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		this.width = width;
		this.height = height;
		lizards = new ArrayList<Lizard>();
		c = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				c[i][j] = new Cell(j, i);
			}
		}
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		if (c[row][col].getWall() == null && c[row][col].getLizard() == null) {
			return true;
		}
		return false;
	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {

		Cell currentCell = c[row][col];
		Lizard lizard = currentCell.getLizard();
		Cell adjCell = getAdjacentCell(col, row, dir);

		if (col < 0 || col >= width || row < 0 || row >= height || lizard == null) {
			return;
		}

		if (moveForward(col, row, lizard, adjCell)) {
			move(lizard.getHeadSegment().getCell().getCol(), lizard.getHeadSegment().getCell().getRow(), dir);
		} else if (moveBackward(col, row, lizard, adjCell)) {
			move(lizard.getTailSegment().getCell().getCol(), lizard.getTailSegment().getCell().getRow(), dir);
		} else if (moveLizardBody(col, row, lizard, adjCell)) {
			move(lizard.getHeadSegment().getCell().getCol(), lizard.getHeadSegment().getCell().getRow(), dir);
		} else if (adjCell == null || !isAvailable(adjCell.getCol(), adjCell.getRow())) {
			return;
		}

		if (lizard == null || !canMove(lizard, dir, currentCell)) {
			return;
		}

		int headCol = lizard.getHeadSegment().getCell().getCol();
		int headRow = lizard.getHeadSegment().getCell().getRow();
		int tailCol = lizard.getTailSegment().getCell().getCol();
		int tailRow = lizard.getTailSegment().getCell().getRow();

		if (headCol == col && headRow == row && dir.equals(RIGHT)) {
			Cell temp = lizard.getHeadSegment().getCell();
			lizard.getHeadSegment().setCell(c[headRow][headCol + 1]);
			for (int i = lizard.getSegments().size() - 2; i >= 0; i--) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}
			c[tailRow][tailCol].removeLizard();
		}
		else if (headCol == col && headRow == row && dir.equals(LEFT)) {
			Cell temp = lizard.getHeadSegment().getCell();
			lizard.getHeadSegment().setCell(c[headRow][headCol - 1]);
			for (int i = lizard.getSegments().size() - 2; i >= 0; i--) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}
			c[tailRow][tailCol].removeLizard();
		}
		else if (headCol == col && headRow == row && dir.equals(UP)) {
			Cell temp = lizard.getHeadSegment().getCell();
			lizard.getHeadSegment().setCell(c[headRow - 1][headCol]);
			for (int i = lizard.getSegments().size() - 2; i >= 0; i--) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}
			c[tailRow][tailCol].removeLizard();
		}
		else if (headCol == col && headRow == row && dir.equals(DOWN)) {
			Cell temp = lizard.getHeadSegment().getCell();
			lizard.getHeadSegment().setCell(c[headRow + 1][headCol]);

			for (int i = lizard.getSegments().size() - 2; i >= 0; i--) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}
			c[tailRow][tailCol].removeLizard();
		}
		else if (tailCol == col && tailRow == row && dir.equals(RIGHT)) {
			Cell temp = lizard.getTailSegment().getCell();
			lizard.getTailSegment().setCell(c[tailRow][tailCol + 1]);
			for (int i = 1; i <= lizard.getSegments().size() - 1; i++) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}
			c[headRow][headCol].removeLizard();
		}
		else if (tailCol == col && tailRow == row && dir.equals(LEFT)) {
			Cell temp = lizard.getTailSegment().getCell();
			lizard.getTailSegment().setCell(c[tailRow][tailCol - 1]);

			for (int i = 1; i <= lizard.getSegments().size() - 1; i++) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;
			}

			c[headRow][headCol].removeLizard();
		}
		else if (tailCol == col && tailRow == row && dir.equals(UP)) {
			Cell temp = lizard.getTailSegment().getCell();
			lizard.getTailSegment().setCell(c[tailRow - 1][tailCol]);
			for (int i = 1; i <= lizard.getSegments().size() - 1; i++) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;

			}
			c[headRow][headCol].removeLizard();

		}
		else if (tailCol == col && tailRow == row && dir.equals(DOWN)) {
			Cell temp = lizard.getTailSegment().getCell();
			lizard.getTailSegment().setCell(c[tailRow + 1][tailCol]);

			for (int i = 1; i <= lizard.getSegments().size() - 1; i++) {
				Cell anotherCell = lizard.getSegments().get(i).getCell();
				lizard.getSegments().get(i).setCell(temp);
				temp = anotherCell;

			}
			c[headRow][headCol].removeLizard();
		}
		if (lizard.getHeadSegment().getCell().getExit() != null
				|| lizard.getTailSegment().getCell().getExit() != null) {
			removeLizard(lizard);
		}
		if (getLizards().isEmpty() && dialogListener != null) {
			dialogListener.showDialog("You win!");
		}
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}

	/**
	 * @param col
	 * @param row
	 * @param lizard
	 * @param adjCell
	 * @return checks if lizard body can move into the adjacent cell, false otherwise
	 */
	private boolean moveLizardBody(int col, int row, Lizard lizard, Cell adjCell) {

		if (adjCell == null)
			return false;
		for (int i = 1; i < lizard.getSegments().size() - 1; i++) {
			if (col == lizard.getSegments().get(i).getCell().getCol() && row == lizard.getSegments().get(i).getCell().getRow()) {
				if (adjCell.getLizard() == null) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * @param col
	 * @param row
	 * @param lizard
	 * @param adjCell
	 * @return true if the lizard can move forward, false otherwise
	 */
	private boolean moveForward(int col, int row, Lizard lizard, Cell adjCell) {
		if (col == lizard.getTailSegment().getCell().getCol() && row == lizard.getTailSegment().getCell().getRow()) {
			if (adjCell == lizard.getSegments().get(1).getCell()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * @param col
	 * @param row
	 * @param lizard
	 * @param adjCell
	 * @return true if the lizard can move backward, false otherwise
	 */
	private boolean moveBackward(int col, int row, Lizard lizard, Cell adjCell) {
		if (col == lizard.getHeadSegment().getCell().getCol() && row == lizard.getHeadSegment().getCell().getRow()) {
			if (adjCell == lizard.getSegments().get(lizard.getSegments().size() - 2).getCell()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	
	/**
	 * @param lizard
	 * @param dir
	 * @param cell
	 * @return false if adjacent cell in given direction is not available for lizard to move
	 * true if lizard can move to adjacent cell
	 */
	private boolean canMove(Lizard lizard, Direction dir, Cell cell) {
		Cell adjCell = getAdjacentCell(cell.getCol(), cell.getRow(), dir);
		if (adjCell == null || !(isAvailable(adjCell.getCol(), adjCell.getRow()))) {
			return false;
		}
		return true;
	}
}
