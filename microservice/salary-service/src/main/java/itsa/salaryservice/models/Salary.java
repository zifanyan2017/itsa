package itsa.salaryservice.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Salary {
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private String userId;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="date_added")
//    @Temporal(TemporalType.DATE)
    private Date date;

    public Salary() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

    }
}