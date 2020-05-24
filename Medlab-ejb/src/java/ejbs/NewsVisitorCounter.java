package ejbs;
import entities.News;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class NewsVisitorCounter {
    
    //@EJB
    //Log log;
    HashMap<News, Integer> map = new HashMap<>();

    public void newVisit(News news) {
        boolean enter = false;
            for (HashMap.Entry<News, Integer> entry : map.entrySet()) {
                if (entry.getKey().getId().equals(news.getId())) {
                    map.put(entry.getKey(), entry.getValue() + 1);
                    enter = true;
                }
            }
        if (!enter) map.put(news,1);
        System.out.println("NewsVisitorCounter::newVisit - News: " + news.getTitle());
        //log.newCallEJB("NewsVisitorCounter::newVisit - News: " + news.getTitle());
    }

    public int getVisitsCount(News news) {
        System.out.println("NewsVisitorCounter::getVisitsCount - News: " + news.getTitle());
        //log.newCallEJB("NewsVisitorCounter::getVisitsCount - News: " + news.getTitle());
        for (HashMap.Entry<News, Integer> entry : map.entrySet()) {
            if (entry.getKey().getId().equals(news.getId())) return entry.getValue(); 
        }
        return 0;
    }

    public HashMap<News, Integer> getAllVisits() {
        System.out.println("NewsVisitorCounter::getAllVisits - Map size: " + map.size());
        //log.newCallEJB("NewsVisitorCounter::getAllVisits - Map size: " + map.size());
        return map;
    }

    public HashMap<News, Integer> sortMap() {
        LinkedHashMap<News, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(HashMap.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        System.out.println("NewsVisitorCounter::sortMap - SortedMap: " + sortedMap.size());
        //log.newCallEJB("NewsVisitorCounter::sortMap -  SortedMap: " + sortedMap.size());
        return sortedMap;
    }

    @PostConstruct
    public void init() {
        System.out.println("NewsVisitorCounter::init - @PostConstruct del Singleton");
        //log.newCallEJB("NewsVisitorCounter::init - @PostConstruct del Singleton");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("NewsVisitorCounter::destroy - @PreDestroy del Singleton");
        //log.newCallEJB("NewsVisitorCounter::destroy - @PreDestroy del Singleton");
    }
}
