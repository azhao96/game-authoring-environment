package authoringenvironment.view.behaviors;

import java.util.ResourceBundle;

import authoringenvironment.view.ActionFactory;
import authoringenvironment.view.TriggerFactory;
import gameengine.model.IAction;
import gameengine.model.ITrigger;
import gui.view.TextFieldWithButton;
/**
 * GUI representation of behaviors that take in a single Double as a parameter
 * @author AnnieTang
 */
//remove abstract and make TickBehavior? Tick only behavior that needs textfield input atm

public abstract class DoubleBehavior extends TextFieldWithButton implements IAuthoringRule{
	private static final String LABEL = "Label";
	private static final String PROMPT = "Prompt";
	private static final String WIDTH = "Width";
	private double value;
	private TriggerFactory triggerFactory;
	private ActionFactory actionFactory;
	private String behaviorType;
	
	public DoubleBehavior(String behaviorType, ResourceBundle myResources) {
		super(myResources.getString(behaviorType+LABEL), 
				myResources.getString(behaviorType+PROMPT), Double.parseDouble(myResources.getString(behaviorType+WIDTH)));
		this.behaviorType = behaviorType;
		this.triggerFactory = new TriggerFactory();
		this.actionFactory = new ActionFactory();
		setButtonAction(event -> {
			this.value = Double.parseDouble(getTextFieldInput());
			createRuleTriggerOrAction();
		});
	}
	/**
	 * Return Double quantity value
	 * @return
	 */
	public double getValue(){
		return value;
	}
	
	protected String getBehaviorType(){
		return this.behaviorType;
	}

	@Override
	protected void updateValueBasedOnEditable(){	
		setTextFieldValue(String.valueOf(value));
	}

	protected TriggerFactory getTriggerFactory() {
		return this.triggerFactory;
	}

	protected ActionFactory getActionFactory() {
		return this.actionFactory;
	}

	abstract void createRuleTriggerOrAction();

	@Override
	public abstract IAction getAction();

	@Override
	public abstract ITrigger getTrigger();
}
