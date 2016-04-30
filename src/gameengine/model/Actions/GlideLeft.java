package gameengine.model.Actions;

import gameengine.model.Actor;

public class GlideLeft extends GlidingAction {

	public GlideLeft(Actor assignedActor, Double offset) {
		super(assignedActor, offset);
	}

	@Override
	public void perform() {
		getMyActor().setHeading(180);
		getMyActor().setDirection();

    	getMyActor().getPhysicsEngine().glideLeft(getMyActor(),this.getGlideOffset());			

	}

}
