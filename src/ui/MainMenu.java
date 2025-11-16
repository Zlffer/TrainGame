
package ui;

import actions.*;
import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private boolean isRunning;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    public void run() {
        System.out.println("Welcome to the passenger train simulator!");

        while (isRunning) {
            displayMainMenu();

            String input = scanner.nextLine();

            handleMenuInput(input);
        }

        System.out.println("Simulator shutdown...");
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Creating a train");
        System.out.println("2. Downloading a train");
        System.out.println("3. Manual");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleMenuInput(String input) {

        switch (input) {
            case "1":
                new MMCreateTrainAction(scanner).execute();
                break;
            case "2":
                new MMDownloadTrainAction(scanner).execute();
                break;
            case "3":
                new MMShowManualAction(scanner).execute();
                break;
            case "4":
                new MMExitAction().execute();
                this.isRunning = false;
                break;
            default:
                System.out.println("Wrong choice >:( ...");
        }
    }

}