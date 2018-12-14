package com.medallia.word2vec;

import com.medallia.word2vec.thrift.Word2VecModelThrift;
import com.medallia.word2vec.util.AutoLog;
import com.medallia.word2vec.util.Common;
import com.medallia.word2vec.util.ProfilingTimer;
import com.medallia.word2vec.util.ThriftUtils;
import org.apache.commons.logging.Log;
import org.apache.thrift.TException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class demoJavaSwing implements ActionListener {
    private JTextField tf1;
    private JTextArea a1;
    private final Log LOG = AutoLog.getLog();
    private Document subj_doc =  ReadFileXml.readFileXml("subj_store.xml");
    private Document full_doc =  ReadFileXml.readFileXml("bestAns_store.xml");
    private NodeList nList_sub = subj_doc.getElementsByTagName("subject");
    private NodeList nList_bAns = full_doc.getElementsByTagName("bestanswer");
    private Word2VecModel model;


    private void JavaSwing() {
        JFrame f = new JFrame("Find the Answer");
        f.setVisible(true);
        f.setSize(1200, 750);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lhead = new JLabel(" LêDuyHưng - LêNgọcChương - NguyễnVănViễn");
        lhead.setBounds(5,3,300,13);
        lhead.setForeground(Color.decode("#0000CD"));
        lhead.setFont(new Font("Plain",Font.PLAIN,11));

        JLabel l1 = new JLabel("Nhập câu hỏi:");
        l1.setFont(new Font("Serif", Font.ITALIC | Font.BOLD,16));
        l1.setBounds(55,45,300,15);

        JLabel l2 = new JLabel("Dựa theo");
        l2.setBounds(915,51,75,35);
        JLabel lYicon = new JLabel("YAHOO!");
        lYicon.setForeground(Color.decode("#4B0082"));
        lYicon.setFont(new Font("Serif", Font.BOLD,30));
        lYicon.setBounds(975,51,150,35);
        JLabel lAns = new JLabel("ANSWER");
        lAns.setFont(new Font("Serif", Font.PLAIN,17));
        lAns.setBounds(1100,51,100,35);

        tf1 = new JTextField();
        tf1.setBounds(50, 60, 550, 35);
//        tf1.setBorder(new LineBorder(Color.black, 2, true));
        JButton b1 = new JButton("Search");
        b1.setBounds(600, 60, 100, 35);
        b1.addActionListener(this);

        JLabel lnote = new JLabel("(* Hỗ trợ tìm kiếm với ngôn ngữ Tiếng Anh)");
        lnote.setFont(new Font("Plain",Font.PLAIN,11));
        lnote.setBounds(55,95,300,11);

        JPanel container = new JPanel();
        container.setBounds(0,110,1200,600);
//        container.setBackground(Color.WHITE);

        a1 = new JTextArea(30,76);
//        a1.setSize(1200,800);
        a1.setWrapStyleWord(true);
        a1.setLineWrap(true);
        a1.setForeground(Color.decode("#0000CD"));
        a1.setFont(new Font("Serif", Font.BOLD,17));
        a1.setEditable(false);

        JPanel scrContainer = new JPanel();
//        scrContainer.setSize(1200,600);
        scrContainer.setBackground(Color.WHITE);
        JScrollPane scrPane = new JScrollPane(scrContainer);
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPane.setPreferredSize(new Dimension(1200,600));
        scrPane.setBorder(BorderFactory.createEmptyBorder());
        scrContainer.add(a1);

        container.add(scrPane);

        JLabel lbot = new JLabel("Project 1 - Ha Noi University of Science and Technology");
        lbot.setBounds(841,3,400,15);

//        f.getContentPane().setBackground(Color.white);
        f.add(lhead);
        f.add(l2);
        f.add(lnote);
        f.add(lYicon);
        f.add(lAns);
        f.add(lbot);
        f.add(container);
        f.add(l1);
        f.add(tf1);
        f.add(b1);
        f.setLayout(null);
    }

    public void actionPerformed(ActionEvent e) {
        String s1 = tf1.getText();
        a1.setText(interact(model.forSearch(), s1)); //tim cap Q&A gan nhat vs ques nhap vao
//        a1.setText("<html>"+"There are nine seats on the United States Supreme Court. You must be nominated by the President of the United States, and confirmed by a majority in the U.S. Senate. Justices are appointed for life (except in cases of retirement or impeachment).&lt;br /&gt;\n" +
//                "&lt;br /&gt;\n" +
//                "It is not necessary for that person to have experience as a judge (George W. Bush's nomination of Harriet Miers http://news.search.yahoo.com/news/search?ei=UTF-8&amp;fl=0&amp;fr=slv5-&amp;p=harriet+miers+%22supreme+court%22&lt;br /&gt;\n" +
//                "is not the first time a non-judge has been nominated), just a solid legal mind. Generally when a President chooses a nominee, he will look for someone whose ideals and worldview match his own. Gerald Ford famously did not make an idealogical choice when he appointed John Paul Stevens (http://www.supremecourthistory.org/04_library/subs_volumes/04_c11_f.html) &lt;br /&gt;\n" +
//                "and in subsequent years was rather disappointed at some of Stevens' decisions. &lt;br /&gt;\n" +
//                "&lt;br /&gt;\n" +
//                "There is a lot more information at the official Supreme Court site: http://www.supremecourtus.gov/</bestanswer>\n" +
//                "<" + "</html>");
    }

    public static void main(String[] args) throws IOException, TException {
        demoJavaSwing t = new demoJavaSwing();
        t.loadModel();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                t.JavaSwing();
            }
        });
    }

    //tim kiem cap Q&A gan
    private String interact(Searcher searcher, String str) {
        String result ="";
        double max = 0;
        int tag = 0;
        List<Integer> count = new ArrayList<Integer>();
        List<Double> equal_sim = new ArrayList<Double>();

        for (int i = 0; i < nList_sub.getLength(); i++) {
            double sim = searcher.cosDisSentences(str, (nList_sub.item(i).getTextContent()));
            if (max == sim) {
                count.add(i);
                equal_sim.add(sim);
            } else if (max < sim) {
                max = sim;
                tag = i;
            }
        }
                for (int i = count.size() - 1; i >= 0; i--) {
                    if (max == equal_sim.get(i)) {
                        result += "\n+) Question:   " + nList_sub.item(count.get(i)).getTextContent().toUpperCase() ;
                        result += "\n\nBest Answer:\n " + nList_bAns.item(count.get(i)).getTextContent() +
                                "\n______________________________________________________________________\n";
                    } else break;
                }
                result+= "\n+) Question: " + nList_sub.item(tag).getTextContent().toUpperCase();
                result+= "\n\nBest Answer:\n" + nList_bAns.item(tag).getTextContent() +
                        "\n______________________________________________________________________\n";
        return result;
    }

    //load tap du lieu W2
    private void loadModel() throws IOException, TException {

        try (ProfilingTimer timer = ProfilingTimer.create(LOG, "Loading model")) {
            String json = Common.readFileToString(new File("text8CBOW.model"));
            model = Word2VecModel.fromThrift(ThriftUtils.deserializeJson(new Word2VecModelThrift(), json));
        }
        System.out.println(" Loaded model");
    }
}
