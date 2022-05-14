package Models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Patient.FindAll",
            query = "Select p From Patient p"),
    @NamedQuery(name = "Patient.FindById",
            query = "Select p From Patient p Where p.identityNo= :identityNo")
})
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private Integer identityNo;
    private String gender;
    private Integer mobileNo;
    private String address;
    private LocalDate dob;

    public Patient() {
    }

    public Patient(String name, Integer identityNo,
            String gender, Integer mobileNo, String address, LocalDate dob) {
        this.name = name;
        this.identityNo = identityNo;
        this.gender = gender;
        this.mobileNo = mobileNo;
        this.address = address;
        this.dob = dob;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(Integer IdentityNo) {
        this.identityNo = IdentityNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", name=" + name + ","
                + " identityNo=" + identityNo + ","
                + " gender=" + gender + ","
                + " mobileNo=" + mobileNo + ","
                + " address=" + address + ", dob=" + dob + '}';
    }

}
