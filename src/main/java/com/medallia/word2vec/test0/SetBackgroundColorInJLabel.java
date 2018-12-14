package com.medallia.word2vec.test0;


import java.awt.*;
import javax.swing.*;

public class SetBackgroundColorInJLabel extends JFrame {

    private static final long serialVersionUID = 1L;

//    public SetBackgroundColorInJLabel() {
//
//        // set flow layout for the frame
//        this.getContentPane().setLayout(new FlowLayout());
//
//        JLabel label = new JLabel("Java Code Geeks - Java Examples");
//
//        // if true the component paints every pixel within its bounds
//        label.setOpaque(true);
//
//        // sets the background color of this component
//        // the background color is used only if the component is opaque
//        label.setBackground(Color.WHITE);
//
//        // add label to frame
//        add(label);
//
//    }

    private static void createAndShowGUI() {

        //Create and set up the window.

        JFrame frame = new JFrame();
        frame.setSize(1200, 800);
        //Display the window.

//        frame.pack();

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        frame.getContentPane().setLayout();
        JTextField tf1 = new JTextField();
        tf1.setBounds(50, 40, 550, 30);
        JButton b1 = new JButton("➜");
        b1.setBounds(600, 40, 100, 30);

        JTextArea textArea = new JTextArea(10,10);
        textArea.setText("dfsfdsfdjfdoisfijofoijd");
        JPanel container = new JPanel();
        container.setBounds(50,110,600,600);
//        container.setBackground(Color.WHITE);
//        a1 = new JTextArea(30,30);
        JScrollPane scrPane = new JScrollPane(container);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPane.setPreferredSize(new Dimension(1200,650));
//        container.add(scrPane);
        JPanel pContain = new JPanel();
        pContain.setBounds(0,110,1200,650);
        scrPane.setBounds(0,110,100,600);
        frame.getContentPane().add(container);
        frame.getContentPane().setBackground(Color.white);
        pContain.add(scrPane);
//        pContain.add(container);
        frame.getContentPane().add(tf1);
        frame.getContentPane().setBackground(Color.white);
//        container.setLayout(null);
//        container.add(tf1);
        container.add(textArea);
        container.setBackground(Color.BLUE);
        frame.setLayout(null);
    }


    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:

        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
//                new SetBackgroundColorInJLabel().createA();

            }
        });
    }

}