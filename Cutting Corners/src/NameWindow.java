import java.io.IOException;

import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Coordinates;
import model.PlayerState;
import model.World;

public class NameWindow {
    boolean isLoaded;
    Boolean userCampaign;
    Boolean cheatMode;
    IntegerProperty difficulty;
    String name;
    @FXML
    TextField txtName;
    
    @FXML
    void initialize(boolean isLoaded,boolean userCampaign,boolean cheatMode,IntegerProperty difficulty) {
        txtName.setText("");
        this.isLoaded = isLoaded;
        this.userCampaign=userCampaign;
        this.cheatMode=cheatMode;
        this.difficulty=difficulty;
    }
    @FXML
    void onConfirmPressed(ActionEvent e) throws IOException
    {
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());
        var stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cutting Corners");
        stage.getIcons().add(new Image("media/windowicon.png"));
        stage.show();

        GameWindow gameWindow = loader.getController();
        gameWindow.Initialize(isLoaded, userCampaign,cheatMode,difficulty,name);
        
        gameWindow.updater();
        gameWindow.playerUpdater();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event){
                if(World.instance().getPlayer()!=null)
                {
                    KeyCode keyPressed = event.getCode();
                    if (keyPressed == KeyCode.SPACE){
                        World.instance().getPlayer().setAttackCount(35);
                        World.instance().getPlayer().setState(PlayerState.drinking);
                    }
                    else{
                        World.instance().getPlayer().removeKey(event.getCode());
                        
                        World.instance().getPlayer().addKey(event.getCode());
                    }
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event){
                if(World.instance().getPlayer()!=null)
                {
                    World.instance().getPlayer().removeKey(event.getCode());
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event){
                int xCoord = (int) event.getX();
                int yCoord = (int) event.getY();
                if(World.instance().getPlayer()!=null)
                {
                    World.instance().getPlayer().setMouseCoordinates(new Coordinates(xCoord, yCoord));
                }
            }
        });

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            @Override
            public void handle(MouseEvent event){
                if(World.instance().getPlayer()!=null)
                {
                    World.instance().getPlayer().setEnemies(World.instance().getCurrentLevel().getCurrentScreen().getEnemies());
                    World.instance().getPlayer().setState(PlayerState.attacking);
                    System.out.println(World.instance().getPlayer().getCoords().getxCoord());
                }
            }
        });
        
    }
    @FXML
    void onCancelPressed(ActionEvent e) throws IOException
    {
        txtName.getScene().getWindow().hide();
        var loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        var scene = new Scene(loader.load());
        var stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Cutting Corners");
        stage.getIcons().add(new Image("media/windowicon.png"));
        stage.show();
        MainWindow mainWindow = loader.getController();
        mainWindow.initialize();
    }
}
