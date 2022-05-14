
package Views;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UpdateAppointmentViewer extends Stage{

    public UpdateAppointmentViewer() throws IOException {
        FXMLLoader fXMLLoader = new FXMLLoader(
                getClass().getResource("UpdateAppointment.fxml"));
        Scene scene;
        scene = new Scene(fXMLLoader.load());
        this.setTitle("Update Appointment Form");
        Image i = new Image("/Images/logo.png");
        this.getIcons().add(i);
        this.setScene(scene);
    }
    
}
