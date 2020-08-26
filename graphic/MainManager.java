package graphic;

import exchange.Client;
import graphic.handlers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ticket.Ticket;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainManager {

    private static Label userLabel;
    private static Button infoButton;
    private static Button addButton;
    private static Button updateButton;
    private static Button removeButton;
    private static Button clearButton;
    private static Button executeScriptButton;
    private static Button exitButton;
    private static Button logOutButton;
    private static Button historyButton;

    private static TableManager tableManager;
    private static LoginManager loginManager;

    private static TextArea textArea;
    public static ComboBox<String> langsComboBox;

    public static void drawScene(Stage primaryStage, Client client, LoginManager loginM){

        loginManager = loginM;
        tableManager = new TableManager(client);
        TableView<Ticket> table = tableManager.getTable();
        //creating table

        textArea = new TextArea();
        textArea.setMaxWidth(950);
        textArea.setMaxHeight(160);

        userLabel = new Label("User: "+LoginManager.getLogin()); userLabel.setPrefWidth(140);
        infoButton = new Button("Info"); infoButton.setPrefWidth(140);
        addButton = new Button("Add"); addButton.setPrefWidth(140);
        updateButton = new Button("Update"); updateButton.setPrefWidth(140);
        removeButton = new Button("Remove"); removeButton.setPrefWidth(140);
        clearButton = new Button("Clear"); clearButton.setPrefWidth(140);
        executeScriptButton = new Button("Execute Script");
        exitButton = new Button("Exit"); exitButton.setPrefWidth(140);
        logOutButton = new Button("Log Out"); logOutButton.setPrefWidth(140);
        historyButton = new Button("History"); historyButton.setPrefWidth(140);

        ObservableList<String> langs = FXCollections.observableArrayList("Русский", "Nederlands", "Svenska", "English(Canada)");
        langsComboBox = new ComboBox<String>(langs); langsComboBox.setPrefWidth(140);
        langsComboBox.setPromptText("Language");
        langsComboBox.setOnAction((event) -> {
            try {
                loadLang(langsComboBox.getValue());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });


        AnchorPane animatonPane = new AnchorPane();
        animatonPane.setPrefWidth(330);
        animatonPane.setMaxHeight(650);
        //animatonPane.setBackground(new Background(new BackgroundFill( Color.web("rgba(0,0,0,0.4)"), CornerRadii.EMPTY, Insets.EMPTY)));
        animatonPane.setBorder(new Border(new BorderStroke(Color.web("rgba(0,0,0,0.3)"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        animatonPane.setBackground(new Background(new BackgroundFill(Color.web("rgba(0,0,0,0.025)"), CornerRadii.EMPTY, Insets.EMPTY)));
        GraphicManager.animationPane = animatonPane;
        GraphicManager.createAnimation(tableManager.tickets, textArea);


        ButtonAddHandler.setAddAction(client, tableManager, addButton, textArea);
        ButtonInfoHandler.setInfoAction(client, infoButton, textArea);
        ButtonUpdateHandler.setUpdateAction(client, tableManager, updateButton, textArea);
        ButtonRemoveHandler.setRemoveAction(client, tableManager, removeButton, textArea);
        ButtonClearHandler.setClearAction(client, tableManager, clearButton, textArea);
        ButtonExitHandler.setExitAction(exitButton);
        ButtonLogOutHandler.setLogOutAction(logOutButton, loginManager);
        ButtonHistoryHandler.setHistoryAction(client, historyButton, textArea);
        //adding handlers on buttons

        VBox userBox = new VBox(10, userLabel, exitButton, logOutButton, langsComboBox);
        userBox.setAlignment(Pos.CENTER);
        userBox.setPrefHeight(150);

        userBox.setBackground(new Background(new BackgroundFill(Color.web("rgba(0, 0, 0, 0.025)"), CornerRadii.EMPTY, Insets.EMPTY)));
        VBox buttons = new VBox(10, userBox, infoButton, addButton, updateButton, removeButton, clearButton, historyButton);
        buttons.setPrefWidth(160);
        buttons.setAlignment(Pos.TOP_CENTER);

        //adding buttons

        Label aL = new Label("");
        aL.setPrefWidth(1000);
        HBox hbox = new HBox(10, new VBox(10, new HBox(10, table, buttons), textArea), animatonPane);
        Scene scene = new Scene(hbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String loc;

    public static void loadLang(String langName) throws UnsupportedEncodingException, NullPointerException {
        Locale locale = new Locale(langName);
        ResourceBundle bundle = ResourceBundle.getBundle("resources/app", locale);
        //textArea.setText("LOCALE "+(locale.toString()));

        System.out.println(locale.toString());
        Locale loc = null;
        if (locale.toString().toLowerCase().trim().equals("svenska")) { loc = new Locale("sv"); }
        else if (locale.toString().toLowerCase().trim().startsWith("neder")) loc = new Locale("de", "NL");
        else if (locale.toString().contains("en")) loc = new Locale("en"); //bundle = ResourceBundle.getBundle("app", new Locale("en"));
        else if (locale.toString().equals("Русский")) loc = new Locale("ru");

        bundle = ResourceBundle.getBundle("resources/app", loc);
        LocalePrinter.setLocale(loc);

        loginManager.label.setText(new String(bundle.getString("label").getBytes("ISO-8859-1"), "UTF-8"));
        loginManager.logButton.setText(new String(bundle.getString("logButton").getBytes("ISO-8859-1"), "UTF-8"));
        loginManager.regButton.setText(new String(bundle.getString("regButton").getBytes("ISO-8859-1"), "UTF-8"));
        loginManager.textField.setPromptText(new String(bundle.getString("textField").getBytes("ISO-8859-1"), "UTF-8"));
        loginManager.passwordField.setPromptText(new String(bundle.getString("passwordField").getBytes("ISO-8859-1"), "UTF-8"));

        userLabel.setText(new String(bundle.getString("user").getBytes("ISO-8859-1"), "UTF-8") + userLabel.getText().split(": ")[1]);

        addButton.setText(new String(bundle.getString("addCommand").getBytes("ISO-8859-1"), "UTF-8"));
        infoButton.setText(new String(bundle.getString("infoCommand").getBytes("ISO-8859-1"), "UTF-8"));
        updateButton.setText(new String(bundle.getString("updateCommand").getBytes("ISO-8859-1"), "UTF-8"));
        removeButton.setText(new String(bundle.getString("removeCommand").getBytes("ISO-8859-1"), "UTF-8"));
        clearButton.setText(new String(bundle.getString("clearCommand").getBytes("ISO-8859-1"), "UTF-8"));
        executeScriptButton.setText(new String(bundle.getString("executeScriptCommand").getBytes("ISO-8859-1"), "UTF-8"));
        exitButton.setText(new String(bundle.getString("exitCommand").getBytes("ISO-8859-1"), "UTF-8"));
        logOutButton.setText(new String(bundle.getString("logOutCommand").getBytes("ISO-8859-1"), "UTF-8"));
        historyButton.setText(new String(bundle.getString("historyCommand").getBytes("ISO-8859-1"), "UTF-8"));

        tableManager.idColumn.setText(new String(bundle.getString("idColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.nameColumn.setText(new String(bundle.getString("nameColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.xColumn.setText(new String(bundle.getString("xColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.yColumn.setText(new String(bundle.getString("yColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.dateColumn.setText(new String(bundle.getString("dateColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.costColumn.setText(new String(bundle.getString("costColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.typeColumn.setText(new String(bundle.getString("typeColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.eventNameColumn.setText(new String(bundle.getString("eventNameColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.eventNumberColumn.setText(new String(bundle.getString("eventNumberColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.eventIdColumn.setText(new String(bundle.getString("eventIdColumn").getBytes("ISO-8859-1"), "UTF-8"));
        tableManager.authorColumn.setText(new String(bundle.getString("authorColumn").getBytes("ISO-8859-1"), "UTF-8"));

        ButtonAddHandler.nameField.setPromptText(new String(bundle.getString("nameField").getBytes("ISO-8859-1"), "UTF-8"));
        ButtonAddHandler.nameLabel.setText(new String(bundle.getString("nameLabel").getBytes("ISO-8859-1"), "UTF-8"));
    }
}
