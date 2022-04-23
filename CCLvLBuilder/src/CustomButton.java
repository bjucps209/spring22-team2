import Model.ObjType;
import Model.Vector;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CustomButton extends Button{
    private String name; //Used for obj creation
    private Image Img; //In case I have issues with ImageView duplication
    private ObjType objType;
    private Vector dimensions;

    public CustomButton(String name, boolean showname, Image img, ObjType ot, int imgwidth, int imgheight, Vector dimensions) {
        super();
        this.name = name;
        Img = img;
        objType = ot;
        this.dimensions = dimensions;

        var imgv = new ImageView(img);
        setGraphic(imgv);
        
        if (showname) {setText(name);}
        imgv.setFitWidth(imgwidth); imgv.setFitHeight(imgheight);
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

    public Vector getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector dimensions) {
        this.dimensions = dimensions;
    }

}
