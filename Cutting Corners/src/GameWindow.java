import java.util.*;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
    Image PAUSE_BACKGROUND = new Image("media/titlescreen.png");
    Image RESUME_BTN = new Image("media/buttons/resumebtn.png");
    Image SAVE_BTN = new Image("media/buttons/savebtn.png");
    Image SAVEEXIT_BTN = new Image("media/buttons/saveexitbtn.png");
    Image EXIT_BTN = new Image("media/buttons/exitbtn.png");
    Label playerDied = new Label("You Died");
    ImageView pauseView = new ImageView(PAUSE_BACKGROUND);
    ImageView resumeView = new ImageView(RESUME_BTN);
    ImageView saveView = new ImageView(SAVE_BTN);
    ImageView saveExitView = new ImageView(SAVEEXIT_BTN);
    ImageView exitView = new ImageView(EXIT_BTN);
    VBox effectBox = new VBox();
    BooleanProperty playerDead = new SimpleBooleanProperty(false);
    

    @FXML
    public void Initialize(boolean isLoaded,Boolean userCampaign,Boolean cheatMode,IntegerProperty difficulty,String name) throws IOException{
        ArrayList<Entity> entities = World.instance().displayCurrentEntities();
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
        ArrayList<Node> toRemove = new ArrayList<>();
        for(Node node:gameWindow.getChildren())
        {
            if(node.getUserData() instanceof Dodecahedron)
            {
                if(!((Dodecahedron)node.getUserData()).isPlayingGif())
                {
                    toRemove.add(node);
                }
            }
            else
            {
                toRemove.add(node);
            }
        }
        gameWindow.getChildren().removeAll(toRemove);
        //gameWindow.getChildren().clear();
        
        if (playerDead.get()){
            World.instance().setIsPaused(true);
        }
        gameWindow.getChildren().add(effectBox);
        effectBox.relocate(950*ratioWidth, 200*ratioHeight);

        World.instance().getCurrentLevel().setObserver( me -> {
            try {
                Initialize(isLoaded,userCampaign,cheatMode,difficulty,name);
            } catch (IOException e) {}
        } );
        backgroundView.setImage(new Image(World.instance().getCurrentLevel().getCurrentScreen().getFilename()));
        
        // check if loading from save file
        if (isLoaded) {
            World.instance().load("savegame.dat");
            entities = World.instance().displayCurrentEntities();
        }

        World.instance().setLoaded(isLoaded);
        World.instance().setCheatMode(cheatMode);
        World.instance().setCampaign(userCampaign);
        World.instance().DifficultyProperty().bind(difficulty);
        World.instance().setPlayerName(name);
        
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
                if(!(((Boss)entity) instanceof Circle))
                {
                    entityImage.setFitWidth(1280);
                }
            }
            
            if(entity instanceof Dodecahedron)
            {
                if(!((Dodecahedron)entity).isPlayingGif())
                {
                    gameWindow.getChildren().add(entityImage);
                }
            }
            else
            {
                gameWindow.getChildren().add(entityImage);
            }
                //displayEntity(entity);
                if(entity instanceof Player)
                {
                    Player player = (Player) entity;
                    displayPlayerWeapon(player);
                    displayPlayerHealthBar(player);
                    displayScoreAndExperience(player);
                        player.getObserver().changeImage(player.getImage(), player.getFacing());
                        player.getWeaponObserver().changeImage(player.getWeaponImage(), player.getFacing());
                    playerDead.bindBidirectional(player.getDead());
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
            pauseView.relocate(0, 0);
            resumeView.relocate(40, 50);
            saveView.relocate(40, 300);
            saveExitView.relocate(640, 50);
            exitView.relocate(640, 300);
            playerDied.getStyleClass().add("title");
            playerDied.relocate(350, 550);
            
            if (playerDead.get()){
                effectBox.getChildren().clear();
                resumeView.setOnMouseClicked(e-> {
                    int currentLevel = World.instance().getCurrentLevelNumber();
                    World.instance().reset();
                    World.instance().setCurrentLevel(currentLevel);
                    World.instance().getCurrentLevel().setCurrentScreen(
                        World.instance().getCurrentLevel().getBaseScreen());
                    playerDead.set(false);
                    World.instance().setIsPaused(false);
                    World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());
                });
            }
            else
            {
                resumeView.setOnMouseClicked(e->{World.instance().setIsPaused(false);
                    World.instance().getCurrentLevel().getObserver().Initialize(World.instance().isLoaded());World.instance().playMusic();});
            }
            saveView.setOnMouseClicked(e->{
                try {
                    World.instance().save("savegame.dat");
                } catch (IOException e1) {
                    System.out.println("Oops Lol");
                }
            });
            saveExitView.setOnMouseClicked(e->{
                try {
                    World.instance().save("savegame.dat");
                    World.reset();
                    Platform.exit();
                    //((Stage)gameWindow.getScene().getWindow()).close();

                } catch (IOException e1) {
                    System.out.println("Oops Lol");
                }
            });
            exitView.setOnMouseClicked(e->{var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Scene scene;
            try {
                scene = new Scene(loader.load());
                var stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Cutting Corners Release Candidate");
                stage.getIcons().add(new Image("media/windowicon.png"));
                stage.show();
                MainWindow mainWindow = loader.getController();
                Platform.exit();
                //World.reset();
                //((Stage)gameWindow.getScene().getWindow()).close();
            } catch (IOException e1) {
                System.out.println("Couldn't Open main");
            }});
            gameWindow.getChildren().add(pauseView);
            gameWindow.getChildren().add(resumeView);
            gameWindow.getChildren().add(saveView);
            gameWindow.getChildren().add(saveExitView);
            gameWindow.getChildren().add(exitView);
            gameWindow.getChildren().add(playerDied);

            playerDied.setVisible(playerDead.get());
            pauseView.setVisible(World.instance().getIsPaused());
            resumeView.setVisible(World.instance().getIsPaused());
            saveExitView.setVisible(World.instance().getIsPaused());
            saveView.setVisible(World.instance().getIsPaused());
            exitView.setVisible(World.instance().getIsPaused());

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
        item.setUnDroppedCountdown(this::showEffectTimer);
    }

    @FXML
    void Notify(String text, DroppedItem item){
        if (text == null){unNotify(item);}
        Label notification = new Label(text);
        notification.setUserData("Notification");
        notification.getStyleClass().add("notification");
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
    void showEffectTimer(Effect effect, String effectName, String icon){
        // gameWindow.getChildren().remove(effectBox);
        // effectBox.relocate(950*ratioWidth, 200*ratioHeight);
        
        VBox effectDropdown = new VBox();
        Label effectTitle = new Label(effectName);

        Image image = new Image(icon);
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(25);
        imageview.setPreserveRatio(true);

        Label duration = new Label();
        HBox row2 = new HBox();
        
        row2.getChildren().add(imageview);

        duration.textProperty().bind(effect.DurationProperty().divide(50).asString());

        if (effect.getDuration() <= 99999){row2.getChildren().add(duration);}
        effectDropdown.getChildren().add(effectTitle);
        effectDropdown.getChildren().add(row2);
        effectDropdown.setUserData(effect);
        effectDropdown.getStyleClass().add("notification");
        effectDropdown.visibleProperty().bind(effect.DurationProperty().greaterThan(1));

        for (Node node: effectBox.getChildren()){
            if (node.getUserData() == null){continue;}
            if (node.getUserData().equals(effect)){return;}
        }

        effectBox.getChildren().add(effectDropdown);
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
        if((enemy instanceof Boss))
        {
            if(!((Boss)enemy instanceof Circle))
            {
                healthBar.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2 + 5));
                healthBar.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2 + 640));
            }
            else
            {
                healthBar.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2 + 5));
                healthBar.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2 + 50));
            }
            
        }
        else
        {
            healthBar.layoutYProperty().bind(enemy.getYProperty().add(enemy.getSize() / 2 + 5));
            healthBar.layoutXProperty().bind(enemy.getXProperty().add(enemy.getSize() / 2 + 50));
        }
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
    
    @FXML
    void ratioImage(ImageView view)
    {
        view.setFitHeight(800);
        view.setFitWidth(1280);
        gameWindow.getChildren().add(view);
        view.toBack();
    }

    
}
