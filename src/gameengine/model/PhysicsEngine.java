package gameengine.model;


/**
 * Physics Engine Class
 * Handles calculating/assigning new positions based on positional attributes and movement vectors
 * Receives Actor object via movement specific methods (ie moveRight, jump)
 * These methods then apply a set force that affects and updates the Actor's 
 * position and velocity variables
 * 
 * @author justinbergkamp
 */


public class PhysicsEngine {
	
	
	//These Variable values are arbitrary, chosen by trial/error
	private int    timeStep         =  1;    //Arbitrary timeStep, will be set to the time provided by step()
	private double friction         = -.05;  //Horizontal acceleration dampening (friction) coefficient
	private double gravity          = .11 ;  //Falling acceleration coefficient
	private double maxHorizVelocity = 50;    //maximum horizontal velocity
	private double maxVertVelocity  = -50;   //maximum vertical velocity 
	private double horizontalForce  = 5;     //Force applied to Actors on horizontal movements
	private double jumpForce        = -5;    //Vertical Force applied to Actors on jump movements
	private double floorHeight      =  500;  
	
	public PhysicsEngine(){
		
	}
	
	
	/**
	 * This method updates a given velocity by applying a given force
	 * @param velo
	 * @param force
	 * @return updated velo
	 */
	private double applyForce(double velo, double force){  //Applies a force(acceleration because mass =1) to a velocity
		double nextVelo = velo + force*getTimeStep();
		return nextVelo;
	}
	
	/**
	 * This method updates a given position by adding a velocity value scaled by a timeStep
	 * @param pos
	 * @param velo
	 * @return updated position
	 */
	private double changePos(double pos, double velo){
		double nextPos = pos + velo*getTimeStep();
		return nextPos;
	}
	
	
	/**
	 * Only want friction to occur on the ground, only want gravity to apply in the air
	 * @param iPlayActor
	 * @param forceX
	 * @param forceYupward
	 * @param forceYdownward
	 * @param friction
	 */
	private void update(IPlayActor iPlayActor,double forceX, double forceYupward, double forceYdownward, double friction){
		double xVelo     = iPlayActor.getVeloX();
		double yVelo     = iPlayActor.getVeloY();
		double xPos      =  iPlayActor.getX();      
		double yPos      =  iPlayActor.getY();
		double nextHorzVelo;
		double nextVertVelo;
		double nextXPos;
		double nextYPos;
		

		if (iPlayActor.isInAir()) {
			forceYdownward = getGravity();
		}
				
		nextHorzVelo = xVelo;      		
		nextVertVelo = applyForce(yVelo, forceYupward);            // Apply  y force from movement action to y velocity
		nextVertVelo = applyForce(nextVertVelo, forceYdownward);    //Apply gravitational force
		nextYPos     = changePos(yPos, nextVertVelo); 
		nextVertVelo = maxLimit(nextVertVelo, getMaxVertVelocity());
			
//		if(nextYPos+iPlayActor.getBounds().getHeight() > getFloorHeight()){                    //Collision detection for the actor and the ground
//			nextYPos = getFloorHeight()-iPlayActor.getBounds().getHeight();				//TODO: delete this if statement after the floor is implemented as an actor
//			nextVertVelo = 0;
//		}

		
		nextHorzVelo = applyForce(xVelo, forceX); 							// Apply  y force from movement action to y velocity
		nextHorzVelo = applyForce(nextHorzVelo, (friction*(nextHorzVelo))); //Apply frictional force
		nextXPos  = changePos(xPos,nextHorzVelo);
		nextHorzVelo = maxLimit(nextHorzVelo, getMaxHorizVelocity());
		setValues(iPlayActor,  nextHorzVelo,  nextVertVelo,  nextXPos,  nextYPos );	
	}
	
	/**
	 * Sets position and velocity variables for an Actor
	 * 
	 * @param iPlayActor
	 * @param nextHorzVelo
	 * @param nextVertVelo
	 * @param nextXPos
	 * @param nextYPos
	 */
	private void setValues(IPlayActor iPlayActor, double nextHorzVelo, double nextVertVelo, double nextXPos, double nextYPos){
		iPlayActor.setVeloX(nextHorzVelo);
		iPlayActor.setVeloY(nextVertVelo);
		iPlayActor.setX(nextXPos);
		iPlayActor.setY(nextYPos);	
	}
	
