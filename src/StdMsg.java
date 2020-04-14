/**This enums stores frequently used output messages and all the error messages.*/
public enum StdMsg {
    EMPTY_LIBRARY_MSG("The library has no book entries."), // library with no books

    STD_NULL_MSG("Given argument must not be null."), // error message for NullPointerExceptions

    INVALID_RATING_MSG("Rating not in range"), // error message for out of bound rating

    INVALID_PAGES_MSG("Invalid Pages"), // error message for invalid number of pages

    FILE_PARSING_ERROR("ERROR: Parsing book data failed: "), // parsing failure message

    CONTENT_LOADING_ERROR("ERROR: No content loaded before parsing."); // content not loaded message

    private final String description; // description of the message

    /**
     * This is a parameterized constructor for the enum
     * @param description is the description of the particular output/error message
     */
    StdMsg(String description) {
        this.description = description;
    }

    /**
     * Overriding the toString() method of enums
     * @return the description of the output/error message
     */
    @Override
    public String toString() {
        return description;
    }
}
