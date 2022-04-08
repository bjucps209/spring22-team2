
import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainWindow {
    @FXML
    Pane pane;
    Image TITLE_SCREEN = new Image("media/titlescreen.png");
    // Image MOVE_GIF = new Image("media/moveGif.gif");
    // Image ATTACK_GIF = new Image("media/attackGif.gif");
    // Image ITEM_GIF = new Image("media/itemGif.gif");
    // Image CREDITS = new Image("media/about.png");
    Image DIFFICULTY_EASY = new Image("media/buttons/difficultyeasy.png");
    Image DIFFICULTY_NORMAL = new Image("media/buttons/difficultynormal.png");
    Image DIFFICULTY_HARD = new Image("media/buttons/difficultyhard.png");
    // Image DIFFICULTY_SLIDER_LEFT = new Image("media/difficultysliderleft.png");
    // Image DIFFICULTY_SLIDER_LEFT_PRESSED = new Image("media/difficultysliderleftpressed.png");
    // Image DIFFICULTY_SLIDER_RIGHT = new Image("media/difficultysliderright.png");
    // Image DIFFICULTY_SLIDER_RIGHT_PRESSED = new Image("media/difficultysliderrightpressed.png");
    Image BACK_BUTTON = new Image("media/buttons/backbtn.png");
    Image BACK_BUTTON_PRESSED = new Image("media/buttons/backbtnpressed.png");
    Image START_BUTTON = new Image("media/buttons/startbtn.png");
    Image START_BUTTON_PRESSED = new Image("media/buttons/startbtnpressed.png");
    Image LOAD_BUTTON = new Image("media/buttons/loadbtn.png");
    Image LOAD_BUTTON_PRESSED = new Image("media/buttons/loadbtnpressed.png");
    Image ABOUT_BUTTON = new Image("media/buttons/aboutbtn.png");
    Image ABOUT_BUTTON_PRESSED = new Image("media/buttons/aboutbtnpressed.png");
    Image HELP_BUTTON = new Image("media/buttons/helpbtn.png");
    Image HELP_BUTTON_PRESSED = new Image("media/buttons/helpbtnpressed.png");
    Image SETTINGS_BUTTON = new Image("media/buttons/settingsbtn.png");
    Image SETTINGS_BUTTON_PRESSED = new Image("media/buttons/settingsbtnpressed.png");
    // Image CHEATMODE = new Image("media/cheatmode.png");
    // Image CHEATMODE_SELECTED = new Image("media/cheatmodeselected.png");
    Image DEFAULT_CAMPAIGN = new Image("media/buttons/defaultcampaign.png");
    Image USER_CAMPAIGN = new Image("media/buttons/usercampaign.png");
    ImageView backgroundView = new ImageView(TITLE_SCREEN);
    // ImageView moveView = new ImageView(MOVE_GIF);
    // ImageView attackView = new ImageView(ATTACK_GIF);
    // ImageView itemView = new ImageView(ITEM_GIF);
    // ImageView creditView = new ImageView(CREDITS);
    ImageView difficultyView = new ImageView(DIFFICULTY_NORMAL);
    ImageView backView = new ImageView(BACK_BUTTON);
    ImageView campaignView = new ImageView(DEFAULT_CAMPAIGN);
    // ImageView diffSliderLeft = new ImageView(DIFFICULTY_SLIDER_LEFT);
    // ImageView diffSliderRight = new ImageView(DIFFICULTY_SLIDER_RIGHT); 
    ImageView startView = new ImageView(START_BUTTON);
    ImageView loadView = new ImageView(LOAD_BUTTON);
    ImageView aboutView = new ImageView(ABOUT_BUTTON);
    ImageView helpView = new ImageView(HELP_BUTTON);
    ImageView settingsView = new ImageView(SETTINGS_BUTTON);
    // ImageView cheatModeView = new ImageView(CHEATMODE);
        

    @FXML
    void initialize(){
        backgroundView.relocate(0, 0);
        // moveView.relocate(2500, 50);
        // attackView.relocate(2500, 350);
        // itemView.relocate(2500, 650);
        // creditView.relocate(200, 1500);
        difficultyView.relocate(-1000, 200);
        backView.relocate(-1000, 10);
        campaignView.relocate(-1000, 500);
        startView.relocate(50, 200);
        aboutView.relocate(50, 600);
        loadView.relocate(750, 200);
        helpView.relocate(1100, 600);
        settingsView.relocate(50, 700);
        startView.setOnMousePressed(me -> startView.setImage(START_BUTTON_PRESSED));
        startView.setOnMouseReleased(me -> {try{onStartClicked();}catch(IOException i){}});
        loadView.setOnMousePressed(me -> loadView.setImage(LOAD_BUTTON_PRESSED));
        loadView.setOnMouseReleased(me -> loadView.setImage(LOAD_BUTTON));
        aboutView.setFitHeight(100);
        aboutView.setFitWidth(300);
        aboutView.setOnMousePressed(me -> aboutView.setImage(ABOUT_BUTTON_PRESSED));
        aboutView.setOnMouseReleased(me -> aboutView.setImage(ABOUT_BUTTON));
        helpView.setFitHeight(100);
        helpView.setFitWidth(300);
        helpView.setOnMousePressed(me -> helpView.setImage(HELP_BUTTON_PRESSED));
        helpView.setOnMouseReleased(me -> helpView.setImage(HELP_BUTTON));
        settingsView.setFitHeight(100);
        settingsView.setFitWidth(300);
        settingsView.setOnMousePressed(me -> settingsView.setImage(SETTINGS_BUTTON_PRESSED));
        settingsView.setOnMouseReleased(me -> {settingsView.setImage(SETTINGS_BUTTON);
                                                changeScreen("Settings");});
        backView.setFitHeight(100);
        backView.setFitWidth(300);
        backView.setOnMousePressed(me -> backView.setImage(BACK_BUTTON_PRESSED));
        backView.setOnMouseReleased(me -> {backView.setImage(BACK_BUTTON);
                                                changeScreen("Start");});
        pane.getChildren().add(backgroundView);
        // pane.getChildren().add(moveView);
        // pane.getChildren().add(attackView);
        // pane.getChildren().add(itemView);
        // pane.getChildren().add(creditView);
        pane.getChildren().add(difficultyView);
        pane.getChildren().add(backView);
        pane.getChildren().add(campaignView);
        pane.getChildren().add(startView);
        pane.getChildren().add(loadView);
        pane.getChildren().add(aboutView);
        pane.getChildren().add(helpView);
        pane.getChildren().add(settingsView);
    }
    @FXML
    void onMouseClicked(MouseEvent event)
    {

    }
    @FXML
    void onMouseMoved(MouseEvent event)
    {

    }
    @FXML
    void changeScreen(String newScreen)
    {
        switch(newScreen)
        {
            case "Start":
                difficultyView.relocate(-1000, 200);
                backView.relocate(-1000, 10);
                campaignView.relocate(-1000, 500);
                startView.relocate(50, 200);
                aboutView.relocate(50, 600);
                loadView.relocate(750, 200);
                helpView.relocate(1100, 600);
                settingsView.relocate(50, 700);
                break;
            case "Settings":
                backView.relocate(10, 10);
                //diffSliderLeft.relocate(370,200);
                difficultyView.relocate(420, 200);
                //diffSliderRight.relocate(470,200);
                //campaignSliderLeft.relocate(370,500);
                campaignView.relocate(420, 500);
                //campaignSliderRight.relocate(470,500)
                startView.relocate(2500, 200);
                loadView.relocate(2500, 200);
                helpView.relocate(2500, 600);
                aboutView.relocate(2500, 600);
                settingsView.relocate(2500, 700);
                break;
            case "About":
                //Move Credits buttons to center and start buttons offscreen
                break;
            case "Help":
                //Move Help buttons to center and start buttons offscreen
                break;
            case "Scores":
                //Move HighScore buttons to center and start buttons offscreen
                break;
        }
    }
    @FXML
    void onStartClicked() throws IOException{
        startView.setImage(START_BUTTON);
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        
        var scene = new Scene(loader.load());
        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        GameWindow gameWindow = loader.getController();
        gameWindow.Initialize();
    }
}
