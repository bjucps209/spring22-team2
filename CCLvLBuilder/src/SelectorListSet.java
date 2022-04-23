import java.util.ArrayList;

import javafx.scene.layout.VBox;

public class SelectorListSet {
    private String groupName;
    private int currentVBoxIndex;
    private ArrayList<VBox> listSet;
    private int lastBoxBtnCount; //amount of btns in latest VBox

    public SelectorListSet(String groupname) {
        groupName = groupname;
        listSet = new ArrayList<VBox>();
        listSet.add(new VBox());
        currentVBoxIndex = 0;
        lastBoxBtnCount = 0;
    }

    public void addCButton(CustomButton newbtn) {
        lastBoxBtnCount++;
        if (lastBoxBtnCount > 5) {
            listSet.add(new VBox());
            lastBoxBtnCount = 1;
        }
        listSet.get(listSet.size() - 1).getChildren().add(newbtn);
    }

    public VBox nextVBox() {
        currentVBoxIndex++;
        if (currentVBoxIndex >= listSet.size()) {
            currentVBoxIndex = 0;
        }
        return getCurrentVBox();
    }

    public VBox prevVBox() {
        currentVBoxIndex--;
        if (currentVBoxIndex < 0) {
            currentVBoxIndex = listSet.size() - 1;
        }
        return getCurrentVBox();
    }

    public void resetCurrentVBox() {
        currentVBoxIndex = 0;
    }

    public VBox getCurrentVBox() {
        return listSet.get(currentVBoxIndex);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<VBox> getListSet() {
        return listSet;
    }

    public void setListSet(ArrayList<VBox> listSet) {
        this.listSet = listSet;
    }

    public int getCurrentVBoxIndex() {
        return currentVBoxIndex;
    }

    public void setCurrentVBoxIndex(int currentVBoxIndex) {
        this.currentVBoxIndex = currentVBoxIndex;
    }

    
}
