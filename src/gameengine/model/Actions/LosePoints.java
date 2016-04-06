package gameengine.model.Actions;

import gameengine.controller.Action;
import gameengine.model.Actor;

public class LosePoints extends Action{

	private int numPointsLost = 1;
	
	public LosePoints(Actor assignedActor) {
		super(assignedActor);
	}

	@Override
	public void perform() {
		getActor().setPoints(getActor().getPoints()-numPointsLost);
	}

	//this is enemy, a is character
	@Override
	public void performOn(Actor a) {
		a.setPoints(a.getPoints()-numPointsLost);
		
	}

}