package gui.view;

public class ButtonSound extends ButtonParent{

	public ButtonSound(String buttonText, String imageName) {
		super(buttonText, imageName);
		setName(this.getClass().getSimpleName());
		setClick();
	}

	@Override
	protected void setButtonAction() {
	}

}
