package itsa.leaveService.leave.resource;




import itsa.leaveService.leave.model.Leave;
import itsa.leaveService.leave.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class LeaveResource {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(       Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)

    @Autowired
    LeaveRepository leaveRepository;
    @GetMapping(value = "/all")
    public List<Leave> getAll(){
        return leaveRepository.findAllLeave();
    }

    @GetMapping(value = "/find/{emp_id}")
    public List<Leave> find(@PathVariable("emp_id") String emp_id){
        return leaveRepository.findAllByUserId(emp_id);
}

    @PutMapping(value = "/update")
    @Transactional
    public void update(@RequestParam("id") String id,
                       @RequestParam("start_date") java.sql.Date start_date,
                       @RequestParam("end_date")java.sql.Date end_date){
        leaveRepository.updateLeave(start_date,end_date,id);

    }

    @PutMapping(value = "/status")
    @Transactional
    public void update (@RequestParam("id") String id,
                       @RequestParam("status") String status){
        leaveRepository.approveLeave(status,id);
    }




    @PostMapping(value = "/insert")
    @Transactional
    public void insert(@RequestParam("emp_id") String emp_id,
                       @RequestParam("start_date")java.sql.Date start_date,
                       @RequestParam("end_date")java.sql.Date end_date){
        leaveRepository.insertLeave(start_date,end_date,emp_id);
}
}