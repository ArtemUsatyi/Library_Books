package ru;

public class Main {
    public static void main(String[] args) {
        GUILibraryWindows GUILibrary = new GUILibraryWindows(ControllerLibrary.getListAllBooks());
        GUILibrary.libraryWindows();
    }
}