package itsa.employeeService.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "Fullname")
    private String fullName;
    @Column(name ="user_id")
    private int userId;
    @Column(name = "entitled_leave")
    private Double leave;
    @Column(name ="Address")
    private String address;
    @Column(name ="Position")
    private String position;
    @Column(name ="date_of_hiring")
    private String hireDate;
    @Column(name ="Salary")
    private Double salary;

    public Employee(int id, int userId, String fullName, Double leave, String address, String position, String hireDate, Double salary) {
        this.id = id;
        this.fullName = fullName;
        this.leave = leave;
        this.address = address;
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
        this.userId = userId;
    }

    public Employee(){}



    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public String getPosition() {
        return position;
    }

    public String getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getLeave() {
        return leave;
    }

    public void setLeave(Double leave) {
        this.leave = leave;
    }
    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.getId()== ((Employee) o).getId() && getFullName()== ((Employee) o).getFullName() && getLeave()== ((Employee) o).getLeave();
    }
}

