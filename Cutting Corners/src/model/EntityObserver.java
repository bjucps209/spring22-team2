package model;

import javafx.scene.image.ImageView;

public interface EntityObserver {
    ImageView image = new ImageView();
    void update();
}
