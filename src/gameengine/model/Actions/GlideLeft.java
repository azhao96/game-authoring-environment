package gameengine.model.Actions;

import gameengine.model.Actor;
import gameengine.model.IPlayActor;

public class GlideLeft extends GlidingAction {

	public GlideLeft(Actor assignedActor, double offset) {
		super(assignedActor, offset);
	}

	@Override
	public void perform() {
    	getMyActor().getPhysicsEngine().glideLeft(getMyActor(),this.getGlideOffset());			

	}

}
