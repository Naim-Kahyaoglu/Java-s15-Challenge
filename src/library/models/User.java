package library.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Fields
    private String id;                  // Unique ID for the user
    private String name;                // Name of the user
    private List<Book> borrowedBooks;   // List of books borrowed by the user
    private static final int BORROW_LIMIT = 3; // Max books a user can borrow

    // Constructor
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Check if the user can borrow more books
    public boolean canBorrow() {
        return borrowedBooks.size() < BORROW_LIMIT;
    }

    // Borrow a book
    public boolean borrowBook(Book book) {
        if (!canBorrow()) {
            System.out.println("User '" + name + "' has reached the borrowing limit of " + BORROW_LIMIT + " books.");
            return false;
        }

        borrowedBooks.add(book);
        System.out.println("Book '" + book.getTitle() + "' has been borrowed by user '" + name + "'.");
        return true;
    }

    // Return a book
    public boolean returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            System.out.println("Book '" + book.getTitle() + "' was not borrowed by user '" + name + "'.");
            return false;
        }

        borrowedBooks.remove(book);
        System.out.println("Book '" + book.getTitle() + "' has been returned by user '" + name + "'.");
        return true;
    }

    // List all borrowed books
    public void listBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("User '" + name + "' has no borrowed books.");
            return;
        }

        System.out.println("Books borrowed by '" + name + "':");
        for (Book book : borrowedBooks) {
            System.out.println("  - " + book.getTitle() + " by " + book.getAuthor() + " (ID: " + book.getId() + ")");
        }
    }

    // toString method to display user details
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", borrowedBooks=" + borrowedBooks.size() + " books" +
                '}';
    }
}