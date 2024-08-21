package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * @author raghavkaashyap 
 * Represents a Lizard as a collection of body segments.
 */
public class Lizard {

	/**
	 * Number of lizard body segments
	 */
	private int numSegments;
	/**
	 * array list of lizard body segments
	 */
	private ArrayList<BodySegment> lizSegment;

	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		// TODO: method stub
		numSegments = 0;
		lizSegment = null;

	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		// TODO: method stub
		numSegments = segments.size();
		lizSegment = new ArrayList<BodySegment>(numSegments);
		for (int i = 0; i < numSegments; i++) {
			lizSegment.add(segments.get(i));
		}

	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {
		// TODO: method stub
		return lizSegment;
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		// TODO: method stub
		return lizSegment.get(numSegments - 1);
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		// TODO: method stub
		return lizSegment.get(0);
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		// TODO: method stub
		for (int i = 0; i < numSegments; i++) {
			if (lizSegment.get(i).getCell().equals(cell)) {
				return lizSegment.get(i);
			}
		}
		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		// TODO: method stub
		if (lizSegment.indexOf(segment) == (numSegments - 1)) {
			return null;
		} else {
			return lizSegment.get(lizSegment.indexOf(segment) + 1);
		}

	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		// TODO: method stub
		if (lizSegment.indexOf(segment) == 0) {
			return null;
		} else {
			return lizSegment.get(lizSegment.indexOf(segment) - 1);
		}
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		// TODO: method stub
		Direction lizDir;
		if (lizSegment.indexOf(segment) == (numSegments - 1)) {
			return null;
		} else {
			lizDir = getBodyDirection(segment, lizSegment.get(lizSegment.indexOf(segment) + 1));
			return lizDir;
		}
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) {
		// TODO: method stub
		Direction lizDir;
		if (lizSegment.indexOf(segment) == 0) {
			return null;
		} else {
			lizDir = getBodyDirection(segment, lizSegment.get(lizSegment.indexOf(segment) - 1));
			return lizDir;
		}
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		// TODO: method stub
		if (numSegments <= 1) {
			return null;
		} else {
			BodySegment s = lizSegment.get(numSegments - 2);
			Direction headD = getBodyDirection(s, lizSegment.get(numSegments-1));
			return headD;
		}
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {
		// TODO: method stub
		if (numSegments <= 1) {
			return null;
		} else {
			BodySegment s = lizSegment.get(1);
			Direction tailD = getBodyDirection(s, lizSegment.get(0));
			return tailD;
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}

	/**
	 * @param segment1
	 * @param segment2
	 * @return Direction of BodySegment
	 * Helper method takes in 2 body segments and returns the direction of segment 1 
	 */
	private Direction getBodyDirection(BodySegment segment1, BodySegment segment2) {
		if ((segment1.getCell().getCol()) < (segment2.getCell().getCol())) {
			return RIGHT;
		} else if (segment1.getCell().getCol() > segment2.getCell().getCol()) {
			return LEFT;
		} else if (segment1.getCell().getRow() < segment2.getCell().getRow()) {
			return DOWN;
		} else {
			return UP;
		}
	}
}
