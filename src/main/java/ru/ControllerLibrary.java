package ru;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ControllerLibrary {
    private static TreeMap<String, List<String>> libraryBook = new TreeMap<>();
    private List<String> nameBookList;

    public void setNewAddAuthor(String nameAuthor, String nameBook) {
        nameBookList = new ArrayList<>();
        nameBookList.add(nameBook);
        libraryBook.put(nameAuthor, nameBookList);
    }

    public List<String> getListBookOfAuthor(String nameAuthor) {
        return nameBookList = libraryBook.get(nameAuthor);
    }

    public static TreeMap getListAllBooks() {
        return libraryBook;
    }

    public Boolean getСheckNameBookOfList(String nameAuthor, String nameBook) {
        nameBookList = libraryBook.get(nameAuthor);
        return nameBookList.contains(nameBook);
    }

    public void setRemoveNameBook(String nameAuthor, String nameBook) {
        nameBookList = libraryBook.get(nameAuthor);
        nameBookList.remove(nameBook);
    }

    public void setAddNewBook(String nameAuthor, String newBook) {
        nameBookList = libraryBook.get(nameAuthor);
        nameBookList.add(newBook);
    }

    public void setEditBook(String nameAuthor, String oldNameBook, String newNameBook) {
        nameBookList = libraryBook.get(nameAuthor);
        int i = nameBookList.indexOf(oldNameBook);
        nameBookList.set(i, newNameBook);
    }
}
