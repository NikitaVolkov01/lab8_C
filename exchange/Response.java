package exchange;

import com.google.gson.internal.$Gson$Types;
import ticket.Coordinates;
import ticket.Event;
import ticket.Ticket;
import ticket.TicketType;

import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Response implements Serializable {
    private boolean correct;
    private List<String> response;
    public Response(boolean correct, List<String> response) {
        this.correct = correct;
        this.response = response;
    }
    public Response(boolean correct) {
        this.correct = correct;
        this.response = new ArrayList<>();
        this.response.add("");
    }
    public Response(boolean correct, String response) {
        this.correct = correct;
        this.response = new ArrayList<>();
        this.response.add(response);
    }
    public List<String> getResponse() {
        return response;
    }
    public String getString() {
        String res = String.join("\n", response);
        char[] chars = { '\\', '{' };
        String str = "\\}, \\{";
        String[] pts = res.split(str);
        str = "";
        for (String p: pts){
            str += (p + "\n");
        }
        return str;
    }
    public ArrayList<Ticket> getTickets(){

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        response.remove(0);
        response.forEach(el -> {
            try {
                tickets.add(TicketDecoder.decodeTicket(el));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });
        return tickets;
    }
    public boolean isCorrect() {
        return correct;
    }
}