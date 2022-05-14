/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package Controls;

import LogFile.LogFileRecording;
import Models.Receptionists;
import Views.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author LENOVO
 */
public class LoginFormController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Button CancelButton;
    private EntityManagerFactory emf;
    @FXML
    private Label status;
    private LogFileRecording lgFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf = Persistence.
                createEntityManagerFactory("DentalClinicReceptionistManagentSystemPU");
        lgFile = LogFileRecording.getLogFileRecording();
    }

    @FXML
    private void signInButtonHandle(ActionEvent event) throws IOException {
        if (!(this.usernameTextField.getText().equals("")
                || this.usernameTextField.getText() == null
                || this.passwordField.getText().equals("")
                || this.passwordField.getText() == null)) {
            EntityManager em = emf.createEntityManager();
            List<Receptionists> receptionist
                    = em.createNamedQuery("Receptionists.FindAll")
                            .getResultList();
            Iterator<Receptionists> ri = receptionist.iterator();
            Receptionists r = null;
            while (ri.hasNext()) {
                r = ri.next();
                if (r.getUsername().equals(this.usernameTextField.getText())) {
                    if (r.getPassword().equals(passwordField.getText())) {
                        new Main().show();
                        Stage stage = (Stage) CancelButton.getScene().getWindow();
                        stage.close();
                        em.close();
                        lgFile.writeActivity("Receptionist logged in "+r);

                        break;
                    } else {
                        this.status.setVisible(true);
                        this.status
                                .setText("The username or password is incorrect,"
                                        + " if you have any issue connect to the "
                                        + "IT Support.");
                    }
                } else {
                    this.status.setVisible(true);
                    this.status
                            .setText("The username or password is incorrect,"
                                    + " if you have any issue connect to the "
                                    + "IT Support.");
                }

            }

        } else {
            this.status.setVisible(true);
            this.status.setText("Please, be sure to fill the field...");
        }

    }

    @FXML
    private void CancelButton(ActionEvent event) {
        System.exit(0);
    }

}
