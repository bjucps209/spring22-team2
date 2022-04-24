import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/* 
GameWindow gameWindow = loader.getController();
int gridSize = (int)sldGridSize.getValue();
gameWindow.initialize(gridSize); */


//<Pane fx:id="gameBuilder" style="-fx-background-color: lightgray" />
public class MainWindow implements LevelObserver {
    //Stuff for window dimensions
    protected Stage theStage; //don't ask
    protected Vector windowSize;

    //Object Button lists
    protected ObjectSelectorCompiler objectBtnCompiler;
    @FXML VBox VBObjectBtnLocation;
    @FXML CustomButton currObjButton; //Obj Button currently clicked

    //Panes
    @FXML ArrayList<Pane> thePanes;

    //@FXML VBox vbWindow;
    @FXML HBox screenBox;
    @FXML Pane currentScreen;
    @FXML Label lblCurScreenID;
    protected ScreenNavPanelState navPanelState = ScreenNavPanelState.MOVE;

    //Current Object Stuff
    protected Node currentObj;
    @FXML Label lblcoName; @FXML Label lblcoID;
    @FXML Label lblcoType; @FXML Label lblcoCoord;
    @FXML Button btnDeleteCurrentObj;

    //Nav Panel
    @FXML Button btnCreate; @FXML Button btnDelete;
    @FXML Button btnNorth; @FXML Button btnSouth;
    @FXML Button btnEast; @FXML Button btnWest;
    @FXML Button btnUp; @FXML Button btnDown;

    //Other Stuff
    @FXML Label lblBtnListSetName; //For ObjBtn Layout Navigation
    @FXML Label lblBtnSetCount;    //
    @FXML Label lblErrorMsg;
    @FXML TextField txtFileName;
    @FXML Label lblslOutcome;

    @FXML
    void initialize() {
        DataManager.DaMan().setMrObserver(this);
        DimensionMan.DiMan(); // initialize 
        objectBtnCompiler = new ObjectSelectorCompiler(VBObjectBtnLocation, this, lblBtnListSetName, lblBtnSetCount);
        objectBtnCompiler.compileLists();
        objectBtnCompiler.pushCurrentBtnSet();
        thePanes = new ArrayList<Pane>();
        createScreen("0,0,0");
        
        disableNavButtons();
        btnDelete.setDisable(true);
        btnDeleteCurrentObj.setDisable(true);
    }

    /// Misc
    //
    @FXML void onListGroupPrevClicked(ActionEvent event) {
        objectBtnCompiler.prevObjectSet().pushCurrentBtnSet();
        //lblBtnListSetName.setText(objectBtnCompiler.getCurrentListSet().getGroupName());
    }
    @FXML void onListGroupNextClicked(ActionEvent event) {
        objectBtnCompiler.nextObjectSet();
        objectBtnCompiler.pushCurrentBtnSet();
        //lblBtnListSetName.setText(objectBtnCompiler.getCurrentListSet().getGroupName());
    }
    @FXML void onListPrevClicked(ActionEvent event) {
        objectBtnCompiler.prevObjectPage();
        objectBtnCompiler.pushCurrentBtnSet();
        //var cls = objectBtnCompiler.getCurrentListSet();
        //lblBtnSetCount.setText(String.valueOf(cls.getCurrentVBoxIndex() + 1) + " of " + cls.getListSet().size());
    }
    @FXML void onListNextClicked(ActionEvent event) {
        objectBtnCompiler.nextObjectPage();
        objectBtnCompiler.pushCurrentBtnSet();
        //var cls = objectBtnCompiler.getCurrentListSet();
       // lblBtnSetCount.setText(String.valueOf(cls.getCurrentVBoxIndex() + 1) + " of " + cls.getListSet().size());
    }

