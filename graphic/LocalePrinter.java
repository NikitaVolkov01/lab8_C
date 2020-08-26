package graphic;

import ticket.Ticket;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalePrinter {

    private static ResourceBundle bundle = ResourceBundle.getBundle("resources/app", new Locale("en"));

    public static void setLocale(Locale loc) {
        bundle = ResourceBundle.getBundle("resources/app", loc);
    }

    public static String printTicket(Ticket ticket) throws UnsupportedEncodingException {

        String answer =  "TICKET: \nID" + ": " + ticket.getId() + ", \n" +
                new String(bundle.getString("nameColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getName() + ", \n" +
                "X" + ": " + ticket.getX() + ", " +
                "Y" + ": " + ticket.getY() + ", \n" +
                new String(bundle.getString("dateColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getDate() + ", \n" +
                new String(bundle.getString("costColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getCost() + ", \n";

        if (ticket.getType() != null) answer += new String(bundle.getString("typeColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getType() + ", \n";
        if (ticket.getBestEvent() != null) {
            answer += (new String(bundle.getString("eventNameColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getEventname() + ", "
                    + new String(bundle.getString("eventNumberColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getEventnum() + ", "
                    + new String(bundle.getString("eventNumberColumn").getBytes("ISO-8859-1"), "UTF-8") + ": " + ticket.getEventnum() + ", ");
        }

        return answer;

    }

    public static String printInfo(String infoData) throws UnsupportedEncodingException {
        return infoData
                .replaceAll("Тип коллекции", new String(bundle.getString("infoCollection").getBytes("ISO-8859-1"), "UTF-8"))
                .replaceAll("Количесво элементов", new String(bundle.getString("infoNumber").getBytes("ISO-8859-1"), "UTF-8"))
                .replaceAll("Дата инициализации", new String(bundle.getString("infoDate").getBytes("ISO-8859-1"), "UTF-8"));
    }
}
