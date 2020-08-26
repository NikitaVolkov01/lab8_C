package ticket;

import javax.xml.bind.ValidationException;
import java.io.Serializable;

public class Event implements Serializable {
    private String name;
    private Float number;
    private Integer id;

    public Event() {}
    public void setName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("Имя не может быть null.");
        }
        if (name.equals("")) {
            throw new ValidationException("Имя не может быть пустой строкой.");
        }
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setNumber(Float num) throws ValidationException {
        if (!(num > 0)){
            throw new ValidationException("Количество билетов не может быть меньше нуля.");
        }
        this.number = num;
    }
    public float getNumber(){
        return this.number;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}