package itsa.sessionService.resource;

import itsa.sessionService.model.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class SessionResource {
    @Autowired
    itsa.sessionService.repository.SessionRepository sessionRepository;

    @GetMapping(value = "/exist/{sessionID}")
    public String exist(@PathVariable("sessionID") String sessionID) {
        JSONObject json = new JSONObject();
        json.put("ID", sessionID);
        String sID = sessionRepository.getSessionIDBySessionID(sessionID);
        boolean sessionIDExist = false;
        if(sID !=null){
            if ( !sID.equals("") ) {
                sessionIDExist = true;
                json.put("Exist", sessionIDExist);
            }else{
                json.put("Exist", sessionIDExist);
            }
            return json.toString();

        }


        json.put("Exist", sessionIDExist);
        return json.toString();
    }

    @GetMapping(value = "/getstate/{sessionID}")
    public String getState(@PathVariable("sessionID") String sessionID) {
        JSONObject json = new JSONObject();
        json.put("ID", sessionID);
        String state = sessionRepository.getStateBySessionID(sessionID);
        if(state == null){
            json.put("status", "error");
            return json.toString();
        }
       else if (state.equals("")){
           json.put("status", "error");
           return json.toString();

       }

        return json.toString();
    }

    @PostMapping(value="/add")
    @Transactional
    public void add( @Param("userID") int userID ) {
        String response = sessionRepository.findSessionIDbyUserID(userID);
        String sessionID = getAlphaNumericString(30);
        if ( response == null ) {
            sessionRepository.newSession( sessionID , userID );
        }else{
            sessionRepository.updateSession( sessionID, userID );
        }
    }

    @PutMapping(value="/update")
    @Transactional
    public void update( @Param("sessionID") String sessionID ,@Param("userID") int userID, @Param("state") String state ) {
        sessionRepository.updateState(state, sessionID, userID);
    }

//    @GetMapping(value = "/all")
//    public List<Session> getAll() {
//        return sessionRepository.findAllEmployee();
//    }
//
//    @GetMapping(value = "/find/{id}")
//    public List<Session> find(@PathVariable("id") int id) {
//        return sessionRepository.findEmp(id);
//    }
//
//    @GetMapping(value = "/findempid/{id}")
//    public JSONArray findEmpId(@PathVariable("id") int id){
//        JSONObject json = new JSONObject();
//        json.put("ID", id);
//        json.put("user_id", sessionRepository.findEmpId(id));
//        JSONArray array = new JSONArray();
//        array.add(json);
//
//        return array;
//    }
//
//    @PostMapping(value="/add")
//    @Transactional
//    public void add(@Param("fullname") String fullname,@Param("userId") int userId, @Param("address") String address
//                    , @Param("position") String position, @Param("hireDate") String hireDate,
//                    @Param("salary") Double salary, @Param("entitled_leave") Double leave)
//    { sessionRepository.insertEmployee(fullname,userId,address,position,hireDate,salary,leave);}
//
//    @PutMapping(value="/update")
//    @Transactional
//    public void update(@Param("id") int id,@Param("fullname") String fullname, @Param("address") String address,
//                       @Param("position") String position, @Param("hireDate") String hireDate,
//                       @Param("salary") Double salary, @Param("entitled_leave") Double leave)
//    { sessionRepository.updateEmployee(id,fullname,address,position,hireDate,salary,leave);}
//
//    @DeleteMapping(value="/delete")
//    @Transactional
//    public void delete(@RequestParam("id") int id)
//    { sessionRepository.deleteEmployee(id);}

    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
