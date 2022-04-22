package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface EntityObserver{
    void changeImage(Image i,boolean flip);
}
