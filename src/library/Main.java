package library;

import library.handlers.Initializer;
import library.handlers.MenuHandler;
import library.models.Library;
import library.models.User;
import library.services.LibraryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Step 1: Initialize the library system
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library);
        Initializer.initializeLibrary(libraryService);

        // Step 2: Prompt user selection
        User currentUser = Initializer.selectUser(library, new Scanner(System.in));
        if (currentUser == null) {
            System.out.println("Exiting Library System. Goodbye!");
            return;
        }

        // Step 3: Menu Handler to process user input
        MenuHandler menuHandler = new MenuHandler(libraryService, library, currentUser);
        menuHandler.startMenu();
    }
}