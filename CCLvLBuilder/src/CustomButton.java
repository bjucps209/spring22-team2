import Model.Cell;
import javafx.scene.control.Button;


public class CustomButton extends Button{
    Cell cellType;

    public CustomButton(Cell ct) {
        super();
        cellType = ct;
    }

    public Cell getCell() {
        return cellType;
    }

    public void setCell(Cell ct) {
        cellType = ct;
    }
}
