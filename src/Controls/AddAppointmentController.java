package Controls;

import LogFile.LogFileRecording;
import Models.Appointment;
import Models.Dentist;
import Models.Patient;
import Views.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField nameTxtField;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField searchIdtxtField;
    @FXML
    private Button searchById;
    @FXML
    private ComboBox<String> dentistCompobox;
    private Patient patient;
    private EntityManagerFactory emf;
    private LogFileRecording lgFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf = Persistence
                .createEntityManagerFactory("DentalClinicReceptionistManagentSystemPU");
        EntityManager em = emf.createEntityManager();
        this.dentistCompobox.getItems().clear();
        List<Dentist> dentists = em.createNamedQuery("Dentist.FindAll")
                .getResultList();
        Iterator<Dentist> di = dentists.iterator();
        while (di.hasNext()) {
            this.dentistCompobox.getItems().add(di.next().getName());
        }
        lgFile = LogFileRecording.getLogFileRecording();
    }

    @FXML
    private void submitButtonHandle(ActionEvent event) throws IOException {
        if (!checkFields()) {
            EntityManager em = emf.createEntityManager();
            Dentist dentist = (Dentist) em.createNamedQuery("Dentist.FindByName")
                    .setParameter("name", this.dentistCompobox.getValue())
                    .getSingleResult();
            Appointment appointment = new Appointment(patient, dentist,
                    this.dateOfBirthField.getValue());
            em.getTransaction().begin();
            em.persist(appointment);
            em.getTransaction().commit();
            em.close();
            emptyTextFields();
            lgFile.writeActivity("Insert Appointment "+appointment);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Success Message.");
            a.setContentText("Appointment Added Successfully.");
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Empty Field!");
            a.showAndWait();
        }

    }

    @FXML
    private void cancelButtonHandle(ActionEvent event) throws IOException {
        new Main().show();
        ((Stage) (this.nameTxtField.getScene().getWindow())).close();

    }

    @FXML
    private void searchByIdHandle(ActionEvent event) {
        if (!(this.searchIdtxtField.getText().equalsIgnoreCase("")
                || this.searchIdtxtField.getText() == null)) {
            EntityManager em = emf.createEntityManager();

            try {
                patient = (Patient) em.createNamedQuery("Patient.FindById")
                        .setParameter("identityNo", Integer.parseInt(this.searchIdtxtField.getText()))
                        .getSingleResult();
                this.nameTxtField.setText(patient.getName());
                this.nameTxtField.setDisable(true);

            } catch (NoResultException nre) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Error Retrieving");
                a.setContentText("No Patient with this identity number: "
                        + this.searchIdtxtField.getText() + ".");
                a.showAndWait();
            } finally {
                em.close();
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Empty Field!");
            a.showAndWait();
        }
    }

    public boolean checkFields() {
        return this.searchIdtxtField.getText().trim().equalsIgnoreCase("")
                || this.dateOfBirthField.getValue() == null
                || this.dentistCompobox.getValue() == null
                || this.nameTxtField.getText().trim().equalsIgnoreCase("");
    }

    public void emptyTextFields() {
        this.nameTxtField.setText("");
        this.dateOfBirthField.setValue(null);
        this.dentistCompobox.setValue("");
        this.nameTxtField.setText("");
        this.searchIdtxtField.setText("");
    }

}
