package authoringenvironment.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import authoringenvironment.model.IAuthoringActor;
import gameengine.model.Actor;
import gameengine.model.AttributeType;
import gameengine.model.IGameElement;
import gameengine.model.IPlayActor;
import gameengine.model.Rule;
import gameengine.model.Actions.Action;
import gameengine.model.Actions.ChangeAttribute;
import gameengine.model.Actions.CreateActor;
import gameengine.model.Triggers.AttributeReached;
import gameengine.model.Triggers.ClickTrigger;
import gameengine.model.Triggers.CollisionTrigger;
import gameengine.model.Triggers.ITrigger;
import gameengine.model.Triggers.KeyTrigger;
import gameengine.model.Triggers.TickTrigger;
import javafx.scene.input.KeyCode;

/**
 * 
 * @author amyzhao
 *
 */
public class ActorCopier {
	private static final String ACTION_DIRECTORY = "gameengine.model.Actions.";
	private static final String TRIGGER_DIRECTORY = "gameengine.model.Triggers.";
	private static final String RESOURCE = "ruleCreator";
	private static final String KEY = "Key";
	private static final String TICK = "Tick";
	private static final String COLLISION = "Collision";
	private static final String CLICK = "Click";
	private static final String ATTRIBUTE = "Attribute";
	private static final String CREATE_ACTOR = "CreateActor";
	private static final String WIN_LOSE = "WinLose";
	private Actor myReferenceActor;
	private ResourceBundle myResources;
	private ActionFactory myActionFactory;
	private TriggerFactory myTriggerFactory;
	
	public ActorCopier() {
		myReferenceActor = null;
		init();
	}

	public ActorCopier(Actor actor) {
		myReferenceActor = actor;
		init();
	}

	public void init() {
		myResources = ResourceBundle.getBundle(RESOURCE);
		myActionFactory = new ActionFactory();
		myTriggerFactory = new TriggerFactory();
	}
	public void setReferenceActor(Actor newReference) {
		myReferenceActor = newReference;
	}

	public IAuthoringActor getReferenceActor() {
		return myReferenceActor;
	}

	public Actor makeCopy() {
		Actor actor = new Actor();
		if (myReferenceActor != null) {
			copyActor(actor, myReferenceActor);
			return actor;
		} else {
			return null;
		}
	}

	// not sure how to make this nicer yet
	public void copyActor(Actor toUpdate, Actor toCopy) {
		toUpdate.setName(toCopy.getName());
		toUpdate.setFriction(toCopy.getFriction());
		toUpdate.setImageViewName(toCopy.getImageViewName());
		toUpdate.setSize(toCopy.getSize());
		toUpdate.setID(toCopy.getID());
		copyRules(toUpdate, toCopy.getRules());
	}

