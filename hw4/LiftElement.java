package hw4;

import java.util.ArrayList;

/**
 * An element with two distinctive behaviors. First, it can be set up to move
 * vertically within a fixed set of boundaries. On reaching a boundary, the
 * y-component of its velocity is reversed. Second, it maintains a list of
 * <em>associated</em> elements whose basic motion all occurs relative to the
 * LiftElement.
 * 
 * @author raghavk
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class LiftElement extends MovingElement{
	
	/**
	 * upper Bound
	 */
	private double upperBound;
	/**
	 * lower Bound
	 */
	private double lowerBound;
	/**
	 *  arraylist of associated elements
	 */
	private ArrayList<api.AbstractElement> associatedElements = new ArrayList<api.AbstractElement>();
	/**
	 * Constructs a new Elevator. Initially the upper and lower boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public LiftElement(double x, double y, int width, int height) {
		// TODO: everything
		super(x, y, width, height);
		upperBound = Double.POSITIVE_INFINITY;
		lowerBound = Double.NEGATIVE_INFINITY;
	}

	// TODO: everything
	
	/**
	 * @param attached
	 */
	public void addAssociated(AttachedElement attached) {
		associatedElements.add(attached);
		
	}
	
	/**
	 * @param follower
	 */
	public void addAssociated(FollowerElement follower) {
		associatedElements.add(follower);
	}
	
	/**
	 * @return array list of associated elements
	 */
	public ArrayList<api.AbstractElement> getAssociated(){
		return associatedElements;
	}
	
	/**
	 * @return max
	 */
	public double getMax() {
		return lowerBound;
	}
	
	/**
	 * @return min
	 */
	public double getMin() {
		return upperBound;
	}
	
	/**
	 * @param min
	 * @param max
	 */
	public void setBounds(double min, double max) {
		upperBound = min;
		lowerBound = max;
	}
	
	/**
	 *  delete
	 */
	public void deleteMarkedAssociated() {
		int i = 0;
		while (i < associatedElements.size()) {
			if(associatedElements.get(i).isMarked()) {
				associatedElements.remove(i);
			}
			else {
				i++;
			}
		}
	}
	
	@Override
	public void update() { 
		super.update();
		setPosition(getXReal(), getYReal() - getDeltaY());
		if (getYReal() + getHeight() + getDeltaY() >= lowerBound) {
			setPosition(getXReal(), lowerBound - getHeight());
			setVelocity(getDeltaX(), -1 * getDeltaY());
		}
		else if(getYReal() + getDeltaY() <= upperBound) { 
			setPosition(getXReal(), upperBound);
			setVelocity(getDeltaX(), -1 * getDeltaY());
		}
		else {
			setPosition(getXReal(), getYReal() + getDeltaY());
		}
		for (int i = 0; i < associatedElements.size(); i++) {
			associatedElements.get(i).update();
		}
	}
}