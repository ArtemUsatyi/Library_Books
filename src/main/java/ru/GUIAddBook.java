package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIAddBook {
    private JFrame frame;
    private JPanel panel;
    private JPanel panelTop;
    private JPanel panelDown;
    private JButton buttonAddBook;
    private JButton buttonBack;
    private JLabel labelAuthor;
    private JLabel labelNameBook;
    private JTextField textAuthor;
    private JTextField textNameBook;
    private JLabel marginTop;

    public void addBookList() {
        ControllerLibrary controllerLibrary = new ControllerLibrary();

        frame = new JFrame("Добавление новой книги");
        panel = new JPanel();
        panelTop = new JPanel();
        panelDown = new JPanel();
        labelAuthor = new JLabel("Введите название автора: ");
        labelNameBook = new JLabel("Введите название книги:   ");
        textAuthor = new JTextField(25);
        textNameBook = new JTextField(25);

        buttonAddBook = new JButton("Сохранить книгу");
        buttonAddBook.setFocusPainted(false);

        buttonAddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
                textAuthor.setText("");
                textNameBook.setText("");
            }
        });

        buttonBack = new JButton("Назад");
        buttonBack.setFocusPainted(false);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUILibraryWindows libraryWindows = new GUILibraryWindows();
                libraryWindows.libraryWindows();
            }
        });

        marginTop = new JLabel("ДОБАВЛЕНИЕ НОВОЙ КНИГИ В БИБИЛИОТЕКУ");
        panelTop.add(marginTop);
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel.add(labelAuthor);
        panel.add(textAuthor);
        panel.add(labelNameBook);
        panel.add(textNameBook);
        panelDown.add(buttonBack);
        panelDown.add(buttonAddBook);
        panelDown.setLayout(new FlowLayout(FlowLayout.CENTER));

        frame.getContentPane().add(BorderLayout.NORTH, panelTop);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, panelDown);

        frame.setSize(470, 170);                    // Размер окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Закрытие формы "на крестик"
        frame.setResizable(false);                              // Редактировать форму запрещено
        frame.setLocationRelativeTo(null);                      // Позиция окна в центре экрана
        frame.setVisible(true);                                 // Отображение формы
    }

    private void addBook() {
        if (textAuthor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Поле ввода 'Название Автора' не должно быть пустым", "Внимание!", JOptionPane.WARNING_MESSAGE);
        } else if (ControllerLibrary.getListAllBooks().containsKey(textAuthor.getText())) {
            JOptionPane.showMessageDialog(frame, "Такой автор уже существует." + "\n" + "Перейдите в раздел 'Редактирование' и добавьте новую книгу к автору", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
        } else if (textNameBook.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Поле ввода 'Название Книги' не должно быть пустым", "Внимание!", JOptionPane.WARNING_MESSAGE);
        } else {
            ControllerLibrary controllerLibrary = new ControllerLibrary();
            controllerLibrary.setNewAddAuthor(textAuthor.getText(), textNameBook.getText());
            JOptionPane.showMessageDialog(frame, "Автор и книга успешно добавлены в библиотеку", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
