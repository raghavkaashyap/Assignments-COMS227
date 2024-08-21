package hw3;

import api.BodySegment;
import api.Cell;
import api.Exit;
import api.Wall;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author raghavkaashyap 
 * Utility class with static methods for loading game files.
 */
public class GameFileUtil {
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		
		File f = new File(filePath); // create file object from the passed in file
		Scanner scnr = null;
		try {
			scnr = new Scanner(f); // create a scanner object to read the file
		} catch (Exception e) {
			scnr.close();
			return;
		}
		String[] grid = scnr.nextLine().split("x");
		int width = Integer.parseInt(grid[0]);
		int height = Integer.parseInt(grid[1]);
		game.resetGrid(width, height);
		for (int i = 0; i < height; i++) {
			String l = scnr.nextLine();
			for (int j = 0; j < width; j++) {
				char c = l.charAt(j);
				Cell cell = game.getCell(j, i);
				if (c == 'W') {
					Wall wall = new Wall(cell);
					game.addWall(wall);
				} else if (c == 'E') {
					Exit exit = new Exit(cell);
					game.addExit(exit);
				} else if (c == ',') {
					break;
				}
			}
		}
		while (scnr.hasNextLine()) {
			String line = scnr.nextLine();
			if (line.startsWith("L")) {
				String[] parts = line.substring(2).split(" ");
				Lizard liz = new Lizard();
				ArrayList<BodySegment> segments = new ArrayList<BodySegment>();
				for (int i = 0; i < parts.length; i++) {
					String part = parts[i];
					String[] segmentPos = part.split(",");
					int col = Integer.parseInt(segmentPos[0]);
					int row = Integer.parseInt(segmentPos[1]);
					BodySegment segment = new BodySegment(liz, game.getCell(col, row));
					segments.add(segment);
				}
				liz.setSegments(segments);
				game.addLizard(liz);
			}
		}
		scnr.close();
	}
}
