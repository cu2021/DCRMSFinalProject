
package Views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class UpdatePatientViewer extends Stage{

    public UpdatePatientViewer() throws IOException {
         FXMLLoader fXMLLoader = new FXMLLoader(
                getClass().getResource("updatePatientService.fxml"));
        Scene scene;
        scene = new Scene(fXMLLoader.load());
        this.setTitle("update Patient");
        Image i = new Image("/Images/logo.png");
        this.getIcons().add(i);
        this.setScene(scene);
    }
    
}
