package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GUILibraryWindows implements ActionListener {
    private JButton[] buttonArr = new JButton[10];
    private JPanel panel = new JPanel();
    private JPanel panelDown= new JPanel();
    private JPanel panelTop = new JPanel();
    private JFrame frame;
    private JButton buttonEdit;
    private JButton buttonAdd;
    private JButton buttonTake;
    private JButton buttonReturnBook;
    private JScrollPane scrollerListBook;
    private JTextField textEdit;
    private JLabel labelAuthor;

    public GUILibraryWindows() {
        this(ControllerLibrary.getListAllBooks());
    }

    public GUILibraryWindows(TreeMap<String, List<String>> listLibraryBook) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        int i = 0;
        for (String authorBook : listLibraryBook.keySet()) {
            buttonArr[i] = new JButton(authorBook);
            buttonArr[i].setSize(20, 20);
            buttonArr[i].setFocusPainted(false);                              // убрать рамку вокруг текста
            buttonArr[i].setContentAreaFilled(false);                         // убрать цвет и заливку кнопки в нажатом состоянии
            buttonArr[i].setBorderPainted(false);                             // убрать контур кнопки и кликабельность

            AtomicInteger colorR = new AtomicInteger(0);
            AtomicInteger colorG = new AtomicInteger(0);
            AtomicInteger colorB = new AtomicInteger(0);

            buttonArr[i].setForeground(new Color(colorR.get(), colorG.get(), colorB.get()));
            int finalI = i;
            buttonArr[i].getModel().addChangeListener(evt -> {
                ButtonModel model = (ButtonModel) evt.getSource();
                Font btnFont = buttonArr[finalI].getFont();
                Map attributes = btnFont.getAttributes();

                if (model.isRollover()) {
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    colorR.set(0);
                    colorG.set(102);
                    colorB.set(204);
                } else {
                    attributes.put(TextAttribute.UNDERLINE, null);
                    colorR.set(0);
                    colorG.set(0);
                    colorB.set(0);
                }
                buttonArr[finalI].setForeground(new Color(colorR.get(), colorG.get(), colorB.get()));
                btnFont = btnFont.deriveFont(attributes);
                buttonArr[finalI].setFont(btnFont);
            });
            panel.add(buttonArr[i]);
            i++;
        }

        for (int j = 0; j < i; j++) {
            buttonArr[j].addActionListener(this);
        }
    }

    public void libraryWindows() {
        frame = new JFrame("Библиотека книг");      // Объявление формы и наименование окна

        textEdit = new JTextField( 15);
        labelAuthor = new JLabel("Выберите автора или введите автора: ");

        buttonEdit = new JButton("Редактировать");
        buttonEdit.setFocusPainted(false);
        buttonEdit.setEnabled(false);

        buttonAdd = new JButton("Добавить");
        buttonAdd.setFocusPainted(false);

        buttonTake = new JButton("Отдать читателю");
        buttonTake.setFocusPainted(false);
        buttonTake.setEnabled(false);

        buttonReturnBook = new JButton("Вернуть книгу");
        buttonReturnBook.setFocusPainted(false);

        buttonTake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textEdit.getText().isEmpty()) {
                    buttonEdit.setEnabled(false);
                    buttonTake.setEnabled(false);
                    return;
                }
                frame.dispose();
                GUIGiveBook give = new GUIGiveBook(textEdit.getText());
                give.giveBook();
            }
        });

        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textEdit.getText().isEmpty()) {
                    buttonEdit.setEnabled(false);
                    buttonTake.setEnabled(false);
                    return;
                }
                frame.dispose();
                GUIEditBook edit = new GUIEditBook(textEdit.getText());
                edit.editBook();
            }
        });
        buttonReturnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUIReturnBookFromReader returnBook = new GUIReturnBookFromReader();
                returnBook.returnBook();
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUIAddBook addBook = new GUIAddBook();
                addBook.addBookList();
            }
        });

        panelTop.add(labelAuthor);
        panelTop.add(textEdit);
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelDown.add(buttonEdit);
        panelDown.add(buttonTake);
        panelDown.add(buttonReturnBook);
        panelDown.add(buttonAdd);
        panelDown.setLayout(new FlowLayout(FlowLayout.CENTER));

        scrollerListBook = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(BorderLayout.CENTER, scrollerListBook);
        frame.getContentPane().add(BorderLayout.NORTH, panelTop);
        frame.getContentPane().add(BorderLayout.SOUTH, panelDown);
        frame.setSize(550, 400);                     // Размер окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Закрытие формы "на крестик"
        frame.setResizable(true);                              // Редактировать форму запрещено
        frame.setLocationRelativeTo(null);                      // Позиция окна в центре экрана
        frame.setVisible(true);                                 // Отображение формы
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEdit.setText(e.getActionCommand());
        buttonEdit.setEnabled(true);
        buttonTake.setEnabled(true);
    }
}
