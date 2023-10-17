import java.util.List;
public class Author {
    private int authorID;

    private String firstName;

    private String lastName;
    private List<Book> bookList;

    public Author(int authorID, String firstName, String lastName, List<Book> bookList) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = bookList;
    }

    //getters and setters of the book class
    public int getAuthorID() {
        return this.authorID;
    }

    public String getFirstName() { return this.firstName; }

    public String getLastName() { return this.lastName; }

    public List<Book> getBookList() { return this.bookList; }

    public void setBookList(List<Book> bookList) { this.bookList = bookList; }

    public void setAuthorID(int authorID) { this.authorID = authorID; }




}
