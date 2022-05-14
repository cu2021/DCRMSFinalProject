
package Views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AddAppointmentViewer extends Stage{

    public AddAppointmentViewer() throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader(
                getClass().getResource("AddAppointment.fxml"));
        Scene scene;
        scene = new Scene(fXMLLoader.load());
        this.setTitle("Add Appointment Form");
        Image i = new Image("/Images/logo.png");
        this.getIcons().add(i);
        this.setScene(scene);
    }
    
}
