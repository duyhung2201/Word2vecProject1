package com.medallia.word2vec;

import com.medallia.word2vec.thrift.Word2VecModelThrift;
import com.medallia.word2vec.util.*;
import org.apache.commons.logging.Log;
import org.apache.thrift.TException;
import org.w3c.dom.Document;

import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class QaACheck {
    private final Log LOG = AutoLog.getLog();
    Document subj_doc = ReadFileXml.readFileXml("subj_store.xml");
    Document full_doc = ReadFileXml.readFileXml("bestAns_store.xml");
    NodeList nList_sub = subj_doc.getElementsByTagName("subject");
    NodeList nList_bAns = full_doc.getElementsByTagName("bestanswer");
    Word2VecModel model;
    /**
     * Runs the example
     */
    public static void main(String[] args) throws IOException, TException, Searcher.UnknownWordException, InterruptedException {
        QaACheck run = new QaACheck();
        run.loadModel(); //loadModel
        run.interact(run.model.forSearch()); //thuc thi ham search
    }

    private void interact(Searcher searcher) throws IOException, Searcher.UnknownWordException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            for (; ; ) {
                System.out.print("Enter word or sentence (EXIT to break):\n");
                String str = br.readLine();
                if(str.equals("exit")) break;

                System.out.println("Searching...");
                double max = 0;
                int tag = 0;
                List<Integer> count = new ArrayList<Integer>();
                List<Double> equal_sim = new ArrayList<Double>();

                for (int i = 0; i < nList_sub.getLength(); i++) {
                    double sim = searcher.cosDisSentences(str, (nList_sub.item(i).getTextContent()));
                    if (max == sim) {
                        count.add(i);
                        equal_sim.add(sim);
                    }
                    else if (max < sim) {
                        max = sim;
                        tag = i;
                    }
                }
                for (int i = count.size() - 1; i >= 0; i--) {
                    if (max == equal_sim.get(i)) {
                        String nQues = nList_sub.item(count.get(i)).getTextContent();
                        System.out.println("+) Question:   " + nQues );
                        System.out.println("Answer:\n" + nList_bAns.item(count.get(i)).getTextContent());
                    } else break;
                }
                String nQues = ( nList_sub.item(tag).getTextContent());
                System.out.println("+) Question: " + nQues );
                System.out.println("Answer:\n" + nList_bAns.item(tag).getTextContent());
            }
        }
    }

    public void loadModel() throws IOException, TException, Searcher.UnknownWordException {

        try (ProfilingTimer timer = ProfilingTimer.create(LOG, "Loading model")) {
            String json = Common.readFileToString(new File("text8CBOW.model"));
            model = Word2VecModel.fromThrift(ThriftUtils.deserializeJson(new Word2VecModelThrift(), json));
        }
        System.out.println(" Loaded model");
    }


}
