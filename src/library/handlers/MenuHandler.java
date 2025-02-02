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
            System.out.println("9. List Transactions History"); // Moved here
            System.out.println("10. Exit"); // Moved here
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                processChoice(choice, scanner);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number from 1 to 10.");
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
            case 9 -> listTransactionsHistory(scanner); // Updated number
            case 10 -> {
                System.out.println("Exiting Library System. Goodbye!");
                System.exit(0);
            }
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
        // Step 1: Get all users
        if (library.getUsers().isEmpty()) {
            System.out.println("No users are currently registered in the library.");
            return;
        }

        // Step 2: Display all users in a numbered list
        List<User> userList = new ArrayList<>(library.getUsers().values()); // Convert map to list
        System.out.println("\nUsers in the library:");
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.println((i + 1) + ". " + user.getName() + " (User ID: " + user.getId() + ")");
        }

        // Step 3: Prompt the user to select a user from the list
        System.out.print("\nEnter the number corresponding to the user to view their transaction history: ");
        try {
            int choice = scanner.nextInt(); // Get the number from the user
            scanner.nextLine(); // Clear any leftover newline character

            // Step 4: Validate input
            if (choice < 1 || choice > userList.size()) {
                System.out.println("Invalid selection. Please enter a number between 1 and " + userList.size() + ".");
                return;
            }

            // Step 5: Fetch and display the selected user's transaction history
            User selectedUser = userList.get(choice - 1); // Convert to zero-based index
            System.out.println("\nTransaction history for user: " + selectedUser.getName());
            library.listUserTransactions(selectedUser.getId()); // Delegate to the Library class' method

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
}
