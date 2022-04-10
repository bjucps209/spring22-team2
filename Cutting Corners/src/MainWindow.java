
import java.io.IOException;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.World;


public class MainWindow {
    @FXML
    Pane pane;
    String state = "START";
    KeyFrame keyFrame = new KeyFrame(Duration.millis(10),e->changeScreens(e));
    Timeline timer = new Timeline(keyFrame);
    Image TITLE_SCREEN = new Image("media/titlescreen.png");
    // Image MOVE_GIF = new Image("media/moveGif.gif");
    // Image ATTACK_GIF = new Image("media/attackGif.gif");
    // Image ITEM_GIF = new Image("media/itemGif.gif");
    // Image CREDITS = new Image("media/about.png");
    Image DIFFICULTY_EASY = new Image("media/buttons/difficultyeasy.png");
    Image DIFFICULTY_NORMAL = new Image("media/buttons/difficultynormal.png");
    Image DIFFICULTY_HARD = new Image("media/buttons/difficultyhard.png");
    Image SLIDER_LEFT = new Image("media/buttons/sliderleftunpressed.png");
    Image SLIDER_LEFT_PRESSED = new Image("media/buttons/sliderleftpressed.png");
    Image SLIDER_RIGHT = new Image("media/buttons/sliderrightunpressed.png");
    Image SLIDER_RIGHT_PRESSED = new Image("media/buttons/sliderrightpressed.png");
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
    Image SCORE_BUTTON = new Image("media/buttons/highscoresbtn.png");
    Image SCORE_BUTTON_PRESSED = new Image("media/buttons/highscoresbtnpressed.png");
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
    ImageView diffSliderLeft = new ImageView(SLIDER_LEFT);
    ImageView diffSliderRight = new ImageView(SLIDER_RIGHT); 
    ImageView campaignSliderLeft = new ImageView(SLIDER_LEFT);
    ImageView campaignSliderRight = new ImageView(SLIDER_RIGHT);
    ImageView startView = new ImageView(START_BUTTON);
    ImageView loadView = new ImageView(LOAD_BUTTON);
    ImageView aboutView = new ImageView(ABOUT_BUTTON);
    ImageView helpView = new ImageView(HELP_BUTTON);
    ImageView settingsView = new ImageView(SETTINGS_BUTTON);
    ImageView scoreView = new ImageView(SCORE_BUTTON);
    // ImageView cheatModeView = new ImageView(CHEATMODE);
        

