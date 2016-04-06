package gameengine.model;


import java.util.Set;

/**
 * This interface defines the the public methods for Actor objects. Each Actor will have a position, a number of points, a designated
 * amount of health, and sets of rules. For Rules, each Trigger will have corresponding Action objects associated with it
 * that define the procedure to be performed when the Trigger is set off.
 *
 * @author blakekaplan
 */
public interface IActor {

    /**
     * Gets the Actor's X location
     *
     * @return The Actor's X coordinate
     */
    public double getX();

    /**
     * Gets the Actor's Y location
     *
     * @return The Actor's Y coordinate
     */
    public double getY();
    
    /**
     * Gets the Actor's velocity along the horizontal vector
     * 
     * @return the Actor's X velocity
     */
    public double getXVelo();

    /**
     * Gets the Actor's velocity along the vertical vector
     * 
     * @return the Actor's Y velocity
     */
    public double getYVelo();
    
    /**
     * Sets an Actor's X position
     * @param updateXPosition
     */
	public void setXPos(double updateXPosition); 
	
	/**
     * Sets an Actor's Y position
     * @param updateYPosition
     */
	public void setYPos(double updateYPosition); 
	
	/**
     * Sets an Actor's X velocity
     * @param updateXVelo
     */
	public void setXVelo(double updateXVelo); 
	
	/**
     * Sets an Actor's Y velocity
     * @param updateYVelo
     */
	public void setYVelo(double updateYVelo);
    
    /**
     * Gets the Actor's amount of health
     *
     * @return The Actor's amount of health
     */
    public int getHealth();

    /**
     * Gets the Actor's number of points
     *
     * @return The Actor's number of points
     */
    public int getPoints();

    /**
     * Moves the Actor's current position
     *
     * @param distance  The distance to move the Actor
     * @param direction The direction that the Actor should move in
     */
    public void move(double distance, double direction);

    /**
     * Changes the Actor's number of points
     *
     * @param change The desired change in the Actor's number of points
     */
    public void changePoints(int change);


    /**
     * Changes the Actor's amount of health
     *
     * @param change The desired change in the Actor's amount of health
     */
    public void changeHealth(int change);

    /**
     * Performs the action for a particular provided trigger
     *
     * @param myTrigger A Trigger object that calls for an appropriate response
     */
    public void performActionsFor(ITrigger myTrigger);

    /**
     * Provides the list of Triggers that the Actor responds to
     *
     * @return The list of Triggers
     */
    public Set<String> getTriggers();

    /**
     * Adds a new Rule to the Actor
     *
     * @param newRule The Rule to be added to the Actor
     */
    public void addRule(IRule newRule);

    /**
     * Provides the Actor's ID
     *
     * @return The Actor's ID
     */
    public int getID();
    
    /**
     * Provides Actor type to distinguish between enemy/neutral etc
     * @return The Actor's type
     */
    public String getActorType();

}