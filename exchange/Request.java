package exchange;

import graphic.handlers.ButtonAddHandler;
import graphic.handlers.ButtonUpdateHandler;
import io.Input;
import io.Output;
import ticket.Ticket;
import ticket.TicketCreator;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private Ticket ticket;
    private String commandParameter;
    public Request(String command) {
        if (command == null) {
            commandName = null;
            commandParameter = null;
        } else {
            String[] values = command.split(" ", 2);
            commandName = values[0];
            if (values.length == 2) commandParameter = values[1] + "\n";

            if (commandName.equals("add")
                    || commandName.equals("update")
                    || commandName.equals("replace_if_lower")) {
                System.out.println("In request");
                if (ButtonAddHandler.ticket != null){
                    System.out.println("in add block");
                    ticket = ButtonAddHandler.ticket; ButtonAddHandler.ticket = null;
                }else if (ButtonUpdateHandler.ticket != null){
                    System.out.println("in update clock");
                    ticket = ButtonUpdateHandler.ticket; ButtonUpdateHandler.ticket = null;
                }else ticket = new TicketCreator(new Input(), new Output()).create();
            }
        }
    }
    public String getCommandName() {
        return commandName;
    }
    public String getCommandParameter() {
        return commandParameter;
    }
    public Ticket getTicket() {
        return ticket;
    }
}