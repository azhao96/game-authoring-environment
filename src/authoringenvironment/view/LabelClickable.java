package authoringenvironment.view;

import authoringenvironment.controller.Controller;
import authoringenvironment.model.IEditableGameElement;
import authoringenvironment.model.IEditingEnvironment;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Stephen
 *
 */

public class LabelClickable extends Label {
	
	private static final String IMAGE_TEXT_PADDING = "    ";
	private static final Double FIT_SIZE = 75.0;
	private static final Double LABEL_PADDING = 10.0;
	
	IEditableGameElement myEditable;
	IEditingEnvironment myEnvironment;
	Controller controller;
	
	public LabelClickable(IEditableGameElement editable, IEditingEnvironment environment, Controller controller) {
		this.myEditable = editable;
		this.controller = controller;
		this.myEnvironment = environment;
		this.setOnMouseClicked(e -> reactToMouseClicked());
		this.setStyle("-fx-border-color: black;");
	}
	
	private void reactToMouseClicked() {
		controller.goToEditingEnvironment(myEditable, myEnvironment);
	}
	
	protected IEditableGameElement getEditable() {
		return myEditable;
	}
	
	protected void update() {
		this.setText(IMAGE_TEXT_PADDING + myEditable.getName());
		ImageView imageView = new ImageView(myEditable.getImage());
		imageView.setFitHeight(FIT_SIZE);
		imageView.setFitWidth(FIT_SIZE);
		Insets insets = new Insets(LABEL_PADDING);
		this.setPadding(insets);
		this.setGraphic(imageView);
	}

}
