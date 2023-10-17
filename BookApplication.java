import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BookApplication {
    public static void main(String[] args) throws SQLException {
        BookDatabaseManager manager = new BookDatabaseManager();

        int choice;

        Scanner input = new Scanner(System.in);

        System.out.println("Please select an option");
        System.out.println("1. Print all of the books from the database");
        System.out.println("2. Print all the authors from the database");
        System.out.println("3. Add a book to the database for an existing author");
        System.out.println("4. Add a new author");
        System.out.println("5. Quit");

        choice = input.nextInt();

        //options

//        public void createBook(Book book) {
//            try {
//                PreparedStatement statement = conn
//            }

        while (choice != 5){




            if (choice == 1) {
                manager.loadBooks();
//            System.out.println("Enter bicycle name");
//            String name = reader.readLine();
//            System.out.println("Enter bicycle colour");
//            String colour = reader.readLine();
//            System.out.println("Enter bicycle size");
//            int size = Integer.parseInt(reader.readLine());
//            System.out.println("Enter bicycle price");
//            float price = Float.parseFloat(reader.readLine());
//            int noInserted = InsertBicycle(stmt, name, colour, size, price);
//            System.out.printf("%s rows inserted", noInserted);
            } else if (choice == 2) {
                manager.loadAuthors();
            } else if (choice == 3){
                manager.addBooktoAuthor();
            } else if (choice == 4){
                manager.addAuthor();
            }
            System.out.println("Please select an option");
            System.out.println("1. Print all of the books from the database");
            System.out.println("2. Print all the authors from the database");
            System.out.println("3. Add a book to the database for an existing author");
            System.out.println("4. Add a new author");
            System.out.println("5. Quit");
            choice = input.nextInt();

        }
    }

}