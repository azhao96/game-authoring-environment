package gameplayer.controller;

import java.util.ResourceBundle;
import gameplayer.view.HighScoreScreen;
import gui.controller.IScreenController;
import javafx.stage.Stage;

public class HighScoreScreenController implements IScreenController {

	private Stage myStage;
	private ResourceBundle myResources;
	private HighScoreScreen myScreen;
	
	public HighScoreScreenController(Stage myStage, HighScoreScreen myBase, ResourceBundle myResources) {
		this.myStage = myStage;
		this.myResources = myResources;
		this.myScreen = myBase;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stage getStage() {
		// TODO Auto-generated method stub
		return null;
	}

}