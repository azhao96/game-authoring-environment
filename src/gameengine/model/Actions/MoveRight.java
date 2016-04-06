package gameengine.model.Actions;

import gameengine.controller.Action;
import gameengine.model.Actor;

import java.util.List;

/**
 * An example of an Action to move an Actor right by a given distance.
 *
 * @author blakekaplan
 */
public class MoveRight extends MovingAction {

    private static final int RIGHT_ANGLE = 0;

    /**
     * Takes in reference to the Actor it will change along with the argument it will require to do so
     *
     * @param assignedActor The Actor that will be changed
     * @param args          The arguments required to perform the change
     */
    public MoveRight(Actor assignedActor, List<Object> args) {
        super(assignedActor, args);
    }

    /**
     * Moves the Actor to the right by the distance provided in the arguments
     */
    @Override
    public void perform() {
        moveActor(RIGHT_ANGLE);
    }

	@Override
	public void performOn(Actor a) {
		// TODO Auto-generated method stub
		
	}
}