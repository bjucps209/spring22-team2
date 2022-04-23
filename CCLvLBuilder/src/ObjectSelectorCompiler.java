//-----------------------------------------------------------
//File:   .java
//Desc:   Compiles a list of buttons created from the
//        list of pictures.
//          Currently in test version
//----------------------------------------------------------- 
import java.util.ArrayList;

import Model.ObjType;
import Model.Vector;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class ObjectSelectorCompiler {
    MainWindow theWindow; //can do probably
    Label lblBtnListSetNameCtrl;
    Label lblBtnSetCountCtrl;

    VBox setLocation;
    SelectorListSet specialSet, backgroundSet;

    static final String[] backgroundNamesFirst = new String[] {"medievalfourway", "medievalonewaydown", "medievalonewayleft", "medievalonewayright", "medievalonewayup", "medievalthreewaydown", "medievalthreewayleft", "medievalthreewayright", "medievalthreewayup", "medievaltwowayhorizontal", "medievaltwowayvertical" };
    static final String[] backgroundNamesSecond = new String[] {"desertfourway", "desertonewaydown", "desertonewayleft", "desertonewayright", "desertonewayup", "desertthreewaydown", "desertthreewayleft", "desertthreewayright", "desertthreewayup", "deserttwowayhorizontal", "deserttwowayvertical" };
    static final String[] backgroundNamesThird = new String[] {"cavemanfourway", "cavemanonewayleft", "cavemanonewayright", "cavemanonewayup", "cavemanthreewaydown", "cavemanthreewayleft", "cavemanthreewayright", "cavemanthreewayup", "cavemantwowayhorizontal", "cavemantwowayvertical"};
    static final String[] backgroundNamesFourth = new String[] {"Bossroom", "secretfourway", "secretonewaydown", "secretonewayleft", "secretonewayright", "secretonewayup", "secretthreewaydown", "secretthreewayleft", "secretthreewayright", "secretthreewayup", "secrettwowayhorizontal", "secrettwowayvertical"};
    static final String[][] backgroundNames = new String[][] {backgroundNamesFirst, backgroundNamesSecond, backgroundNamesThird, backgroundNamesFourth};

    ArrayList<SelectorListSet> setList; //List of all the Sets
    private int currentSelectorSet; //Current set index

    private static final int BtnImgSize = 100;
    
    public ObjectSelectorCompiler(VBox btnlocation, MainWindow mw, Label lblbtnlsnc, Label lblbtnscc) {
        setLocation = btnlocation;
        theWindow = mw;
        lblBtnListSetNameCtrl = lblbtnlsnc;
        lblBtnSetCountCtrl = lblbtnscc;

        specialSet = new SelectorListSet("Special");
        backgroundSet = new SelectorListSet("Backgrounds");

        setList = new ArrayList<SelectorListSet>();
        setList.add(backgroundSet);
        setList.add(specialSet);


        currentSelectorSet = 0;
    }

    public void compileLists() {
        compileBackground();
        compileSpecial();
    }

    private void compileSpecial() {
        Image playerimage = new Image("TempImages/Cirkyle v1.png");
        CustomButton newbutton = new CustomButton("Cirkyle v1", "TempImages/Cirkyle v1.png", true, playerimage, ObjType.Player, BtnImgSize, BtnImgSize, new Vector(2, 1));
        newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
        specialSet.addCButton(newbutton);

        CustomButton anotherbutton = new CustomButton("Cirkyle v1", "TempImages/Cirkyle v1.png", true, playerimage, ObjType.Player, BtnImgSize, BtnImgSize, new Vector(1,1));
        anotherbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
        specialSet.addCButton(anotherbutton);
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