    @FXML
    void initialize(){
        timer.setCycleCount(100);
        backgroundView.relocate(0, 0);
        // moveView.relocate(2500, 50);
        // attackView.relocate(2500, 350);
        // itemView.relocate(2500, 650);
        // creditView.relocate(200, 1500);
        difficultyView.relocate(-1930, 200);
        diffSliderLeft.relocate(-2090, 200);
        diffSliderRight.relocate(-1770, 200);
        backView.relocate(-2490, 10);
        campaignView.relocate(-1930, 500);
        campaignSliderLeft.relocate(-2090, 500);
        campaignSliderRight.relocate(-1770, 500);
        startView.relocate(50, 200);
        aboutView.relocate(50, 600);
        loadView.relocate(750, 200);
        helpView.relocate(1100, 600);
        settingsView.relocate(50, 700);
        scoreView.relocate(1100, 700);
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
                                                state="SETTINGS";
                                                timer.play();});
        scoreView.setFitHeight(100);
        scoreView.setFitWidth(300);
        scoreView.setOnMousePressed(me -> scoreView.setImage(SCORE_BUTTON_PRESSED));
        scoreView.setOnMouseReleased(me -> {scoreView.setImage(SCORE_BUTTON);
                                                state="SCORES";});
        backView.setFitHeight(100);
        backView.setFitWidth(300);
        backView.setOnMousePressed(me -> backView.setImage(BACK_BUTTON_PRESSED));
        backView.setOnMouseReleased(me -> {backView.setImage(BACK_BUTTON);
                                                state="START";
                                                timer.play();});
        difficultyView.setFitHeight(100);
        difficultyView.setFitWidth(300);
        diffSliderLeft.setFitHeight(100);
        diffSliderLeft.setFitWidth(300);
        diffSliderRight.setFitHeight(100);
        diffSliderRight.setFitWidth(300);
        campaignView.setFitHeight(100);
        campaignView.setFitWidth(300);
        campaignSliderLeft.setFitHeight(100);
        campaignSliderLeft.setFitWidth(300);
        campaignSliderRight.setFitHeight(100);
        campaignSliderRight.setFitWidth(300);
        pane.getChildren().add(backgroundView);
        // pane.getChildren().add(moveView);
        // pane.getChildren().add(attackView);
        // pane.getChildren().add(itemView);
        // pane.getChildren().add(creditView);
        pane.getChildren().add(difficultyView);
        pane.getChildren().add(diffSliderLeft);
        pane.getChildren().add(diffSliderRight);
        pane.getChildren().add(backView);
        pane.getChildren().add(campaignView);
        pane.getChildren().add(campaignSliderLeft);
        pane.getChildren().add(campaignSliderRight);
        pane.getChildren().add(startView);
        pane.getChildren().add(loadView);
        pane.getChildren().add(aboutView);
        pane.getChildren().add(helpView);
        pane.getChildren().add(scoreView);
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
    void changeScreens(ActionEvent event)
    {
        switch(state)
        {
            case "START":
            updateButton(startView, 50, 200);
            updateButton(loadView, 750, 200);
            updateButton(aboutView, 50, 600);
            updateButton(helpView, 1100, 600);
            updateButton(settingsView, 50, 700);
            updateButton(scoreView, 1100, 700);
            updateButton(backView, -2490, 10);
            updateButton(difficultyView, -1930, 200);
            updateButton(diffSliderLeft, -2090, 200);
            updateButton(diffSliderRight, -1770, 200);
            updateButton(campaignView, -1930, 500);
            updateButton(campaignSliderLeft, -2090, 500);
            updateButton(campaignSliderRight, -1770, 500);
                break;
            case "SETTINGS":
            updateButton(backView, 10, 10);
            updateButton(difficultyView, 570, 200);
            updateButton(diffSliderLeft, 410, 200);
            updateButton(diffSliderRight, 730, 200);
            updateButton(campaignView, 570, 500);
            updateButton(campaignSliderLeft, 410, 500);
            updateButton(campaignSliderRight, 730, 500);
            updateButton(startView, 2550, 200);
            updateButton(loadView, 3250, 200);
            updateButton(aboutView, 2550, 600);
            updateButton(helpView, 3600, 600);
            updateButton(settingsView, 2550, 700);
            updateButton(scoreView, 3600, 700);
                break;
            case "HELP":
                break;
            case "ABOUT":
                break;
            case "SCORES":
            break;
        }
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
                diffSliderLeft.relocate(410,200);
                difficultyView.relocate(570, 200);
                diffSliderRight.relocate(730,200);
                campaignSliderLeft.relocate(410,500);
                campaignView.relocate(570, 500);
                campaignSliderRight.relocate(730,500);
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
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event){
                World.instance().getPlayer().removeKey(event.getCode());
                World.instance().getPlayer().addKey(event.getCode());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event){
                World.instance().getPlayer().removeKey(event.getCode());
            }
        });
    }
    @FXML
    void updateButton(ImageView view,double x,double y)
    {
        if(view.getLayoutX()>x)
        {
            view.setLayoutX(view.getLayoutX()-50);
        }
        else if(view.getLayoutX()<x)
        {
            view.setLayoutX(view.getLayoutX()+50);
        }
        if(view.getLayoutY()>y)
        {
            view.setLayoutY(view.getLayoutY()-50);
        }
        else if(view.getLayoutY()<y)
        {
            view.setLayoutY(view.getLayoutY()+50);
        }
    }
}
