import java.util.Objects;

public class RemoveCmd extends LibraryCommand {
    String removeArgument;
    /**Constant string which indicates author removal*/
    public final String AUTHOR_REMOVE = "AUTHOR";
    /**Constant string which indicates title removal*/
    public final String TITLE_REMOVE = "TITLE";
    public RemoveCmd(String removeArgument) {
        super(CommandType.REMOVE,removeArgument);
        this.removeArgument = removeArgument;
    }

    @Override
    public void execute(LibraryData data) {

    }

    /**
     * Used to check if the string argument to be removed is valid. Two aspects are checked,
     * One if the argument is AUTHOR or TITLE, and the other if whatever being removed is not an empty string
     * @param bookRemoval is the argument which contains the string where we are told about what to be removed
     * @return true if the argument being returned is valid and can hence be excuted by us
     */
    @Override
    protected boolean parseArguments(String bookRemoval) {
        Objects.requireNonNull(bookRemoval,"String cannot be null");
        removeArgument = bookRemoval;
        int firstSpace = bookRemoval.indexOf(' '); //  Checking the first occurrence of whitespace
        if (firstSpace != -1){
            String removeArg = splitRemoval(firstSpace,bookRemoval)[0];
            String removeBook = splitRemoval(firstSpace,bookRemoval)[1];
            return (removeArg.equals(AUTHOR_REMOVE) || removeArg.equals(TITLE_REMOVE)) &&
                    !(removeBook.equals("") || removeBook == null);
        }
        else {
            return false;
        }
    }

    /**
     * Using to split the AUTHOR or TITLE part of the removal from the author name or title
     * @param spacePosition is the first position of the space in the string
     * @param bookRemoval from which we parse the arguments
     * @return a string array containing the removal type (i.e. AUTHOR or TITLE) and the author name
     */
    public String[] splitRemoval(int spacePosition, String bookRemoval) {
        return new String[]{bookRemoval.substring(0, spacePosition), bookRemoval.substring(spacePosition + 1)};
    }
}
