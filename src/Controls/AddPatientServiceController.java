/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controls;

import LogFile.LogFileRecording;
import Models.Patient;
import Views.Main;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class AddPatientServiceController implements Initializable {

    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField idTxtField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<String> genderCompobox;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField phoneNumberField;
    private EntityManagerFactory emf;
    private LogFileRecording lgFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderCompobox.getItems().addAll("Female", "Male");
        emf
                = Persistence
                        .createEntityManagerFactory("DentalClinicReceptionistManagentSystemPU");
        lgFile = LogFileRecording.getLogFileRecording();
    }

    @FXML
    private void submitButtonHandle(ActionEvent event) {
        try {
            LocalDate localDate = this.dateOfBirthField.getValue();
            Patient p = new Patient(this.nameTxtField.getText(),
                    Integer.parseInt(this.idTxtField.getText()),
                    this.genderCompobox.getValue(),
                    Integer.parseInt(this.phoneNumberField.getText()),
                    this.addressField.getText(),
                    localDate);

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            em.close();
            lgFile.writeActivity("Insert patient "+p);
            emptyTextFields();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Success");
            a.setContentText("Patient has been added successfully(^_^).");
            a.setHeaderText(null);
            a.showAndWait();

        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Be sure of your Input...!");
            a.showAndWait();
        }

    }

    public void emptyTextFields() {
        this.addressField.setText("");
        this.dateOfBirthField.setValue(null);
        this.genderCompobox.setValue("");
        this.idTxtField.setText("");
        this.nameTxtField.setText("");
        this.phoneNumberField.setText("");
    }

    @FXML
    private void cancelButtonHandle(ActionEvent event) throws IOException {
        new Main().show();
        ((Stage) (this.addressField.getScene().getWindow())).close();
    }

}
