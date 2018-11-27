package com.medallia.word2vec.test0;

import com.medallia.word2vec.ReadFileXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XmlDemo {
    Pattern p = Pattern.compile("\\d+(\\.*\\d+)?");

    public String[] splitStr(String s){
        String[] arr = s.split("\\s*[^a-zA-Z]+\\s*");
        ArrayList<String> str = new ArrayList<String>(Arrays.asList(arr));
        Matcher m = p.matcher(s);
        while(m.find()) {
            str.add(m.group(1));
        }
        arr = str.toArray(new String[str.size()]);
        return arr;
    }

    public static boolean checkValidDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    public  double cosDouble(String d1, String d2){
        return (Double.parseDouble(d1)==Double.parseDouble(d2))?1:0;
    }

    public static void main(String[] args) {
//        String s = "2,11";
// String s = "328sfdjhuhuhdss344.63 654.3443 34543;64 89ewdsf 1=-=320 1we1d0dsa21203123`1321  434344=-qw[]a;'a.; 43p. '2;11.;'211231.'/ r'wwa/.as.'d.";
//        Matcher m = new XmlDemo().p.matcher(s);
//        while (m.find()) {
//            System.out.println(m.group(0));
 //        String[] arr = new XmlDemo().splitStr(s);
//        for(String si: arr)
//            System.out.println(si);

//        String[] regex = {"<br>","<br />","(?i)he's","(?i)she's","(?i)i'm","(?i)it's","(?i)you're","(?i)we're",
//                "(?i)they're","(?i)wasn't","(?i)weren't","(?i)don't","(?i)doesn't","(?i)didn't","(?i)isn't",
//                "(?i)aren't", "(?i)'ve","(?i)haven't","(?i)hasn't","(?i)couldn't","(?i)can't","(?i)wouldn't",
//                "(?i)hadn't","(?i)shan't", "(?i)mustn't","(?i)shouldn't","(?i)i'll","(?i)he'll",
//                "(?i)she'll","(?i)it'll","(?i)they'll","(?i)we'll", "(?i)you'll","(?i)let's"};
//
//        String[] replace = {"","","he is","she is","i am","it is","you are","we are","they are","was not",
//                "were not", "do not","does not","did not","is not","are not","have","have not","has not",
//                "could not","can not","would not", "had not","shall not","must not","should not","i will",
//                "he will","she will","it will","they will","we will", "you will","let us"};

//        XmlDemo a = new XmlDemo();
        Document doc = ReadFileXml.readFileXml("edited_manner.xml");
        NodeList nList = doc.getElementsByTagName("document");

        for (int temp = 0; temp < 1; temp++) {

            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
//                for(int i=0; i<regex.length;i++) {
////                    eElement.getElementsByTagName("content").item(0).replaceAll(regex[i],replace[i]);
//                }
                String a = eElement.getElementsByTagName("content").item(0).getTextContent();
                System.out.println(a);
//                for(String out: arr)
//                         System.out.println(out);
            }
        }
    }
}