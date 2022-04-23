import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.image.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import model.*;
import java.awt.*;
//import javafx.embed.swing.SwingFXUtils;

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
        backgroundView.setImage(new Image(World.instance().getCurrentLevel().getCurrentScreen().getFilename()));
        ratioImage(backgroundView);
        for (Entity entity: entities){
            EntityImageView entityImage = new EntityImageView(new Image(entity.getImage()));
            entityImage.setImage(new Image(entity.getImage()));
            entityImage.setX(entity.getX());
            entityImage.setY(entity.getY());
            entityImage.xProperty().bind(entity.getXProperty());
            entityImage.yProperty().bind(entity.getYProperty());
            entityImage.setUserData(entity);
            entity.setObserver(entityImage);
            //entityImage.setFitWidth(entity.getSize()*200*ratioWidth);
            entityImage.setPreserveRatio(true);
            if(entity instanceof Boss)
            {
                entityImage.setFitWidth(1280);
            }
            gameWindow.getChildren().add(entityImage);
            if(entity instanceof Player)
            {
                Player player = (Player) entity;
                EntityImageView weaponImage=new EntityImageView(new Image("media/Player/swordwalk.gif"));
                ((Player) entity).setWeaponImage("media/Player/swordwalk.gif");
                ((Player) entity).setWeaponObserver(weaponImage);
                weaponImage.setX(entity.getX());
                weaponImage.setY(entity.getY());
                weaponImage.xProperty().bind(entity.getXProperty());
                weaponImage.yProperty().bind(entity.getYProperty());
                weaponImage.setUserData(entity);
                weaponImage.setFitWidth(entity.getSize());
                weaponImage.setPreserveRatio(true);
                gameWindow.getChildren().add(weaponImage);

                ProgressBar playerHealth = new ProgressBar();
                playerHealth.progressProperty().bind(player.getStats().healthProperty()
                                                           .divide(player.getTotalHealth()));
                playerHealth.setScaleX(player.getTotalHealth() / 8);
                playerHealth.relocate(1200*ratioHeight, 100*ratioWidth);
                playerHealth.toFront();
                gameWindow.getChildren().add(playerHealth);
            }
            if (entity instanceof Enemy){
                Enemy enemy = (Enemy) entity;
                ProgressBar healthBar = new ProgressBar();
                healthBar.progressProperty().bind(enemy.getStats().healthProperty().divide(enemy.getTotalHealth()));
                healthBar.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2));
                healthBar.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2));
                gameWindow.getChildren().add(healthBar);
                entityImage.setImage(new Image(enemy.getImage()));
            }
        }

        Screen currentScreen = World.instance().getCurrentLevel().getCurrentScreen();
        for (Obstacle obstacle: currentScreen.findObstacles()){
            ImageView obstacleImage = new ImageView();
            if(currentScreen.getFilename().contains("medieval"))
            {
                obstacleImage.setImage(new Image("media/terrain/medieval/rock.png"));
            }
            if(currentScreen.getFilename().contains("desert"))
            {
                obstacleImage.setImage(new Image("media/terrain/egypt/cactus.png"));
            }
            if(currentScreen.getFilename().contains("caveman"))
            {
                obstacleImage.setImage(new Image("media/terrain/caveman/deadbush.png"));
                obstacleImage.setFitWidth(70);
            }
            if(currentScreen.getFilename().contains("secret")||currentScreen.getFilename().contains("boss"))
            {
                obstacleImage.setImage(new Image("media/terrain/secret&boss/lantern.png"));
                obstacleImage.setFitWidth(100);
            }
            obstacleImage.setX(obstacle.getX());
            obstacleImage.setY(obstacle.getY());
            obstacleImage.prefWidth(100);
            obstacleImage.setPreserveRatio(true);
            gameWindow.getChildren().add(obstacleImage);
        }
    }

    @FXML
    void updater(){
        KeyFrame frames = new KeyFrame(Duration.millis(20), me -> World.instance().updateView());
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    void playerUpdater(){
        KeyFrame frames = new KeyFrame(Duration.millis(20), me -> World.instance().updatePlayer());
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML 
    void changeImage(Image image, Entity entity){
        for (Node node: gameWindow.getChildren()){
            if (node instanceof ImageView){
                ImageView imageview = (ImageView) node;
                if (imageview.getUserData() == null) continue;
                if (imageview.getUserData().equals(entity)){
                    imageview.setImage(image);
                }
            }
        }
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
