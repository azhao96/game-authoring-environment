package authoringenvironment.view.behaviors;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

import authoringenvironment.model.IAuthoringActor;
import authoringenvironment.view.ActorRule;
import gameengine.model.ITrigger;

public class SelfTriggerBehavior extends LabelBehavior {
	private ITrigger myTrigger;
	private IAuthoringActor myActor;
	
	public SelfTriggerBehavior(ActorRule myActorRule, IAuthoringActor myActor, String behaviorType, ResourceBundle myResources) {
		super(myActorRule, behaviorType, myResources);
		this.myActor = myActor;
	}

	@Override
	public void setTriggerOrAction() {
		addTrigger(this, myTrigger);
	}

	@Override
	public void addNodeObserver(Observer observer) {
	}

	@Override
	protected void createTriggerOrAction() {
		List<Object> arguments = new ArrayList<>();
		arguments.add(myActor);
		myTrigger = getTriggerFactory().createNewTrigger(getBehaviorType(), arguments);
	}

	@Override
	public boolean isTrigger() {
		return true;
	}

}
