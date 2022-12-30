package ru;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIEditBook extends Component implements ActionListener {
    private JButton[] buttonArr = new JButton[10];
    private JPanel panel = new JPanel();
    private JPanel panelDown = new JPanel();
    private JPanel panelTop = new JPanel();
    private JPanel panelLeft = new JPanel();
    private JTextField textFieldNameBook = new JTextField(15);
    private JLabel labelNameBook = new JLabel("РЕД и СОХР новой книги");
    private JFrame frame;
    private JButton buttonSave;
    private JButton buttonBack;
    private JButton buttonEdit;
    private JButton buttonDelete;
    private ControllerLibrary controllerLibrary = new ControllerLibrary();
    ;
    private List<String> listBooksOfAuthor;
    private String nameAuthor;

    public GUIEditBook(String nameAuthor) {
        this.nameAuthor = nameAuthor;

    }

    public void editBook() {
        frame = new JFrame("Редактирование книг");      // Объявление формы и наименование окна
        buttonSave = new JButton("Сохранить");
        buttonSave.setFocusPainted(false);
        buttonBack = new JButton("Назад");
        buttonBack.setFocusPainted(false);
        buttonDelete = new JButton("Удалить");
        buttonDelete.setFocusPainted(false);
        buttonEdit = new JButton("Редактировать");
        buttonEdit.setFocusPainted(false);
        listBooks();
        JScrollPane scroller = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUILibraryWindows libraryWindows = new GUILibraryWindows();
                libraryWindows.libraryWindows();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldNameBook.getText().isEmpty()) return;
                if (!controllerLibrary.getСheckNameBookOfList(nameAuthor, textFieldNameBook.getText())) {
                    int answer = JOptionPane.showConfirmDialog(frame,
                            "Вы точно хотите сохранить данную книгу?",
                            "Сохранение книги",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (answer == 0) {
                        controllerLibrary.setAddNewBook(nameAuthor, textFieldNameBook.getText());
                        listBooks();
                        textFieldNameBook.setText("");
                    }
                } else
                    JOptionPane.showMessageDialog(frame, "Такая книга уже есть в списке", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldNameBook.getText().isEmpty()) return;
                if (controllerLibrary.getСheckNameBookOfList(nameAuthor, textFieldNameBook.getText())) {
                    String newName = JOptionPane.showInputDialog(frame,
                            "Введите новое название книги" + "\n" + "Текущее название книги: " + textFieldNameBook.getText(), "новое название книги");
                    if (newName.equals(textFieldNameBook.getText())) {
                        return;
                    } else {
                        controllerLibrary.setEditBook(nameAuthor, textFieldNameBook.getText(), newName);
                        listBooks();
                        textFieldNameBook.setText("");
                    }
                } else
                    JOptionPane.showMessageDialog(frame, "Укажите точное имя книги для редактирования из списка!", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldNameBook.getText().isEmpty()) return;

                if (controllerLibrary.getСheckNameBookOfList(nameAuthor, textFieldNameBook.getText())) {
                    int answer = JOptionPane.showConfirmDialog(frame,
                            "Вы точно хотите удалить данную книгу?",
                            "Удаление книги из списка",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (answer == 0) {
                        controllerLibrary.setRemoveNameBook(nameAuthor, textFieldNameBook.getText());
                        listBooks();
                        textFieldNameBook.setText("");
                    }
                } else
                    JOptionPane.showMessageDialog(frame, "Такой книги нет в списке у автора!", "Информационное сообщение", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panelDown.add(buttonBack);
        panelDown.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelLeft.add(labelNameBook);
        panelLeft.add(textFieldNameBook);
        panelLeft.add(buttonSave);
        panelLeft.add(buttonEdit);
        panelLeft.add(buttonDelete);

        panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelLeft.setPreferredSize(new Dimension(170, 300));

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        frame.getContentPane().add(BorderLayout.SOUTH, panelDown);
        frame.getContentPane().add(BorderLayout.NORTH, panelTop);
        frame.getContentPane().add(BorderLayout.EAST, panelLeft);
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
        textFieldNameBook.setText(e.getActionCommand());
    }
}
