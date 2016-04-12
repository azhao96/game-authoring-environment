package gameplayer.view;

import gameengine.controller.*;
import gameengine.model.Actor;
import gameengine.model.Attribute;
import gameengine.model.PhysicsEngine;
import gameengine.model.Rule;
import gameengine.model.Actions.Action;
import gameengine.model.Actions.GainPoints;
import gameengine.model.Actions.HorizontalBounceCollision;
import gameengine.model.Actions.HorizontalStaticCollision;
import gameengine.model.Actions.LoseGame;
import gameengine.model.Actions.MoveLeft;
import gameengine.model.Actions.MoveRight;
import gameengine.model.Actions.MoveUp;
import gameengine.model.Actions.NextLevel;
import gameengine.model.Actions.VerticalBounceCollision;
import gameengine.model.Triggers.AttributeType;
import gameengine.model.Triggers.KeyTrigger;
import gameengine.model.Triggers.SideCollision;
import gameengine.model.Triggers.TopCollision;
import gameplayer.controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

import gamedata.controller.CreatorController;

public class Main extends Application {
	
	public Main() {
		// TODO Auto-generated constructor stub
	}
	

	
	public static void main(String args[]){
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameInfo info = new GameInfo();
		info.setMyCurrentLevelNum(0);
		
		Actor actor1 = new Actor();
		actor1.setMyImageViewName("redball.png");
		actor1.setMyName("A1");
		
		Actor actor2 = new Actor();
		actor2.setMyImageViewName("purplecircle.png");
		actor2.setX(600);
		actor2.setMyName("A2");
		
		Actor actor3 = new Actor();
		actor3.setMyImageViewName("elsa.png");
		
		KeyTrigger trigger1 = new KeyTrigger(KeyCode.RIGHT);
		KeyTrigger trigger2 = new KeyTrigger(KeyCode.LEFT);
		SideCollision trigger3 = new SideCollision(actor1,actor2);
		KeyTrigger trigger4 = new KeyTrigger(KeyCode.SPACE);
		Action action1 = new MoveRight(actor1);
		Action action2 = new MoveLeft(actor1);
		Action action3 = new NextLevel(actor1);
		Action action4 = new MoveUp(actor1);
		Rule rule = new Rule(trigger1,action1);
		Rule rule2 = new Rule(trigger2, action2);
		Rule rule3 = new Rule(trigger3,action3);
		Rule rule4 = new Rule(trigger4,action4);
		actor1.addRule(rule);
		actor1.addRule(rule2);
		actor1.addRule(rule3);
		actor1.addRule(rule4);
		
		List<Level> levels = new ArrayList<Level>();
		Level level1 = new Level();
		levels.add(level1);
		level1.addActor(actor1);
		level1.addActor(actor2);
		Level level2 = new Level();
		level2.addActor(actor3);
		levels.add(level2);
		
		Group group = new Group();
		Scene scene = new Scene(group);
		
		Game model = new Game(info,levels);
		CreatorController c = new CreatorController(model);
		c.saveForEditing(new File("gamefiles/test.xml"));
		PerspectiveCamera camera = new PerspectiveCamera();
		GameScreen view = new GameScreen(camera);

		GameController controller = new GameController();
		controller.setGame(model);
		controller.setGameView(view);

		SubScene sub = view.getScene();
		sub.fillProperty().set(Color.BLUE);
		group.getChildren().add(sub);
		
		
		Stage stage = new Stage();
		stage.setWidth(800);
		stage.setHeight(600);

//		CreatorController c = new CreatorController(model);
//		System.out.println(c);
//		File myF = new File("gamefiles/test.xml");
//		System.out.println(myF);
//		c.saveForEditing(myF);
		

		sub.setCamera(camera);
		stage.setScene(scene);
		stage.show();
		controller.initialize(0);

		
//		Stage stage = new Stage();
//		Group root = new Group();
//		Image actor1img = new Image(getClass().getClassLoader().getResourceAsStream("newactor.png"));
//		ImageView imgview = new ImageView(actor1img);
//		root.getChildren().add(imgview);
//		Scene scene = new Scene(root,800,800);
//		PerspectiveCamera camera = new PerspectiveCamera();
//
//		scene.setCamera(camera);
//		stage.setScene(scene);
//		stage.show();
//		
//        KeyFrame frame = new KeyFrame(Duration.seconds(.01),
//                e -> {
//                	camera.setTranslateX(camera.getTranslateX()-5);
//                	System.out.println(imgview.getX());
//                });
//		Timeline animation = new Timeline();
//		animation.setCycleCount(Timeline.INDEFINITE);
//		animation.getKeyFrames().add(frame);
//		animation.play();
		
	}

}
