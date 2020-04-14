/**This enums stores frequently used output messages and all the error messages.*/
public enum StdMsg {
    EMPTY_LIBRARY_MSG("The library has no book entries."), // Empty library message

    STD_NULL_MSG("Given argument must not be null."), // NullPointerExceptions Message

    INVALID_RATING_MSG("Rating not in range"), // Rating out of bound message

    INVALID_PAGES_MSG("Invalid Pages"), // Invalid pages message

    FILE_PARSING_ERROR("ERROR: Parsing book data failed: "), // Parsing error message

    STD_ILLEGAL_MSG("Invalid argument for the command"), // IllegalArgumentException Message

    CONTENT_LOADING_ERROR("ERROR: No content loaded before parsing."); // Content not loaded message

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
