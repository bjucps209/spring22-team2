import Model.Cell;
import Model.ObjType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CustomButton extends Button{
    private String name; //Used for obj creation
    private Image Img; //In case I have issues with ImageView duplication
    private ObjType objType;

    public CustomButton(String name, Image img, ObjType ot, int imgsize) {
        super();
        this.name = name;
        Img = img;
        objType = ot;
        var imgv = new ImageView(img);
        setGraphic(imgv);
        setText(name);
        imgv.setFitWidth(imgsize); imgv.setFitHeight(imgsize);
    }

    ///G&S

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImg() {
        return Img;
    }

    public void setImg(Image img) {
        Img = img;
    }

    public ObjType getObjType() {
        return objType;
    }



    public void setObjType(ObjType objType) {
        this.objType = objType;
    }



    public ObjType getType() {
        return objType;
    }

    public void setType(ObjType ot) {
        objType = ot;
    }

}
