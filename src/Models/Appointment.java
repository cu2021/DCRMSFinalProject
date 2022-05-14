package Models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Appointment.FindAll",
            query = "Select a From Appointment a"),
    @NamedQuery(name = "Appointment.FindByPatientId",
            query = "Select a From Appointment a Where a.patient.id= :patientid")
})
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "dentistid")
    private Dentist dentist;
    private LocalDate date;

    public Appointment() {
    }

    public Appointment(Patient patient, Dentist dentist, LocalDate date) {
        this.patient = patient;
        this.dentist = dentist;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", patient=" + patient +
                ", dentist=" + dentist + ", date=" + date + '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Integer getPatientid() {
        return this.patient.getId();
    }

    public Integer getDentistid() {
        return this.dentist.getId();
    }

    public String getDentistName() {
        return this.dentist.getName();
    }

}
