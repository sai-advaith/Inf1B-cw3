import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupCmd extends LibraryCommand {
    String groupArgument;
    public GroupCmd(String groupArgument) {
        super(CommandType.GROUP,groupArgument);
        this.groupArgument = groupArgument;
    }

    @Override
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Map<String, List<String>> groupMap = new HashMap<String, List<String>>();
        if(groupArgument.equals(RemoveCmd.TITLE)) {
            for (BookEntry book : books) {
                if (Character.isAlphabetic(book.getTitle().charAt(0))) {
                    
                }
            }
        }
    }
    @Override
    protected boolean parseArguments(String groupInput) {
        groupArgument = groupInput;
        Objects.requireNonNull(groupInput);
        return groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE);
    }
}
