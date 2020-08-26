package ticket;

import io.Input;
import io.Output;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.function.Predicate;

public class TicketCreator {
    Input in;
    Output out;
    public TicketCreator(Input in, Output out) {
        this.in = in;
        this.out = out;
    }
    private String yesNoInput() {
        String string = in.readLine();
        while (!string.equalsIgnoreCase("y") && !string.equalsIgnoreCase("n")) {
            out.print("Неверный формат, пожалуйста, повторите ввод [y/n]: ");
            string = in.readLine();
        }
        return string;
    }
    public boolean inputValue(String message, boolean required, Predicate<String> lambda) {
        while (true) {
            out.print(message);
            String input = in.readLine();
            if (input == null) {
                out.print(System.lineSeparator() + "Неверный формат. ");
                in = new Input();
            } else if (input.equals("cancel")) {
                out.println("Отмена создания нового элемента");
                return false;
            } else if (input.equals("")) {
                if (required) out.print("Обязательный параметр. ");
                else return true;
            } else if (lambda.test(input)) {
                return true;
            }
        }
    }
    public Ticket create() {
        out.println(
                "Введите необходимые данные или нажмите Enter для пропуска параметра, если это возможно (введите cancel для отмены)");
        Ticket ticket = new Ticket();
        if (!inputValue(
                "Введите имя: ",
                true,
                input -> {
                    try {
                        ticket.setName(input);
                        return true;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;
        Coordinates coordinates = new Coordinates();
        if (!inputValue(
                "Введите x: ",
                true,
                input -> {
                    try {
                        coordinates.setX(Float.parseFloat(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;
        if (!inputValue(
                "Введите y: ",
                true,
                input -> {
                    try {
                        coordinates.setY(Long.parseLong(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;
        try {
            ticket.setCoordinates(coordinates);
        } catch (ValidationException e) {
            out.print("Неверное значение. " + e.getMessage() + " ");
        }
        if (!inputValue(
                "Введите цену: ",
                true,
                input -> {
                    try {
                        ticket.setPrice(Long.parseLong(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;
        if (!inputValue(
                "Введите Тип Билета " + Arrays.toString(TicketType.values()) + ": ",
                false,
                input -> {
                    try {
                        ticket.setType(TicketType.valueOf(input));
                        return true;
                    } catch (IllegalArgumentException e) {
                        out.print("Неверное значение. ");
                        return false;
                    }
                })) return null;
        out.print("Добавить Event?[y/n]: ");
        String includeEvent = yesNoInput();
        if (includeEvent.equalsIgnoreCase("y")) {
            Event event = new Event();
            if (!inputValue(
                    "Введите название Event: ",
                    true,
                    input -> {
                        try {
                            event.setName(input);
                            return true;
                        } catch (ValidationException e) {
                            out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                            return false;
                        }
                    })) return null;
            if (!inputValue(
                    "Введите количество билетов: ",
                    true,
                    input -> {
                        try {
                            event.setNumber(Float.parseFloat(input));
                            return true;
                        } catch (NumberFormatException e) {
                            out.print("Неверный формат. ");
                            return false;
                        } catch (ValidationException e) {
                            out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                            return false;
                        }
                    })) return null;
            //int id = Tic
            ticket.setEvent(event);
        }
        return ticket;
    }
}
