package gameengine.controller;

import gameengine.model.Actor;
import gameengine.model.IAuthoringActor;
import gameengine.model.IPlayActor;
import gameengine.model.ITrigger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import authoringenvironment.model.IEditableGameElement;

/**
 * A Level is essentially a package of Actor objects. It is able to relay a Trigger to Actors when it receives one.
 *
 * @author blakekaplan
 */
public class Level implements ILevel, IEditableGameElement {

	// TODO: should probably set these default things via properties file but idk sry guyz
	private static final String DEFAULT_NAME = "Default";
	private static final String DEFAULT_IMAGE_NAME = "default_landscape.png";
	private static final double DEFAULT_HEIGHT = 800;
	private static final double DEFAULT_WIDTH = 1024;
	private static final String DEFAULT_SCROLLING = "Vertically";
	private static final String DEFAULT_TERMINATION = "Infinite";
	private static final String DEFAULT_WINNING_CONDITION = "Survival time";
	private static final String DEFAULT_LOSING_CONDITION = "Player dies";
    private List<IPlayActor> myActors;
    private Map<String, List<IPlayActor>> myTriggerMap;
    private String myName;
    private double myHeight;
    private double myWidth;
    private List<String> myHUDOptions;
    private String myScrollingDirection;
    private String myTermination;
    private String myWinningCondition;
    private String myLosingCondition;
    private String myBackgroundImgName;
	@XStreamOmitField
    private ImageView myBackground;


