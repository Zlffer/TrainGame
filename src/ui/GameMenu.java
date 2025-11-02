
package ui;

import actions.*;
import java.util.Scanner;


public class GameMenu {

    private Scanner scanner;
    private boolean isRunning;

    public GameMenu(Scanner scanner) {
        this.scanner = scanner;
        this.isRunning = true;
    }

    public void run() {
        System.out.println("\n[DEBUG]: Train is ready. Entering Game Menu...");

        while (isRunning) {
            displayGameMenu();
            String input = scanner.nextLine();
            handleGameMenuInput(input);
        }

        System.out.println("[DEBUG]: Exiting Game Menu, returning to Main Menu...");
    }

    private void displayGameMenu() {
        System.out.println("\n--- Game Menu (Train Control) ---");
        System.out.println("1. Next station");
        System.out.println("2. Station info");
        System.out.println("3. Carriage info (sort by comfort)");
        System.out.println("4. Passengers transit");
        System.out.println("5. Check carriages (tickets)");
        System.out.println("6. Find carriages (by passenger range)");
        System.out.println("7. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void handleGameMenuInput(String input) {
        switch (input) {
            case "1":
                System.out.println("[DEBUG]: Action 1 (Next Station)...");
                break;
            case "2":
                System.out.println("[DEBUG]: Action 2 (Station Info)...");
                break;
            case "3":
                System.out.println("[DEBUG]: Action 3 (Carriage Info)...");
                break;
            case "4":
                System.out.println("[DEBUG]: Action 4 (Passengers Transit)...");
                break;
            case "5":
                System.out.println("[DEBUG]: Action 5 (Check Carriages)...");
                break;
            case "6":
                System.out.println("[DEBUG]: Action 6 (Find Carriages)...");
                break;
            case "7":
                this.isRunning = false;
                break;
            default:
                System.out.println("Wrong choice >:( ...");
        }
    }
}