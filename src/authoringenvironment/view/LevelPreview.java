package authoringenvironment.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import authoringenvironment.model.IAuthoringActor;
import authoringenvironment.model.ImageEditingEnvironmentWithActor;
import gameengine.controller.Level;
import gameengine.model.Actor;
import gameengine.model.IPlayActor;
import gui.view.IGUI;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LevelPreview implements IGUI, Observer {
	private static final Color DEFAULT_COLOR = Color.CORNFLOWERBLUE;
	private static final String VERTICAL = "Vertically";
	private static final double SUBSCENE_HEIGHT = 500; // 700 * 3/4
	private static final double SUBSCENE_WIDTH = 1000;
	private static final int POP_UP_WIDTH = 300;
	private static final int POP_UP_HEIGHT = 100;
	private Pane myLevelPane;
	private StackPane myStackPane;
	private ScrollPane myScrollPane;
	private Pane myPane;
	private Level myLevel;
	private ImageView myLevelBackground;
	private List<ImageviewActorIcon> myActorPreviews;
	private LevelEditingEnvironment myLevelEditingEnvironment;

	public LevelPreview(LevelEditingEnvironment levelEditor) {
		myActorPreviews = new ArrayList<>();
		myLevelEditingEnvironment = levelEditor;
		init();
	}

	private void init() {
		myPane = new Pane();
		myPane.setBackground(new Background(new BackgroundFill(DEFAULT_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		myPane.setMinWidth(SUBSCENE_WIDTH);
		myPane.setMinHeight(SUBSCENE_HEIGHT);
		myPane.setMaxWidth(SUBSCENE_WIDTH);
		myPane.setMaxHeight(SUBSCENE_HEIGHT);
		myLevelPane = new Pane();
		initStackPane();
		initScrollPane();
		addChildrenToPanes();
	}

	private void addChildrenToPanes() {
		myScrollPane.setContent(myStackPane);
		myStackPane.getChildren().add(myLevelPane);
		myPane.getChildren().add(myScrollPane);
	}

	private void initStackPane() {
		myStackPane = new StackPane();
		myStackPane.setAlignment(Pos.CENTER);
	}

	private void initScrollPane() {
		myScrollPane = new ScrollPane();
		//myScrollPane.setMinViewportWidth(SUBSCENE_WIDTH);
		//myScrollPane.setMinViewportHeight(SUBSCENE_HEIGHT);
		myScrollPane.setPrefViewportWidth(SUBSCENE_WIDTH);
		myScrollPane.setPrefViewportHeight(SUBSCENE_HEIGHT);
		myScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		myScrollPane.setFitToHeight(true);
		myScrollPane.setFitToWidth(true);
		myScrollPane.setStyle("-fx-background-color: lightgray");
	}
	
	@Override
	public Pane getPane() {
		return myPane;
	}

	public void updateLevelPreview(Level level) {
		myLevel = level;
		myLevelPane.getChildren().clear();
		myStackPane.getChildren().clear();
		updateLevelBackground();
		addLevelActorsToScene();
		// updateIcons();
	}

	/**
	 * Updates the preview to show the level's background.
	 */
	private void updateLevelBackground() {
		myLevelBackground = myLevel.getImageView();
		resizeBackgroundBasedOnScrolling();
		myStackPane.getChildren().addAll(myLevelBackground, myLevelPane);
	}

	public void changeBackgroundImage(Image image, File imageFile) {
		myStackPane.getChildren().clear();
		myLevel.setImageView(new ImageView(image));
		myLevel.setMyBackgroundImgName(imageFile.getName());
		updateLevelBackground();
	}

	public void resizeBackgroundBasedOnScrolling() {
		// myLevelBackground.setImage(new
		// Image(myLevel.getMyBackgroundImgName()));
		if (myLevel.getMyScrollingDirection().equals(VERTICAL)) {
			myLevelBackground.setFitWidth(SUBSCENE_WIDTH);
			// need to get background and set size
		} else {
			myLevelBackground.setFitHeight(SUBSCENE_HEIGHT);
			// need to get background and set size
		}
		myLevelBackground.setPreserveRatio(true);
	}

	/**
	 * Add a level's actors to the preview in the center pane.
	 */
	public void addLevelActorsToScene() {
		myLevelPane.getChildren().removeAll(myActorPreviews);
		myActorPreviews.clear();
		for (IPlayActor actor : myLevel.getActors()) {
			ImageviewActorIcon icon = addActorToScene((IAuthoringActor) actor);
			icon.setX(actor.getX());
			icon.setY(actor.getY());
		}
	}

	public ImageviewActorIcon addActorToScene(IAuthoringActor actor) {
		ImageviewActorIcon icon = new ImageviewActorIcon(actor, actor.getSize());
		setIconBehavior(icon);
		icon.setOnLevel(true);
		myActorPreviews.add(icon);
		myLevelPane.getChildren().add(icon);
		return icon;
	}

	private void setIconBehavior(ImageviewActorIcon icon) {
		icon.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				moveActor(icon, event);
				event.consume();
			}
		});

		ContextMenuActorInLevel contextMenu = new ContextMenuActorInLevel(this);
		icon.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent t) {
				contextMenu.setIcon(icon);
				contextMenu.show(icon, t.getSceneX(), t.getScreenY());
				System.out.println("pressed");
			}
		});

		icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						System.out.println("Double clicked");
//						PopUpActorResize popUp = new PopUpActorResize(POP_UP_WIDTH, POP_UP_HEIGHT, icon.getRefActor(),
//								myLevelEditingEnvironment.getController());
						ImageEditingEnvironmentWithActor iEE = new ImageEditingEnvironmentWithActor(icon.getRefActor());
						iEE.addObserver(LevelPreview.this);
					}
				}
			}
		});
	}

	public void removeActorFromLevel(ImageviewActorIcon icon) {
		myActorPreviews.remove(icon);
		myLevelPane.getChildren().remove(icon);
		myLevel.removeActor((Actor) icon.getRefActor());
	}

	/**
	 * Move an actor by changing its (x, y)-coordinates and visualize by moving
	 * its imageview.
	 * 
	 * @param actor:
	 *            actor to move.
	 * @param actorIV:
	 *            Imageview of actor to move.
	 * @param event:
	 *            drag.
	 */
	private void moveActor(ImageviewActorIcon icon, MouseEvent event) {
		icon.updateIconActorPosition(event.getX(), event.getY());
		icon.setX(event.getX());
		icon.setY(event.getY());
	}

	public void updateIcons() {
		for (ImageviewActorIcon icon : myActorPreviews) {
			icon.updateImageView();
		}
	}

	public Pane getLevelPane() {
		return myLevelPane;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		myLevelEditingEnvironment.getController().updateRefActor((IAuthoringActor) arg1);
		addLevelActorsToScene();
	}
	
	
	public void groupActors(ImageviewActorIcon icon) {
		IAuthoringActor actor = icon.getRefActor(); 
		myLevelEditingEnvironment.groupActors(actor);
	}

}
