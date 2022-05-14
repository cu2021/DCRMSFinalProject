package Views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Stage {

    public Main() throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader(
                getClass().getResource("main.fxml"));
        Scene scene;
        scene = new Scene(fXMLLoader.load());
        this.setTitle("Main Screen");
        Image i = new Image("/Images/logo.png");
        this.getIcons().add(i);
        this.setScene(scene);
    }

}
