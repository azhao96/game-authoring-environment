package authoringenvironment.model;

import javafx.scene.layout.Pane;

/**
 * This class serves as the interface for all editing environments that edit an IEditableGameElement.
 * It extends the IGUIEditingElement interface and promises that all classes that implement this 
 * (the IEditingEnvironment) interface can have their instance of IEditableGameElement set to a new 
 * IEditableGameElement. This interface also ensures that all classes that implement this interface
 * can have their Pane containing all of the editing environment's GUI elements retrieved. 
 * 
 * @author Stephen
 *
 */

public interface IEditingEnvironment extends IEditingElement {	
	
	/**
	 * 
	 * @return the Pane containing the editing environment's GUI Elements
	 */
	public Pane getPane();

}
