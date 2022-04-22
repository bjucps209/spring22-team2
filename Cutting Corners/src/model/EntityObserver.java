package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface EntityObserver{
    void changeImage(String i,boolean flip);
}
