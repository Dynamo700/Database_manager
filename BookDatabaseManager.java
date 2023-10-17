import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class BookDatabaseManager {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3300/books";
    static final String USER = "root";
    static final String PASS = "password";
    PreparedStatement pstmt;
    Connection conn;

    List<Book> BookList;
    List<Author> authorList;

    public BookDatabaseManager() {
        try {
            this.BookList = new ArrayList<Book>();
            this.authorList = new ArrayList<Author>();
            this.conn = DriverManager.getConnection(DB_URL,
                    USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public int createBook(String isbn, String title, int editionNumber, String copyright) throws SQLException {
        pstmt.setString(1, isbn);
        pstmt.setString(2, title);
        pstmt.setInt(3, editionNumber);
        pstmt.setString(4, copyright);
        List<Author> authorList = new ArrayList<Author>();
        Book newBook = new Book(isbn, copyright, editionNumber, title, authorList);
        this.BookList.add(newBook);
        return pstmt.executeUpdate();
    }

    public int createAuthor(int authorID, String firstName, String lastName) throws SQLException {
        pstmt.setInt(1, authorID);
        pstmt.setString(2, firstName);
        pstmt.setString(3, lastName);
        List<Book> bookList = new ArrayList<Book>();
        Author newAuthor = new Author(authorID, firstName, lastName, bookList);
        this.authorList.add(newAuthor);
        return pstmt.executeUpdate();
    }

    public void addAuthor() throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Create an author");
        System.out.println("Enter Author ID");
        int authorID = Integer.parseInt(scan.nextLine());
        System.out.print("Enter First name: ");
        String firstName = scan.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scan.nextLine();

        pstmt = conn.prepareStatement("INSERT INTO authors (authorID, firstName, lastName) VALUES (?, ?, ?)");

        int result = createAuthor(authorID, firstName, lastName);
    }

    public void addBooktoAuthor() throws SQLException {
        System.out.println("List of authors: ");
        loadAuthors();
        System.out.println("Please select index of author: ");
        Scanner scan = new Scanner(System.in);
        System.out.println("Add book to author");
        System.out.println("Enter index of said author");
        int input = scan.nextInt();
        Author author = authorList.get(input - 1);
        System.out.println("Enter isbn");
        String isbn = scan.next();
        scan.nextLine();
        System.out.println("Enter title");
        String title = scan.nextLine();
        System.out.println("Enter edition number");
        int editionNumber = scan.nextInt();
        System.out.println("Enter copyright year");
        String copyright = scan.next();
        pstmt = conn.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
        createBook(isbn, title, editionNumber, copyright);
        Statement stmt= this.conn.createStatement();
        ResultSet rs = stmt.executeQuery("INSERT INTO authorisbn (authorid, isbn) VALUES ("+author.getAuthorID()+","+isbn+")");

//        for (int i = 0; i < authorList.size(); i++){
//            System.out.println((i + 1) +" - "+ authorList.get(i).getFirstName() + " " + authorList.get(i).getLastName());
//        }
//        ResultSet rs = stmt.executeQuery("select * from authors");
//        while (rs.next()){
//            int authorID = rs.getInt(1);
//            String firstName = rs.getString(2);
//            String lastName = rs.getString(3);
//        }
    }



    public void loadAuthors() throws SQLException {
        Statement stmt= this.conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from authors");
        int index = 1;
        while (rs.next()) {

            int authorID = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            ResultSet rs2 = stmt.executeQuery("select titles.isbn, titles.title, titles.editionNumber, titles.copyright from authorisbn inner join titles on authorISBN.isbn = titles.isbn where authorisbn.authorid =" + authorID );
            System.out.println(index + " - " + firstName + " " + " " + lastName);
            index++;
            List<Book> authorBooks = new ArrayList<Book>();
            while ( rs2.next() ){
                String isbn = rs2.getString(1);
                String title = rs2.getString(2);
                int editionNumber = rs2.getInt(3);
                String copyright = rs2.getString(4);
                System.out.println(isbn + " "+" " + title + " "+" " + editionNumber + " "+" "+ copyright);
                Book book = new Book(isbn, copyright, editionNumber, title, new ArrayList<Author>());
                authorBooks.add(book);
            }
            Author author = new Author(authorID, firstName, lastName, authorBooks);
            this.authorList.add(author);
        }
    }

    public void loadBooks() throws SQLException {
        Statement stmt= this.conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from titles");

        while (rs.next()){
            Statement stmt2 = this.conn.createStatement();

//            Book book = new Book();
            String isbn = rs.getString(1);
            String title = rs.getString(2);
            int editionNumber = rs.getInt(3);
            String copyright = rs.getString(4);
            ResultSet rs2 = stmt.executeQuery("select authors.authorID, authors.firstName, authors.lastName from authorisbn inner join authors on authorISBN.authorID = authors.authorID where authorisbn.isbn = \"" + isbn + "\"");
            System.out.println(isbn + " "+" " + title + " "+" " + editionNumber + " "+" "+ copyright);
            List<Author> bookAuthors = new ArrayList<Author>();
            while(rs2.next()) {
                int authorID = rs2.getInt(1);
                String firstName = rs2.getString(2);
                String lastName = rs2.getString(3);
                Author author = new Author(authorID, firstName, lastName, new ArrayList<Book>());
                bookAuthors.add(author);
                System.out.println(firstName + " " + " " + lastName);

            }
            Book book = new Book(isbn, title, editionNumber, copyright, bookAuthors);
            this.BookList.add(book);
        }
    }
    public void create() throws SQLException {
        try {
            while (true) {
                System.out.println("1. Add a book for an existing author");
                System.out.println("2. Add an author");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));

                String option = reader.readLine();
                Statement stmt = this.conn.createStatement();

                if (option.equals("1")){
                    System.out.println("create book");
                    System.out.println("Enter ISBN: ");
                    String isbn = (reader.readLine());
                    System.out.println("Enter Title: ");
                    String title = reader.readLine();
                    System.out.println("Enter Edition Number: ");
                    int editionNumber = Integer.parseInt(reader.readLine());
                    System.out.println("Enter Copyright year: ");
                    String copyright = (reader.readLine());

                    pstmt = conn.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
                    int result = createBook(isbn, title, editionNumber, copyright);
                    if (result > 0) {
                        System.out.println("Book added successfully");
                    } else {
                        System.out.println("Failed to add book");
                    }

                } else if (option.equals("2")) {
                    System.out.println("Create an author");
                    System.out.println("Enter Author ID");
                    int authorID = Integer.parseInt(reader.readLine());
                    System.out.print("Enter First name: ");
                    String firstName = reader.readLine();
                    System.out.print("Enter last name: ");
                    String lastName = reader.readLine();

                    pstmt = conn.prepareStatement("INSERT INTO authors (authorID, firstName, lastName) VALUES (?, ?, ?)");

                    int result = createAuthor(authorID, firstName, lastName);
                    if (result > 0) {
                        System.out.println("Author added successfully");
                    } else {
                        System.out.println("Sorry! Author could not be created");
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() throws SQLException {
        try
        {
            while (true) {
                System.out.println("press 1 to show all authors");
                System.out.println("press 2 to show all titles");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            String option = reader.readLine();
            Statement stmt= this.conn.createStatement();

            if (option.equals("1")) {
                loadAuthors();
                //append to the books list
                //do the same for authors and add to author's list

            } else if (option.equals("2")) {
                loadBooks();
                //append to the books list
                //do the same for authors and add to author's list

            }
                conn.close();
            }

        }catch(Exception e){ System.out.println(e);}
    }



}
