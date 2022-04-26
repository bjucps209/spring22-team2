//-----------------------------------------------------------
//File:   .java
//Desc:   Compiles a list of buttons created from the
//        list of pictures.
//          Currently in I don't care anymore version
//----------------------------------------------------------- 
import java.util.ArrayList;

import Model.ObjType;
import Model.Vector;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class ObjectSelectorCompiler {
    MainWindow theWindow; //Okay to do probably
    Label lblBtnListSetNameCtrl;
    Label lblBtnSetCountCtrl;

    VBox setLocation;
    SelectorListSet specialSet, entitySet, obstacleSet, itemSet ,backgroundSet;

    static final String[] backgroundNamesFirst = new String[] {"medievalfourway", "medievalonewaydown", "medievalonewayleft", "medievalonewayright", "medievalonewayup", "medievalthreewaydown", "medievalthreewayleft", "medievalthreewayright", "medievalthreewayup", "medievaltwowayhorizontal", "medievaltwowayvertical" };
    static final String[] backgroundNamesSecond = new String[] {"desertfourway", "desertonewaydown", "desertonewayleft", "desertonewayright", "desertonewayup", "desertthreewaydown", "desertthreewayleft", "desertthreewayright", "desertthreewayup", "deserttwowayhorizontal", "deserttwowayvertical" };
    static final String[] backgroundNamesThird = new String[] {"cavemanfourway", "cavemanonewayleft", "cavemanonewayright", "cavemanonewayup", "cavemanthreewaydown", "cavemanthreewayleft", "cavemanthreewayright", "cavemanthreewayup", "cavemantwowayhorizontal", "cavemantwowayvertical"};
    static final String[] backgroundNamesFourth = new String[] {"Bossroom", "secretfourway", "secretonewaydown", "secretonewayleft", "secretonewayright", "secretonewayup", "secretthreewaydown", "secretthreewayleft", "secretthreewayright", "secretthreewayup", "secrettwowayhorizontal", "secrettwowayvertical"};
    static final String[][] backgroundNames = new String[][] {backgroundNamesFirst, backgroundNamesSecond, backgroundNamesThird, backgroundNamesFourth};

    static final String[] entityNamesFirst = new String[] { "evil", "triangle", "square", "hexagon", "octagon"};
    static final String[] entityNamesBosses = new String[] {"pyramidboss", "cube", "dodeca"};

    static final String[] obstacleNames = new String[] { "Medieval/rock", "Medieval/tree", "Egypt/cactus", "Egypt/pyramid", "Caveman/boulder", "Caveman/deadbush", "Secret&Boss/lantern"};

    static final String[] itemNames = new String[] { "Bow", "Arrow", "Fireball", "Mace", "Cirkyle's Sword"};

    ArrayList<SelectorListSet> setList; //List of all the Sets
    private int currentSelectorSet; //Current set index

    private static final int BtnImgSize = 100;



    public ObjectSelectorCompiler(VBox btnlocation, MainWindow mw, Label lblbtnlsnc, Label lblbtnscc) {
        setLocation = btnlocation;
        theWindow = mw;
        lblBtnListSetNameCtrl = lblbtnlsnc;
        lblBtnSetCountCtrl = lblbtnscc;

        specialSet = new SelectorListSet("Special");
        entitySet = new SelectorListSet("Entities");
        obstacleSet = new SelectorListSet("Obstacles");
        itemSet = new SelectorListSet("Items");
        backgroundSet = new SelectorListSet("Backgrounds");

        setList = new ArrayList<SelectorListSet>();
        //Determines the order of the lists
        setList.add(specialSet);
        setList.add(entitySet);
        setList.add(obstacleSet);
        setList.add(itemSet);
        setList.add(backgroundSet);

        currentSelectorSet = 0;
    }

    public void compileLists() {
        compileSpecial();
        compileEntity();
        compileObstacle();
        compileItem();   
        compileBackground();
    }

    private void compileSpecial() {
        //Player
        Image playerimage = new Image("media/Player/Cirkyle v1.png");
        CustomButton anotherbutton = new CustomButton("Cirkyle v1", "media/Player/Cirkyle v1.png", true, playerimage, ObjType.Player, BtnImgSize, BtnImgSize, new Vector(1,1));
        anotherbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
        specialSet.addCButton(anotherbutton);

    }

    private void compileEntity() {
        String sharedpath = "media/Enemies/";
        for (String name : entityNamesFirst) {
            String imgPath = sharedpath + name + ".png";   
            var newbutton = new CustomButton(name, imgPath, true, new Image(imgPath), ObjType.Entity, BtnImgSize, BtnImgSize, new Vector(1, 1));
            newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
            entitySet.addCButton(newbutton);
        }


        
        for (String name : entityNamesBosses) {
            String imgPath = sharedpath + name + ".png";
            var newbutton = new CustomButton(name, imgPath, true, new Image(imgPath), ObjType.Boss, BtnImgSize, BtnImgSize, new Vector(3, 7));
            newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
            entitySet.addCButton(newbutton);
        }
    }

    public void compileObstacle() {
        String sharedpath = "media/Terrain/";
        for (String obstName : obstacleNames) {
            String imgPath = sharedpath + obstName + ".png";   
            var newbutton = new CustomButton(  obstName.split("/")[1], imgPath, true, new Image(imgPath), ObjType.Obstacle, BtnImgSize, BtnImgSize, new Vector(1, 1));
            newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
            obstacleSet.addCButton(newbutton);
        }
    }

    public void compileItem() {
        String sharedpath = "media/Player/";
        for (String name : itemNames) {
            String imgPath = sharedpath + name + ".png";   
            var newbutton = new CustomButton(name, imgPath, true, new Image(imgPath), ObjType.Item, BtnImgSize, BtnImgSize, new Vector(1, 1));
            newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
            itemSet.addCButton(newbutton);
        }
    }

    private void compileBackground() {
        String[] blah = new String[] {"Medieval", "Egypt", "Caveman", "Secret&Boss"};
        for (int list = 0; list < backgroundNames.length; list++) { //String[] bgLists : backgroundNames
            for (String bgName : backgroundNames[list]) {
                String imgPath = "media/Terrain/" + blah[list] + "/" + bgName + ".png";
                var backgroundimage = new Image(imgPath);
                var newbutton = new CustomButton(imgPath, imgPath, false, backgroundimage, null, 160, 90, null);
                newbutton.setOnAction((e) -> theWindow.onBackgroundBtnClicked(e));
                backgroundSet.addCButton(newbutton);        
            }
        }
    }

    public void pushCurrentBtnSet() {
        VBox curBox = getCurrentListSet().getCurrentVBox();
        setLocation.getChildren().clear();
        setLocation.getChildren().add(curBox);
        lblBtnListSetNameCtrl.setText(getCurrentListSet().getGroupName());
        lblBtnSetCountCtrl.setText(   String.valueOf(getCurrentListSet().getCurrentVBoxIndex() + 1) + " of " + getCurrentListSet().getListSet().size());
    }

    public void nextObjectSet() {
        currentSelectorSet++;
        if (currentSelectorSet >= setList.size()) {
            currentSelectorSet = 0;
        }
        getCurrentListSet().resetCurrentVBox();
        pushCurrentBtnSet();
    }

    public ObjectSelectorCompiler prevObjectSet() {
        currentSelectorSet--;
        if (currentSelectorSet < 0) {
            currentSelectorSet = setList.size() - 1;
        }
        getCurrentListSet().resetCurrentVBox();
        pushCurrentBtnSet();
        return this;
    }

    public void nextObjectPage() {
        var curListSet = getCurrentListSet();
        curListSet.nextVBox();
        pushCurrentBtnSet();
    }

    public void prevObjectPage() {
        var curListSet = getCurrentListSet();
        curListSet.prevVBox();
        pushCurrentBtnSet();
    }

    public SelectorListSet getCurrentListSet() {
        return setList.get(currentSelectorSet);
    }

    public void setMainWindow(MainWindow window) {
        theWindow = window;
    }

}
