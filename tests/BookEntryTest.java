import org.junit.Before;

import java.util.Objects;

public abstract class BookEntryTest {

    protected static final float CMP_DELTA = 0.0005f;

    protected static final String TITLE_FIELD_NAME = "title";
    protected static final String AUTHORS_FIELD_NAME = "authors";
    protected static final String RATING_FIELD_NAME = "rating";
    protected static final String ISBN_FIELD_NAME = "ISBN";
    protected static final String PAGES_FIELD_NAME = "pages";

    protected static final String DEFAULT_TITLE = "Harry Potter";
    protected static final String ADVANCED_TITLE = "Odes to Common Things";

    protected static final String[] DEFAULT_AUTHORS = { "J.K. Rowling" };
    protected static final String[] ADVANCED_AUTHORS = {"J.K. Rowling", null};
    protected static final String[] ADVANCED_AUTHORS1 = {"Pablo Neruda-Ferris","Cook-Ken Krabbenhoft","Hello World"};
    protected static final String[] ADVANCED_AUTHORS2 = {"Hello World","Pablo Neruda-Ferris","Cook-Ken Krabbenhoft"};
    protected static final float DEFAULT_RATING = 4.2f;
    protected static final float ADVANCED_RATING1 = (float) (1.0f / 0.0); // Infinity
    protected static final float ADVANCED_RATING2 = (float) (0.0f/0.0); // NaN
    protected static final float ADVANCED_RATING3 = 4.38f;

    protected static final String DEFAULT_ISBN = "074754624X";
    protected static final String ADVANCED_ISBN1 = null;
    protected static final String ADVANCED_ISBN2 = "821220802";

    protected static final int DEFAULT_PAGES = 521;
    protected static final int ADVANCED_PAGES1 = 2147483647;
    protected static final int ADVANCED_PAGES2 = 152;

    protected static final String DEFAULT_TOSTRING_RESULT = "Harry Potter\nby J.K. Rowling\nRating: 4.20\nISBN: 074754624X\n521 pages";
    protected static final String ADVANCED_TOSTRING_RESULT = "Odes to Common Things\nby Pablo Neruda-Ferris, Cook-Ken Krabbenhoft, Hello World\nRating: 4.38\nISBN: 821220802\n152 pages";
    protected static final Class<String> TITLE_FIELD_TYPE = String.class;
    protected static final Class<?> AUTHORS_FIELD_TYPE = DEFAULT_AUTHORS.getClass();
    protected static final Class<Float> RATING_FIELD_TYPE = Float.TYPE;
    protected static final Class<String> ISBN_FIELD_TYPE = String.class;
    protected static final Class<Integer> PAGES_FIELD_TYPE = Integer.TYPE;

    public static final String[] BOOK_ENTRY_FIELD_NAMES = { TITLE_FIELD_NAME, AUTHORS_FIELD_NAME, RATING_FIELD_NAME,
            ISBN_FIELD_NAME, PAGES_FIELD_NAME };
    public static final Object[] BOOK_ENTRY_FIELD_VALUES = { DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING,
            DEFAULT_ISBN, DEFAULT_PAGES };
    public static final Object[] BOOK_ENTRY_FIELD_VALUES_ADVANCED= {ADVANCED_TITLE,ADVANCED_AUTHORS1,ADVANCED_RATING3,ADVANCED_ISBN2,ADVANCED_PAGES2};
    public static final Class<?>[] BOOK_ENTRY_FIELD_TYPES = { TITLE_FIELD_TYPE, AUTHORS_FIELD_TYPE, RATING_FIELD_TYPE,
            ISBN_FIELD_TYPE, PAGES_FIELD_TYPE };

    protected BookEntry testBook;

    public BookEntryTest() {
        testBook = null;
    }

    @Before
    public void setup() {
        testBook = new BookEntry(DEFAULT_TITLE, DEFAULT_AUTHORS, DEFAULT_RATING, DEFAULT_ISBN, DEFAULT_PAGES);
    }
}