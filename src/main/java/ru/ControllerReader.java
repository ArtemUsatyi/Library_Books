package ru;

import java.util.TreeMap;

public class ControllerReader {
    private static TreeMap<String, String> readers = new TreeMap<>();

    public void setAddReaderOfBook(String nameReader, String nameAuthor, String nameBook) {
        String nameAuthorPlusBook = nameAuthor + " - " + nameBook;
        readers.put(nameReader, nameAuthorPlusBook);
    }

    public TreeMap<String, String> getListOfReader() {
        return readers;
    }

    public Boolean getReturnBook(String nameReader, String nameBook) {
        if (readers.containsKey(nameReader) && readers.containsValue(nameBook)) {
            readers.remove(nameReader);
            return true;
        } else return false;
    }

}
