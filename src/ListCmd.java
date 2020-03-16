import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListCmd extends LibraryCommand {
    String shortArgument;

    /**String for long listing*/
    public final String LONG = "long";
    /**Strings for short listing*/
    public final String SHORT_1 = "short";
    public final String SHORT_2 = "";
    public ListCmd(String shortArgument) {
        super(CommandType.LIST,shortArgument);
        this.shortArgument = shortArgument;
    }

    @Override
    public void execute(LibraryData data) {
        // TODO should i check the parseArguments part
        Objects.requireNonNull(data,"Cannot be null");
        List<BookEntry> listCmdBooks = data.getBookData();
        Iterator<BookEntry> iterator = listCmdBooks.iterator();
        StringBuilder listOutput = new StringBuilder();
        if (listCmdBooks.size() == 0) {
            listOutput.append("The library has no book entries.");
        }
        else {
            listOutput.append(listCmdBooks.size()+" books in library:\n");
            if (shortArgument.equalsIgnoreCase(SHORT_1) || shortArgument.equalsIgnoreCase(SHORT_2)) {
                while (iterator.hasNext()) {
                    BookEntry book = iterator.next();
                    listOutput.append(book.getTitle()).append('\n');
                }
            }
            else if (shortArgument.equalsIgnoreCase(LONG)) {
                while (iterator.hasNext()){
                    BookEntry book = iterator.next();
                    listOutput.append(book.toString());
                    listOutput.append("\n\n");
                }
            }
        }

        System.out.println(listOutput);
    }
    @Override
    protected boolean parseArguments(String listArgument) {
        Objects.requireNonNull(listArgument,"Cannot be null");
        shortArgument = listArgument;
        return  listArgument.equalsIgnoreCase(LONG) ||
                listArgument.equalsIgnoreCase(SHORT_1) ||
                listArgument.equalsIgnoreCase(SHORT_2);
    }
}
