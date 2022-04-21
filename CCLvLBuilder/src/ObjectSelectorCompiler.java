//-----------------------------------------------------------
//File:   .java
//Desc:   Compiles a list of buttons created from the
//        list of pictures.
//          Currently in test version
//----------------------------------------------------------- 
import java.util.ArrayList;

import Model.Cell;
import Model.ObjType;
import Model.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ObjectSelectorCompiler {
    MainWindow theWindow; //can do probably
    VBox setLocation;
    SelectorListSet specialSet;
    ArrayList<SelectorListSet> setList;
    
    private int currentSelectorSet;

    private static final int BtnImgSize = 100; 
    
    public ObjectSelectorCompiler(VBox btnlocation, MainWindow mw) {
        setLocation = btnlocation;
        theWindow = mw;
        specialSet = new SelectorListSet();
        setList = new ArrayList<SelectorListSet>();
        setList.add(specialSet);
        currentSelectorSet = 0;
    }

    public void compileStuff() {
        compileSpecial();
    }

    public void compileLists() {}

    public void compileSpecial() {
        Image playerimage = new Image("TempImages/Cirkyle v1.png");
        CustomButton newbutton = new CustomButton("Cirkyle v1" , playerimage, ObjType.Player, BtnImgSize, new Vector(1, 1));
        newbutton.setOnAction((e) -> theWindow.onObjectBtnClicked(e));
        specialSet.addCButton(newbutton);
    }

    public void pushCurrentBtnSet() {
        VBox curBox = getCurrentListSet().getCurrentVBox();
        setLocation.getChildren().clear();
        setLocation.getChildren().add(curBox);
    }

    public void nextObjectSet() {
        currentSelectorSet++;
        if (currentSelectorSet >= setList.size()) {
            currentSelectorSet = 0;
        }
        getCurrentListSet().resetCurrentVBox();
        pushCurrentBtnSet();
    }

    public void prevObjectSet() {
        currentSelectorSet--;
        if (currentSelectorSet < 0) {
            currentSelectorSet = setList.size() - 1;
        }
        getCurrentListSet().resetCurrentVBox();
        pushCurrentBtnSet();
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
