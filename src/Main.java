

import library.models.Library;
import library.services.LibraryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize Library and LibraryService
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);

        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\n==== Library System Menu ====");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. List All Books");
            System.out.println("6. List Books by Category");
            System.out.println("7. List Books by Author");
            System.out.println("8. Remove Book");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook(libraryService, scanner);
                case 2 -> addUser(libraryService, scanner);
                case 3 -> borrowBook(libraryService, scanner);
                case 4 -> returnBook(libraryService, scanner);
                case 5 -> library.listAllBooks();
                case 6 -> listBooksByCategory(library, scanner);
                case 7 -> listBooksByAuthor(library, scanner);
                case 8 -> removeBook(library, scanner);
                case 9 -> {
                    System.out.println("Exiting Library System. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
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

    private static void addUser(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter User ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        libraryService.addUser(id, name);
    }

    private static void borrowBook(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        libraryService.borrowBook(userId, bookId);
    }

    private static void returnBook(LibraryService libraryService, Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        libraryService.getLibrary().returnBook(userId, bookId);
    }

    private static void listBooksByCategory(Library library, Scanner scanner) {
        System.out.print("Enter Category Name: ");
        String category = scanner.nextLine();

        library.listBooksByCategory(category);
    }

    private static void listBooksByAuthor(Library library, Scanner scanner) {
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();

        library.listBooksByAuthor(author);
    }

    private static void removeBook(Library library, Scanner scanner) {
        System.out.print("Enter Book ID to Remove: ");
        String bookId = scanner.nextLine();

        library.removeBook(bookId);
    }
}