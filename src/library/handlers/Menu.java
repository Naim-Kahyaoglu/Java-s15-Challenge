package library.handlers;

public class Menu {

    public static void displayMenu() {
        System.out.println("\n==== Library System Menu ====");
        System.out.println("1. Add Book");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. List All Books");
        System.out.println("5. List Books by Category");
        System.out.println("6. List Books by Author");
        System.out.println("7. Remove Book");
        System.out.println("8. List Transaction History");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }
}