    @FXML
    void onBackgroundBtnClicked(ActionEvent event) {
        CustomButton btn = (CustomButton)event.getSource();
        DataManager.DaMan().getCurrentScreen().setBackgroundPathName(btn.getName());

        BackgroundSize what = new BackgroundSize(100, 100, true, true, true, true); //Does a thing
        BackgroundImage thing = new BackgroundImage(btn.getImg(), null, null, null, what); //Does another thing
        Background bg = new Background(thing); //idk
        currentScreen.setBackground(bg); //Byeas
    }

    @FXML
    void onLoadFileClicked(ActionEvent event) throws FileNotFoundException, IOException {
        String filename = txtFileName.getText();

        String msg = DataManager.DaMan().load(filename);
        lblslOutcome.setText(msg);
    }
    @FXML
    void onSaveFileClicked(ActionEvent event) throws Exception {
        String filename = txtFileName.getText();
        try {
            if (filename != null | filename != "") {
               String outcome = DataManager.DaMan().save(filename, false);
               lblslOutcome.setText(outcome);
            }
        }
        catch (Exception e) {
            lblslOutcome.setText("Filename is invalid");
            System.out.println(e);
        }
    }

    ///Screen Control System
    
    @FXML
    void onCreateClicked(ActionEvent event) {
        switch (navPanelState) {
            case MOVE:
                navPanelState = ScreenNavPanelState.CREATE; //nav buttons now create screens
                btnCreate.setText("Cancel");
                break;
            case CREATE:
                navPanelState = ScreenNavPanelState.MOVE; //Create is cancelled
                btnCreate.setText("Create");
                break;
            case DELETE: //Deletion occurs
                navPanelState = ScreenNavPanelState.MOVE;
                DataManager.DaMan().deleteScreen();
                btnCreate.setText("Create");
                btnDelete.setText("Delete");
                
        }
        disableNavButtons();
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        switch (navPanelState) {
            case MOVE, CREATE: //Enables deletion
                navPanelState = ScreenNavPanelState.DELETE;            
                btnCreate.setText("Confirm");
                btnDelete.setText("Cancel");
                break;
            case DELETE: //Cancels Deletion
                navPanelState = ScreenNavPanelState.MOVE;
                btnCreate.setText("Create");
                btnDelete.setText("Delete");
                break;
        }
    }

    @FXML
    void onNavButtonClicked(ActionEvent event) {
        Direction[] dir = new Direction[] {Direction.North, Direction.South,Direction.East,Direction.West, Direction.Up, Direction.Down};
        Button[] buttonnames = new Button[] {btnNorth, btnSouth, btnEast, btnWest, btnUp, btnDown};
        
        var sourcebtn = (Button)event.getSource(); //Creates button reference        
        for (int i = 0; i < buttonnames.length; i++ ) {
            if (sourcebtn == buttonnames[i]) { //Chooses what to do depending on button
                switch (navPanelState){ //Chooses action
                    case MOVE:
                        DataManager.DaMan().attemptMoveToScreen(dir[i]);
                        break;
                    case CREATE:
                        DataManager.DaMan().attemptCreateScreen(dir[i]);
                        break;
                    case DELETE:
                        System.out.println("MWonNavButtonCLicked, DELETE section accessed (not good)");
                }
            }
        } 
    }

    //Disables appropriate navigation buttons (btnNorth, btnEast, btnDelete...)
    public void disableNavButtons() {
        Screen[] surroundingScreens = DataManager.DaMan().getCurrentScreen().getAdjacentScreens();
        Button[] bleck = new Button[] {btnNorth, btnSouth, btnEast, btnWest, btnUp, btnDown};
        
        for (int dir = 0; dir < bleck.length; dir++) {
            if (surroundingScreens[dir] == null) {
                bleck[dir].setDisable(navPanelState == ScreenNavPanelState.MOVE);
            } else {
                bleck[dir].setDisable(navPanelState == ScreenNavPanelState.CREATE);
            }
        }
        btnDelete.setDisable(thePanes.size() == 1); //Disables delete button if only one screen
    }

