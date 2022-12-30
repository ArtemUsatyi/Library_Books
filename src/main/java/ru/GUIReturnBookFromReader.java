package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIReturnBookFromReader implements ActionListener {
    private JButton[] buttonArr = new JButton[10];
    private JFrame frame;
    private JPanel panel = new JPanel();
    private JPanel panelDown = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JButton buttonBack;
    private JButton buttonReturnBook;
    private JLabel labelNameReader;
    private JLabel labelNameBook;
    private JTextField textNameReader;
    private JTextField textNameBook;
    private ControllerReader controllerReader = new ControllerReader();
    private ControllerLibrary controllerLibrary = new ControllerLibrary();

    public GUIReturnBookFromReader() {
        listBooks();
    }

    public void returnBook() {
        frame = new JFrame("Вернуть книгу в бибилиотеку");
        buttonReturnBook = new JButton("Вернуть книгу");
        buttonReturnBook.setFocusPainted(false);
        buttonBack = new JButton("Назад");
        buttonBack.setFocusPainted(false);
        labelNameReader = new JLabel("Имя читателя:");
        textNameReader = new JTextField(17);
        labelNameBook = new JLabel("Название книги:");
        textNameBook = new JTextField(17);

        JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        panelLeft.add(labelNameReader);
        panelLeft.add(textNameReader);
        panelLeft.add(labelNameBook);
        panelLeft.add(textNameBook);
        panelLeft.add(buttonReturnBook);
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
        buttonReturnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textNameReader.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Имя читателя не введено", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (textNameBook.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Название книги не введено", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (controllerReader.getReturnBook(textNameReader.getText(), textNameBook.getText())) {
                    String[] nameBookAndAuthor = textNameBook.getText().split(" - ");
                    controllerLibrary.setAddNewBook(nameBookAndAuthor[0].trim(), nameBookAndAuthor[1].trim());

                    JOptionPane.showMessageDialog(frame, "Читатель вернул книгу в библиотеку", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
                    listBooks();
                    textNameBook.setText("");
                    textNameReader.setText("");
                } else
                    JOptionPane.showMessageDialog(frame, "Не получилось записать книгу в библиотеку.", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
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

        buttonArr[0] = new JButton("СПИСОК ЧИТАТЕЛЕЙ:");
        buttonArr[0].setFocusPainted(false);                              // убрать рамку вокруг текста
        buttonArr[0].setContentAreaFilled(false);                         // убрать цвет и заливку кнопки в нажатом состоянии
        buttonArr[0].setBorderPainted(false);                             // убрать контур кнопки и кликабельность
        panel.add(buttonArr[0]);

        int i = 1;
        for (Map.Entry<String, String> nameReader : controllerReader.getListOfReader().entrySet()) {
            buttonArr[i] = new JButton(nameReader.getKey() + " : " + nameReader.getValue());
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
        String[] nameReader = e.getActionCommand().split(":");
        textNameReader.setText(nameReader[0].trim());
        textNameBook.setText(nameReader[1].trim());
    }
}
