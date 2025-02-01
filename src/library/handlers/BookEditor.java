package library.handlers;

import library.models.Book;
import library.models.Library;
import library.utils.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookEditor {
    private final Library library;

    // Constructor
    public BookEditor(Library library) {
        this.library = library;
    }

    // Edit book information
    public void editBookInformation(Scanner scanner) {
        // Step 1: List all books in the library
        List<Book> bookList = new ArrayList<>(library.getBooks().values()); // Convert book map to list
        if (bookList.isEmpty()) {
            System.out.println("No books are available in the library.");
            return;
        }

        System.out.println("\nAvailable Books:");
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor() + " (Category: " + book.getCategory() + ")");
        }

        // Step 2: Prompt the user to select a book by index
        System.out.print("\nEnter the number of the book you want to edit: ");
        int bookIndex;
        try {
            bookIndex = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        if (bookIndex < 1 || bookIndex > bookList.size()) {
            System.out.println("Invalid choice. Please select a valid book number.");
            return;
        }

        Book selectedBook = bookList.get(bookIndex - 1); // Get the selected book

        // Step 3: Display the book's details in numbered order
        System.out.println("\nBook Information:");
        System.out.println("1. Title: " + selectedBook.getTitle());
        System.out.println("2. Author: " + selectedBook.getAuthor());
        System.out.println("3. Category: " + selectedBook.getCategory());

        // Step 4: Prompt the user to edit the fields
        System.out.println("\nWhat do you want to edit?");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. Cancel");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to the main menu.");
            scanner.nextLine(); // Clear invalid input
            return;
        }

        // Execute the chosen edit operation
        switch (choice) {
            case 1 -> editTitle(selectedBook, scanner);
            case 2 -> editAuthor(selectedBook, scanner);
            case 3 -> editCategory(selectedBook, scanner);
            case 4 -> System.out.println("Edit operation canceled.");
            default -> System.out.println("Invalid choice. Returning to the main menu.");
        }
    }

    // Edit the book's title
    private void editTitle(Book book, Scanner scanner) {
        System.out.print("Enter the new title: ");
        String newTitle = scanner.nextLine();
        if (InputValidator.isValidString(newTitle)) {
            String oldTitle = book.getTitle();
            book.setTitle(newTitle);
            System.out.println("Book title updated from '" + oldTitle + "' to '" + newTitle + "'.");
        }
    }

    // Edit the book's author
    private void editAuthor(Book book, Scanner scanner) {
        System.out.print("Enter the new author: ");
        String newAuthor = scanner.nextLine();
        if (InputValidator.isValidString(newAuthor)) {
            String oldAuthor = book.getAuthor();
            book.setAuthor(newAuthor);
            System.out.println("Book author updated from '" + oldAuthor + "' to '" + newAuthor + "'.");
        }
    }

    // Edit the book's category
    private void editCategory(Book book, Scanner scanner) {
        System.out.print("Enter the new category: ");
        String newCategory = scanner.nextLine();
        if (InputValidator.isValidCategory(newCategory)) {
            String oldCategory = book.getCategory();
            book.setCategory(newCategory);

            // Update the library data structure
            library.getCategories().get(oldCategory).removeBook(book); // Remove from old category
            library.getCategories().putIfAbsent(newCategory, new library.models.Category(newCategory)); // Add new category
            library.getCategories().get(newCategory).addBook(book); // Add to the new category

            System.out.println("Book category updated from '" + oldCategory + "' to '" + newCategory + "'.");
        }
    }
}