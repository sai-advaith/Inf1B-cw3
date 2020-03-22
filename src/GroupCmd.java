import com.sun.source.tree.Tree;

import java.awt.print.Book;
import java.util.*;

public class GroupCmd extends LibraryCommand {
    String groupArgument;
    public GroupCmd(String groupArgument) {
        super(CommandType.GROUP,groupArgument);
        this.groupArgument = groupArgument;
    }
//TODO: Fix indentation
    @Override
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        Map<String, List<String>> groupMap = new HashMap<String, List<String>>(); // hashmap to manipulate the data
        if(groupArgument.equals(RemoveCmd.TITLE)) {
            groupMap = titleGroup(books);
            TreeMap<String,List<String>> sortedMap = new TreeMap<>(groupMap); //  using a tree map to sort by keys
            System.out.println(mapOutput(sortedMap)); // grouping and outputting
        }
        else if(groupArgument.equals(RemoveCmd.AUTHOR)) {
            groupMap = authorGroup(books);
            TreeMap<String,List<String>> sortedMap = new TreeMap<>(groupMap);
            System.out.println(mapOutput(sortedMap));
        }
    }
    public String mapOutput(Map<String,List<String>> groupArg) {
        StringBuilder output = new StringBuilder();
        if (groupArg.size() == 0) {
            output.append("The library has no book entries.");
       }
       else {
            output.append("Grouped data by ").append(groupArgument).append("\n");
            for (Map.Entry<String,List<String>> arg : groupArg.entrySet()) {
               output.append("## ").append(arg.getKey()).append("\n");
               output.append(listToString(arg.getValue()));
           }
       }
       return output.toString();
    }
    public String listToString(List<String> listValues) {
        StringBuilder listConverter = new StringBuilder();
        for (String listValue : listValues) {
            listConverter.append("\t").append(listValue).append("\n");
        }
        return listConverter.toString();
    }
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
            else {
                if (!titleMap.containsKey(nonNumericTitle)) {
                    titleMap.put(nonNumericTitle,new ArrayList<>());
                }
                titleMap.get(nonNumericTitle).add(book.getTitle());
            }
        }
        return titleMap;
    }
    public Map<String,List<String>> authorGroup(List<BookEntry> books) {
        Map<String,List<String>> authorMap = new HashMap<String, List<String>>();
        for (BookEntry book : books) {
            for (String author : book.getAuthors()) {
                if (!authorMap.containsKey(author)) {
                    authorMap.put(author, new ArrayList<>());
                }
                authorMap.get(author).add(book.getTitle());
            }
        }
        return authorMap;
    }
    @Override
    protected boolean parseArguments(String groupInput) {
        groupArgument = groupInput;
        Objects.requireNonNull(groupInput);
        return groupInput.equals(RemoveCmd.AUTHOR) || groupInput.equals(RemoveCmd.TITLE);
    }
}
