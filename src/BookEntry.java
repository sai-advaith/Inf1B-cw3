import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {
    /**This is an encapsulated string for the book title*/
    private final String title;
    /**This is an encapsulated string array used to store the author/s of the book*/
    private final String[] authors;
    /**This is an encapsulated float for the rating of the book out of 5*/
    private final float rating;
    /**This is an encapsulated String for the ISBN code of the book*/
    private final String ISBN;
    /**This is an encapsulated integer for the number of pages in the book*/
    private final int pages;

    /**
     * This is a parameterised constructor to initialise the encapsulated the class variables
     * @param title is the title of the book and will hence be initialized to the encapsulated class variable title
     * @param authors is the array of authors and will be initialized to the encapsulated class variable authors
     * @param rating is the rating of the book and will be initialized to the encapsulated class variable rating
     * @param ISBN is the ISBN code of the book and will be initialized to the encapsulated class variable ISBN
     * @param pages is the number of pages in a book and will be initialized to the encapsulated class variable pages
     * @throws IllegalArgumentException is thrown if any of the parameters of the constructor are not valid and hence need to be handled
     * @throws NullPointerException is thrown is any of the objects being passed into the constructor are null
     */
    public BookEntry(String title,String[] authors,float rating,String ISBN,int pages) throws IllegalArgumentException, NullPointerException{
        if (title == null) {
            throw new NullPointerException("Title cannot be null");
        }
        else if (authors == null) {
            throw new NullPointerException("Authors cannot be null");
        }
        else if (ISBN == null) {
            throw new NullPointerException("ISBN cannot be null");
        }
        else if (authors.length == 0) {
            throw new IllegalArgumentException("There cannot be 0 authors");
        }
        else if (Arrays.asList(authors).contains(null)) {
            throw new IllegalArgumentException("No author cannot be null");
        }
        else if(title.length() == 0) {
            throw new IllegalArgumentException("Title cannot be of length 0");
        }
        else if(ISBN.length() == 0) {
            throw new IllegalArgumentException("ISBN length cannot be 0");
        }
        else if (rating <0.0) {
            throw new IllegalArgumentException("Rating cannot negative");
        }
        else if (rating > 5.0) {
            throw new IllegalArgumentException("Rating cannot be more than 5");
        }
        else if (pages < 0) {
            throw new IllegalArgumentException("Pages cannot be negative");
        }
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /**
     * This is the getter method to attain the title of the book
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * This is the getter method to attain the array of authors of the book
     * @return the authors of the book
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * This is the getter method to attain the rating of the book
     * @return the rating of the book
     */
    public float getRating() {
        return rating;
    }

    /**
     * This is the getter method to attain the ISBN code of the book
     * @return
     */
    public String getISBN() {
        return ISBN;
    }


    /**
     * This is the getter method to attain the number of pages in the book
     * @return the pages of the book
     */
    public int getPages() {
        return pages;
    }

    /**
     * This is the overriding toString() function converting the book object to a string
     * @return the book object as a string
     */
    @Override
    public String toString() {
        return  title + '\n' +
                "by " + Arrays.toString(authors).substring(1,Arrays.toString(authors).length()-1) + '\n' +
                "Rating: " + String.format("%.02f",rating) + '\n' +
                "ISBN: " + ISBN + '\n' +
                pages + " pages";
    }

    /**
     * This is the overriding equals() method to check if two books have the same fields
     * @param o is of type Object and is comparing all instance fields with the parameter 'o'
     * @return a boolean if the two books are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookEntry bookEntry = (BookEntry) o; // type casting the object
        String[] authorsCopyBookEntry = (Arrays.copyOf(bookEntry.authors,bookEntry.authors.length)); // not changing the original array
        String[] authorsCopy = (Arrays.copyOf(authors,authors.length)); // not changing the original array
        Arrays.sort(authorsCopyBookEntry); // sorting the array
        Arrays.sort(authorsCopy); // sorting the array
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authorsCopyBookEntry,authorsCopy) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**
     * This is an overriding method to give the hashCode of the class variables
     * @return the hashCode of the class variables
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }
}
