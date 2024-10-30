package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {
    private List<Event> events = new ArrayList<>(10);
    public List<Event> findAll(){
        return events;
    }
    public List<Event> searchEvents(String text){
        return events.stream().filter(event -> event.getName().contains(text)
                || event.getDescription().contains(text)).toList();
    }
}
