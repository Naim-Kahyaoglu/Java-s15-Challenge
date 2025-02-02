package library.handlers;

import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.Scanner;

public class MenuHandler {
    private final BookHandler bookHandler;
    private final TransactionHandler transactionHandler;

    public MenuHandler(LibraryService libraryService, Library library, User currentUser) {
        this.bookHandler = new BookHandler(libraryService, library, currentUser);
        this.transactionHandler = new TransactionHandler(library);
    }

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu.displayMenu();
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume any trailing newline
                processChoice(choice, scanner);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void processChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> bookHandler.addBook(scanner);
            case 2 -> bookHandler.borrowBook(scanner);
            case 3 -> bookHandler.returnBook(scanner);
            case 4 -> bookHandler.listBooks(scanner); // Assuming another method could handle this.
            case 5 -> bookHandler.listBooksByCategory(scanner); // Assuming implementation already exists.
            case 6 -> bookHandler.listBooksByAuthor(scanner); // Assuming implementation already exists.
            case 7 -> bookHandler.removeBook(scanner);
            case 8 -> transactionHandler.listTransactionsHistory(scanner);
            case 9 -> {
                System.out.println("Exiting Library System. Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please enter a valid number.");
        }
    }
}