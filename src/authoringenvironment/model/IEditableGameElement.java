package authoringenvironment.model;

import javafx.scene.image.Image;

/**
 * 
 * Interface for all game elements that can be edited in Game Authoring Environment (actors and levels)
 * 
 * @author Stephen
 *
 */

public interface IEditableGameElement {
	
	public void setName(String name);

	public String getName();
	
	public Image getImage();

	public void setImage(Image image);
	
	public void setID(int ID);
	
	public int getID();
}
