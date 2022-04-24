import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import model.Direction;
import model.EntityObserver;

public class EntityImageView extends ImageView implements EntityObserver{
    EntityImageView(Image img)
    {
        super(img);
        this.setRotationAxis(Rotate.Y_AXIS);
    }
    public void changeImage(String i,Direction d)
    {
        super.setImage(new Image(i));
        if(d==Direction.right)
        {
            this.setRotate(180);
        }
        else
        {
            this.setRotate(0);
        }
    }
}
