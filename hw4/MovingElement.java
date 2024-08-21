package hw4;

/**
 * An element in which the <code>update</code> method updates the position each
 * frame according to a <em>velocity</em> vector (deltaX, deltaY). The units are
 * assumed to be "pixels per frame".
 * 
 * @author raghavk
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class MovingElement extends SimpleElement{
	
	/**
	 * stores the x component of the object's velocity
	 */
	private double velocityX;
	/**
	 * stores the y component of the object's velocity
	 */
	private double velocityY;

	/**
	 * Constructs a MovingElement with a default velocity of zero in both
	 * directions.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  object's width
	 * @param height object's height
	 */
	public MovingElement(double x, double y, int width, int height) {
		super(x,y,width,height);
		velocityX = 0;
		velocityY = 0;
	}
	
	/**
	 * @return x component of the object's velocity
	 */
	public double getDeltaX() {
		return velocityX;
	}
	
	/**
	 * @return y component of the object's velocity
	 */
	public double getDeltaY() {
		return velocityY;
	}
	
	/**
	 * @param deltaX
	 * @param deltaY
	 * sets the x and y components of objects velocity
	 */
	public void setVelocity(double deltaX, double deltaY) {
		velocityX = deltaX;
		velocityY = deltaY;
	}
	
	/**
	 * moves the object by deltaX in the x direction and deltaY in the y direction
	 */
	@Override
	public void update() {
		super.update();
		setPosition(getXReal() + velocityX, getYReal() + velocityY); 
	}


}
