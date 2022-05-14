package dentalclinicreceptionistmanagentsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DentalClinicReceptionistManagentSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/LoginForm.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setTitle("DCMS");
        Image i = new Image("/Images/logo.png");
        stage.getIcons().add(i);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