    /// Object Control System
    //
    @FXML void onObjectBtnClicked(ActionEvent event){
        CustomButton btnClicked = (CustomButton)event.getSource();
        if(currObjButton != null) {
            currObjButton.setText(currObjButton.getName());
        }
        currObjButton = btnClicked;
        btnClicked.setText(btnClicked.getName() + "/n" + "Selected");
    }

    @FXML void onPaneClicked(MouseEvent event) {
        if (currObjButton == null) { return; }
        double xx = event.getX(); event.getSceneX(); //event.getSceneX();
        double yy = event.getY(); //event.getSceneY()
        Vector topleftcell = DimensionMan.DiMan().coordstoGrid(yy, xx);
        DataManager.DaMan().createObject(currObjButton.getName(), currObjButton.getImgPath(), currObjButton.getObjType(), topleftcell, currObjButton.getDimensions());
    }

    @FXML void onDeleteObjClicked(ActionEvent event) {
        DataManager.DaMan().deleteObject((int)currentObj.getUserData());
    }

    void updateCurrentObject(Node newNode){
        if (currentObj != null) {
            currentObj.getStyleClass().clear(); 
        }
        currentObj = newNode;
        if (currentObj != null) {
            currentObj.getStyleClass().add("current");
            btnDeleteCurrentObj.setDisable(false);
        } else {
            btnDeleteCurrentObj.setDisable(true);
        }
    }

    void updateCurrentObjectInfo(LvLObject obj) {
        if (obj != null) {

        lblcoName.setText(obj.getName());
        lblcoID.setText(String.valueOf(obj.getId()));
        lblcoType.setText(String.valueOf(obj.getObjType()));
        lblcoCoord.setText(String.valueOf(obj.getTopLeftCell().getX()) +", " + String.valueOf(obj.getTopLeftCell().getY()));
        } else {
            lblcoName.setText(""); lblcoID.setText(""); lblcoType.setText(""); lblcoCoord.setText(""); 
        }
    }

    ///Observer functions
    //
    @Override
    public void createScreen(String StrID) { //Needs work
        currentScreen = new Pane();          //         no... :00
        thePanes.add(currentScreen);
        currentScreen.setMinSize(DimensionMan.screenwidth, DimensionMan.screenheight);
        currentScreen.setMaxSize(DimensionMan.screenwidth, DimensionMan.screenheight);
        currentScreen.setStyle("-fx-background-color: lightgray");
        currentScreen.setUserData(StrID);
        currentScreen.setOnMouseClicked(this::onPaneClicked);
        screenBox.getChildren().clear();
        screenBox.getChildren().add(currentScreen);
        lblCurScreenID.setText(StrID);

        //Switch the buttons back to normal depending on nav panel state
        if (navPanelState == ScreenNavPanelState.CREATE) {
            onCreateClicked(null);
        }
        updateCurrentObject(null);
        updateCurrentObjectInfo(null);
    }

    @Override
    public void movetoScreen(String StrID) { 
        for (Pane aPane : thePanes) {
            if (aPane.getUserData().equals(StrID)) {
                screenBox.getChildren().clear();
                screenBox.getChildren().add(aPane);
                currentScreen = aPane;
                lblCurScreenID.setText(StrID);
            }
        }
        disableNavButtons();
        updateCurrentObject(null);
        updateCurrentObjectInfo(null);
    }

    public enum ScreenNavPanelState {
        MOVE, CREATE, DELETE
    }

    @Override
    public void deleteCurrentScreen(String oldStrID, String newStrID) {
        Pane delPane = null;
        for (Pane paaa : thePanes) {
            if (paaa.getUserData().equals(oldStrID)) {
                delPane = paaa;
             } /*else if (paaa.getUserData().equals(newStrID)) {
                currentScreen = paaa;
            } */
        }
        movetoScreen(newStrID);
        thePanes.remove(delPane);

        updateCurrentObject(null);
        updateCurrentObjectInfo(null);
    }

