package Models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Dentist.FindAll",
            query = "Select d From Dentist d"),
    @NamedQuery(name = "Dentist.FindByName",
            query = "Select d From Dentist d Where d.name= :name")
})
public class Dentist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer Age;
    private String address;
    private Integer mobileNo;

    public Dentist() {
    }

    public Dentist(String name, Integer Age, String address, Integer mobileNo) {
        this.name = name;
        this.Age = Age;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
        this.mobileNo = mobileNo;
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

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Dentist{" + "id=" + id + ", name=" + name +
                ", Age=" + Age + ", address=" + address +
                ", mobileNo=" + mobileNo + '}';
    }

}
