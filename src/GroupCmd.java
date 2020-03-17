import java.util.Objects;

public class GroupCmd extends LibraryCommand {
    String groupArgument;
    public GroupCmd(String groupArgument) {
        super(CommandType.GROUP,groupArgument);
        this.groupArgument = groupArgument;
    }

    @Override
    public void execute(LibraryData data) {

    }
    @Override
    protected boolean parseArguments(String groupInput) {
        groupArgument = groupInput;
        return groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE);
    }
}
