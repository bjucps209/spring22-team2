import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.*;
import java.awt.*;


public class GameWindow {

    @FXML Pane gameWindow;
    @FXML VBox vBox;
    Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
    double ratioWidth = size.getWidth()/1280;
    double ratioHeight = size.getHeight()/800;
    Image background = new Image("media/terrain/medieval/medievalfourway.png");
    ImageView backgroundView = new ImageView(background);
    ArrayList<Character> keysPressed = new ArrayList<Character>();

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }

    @FXML
    void updater(){
        KeyFrame frames = new KeyFrame(Duration.millis(20), this::updateView);
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    public void Initialize() {
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
        vBox.setMinWidth(size.getWidth());
        vBox.setMinHeight(size.getHeight());
        gameWindow.setMinWidth(size.getWidth());
        gameWindow.setMinHeight(size.getHeight());
        gameWindow.getChildren().clear();
        ArrayList<Entity> entities = World.instance().displayCurrentEntities();
        World.instance().getCurrentLevel().setObserver(this::Initialize);
        ratioImage(backgroundView);
        for (Entity entity: entities){
            ImageView entityImage = new ImageView(entity.getImage());
            entityImage.setX(entity.getX());
            entityImage.setY(entity.getY());
            entityImage.xProperty().bind(entity.getXProperty());
            entityImage.yProperty().bind(entity.getYProperty());
            entityImage.setUserData(entity);
            entityImage.prefWidth(200);
            entityImage.setPreserveRatio(true);
            gameWindow.getChildren().add(entityImage);
        }

        Screen currentScreen = World.instance().getCurrentLevel().getCurrentScreen();
        for (Obstacle obstacle: currentScreen.findObstacles()){
            ImageView obstacleImage = new ImageView(new Image("media/terrain/medieval/rock.png"));
            obstacleImage.setX(obstacle.getX());
            obstacleImage.setY(obstacle.getY());
            obstacleImage.prefWidth(200);
            obstacleImage.setPreserveRatio(true);
            gameWindow.getChildren().add(obstacleImage);
        }
    }

    @FXML
    void updateView(ActionEvent event){
        try{for (Entity entity: World.instance().displayCurrentEntities()){
            entity.performMovement();
        }
        }catch(ConcurrentModificationException c){return;}
    }

    /**
     * saves the state of the game when the save button is clicked
     * @param event
     */
    @FXML
    void onSaveClicked(ActionEvent event) {

    }
    @FXML
    void ratioImage(ImageView view)
    {
        view.setFitHeight(view.getFitHeight()*ratioHeight);
        view.setFitWidth(view.getFitWidth()*ratioWidth);
        gameWindow.getChildren().add(view);
    }

    
}
