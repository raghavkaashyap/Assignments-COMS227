package hw1;

import java.lang.Math;
/**
 * @author raghavk
 * class contains instances and methods of a balloon object
 */
public class Balloon {
	
	/**
	 * constant variable representing heat loss factor
	 */
	private final double HEAT_LOSS_FACTOR = 0.1;
	/**
	 * constant variable representing the volume of air in the balloon
	 */
	private final int VOLUME_OF_AIR = 61234;
	/**
	 * constant variable representing the acceleration due to gravity
	 */
	private final double GRAVITY = 9.81;
	/**
	 * constant variable representing the gas constant
	 */
	private final double GAS_CONSTANT = 287.05;
	/**
	 * constant variable representing the standard pressure
	 */
	private final double STANDARD_PRESSURE = 1013.25;
	/**
	 * constant variable representing the kelvin temperature at 0 degrees celsius
	 */
	private final double KELVIN_AT_ZERO_DEGREE_CELSIUS = 273.15;
	
	/**
	 * variable representing the wind direction
	 */
	private double wind;
	/**
	 * variable representing the balloon temperature
	 */
	private double balloonTemperature;
	/**
	 * variable representing the time the simulation's run for
	 */
	private int simulationTime;
	/**
	 * variable representing the fuel remaining in the balloon
	 */
	private double remainingFuel;
	/**
	 * variable representing the balloon's altitude
	 */
	private double balloonAltitude;
	/**
	 * variable representing the velocity of the balloon
	 */
	private double velocity;
	/**
	 * variable representing the rate of burn of fuel
	 */
	private double fuelBurnRate;
	/**
	 * variable representing the mass of the balloon
	 */
	private double balloonMass;
	/**
	 * variable representing the tether length
	 */
	private double tetherLength;
	/**
	 * variable representing the temperature outside the balloon
	 */
	private double outsideAirTemp;
	
	/**
	 * variable representing the initial temperature assigned in the constructor 
	 */
	private final double INITIAL_AIR_TEMP;
	/**
	 * variable representing the initial wind direction assigned in the constructor
	 */
	private final double INITIAL_WIND_DIRECTION;
	
	/**
	 * @param airTemp
	 * @param windDirection
	 * sets initial variable values
	 */
	public Balloon(double airTemp, double windDirection) {
		outsideAirTemp = airTemp;
		balloonTemperature = airTemp;
		INITIAL_AIR_TEMP = airTemp;
		INITIAL_WIND_DIRECTION = windDirection;
		wind = windDirection;
		simulationTime = 0;
		fuelBurnRate = 0;
		balloonAltitude = 0;
		remainingFuel = 0;
		balloonMass = 0;
		velocity = 0;
		tetherLength = 0;
	}
	
	/**
	 * @return the amount of fuel remaining 
	 */
	public double getFuelRemaining(){
		return remainingFuel;
	}
	
	/**
	 * @param fuel
	 * sets the value of the remainingFuel variable
	 */
	public void setFuelRemaning(double fuel) {
		remainingFuel = fuel;
	}
	
	/**
	 * @return the mass of the balloon
	 */
	public double getBalloonMass() {
		return balloonMass;
	}
	
	/**
	 * @param mass
	 * sets the value of the balloonMass variable
	 */
	public void setBalloonMass(double mass) {
		balloonMass = mass;
	}
	
	/**
	 * @return the outside air temperature
	 */
	public double getOutsideAirTemp() {
		return outsideAirTemp;
	}
	
	/**
	 * @param temp
	 * sets the value of the outsideAirTemp variable
	 */
	public void setOutsideAirTemp(double temp) {
		outsideAirTemp = temp;
	}
	
	/**
	 * @return the fuel burn rate
	 */
	public double getFuelBurnRate() {
		
		return fuelBurnRate;
	}
	
	/**
	 * @param rate
	 * sets the value of the fuelBurnRate variable
	 */
	public void setFuelBurnRate(double rate) {
		fuelBurnRate = rate;
	}
	
