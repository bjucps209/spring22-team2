import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow {
    @FXML
    void onStart(ActionEvent e) throws IOException {
        var loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
        var scene = new Scene(loader.load());

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hello"); // Title of main window
        stage.show();
        GameWindow window = loader.getController();
        window.Initialize();
    }
}
