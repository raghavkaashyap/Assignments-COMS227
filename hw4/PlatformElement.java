package hw4;

import java.util.ArrayList;

/**
 * A PlatformElement is an element with two distinctive behaviors. First, it can
 * be set up to move horizontally within a fixed set of boundaries. On reaching
 * a boundary, the x-component of its velocity is reversed. Second, it maintains
 * a list of <em>associated</em> elements whose basic motion all occurs relative
 * to the PlatformElement.
 * 
 * @author raghavk
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class PlatformElement extends MovingElement{
	
	/**
	 * stores the value of the leftBound for the platform
	 */
	private double leftBound;
	/**
	 * stores rightbound for the platform
	 */
	private double rightBound;
	/**
	 * arrayList of associated elements
	 */
	private ArrayList<api.AbstractElement> elements = new ArrayList<api.AbstractElement>();

	/**
	 * Constructs a new PlatformElement. Initially the left and right boundaries are
	 * <code>Double.NEGATIVE_INFINITY</code> and
	 * <code>Double.POSITIVE_INFINITY</code>, respectively.
	 * 
	 * @param x      x-coordinate of initial position of upper left corner
	 * @param y      y-coordinate of initial position of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public PlatformElement(double x, double y, int width, int height) {
		super(x,y,width,height);
		rightBound = Double.POSITIVE_INFINITY;
		leftBound = Double.NEGATIVE_INFINITY;
	}
	
	
	/**
	 * @param attached added to arrayList
	 */
	public void addAssociated(AttachedElement attached) {
		elements.add(attached);
		attached.setBase(this);
	}
	
	/**
	 * @param follower added to arrayList
	 */
	public void addAssociated(FollowerElement follower) {
		elements.add(follower);
		follower.setBase(this);
	}
	
	/** 
	 * @return arrayList of associated elements
	 */
	public ArrayList<api.AbstractElement> getAssociated(){
		return elements;
	}
	
	/**
	 * deletes associated element marked for deletion
	 */
	public void deleteMarkedAssociated(){
		int i = 0;
		while (i < elements.size()) {
			if(elements.get(i).isMarked()) {
				elements.remove(i);
			}
			else {
				i++;
			}
		}
	}
	
	/**
	 * @return rightBound
	 */
	public double getMax() {
		return rightBound;
	}
	
	/**
	 * @return leftBound
	 */
	public double getMin() {
		return leftBound;
	}
	
	/**
	 * @param min
	 * @param max
	 * sets left and right bounds
	 */
	public void setBounds(double min, double max) {
		leftBound = min;
		rightBound = max;
	}
	
	/**
	 * reverses velocity of platform if it reaches left or right bounds
	 */
	@Override
	public void update() {
		
		super.update();
		
			
		setPosition(getXReal() - getDeltaX(), getYReal());
		if (getXReal() + getWidth() + getDeltaX() >= rightBound) {
			setPosition(rightBound - getWidth(), getYReal());
			setVelocity(-1 * (getDeltaX()), getDeltaY());
		}
		else if(getXReal() + getDeltaX() <= leftBound) { 
			setPosition(leftBound, getYReal());
			setVelocity(-1 * (getDeltaX()), getDeltaY());
		}
		else {
			setPosition(getXReal() + getDeltaX(), getYReal());
		}
		
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).update();
		}
		
	}
}
