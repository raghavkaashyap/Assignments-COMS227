package hw4;

import api.AbstractElement;

// TODO:
// Special documentation requirement: (see page 11 of documentation)
// you must add a comment to the top of the SimpleElement class
// with a couple of sentences explaining how you decided to organize
// the class hierarchy for the elements.

/**Class hierarchy 
 * SimpleElement extends AbstractElement. 
 * MovingElement, VanishingElement, AttachedElement extend class SimpleElement.
 * PlatformElement, LiftElement, Follower, and FlyingElement extend class MovingElement. 
 */

/**
 * Minimal concrete extension of AbstractElement. The <code>update</code> method
 * in this implementation just increments the frame count.
 * 
 * @author raghavk
 */

public class SimpleElement extends AbstractElement {

	/**
	 * Stores the x-coordinate of the upper left corner
	 */
	private double x;
	/**
	 * Stores y-coordinate of upper left corner
	 */
	private double y;
	/**
	 * stores element's width
	 */
	private int width;
	/**
	 * stores element's height
	 */
	private int height;
	/**
	 * stores the value of the frame count
	 */
	private int frameCount;
	/**
	 * checks if element is marked for deletion
	 */
	private boolean isMarked = false;
	/**
	 * rectangle representing an element
	 */
	private java.awt.Rectangle rect;

	/**
	 * Constructs a new SimpleElement.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public SimpleElement(double x, double y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		frameCount = 0;
		rect = new java.awt.Rectangle((int) Math.round(x), (int) Math.round(y), width, height);
	}

	/**
	   * Determines whether this element's bounding rectangle overlaps the 
	   * given element's bounding rectangle.
	   * @param other
	   *   given element
	   * @return
	   *   true if this element overlaps the given element
	   */
	@Override
	public boolean collides(AbstractElement other) {
		java.awt.Rectangle otherRect = other.getRect();
		if (otherRect.intersects(rect)) {
			return true;
		}
		return false;
	}

	/**
	  * Returns the number of times that update() has been invoked for this
	  * object.
	  * @return
	  *   number of frames
	  */
	@Override
	public int getFrameCount() {
		return frameCount;
	}

	/**
	 * returns the height of the object
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * returns the rectangle associated with the element
	 */
	@Override
	public java.awt.Rectangle getRect() {
		return rect;
	}

	/**
	 * returns the width of the object 
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * returns the x-coordinate as an integer
	 */
	@Override
	public int getXInt() {
		return (int) Math.round(x);
	}
	
	/**
	   * Returns the x-coordinate's exact value as a double.
	   * @return
	   *   the x-coordinate
	   */
	@Override
	public double getXReal() {
		return x;
	}

	/**
	 * returns the y-coordinate as an integer
	 */
	@Override
	public int getYInt() {
		return (int) Math.round(y);
	}
	
	/**
	   * Returns the y-coordinate's exact value as a double.
	   * @return
	   *   the y-coordinate
	   */
	@Override
	public double getYReal() {
		return y;
	}

	/**
	   * Returns true if this element has been marked for deletion.
	   * @return
	   *   true if this element has been marked for deletion
	   */
	@Override
	public boolean isMarked() {
		return isMarked;
	}

	/**
	  * Marks this element for deletion.
	  */
	@Override
	public void markForDeletion() {
		isMarked = true;
	}

	/**
	 * Sets the position of this element.
	 * 
	 * @param newX the new x-coordinate
	 * @param newY the new y-coordinate
	 */
	@Override
	public void setPosition(double newX, double newY) {
		x = newX;
		y = newY;
		rect.setLocation(getXInt(), getYInt());
	}

	/**
	 * Updates this object's attributes for the next frame.
	 */
	@Override
	public void update() {
		frameCount++;
	}

}
