import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;
/**
 * This command is a variant of the LIST command
 * and should allow the user to display book entries in specific groups.
 */
public class GroupCmd extends LibraryCommand {
    private String groupField; //  instance field

    /**If the title is numeric i.e. does not start with alphabet*/
    private final String numericTitle = "[0-9]";

    /**
     * Constructor of group Command to call the superclass constructor
     * @param groupField is the parameter of the constructor
     */
    public GroupCmd(String groupField) {
        super(CommandType.GROUP,groupField); // calling the superclass constructor
    }

    /**
     * Overriding method to make sure the argument given is compatible
     * @param groupInput is the input which specifies how the books need to be grouped
     * @return whether the argument is valid or not.
     */
    @Override
    protected boolean parseArguments(String groupInput) {
        Objects.requireNonNull(groupInput, StdMsg.STD_NULL_MSG.toString());
        if (groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE)) {
            groupField = groupInput; // checking if the parameter with group is AUTHOR or TITLE. Nothing else
            return true;
        }
        return false;
    }

    /**
     * Overriding execute method which prints the books in a group based on corresponding user request
     * @param data is the LibraryData object which is used to access the BookEntry list
     * @throws NullPointerException if the BookEntry list contains a null reference or LibraryData object is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, StdMsg.STD_NULL_MSG.toString()); // making sure the data object is not null

        List<BookEntry> books = data.getBookData(); // using the getter
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString()); // list should not be null
        if (books.contains(null)) {
            throw new NullPointerException(StdMsg.STD_NULL_MSG.toString()); // cannot contain null
        }

        Map<String, List<String>> groupMap = new HashMap<>(); // HashMap to manipulate the data
        switch(groupField) {
            case RemoveCmd.TITLE:
                titleGroup(groupMap, books);// grouping titles
                break;

            case RemoveCmd.AUTHOR:
                authorGroup(groupMap, books);// grouping authors
                break;
        }
        // lexicographic sorting of the keys
        TreeMap<String,List<String>> sortedMap = new TreeMap<>(groupMap);
        System.out.println(mapOutput(sortedMap).trim());
    }

    /**
     * This is to output the group HashMap based on the grouping criteria specified by the user.
     * The format followed is specified in the assignment.
     * @param groupArg is the HashMap which will be printed in the execute method
     * @return the formatted HashMap as a String
     * @throws NullPointerException if any of the parameters are null
     */
    private String mapOutput(Map<String,List<String>> groupArg) {
        Objects.requireNonNull(groupArg, StdMsg.STD_NULL_MSG.toString());

        StringBuilder output = new StringBuilder(); //  Contains the output string
        StringBuilder numericOutput = new StringBuilder(); // Ensuring numeric titles appear last
        String groupPrefix = "## ";

        if (groupArg.isEmpty()) {
            output.append(StdMsg.EMPTY_LIBRARY_MSG.toString());//  the case where the HashMap has no values
        }

        else {//  printing the particular HashMap
            output.append(StdMsg.GROUP_TYPE_MSG.toString()).append(groupField).append("\n");
            for (Map.Entry<String,List<String>> arg : groupArg.entrySet()) {
                if (!arg.getKey().equals(numericTitle)){
                    output.append(groupPrefix);
                    groupAppend(output, arg.getKey(), arg.getValue());
                } // this difference makes sure that books with number titles come after all alphabets
                else {
                    numericOutput.append(groupPrefix);
                    groupAppend(numericOutput, numericTitle, arg.getValue());
                }
            }
        }
        // numeric group appears after alphabet group, based on the output in the assignment
        return output.append(numericOutput).toString();
    }

    /**
     * Appends the keys of the HashMap to a StringBuilder which is later outputted in execute()
     * @param output is the StringBuilder object to store the output
     * @param key is the the respective key of the grouped map
     * @param values all the book titles under that key
     * @throws NullPointerException if any of the function parameters are null
     */
    private void groupAppend(StringBuilder output, String key, List<String> values) {
        Objects.requireNonNull(output, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(key, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(values, StdMsg.STD_NULL_MSG.toString()); // error handling
        // No value returned as the parameters are passed by reference

        output.append(key).append("\n").append(listToString(values)); // appending based on the format in assignment
    }

    /**
     * Convert the values into a string which is later printed in the respective category
     * @param listValues is the list of strings of book titles
     * @return the book titles as a string which is formatted as per the requirement of the assignment
     * @throws NullPointerException if any of the parameters are null
     */
    private String listToString(List<String> listValues) {
        Objects.requireNonNull(listValues, StdMsg.STD_NULL_MSG.toString()); // error handling

        StringBuilder listConverter = new StringBuilder();
        for (String listValue : listValues) {
            listConverter.append("\t").append(listValue).append("\n"); // List formatting
        }
        return listConverter.toString();
    }



    /**
     * Performs the addition of keys and values to the HashMap
     * @param groupedData is the HashMap which contains the data grouped(either AUTHOR or TITLE)
     * @param titleCategory is the author/alphabet which forms the key of the HashMap
     * @param title is the book title which forms the value of the HashMap
     * @throws NullPointerException if any of the function parameters are null
     */
    private void dataAdd(Map<String,List<String>> groupedData, String titleCategory, String title) {
        Objects.requireNonNull(groupedData, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(titleCategory, StdMsg.STD_NULL_MSG.toString());
        Objects.requireNonNull(title, StdMsg.STD_NULL_MSG.toString()); // error handling

        if (!groupedData.containsKey(titleCategory)) {
            groupedData.put(titleCategory, new ArrayList<>()); // if key does not exist, create a new one
        }
        // No value returned as the parameters are passed by reference

        groupedData.get(titleCategory).add(title); // add values to key
    }

    /**
     * Grouping the titles of the book in a lexicographic way
     * @param titleMap is the HashMap to group all the titles
     * @param books is the list of books which have to be sorted
     * @throws NullPointerException if any of function parameters are null
     */
    private void titleGroup(Map<String, List<String>> titleMap, List<BookEntry> books) {
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString()); // error handling
        Objects.requireNonNull(titleMap,StdMsg.STD_NULL_MSG.toString());
        for (BookEntry book : books) {

            if (Character.isAlphabetic(book.getTitle().charAt(0))) { // checking the first letter
                String titleCategory = String.valueOf(book.getTitle().charAt(0)).toUpperCase();
                dataAdd(titleMap, titleCategory, book.getTitle());// non numeric titles
            }
            else {
                dataAdd(titleMap, numericTitle, book.getTitle());// numeric titles
            } // grouping all titles.
        }
    }

    /**
     * Grouping the authors of the books in a lexicographic manner
     * @param authorMap is the HashMap to group all the authors
     * @param books is the list of books which are grouped based on their author/s
     * @throws NullPointerException if the function parameter is null
     */
    private void authorGroup(Map<String,List<String>> authorMap, List<BookEntry> books) {
        Objects.requireNonNull(books, StdMsg.STD_NULL_MSG.toString()); // error handling
        Objects.requireNonNull(authorMap,StdMsg.STD_NULL_MSG.toString());
        for (BookEntry book : books) {
                for (String author : book.getAuthors()) {
                    dataAdd(authorMap, author, book.getTitle());
                } // grouping all the authors
        }
    }
}
