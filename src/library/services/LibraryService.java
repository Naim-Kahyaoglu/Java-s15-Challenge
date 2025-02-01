package library.services;

import library.models.Book;
import library.models.Library;
import library.models.User;
import library.utils.InputValidator;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void addBook(String id, String title, String author, String category) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(title) ||
                !InputValidator.isValidString(author) || !InputValidator.isValidCategory(category)) {
            return; // Abort if invalid input
        }

        Book book = new Book(id, title, author, category);
        library.addBook(book);
    }

    public void addUser(String id, String name) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(name)) {
            return; // Abort if invalid input
        }

        User user = new User(id, name);
        library.addUser(user);
    }

    // Add a book silently (no log output)
    public void addBookSilently(String id, String title, String author, String category) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(title) ||
                !InputValidator.isValidString(author) || !InputValidator.isValidCategory(category)) {
            return; // Abort if invalid input
        }

        Book book = new Book(id, title, author, category);
        library.addBook(book, true); // Silent mode
    }

    // Add a user silently (no log output)
    public void addUserSilently(String id, String name) {
        if (!InputValidator.isValidId(id) || !InputValidator.isValidString(name)) {
            return; // Abort if invalid input
        }

        User user = new User(id, name);
        library.addUser(user, true); // Silent mode
    }

    // Method to list all books (available and unavailable)
    public void listAllBooks() {
        if (library.getBooks().isEmpty()) {
            System.out.println("No books are currently available in the library.");
            return;
        }

        System.out.println("List of all books in the library:");
        for (Book book : library.getBooks().values()) { // Assuming books are stored as a Map<String, Book>
            System.out.println(book);
        }
    }

    // Method to list only available books
    public void listAllAvailableBooks() {
        if (library.getBooks().isEmpty()) {
            System.out.println("No books are currently available in the library.");
            return;
        }

        boolean hasAvailableBooks = false;
        System.out.println("List of available books in the library:");
        for (Book book : library.getBooks().values()) {
            if (book.isAvailable()) { // Show only books that can be borrowed
                System.out.println(book);
                hasAvailableBooks = true;
            }
        }

        if (!hasAvailableBooks) {
            System.out.println("No books are currently available for borrowing.");
        }
    }

    // Manage the borrowing flow (list books and handle borrowing process)
    public void handleBorrowBook() {
        // Step 1: List all available books first
        listAllAvailableBooks();

        // Step 2: Prompt the user for input
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter the ID of the book you want to borrow: ");
        String bookId = scanner.nextLine();

        // Step 3: Attempt to borrow the book
        borrowBook(userId, bookId);
    }
    // List the transaction history of a specific user
    public void listUserTransactions(String userId) {

        // Step 1: Get all users
        if (library.getUsers().isEmpty()) {
            System.out.println("No users are currently registered in the library.");
            return;
        }

        // Step 2: Display all users in a numbered list
        List<User> userList = new ArrayList<>(library.getUsers().values()); // Convert map to list
        System.out.println("Users in the library:");
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.println((i + 1) + ". " + user.getName() + " (User ID: " + user.getId() + ")");
        }

        // Step 3: Prompt the user to select a number
        Scanner scanner = new Scanner(System.in);
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
    // Core borrowing functionality
    public void borrowBook(String userId, String bookId) {
        // Ensure IDs are valid
        if (!InputValidator.isValidId(userId) || !InputValidator.isValidId(bookId)) {
            return;
        }

        // Find User and Book
        User user = library.getUsers().get(userId);
        Book book = library.getBooks().get(bookId);

        if (user == null) {
            System.out.println("No user found with ID: " + userId);
            return;
        }

        if (book == null) {
            System.out.println("No book found with ID: " + bookId);
            return;
        }

        // Validate borrowing rules
        if (!InputValidator.isWithinBorrowLimit(user)) {
            System.out.println("User '" + user.getName() + "' has reached their borrowing limit.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is currently not available.");
            return;
        }

        // Borrow the book
        library.borrowBook(userId, bookId);
    }
}