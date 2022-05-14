package Controls;

import LogFile.LogFileRecording;
import Views.UpdateAppointmentViewer;
import Views.AddAppointmentViewer;
import Views.UpdatePatientViewer;
import Views.AddPatientViewer;
import dentalclinicreceptionistmanagentsystem.DentalClinicReceptionistManagentSystem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private ImageView AddPatientButton;
    @FXML
    private ImageView upadatePatientButton;
    @FXML
    private ImageView addAppointmentButton;
    @FXML
    private ImageView UpdateAppointmentButton;
    Stage stage;
    @FXML
    private Label titleLabel;
    @FXML
    private Button signOutButton;
    private LogFileRecording lgFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         lgFile = LogFileRecording.getLogFileRecording();
    }

    @FXML
    private void AddPatientButtonHandle(MouseEvent event) throws IOException {
        new AddPatientViewer().show();
        stage = (Stage) (titleLabel.getScene().getWindow());
        stage.close();

    }

    @FXML
    private void upadatePatientButtonHandle(MouseEvent event) throws IOException {
        new UpdatePatientViewer().show();
        stage = (Stage) (titleLabel.getScene().getWindow());
        stage.close();
    }

    @FXML
    private void addAppointmentButtonHandle(MouseEvent event) throws IOException {
        new AddAppointmentViewer().show();
        stage = (Stage) (titleLabel.getScene().getWindow());
        stage.close();
    }

    @FXML
    private void UpdateAppointmentHandle(MouseEvent event) throws IOException {
        new UpdateAppointmentViewer().show();
        stage = (Stage) (titleLabel.getScene().getWindow());
        stage.close();
    }

    @FXML
    private void signOutButtonHandle(ActionEvent event) throws Exception {
        stage = (Stage) (titleLabel.getScene().getWindow());
        new DentalClinicReceptionistManagentSystem().start(stage);
        this.lgFile.writeActivity("User Signed out");
        

    }

}