	/**
	 * @return the temperature of the balloon
	 * 
	 */
	public double getBalloonTemp() {
		return balloonTemperature;
	}
	
	/**
	 * @param temp
	 * sets the value of the balloonTemperature variable
	 */
	public void setBalloonTemp(double temp) {
		balloonTemperature = temp;
	}
	
	/**
	 * @return the velocity of the balloon
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * @return the altitude of the balloon
	 */
	public double getAltitude() {
		return Math.max(0.0, balloonAltitude);
	}
	
	/**
	 * @return tether length
	 */
	public double getTetherLength() {
		return tetherLength;
	}
	
	/**
	 * @return remaining tether
	 * 
	 */
	public double getTetherRemaining() {
		
		return tetherLength - balloonAltitude;
	}
	
	/**
	 * @param length
	 * Sets the value of the tetherLength variable.
	 */
	public void setTetherLength(double length) {
		tetherLength = length;
	}
	
	/**
	 * @return wind direction
	 */
	public double getWindDirection() {
		return wind;
	}
	
	/**
	 * @param deg
	 * Updates the wind direction by adding the giving value on to the current wind direction.
	 */
	public void changeWindDirection(double deg) {
		
		// adds 360 degrees to the value of wind so the value is always positive
		
		wind = wind + deg;
		wind = (wind+360)%360;
	}
	
	/**
	 * @return the number of minutes passed in the simulation
	 */
	public long getMinutes() {
		long minutes = simulationTime/60;
		return minutes;
	}
	
	/**
	 * @return the number of seconds passed in the simulation
	 */
	public long getSeconds() {
		long seconds = simulationTime%60;
		return seconds;
	}
	
	/**
	 * Calling this method represents 1 second of simulated time passing.  
	 * The fuel remaining is consumed at the set fuel burn rate, but it can never drop below 0. 
	 * If the fuel burn rate is more than the available amount of fuel then consume as much fuel as is available but no more. 
	 * The temperature inside of the balloon is updated 
	 */
	public void update() {
		
		double netAcceleration;
		double densitySurrounding;
		double densityBalloon;
		double forceOfLift;
		double forceOfGravity;
		double netForce;
		double rateOfTempChange;
		
		simulationTime = simulationTime+1;
		fuelBurnRate = Math.min(remainingFuel, fuelBurnRate);
		remainingFuel = Math.max(remainingFuel - (fuelBurnRate * simulationTime),0.0);
		// Value of net acceleration calculated 
		rateOfTempChange = fuelBurnRate + (outsideAirTemp - balloonTemperature)*HEAT_LOSS_FACTOR;
		balloonTemperature = balloonTemperature + rateOfTempChange;
		densitySurrounding = STANDARD_PRESSURE / ((GAS_CONSTANT)*(outsideAirTemp+KELVIN_AT_ZERO_DEGREE_CELSIUS));
		densityBalloon = STANDARD_PRESSURE / ((GAS_CONSTANT)*(balloonTemperature+KELVIN_AT_ZERO_DEGREE_CELSIUS));
		forceOfLift = VOLUME_OF_AIR * (densitySurrounding - densityBalloon) * GRAVITY;
		forceOfGravity = balloonMass * GRAVITY;
		netForce = forceOfLift - forceOfGravity;
		netAcceleration = netForce / balloonMass;
		velocity = velocity + netAcceleration;
		balloonAltitude = balloonAltitude + velocity;


		balloonAltitude = Math.min(tetherLength, balloonAltitude);
		balloonAltitude = Math.max(balloonAltitude, 0);
	}
	
	/**
	 * Calling this method resets the simulation to its initial state 
	 */
	public void reset() {

		outsideAirTemp = INITIAL_AIR_TEMP;
		balloonTemperature = INITIAL_AIR_TEMP;
		wind = INITIAL_WIND_DIRECTION;
		simulationTime = 0;
		fuelBurnRate = 0;
		balloonAltitude = 0;
		remainingFuel = 0;
		balloonMass = 0;
		velocity = 0;
		tetherLength = 0;
	}
}
