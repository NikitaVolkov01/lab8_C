package graphic;

import exchange.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ticket.Ticket;
import ticket.TicketType;

import java.time.LocalDate;
import java.util.ArrayList;

public class TableManager {

    TableColumn idColumn;
    TableColumn nameColumn;
    TableColumn xColumn;
    TableColumn yColumn;
    TableColumn dateColumn;
    TableColumn costColumn;
    TableColumn typeColumn;
    TableColumn eventNameColumn;
    TableColumn eventNumberColumn;
    TableColumn eventIdColumn;
    TableColumn authorColumn;

    private TableView<Ticket> table;
    public ObservableList<Ticket> tickets;

    TableManager(Client client){
        table = new TableView<Ticket>(loadLabs(client));
        table.setMaxWidth(760);
        //System.out.println("TABLE "+table);
        table.setPrefHeight(480);
        createColumns(table);
    }

    public ObservableList<Ticket> loadLabs(Client client){
        tickets = FXCollections.observableArrayList();
        client.get("show");
        try{client.getTickets().stream().forEach(el -> tickets.add(el));} catch (NullPointerException e){}
        //System.out.println("NUM " + ol.size());
        return tickets;
    }

    public TableView<Ticket> getTable(){
        return table;
    }

    private void createColumns(TableView<Ticket> table){
        //creates id column
        idColumn = new TableColumn<Ticket, Integer>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("id"));
        table.getColumns().add(idColumn);

        //creates name column
        nameColumn = new TableColumn<Ticket, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("name"));
        table.getColumns().add(nameColumn);

        //creates x coordinate column
        xColumn = new TableColumn<Ticket, Float>("x");
        xColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Float>("x"));
        table.getColumns().add(xColumn);

        //creates y coordinate column
        yColumn = new TableColumn<Ticket, Long>("y");
        yColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("y"));
        table.getColumns().add(yColumn);

        //creates creation date column
        dateColumn = new TableColumn<Ticket, LocalDate>("Creation date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDate>("date"));
        table.getColumns().add(dateColumn);

        //creates cost column
        costColumn = new TableColumn<Ticket, Long>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("cost"));
        table.getColumns().add(costColumn);

        //creates Ticket Type column
        typeColumn = new TableColumn<Ticket, TicketType>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<Ticket, TicketType>("type"));
        table.getColumns().add(typeColumn);

        //creates event name column
        eventNameColumn = new TableColumn<Ticket, String>("Event name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("eventname"));
        table.getColumns().add(eventNameColumn);

        //creates event number of tickets column
        eventNumberColumn = new TableColumn<Ticket, Float>("Event number");
        eventNumberColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Float>("eventnum"));
        table.getColumns().add(eventNumberColumn);

        //creates event id column
        eventIdColumn = new TableColumn<Ticket, Integer>("Event Id");
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("eventid"));
        table.getColumns().add(eventIdColumn);

        //creates author column
        authorColumn = new TableColumn<Ticket, String>("author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<Ticket, String>("author"));
        table.getColumns().add(authorColumn);
    }

}
