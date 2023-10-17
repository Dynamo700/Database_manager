import java.util.List;

public class Book {

    private String isbn;

    private String copyright;

    private int editionNumber;
    private String title;
    private List<Author> authorList;

    public Book(String isbn, String copyright, int editionNumber, String title, List<Author> authorList) {
        this.isbn = isbn;
        this.copyright = copyright;
        this.editionNumber = editionNumber;
        this.title = title;
        this.authorList = authorList;
    }

    //getters and setters of the book class
    public String getisbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Author> getAuthorList() {
        return this.authorList;
    }

    public void setisbn(String isbn) {
        this.isbn = isbn;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthorList(List<Author> authorList){
        this.authorList = authorList;
    }
}
