package com.medallia.word2vec;

import com.medallia.word2vec.thrift.Word2VecModelThrift;
import com.medallia.word2vec.util.*;
import org.apache.commons.logging.Log;
import org.apache.thrift.TException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/** Example usages of {@link Word2VecModel} */
public class QaACheck {
    private static final Log LOG = AutoLog.getLog();

    /**
     * Runs the example
     */
    public static void main(String[] args) throws IOException, TException, Searcher.UnknownWordException, InterruptedException {
        loadModel();
    }

    private static void interact(Searcher searcher) throws IOException, Searcher.UnknownWordException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter word or sentence (EXIT to break):\n");
            String sen1 = br.readLine();
            System.out.println("Searching...");
            Document doc = ReadFileXml.readFileXml("q&aDataSet.xml");
            NodeList nList = doc.getElementsByTagName("document");
            double max = 0;
            int tag = 0;
            List<Integer> count = null;
            List<Double> test = null;
            String str = sen1;
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;

//                System.out.println("*" + eElement.getElementsByTagName("subject").item(0).getTextContent());
//                System.out.println("*" + eElement.getElementsByTagName("content").item(0).getTextContent());
//                System.out.println("-----");
                double test2 = searcher.cosDisSentences(str,eElement
                        .getElementsByTagName("subject").item(0).getTextContent());
                if(max<test2){
                    max = test2;
                    tag = i;
                }
//                if(max == test2){
//                    count.add(i);
//                    test.add(test2);
//                }
//                double test1 = searcher.cosineQuesAns(eElement.getElementsByTagName("subject").item(0).getTextContent().toLowerCase()
//                        , eElement.getElementsByTagName("bestanswer").item(0).getTextContent().toLowerCase());
//                double test0 = searcher.cosineQuesAns(eElement.getElementsByTagName("subject").item(0).getTextContent().toLowerCase()
//                        , eElement.getElementsByTagName("answer_item").item(1).getTextContent().toLowerCase());
//                System.out.println(test2);
            }
//            System.out.println(tag);
//            for(int i=0;i<count.size();i++) {
//                if(max == test.get(i))
//                    System.out.println(((Element) nList.item(count.get(i))).getElementsByTagName("subject").item(0)
//                            .getTextContent());
//            }
            System.out.println(((Element) nList.item(tag)).getElementsByTagName("subject").item(0).getTextContent());
            System.out.println(((Element) nList.item(tag)).getElementsByTagName("bestanswer").item(0).getTextContent());
        }
    }

    public static void loadModel() throws IOException, TException, Searcher.UnknownWordException {
        final Word2VecModel model;
        try (ProfilingTimer timer = ProfilingTimer.create(LOG, "Loading model")) {
            String json = Common.readFileToString(new File("text8CBOW.model"));
            model = Word2VecModel.fromThrift(ThriftUtils.deserializeJson(new Word2VecModelThrift(), json));
        }
        interact(model.forSearch());
    }

//    static double cos(String ques,String ans, Searcher searcher) throws Searcher.UnknownWordException {
//        double sumMax = 0;
//        String[] q = searcher.splitStr(ques);
//        String[] a = searcher.splitStr(ans);
//        int len = a.length;
//        for (String aj : a) {
//            double max = 0;
//            if(searcher.checkValidDouble(aj)) {
//                System.out.println("double " + aj);
//                for (String qi : q)
//                    max = (searcher.checkValidDouble(qi) && max < searcher.cosineDouble(aj, qi))
//                            ? searcher.cosineDouble(aj, qi) : max;
//            }
//            else if (searcher.contains(aj)) {
//                for (String qi : q) {
//                    if (!searcher.checkValidDouble(qi) && searcher.contains(qi)) {
//                        double d = searcher.cosineDistance(aj, qi);
//                        System.out.println(aj + "-" + qi + "=" + d);
//                        max = (max < d) ? d : max;
//                    }
//                }
//            }
//            else {
//                len -= 1;
//                System.out.println(aj + "-");
//            }
//            sumMax += max;
//        }
//        return sumMax / len;
//    }
}
