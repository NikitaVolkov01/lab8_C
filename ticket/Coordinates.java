package ticket;

import javax.xml.bind.ValidationException;
import java.io.Serializable;

public class Coordinates implements Serializable {
    private float x;
    private Long y;
    public Coordinates(float x, Long y) throws ValidationException {
        setX(x);
        setY(y);
    }
    public Coordinates() {
    }
    public float getX() {
        return x;
    }
    public void setX(float x) throws ValidationException {
        if (!(x > 0 ) || !(x < 350)) throw new ValidationException("Значение x должно быть 0 < X < 350");

        this.x = x;
    }
    public Long getY() {
        return y;
    }
    public void setY(Long y) throws ValidationException {
        if (!(x > 0 ) || !(x < 630)) throw new ValidationException("Значение x должно 0 < Y < 630");
        this.y = y;
    }
}