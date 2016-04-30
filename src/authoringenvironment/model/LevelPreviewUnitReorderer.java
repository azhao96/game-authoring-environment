package authoringenvironment.model;

import java.util.*;

import authoringenvironment.controller.GameEditingEnvironment;
import authoringenvironment.view.ActorsAndLevelsDisplay;
import authoringenvironment.view.PreviewUnitWithEditable;
import authoringenvironment.view.PreviewUnitWithLevel;
import gameengine.controller.Level;
import gui.view.PopUpParent;
import gui.view.PopUpSimpleErrorMessage;
import javafx.scene.layout.VBox;

/**
 * 
 * This class reorders the levels created as specified by the author. If invalid
 * data is entered, an error message is generated notifying the author.
 * 
 * @author Stephen
 *
 */

public class LevelPreviewUnitReorderer {

	private static final String POPUP_ERROR_MESSAGE = "Invalid Input. Please try again";
	private static final String POPUP_BUTTON_TEXT = "Close Window";
	private static final int POPUP_WIDTH = 250;
	private static final int POPUP_HEIGHT = 100;
	private static final int MINIMUM_PLAY_POSITION = 0;
	private static final int MINIMUM_LIST_INDEX = 0;
	private List<PreviewUnitWithLevel> myLevelPreviewUnits;
	private VBox myPreviewUnitsContainer;
	private List<Level> myLevels;
	private IEditingEnvironment myLevelEditor;
	private List<PreviewUnitWithEditable> myPreviewUnits;
//	private GUIMainScreen myMainScreen;
	private List<Integer> playPositions;
	private Set<Integer> uniquePlayPositions;
	private ActorsAndLevelsDisplay myPreviewUnitsDisplay;
	private GameEditingEnvironment myGameEditor;

//	public LevelPreviewUnitReorderer(List<PreviewUnitWithLevel> levelPreviewUnits, VBox previewUnitsContainer,
//			List<Level> levels, IEditingEnvironment levelEditor, List<PreviewUnitWithEditable> allPreviewUnits,
//			GUIMainScreen mainScreen) {
//		myLevelPreviewUnits = levelPreviewUnits;
//		myPreviewUnitsContainer = previewUnitsContainer;
//		myLevels = levels;
//		myLevelEditor = levelEditor;
//		myPreviewUnits = allPreviewUnits;
////		myMainScreen = mainScreen;
//		initializeErrorChecking();
//	}
	
	public LevelPreviewUnitReorderer(List<Level> levels, IEditingEnvironment levelEditor, ActorsAndLevelsDisplay display, GameEditingEnvironment gameEditor) {
		myLevelPreviewUnits = display.getLevelPreviewUnits();
		myPreviewUnitsContainer = display.getLevelPreviewUnitsContainer();
		myLevels = levels;
		myLevelEditor = levelEditor;
		myPreviewUnits = display.getPreviewUnits();
		myPreviewUnitsDisplay = display;
		myGameEditor = gameEditor;
		initializeErrorChecking();
	}

	/**
	 * Initializes error checking
	 */
	private void initializeErrorChecking() {
		playPositions = new ArrayList<Integer>();
		myLevelPreviewUnits.stream().forEach(display -> playPositions.add(display.getLevelPlayPosition()));
		uniquePlayPositions = new HashSet<Integer>(playPositions);
		Collections.sort(playPositions);
	}

	/**
	 * Updates play positions for each level, sorts the list of levels so that
	 * levels to be played earlier come first, and repopulates the preview unit
	 * container so that preview units with earlier levels appear at the top of
	 * the container
	 */
	public void reorderLevels() {
		if (!validDataEntered()) notifyAuthorOfError();
		myLevelPreviewUnits.stream().forEach(unit -> unit.updateLevelPlayPosition());
		myLevelPreviewUnits.clear();
		myPreviewUnitsContainer.getChildren().clear();
		Collections.sort(myLevels);
		myLevels.stream().forEach(level -> myGameEditor.createLevelPreviewUnit(level, myLevelEditor));
		myPreviewUnits.stream().filter(unit -> myPreviewUnitsContainer.getChildren().contains(unit));
		myPreviewUnitsDisplay.updatePreviewUnits();
	}
	
	/**
	 * Notifies the author that invalid input was entered
	 */
	private void notifyAuthorOfError() {
		@SuppressWarnings("unused")
		PopUpParent errorNotification = new PopUpSimpleErrorMessage(POPUP_WIDTH, POPUP_HEIGHT, POPUP_BUTTON_TEXT,
				POPUP_ERROR_MESSAGE);
	}

	/**
	 * Verifies whether user's input for each level's play position is valid
	 * 
	 * @return true if user input is valid; false otherwise
	 */
	private boolean validDataEntered() {
		return atLeastOneLevelCreated() && lowestPlayPositionEqualsZero() && highestPlayPositionEqualsMaxPlayPosition()
				&& noDuplicatePositionsEntered();
	}

	/**
	 * Verifies that at least one level was created
	 * 
	 * @return true if at least one level created; false otherwise
	 */
	private boolean atLeastOneLevelCreated() {
		return !playPositions.isEmpty();
	}

	/**
	 * Verifies that the lowest play position entered is equal to the lowest
	 * possible play position (1)
	 * 
	 * @return true if lowest play position equals 1; false otherwise
	 */
	private boolean lowestPlayPositionEqualsZero() {
		return playPositions.get(MINIMUM_LIST_INDEX) == MINIMUM_PLAY_POSITION;
	}

	/**
	 * Verifies that the highest play position entered is equal to the highest
	 * possible play possible given the number of levels created
	 * 
	 * @return true if the highest play position entered equals the number of
	 *         levels created; false otherwise
	 */
	private boolean highestPlayPositionEqualsMaxPlayPosition() {
		int highestPossiblePlayPosition = playPositions.size() - 1;
		int indexOfHighestEnteredPlayPosition = playPositions.size() - 1;
		return playPositions.get(indexOfHighestEnteredPlayPosition) == highestPossiblePlayPosition;
	}

	/**
	 * Verifies that no duplicate play positions were entered
	 * 
	 * @return true if no duplicated play positions entered; false otherwise
	 */
	private boolean noDuplicatePositionsEntered() {
		return playPositions.size() == uniquePlayPositions.size();
	}

}