package authoringenvironment.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
/**
 * Tab contains ListView of sounds
 * @author AnnieTang
 *
 */
public class TabLibrarySounds extends TabLibrary{
	private static final String SOUND_IMAGE_NAME = "sound.png";
	private static final int STANDARD_IMAGE_HEIGHT = 20;
	private ObservableList<Label> soundLabels; 
	private ListView<Label> listView;
	
	public TabLibrarySounds(ResourceBundle myResources, String tabText, ActorRuleCreator myRuleMaker) {
		super(myResources, tabText, myRuleMaker);
		setContent();
	}
	
	@Override
	public void setContent() {
		fillFileNames();
		soundLabels = FXCollections.observableArrayList();
		for(String soundName: fileNames){
			Label soundLabel = new Label(soundName, createPlaySoundButton(soundName));
			if(myActorRuleCreator!=null){
				setDragEvent(soundLabel);
			}
			soundLabels.add(soundLabel);
		}
		listView = new ListView<>(soundLabels);
	}
	
	@Override
	Node getContent() {
		return listView;
	}
	
	private Button createPlaySoundButton(String soundName){
		ImageView iv = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(SOUND_IMAGE_NAME)));
		iv.setFitHeight(STANDARD_IMAGE_HEIGHT);
		iv.setPreserveRatio(true);
		Button button = new Button("",iv);
		URL resource = getClass().getClassLoader().getResource(soundName);
    	AudioClip sound = new AudioClip(resource.toString());
	    button.setOnAction(event -> {
	    	if(sound.isPlaying()){
	    		sound.stop();
	    	}
	    	else sound.play();
	    });
		return button;
	}

}
