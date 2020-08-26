package exchange;

import ticket.Coordinates;
import ticket.Event;
import ticket.Ticket;
import ticket.TicketType;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;

public class TicketDecoder {
    public static Ticket decodeTicket(String source) throws ValidationException {

        //try {
            String parts[] = source.split(", ");
            Integer id = Integer.parseInt(parts[0].split(": ")[1]);

            String name = parts[1].split(": ")[1];

            Float x = Float.parseFloat(parts[2].split(": ")[1].split(";")[0].replace("(", ""));
            Long y = Long.parseLong(parts[2].split(": ")[1].split(";")[1].replace(")", ""));
            Coordinates coordinates = new Coordinates();
            try {
                coordinates = new Coordinates(x, y);
            } catch (ValidationException e) {
            }

            String[] dateParts = parts[3].split(": ")[1].split("-");
            LocalDate creationDate = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

            long cost = Long.parseLong(parts[4].split(": ")[1]);

            String author = source.split(", автор ")[1];

            TicketType type = null;
            try {
                type = TicketType.valueOf(source.split(", тип ")[1].split(", ")[0]);

            } catch (Exception e) {
                //e.printStackTrace();
            }

            Event bestEvent = null;
            String eName = "";
            Float eNumber = null;
            Integer eId = null;
            try {
                bestEvent = new Event();
                //String[] eventParts = parts[6].split(" ")[1];
                bestEvent.setName(source.split(", событие ")[1].split(", ")[0]);
                bestEvent.setNumber(Float.parseFloat(source.split(", количество билетов ")[1].split(", ")[0]));
                bestEvent.setId(Integer.parseInt(source.split(", event id ")[1].split(", ")[0]));

            } catch (Exception e) {
                //e.printStackTrace();
            }

        //}catch (ValidationException e){}

        Ticket ticket = new Ticket();
        return setEvent(setType(setIdNDate(setFields(ticket, name, x, y, cost, author), id, creationDate), type), bestEvent);
    }

    public static Ticket setFields(Ticket ticket, String name, Float x, Long y, long cost, String author) throws ValidationException {

        ticket.setName(name);
        Coordinates coordinates = new Coordinates(x, y);
        ticket.setCoordinates(coordinates);
        ticket.setPrice(cost);
        ticket.setAuthor(author);

        return ticket;

    }

    public static Ticket setIdNDate(Ticket ticket, Integer id, LocalDate localDate) throws ValidationException {
        ticket.setId(id);
        ticket.setCreationDate(localDate);
        return ticket;
    }

    public static Ticket setType(Ticket ticket, TicketType type){
        ticket.setType(type);
        return ticket;
    }

    public static Ticket setEvent(Ticket ticket, Event event){
        ticket.setEvent(event);
        return ticket;
    }
}
