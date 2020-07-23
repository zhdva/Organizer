package org.zhadaev.organizer;

import java.awt.event.*;

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.*;

public class Main {

    private static JComboBox<String> cb;
    private static JButton infoButton;
    private static JScrollPane scrollTextArea1;
    private static JScrollPane scrollTextArea2;
    private static JTextArea ta1;
    private static JTextArea ta2;
    private static JButton saveButton;
    private static JButton openButton;
    private static JButton calculateButton;
    private static JScrollPane scrollResultArea;
    private static JTextArea resultArea;
    private final static String[] tasks = {" ", "Expander", "Comparator"};

    public static void main(String[] args) {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {

        JFrame frame = new JFrame("Органайзер");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        frame.add(jp);

        setComponents();
        setGroupLayout(jp);

        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

    }

    private static JComboBox<String> getComboBox() {

        JComboBox<String> cb = new JComboBox<>(tasks);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getItem().toString()) {
                    case "Expander":
                        ta1.setEnabled(true);
                        ta2.setEnabled(false);
                        ta1.setText("");
                        ta2.setText("");

                        resultArea.setText("Введите число");
                        break;
                    case "Comparator":
                        ta1.setEnabled(true);
                        ta2.setEnabled(true);
                        ta1.setText("");
                        ta2.setText("");
                        resultArea.setText("Введите слова в поля через пробел");
                        break;
                    default:
                        ta1.setEnabled(false);
                        ta2.setEnabled(false);
                        ta1.setText("");
                        ta2.setText("");
                        resultArea.setText("Выберите задачу или загрузите файл");
                }
            }
        });

        return cb;

    }

    private static void setComponents() {

        ta1 = new JTextArea();
        ta2 = new JTextArea();
        ta1.setEnabled(false);
        ta2.setEnabled(false);
        //ta1.setPreferredSize(new Dimension(480, 105));
        //ta2.setPreferredSize(new Dimension(480, 105));
        ta1.setLineWrap(true);
        ta2.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta2.setWrapStyleWord(true);

        scrollTextArea1 = new JScrollPane(ta1,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTextArea2 = new JScrollPane(ta2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String filePath;
                switch (cb.getSelectedItem().toString()) {
                    case "Expander":
                        filePath = FileSaverAndOpener.save(cb.getSelectedItem().toString(), ta1.getText());
                        resultArea.setText("Сохранён файл" + filePath);
                        break;
                    case "Comparator":
                        filePath = FileSaverAndOpener.save(cb.getSelectedItem().toString(), ta1.getText(), ta2.getText());
                        resultArea.setText("Сохранён файл" + filePath);
                        break;
                    default:
                        resultArea.setText("Выберите задачу");
                }
            }
        });

        openButton = new JButton("Загрузить");

        calculateButton = new JButton("Посчитать");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StringBuilder result;
                switch (cb.getSelectedItem().toString()) {
                    case "Expander":
                        result = new StringBuilder(Expander.expand(ta1.getText()));
                        resultArea.setText(result.toString());
                        break;
                    case "Comparator":
                        String[] a1 = ta1.getText().split(" ");
                        String[] a2 = ta2.getText().split(" ");
                        result = new StringBuilder();
                        for (String str: Comparator.compare(a1, a2)) {
                            result.append(str).append(" ");
                        }
                        resultArea.setText(result.toString());
                        break;
                    default:
                        resultArea.setText("Выберите задачу или загрузите файл");
                }
            }
        });

        cb = getComboBox();
        infoButton = new JButton("?");

        resultArea = new JTextArea();
        resultArea.setEnabled(false);
        resultArea.setLineWrap(true);
        scrollResultArea = new JScrollPane(resultArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultArea.setText("Выберите задачу или загрузите файл");

    }

    private static void setGroupLayout(final JPanel jp) {

        GroupLayout layout = new GroupLayout(jp);
        jp.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.linkSize(saveButton, openButton, calculateButton);
        layout.linkSize(scrollTextArea1, scrollTextArea2);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(cb)
                        .addComponent(infoButton))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(scrollTextArea1, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollTextArea2))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addComponent(openButton)
                                .addComponent(calculateButton)))
                .addComponent(scrollResultArea)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(cb)
                        .addComponent(infoButton))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollTextArea1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollTextArea2))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                .addComponent(openButton)
                                .addComponent(calculateButton)))
                .addComponent(scrollResultArea, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        );

    }

}