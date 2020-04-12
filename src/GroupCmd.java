import com.sun.source.tree.Tree;

import java.awt.print.Book;
import java.util.*;

public class GroupCmd extends LibraryCommand {
    private String groupField; //  argument to store the user input
    /**If the title is numeric i.e. does not start with alphabet*/
    private final String numericTitle = "[0-9]";

    /**
     * Constructor of group Command to instantiate the parameters
     * @param groupField is the parameter of the constructor
     */
    public GroupCmd(String groupField) {
        super(CommandType.GROUP,groupField); // calling the baseclass constructor
    }
    /**
     * Overriding method to make sure the arguments being given are as per the requirement of the user
     * @param groupInput is the input which specifies how the books need to be grouped as per the requirement of the user
     * @return a boolean whether the argument is valid or not.
     */
    @Override
    protected boolean parseArguments(String groupInput) {
        Objects.requireNonNull(groupInput,StdMsgs.STD_NULL_MSG.toString());
        if (groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE)) {
            groupField = groupInput; // checking if the parameter with group is AUTHOR or TITLE. Nothing else
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This is the overriding execute method which prints the books in a group based on user request
     * @param data is the LibraryData object which is used to access the book data
     * @throws NullPointerException if the BookEntry list contains a null reference
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,StdMsgs.STD_NULL_MSG.toString()); // making sure the data object is not null

