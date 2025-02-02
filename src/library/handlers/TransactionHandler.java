package library.handlers;

import library.models.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionHandler {
    private final Library library;

    public TransactionHandler(Library library) {
        this.library = library;
    }

    // List transaction history for a user
    public void listTransactionsHistory(Scanner scanner) {
        if (library.getUsers().isEmpty()) {
            System.out.println("No users are currently registered in the library.");
            return;
        }

        List<library.models.User> userList = new ArrayList<>(library.getUsers().values());
        System.out.println("\nUsers in the library:");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println((i + 1) + ". " + userList.get(i).getName() + " (User ID: " + userList.get(i).getId() + ")");
        }

        System.out.print("\nEnter the number corresponding to the user to view their transaction history: ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            if (choice < 1 || choice > userList.size()) {
                System.out.println("Invalid selection. Please try again.");
                return;
            }

            library.models.User selectedUser = userList.get(choice - 1);
            System.out.println("\nTransaction history for " + selectedUser.getName() + ":");
            library.listUserTransactions(selectedUser.getId()); // Assuming `listUserTransactions` exists in Library
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to the menu.");
            scanner.nextLine();
        }
    }
}