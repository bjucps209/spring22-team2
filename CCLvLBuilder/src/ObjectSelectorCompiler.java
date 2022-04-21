//-----------------------------------------------------------
//File:   .java
//Desc:   Compiles a list of buttons created from the
//        list of pictures.
//          Currently in test version
//----------------------------------------------------------- 
import java.util.ArrayList;

import Model.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ObjectSelectorCompiler {
    VBox setLocation;
    SelectorListSet specialSet;
    ArrayList<SelectorListSet> setList;
    
    private int currentSelectorSet;
    
    public ObjectSelectorCompiler(VBox btnlocation) {
        setLocation = btnlocation;
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
        ImageView playerimage = new ImageView(new Image("TempImages/Cirkyle v1.png"));
        playerimage.setFitWidth(100); playerimage.setFitHeight(100);
        CustomButton newbutton = new CustomButton(Cell.Player);
        newbutton.setGraphic(playerimage);
        newbutton.setText("Cirkyle v1");

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

}