        List<BookEntry> books = data.getBookData();
        Objects.requireNonNull(books,StdMsgs.STD_NULL_MSG.toString()); // list should not be null and should not contain null
        if (books.contains(null)) {
            throw new NullPointerException(StdMsgs.STD_NULL_MSG.toString());
        }
        Map<String, List<String>> groupMap; // hashMap to manipulate the data
        TreeMap<String,List<String>> sortedMap = new TreeMap<>(); // case insensitive sorting of the keys
        switch(groupField) {
            case RemoveCmd.TITLE:
                groupMap = titleGroup(books); // grouping titles
                sortedMap.putAll(groupMap); //  putting all the grouped books in the treeMap
                System.out.println(mapOutput(sortedMap).trim()); // grouping and outputting
                break;

            case RemoveCmd.AUTHOR:
                groupMap = authorGroup(books); // grouping authors
                sortedMap.putAll(groupMap);//  putting all the grouped books in the treeMap
                System.out.println(mapOutput(sortedMap).trim()); // grouping and outputting
                break;
        }
    }

    /**
     * This is to output the group HashMap based on the criteria laid down by the user
     * @param groupArg is the HashMap which will be printed in the execute method
     * @return gives the HashMap output as a string and is later printed
     * @throws NullPointerException if any of the parameters are null
     */
    public String mapOutput(Map<String,List<String>> groupArg) {
        Objects.requireNonNull(groupArg,StdMsgs.STD_NULL_MSG.toString());
        StringBuilder output = new StringBuilder(); //  StringBuilder which will contain the output string
        StringBuilder numericOutput = new StringBuilder(); // Solving the issue with numbers appearing after the list
        // in author this does not matter, for title it does
        String groupPrefix = "## ";
        if (groupArg.size() == 0) {
            output.append(StdMsgs.EMPTY_LIBRARY_MSG.toString());//  the case where the HashMap has no values
       }
        else {//  printing the particular HashMap
            output.append(StdMsgs.GROUP_TYPE_MSG.toString()).append(groupField).append("\n");
            for (Map.Entry<String,List<String>> arg : groupArg.entrySet()) {
                if (!arg.getKey().equals(numericTitle)){
                    output.append(groupPrefix);
                    output = groupAppend(output,arg.getKey(),arg.getValue());
                } // this difference makes sure that books with number titles come after all alphabets
                else {
                    numericOutput.append(groupPrefix);
                    numericOutput = groupAppend(numericOutput,numericTitle,arg.getValue());
                }
           }
       }
        return output.append(numericOutput).toString(); // appending numeric after alphabets based on output in papers
    }

    /**
     * Performs addition of data to a StringBuilder which is used for the output
     * @param output is the StringBuilder containing the output
     * @param key is the the respective key of the grouped map
     * @param values all the book titles under that category
     * @return the StringBuilder output which is later updated
     * @throws NullPointerException if any of the function parameters are null
     */
    public StringBuilder groupAppend(StringBuilder output, String key,List<String> values) {
        Objects.requireNonNull(output,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(key,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(values,StdMsgs.STD_NULL_MSG.toString()); // error handling

        output.append(key).append("\n").append(listToString(values)); // appending based on the format
        return output;
    }
    /**
     * This method is to convert the list values into string which is later printed
     * @param listValues is the list of strings of book titles
     * @return the book titles as a string and formatted as per the requirement of the code
     * @throws NullPointerException if any of the parameters are null
     */
    public String listToString(List<String> listValues) {
        Objects.requireNonNull(listValues,StdMsgs.STD_NULL_MSG.toString()); // error handling

        StringBuilder listConverter = new StringBuilder();
        for (String listValue : listValues) {
            listConverter.append("\t").append(listValue).append("\n"); // converting the list to a string based on the format
        }
        return listConverter.toString();
    }

    /**
     * Grouping the titles of the book in a lexicographic way
     * @param books is the list of books which have to be sorted
     * @return the HashMap with all the books sorted in that fashion
     * @throws NullPointerException if any of function parameters are null
     */
    public Map<String,List<String>> titleGroup(List<BookEntry> books) {
        Objects.requireNonNull(books,StdMsgs.STD_NULL_MSG.toString()); // error handling

        Map<String, List<String>> titleMap = new HashMap<>(); // Map for all titles
        for (BookEntry book : books) {

                if (Character.isAlphabetic(book.getTitle().charAt(0))) { // checking the first letter
                    String titleCategory = String.valueOf(book.getTitle().charAt(0)).toUpperCase();
                    titleMap = dataAdd(titleMap,titleCategory,book.getTitle()); // non numeric titles
                }
                else {
                    titleMap = dataAdd(titleMap,numericTitle,book.getTitle()); // numeric titles
                } // grouping all titles.
        }
        return titleMap;
    }

    /**
     * Performs the addition of keys and values to the HashMap
     * @param groupedData is the HashMap which contains the data grouped(either AUTHOR or TITLE)
     * @param titleCategory is the author/alphabet which forms the key of the HashMap
     * @param title is the book title which forms the value of the HashMap
     * @return the updated HashMap
     * @throws NullPointerException if any of the function parameters are null
     */
    public Map<String,List<String>> dataAdd(Map<String,List<String>> groupedData, String titleCategory, String title) {
        Objects.requireNonNull(groupedData,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(titleCategory,StdMsgs.STD_NULL_MSG.toString());
        Objects.requireNonNull(title,StdMsgs.STD_NULL_MSG.toString()); // error handling

        if (!groupedData.containsKey(titleCategory)) {
            groupedData.put(titleCategory, new ArrayList<>()); // if key does not exist, create a new one
        }
        groupedData.get(titleCategory).add(title); // add values to key
        return groupedData;
    }
    /**
     * This is the method which groups the array based on the authors
     * @param books is the list of books which are grouped based on the author
     * @return the hashMap which contains the books grouped based on the authors
     * @throws NullPointerException if the function parameter is null
     */
    public Map<String,List<String>> authorGroup(List<BookEntry> books) {
        Objects.requireNonNull(books,StdMsgs.STD_NULL_MSG.toString()); // error handling
        Map<String,List<String>> authorMap = new HashMap<>(); // Map for all authors
        for (BookEntry book : books) {
                for (String author : book.getAuthors()) {
                    authorMap = dataAdd(authorMap,author,book.getTitle());
                } // grouping all the authors
        }
        return authorMap;
    }


}
