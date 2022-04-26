import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.border.StrokeBorder;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.image.*;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
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
    HBox effectBox = new HBox();
    

    @FXML
    void onGreetClicked(ActionEvent event) {
        var alert = new Alert(AlertType.INFORMATION, "Hello, world!");
        alert.setHeaderText(null);
        alert.show();

    }
    

    @FXML
    public void Initialize(boolean isLoaded) throws IOException{
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
        
        //check if loading from save file
        // if (isLoaded) {
        //     World.instance().load("savegame.dat");
        //     entities = World.instance().displayCurrentEntities();
        // }

        // World.instance().getCurrentLevel().setObserver(() -> {
        //     try {
        //         Initialize();
        //     } catch (IOException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        // });
        World.instance().getCurrentLevel().setObserver( me -> {
            try {
                Initialize(isLoaded);
            } catch (IOException e) {}
        } );
        backgroundView.setImage(new Image(World.instance().getCurrentLevel().getCurrentScreen().getFilename()));
        
        effectBox.relocate(950*ratioWidth, 200*ratioHeight);

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
            //displayEntity(entity);
            if(entity instanceof Player)
            {
                Player player = (Player) entity;
                displayPlayerWeapon(player);
                displayPlayerHealthBar(player);
                displayScoreAndExperience(player);
                    player.getObserver().changeImage(player.getImage(), player.getFacing());
                    player.getWeaponObserver().changeImage(player.getWeaponImage(), player.getFacing());
            }
            if (entity instanceof Enemy){
                Enemy enemy = (Enemy) entity;
                displayEnemyHealthBars(enemy);
            }
            if (entity instanceof DroppedItem){
                DroppedItem item = (DroppedItem) entity;
                showItem(item);
            }
        }

        Screen currentScreen = World.instance().getCurrentLevel().getCurrentScreen();
        displayObstacles(currentScreen);
        
        ratioImage(backgroundView);
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
    void displayDamage(Entity entity, int damage, int time){
        Label damageIndicator = new Label("- " + String.valueOf(damage));
        damageIndicator.layoutXProperty().bind(entity.getXProperty().subtract(5));
        damageIndicator.layoutYProperty().bind(entity.getYProperty().add(25));
        damageIndicator.getStyleClass().add("achievements");
        damageIndicator.getStyleClass().add("health");
        gameWindow.getChildren().add(damageIndicator);

        KeyFrame frames = new KeyFrame(Duration.millis(time), me -> unDisplayDamage(damageIndicator));
        Timeline damageTimer = new Timeline(frames);
        damageTimer.play();
    }

    @FXML
    void unDisplayDamage(Label damageIndicator){
        gameWindow.getChildren().remove(damageIndicator);
    }

    @FXML
    void showItem(DroppedItem item){
        item.setInformant(this::Notify);
    }

    @FXML
    void Notify(String text, DroppedItem item){
        if (text == null){unNotify(item);}
        Label notification = new Label(text);
        notification.setUserData("Notification");
        notification.setLayoutX(item.getX());
        notification.setLayoutY(item.getY() + 50);
        gameWindow.getChildren().add(notification);
    }

    @FXML
    void unNotify(DroppedItem item){
        for (Node node: gameWindow.getChildren()){
            if (node.getUserData() != null && node.getUserData().equals("Notification")){
                gameWindow.getChildren().remove(node);
            }
        }
    }

    @FXML
    void showEffectTimer(int time, String effectName, String icon){
        VBox effectDropdown = new VBox();
        Label effectTitle = new Label(effectName);
        Image image = new Image(icon);
        ImageView imageview = new ImageView(image);
        Label duration = new Label();

        KeyFrame frames = new KeyFrame(Duration.seconds(1), me -> countdown(duration));
        Timeline timer = new Timeline(frames);
        timer.setCycleCount(time);

        effectDropdown.getChildren().add(effectTitle);
        HBox row2 = new HBox(imageview, duration);
        effectDropdown.getChildren().add(row2);
        effectBox.getChildren().add(effectDropdown);
    }

    @FXML
    void countdown(Label duration){
        String text = duration.getText();
        int time = Integer.parseInt(text);

        duration.setText(time - 1 + "");
    }

    @FXML
    void displayInventory(Player player){
        HBox inventorySlots = new HBox();
        for (int i = 0; i < 5; i++){
            if (player.getInventory().size() > i){
                Item displayed = player.getInventory().get(i);
                ImageView itemImage = new ImageView(new Image(displayed.getImage()));
                inventorySlots.getChildren().add(itemImage);
            }
        }
        inventorySlots.relocate(ratioWidth*500, ratioHeight*600);
        inventorySlots.getStyleClass().add("inventory");
        gameWindow.getChildren().add(inventorySlots);
    }

    @FXML
    void displayEntity(Entity entity){
        
    }

    @FXML
    void displayPlayerWeapon(Player player){
        EntityImageView weaponImage=new EntityImageView(new Image("media/Player/swordwalk.gif"));
        player.setWeaponImage("media/Player/swordwalk.gif");
        player.setWeaponObserver(weaponImage);
        weaponImage.setX(player.getX());
        weaponImage.setY(player.getY());
        weaponImage.xProperty().bind(player.getXProperty());
        weaponImage.yProperty().bind(player.getYProperty());
        weaponImage.setUserData(player);
        weaponImage.setFitWidth(player.getSize());
        weaponImage.setPreserveRatio(true);
        gameWindow.getChildren().add(weaponImage);
    }

    @FXML
    void displayPlayerHealthBar(Player player){
        player.setIndicator(this::displayDamage);
        ProgressBar playerHealth = new ProgressBar();
        playerHealth.progressProperty().bind(player.getStats().healthProperty()
                                                    .divide(player.getTotalHealth()));
        playerHealth.setScaleX(player.getTotalHealth() / 4);
        playerHealth.setScaleY(1.5);
        playerHealth.relocate(1000*ratioWidth, 100*ratioHeight);
        gameWindow.getChildren().add(playerHealth);

        Label healthLabel = new Label();
        healthLabel.getStyleClass().add("health");
        healthLabel.textProperty().bind(player.getStats().healthProperty().asString());
        healthLabel.relocate(875*ratioWidth, 95*ratioHeight);
        gameWindow.getChildren().add(healthLabel);
    }

    @FXML
    void displayEnemyHealthBars(Enemy enemy){
        enemy.setIndicator(this::displayDamage);
        ProgressBar healthBar = new ProgressBar();
        healthBar.progressProperty().bind(enemy.getStats().healthProperty().divide(enemy.getTotalHealth()));
        healthBar.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2 + 5));
        healthBar.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2 - 50));
        healthBar.setScaleX(enemy.getTotalHealth() / 3);
        healthBar.setScaleY(1.5);
        gameWindow.getChildren().add(healthBar);

        Label healthLabel = new Label();
        healthLabel.textProperty().bind(enemy.getStats().healthProperty().asString());
        healthLabel.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2));
        healthLabel.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2));
        healthLabel.getStyleClass().add("health");
        gameWindow.getChildren().add(healthLabel);
    }

    @FXML
    void displayScoreAndExperience(Player player){
        Label score = new Label("Score: " + String.valueOf(player.getScore()));
        Label experience = new Label("Exp: \n" + String.valueOf(player.getExperience()));

        score.relocate(950*ratioWidth, 150*ratioHeight);
        experience.relocate(1150*ratioWidth, 150*ratioHeight);

        score.getStyleClass().add("achievements");
        experience.getStyleClass().add("achievements");

        gameWindow.getChildren().add(score);
        // gameWindow.getChildren().add(experience);
    }

    @FXML
    void displayObstacles(Screen currentScreen){
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
            obstacleImage.toBack();
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
        view.toBack();
    }

    
}
