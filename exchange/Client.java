package exchange;

import adapters.LocalDateDeserializer;
import adapters.LocalDateSerializer;
import adapters.LoggerSetup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.Input;
import io.Output;
import ticket.Ticket;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Client {
    private boolean logged;
    private boolean regged;
    private ArrayList<Ticket> tickets;
    private Integer addId;
    private Integer addEventId;
    private String metaData;

    private final InetSocketAddress inetSocketAddress;
    private Gson gson;
    private Input in;
    private Output out;
    private Logger logger;
    private ByteBuffer buffer;
    private DatagramSocket socket;
    public Client(String host, int port) throws UnknownHostException {
        gson =
                new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                        .create();
        inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        buffer = ByteBuffer.allocate(8192);
        in = new Input();
        out = new Output();
        logger = Logger.getLogger(Client.class.getName());
        LoggerSetup.setupLogger(logger, "client" + File.separator + LocalDate.now() + "_log.log");
    }
    public Input getIn() {
        return in;
    }
    public Output getOut() {
        return out;
    }

    public Integer getAddId() {
        return addId;
    }

    public Integer getAddEventId() {
        return addEventId;
    }

    public String getMetaData() {
        return metaData;
    }

    public boolean isConnected() {
        return (!socket.isClosed() && socket.isConnected());
    }
    public boolean connect() throws IOException {
        socket = new DatagramSocket();
        socket.connect(inetSocketAddress);
        socket.setSoTimeout(1000);
        //logger.finest("Соединение установлено.");
        return true;
    }
    public boolean isLogged(){
        return logged;
    }
    public boolean isRegged(){
        return regged;
    }
    public ArrayList<Ticket> getTickets(){
        return tickets;
    }
    public void close() {
        socket.close();
        //logger.finest("Сокет успешно закрыт");
    }
    public void checkConnection(String input) {
        while (!isConnected()) {
            try {
                connect();
                get(input);
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.severe("Ошибка приостановки выполнения потока");
                }
            }
        }
    }
    public void get(String input) {
        try {
            Long time1 = System.currentTimeMillis();
            buffer.clear();
            Request request = new Request(input);
            //checking for add to create a ticket

            if (request.getCommandName().equals("exit")) {
                out.println("Завершение работы");
                //logger.fine("Закрытие соединения");
                socket.close();
                System.exit(0);
            }
            String data = gson.toJson(request);
            buffer.put(data.getBytes());
            buffer.flip();
            DatagramPacket sendPacket =
                    new DatagramPacket(buffer.array(), buffer.remaining(), inetSocketAddress);
            socket.send(sendPacket);
            buffer.clear();
            DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.remaining());
            socket.receive(receivePacket);
            data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            Response response = gson.fromJson(data, Response.class);
            out.println();
            out.println(response.getString());
            out.println();
            if (response.getString().trim().equals("Успешный логин")) logged = true;
            else if (response.getString().trim().equals("Успешная регистрация")) regged = true;
            else if (response.getString().trim().startsWith("Элемент обновлен ")) metaData = response.getString();
            else if (response.getString().trim().startsWith("Эл")) tickets = response.getTickets();
            else if (response.getString().trim().startsWith("Добавление ")) {
                try {
                    addId = Integer.parseInt(response.getString().split("\n")[1]);
                    System.out.println("ID assigned");

                }catch (NumberFormatException e) {
                    addId = 1;

                }
                try {
                    addEventId = Integer.parseInt(response.getString().split("\n")[2]);
                }catch (NumberFormatException e){
                    addEventId = 0;
                }

            }
            else if (response.getString().trim().startsWith("Тип коллекции: ")) metaData = response.getString();
            else if (response.getString().trim().startsWith("История ")) metaData = response.getString();
            //logger.info("Данные клиента: " + request.getCommandName() + " " + request.getCommandParameter());
            //logger.info(response.getString());
            Long time2 = System.currentTimeMillis();
            //System.out.println("TIME SPENT --- " + (time2 - time1));
        } catch (IOException e) {
            e.printStackTrace();
            out.print("\rРазрыв соединения, ожидание подключения");
            close();
        }
    }
}