	// work in progress.. currently only works for KeyTriggers and Move actions
	private void copyRules(Actor toUpdate, Map<String, List<Rule>> rulesToCopy) {
		toUpdate.getRules().clear();
		for (String trigger : rulesToCopy.keySet()) {
			List<Rule> toAdd = rulesToCopy.get(trigger);
			for (int i = 0; i < toAdd.size(); i++) {
				try {
					ITrigger triggerToAdd = createTrigger(toAdd.get(i), (IPlayActor) toUpdate);
					Action actionToAdd = createAction(toAdd.get(i), (IPlayActor) toUpdate);
					//for colettes test with tick
					/*if (toAdd.get(i).getMyTrigger().getClass().equals(TickTrigger.class)){
						ITrigger trigger1 = toAdd.get(i).getMyTrigger();

						String actionName = toAdd.get(i).getMyAction().getClass().getName();
						Class<?> actionClassName = Class.forName(actionName);
						Constructor<?> actionConstructor = actionClassName.getConstructor(IPlayActor.class);
						Action actionToAdd = (Action) actionConstructor.newInstance((IPlayActor) toUpdate);

						toUpdate.addRule(new Rule(trigger1,actionToAdd));

					}else{
					className = Class.forName(triggerName);
					Constructor<?> triggerConstructor = className.getConstructor(KeyCode.class);
					KeyCode key = ((KeyTrigger) toAdd.get(i).getMyTrigger()).getMyKeyCode();
					ITrigger triggerToAdd = (KeyTrigger) triggerConstructor.newInstance(key);


					String actionName = toAdd.get(i).getMyAction().getClass().getName();
					Class<?> actionClassName = Class.forName(actionName);
					Constructor<?> actionConstructor = actionClassName.getConstructor(IPlayActor.class);
					Action actionToAdd = (Action) actionConstructor.newInstance((IPlayActor) toUpdate);
					 */
					Rule rule = new Rule(triggerToAdd, actionToAdd);
					rule.setID(toAdd.get(i).getID() + 1);
					toUpdate.addRule(rule);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}


	}

	private ITrigger createTrigger(Rule rule, IPlayActor toUpdate) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ITrigger triggerToAdd = null;
		String triggerClassName = rule.getMyTrigger().getClass().getSimpleName();
		List<Object> arguments = new ArrayList<>();
		if (checkType(triggerClassName, KEY)) {
			arguments.add(((KeyTrigger) rule.getMyTrigger()).getMyKeyCode());
			triggerClassName = KEY; // ideally won't need this...
		} else if (checkType(triggerClassName, TICK)) {
			arguments.add(((TickTrigger) rule.getMyTrigger()).getMyInterval());
			triggerClassName = TICK;
		} else if (checkType(triggerClassName, COLLISION)) {
			arguments.add(toUpdate);
			arguments.add(((CollisionTrigger) rule.getMyTrigger()).getMyCollisionActor());
		} else if (checkType(triggerClassName, ATTRIBUTE)) {
			AttributeReached trigger = (AttributeReached) rule.getMyTrigger();
			arguments.add(trigger.getMyType());
			arguments.add((IGameElement) toUpdate);
			arguments.add(trigger.getMyValue());
		} else if (checkType(triggerClassName, CLICK)) {
			arguments.add((IGameElement) toUpdate);
			triggerClassName = CLICK;
		}
		triggerToAdd = myTriggerFactory.createNewTrigger(triggerClassName, arguments);
		return triggerToAdd;
	}
	
	private Action createAction(Rule rule, IPlayActor toUpdate) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Action actionToAdd = null;
		String actionClassName = rule.getMyAction().getClass().getSimpleName();
		List<Object> arguments = new ArrayList<>();
		if (checkType(actionClassName, ATTRIBUTE)) {
			ChangeAttribute action = (ChangeAttribute) rule.getMyAction();
			arguments.add((IGameElement) toUpdate);
			arguments.add(action.getMyType());
			arguments.add(action.getMyValue());
		} else if (checkType(actionClassName, CREATE_ACTOR)) {
			CreateActor action = (CreateActor) rule.getMyAction();
			arguments.add(toUpdate);
			arguments.add(action.getMyActorToCopy());
			arguments.add(action.getMyX());
			arguments.add(action.getMyY());
		} else if (checkType(actionClassName, WIN_LOSE)) {
			arguments.add((IGameElement) toUpdate);
		} else {
			arguments.add(toUpdate);
		}
		actionToAdd = (Action) myActionFactory.createNewAction(actionClassName, arguments);
		return actionToAdd;
	}

	/*private Action createAction(Rule rule, IPlayActor toUpdate) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Action actionToAdd = null;
		String actionClassName = rule.getMyAction().getClass().getName();
		Class<?> actionClass = Class.forName(actionClassName);
		//String actionClassName = getClassName(fullActionName);
		if (checkType(actionClassName, ATTRIBUTE)) {
			Constructor<?> actionConstructor = actionClass.getConstructor(IGameElement.class, AttributeType.class, int.class);
			ChangeAttribute action = (ChangeAttribute) rule.getMyAction();
			actionToAdd = (ChangeAttribute) actionConstructor.newInstance(toUpdate, action.getMyType(), action.getMyValue());
		} else if (checkType(actionClassName, CREATE_ACTOR)) {
			Constructor<?> actionConstructor = actionClass.getConstructor(IPlayActor.class, Actor.class, double.class, double.class);
			CreateActor action = (CreateActor) rule.getMyAction();
			actionToAdd = (CreateActor) actionConstructor.newInstance(toUpdate, action.getMyActorToCopy(), action.getMyX(), action.getMyY());
		} else if (checkType(actionClassName, WIN_LOSE)) {
			Constructor<?> actionConstructor = actionClass.getConstructor(IGameElement.class);
			actionToAdd = (Action) actionConstructor.newInstance((IGameElement) toUpdate);
		} else {
			Constructor<?> actionConstructor = actionClass.getConstructor(IPlayActor.class);
			actionToAdd = (Action) actionConstructor.newInstance(toUpdate);
		}
		return actionToAdd;
	}*/

	private boolean checkType(String name, String key) {
		if (Arrays.asList(myResources.getString(key).split(",")).contains(name)) {
			return true;
		}
		return false;
	}
	
}
