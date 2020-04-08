import com.sun.source.tree.Tree;

import java.awt.print.Book;
import java.util.*;

public class GroupCmd extends LibraryCommand {
    private String groupField; //  argument to store the user input
    String numericTitle = "[0-9]";
    /**
     * Constructor of group Command to instantiate the parameters
     * @param groupField is the parameter of the constructor
     */
    public GroupCmd(String groupField) {
        super(CommandType.GROUP,groupField);
        Objects.requireNonNull(groupField);
    }
    /**
     * This is the overriding execute method which prints the books in a group based on user request
     * @param data is the LibraryData object which is used to access the book data
     */
    @Override
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Map<String, List<String>> groupMap; // hashMap to manipulate the data
        TreeMap<String,List<String>> sortedMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); // case insensitive sorting of the keys
        switch(groupField) {
            case RemoveCmd.TITLE:
                groupMap = titleGroup(books);
                sortedMap.putAll(groupMap); //  putting all the grouped books in the treeMap
                System.out.println(mapOutput(sortedMap).trim()); // grouping and outputting
                break;
            case RemoveCmd.AUTHOR:
                groupMap = authorGroup(books);
                sortedMap.putAll(groupMap);//  putting all the grouped books in the treeMap
                System.out.println(mapOutput(sortedMap).trim()); // grouping and outputting
                break;
        }
    }

    /**
     * This is to output the group HashMap based on the criteria laid down by the user
     * @param groupArg is the HashMap which will be printed in the execute method
     * @return gives the HashMap output as a string and is later printed
     */
    public String mapOutput(Map<String,List<String>> groupArg) {
        StringBuilder output = new StringBuilder(); //  StringBuilder which will contain the output string
        StringBuilder out2 = new StringBuilder();
        if (groupArg.size() == 0) {
            output.append("The library has no book entries.");//  the case where the HashMap has no values
       }
        else {//  printing the particular HashMap
            output.append("Grouped data by ").append(groupField).append("\n");
            for (Map.Entry<String,List<String>> arg : groupArg.entrySet()) {
                if (!arg.getKey().equals(numericTitle)){
                    output.append("## ").append(arg.getKey()).append("\n");
                    output.append(listToString(arg.getValue()));
                }
                else {
                    out2.append("## ").append(arg.getKey()).append("\n");
                    out2.append(listToString(arg.getValue()));
                }
           }
       }
        return output.append(out2).toString();
    }

    /**
     * This method is to convert the list values into string which is later printed
     * @param listValues is the list of strings of book titles
     * @return the book titles as a string and formatted as per the requirement of the code
     */
    public String listToString(List<String> listValues) {
        StringBuilder listConverter = new StringBuilder();
        for (String listValue : listValues) {
            listConverter.append("\t").append(listValue).append("\n");
        }
        return listConverter.toString();
    }

    /**
     * Grouping the titles of the book in a lexicographic way
     * @param books is the list of books which have to be sorted
     * @return the HashMap with all the books sorted in that fashion
     */
    public Map<String,List<String>> titleGroup(List<BookEntry> books) {
        Map<String, List<String>> titleMap = new HashMap<String, List<String>>();
        for (BookEntry book : books) {
            if (Character.isAlphabetic(book.getTitle().charAt(0))) { // checking the first letter
                String titleCategory = String.valueOf(book.getTitle().charAt(0)).toUpperCase();
                if (!titleMap.containsKey(titleCategory)) {
                    titleMap.put(titleCategory, new ArrayList<>());
                }
                titleMap.get(titleCategory).add(book.getTitle());
            }
            else {
                if (!titleMap.containsKey(numericTitle)) {
                    titleMap.put(numericTitle,new ArrayList<>()); // adding based on whether the key's existence
                }
                titleMap.get(numericTitle).add(book.getTitle());
            }
        }
        return titleMap;
    }

    /**
     * This is the method which groups the array based on the authors
     * @param books is the list of books which are grouped based on the author
     * @return the hashMap which contains the books grouped based on the authors
     */
    public Map<String,List<String>> authorGroup(List<BookEntry> books) {
        Map<String,List<String>> authorMap = new HashMap<>();
        for (BookEntry book : books) {
            for (String author : book.getAuthors()) {//placing the books based on the existence of the authors in the map
                if (!authorMap.containsKey(author)) {
                    authorMap.put(author, new ArrayList<>());
                }
                authorMap.get(author).add(book.getTitle());
            }
        }
        return authorMap;
    }

    /**
     * Overriding method to make sure the arguments being given are as per the requirement of the user
     * @param groupInput is the input which specifies how the books need to be grouped as per the requirement of the user
     * @return a boolean whether the argument is valid or not.
     */
    @Override
    protected boolean parseArguments(String groupInput) {
        Objects.requireNonNull(groupInput);
        if (groupInput.trim().equals(RemoveCmd.AUTHOR) || groupInput.trim().equals(RemoveCmd.TITLE)) {
            groupField = groupInput.trim();
            return true;
        }
        else {
            return false;
        }
    }
}
