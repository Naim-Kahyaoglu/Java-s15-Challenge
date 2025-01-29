package library;

import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize Library and LibraryService
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);

        // Add predefined books and users
        initializeLibrary(libraryService);

        // Prompt user to select an existing user
        User currentUser = selectUser(library, new Scanner(System.in));
        if (currentUser == null) {
            System.out.println("Exiting Library System. Goodbye!");
            return;
        }

        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\n==== Library System Menu (User: " + currentUser.getName() + ") ====");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. List All Books");
            System.out.println("5. List Books by Category");
            System.out.println("6. List Books by Author");
            System.out.println("7. Remove Book");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook(libraryService, scanner);
                case 2 -> borrowBook(libraryService, currentUser, scanner);
                case 3 -> returnBook(libraryService, currentUser, scanner);
                case 4 -> library.listAllBooks();
                case 5 -> listBooksByCategory(library, scanner);
                case 6 -> listBooksByAuthor(library, scanner);
                case 7 -> removeBook(library, scanner);
                case 8 -> {
                    System.out.println("Exiting Library System. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeLibrary(LibraryService libraryService) {
        // Add predefined books quietly
        libraryService.addBookSilently("BK1", "The Catcher in the Rye", "J.D. Salinger", "Fiction");
        libraryService.addBookSilently("BK2", "To Kill a Mockingbird", "Harper Lee", "Fiction");
        libraryService.addBookSilently("BK3", "1984", "George Orwell", "Dystopian");
        libraryService.addBookSilently("BK4", "Moby Dick", "Herman Melville", "Adventure");
        libraryService.addBookSilently("BK5", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction");
        libraryService.addBookSilently("BK6", "War and Peace", "Leo Tolstoy", "Historical");
        libraryService.addBookSilently("BK7", "Pride and Prejudice", "Jane Austen", "Romance");

        // Add predefined users quietly
        libraryService.addUserSilently("U01", "Alice");
        libraryService.addUserSilently("U02", "Bob");
        libraryService.addUserSilently("U03", "Charlie");
        libraryService.addUserSilently("U04", "Diana");

        System.out.println("Library initialized with predefined books and users.");
    }

    private static User selectUser(Library library, Scanner scanner) {
        // Fetch a list of all users
        List<User> userList = new ArrayList<>(library.getUsers().values());

        if (userList.isEmpty()) {
            System.out.println("No users found in the library. Please contact the administrator.");
            return null;
        }

        // Display users as a numbered list
        System.out.println("Select a user to proceed:");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println((i + 1) + ". " + userList.get(i).getName() + " (ID: " + userList.get(i).getId() + ")");
        }

        // Prompt user to select a user
        System.out.print("Enter the number corresponding to your choice: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Validate input
            if (choice < 1 || choice > userList.size()) {
                System.out.println("Invalid choice. Exiting Library System.");
                return null;
            }

            // Return the selected user
            return userList.get(choice - 1);
        } catch (Exception e) {
            System.out.println("Invalid input. Exiting Library System.");
            return null;
        }
    }

    private static void addBook(LibraryService libraryService, Scanner scanner) {
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

    private static void borrowBook(LibraryService libraryService, User user, Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        libraryService.borrowBook(user.getId(), bookId);
    }

    private static void returnBook(LibraryService libraryService, User user, Scanner scanner) {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        libraryService.getLibrary().returnBook(user.getId(), bookId);
    }

    private static void listBooksByCategory(Library library, Scanner scanner) {
        // Method implementation remains the same
        List<String> categoryList = new ArrayList<>(library.getCategories().keySet());

        if (categoryList.isEmpty()) {
            System.out.println("No categories available in the library.");
            return;
        }

        System.out.println("Available Categories:");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i));
        }

        System.out.print("Select a category by number: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice < 1 || choice > categoryList.size()) {
                System.out.println("Invalid choice. Please select a valid category number.");
                return;
            }

            String selectedCategory = categoryList.get(choice - 1);
            library.listBooksByCategory(selectedCategory);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Consume invalid input
        }
    }

    private static void listBooksByAuthor(Library library, Scanner scanner) {
        // Same as before: List authors and prompt for selection
    }

    private static void removeBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID to Remove: ");
        String bookId = scanner.nextLine();

        library.removeBook(bookId);
    }
}