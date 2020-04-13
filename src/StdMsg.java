/**
 *
 */
public enum StdMsgs {
    /**This illustrates all the standard messages for the console output*/
    EMPTY_LIBRARY_MSG("The library has no book entries."),
    STD_NULL_MSG("Given input argument must not be null."),
    NO_SEARCH_MSG("No hits found for search term: "),
    GROUP_TYPE_MSG("Grouped data by "),
    BOOK_NUM_MSG(" books in library:\n"),
    AUTHOR_REMOVE_MSG(" books removed for author: "),
    REMOVE_SUCCESS_MSG(": removed successfully."),
    INVALID_RATING_MSG("Invalid rating"),
    INVALID_PAGES_MSG("Invalid Pages"),
    OUT_OF_BOUND_MSG("Value out of bound"),
    FILE_PARSING_ERROR("ERROR: No content loaded before parsing."),
    CONTENT_LOADING_ERROR("ERROR: No content loaded before parsing."),
    TITLE_NOT_FOUND_MSG(": not found.");
    private final String description; // description of the message

    /**
     * This is a parameterized constructor for the string as a std_msg
     * @param description is the std_msg
     */
    StdMsgs(String description) {
        this.description = description;
    }

    /**
     * Getter for the description of the message
     * @return message description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Overriding the toString method of enums
     * @return the description as a string
     */
    @Override
    public String toString() {
        return description;
    }
}
