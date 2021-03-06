package jdt.maker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;
import java.awt.event.KeyEvent.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.Instant.*;
import java.util.ArrayList;
import java.util.Collection;

public class Window extends JFrame {

    private JPanel container = new JPanel();
    //private JPanel datePane = new JPanel();
    private JPanel hourPane = new JPanel();
    private JPanel fieldPane = new JPanel();
    private JPanel buttonPane = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem exitMenu = new JMenuItem("Exit");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem aboutMenu = new JMenuItem("About");

    private JLabel dateLabel = new JLabel("Date");
    private JLabel startLabel = new JLabel("Start Time");
    private JLabel endLabel = new JLabel("Ending Time");

    private JTextField fieldDate = new JTextField();
    private JComboBox startCombo = new JComboBox();
    private JComboBox endCombo = new JComboBox();

    private JTextArea fieldAction = new HintTextField("Type what you did here...");

    private JButton buttonPrev = new JButton("Prev");
    private JButton buttonNext = new JButton("Next");
    private JButton buttonFinish = new JButton("Finish");

    public int itemNumber = 0;

    public JSONObject jsonTitle = new JSONObject();

    public Window() {
        this.setTitle("JDT Maker");
        this.setSize(600, 500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        this.setBackground(Color.WHITE);

        exitMenu.setToolTipText("Informations about the application");
        exitMenu.addActionListener((event) -> System.exit(0));

        fileMenu.add(exitMenu);
        helpMenu.add(aboutMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        for (int i = 0; i < 24; i++) {
            String var1 = i + ":00";
            String var2 = i + ":30";
            startCombo.addItem(var1);
            endCombo.addItem(var1);
            startCombo.addItem(var2);
            endCombo.addItem(var2);
        }


        //hourPane.add(valueDatePicker);
        fieldDate.setPreferredSize(new Dimension(80, 20));
        hourPane.add(dateLabel);
        hourPane.add(fieldDate);
        hourPane.add(startLabel);
        hourPane.add(startCombo);
        hourPane.add(endLabel);
        hourPane.add(endCombo);

        fieldAction.setPreferredSize(new Dimension(350, 150));

        fieldPane.add(fieldAction);

        buttonPrev.setBackground(Color.GRAY);
        buttonPrev.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonPrev.setForeground(Color.WHITE);

        buttonNext.setBackground(Color.BLUE);
        buttonNext.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonNext.setForeground(Color.WHITE);

        buttonFinish.setBackground(Color.GREEN);
        buttonFinish.setFont(new Font("Verdana", Font.BOLD, 10));
        buttonFinish.setForeground(Color.WHITE);

        buttonPrev.addActionListener(new BPListener());
        buttonNext.addActionListener(new BNListener());
        buttonFinish.addActionListener(new BFListener());
        buttonPane.add(buttonPrev);
        buttonPane.add(buttonNext);
        buttonPane.add(buttonFinish);

        container.setLayout(new BorderLayout());
        container.add(hourPane, BorderLayout.NORTH);

        container.add(fieldPane, BorderLayout.CENTER);
        container.add(buttonPane, BorderLayout.SOUTH);
        //container.add(hourPane, BorderLayout.EAST);

        fieldDate.setText(String.valueOf(java.time.LocalDate.now()));

        this.setContentPane(container);
        this.setVisible(true);
    }

    class BPListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            System.out.println("PREV");
        }
    }

    class BNListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {

            JSONObject jsonDetail = new JSONObject();

            jsonDetail.put("Start", startCombo.getSelectedItem());
            jsonDetail.put("End", endCombo.getSelectedItem());
            jsonDetail.put("Action", fieldAction.getText());

            jsonTitle.put(itemNumber, jsonDetail);


            System.out.println(jsonTitle.toJSONString());

            startCombo.setSelectedItem(endCombo.getSelectedItem());
            fieldAction.setText("");
            itemNumber++;
        }
    }

    class BFListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            try (FileWriter file = new FileWriter("JDT.json")) {
                file.write(jsonTitle.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
