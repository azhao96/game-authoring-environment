package gameengine.model.Actions;

import gameengine.actors.Actor;
import gameengine.controller.Action;

public class HorizontalStaticCollision extends Action{

	public HorizontalStaticCollision(Actor assignedActor) {
		super(assignedActor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		getActor().setXVelo(0);
	}

}

