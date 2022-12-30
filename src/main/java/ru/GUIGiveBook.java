package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIGiveBook implements ActionListener {
    private JButton[] buttonArr = new JButton[10];
    private String nameAuthor;
    private JPanel panel = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JPanel panelDown = new JPanel();
    private JButton buttonGiveBook;
    private JButton buttonBack;
    private JTextField textGiveBook;
    private JTextField textNameReader;
    private JLabel labelInfo;
    private JLabel labelGiveBook;
    private JLabel labelNameReader;
    private JFrame frame;
    private ControllerLibrary controllerLibrary = new ControllerLibrary();
    private ControllerReader controllerReader = new ControllerReader();
    private List<String> listBooksOfAuthor;

    public GUIGiveBook(String nameAuthor) {
        this.nameAuthor = nameAuthor;
        listBooks();
    }

    public void giveBook() {
        frame = new JFrame("Отдать книгу читателю");
        labelInfo = new JLabel("ОТДАТЬ КНИГУ ЧИТАТЕЛЮ:");
        labelGiveBook = new JLabel("Название книги");
        labelNameReader = new JLabel("Имя читателя");
        textNameReader = new JTextField(15);
        textGiveBook = new JTextField(15);
        buttonGiveBook = new JButton("Отдать");
        buttonGiveBook.setFocusPainted(false);
        buttonBack = new JButton("Назад");
        buttonBack.setFocusPainted(false);

        JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        panelLeft.add(labelInfo);
        panelLeft.add(labelGiveBook);
        panelLeft.add(textGiveBook);
        panelLeft.add(labelNameReader);
        panelLeft.add(textNameReader);
        panelLeft.add(buttonGiveBook);
        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelLeft.setPreferredSize(new Dimension(180, 300));

        panelDown.add(buttonBack);
        panelDown.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUILibraryWindows libraryWindows = new GUILibraryWindows();
                libraryWindows.libraryWindows();
            }
        });
        buttonGiveBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textGiveBook.getText().isEmpty() || textNameReader.getText().isEmpty()) return;
                controllerReader.setAddReaderOfBook(textNameReader.getText(), nameAuthor, textGiveBook.getText());
                controllerLibrary.setRemoveNameBook(nameAuthor, textGiveBook.getText());
                listBooks();
                textNameReader.setText("");
                textGiveBook.setText("");
            }
        });

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        frame.getContentPane().add(BorderLayout.EAST, panelLeft);
        frame.getContentPane().add(BorderLayout.SOUTH, panelDown);
        frame.setSize(550, 400);                    // Размер окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Закрытие формы "на крестик"
        frame.setResizable(false);                              // Редактировать форму запрещено
        frame.setLocationRelativeTo(null);                      // Позиция окна в центре экрана
        frame.setVisible(true);                                 // Отображение формы
    }

    private void listBooks() {
        panel.revalidate();
        panel.repaint();
        panel.removeAll();

        buttonArr[0] = new JButton("СПИСОК КНИГ АВТОРА : " + nameAuthor);
        buttonArr[0].setFocusPainted(false);                              // убрать рамку вокруг текста
        buttonArr[0].setContentAreaFilled(false);                         // убрать цвет и заливку кнопки в нажатом состоянии
        buttonArr[0].setBorderPainted(false);                             // убрать контур кнопки и кликабельность
        panel.add(buttonArr[0]);
        listBooksOfAuthor = controllerLibrary.getListBookOfAuthor(nameAuthor);
        Collections.sort(listBooksOfAuthor);                              // Сортировка книг по алфавиту -> АБВГ... абвг

        int i = 1;
        for (String nameBookSort : listBooksOfAuthor) {
            buttonArr[i] = new JButton(nameBookSort);
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
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            i++;
        }
        for (int j = 1; j < i; j++) {
            buttonArr[j].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textGiveBook.setText(e.getActionCommand());
    }
}