    @Override
    public void addLvLObject(LvLObject theObject) { //Rework ___________________________________________________________________________
        double imgwidth = theObject.getDimensions().getX() * DimensionMan.DiMan().getCellLength();
        double imgheight = theObject.getDimensions().getY() * DimensionMan.DiMan().getCellLength();
        double[] coord = DimensionMan.DiMan().gridtoCoords((theObject.getTopLeftCell()));

        try {
            Image img = new Image(theObject.getImgPath());
            ImageView newObj = new ImageView(img);
            newObj.setUserData(theObject.getId());
            newObj.setFitWidth(imgwidth); newObj.setFitHeight(imgheight);
            newObj.setLayoutY(coord[0]); newObj.setLayoutX(coord[1]);
            currentScreen.getChildren().add(newObj);

            makeInteractive(newObj);
        } catch (IllegalArgumentException e) {
            ImageView newObj = new ImageView(new Image("uhohstinky.png"));
            newObj.setUserData(theObject.getId());
            newObj.setFitWidth(imgwidth); newObj.setFitHeight(imgheight);
            newObj.setLayoutY(coord[0]); newObj.setLayoutX(coord[1]);
            currentScreen.getChildren().add(newObj);

            makeInteractive(newObj);
        }
    }

    @Override
    public void moveLvLObject(LvLObject theObject) {
        Node curObj = findObjectOnScreen(theObject.getId());
        double[] blah = DimensionMan.DiMan().gridtoCoords(theObject.getTopLeftCell());
        curObj.relocate(blah[1], blah[0]);

        updateCurrentObjectInfo(theObject);
    }

    @Override
    public void deleteLvLObject(int id) {
        currentScreen.getChildren().remove(currentObj);
        updateCurrentObject(null);
        updateCurrentObjectInfo(null);
    }

    //Finds Node with id
    private Node findObjectOnScreen(int id) {
        return currentScreen.getChildren()
        .stream()
        .filter(cr -> (int)cr.getUserData() == id)
        .findFirst().get();
    }

    @Override
    public void updateActionStatement(String statementMsg) {
        lblErrorMsg.setText(statementMsg);
        System.out.println(statementMsg);
    }
    

    ///Borrowed from Dr. Schaub again because I didn't feel like remembering how
    // From https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub
    private void makeInteractive(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND) );
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT) );
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);

            updateCurrentObject(node);
            updateCurrentObjectInfo(DataManager.DaMan().getCurrentScreen().findObject((int)node.getUserData()));
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(me -> { 
            node.getScene().setCursor(Cursor.HAND);
            DataManager.DaMan().moveObject((int)node.getUserData(), DimensionMan.DiMan().objCenterCoordstoGrid(node.getLayoutY(), node.getLayoutX()));
         } );

        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        node.setOnMouseClicked(me -> me.consume());
    }

    private class Delta {
        public double x;
        public double y;
    }

    @Override
    public void populateScreens(ArrayList<Screen> screens) {
        System.out.println("MW populateScreens");
        thePanes.clear();
        Pane paneWithPlayer = null;
        boolean playerfound = false;
        for (Screen scr : screens) {
            createScreen(scr.getStrID());
            try {
                BackgroundSize what = new BackgroundSize(100, 100, true, true, true, true); //Does a thing
                BackgroundImage thing = new BackgroundImage( new Image(scr.getBackgroundPathName()), null, null, null, what); //Does another thing
                Background bg = new Background(thing); //idk
                currentScreen.setBackground(bg); //Byeas
            } catch (Exception e) {}
            for(LvLObject obj : scr.getObjects()) {
                if (obj.getObjType() == ObjType.Player) {
                    paneWithPlayer = currentScreen;
                    playerfound = true;
                }
                addLvLObject(obj);
            }
        }
        if (playerfound) { 
            currentScreen = paneWithPlayer;
            movetoScreen((String)paneWithPlayer.getUserData());
        }
    }

}
