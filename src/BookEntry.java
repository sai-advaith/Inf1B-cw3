import java.util.Arrays;
import java.util.InputMismatchException;
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
     * This is a parameterised constructor to initialise the encapsulated the class variables
     * @param title is the title of the book and will hence be initialized to the encapsulated class variable title
     * @param authors is the array of authors and will be initialized to the encapsulated class variable authors
     * @param rating is the rating of the book and will be initialized to the encapsulated class variable rating
     * @param ISBN is the ISBN code of the book and will be initialized to the encapsulated class variable ISBN
     * @param pages is the number of pages in a book and will be initialized to the encapsulated class variable pages
     * @throws IllegalArgumentException is thrown if any of the parameters of the constructor are not valid and hence need to be handled
     * @throws NullPointerException is thrown is any of the objects being passed into the constructor are null
     * @throws ArithmeticException is thrown if the rating is positive or negative infinity or NaN.
     */
    public BookEntry(String title,String[] authors,float rating,String ISBN,int pages) throws IllegalArgumentException, NullPointerException{
        Objects.requireNonNull(title,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(authors,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(ISBN,StdMsgs.STD_NULL_MSG.toString()); // checking for null in the parameters

        if (Arrays.asList(authors).contains(null)) {
            throw new NullPointerException(StdMsgs.STD_NULL_MSG.toString()); // making sure the list does not contain null
        }
        else if(Float.isNaN(rating) || Float.isInfinite(rating) || Float.isNaN(pages)) {
            throw new ArithmeticException(StdMsgs.OUT_OF_BOUND_MSG.toString()); // ensuring the primitives are not infinite or NaN
        }
        else if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException(StdMsgs.INVALID_RATING_MSG.toString()); // ratings check
        }
        else if (pages < MIN_PAGES) {
            throw new IllegalArgumentException(StdMsgs.INVALID_PAGES_MSG.toString()); // pages check
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
     * This is the overriding toString() function converting the book object to a string
     * @return the book object as a string
     */
    @Override
    public String toString() {
        String authorStr = Arrays.toString(authors); // string equivalent of the array
        return  title + '\n' +
                "by " + authorStr.substring(1,authorStr.length()-1) + '\n' + // taking everything apart opening and closing braces
                "Rating: " + String.format(Locale.US,"%.02f",rating) + '\n' +
                "ISBN: " + ISBN + '\n' +
                pages + " pages";
    }

    /**
     * This is the overriding equals() method to check if two books have the same fields
     * @param obj is of type Object and is comparing all instance fields with the parameter 'o'
     * @return a boolean if the two books are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) { // checking whether obj is null and of same class
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
     * This is an overriding method to give the hashCode of the class variables
     * @return the hashCode of the class variables
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages); // hashcode of other variables
        result = 31 * result + Arrays.hashCode(authors); // hashcode of the array
        return result;
    }
}
