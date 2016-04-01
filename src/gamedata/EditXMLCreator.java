package gamedata;

import java.util.List;
import java.util.Map;

import authoringenvironment.model.ICreatedActor;

/** 
 * This class serves as the private interface that any Game Data xml creator writing editable game files must implement in order to write data to an initial XML file from objects belonging to the authoring environment.
 * @author cmt57
 */

public interface EditXMLCreator {
	
	
	/**
	 * Writes to an XML file a piece of information pertinent to an level's settings.
	 * @param levelInfoTag a string representing the moniker for a specific level setting
	 * @param levelInfoValue a string representing the value of a specific level setting 
	 */
	public void writeLevelInfo (String levelInfoTag, String levelInfoValue);
	
	/**
	 * Writes to an XML file all the information pertinent to an actor's settings.
	 * @param actor an instance of a level's actor
	 */
	public void writeActorInfo (ICreatedActor actor);
	
	/**
	 * Saves all relevant information for each level in a specific format reflected at a basic level in initialGame.XML.
	 * @param levelInfo a map of tags referring to level settings matched to their values
	 * @param levelActors a list of actors belonging to a level's editing environment
	 */
	public void saveLevel (Map<String, String> levelInfo, List<ICreatedActor> levelActors);

}
