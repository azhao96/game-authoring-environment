package authoringenvironment.model;

import java.util.List;

import authoringenvironment.view.ActorRule;
import gameengine.model.ActorState;
import gameengine.model.Attribute;
import gameengine.model.Rule;
import javafx.scene.image.ImageView;


/**
 * This interface defines the subset of Actor functionality that will be accessible to the game authoring environment.
 * We decided to make a separate interface since the authoring environment has different functionality requirements.
 *
 * @author blakekaplan
 */
public interface IAuthoringActor extends IEditableGameElement{
	/**
     * Adds a new Rule to the Actor
     *
     * @param newRule The Rule to be added to the Actor
     */
	void addRule(Rule newRule);
    /**
     * Sets the Actor's X coordinate
     * @param updateXPosition   The new X coordinate
     */
    void setX(double updateXPosition);
    /**
     * Sets the Actor's Y position
     * @param updateYPosition   The new Y position
     */
    void setY(double updateYPosition);
    /**
     * Provides the Actor's name
     * @return  The Actor's name
     */
    String getName();
    /**
     * Sets a new Actor name
     * @param name  The new Actor name
     */
    void setName(String name);
    /**
     * Provides the Actor's Imageview
     * @return  The Actor's Imageview
     */
    ImageView getImageView();
    /**
     * Sets a new Actor ImageView
     * @param imageView The new ImageView
     */
    void setImageView(ImageView imageView);
    /**
     * Provides the Actor's X coordinate
     * @return  The Actor's X coordinate
     */
    double getX();
    /**
     * Provides the Actor's Y coordinate
     * @return  The Actor's Y coordinate
     */
    double getY();
    /**
     * Provides the Actor's ImageView
     * @return  The Actor's ImageView
     */
    String getImageViewName();
    /**
     * Sets the name of the Actor's ImageView
     * @param imageViewName   The Actor's ImageView
     */
    void setImageViewName(String imageViewName);
    /**
     * Sets the Actor's ImageView's size
     * @param size  The ImageView's size
     */
    void setSize(double size);

    /**
     * Provides the List of the Actor's Rules
     * @return  The Actor's ActorRules
     */
    List<ActorRule> getActorRules();

	void setFriction(double parseDouble);
	
	/**
	 * @return the myFriction
	 */
	double getFriction();

	int getMyID();

	void addState(ActorState state);

	boolean checkState(ActorState main);

	double getSize();

	void setID(int ID);

    void addAttribute(Attribute newAttribute);

    void addSpriteImage(String newImage);

}
