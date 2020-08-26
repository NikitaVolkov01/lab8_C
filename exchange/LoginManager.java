package exchange;

import java.util.Scanner;

public class LoginManager {
    private static Scanner sc = new Scanner(System.in);
    public static void start(Client client){
        boolean loggedIn = false;
        while (!loggedIn){
            System.out.println("Введите логин или введите Р, если желаете зарегистрироваться");
            String login = sc.nextLine();
            if (login.equals("P") || login.equals("Р") || login.equals("R")) register(client);
            else {
                System.out.println("Введите пароль ");
                String password = sc.nextLine();
                client.get("login " + login + " " + password);
                if (client.isLogged()) {
                    loggedIn = true;
                    break;
                }
            }
        }
    }
    private static void register(Client client){
        System.out.println("Введите логин для регистрации ");
        String login = sc.nextLine();
        System.out.println("Введите пароль ");
        String password = sc.nextLine();
        client.get("register " + login + " " + password);
    }
}
