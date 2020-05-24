package ejbs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class LoginStats {
    @EJB
    Log log;
    HashMap<String, Integer> map = new HashMap<>();
    HashMap<String, ArrayList<String>> dates_map = new HashMap<>();
    
    public void newLogin(String email) {
        if (map.get(email) == null) map.put(email, 1);
        else map.put(email, map.get(email)+1);
        ArrayList<String> all_dates = dates_map.get(email);
        if (all_dates == null) all_dates = new ArrayList<>();
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy - HH:mm:ss"; 
        SimpleDateFormat format_date = new SimpleDateFormat(strDateFormat); 
        all_dates.add(format_date.format(date));
        dates_map.put(email, all_dates);
        System.out.println("LoginStats::newLogin - User: " + email + " Date: "+format_date.format(date));
        log.newCallEJB("LoginStats::newLogin - User: " + email + " Date: "+format_date.format(date));
    }

    public ArrayList<String> getLoginsDate(String email){
        System.out.println("LoginStats::getLoginsDate - User: " + email);
        log.newCallEJB("LoginStats::getLoginsDate - User: " + email);
        return dates_map.get(email);
    }
            
    public HashMap<String, Integer> getAllLogins() {
        System.out.println("LoginStats::getAllLogins - Logins: " + map.size());
        log.newCallEJB("LoginStats::getAllLogins - Logins: " + map.size());
        return map;
    }

    @PostConstruct
    public void init() {
        System.out.println("LoginStats::init - @PostConstruct del Singleton");
        log.newCallEJB("LoginStats::init - @PostConstruct del Singleton");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("LoginStats::destroy - @PreDestroy del Singleton");
        log.newCallEJB("LoginStats::destroy - @PreDestroy del Singleton");
    }
}
