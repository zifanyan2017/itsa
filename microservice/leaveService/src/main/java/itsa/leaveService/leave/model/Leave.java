package itsa.leaveService.leave.model;

import javax.persistence.*;
import java.sql.Date;


@Entity //This tells Hibernate to make a table out of this class
@Table(name = "itsa_leave")
public class Leave {
    @Id
    @GeneratedValue
    @Column(name = "`id`")
    private Integer id;
    @Column(name = "`emp_id`")
    private String emp_id;
    @Column(name = "`start_date`")
    private Date start_date;
    @Column(name = "`end_date`")
    private Date end_date;
    @Column(name = "`status`")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
