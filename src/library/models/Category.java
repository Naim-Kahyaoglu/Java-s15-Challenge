package library.models;

import java.util.ArrayList;
import java.util.List;

public class Category {
    // Fields
    private String name;             // The name of the category
    private List<Book> books;        // List of books in this category

    // Constructor
    public Category(String name) {
        this.name = name;
        this.books = new ArrayList<>();  // Initialize an empty list for books
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Methods to manage books in the category
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        if (!books.remove(book)) {
            System.out.println("The book was not found in the category: " + name);
        }
    }

    // Display all books in the category
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books are available in the '" + name + "' category.");
        } else {
            System.out.println("Books in '" + name + "' category:");
            for (Book book : books) {
                System.out.println("  - " + book.getTitle() + " by " + book.getAuthor() + " (ID: " + book.getId() + ")");
            }
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", books=" + books.size() + " books" +
                '}';
    }
}