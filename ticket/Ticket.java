package ticket;

import graphic.LocalePrinter;

import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Ticket implements Comparable<Ticket>, Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long cost;
    private TicketType type;
    private Event bestEvent;
    private String author;
    public Ticket(
            String name,
            Coordinates coordinates,
            long cost,
            TicketType genre,
            Event bestEvent)
            throws ValidationException {
        setName(name);
        setCoordinates(coordinates);
        setPrice(cost);
        setType(genre);
        setEvent(bestEvent);
    }
    public Ticket() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Значение id не может быть null.");
        }
        if (id <= 0) {
            throw new ValidationException("Значение id должно быть больше 0.");
        }
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("Имя не может быть null.");
        }
        if (name.equals("")) {
            throw new ValidationException("01 Имя не может быть пустой строкой.");
        }
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return this.coordinates;
    }
    public Float getX(){return this.coordinates.getX();}
    public Long getY(){return this.coordinates.getY();}
    public void setCoordinates(Coordinates coordinates) throws ValidationException {
        if (coordinates == null) {
            throw new ValidationException("Значение coordinates не может быть null.");
        }
        this.coordinates = coordinates;
    }
    public LocalDate getDate() {
        return this.creationDate;
    }
    public void setCreationDate(LocalDate creationDate) throws ValidationException {
        if (creationDate == null) {
            throw new ValidationException("Значение createDate не може быть null.");
        }

        this.creationDate = creationDate;
    }
    public long getCost() {
        return this.cost;
    }
    public void setPrice(long cost) throws ValidationException {
        if (cost <= 0) {
            throw new ValidationException("Значение цены должно быть больше 0.");
        }

        this.cost = cost;
    }
    public TicketType getType() {
        return this.type;
    }
    public void setType(TicketType type) {
        this.type = type;
    }
    public Event getBestEvent() {
        return this.bestEvent;
    }
    public String getEventname(){
        try {
            return this.bestEvent.getName();
        }catch (NullPointerException e){
            return "";
        }
    }
    public String getEventnum(){
        try {
            return String.valueOf(this.bestEvent.getNumber());
        }catch (NullPointerException e){return "";}
    }
    public String getEventid(){
        try {
            return String.valueOf(this.bestEvent.getId());
        }catch (NullPointerException e){
            return "";
        }
    }
    public void setEvent(Event event) {
        this.bestEvent = event;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String name){
        author = name;
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, cost, type, bestEvent);
    }
    @Override
    public int compareTo(Ticket other) {
        return (this.name.length() - other.getName().length() + this.id - other.id);
    }
}
