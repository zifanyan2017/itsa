package itsa.sessionService.model;
import javax.persistence.*;

@Entity
@Table(name = "Session")
public class Session {

    @Id
    @GeneratedValue
    @Column(name = "sessionID")
    private String sessionID;
    @Column(name = "userID")
    private int userID;
    @Column(name ="state")
    private String state;

    public Session(String sessionID, int userID, String state) {
        this.sessionID = sessionID;
        this.userID = userID;
        this.state = state;
    }

    public Session(){}

    public String getSessionID() {
        return sessionID;
    }

    public int getUserID() {
        return userID;
    }

    public String getState() {
        return state;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Override

//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        return this.getId()== ((Session) o).getId() && getFullName()== ((Session) o).getFullName() && getLeave()== ((Session) o).getLeave();
//    }
}