	private double maxLimit(double vector, double limit){
		double v = Math.abs(vector);
		if( v > Math.abs(limit)){
			return limit;
		}
		return vector;
	}
	
	//These methods correspond to Actions that call them
	//They differ in the force applied to the Actor
	
	public void moveRight(IPlayActor iPlayActor) {
		update(iPlayActor,getHorizontalForce(), 0, 0, iPlayActor.getMyFriction());
	}

	public void moveLeft(IPlayActor iPlayActor) {
		update(iPlayActor,-getHorizontalForce(), 0, 0, iPlayActor.getMyFriction());
	}
	
	public void jump(IPlayActor iPlayActor){
		update(iPlayActor,0,getJumpForce(), getGravity(), iPlayActor.getMyFriction());
	}
	//gliding methods for when force and gravity aren't applied
	
	public void glideRight(IPlayActor iPlayActor) {
		iPlayActor.setX(iPlayActor.getX()+5);
	}

	public void glideLeft(IPlayActor iPlayActor) {
		iPlayActor.setX(iPlayActor.getX()-5);
	}
	
	public void glideUp(IPlayActor iPlayActor ){
		iPlayActor.setY(iPlayActor.getY()+5);
	}
	
	public void tick(IPlayActor iPlayActor) {
		update(iPlayActor,0.0,0.0, getGravity(), friction);
	}
	
	public void staticHorizontalCollision(Actor a1, Actor a2) {
		if(a1.getVeloX() != 0){     					//If the object is moving 
			if(a1.getX() <  a2.getX()){ 			 //if the collision is occuring on the left side
				a1.setX(a2.getX()-a1.getBounds().getWidth());  //Offset x value to the left
			}else{ 										//If collision is happening from right
				a1.setX(a2.getBounds().getMaxX());	//Offset x value to the right		
			}
			a1.setVeloX(0);                             //Stop movement
		}
	}

	public void staticVerticalCollision(Actor a1, Actor a2) {
		if(a1.getVeloY() != 0){     					//If the object is moving 
			if(a1.getY() <= a2.getY()){ 			 //if the collision is occuring on the top side
				a1.setY(a2.getY()-a1.getBounds().getWidth());  //Offset y value up
			}else{ 										//If collision is happening from right
				a1.setY(a2.getBounds().getMaxY());	//Offset x value to the right		
			}
			a1.setVeloY(0);                             //Stop movement
		}
	}
	
	public void elasticVerticalCollision(Actor a1, Actor a2){
		staticVerticalCollision(a1,a2);
		a1.setVeloY(-5);
	}

	public void elasticHorizontalCollision(Actor a1, Actor a2){
		staticHorizontalCollision(a1,a2);
		a1.setVeloX(-3);
	}


	public int getTimeStep() {
		return timeStep;
	}


	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}


	public double getFriction() {
		return friction;
	}


	public void setFriction(double friction) {
		this.friction = friction;
	}


	public double getGravity() {
		return gravity;
	}


	public void setGravity(double gravity) {
		this.gravity = gravity;
	}


	public double getMaxHorizVelocity() {
		return maxHorizVelocity;
	}


	public void setMaxHorizVelocity(double maxHorizVelocity) {
		this.maxHorizVelocity = maxHorizVelocity;
	}


	public double getMaxVertVelocity() {
		return maxVertVelocity;
	}


	public void setMaxVertVelocity(double maxVertVelocity) {
		this.maxVertVelocity = maxVertVelocity;
	}


	public double getHorizontalForce() {
		return horizontalForce;
	}


	public void setHorizontalForce(double horizontalForce) {
		this.horizontalForce = horizontalForce;
	}


	public double getJumpForce() {
		return jumpForce;
	}


	public void setJumpForce(double jumpForce) {
		this.jumpForce = jumpForce;
	}


	public double getFloorHeight() {
		return floorHeight;
	}


	public void setFloorHeight(double floorHeight) {
		this.floorHeight = floorHeight;
	}

	
}