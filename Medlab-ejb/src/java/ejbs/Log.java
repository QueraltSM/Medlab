package ejbs;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@LocalBean
public class Log {

    ArrayList<String> log = new ArrayList<>();
    int log_size = 0;
    
    //@Resource
    //TimerService timer; // Programatic timer
    //int selected_time = 0;
    
    //@Schedule(second="*/5", minute="*", hour="*") // Automatic timer
    /*public void scheduleTimer(Timer timer) {
        if (log.size() == log_size) {
            System.out.println("Log::scheduleTimer - @Schedule: Inactive system for 5 seconds");
            newCallEJB("Log::scheduleTimer - @Schedule: Inactive system for 5 seconds");
        }
    }*/
    
    /*public void setSelectedTime(int time) {
        this.selected_time = (time*1000);
        timer.createSingleActionTimer(selected_time, new TimerConfig());
        System.out.println("Log::setSelectedTime() - Time: " + time + " seconds");
        newCallEJB("Log::setSelectedTime() - Time: " + time + " seconds");
    }
    
    public int getSelectedTime(){
        System.out.println("Log::getSelectedTime() - Time: " + (selected_time/1000) + " seconds");
        newCallEJB("Log::getSelectedTime() - Time: " + (selected_time/1000) + " seconds");
        return (selected_time/1000);
    }
    
    @Timeout
    public void timerService(Timer time) { 
        System.out.println("Log::timerService - @Timeout - Time: " + (selected_time/1000) + " seconds");
        newCallEJB("Log::timerService - @Timeout - Time: " + (selected_time/1000) + " seconds");
        timer.createSingleActionTimer(selected_time, new TimerConfig());
    }*/
    
    public void newCallEJB(String EJB) {
        log.add(EJB + "\n");
        log_size = log.size();
    }
    
    @Lock(LockType.READ)
    public ArrayList<String> getEJBsTrace(){
        System.out.println("Log::getEJBsTrace()");
        newCallEJB("Log::getEJBsTrace");
        return log;
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Log::init - @PostConstruct del Singleton");  
        newCallEJB("Log::init - @PostConstruct del Singleton");
    }
   
    @PreDestroy
    public void destroy() {
        System.out.println("Log::destroy - @PreDestroy del Singleton");    
        newCallEJB("Log::destroy - @PreDestroy del Singleton");
    }
}