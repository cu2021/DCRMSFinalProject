/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controls;

import LogFile.LogFileRecording;
import Models.Appointment;
import Models.Dentist;
import Models.Patient;
import Views.Main;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
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
public class UpdateAppointmentController implements Initializable {

    @FXML
    private TextField nameTxtField;
    @FXML
    private TextField searchIdtxtField;
    @FXML
    private Button searchById;
    @FXML
    private ImageView backButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Appointment, Integer> tcPatientId;
    @FXML
    private TableColumn<Appointment, Integer> tcDentist;
    @FXML
    private TableColumn<Appointment, LocalDate> tcDate;
    @FXML
    private ComboBox<String> dentistCompobox;
    @FXML
    private DatePicker appointmentDateField;
    @FXML
    private TableView<Appointment> tableView;
    private EntityManagerFactory emf;
    private Appointment appointment;
    private LogFileRecording lgFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lgFile = LogFileRecording.getLogFileRecording();
        this.nameTxtField.setDisable(true);
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
        tcPatientId.setCellValueFactory(new PropertyValueFactory("patientid"));
        tcDentist.setCellValueFactory(new PropertyValueFactory("dentistid"));
        tcDate.setCellValueFactory(new PropertyValueFactory("date"));
        show();
        tableView.getSelectionModel()
                .selectedItemProperty().addListener(e -> {
                    this.showSelectedAppointment();
                });

    }

    private void show() {
        EntityManager em = emf.createEntityManager();
        List<Appointment> appointments = em.createNamedQuery("Appointment.FindAll")
                .getResultList();
        tableView.getItems().setAll(appointments);
        em.close();
    }

    private void showSelectedAppointment() {
        this.appointment = tableView.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            this.nameTxtField.setText(appointment.getPatient().getName());
            this.searchIdtxtField.setText(appointment.getPatient().getIdentityNo() + "");
            this.dentistCompobox.setValue(appointment.getDentistName());
            this.appointmentDateField.setValue(appointment.getDate());
        }

    }

    @FXML
    private void searchByIdHandle(ActionEvent event) {
        if (!(this.searchIdtxtField.getText().equalsIgnoreCase("")
                || this.searchIdtxtField.getText() == null)) {
            EntityManager em = emf.createEntityManager();

            try {
                Patient p = (Patient) em.
                        createNamedQuery("Patient.FindById")
                        .setParameter("identityNo",
                                Integer.parseInt(this.searchIdtxtField.getText()))
                        .getSingleResult();

                List<Appointment> appointments = em.
                        createNamedQuery("Appointment.FindAll")
                        .getResultList();

                Iterator<Appointment> ai = appointments.iterator();
                while (ai.hasNext()) {
                    appointment = ai.next();
                    if (appointment.getId() == p.getId()) {
                        this.tableView.getSelectionModel().select(appointment);
                        break;

                    } else {
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setTitle("Error Retrieving");
                        a.setContentText("No Appointment for this Patient: "
                                + this.searchIdtxtField.getText() + ".");
                        a.showAndWait();
                    }
                }

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

    @FXML
    private void backButtonHandle(MouseEvent event) throws IOException {
        new Main().show();
        ((Stage) (deleteButton.getScene().getWindow())).close();
    }

    @FXML
    private void updateButtonHandle(ActionEvent event) throws IOException {
        if (!checkFields()) {

            EntityManager em = emf.createEntityManager();

            LocalDate AppDate = this.appointmentDateField.getValue();
            String dentistName = this.dentistCompobox.getValue();
            Integer patientIdentityNo = Integer.parseInt(this.searchIdtxtField.getText());

            Dentist dentist = (Dentist) em.createNamedQuery("Dentist.FindByName")
                    .setParameter("name", dentistName).getSingleResult();
            Appointment appointment = em.find(Appointment.class, this.appointment.getId());
            em.getTransaction().begin();

            appointment.setDate(AppDate);
            appointment.setDentist(dentist);

            em.getTransaction().commit();
            em.close();
            show();
            emptyTextFields();
            lgFile.writeActivity("Update Appointment: " + appointment);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Success Message.");
            a.setContentText("Appointment Upadated Successfully.");

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Empty Field!");
            a.showAndWait();
        }

    }

    public boolean checkFields() {
        return this.searchIdtxtField.getText().trim().equalsIgnoreCase("")
                || this.appointmentDateField.getValue() == null
                || this.dentistCompobox.getValue() == null
                || this.nameTxtField.getText().trim().equalsIgnoreCase("");
    }

    public void emptyTextFields() {
        this.nameTxtField.setText("");
        this.appointmentDateField.setValue(null);
        this.dentistCompobox.setValue("");
        this.nameTxtField.setText("");
        this.searchIdtxtField.setText("");
    }

    @FXML
    private void deleteButtonHandle(ActionEvent event) throws IOException {
        if (!checkFields()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deletion Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete the appointment of: "
                    + this.appointment.getPatient().getName() + "?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                EntityManager em = emf.createEntityManager();
                Appointment appointment = em.find(Appointment.class, this.appointment.getId());
                em.getTransaction().begin();
                em.remove(appointment);
                em.getTransaction().commit();
                em.close();
                show();
                emptyTextFields();
                lgFile.writeActivity("Delete Appointment: " + appointment);
            }

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("No selected Appointment!");
            a.showAndWait();
        }
    }

}