    /**
     * Instantiates the triggerMap and Actor list
     */
    public Level() {
        setMyActors(new ArrayList<>());
        setMyTriggerMap(new HashMap<>());
        setMyName(DEFAULT_NAME);
        myBackgroundImgName = DEFAULT_IMAGE_NAME;
        setImageView(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(myBackgroundImgName))));
        myTermination = DEFAULT_TERMINATION;
        myScrollingDirection = DEFAULT_SCROLLING;
        myWinningCondition = DEFAULT_WINNING_CONDITION;
        myLosingCondition = DEFAULT_LOSING_CONDITION;
        myName = DEFAULT_NAME;
        myHeight = DEFAULT_HEIGHT;
        myWidth = DEFAULT_WIDTH;
    }

    /**
     * Calls for the appropriate response upon receiving a particular Trigger
     *
     * @param myTrigger A particular Trigger object sent from the game player
     */
    @Override
    public void handleTrigger(ITrigger myTrigger) {
        if (!getMyTriggerMap().containsKey(myTrigger.getMyKey())) return;
        List<IPlayActor> relevantActors = getMyTriggerMap().get(myTrigger.getMyKey());
        for (IPlayActor myActor : relevantActors) {
            if (myTrigger.evaluate(myActor)){
                myActor.performActionsFor(myTrigger.getMyKey());
            }
        }
    }

    /**
     * Sets the Level's name
     *
     * @param name A name for the Level
     */
    @Override
    public void setMyName(String name) {
        this.myName = name;
    }

    public void setWidth(double width) {
    	myWidth = width;
    }
    
    public double getWidth() {
    	return myWidth;
    }
    
    public void setHeight(double height) {
    	myHeight = height;
    }
    
    public double getHeight() {
    	return myHeight;
    }
    
    public void setHUDOptions(List<String> options) {
    	myHUDOptions = options;
    }
    
    public List<String> getHUDOption() {
    	return myHUDOptions;
    }
    
    public void setScrollingDirection(String scrollingDirection) {
    	myScrollingDirection = scrollingDirection;
    }

    public String getScrollingDirection() {
    	return myScrollingDirection;
    }
    
    public void setTermination(String termination) {
    	myTermination = termination;
    }
    
    public String getTermination() {
    	return myTermination;
    }
    
    public void setWinningCondition(String winningCondition) {
    	myWinningCondition = winningCondition;
    }
    
    public String getWinningCondition() {
    	return myWinningCondition;
    }
    
    public void setLosingCondition(String losingCondition) {
    	myLosingCondition = losingCondition;
    }
    
    public String getLosingCondition() {
    	return myLosingCondition;
    }
    
    /**
     * Adds a new Actor to the Level and updates the triggerMap accordingly
     *
     * @param newActor The Actor to be added to the Level
     */
    @Override
    public void addActor(IAuthoringActor newActor) {
        getActors().add((IPlayActor)newActor);
        Set<String> actorTriggers = ((IPlayActor)newActor).getMyRules().keySet();
        for (String myTrigger : actorTriggers) {
            if (getMyTriggerMap().containsKey(myTrigger)) {
                List<IPlayActor> levelActors = getMyTriggerMap().get(myTrigger);
                levelActors.add((IPlayActor)newActor);
                getMyTriggerMap().put(myTrigger, levelActors);
            } else {
                List<IPlayActor> levelActors = new ArrayList<>();
                levelActors.add((IPlayActor)newActor);
                getMyTriggerMap().put(myTrigger, levelActors);
            }
        }
        
    }

    /**
     * Provides the Level's name
     *
     * @return The Level's name
     */
    @Override
    public String getMyName() {
        return myName;
    }

	@Override
	public ImageView getImageView() {
		return myBackground;
	}

	@Override
	public void setImageView(ImageView imageView) {
		myBackground = imageView;
	}

	@Override
	public void setMyID(int ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMyID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
    public String getMyBackgroundImgName() {
		return myBackgroundImgName;
	}

	public void setMyBackgroundImgName(String myBackgroundImgName) {
		this.myBackgroundImgName = myBackgroundImgName;
	}
	
	public String toString() {

	      StringBuilder stringBuilder = new StringBuilder();
	      
	      stringBuilder.append("\nLevel [ ");
	      stringBuilder.append("\nmyName: ");
	      stringBuilder.append(getMyName());
	      stringBuilder.append("\nbckImg: ");
	      stringBuilder.append(myBackgroundImgName);
	      stringBuilder.append("\nmyActors: ");
	      stringBuilder.append(getActors().toString());
	      stringBuilder.append("\nTriggerMap: ");
	      stringBuilder.append(getMyTriggerMap().toString());
	      stringBuilder.append("\nimg: ");
	      stringBuilder.append(myBackground);
	      stringBuilder.append(" ]");
	      
	      return stringBuilder.toString();
	}

	public Map<String, List<IPlayActor>> getMyTriggerMap() {
		return myTriggerMap;
	}

	public void setMyTriggerMap(Map<String, List<IPlayActor>> myTriggerMap) {
		this.myTriggerMap = myTriggerMap;
	}

	public List<IPlayActor> getActors() {
		return myActors;
	}

	public void setMyActors(List<IPlayActor> myActors) {
		this.myActors = myActors;
	}

	public double getMyHeight() {
		return myHeight;
	}

	public void setMyHeight(double myHeight) {
		this.myHeight = myHeight;
	}

	public double getMyWidth() {
		return myWidth;
	}

	public void setMyWidth(double myWidth) {
		this.myWidth = myWidth;
	}

	public List<String> getMyHUDOptions() {
		return myHUDOptions;
	}

	public void setMyHUDOptions(List<String> myHUDOptions) {
		this.myHUDOptions = myHUDOptions;
	}

	public String getMyScrollingDirection() {
		return myScrollingDirection;
	}

	public void setMyScrollingDirection(String myScrollingDirection) {
		this.myScrollingDirection = myScrollingDirection;
	}

	public String getMyTermination() {
		return myTermination;
	}

	public void setMyTermination(String myTermination) {
		this.myTermination = myTermination;
	}

	public String getMyWinningCondition() {
		return myWinningCondition;
	}

	public void setMyWinningCondition(String myWinningCondition) {
		this.myWinningCondition = myWinningCondition;
	}

	public String getMyLosingCondition() {
		return myLosingCondition;
	}

	public void setMyLosingCondition(String myLosingCondition) {
		this.myLosingCondition = myLosingCondition;
	}
	
	public void removeActors(List<IPlayActor> deadActors) {
		myActors.removeAll(deadActors);
	}

}

