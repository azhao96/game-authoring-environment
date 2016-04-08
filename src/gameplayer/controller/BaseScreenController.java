package gameplayer.controller;

import java.util.ResourceBundle;

import gameplayer.view.BaseScreen;
import gui.controller.IScreenController;
import javafx.stage.Stage;

public class BaseScreenController implements IScreenController{

	private Stage myStage;
	private ResourceBundle myResources;
	private BaseScreen myScreen;
	
	public BaseScreenController(Stage myStage, BaseScreen myBase, ResourceBundle myResources) {
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