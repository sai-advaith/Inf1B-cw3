import java.util.Arrays;
import java.util.Locale;
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

    /**Minimum book rating*/
    public final float MIN_RATING = 0.0f;

    /**Maximum book rating*/
    public final float MAX_RATING = 5.0f;

    /**Minimum number of pages*/
    public final int MIN_PAGES = 0;
    /**
     * This is a parameterised constructor to initialise the encapsulated variables
     * @param title is the title of the book and will be initialized to the encapsulated field 'title'
     * @param authors is the authors of the book and will be initialized to the encapsulated field 'authors'
     * @param rating is the rating of the book and will be initialized to the encapsulated field 'rating'
     * @param ISBN is the ISBN code of the book and will be initialized to the encapsulated field 'ISBN'
     * @param pages is the pages in a book and will be initialized to the encapsulated field 'pages'
     * @throws IllegalArgumentException if any of the parameters of the constructor are not valid
     * @throws NullPointerException if any of the parameters being passed into the constructor are null or contain null
     */
    public BookEntry(String title,String[] authors,float rating,String ISBN,int pages) throws IllegalArgumentException,
            NullPointerException{
        Objects.requireNonNull(title, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(authors, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(ISBN, StdMsg.STD_NULL_MSG.toString()); // checking for null in the parameters

        if (Arrays.asList(authors).contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // list cannot contain null
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(StdMsg.INVALID_RATING_MSG.toString()); // ratings check
        }
        else if (pages < MIN_PAGES) {
            throw new IllegalArgumentException(StdMsg.INVALID_PAGES_MSG.toString()); // pages check
        }
        // assigning once possible errors are checked for
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
     * @return the ISBN code of the book
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
     * This overrides toString() function, converting the book object to a string
     * @return the book object as a string
     */
    @Override
    public String toString() {
        String authorStr = Arrays.toString(authors); // string equivalent of the array
        return  title + '\n' +
                "by " + authorStr.substring(1,authorStr.length()-1) + '\n' + // everything apart from parenthesis
                "Rating: " + String.format(Locale.US,"%.02f",rating) + '\n' + // 2 decimal places
                "ISBN: " + ISBN + '\n' +
                pages + " pages";
    }

    /**
     * This overrides the equals() method to check if two books have the same instance fields
     * @param obj is of type Object and is comparing all instance fields of BookEntry with the parameter 'o'
     * @return a boolean if the two objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) { // checking whether obj is not null and of same class
            return false;
        }
        BookEntry bookEntry = (BookEntry) obj; // type casting the object
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(bookEntry.authors,authors) &&
                ISBN.equals(bookEntry.ISBN); // checking for parameters equality
    }

    /**
     * This is an overriding method to give the hashCode for all of the instance fields
     * For Strings and other primitive types, hash() is used and Arrays.hashCode() for authors
     * @return the hashCode for all five instance fields
     */
    @Override
    public int hashCode() {
        int oddPrime = 31; // used for calculating HashMap
        int hashcode = Objects.hash(title, rating, ISBN, pages); // hashcode of other variables
        hashcode = oddPrime * hashcode + Arrays.hashCode(authors); // hashcode of the array
        return hashcode;
    }
}
