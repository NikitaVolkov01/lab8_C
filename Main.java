import exchange.Client;
import graphic.LoginManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Client client;
    private final static String HOST = "localhost";
    private final static int PORT = 1111;

    @Override
    public void start(Stage primaryStage) throws Exception{

        client = new Client(HOST, PORT);
        if (!client.connect()) System.exit(1);
        LoginManager loginManager = new LoginManager();
        loginManager.drawForm(primaryStage);

        loginManager.setListeners(client);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
