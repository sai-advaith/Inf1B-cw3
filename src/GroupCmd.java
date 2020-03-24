import com.sun.source.tree.Tree;

import java.awt.print.Book;
import java.util.*;

public class GroupCmd extends LibraryCommand {
    String groupArgument; //  argument to store the user input

    /**
     * Constructor of group Command to instantiate the parameters
     * @param groupArgument is the parameter of the constructor
     */
    public GroupCmd(String groupArgument) {
        super(CommandType.GROUP,groupArgument);
        this.groupArgument = groupArgument;
    }
//TODO: Fix indentation
    @Override
    /**
     * This is the overriding execute method which prints the books in a group based on user request
     * @param data is the LibraryData object which is used to access the book data
     */
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Map<String, List<String>> groupMap = new HashMap<String, List<String>>(); // hashmap to manipulate the data
        if(groupArgument.equals(RemoveCmd.TITLE)) {
            groupMap = titleGroup(books);
            TreeMap<String,List<String>> sortedMap = new TreeMap<>(groupMap); //  using a tree map to sort by keys
            System.out.println(mapOutput(sortedMap).trim()); // grouping and outputting
        }
        else if(groupArgument.equals(RemoveCmd.AUTHOR)) {
            groupMap = authorGroup(books);
            Map<String,List<String>> sortedMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            sortedMap = groupMap;
            System.out.println(mapOutput(sortedMap).trim()); // trimming to get rid off the new lines at the end of the ouput
        }
    }

    /**
     * This is to output the group HashMap based on the criteria laid down by the user
     * @param groupArg is the hashmap which will be printed in the execute method
     * @return gives the hashmap output as a string and is later printed
     */
    public String mapOutput(Map<String,List<String>> groupArg) {
        StringBuilder output = new StringBuilder(); //  stringbuilder which will contain the output string
        if (groupArg.size() == 0) {
            output.append("The library has no book entries.");//  the case where the hashmap has no values
       }
       else {//  printing the particular hashmap
            output.append("Grouped data by ").append(groupArgument).append("\n");
            for (Map.Entry<String,List<String>> arg : groupArg.entrySet()) {
               output.append("## ").append(arg.getKey()).append("\n");
               output.append(listToString(arg.getValue()));
           }
       }
       return output.toString();
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
     * @return the hashmap with all the books sorted in that fashion
     */
    public Map<String,List<String>> titleGroup(List<BookEntry> books) {
        String nonNumericTitle = "[0-9]";
        Map<String, List<String>> titleMap = new HashMap<String, List<String>>();
        for (BookEntry book : books) {
            if (Character.isAlphabetic(book.getTitle().charAt(0))) { // checking the first letter
                String titleCategory = String.valueOf(book.getTitle().charAt(0)).toUpperCase();
                if (!titleMap.containsKey(titleCategory)) {
                    titleMap.put(titleCategory, new ArrayList<>());
                }
                titleMap.get(titleCategory).add(book.getTitle());
            }
            // adding based on whether the key's existence
            else {
                if (!titleMap.containsKey(nonNumericTitle)) {
                    titleMap.put(nonNumericTitle,new ArrayList<>());
                }
                titleMap.get(nonNumericTitle).add(book.getTitle());
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
        Map<String,List<String>> authorMap = new HashMap<String, List<String>>();
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
        groupArgument = groupInput;
        Objects.requireNonNull(groupInput);
        return groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE); // checking if author or title
    }
}
