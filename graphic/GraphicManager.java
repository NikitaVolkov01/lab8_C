package graphic;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ticket.Ticket;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicManager {

    private static Map<String, String> userColor = new HashMap<String, String>();
    public static AnchorPane animationPane;
    public static ArrayList<Button> buttonList =  new ArrayList<Button>();

    public static void createAnimation(ObservableList<Ticket> tickets, TextArea textArea){

        for (Ticket ticket: tickets){
            createRect(ticket, textArea);
        }

    }

    public static void createRect(Ticket ticket, TextArea textArea){
        Button button = new Button();
        String color = null;

        if (userColor.containsKey(ticket.getAuthor())){
            color = userColor.get(ticket.getAuthor());
        }else{
            color = createColor();
            userColor.put(ticket.getAuthor(), color);
        }

        button.setStyle("-fx-border-radius: " + 25 + "; -fx-background-radius: " + 25 + ";-fx-background-color: "
                + color + "; -fx-pref-height: " + 25 + "; -fx-pref-width: " + 25);

        button.setLayoutX(ticket.getX() );
        button.setLayoutY(ticket.getY() );
        button.setFont(Font.font(8));
        button.setText(String.valueOf(ticket.getId()));

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    textArea.setText(LocalePrinter.printTicket(ticket));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        animationPane.getChildren().add(button);




        Rectangle rectParallel = new Rectangle(10,200,50, 50);
        rectParallel.setArcHeight(15);
        rectParallel.setArcWidth(15);
        rectParallel.setTranslateX(50);
        rectParallel.setTranslateY(75);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000), rectParallel);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setNode(button);


        //transition.setPath(rectParallel);
        //transition.setAutoReverse(true);
        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);

        rotateTransition.play();

        buttonList.add(button);
    }

    private static String createColor(){
        String green = Integer.toHexString((int) (Math.random()*105 + 150)).toUpperCase();
        String blue = Integer.toHexString((int) (Math.random()*105 + 150)).toUpperCase();
        String color = "#00" + green + blue;
        return color;
    }

    public static void deleteAnimation(){
        for(Button button: buttonList){
            animationPane.getChildren().remove(button);
        }
    }

}
