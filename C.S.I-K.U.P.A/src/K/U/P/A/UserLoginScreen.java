package K.U.P.A;

import CSI.CSIThread;

import java.util.Scanner;

public class UserLoginScreen {

    private static CSIThread csiThread;

    private static boolean firstLogin = true;
    private static final Scanner input = new Scanner(System.in);

    private static String usernameToLogInto;

    public static void goToLoginScreen(){
        loginScreen();
        csiThread.startUI(usernameToLogInto);
    }

    public static void loginScreen(){

        System.out.println("1: Choose profile.\n" +
                           "2: Create new profile.\n");

        switch (input.nextLine()){
            case "1":
                System.out.println("Type in your username to login.");
                System.out.println("Below you have a list of registered usernames.\n");

                for (String username : csiThread.getUsernameList()){
                    System.out.println("-" + username);
                }

                String usernameInput = input.nextLine();

                boolean doesUserExist = false;

                for (String username : csiThread.getUsernameList()){
                    if (usernameInput.equals(username)){

                        usernameToLogInto = username;

                        if (firstLogin){
                            firstLogin = false;
                        }

                        System.out.println("\nYou have successfully logged in.\n\n");
                        doesUserExist = true;
                        break;
                    }
                }

                if (!doesUserExist){
                    System.out.println("That username is not registered.");
                    loginScreen();
                }

                break;
            case "2":
                System.out.println("Type in your username.\n");
                String username = input.nextLine();
                if (!csiThread.getUsernameList().contains(username)) {
                    User user = new User(username);

                    csiThread.addUser(user);
                    usernameToLogInto = user.getUsername();
                    System.out.println("\nProfile creation successful.\n");
                }else {
                    System.out.println("\nUsername already taken\n");
                    loginScreen();
                }
                break;
            default:
                System.out.println("Wrong input.");
                if (firstLogin){
                    loginScreen();
                }
        }

    }

    public static void setCSIThread(CSIThread csi){
        csiThread = csi;
    }

}
