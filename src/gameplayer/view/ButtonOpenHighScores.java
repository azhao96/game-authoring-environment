package gameplayer.view;

import gameplayer.controller.SplashScreenController;
import gui.controller.IScreenController;
import gui.view.ButtonParent;

public class ButtonOpenHighScores extends ButtonParent{
	
	private SplashScreenController myControl;

	public ButtonOpenHighScores(IScreenController myController, String buttonText, String imageName) {
		super(myController, buttonText, imageName);
		this.myControl = (SplashScreenController) myController;
	}

	@Override
	protected void setButtonAction() {
		button.setOnAction(e -> myControl.openHighScores());
		
	}

}
