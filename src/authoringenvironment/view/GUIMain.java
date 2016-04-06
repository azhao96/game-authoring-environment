package authoringenvironment.view;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import authoringenvironment.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main GUI class. Fixed tool-bar as top pane. 
 * @author AnnieTang
 *
 */
public class GUIMain implements IGUI {
    private static final String GUI_RESOURCE = "authoringGUI";
    private static final String TOP_PANE_ELEMENTS = "TopPaneElements";
    private static final int PADDING = 10;
    private Scene myScene;
	private BorderPane myRoot;
	private ResourceBundle myResources;
	private int windowHeight;
	private int windowWidth;
	private Stage myStage;
	private Controller myController;
	private GUIFactory factory;
	private IGUIElement levels;
	private Scene splashScene;
	
	public GUIMain(int windowWidth, int windowHeight, Stage s, Scene splashScene) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.myStage = s;
		this.splashScene = splashScene;
		init();
	}
	
	/**
	 * Initializes resource bundle, controller, and factory class.
	 */
	private void init(){
		this.myResources = ResourceBundle.getBundle(GUI_RESOURCE);
		myController = new Controller(myStage, this, this.myResources);
		factory = new GUIFactory(myResources, myController);
	}
	
	/**
	 * Creates the fixed tool-bar and sets up over-arching BorderPane. 
	 * @return Scene
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Scene getScene() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		myRoot = new BorderPane();
		setTopPane();
		setCenterPane();
		myScene = new Scene(myRoot, windowHeight, windowWidth, Color.WHITE);
		return myScene;
	}
	
	public void setLeftPane(Pane pane){
		myRoot.setLeft(pane);
	}
	
	public void setRightPane(Pane pane){
		myRoot.setRight(pane);
	}
	
	public void setCenterPane(Pane pane){
		myRoot.setCenter(pane);
	}
	
	public void setBottomPane(Pane pane){
		myRoot.setBottom(pane);
	}
	
	public void setCenterPane(){
//		myController.goToMainScreen();
		IGUI actorEditing = new GUIActorEditingEnvironment(myResources);
		myRoot.setCenter(actorEditing.getPane());
//		GUIMainScreen test = new GUIMainScreen(myController);
//		myRoot.setCenter(test.getPane());
	}
	
	private void setTopPane() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HBox hbox = new HBox(PADDING);
		hbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));		
		initializeTopPaneElements(hbox);
		hbox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		myRoot.setTop(hbox);
	}

	private void initializeTopPaneElements(HBox hbox) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] topPaneElements = myResources.getString(TOP_PANE_ELEMENTS).split(",");
		for (int i = 0; i < topPaneElements.length; i++) {
			IGUIElement elementToCreate = factory.createNewGUIObject(topPaneElements[i]);
			hbox.getChildren().add(elementToCreate.createNode());
		}
	}

	@Override
	public void updateAllNodes() {
		levels.updateNode();
	}

	@Override
	public Pane getPane() {
		return myRoot;
	}

}