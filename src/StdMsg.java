/**All types of standard output messages for the commands*/
public enum StdMsg {
    EMPTY_LIBRARY_MSG("The library has no book entries."),

    STD_NULL_MSG("Given input argument must not be null."),

    NO_SEARCH_MSG("No hits found for search term: "),

    GROUP_TYPE_MSG("Grouped data by "),

    BOOK_NUM_MSG(" books in library:\n"),

    AUTHOR_REMOVE_MSG(" books removed for author: "),

    REMOVE_SUCCESS_MSG(": removed successfully."),

    INVALID_RATING_MSG("Invalid rating"),

    INVALID_PAGES_MSG("Invalid Pages"),

    FILE_PARSING_ERROR("ERROR: Parsing book data failed: "),

    CONTENT_LOADING_ERROR("ERROR: No content loaded before parsing."),

    TITLE_NOT_FOUND_MSG(": not found.");

    private final String description; // description of the message

    /**
     * This is a parameterized constructor for the enum
     * @param description is the description of the console output
     */
    StdMsg(String description) {
        this.description = description;
    }

    /**
     * Overriding the toString method of enums
     * @return the description of the output
     */
    @Override
    public String toString() {
        return description;
    }
}
