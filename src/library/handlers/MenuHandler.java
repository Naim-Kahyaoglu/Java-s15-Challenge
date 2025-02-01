package library.handlers;

import library.models.Book;
import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private final LibraryService libraryService;
    private final Library library;
    private final User currentUser;
    private final BookEditor bookEditor;


    // Constructor
    public MenuHandler(LibraryService libraryService, Library library, User currentUser) {
        this.libraryService = libraryService;
        this.library = library;
        this.currentUser = currentUser;
        this.bookEditor = new BookEditor(library);

    }

    // Starts the main menu loop
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display the main menu
            System.out.println("\n==== Library System Menu ====");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. List All Books");
            System.out.println("5. List Books by Category");
            System.out.println("6. List Books by Author");
            System.out.println("7. Remove Book");
            System.out.println("8. Edit Book Information");
            System.out.println("9. Exit");
            System.out.println("10. List Transactions History");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                processChoice(choice, scanner);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.");
                scanner.nextLine(); // Clear invalid input
            }
        }

    }

    // Processes the user's choice from the main menu
    private void processChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1 -> addBook(scanner);
            case 2 -> borrowBook(scanner);
            case 3 -> returnBook(scanner);
            case 4 -> libraryService.listAllBooks();
            case 5 -> listBooksByCategory(scanner);
            case 6 -> listBooksByAuthor(scanner);
            case 7 -> removeBook(scanner);
            case 8 -> bookEditor.editBookInformation(scanner);
            case 9 -> {
                System.out.println("Exiting Library System. Goodbye!");
                System.exit(0);
            }
            case 10 -> listTransactionsHistory(scanner); // <== New Case
            default -> System.out.println("Invalid option. Please enter a valid number from 1 to 10.");
        }
    }


    // Add a new book to the library
    private void addBook(Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Book Category: ");
        String category = scanner.nextLine();

        libraryService.addBook(id, title, author, category);
    }

    // Borrow a book
    private void borrowBook(Scanner scanner) {
        System.out.println("\nAvailable Books for Borrowing:");
        libraryService.listAllAvailableBooks();

        System.out.print("Enter the Book ID you want to borrow: ");
        String bookId = scanner.nextLine();

        libraryService.borrowBook(currentUser.getId(), bookId);
    }

    // Return a borrowed book
    private void returnBook(Scanner scanner) {
        System.out.print("Enter the Book ID to return: ");
        String bookId = scanner.nextLine();

        libraryService.getLibrary().returnBook(currentUser.getId(), bookId);
    }

    // List books by category
    private void listBooksByCategory(Scanner scanner) {
        // Step 1: Retrieve all categories and display them
        List<String> categories = new ArrayList<>(library.getCategories().keySet());
        if (categories.isEmpty()) {
            System.out.println("No categories are available in the library.");
            return;
        }

        System.out.println("\nAvailable Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        // Step 2: Prompt the user to select a category
        System.out.print("\nEnter the number corresponding to the category: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Validate the user's input
            if (choice < 1 || choice > categories.size()) {
                System.out.println("Invalid choice. Returning to the main menu.");
                return;
            }

            // Step 3: Fetch the books in the selected category
            String selectedCategory = categories.get(choice - 1);
            List<Book> books = library.getBooksByCategory(selectedCategory);

            // Step 4: Display the books in the selected category
            if (books.isEmpty()) {
                System.out.println("No books are available in the '" + selectedCategory + "' category.");
            } else {
                System.out.println("\nBooks in '" + selectedCategory + "' category:");
                for (Book book : books) {
                    System.out.println("  - " + book.getTitle() + " by " + book.getAuthor() + " (ID: " + book.getId() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // List books by author
    private void listBooksByAuthor(Scanner scanner) {
        // Step 1: Display all authors
        List<String> authors = new ArrayList<>(library.getAllAuthors());
        if (authors.isEmpty()) {
            System.out.println("No authors are available in the library.");
            return;
        }

        System.out.println("\nAvailable Authors:");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println((i + 1) + ". " + authors.get(i));
        }

        // Step 2: Prompt the user to select an author
        System.out.print("\nEnter the number corresponding to the author: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Validate the user's input
            if (choice < 1 || choice > authors.size()) {
                System.out.println("Invalid choice. Returning to the main menu.");
                return;
            }

            // Step 3: Get the books by the selected author and display them
            String selectedAuthor = authors.get(choice - 1);
            library.listBooksByAuthor(selectedAuthor);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // Remove a book by its ID
    private void removeBook(Scanner scanner) {
        System.out.print("Enter the Book ID to remove: ");
        String bookId = scanner.nextLine();

        library.removeBook(bookId);
    }
    // List the transaction history of a user
    private void listTransactionsHistory(Scanner scanner) {
        System.out.print("Enter the User ID to view their transaction history: ");
        String userId = scanner.nextLine();

        // Call the library service to list the transaction history for the user
        libraryService.listUserTransactions(userId);
    }
}
