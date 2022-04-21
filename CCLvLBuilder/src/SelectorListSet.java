import java.util.ArrayList;

import javafx.scene.layout.VBox;

public class SelectorListSet {
    private int currentVBox;
    private ArrayList<VBox> listSet;
    private int lastBoxBtnCount; //amount of btns in latest VBox

    public SelectorListSet() {
        listSet = new ArrayList<VBox>();
        listSet.add(new VBox());
        currentVBox = 0;
        lastBoxBtnCount = 0;
    }

    public void addCButton(CustomButton newbtn) {
        lastBoxBtnCount++;
        if (lastBoxBtnCount > 5) {
            listSet.add(new VBox());
            lastBoxBtnCount = 0;
        }
        listSet.get(listSet.size() - 1).getChildren().add(newbtn);
    }

    public VBox getCurrentVBox() {
        return listSet.get(currentVBox);
    }

    public VBox nextVBox() {
        currentVBox++;
        if (currentVBox >= listSet.size()) {
            currentVBox = 0;
        }
        return getCurrentVBox();
    }

    public VBox prevVBox() {
        currentVBox--;
        if (currentVBox < 0) {
            currentVBox = listSet.size() - 1;
        }
        return getCurrentVBox();
    }

    public void resetCurrentVBox() {
        currentVBox = 0;
    }
}
