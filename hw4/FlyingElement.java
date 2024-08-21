package hw4;

/**
 * Moving element in which the vertical velocity is adjusted each frame by a
 * gravitational constant to simulate gravity. The element can be set to
 * "grounded", meaning gravity will no longer influence its velocity.
 * 
 * @author raghavk
 */
//TODO: This class must directly or indirectly extend AbstractElement
public class FlyingElement extends MovingElement{
	
	/**
	 * checks if object is grounded
	 */
	private boolean isGrounded;
	/**
	 * stores the value of gravity as a double
	 */
	private double gravity;

	/**
	 * Constructs a new FlyingElement. By default it should be grounded, meaning
	 * gravity does not influence its velocity.
	 * 
	 * @param x      x-coordinate of upper left corner
	 * @param y      y-coordinate of upper left corner
	 * @param width  element's width
	 * @param height element's height
	 */
	public FlyingElement(double x, double y, int width, int height) {
		super(x, y, width, height);
		isGrounded = false;
		gravity = 0;
	}
	
	/**
	 * @return isGrounded
	 */
	public boolean isGrounded() {
		return isGrounded;
	}
	
	/**
	 * @param grounded
	 */
	public void setGrounded(boolean grounded) {
		isGrounded = grounded;
	}
	
	/**
	 * @param gravity
	 * sets the value of gravity
	 */
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	
	/**
	 * adds gravity to the y component of velocity if the object is not grounded
	 */
	@Override
	public void update() {
		super.update();
		if (!isGrounded) {
			setVelocity(getDeltaX(), getDeltaY() + gravity);
		}
	}
}
