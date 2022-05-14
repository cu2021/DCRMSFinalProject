package Controls;

import LogFile.LogFileRecording;
import Models.Patient;
import Views.Main;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class UpdatePatientServiceController implements Initializable {

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
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Patient> tabelView;
    @FXML
    private TableColumn<Patient, String> tcName;
    @FXML
    private TableColumn<Patient, Integer> tcID;
    @FXML
    private TextField searchIdtxtField;
    @FXML
    private ImageView backButton;
    @FXML
    private TableColumn<Patient, String> tcAddress;
    @FXML
    private TableColumn<Patient, LocalDate> tcDob;
    @FXML
    private TableColumn<Patient, Integer> tcPhoneNumber;
    @FXML
    private Button searchById;
    @FXML
    private TableColumn<Patient, String> tcGender;
    private EntityManagerFactory emf;
    private Patient patient;
    @FXML
    private TextField phoneNumber;
    private LogFileRecording lgFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderCompobox.getItems().addAll("Female", "Male");
        emf
                = Persistence
                        .createEntityManagerFactory("DentalClinicReceptionistManagentSystemPU");
        tcID.setCellValueFactory(new PropertyValueFactory("identityNo"));
        tcName.setCellValueFactory(new PropertyValueFactory("name"));
        tcGender.setCellValueFactory(new PropertyValueFactory("gender"));
        tcPhoneNumber.setCellValueFactory(new PropertyValueFactory("mobileNo"));
        tcDob.setCellValueFactory(new PropertyValueFactory("dob"));
        tcAddress.setCellValueFactory(new PropertyValueFactory("address"));
        show();
        tabelView.getSelectionModel()
                .selectedItemProperty().addListener(e -> {
                    this.showSelectedPatient();
                });
        lgFile = LogFileRecording.getLogFileRecording();
    }

    private void show() {
        EntityManager em = emf.createEntityManager();
        List<Patient> patients = em.createNamedQuery("Patient.FindAll")
                .getResultList();
        tabelView.getItems().setAll(patients);
        em.close();
    }

    private void showSelectedPatient() {

        patient = tabelView.getSelectionModel().getSelectedItem();
        if (patient != null) {
            this.nameTxtField.setText(patient.getName());
            this.addressField.setText(patient.getAddress());
            this.dateOfBirthField.setValue(patient.getDob());
            this.genderCompobox.setValue(patient.getGender());
            this.idTxtField.setText(patient.getIdentityNo() + "");
            this.phoneNumber.setText(patient.getMobileNo() + "");
        }

    }

    @FXML
    private void updateButtonHandle(ActionEvent event) throws IOException {
        if (!checkFields()) {

            EntityManager em = emf.createEntityManager();

            LocalDate dob = this.dateOfBirthField.getValue();
            String address = this.addressField.getText();
            Integer mobileNo = Integer.parseInt(this.phoneNumber.getText());
            String gender = this.genderCompobox.getValue();
            Integer identityNo = Integer.parseInt(this.idTxtField.getText());
            String name = this.nameTxtField.getText();
            Patient patient = em.find(Patient.class, this.patient.getId());
            em.getTransaction().begin();

            patient.setAddress(address);
            patient.setDob(dob);
            patient.setGender(gender);
            patient.setIdentityNo(identityNo);
            patient.setMobileNo(mobileNo);
            patient.setName(name);

            em.getTransaction().commit();
            em.close();
            show();
            emptyTextFields();
            lgFile.writeActivity("update Patient " + patient);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Success Message.");
            a.setContentText("Patient Upadated Successfully.");

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Empty Field!");
            a.showAndWait();
        }

    }

    @FXML
    private void deleteButtonHandle(ActionEvent event) throws IOException {
        if (!checkFields()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete the patient:" + this.patient.getName() + "?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                EntityManager em = emf.createEntityManager();
                Patient patient = em.find(Patient.class, this.patient.getId());
                em.getTransaction().begin();
                em.remove(patient);
                em.getTransaction().commit();
                em.close();
                show();
                emptyTextFields();
                lgFile.writeActivity("Delete Patient " + patient);

            }

        }
    }

    @FXML
    private void backButtonHandle(MouseEvent event) throws IOException {
        new Main().show();
        ((Stage) (deleteButton.getScene().getWindow())).close();
    }

    @FXML
    private void searchByIdHandle(ActionEvent event) {
        if (!(this.searchIdtxtField.getText().equalsIgnoreCase("")
                || this.searchIdtxtField.getText() == null)) {
            EntityManager em = emf.createEntityManager();

            try {
                Patient patient1 = (Patient) em.createNamedQuery("Patient.FindById")
                        .setParameter("identityNo", Integer.parseInt(this.searchIdtxtField.getText()))
                        .getSingleResult();
                tabelView.getSelectionModel().select(patient1);

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
        return this.addressField.getText().trim().equalsIgnoreCase("")
                || this.dateOfBirthField.getValue() == null
                || this.genderCompobox.getValue() == null
                || this.idTxtField.getText().trim().equalsIgnoreCase("")
                || this.nameTxtField.getText().trim().equalsIgnoreCase("")
                || this.phoneNumber.getText().trim().equalsIgnoreCase("");
    }

    public void emptyTextFields() {
        this.addressField.setText("");
        this.dateOfBirthField.setValue(null);
        this.genderCompobox.setValue("");
        this.idTxtField.setText("");
        this.nameTxtField.setText("");
        this.phoneNumber.setText("");
        this.searchIdtxtField.setText("");
    }
}
