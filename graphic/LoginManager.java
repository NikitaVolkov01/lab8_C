package graphic;

import exchange.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginManager {

    private static String login;

    public Label label;
    public Button logButton;
    public Button regButton;
    public PasswordField passwordField;
    public TextField textField;
    public Label messageLabel;

    public ComboBox<String> langsComboBox;

    private Scene scene;
    private Stage primaryStage;

    public static String getLogin(){
        return login;
    }

    public void drawForm(Stage ps) throws IOException {

        primaryStage = ps;

        label = new Label("LOGIN / REGISTER");
        label.setTextFill(Color.WHITE); label.setFont(Font.font(22)); label.setAlignment(Pos.CENTER); label.setPrefHeight(80);
        //Label

        textField = new TextField(); textField.setPromptText("Username"); textField.setFont(Font.font(15)); textField.setMaxWidth(300); textField.setAlignment(Pos.CENTER);
        //Login

        passwordField = new PasswordField(); passwordField.setPromptText("Password"); passwordField.setFont(Font.font(15)); passwordField.setMaxWidth(300); passwordField.setAlignment(Pos.CENTER);
        //Password

        logButton = new Button("Login"); logButton.setFont(Font.font(15)); logButton.setPrefWidth(90); logButton.setAlignment(Pos.CENTER);
        //Login button
        regButton = new Button("Sign Up"); regButton.setFont(Font.font(15)); regButton.setPrefWidth(90); regButton.setAlignment(Pos.CENTER);
        //Login button
        HBox logregBox = new HBox(10, logButton, regButton); logregBox.setAlignment(Pos.CENTER);

        messageLabel = new Label(); messageLabel.setTextFill(Color.RED); messageLabel.setBackground(new Background(new BackgroundFill(Color.web("rgba(0,0,0,0.6)"), CornerRadii.EMPTY, Insets.EMPTY))); messageLabel.setFont(Font.font(14));

        ObservableList<String> langs = FXCollections.observableArrayList("Русский", "Nederlands", "Svenska", "English(Canada)");
        langsComboBox = new ComboBox<String>(langs); langsComboBox.setPrefWidth(140);
        langsComboBox.setPromptText("Language");
        langsComboBox.setOnAction((event) -> {
            try {
                Locale locale = new Locale(langsComboBox.getValue());
                ResourceBundle bundle = ResourceBundle.getBundle("resources/app", locale);
                //textArea.setText("LOCALE "+(locale.toString()));

                System.out.println(locale.toString());
                if (locale.toString().toLowerCase().trim().equals("svenska")) bundle = ResourceBundle.getBundle("resources/app", new Locale("sv"));
                else if (locale.toString().toLowerCase().trim().startsWith("neder")) {System.out.println("NNNEEE");bundle = ResourceBundle.getBundle("resources/app", new Locale("de", "NL"));}
                else if (locale.toString().contains("en")) bundle = ResourceBundle.getBundle("resources/app", new Locale("en")); //bundle = ResourceBundle.getBundle("app", new Locale("en"));
                else if (locale.toString().equals("Русский")) bundle = ResourceBundle.getBundle("resources/app", new Locale("ru"));

                label.setText(new String(bundle.getString("label").getBytes("ISO-8859-1"), "UTF-8"));
                logButton.setText(new String(bundle.getString("logButton").getBytes("ISO-8859-1"), "UTF-8"));
                regButton.setText(new String(bundle.getString("regButton").getBytes("ISO-8859-1"), "UTF-8"));
                textField.setPromptText(new String(bundle.getString("textField").getBytes("ISO-8859-1"), "UTF-8"));
                passwordField.setPromptText(new String(bundle.getString("passwordField").getBytes("ISO-8859-1"), "UTF-8"));

            } catch (UnsupportedEncodingException | NullPointerException e) {
                e.printStackTrace();
            }
        });


        VBox innerBox = new VBox(10, label, textField, passwordField, logregBox, langsComboBox, messageLabel); innerBox.setPrefHeight(400); innerBox.setPrefWidth(400);
        innerBox.setAlignment(Pos.CENTER); innerBox.setBackground(new Background(new BackgroundFill( Color.web("rgba(0,0,0,0.4)"), CornerRadii.EMPTY, Insets.EMPTY)));
        //Inner Element Box

        HBox hbox = new HBox(innerBox);
        hbox.setSpacing(10); hbox.setPrefWidth(1300); hbox.setPrefHeight(800);
        hbox.setAlignment(Pos.CENTER);
        // Picture Box

        scene = new Scene(hbox);

        FileInputStream input = new FileInputStream("src/css/pics/wolf.jpg");
        Image image = new Image(input);
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        hbox.setBackground(background);
        //loading image

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void show(){
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setListeners(Client client){

        logButton.setOnMouseClicked((event) -> {
            client.get("login " + textField.getText() + " " + passwordField.getText());
            if (client.isLogged()) {
                login = textField.getText();
                MainManager.drawScene(primaryStage, client, this);
                // Drawing main scene if logged in
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Login & Password are not correct. Try again or click Sign Up to Register");
            }
        });

        regButton.setOnMouseClicked((event -> {
            client.get("register " + textField.getText() + " " + passwordField.getText());
            if (client.isRegged()) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Successful Registration");
            }else{
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Registration failed. Name already in use");
            }
        }));

    }
}
