package library.handlers;

import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BookHandler {
    private final LibraryService libraryService;
    private final Library library;
    private final User currentUser;

    public BookHandler(LibraryService libraryService, Library library, User currentUser) {
        this.libraryService = libraryService;
        this.library = library;
        this.currentUser = currentUser;
    }
    // List all books in the library
    public void listBooks(Scanner scanner) {
        library.listAllBooks(); // Call the method from the Library class to list all books
    }
    // List books by category with numbered selection
    public void listBooksByCategory(Scanner scanner) {
        // Convert categories to a list for easy indexing
        List<String> categoryList = new ArrayList<>(library.getCategories().keySet());

        if (categoryList.isEmpty()) {
            System.out.println("No categories available in the library.");
            return;
        }

        // Display categories in numbered order
        System.out.println("\nAvailable Categories:");
        for (int i = 0; i < categoryList.size(); i++) {
            System.out.println((i + 1) + ". " + categoryList.get(i));
        }

        // Ask the user to choose a category
        System.out.print("\nEnter the number corresponding to the category: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice < 1 || choice > categoryList.size()) {
                System.out.println("Invalid choice. Please select a valid category number.");
                return;
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Get the selected category name and list books in that category
        String selectedCategory = categoryList.get(choice - 1);
        library.listBooksByCategory(selectedCategory); // Use Library's `listBooksByCategory` method
    }
    // List books by author with numbered selection
    public void listBooksByAuthor(Scanner scanner) {
        // Retrieve all books and extract unique authors
        List<String> authorList = new ArrayList<>();
        library.getBooks().values().forEach(book -> {
            if (!authorList.contains(book.getAuthor())) {
                authorList.add(book.getAuthor());
            }
        });

        if (authorList.isEmpty()) {
            System.out.println("No authors available in the library.");
            return;
        }

        // Display all authors in numbered order
        System.out.println("\nAvailable Authors:");
        for (int i = 0; i < authorList.size(); i++) {
            System.out.println((i + 1) + ". " + authorList.get(i));
        }

        // Prompt user to choose an author
        System.out.print("\nEnter the number corresponding to the author: ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Validate the user's choice
            if (choice < 1 || choice > authorList.size()) {
                System.out.println("Invalid choice. Please select a valid author number.");
                return;
            }

            // Get the selected author
            String selectedAuthor = authorList.get(choice - 1);

            // Call the `listBooksByAuthor` method in Library class
            library.listBooksByAuthor(selectedAuthor);

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // Add a new book to the library
    public void addBook(Scanner scanner) {
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
    public void borrowBook(Scanner scanner) {
        System.out.println("\nAvailable Books for Borrowing:");
        libraryService.listAllAvailableBooks();

        System.out.print("Enter the Book ID you want to borrow: ");
        String bookId = scanner.nextLine();

        libraryService.borrowBook(currentUser.getId(), bookId);
    }

    // Return a borrowed book
    public void returnBook(Scanner scanner) {
        System.out.print("Enter the Book ID to return: ");
        String bookId = scanner.nextLine();

        library.returnBook(currentUser.getId(), bookId);
    }

    // Remove a book from the library
    public void removeBook(Scanner scanner) {
        System.out.print("Enter the Book ID to remove: ");
        String bookId = scanner.nextLine();

        library.removeBook(bookId);
    }
}