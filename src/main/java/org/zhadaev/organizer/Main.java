package org.zhadaev.organizer;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.*;

public class Main {

    private static JFrame frame;
    private static JComboBox<String> comboBox;
    private static JButton infoButton;
    private static JScrollPane scrollTextArea1;
    private static JScrollPane scrollTextArea2;
    private static JTextArea ta1;
    private static JTextArea ta2;
    private static JButton saveButton;
    private static JButton openButton;
    private static JButton calculateButton;
    private static JScrollPane scrollResultArea;
    private static JTextPane resultArea;
    private static JFileChooser fileChooser;
    private static String[] tasks;

    private static final String task1 = "Comparator";
    private static final String task2 = "Expander";

    private static final String task = "task";
    private static final String number = "number";
    private static final String a1 = "array1";
    private static final String a2 = "array2";

    private static Color inactive;

    public static void main(String[] args) {

        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.directoryOpenButtonText", "Открыть");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put("FileChooser.lookInLabelText", "Директория");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {

        frame = new JFrame("Органайзер");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        frame.add(jp);

        setComponents();
        setGroupLayout(jp);

        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

    }

    private static void setComponents() {

        tasks = new String[3];
        tasks[0] = "";
        tasks[1] = task1;
        tasks[2] = task2;
        inactive = new Color(220, 220, 220);

        setTextAreas();
        setSaveButton();
        setOpenButton();
        setCalculateButton();
        setComboBox();
        setInfoButton();

    }

    private static void setSaveButton() {

        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String filePath;
                switch (comboBox.getSelectedItem().toString()) {

                    case task1:
                        filePath = FileSaverAndOpener.save(comboBox.getSelectedItem().toString(),
                                                            ta1.getText(),
                                                            ta2.getText());
                        resultArea.setText("Сохранён файл" + filePath);
                        break;

                    case task2:
                        filePath = FileSaverAndOpener.save(comboBox.getSelectedItem().toString(),
                                                            ta1.getText());
                        resultArea.setText("Сохранён файл" + filePath);
                        break;

                }
            }
        });

    }

    private static void setOpenButton() {

        openButton = new JButton("Загрузить");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser = new JFileChooser(FileSaverAndOpener.getPath());
                fileChooser.setDialogTitle("Загрузить файл");
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    Map<String, String> map = FileSaverAndOpener.open(file);

                    if (map.containsKey("Exception")) {
                        resultArea.setText(map.get("Exception"));
                        return;
                    }

                    switch (map.get(task)) {

                        case task1:
                            comboBox.setSelectedItem(task1);
                            ta1.setText(map.get(a1));
                            ta2.setText(map.get(a2));
                            calculate();
                            break;

                        case task2:
                            comboBox.setSelectedItem(task2);
                            ta1.setText(map.get(number));
                            calculate();
                            break;

                        default:
                            resultArea.setText("");

                    }

                } else {
                    resultArea.setText("Файл не загружен");
                }
            }
        });

    }

    private static void setCalculateButton() {

        calculateButton = new JButton("Посчитать");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

    }

    private static void calculate() {

        StringBuilder result;
        switch (comboBox.getSelectedItem().toString()) {

            case task1:
                String[] array1 = ta1.getText().split(" ");
                String[] array2 = ta2.getText().split(" ");
                result = new StringBuilder();
                for (String str: Comparator.compare(array1, array2)) {
                    result.append(str).append(" ");
                }
                resultArea.setText(result.toString());
                break;

            case task2:
                result = new StringBuilder(Expander.expand(ta1.getText()));
                resultArea.setText(result.toString());
                break;

            default:
                resultArea.setText("Выберите задачу или загрузите файл");
        }

    }

    private static void setInfoButton() {

        infoButton = new JButton("?");
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, getInfo(), "Справка", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    private static String getInfo() {

        StringBuilder result = new StringBuilder("");

        File file = new File(Main.class.getClassLoader().getResource("info.txt").getFile());

        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    private static void setComboBox() {

        comboBox = new JComboBox<String>(tasks) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (comboBox.getSelectedItem().toString().equals("")) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setBackground(Color.BLACK);
                    g2.setColor(Color.gray);
                    g2.drawString("Выберите задачу...", 7, 14);
                    g2.dispose();
                }
            }
        };
        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.DESELECTED) return;

                switch (e.getItem().toString()) {

                    case task1:
                        ta1.setEnabled(true);
                        ta2.setEnabled(true);
                        ta1.setText("");
                        ta2.setText("");
                        ta1.setBackground(Color.WHITE);
                        ta2.setBackground(Color.WHITE);

                        if (ta1.getKeyListeners().length != 0) {
                            ta1.removeKeyListener(ta1.getKeyListeners()[0]);
                        }

                        resultArea.setText("Введите слова через пробел в верхнее и нижнее поля");
                        break;

                    case task2:
                        ta1.setEnabled(true);
                        ta2.setEnabled(false);
                        ta1.setText("");
                        ta2.setText("");
                        ta1.setBackground(Color.WHITE);
                        ta2.setBackground(inactive);

                        ta1.addKeyListener(new KeyAdapter() {
                            public void keyTyped(KeyEvent e) {
                                char c = e.getKeyChar();
                                if (((c < '0') || (c > '9'))) {
                                    e.consume();
                                }
                            }
                        });

                        resultArea.setText("Введите число");
                        break;

                    default:
                        ta1.setEnabled(false);
                        ta2.setEnabled(false);
                        ta1.setText("");
                        ta2.setText("");
                        ta1.setBackground(inactive);
                        ta2.setBackground(inactive);
                        resultArea.setText("Выберите задачу или загрузите файл");
                }
            }
        });

    }

    private static void setTextAreas() {

        ta1 = new JTextArea() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setBackground(Color.BLACK);
                    g2.setColor(Color.gray);
                    switch (comboBox.getSelectedItem().toString()) {
                        case task1:
                            g2.drawString("Введите слова...", 7, 14);
                            g2.dispose();
                            break;
                        case task2:
                            g2.drawString("Введите число...", 7, 14);
                            g2.dispose();
                            break;
                    }
                }
            }
        };

        ta2 = new JTextArea() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setBackground(Color.BLACK);
                    g2.setColor(Color.gray);
                    if (comboBox.getSelectedItem().toString().equals(task1)) {
                            g2.drawString("Введите слова...", 7, 14);
                            g2.dispose();
                    }
                }
            }
        };

        ta1.setEnabled(false);
        ta2.setEnabled(false);
        ta1.setLineWrap(true);
        ta2.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta2.setWrapStyleWord(true);
        ta1.setBackground(inactive);
        ta2.setBackground(inactive);

        resultArea = new JTextPane();
        resultArea.setBackground(new Color(230, 230, 230));
        resultArea.setText("Выберите задачу или загрузите файл");
        resultArea.setEnabled(false);
        resultArea.setDisabledTextColor(Color.BLACK);

        scrollTextArea1 = new JScrollPane(ta1,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTextArea2 = new JScrollPane(ta2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollResultArea = new JScrollPane(resultArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
                        .addComponent(comboBox)
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
                        .addComponent(comboBox)
                        .addComponent(infoButton))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollTextArea1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollTextArea2))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                                .addComponent(openButton)
                                .addComponent(calculateButton)))
                .addComponent(scrollResultArea, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
        );

    }

}