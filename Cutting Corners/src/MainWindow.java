
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Coordinates;
import model.HighScoreManager;
import model.PlayerState;
import model.World;
import java.awt.*;


public class MainWindow {
    @FXML
    Pane pane;
    @FXML
    VBox MainWindow;
    String state = "START";
    KeyFrame keyFrame = new KeyFrame(Duration.millis(10),e->{changeScreens(e);MainWindow.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            
        @Override
        public void handle(KeyEvent event){
            keys.add(event.getCode());
        }
    });

    MainWindow.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
        
        @Override
        public void handle(KeyEvent event){
            if(keys.get(keys.size()-1)==KeyCode.ESCAPE)
            {
                state="START";
                timerCredits.stop();
                timer.play();
                credits.relocate(100*ratioHeight, 1000*ratioWidth);
            }
        }
    });});
    Timeline timer = new Timeline(keyFrame);
    KeyFrame keyFrameCredits = new KeyFrame(Duration.millis(10),e->rollCredits());
    Timeline timerCredits = new Timeline(keyFrameCredits);
    Boolean userCampaign = false;
    Boolean cheatMode = false;
    Boolean isLoaded = false;
    IntegerProperty difficulty = new SimpleIntegerProperty(2);
    Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
    double ratioWidth = size.getWidth()/1280;
    double ratioHeight = size.getHeight()/800;
    @FXML
    Label highScores = new Label();
    @FXML
    Label title = new Label();
    @FXML
    Label settings = new Label();
    @FXML
    Label controls = new Label();
    @FXML
    Label credits = new Label();
    @FXML
    ImageView imgView;
    HighScoreManager scores = new HighScoreManager();
    
    ArrayList<KeyCode> keys = new ArrayList<KeyCode>();
    Image TITLE_SCREEN = new Image("media/titlescreen.png");
    Image MOVE_GIF = new Image("media/moveGif.gif");
    Image ATTACK_GIF = new Image("media/attackGif.gif");
    Image ITEM_GIF = new Image("media/itemGif.gif");
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
    Image CHEATMODE = new Image("media/buttons/cheatmode.png");
    Image CHEATMODE_SELECTED = new Image("media/buttons/cheatmodeselected.png");
    Image DEFAULT_CAMPAIGN = new Image("media/buttons/defaultcampaign.png");
    Image USER_CAMPAIGN = new Image("media/buttons/usercampaign.png");
    Image LVL_BUILDER = new Image("media/buttons/lvlbuilderbtn.png");
    Image LVL_BUILDER_PRESSED = new Image("media/buttons/lvlbuilderbtnpressed.png");
    AudioClip TITLE_MUSIC = new AudioClip(getClass().getResource("media/sounds/music/alexander-nakarada-magic-tavern (Title Music).mp3").toString());
    ImageView backgroundView = new ImageView(TITLE_SCREEN);
    ImageView moveView = new ImageView(MOVE_GIF);
    ImageView attackView = new ImageView(ATTACK_GIF);
    ImageView itemView = new ImageView(ITEM_GIF);
    ImageView difficultyView = new ImageView(DIFFICULTY_NORMAL);
    ImageView backView = new ImageView(BACK_BUTTON);
    ImageView campaignView = new ImageView(DEFAULT_CAMPAIGN);
    ImageView diffSliderLeft = new ImageView(SLIDER_LEFT);
    ImageView diffSliderRight = new ImageView(SLIDER_RIGHT); 
    ImageView campaignSliderLeft = new ImageView(SLIDER_LEFT);
    ImageView campaignSliderRight = new ImageView(SLIDER_RIGHT);
    ImageView lvlBuilderView = new ImageView(LVL_BUILDER);
    ImageView startView = new ImageView(START_BUTTON);
    ImageView loadView = new ImageView(LOAD_BUTTON);
    ImageView aboutView = new ImageView(ABOUT_BUTTON);
    ImageView helpView = new ImageView(HELP_BUTTON);
    ImageView settingsView = new ImageView(SETTINGS_BUTTON);
    ImageView scoreView = new ImageView(SCORE_BUTTON);
    ImageView cheatModeView = new ImageView(CHEATMODE);
    
        

    @FXML
    void initialize(){
        TITLE_MUSIC.setCycleCount(Timeline.INDEFINITE);
        TITLE_MUSIC.play();
        if(ratioHeight>1)
        {
            size = new Dimension((int)size.getWidth(), 800);
            ratioHeight=0.888888888;
        }
        if(ratioWidth>1)
        {
            size = new Dimension(1280, (int)size.getHeight());
            ratioWidth=0.888888888;
        }
        MainWindow.setMinWidth(size.getWidth());
        MainWindow.setMinHeight(size.getHeight());
        pane.setMinWidth(size.getWidth());
        pane.setMinHeight(size.getHeight());
        timer.setCycleCount(50);
        timerCredits.setCycleCount(Timeline.INDEFINITE);
        highScores.setText("High Scores:\n");
        try {
            scores.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for(int i=0;i<5;i++)
        {
            highScores.setText(highScores.getText()+"\n"+scores.get(i).getName()+":\t"+scores.get(i).getScore());
        }
        try
        {
            scores.save();
        }
        catch (IOException e)
        {
            System.out.println("Could not save");
        }
        highScores.getStyleClass().add("heading");
        title.setText("Cutting Corners");
        controls.setText("Controls");
        settings.setText("Settings");
        credits.setText("Credits\n\nBasic Gameplay:\t\t\t\tTripp Lawrence, Ethan Collins\nLevel Builder:\t\t\t\t\t\t\t\t"+
        "Seth Meyer\nSerialization:\t\t\t\t\t\t\t\tPaul Alger\nAuxillary Screens:\t\t\t\t\t\t\tEthan Collins\nNarative Lead:\t\t\t\t\t\t\t"+
        "Ethan Collins\n\n\nMedia\n\nCharacter Sprites:\t\t\t\t\t\t\tEthan Collins\nEnemy Sprites:\t\t\t\t\t\t\tEthan Collins\nBoss Sprites:"+
        "\t\t\t\t\t\t\t\tEthan Collins\nTerrain:\t\t\t\t\t\t\t\t\tEthan Collins\nInterface Visuals:\t\t\t\t\t\t\tEthan Collins\n\nObstacles\n\nInspiration for the obstacles"+
        "\ncame from the stamp feature"+
        " at pixilart.com\n\nStaring Cirkyle as himself\n\nMusic\n\n"+
        "Title Music:        Magic Tavern - Alexander Nakarada\nCaveman Level:  Little World - Nul Tiel Records\nEgypt Level:\t    Reverie - Nul"+
        " Tiel Records\nMedieval Level:  Fairy of the Forest - Alexander Nakarada\nSecret Level:\t    Lurking in the Shadows - Scott Holmes Music\n\n"+
        "Sound Effects\n\nBow Shoot:\t\t\t\tK6EQG35-bow-arrow-shot\nSword Attack:\t\t\t\tmixkit-dagger-woosh-1487\nSword Hit:\t\t\t\t"+
        "mixkit-swift-sword-strike-2166\nMace Hit:\t\t\t\tmixkit-sword-pierces-armor-2761\nWand Shoot:\t\t\t\tmixkit-wizard-fire-woosh-1326\nPlayer Hurt:"+
        "\t\t\t\tMinecrat Damage Sound\n\n\n\n\t"+
        "    The story of this game, all names, characters,\n    and incidents portrayed in this production are fictitious.\n  No identification with"+
        " actual persons (living or deceased),\n\t    places, buildings,and products is intended\n\t\t\t    or should be inferred.\n\n\n\n\t\tNo animals,"+
        " pies or sentient shapes were\n\t\t    harmed in the making of this game\n\n\n\n\n\t\tThis game was brought to you in part by\n\t\t"+
        "  Dr. Schaub and the 2pm CpS 209 class\n\n\n\n\n\n\t\tPress Esc to return to the Title Screen");
        title.getStyleClass().add("title");
        settings.getStyleClass().add("title");
        controls.getStyleClass().add("title");
        credits.getStyleClass().add("credits");
        credits.setAlignment(Pos.CENTER);
        highScores.relocate(3020*ratioWidth, 100*ratioHeight);
        title.relocate(250*ratioHeight, 30*ratioWidth);
        controls.relocate(500*ratioHeight, -2470*ratioWidth);
        settings.relocate(-2000*ratioHeight, 30*ratioWidth);
        credits.relocate(100*ratioHeight, 1000*ratioWidth);
        backgroundView.relocate(0, 0);
        moveView.relocate(150*ratioWidth, -2350*ratioHeight);
        attackView.relocate(150*ratioWidth, -2100*ratioHeight);
        itemView.relocate(150*ratioWidth, -1850*ratioHeight);
        difficultyView.relocate(-1930*ratioWidth, 600*ratioHeight);
        diffSliderLeft.relocate(-2090*ratioWidth, 600*ratioHeight);
        diffSliderRight.relocate(-1770*ratioWidth, 600*ratioHeight);
        backView.relocate(-2490*ratioWidth, 10*ratioHeight);
        campaignView.relocate(-1930*ratioWidth, 500*ratioHeight);
        campaignSliderLeft.relocate(-2090*ratioWidth, 500*ratioHeight);
        campaignSliderRight.relocate(-1770*ratioWidth, 500*ratioHeight);
        lvlBuilderView.relocate(-2110*ratioWidth, 200*ratioHeight);
        cheatModeView.relocate(-2180*ratioWidth, 750*ratioHeight);
        startView.relocate(50*ratioWidth, 200*ratioHeight);
        aboutView.relocate(50*ratioWidth, 600*ratioHeight);
        loadView.relocate(750*ratioWidth, 200*ratioHeight);
        helpView.relocate(1100*ratioWidth, 600*ratioHeight);
        settingsView.relocate(50*ratioWidth, 700*ratioHeight);
        scoreView.relocate(1100*ratioWidth, 700*ratioHeight);
        startView.setOnMousePressed(me -> startView.setImage(START_BUTTON_PRESSED));
        startView.setOnMouseReleased(me -> {try{onStartClicked();}catch(IOException i){}
                                            startView.setImage(START_BUTTON);});
        loadView.setOnMousePressed(me -> loadView.setImage(LOAD_BUTTON_PRESSED));
        loadView.setOnMouseReleased(me -> { isLoaded=true;
                                            try{onStartClicked();}catch(IOException i){} // Adding load button
                                            loadView.setImage(LOAD_BUTTON);}); 
        aboutView.setOnMousePressed(me -> aboutView.setImage(ABOUT_BUTTON_PRESSED));
        aboutView.setOnMouseReleased(me -> {aboutView.setImage(ABOUT_BUTTON);
                                                state="ABOUT";
                                                timerCredits.play();
                                                timer.play();});
        helpView.setOnMousePressed(me -> helpView.setImage(HELP_BUTTON_PRESSED));
        helpView.setOnMouseReleased(me -> {helpView.setImage(HELP_BUTTON);
                                                state="HELP";
                                                backView.relocate(10*ratioWidth, -2490*ratioHeight);
                                                timer.play();});
        settingsView.setOnMousePressed(me -> settingsView.setImage(SETTINGS_BUTTON_PRESSED));
        settingsView.setOnMouseReleased(me -> {settingsView.setImage(SETTINGS_BUTTON);
                                                state="SETTINGS";
                                                backView.relocate(-2490*ratioWidth, 10*ratioHeight);
                                                timer.play();});
        scoreView.setOnMousePressed(me -> scoreView.setImage(SCORE_BUTTON_PRESSED));
        scoreView.setOnMouseReleased(me -> {scoreView.setImage(SCORE_BUTTON);
                                                state="SCORES";
                                                backView.relocate(2510*ratioWidth, 10*ratioHeight);
                                                timer.play();});
        backView.setOnMousePressed(me -> backView.setImage(BACK_BUTTON_PRESSED));
        backView.setOnMouseReleased(me -> {backView.setImage(BACK_BUTTON);
                                                state="START";
                                                timer.play();});
        cheatModeView.setOnMouseReleased(me -> {if(cheatMode){cheatModeView.setImage(CHEATMODE);cheatMode=false;}else{cheatModeView.setImage(CHEATMODE_SELECTED);cheatMode=true;}});
        diffSliderLeft.setOnMousePressed(me -> diffSliderLeft.setImage(SLIDER_LEFT_PRESSED));
        diffSliderLeft.setOnMouseReleased(me -> diffSliderPressed(diffSliderLeft));
        diffSliderRight.setOnMousePressed(me -> diffSliderRight.setImage(SLIDER_RIGHT_PRESSED));
        diffSliderRight.setOnMouseReleased(me -> diffSliderPressed(diffSliderRight));
        campaignSliderLeft.setOnMousePressed(me -> campaignSliderLeft.setImage(SLIDER_LEFT_PRESSED));
        campaignSliderLeft.setOnMouseReleased(me -> campaignSliderPressed(campaignSliderLeft));
        campaignSliderRight.setOnMousePressed(me -> campaignSliderRight.setImage(SLIDER_RIGHT_PRESSED));
        campaignSliderRight.setOnMouseReleased(me -> campaignSliderPressed(campaignSliderRight));
        lvlBuilderView.setOnMousePressed(me -> lvlBuilderView.setImage(LVL_BUILDER_PRESSED));
        lvlBuilderView.setOnMouseReleased(me ->lvlBuilderView.setImage(LVL_BUILDER));
        ratioImage(backgroundView);
        ratioImage(startView);
        ratioImage(loadView);
        ratioImage(moveView);
        ratioImage(attackView);
        ratioImage(itemView);
        ratioImage(lvlBuilderView);
        ratioImage(cheatModeView);
        sizeButton(backView);
        sizeButton(difficultyView);
        sizeButton(diffSliderLeft);
        sizeButton(diffSliderRight);
        sizeButton(campaignView);
        sizeButton(campaignSliderLeft);
        sizeButton(campaignSliderRight);
        sizeButton(scoreView);
        sizeButton(settingsView);
        sizeButton(helpView);
        sizeButton(aboutView);
        pane.getChildren().add(highScores);
        pane.getChildren().add(title);
        pane.getChildren().add(settings);
        pane.getChildren().add(controls);
        pane.getChildren().add(credits);
    }
    @FXML
    void changeScreens(ActionEvent event)
    {
        switch(state)
        {
            case "START":
                updateButton(title, 250, 30);
                updateButton(startView, 50, 200);
                updateButton(loadView, 750, 200);
                updateButton(aboutView, 50, 600);
                updateButton(helpView, 1100, 600);
                updateButton(settingsView, 50, 700);
                updateButton(scoreView, 1100, 700);
                updateButton(settings, -2000, 30);
                updateButton(backView, -2490, 10);
                updateButton(difficultyView, -1930, 600);
                updateButton(diffSliderLeft, -2090, 600);
                updateButton(diffSliderRight, -1770, 600);
                updateButton(campaignView, -1930, 500);
                updateButton(campaignSliderLeft, -2090, 500);
                updateButton(campaignSliderRight, -1770, 500);
                updateButton(lvlBuilderView, -2110, 200);
                updateButton(cheatModeView, -2180, 750);
                updateButton(highScores, 3020, 100);
                updateButton(controls, 500, -2470);
                updateButton(moveView, 150, -2350);
                updateButton(attackView, 150, -2100);
                updateButton(itemView, 150, -1850);
                break;
            case "SETTINGS":
                updateButton(settings,500 , 30);
                updateButton(backView, 10, 10);
                updateButton(difficultyView, 570, 600);
                updateButton(diffSliderLeft, 410, 600);
                updateButton(diffSliderRight, 730, 600);
                updateButton(campaignView, 570, 500);
                updateButton(campaignSliderLeft, 410, 500);
                updateButton(campaignSliderRight, 730, 500);
                updateButton(lvlBuilderView, 390, 200);
                updateButton(cheatModeView, 320, 750);
                updateButton(title, 2750, 30);
                updateButton(startView, 2550, 200);
                updateButton(loadView, 3250, 200);
                updateButton(aboutView, 2550, 600);
                updateButton(helpView, 3600, 600);
                updateButton(settingsView, 2550, 700);
                updateButton(scoreView, 3600, 700);
                break;
            case "HELP":
                updateButton(backView, 10, 10);
                updateButton(controls, 500, 30);
                updateButton(moveView, 150, 150);
                updateButton(attackView, 150, 400);
                updateButton(itemView, 150, 650);
                updateButton(title, 250, 2530);
                updateButton(startView, 50, 2700);
                updateButton(loadView, 750, 2700);
                updateButton(aboutView, 50, 3100);
                updateButton(helpView, 1100, 3100);
                updateButton(settingsView, 50, 3200);
                updateButton(scoreView, 1100, 3200);
                
                break;
            case "ABOUT":
                updateButton(title, 250, -2470);
                updateButton(startView, 50, -2300);
                updateButton(loadView, 750, -2300);
                updateButton(aboutView, 50, -1900);
                updateButton(helpView, 1100, -1900);
                updateButton(settingsView, 50, -1800);
                updateButton(scoreView, 1100, -1800);
                break;
            case "SCORES":
                updateButton(backView, 10, 10);
                updateButton(highScores, 520, 100);
                updateButton(title, -2250, 30);
                updateButton(startView, -2450, 200);
                updateButton(loadView, -1750, 200);
                updateButton(aboutView, -2450, 600);
                updateButton(helpView, -1400, 600);
                updateButton(settingsView, -2450, 700);
                updateButton(scoreView, -1400, 700);
            break;
        }
    }
    @FXML
    void rollCredits()
    {
        credits.setLayoutY(credits.getLayoutY()-(1*ratioHeight));
    }
    @FXML
    void onStartClicked() throws IOException{
        //String name = JOptionPane.showInputDialog("Enter your Name");
        String name = "Ethan";
        startView.setImage(START_BUTTON);
        TITLE_MUSIC.stop();
        var loader = new FXMLLoader(getClass().getResource("NameWindow.fxml"));
        var scene = new Scene(loader.load());
        pane.getScene().getWindow().hide();
        var stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        NameWindow nameWindow = loader.getController();
        nameWindow.initialize(isLoaded,userCampaign,cheatMode,difficulty);
        // var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        // var scene = new Scene(loader.load());
        // var stage = new Stage();
        // stage.setScene(scene);
        // stage.setTitle("Cutting Corners");
        // stage.getIcons().add(new Image("media/windowicon.png"));
        // stage.show();

        // GameWindow gameWindow = loader.getController();
        // gameWindow.Initialize(isLoaded, userCampaign,cheatMode,difficulty,name);
        // pane.getScene().getWindow().hide();
        // TITLE_MUSIC.stop();
        // gameWindow.updater();
        // gameWindow.playerUpdater();
        // scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
        //     @Override
        //     public void handle(KeyEvent event){
        //         if(World.instance().getPlayer()!=null)
        //         {
        //             KeyCode keyPressed = event.getCode();
        //             if (keyPressed == KeyCode.SPACE){
        //                 World.instance().getPlayer().setAttackCount(35);
        //                 World.instance().getPlayer().setState(PlayerState.drinking);
        //             }
        //             else{
        //                 World.instance().getPlayer().removeKey(event.getCode());
                        
        //                 World.instance().getPlayer().addKey(event.getCode());
        //             }
        //         }
        //     }
        // });

        // scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            
        //     @Override
        //     public void handle(KeyEvent event){
        //         if(World.instance().getPlayer()!=null)
        //         {
        //             World.instance().getPlayer().removeKey(event.getCode());
        //         }
        //     }
        // });

        // scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            
        //     @Override
        //     public void handle(MouseEvent event){
        //         int xCoord = (int) event.getX();
        //         int yCoord = (int) event.getY();
        //         if(World.instance().getPlayer()!=null)
        //         {
        //             World.instance().getPlayer().setMouseCoordinates(new Coordinates(xCoord, yCoord));
        //         }
        //     }
        // });

        // scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
        //     @Override
        //     public void handle(MouseEvent event){
        //         if(World.instance().getPlayer()!=null)
        //         {
        //             World.instance().getPlayer().setEnemies(World.instance().getCurrentLevel().getCurrentScreen().getEnemies());
        //             World.instance().getPlayer().setState(PlayerState.attacking);
        //             System.out.println(World.instance().getPlayer().getCoords().getxCoord());
        //         }
        //     }
        // });
    }

    @FXML
    void diffSliderPressed(ImageView view)
    {
        if(view==diffSliderLeft&&difficulty.get()!=1)
        {
            difficulty.set(difficulty.get()-1);
            System.out.println(difficulty);
            switch(difficulty.get())
            {
                case 1:
                    difficultyView.setImage(DIFFICULTY_EASY);
                    break;
                case 2:
                    difficultyView.setImage(DIFFICULTY_NORMAL);
                    diffSliderLeft.setImage(SLIDER_LEFT);
                    break;
            }
            diffSliderRight.setImage(SLIDER_RIGHT);
        }
        if(view==diffSliderRight&&difficulty.get()<3)
        {
            difficulty.set(difficulty.get()+1);
            switch(difficulty.get())
            {
                case 2:
                    difficultyView.setImage(DIFFICULTY_NORMAL);
                    diffSliderRight.setImage(SLIDER_RIGHT);
                    break;
                case 3:
                    difficultyView.setImage(DIFFICULTY_HARD);
                    break;
            }
            diffSliderLeft.setImage(SLIDER_LEFT);
        }
    }
    @FXML
    void campaignSliderPressed(ImageView view)
    {
        if(userCampaign)
        {
            userCampaign=false;
            campaignView.setImage(USER_CAMPAIGN);
        }
        else
        {
            userCampaign=true;
            campaignView.setImage(DEFAULT_CAMPAIGN);
        }
        if(view==campaignSliderLeft)
        {
            campaignSliderLeft.setImage(SLIDER_LEFT);
        }
        else
        {
            campaignSliderRight.setImage(SLIDER_RIGHT);
        }
    }
    @FXML
    void updateButton(Node view,double x,double y)
    {
        if(Math.abs(view.getLayoutX()-(x*ratioWidth))>25)
        {
            if(view.getLayoutX()>x*ratioWidth)
            {
                view.setLayoutX(view.getLayoutX()-(50*ratioWidth));
            }
            else if(view.getLayoutX()<x*ratioWidth)
            {
                view.setLayoutX(view.getLayoutX()+(50*ratioWidth));
            }
        }
        if(Math.abs(view.getLayoutY()-(y*ratioHeight))>25)
        {
            if(view.getLayoutY()>y*ratioHeight)
            {
                view.setLayoutY(view.getLayoutY()-(50*ratioHeight));
            }
            else if(view.getLayoutY()<y*ratioHeight)
            {
                view.setLayoutY(view.getLayoutY()+(50*ratioHeight));
            }
        }
    }
    @FXML
    void sizeButton(ImageView view)
    {
        view.setFitHeight(100*ratioHeight);
        view.setPreserveRatio(true);
        pane.getChildren().add(view);
    }
    @FXML
    void ratioImage(ImageView view)
    {
        view.setFitHeight(view.getFitHeight()*ratioHeight);
        view.setFitWidth(view.getFitWidth()*ratioWidth);
        pane.getChildren().add(view);
    }
}
