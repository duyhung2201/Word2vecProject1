package com.medallia.word2vec.test0;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.medallia.word2vec.ReadFileXml;
import org.w3c.dom.*;

public class transfer {
    public static String username;
    public static File f = new File("test_manner.xml");
    static String[] regex = {"<br>","<br />","(?i)let's","(?i)\\s*'s\\s+","(?i)\\s*'m\\s+","(?i)\\s*'re\\s+",
            "(?i)n\\s*'t\\s+", "(?i)\\s*'ve\\s+","(?i)\\s*'ll\\s+"};
    static String[] strReplace = {"", "", "let us", " is ", " am ", " are ", " not ", " have ", " will "};

    public static void main(String[] argv) throws Exception {
        Document docIn = ReadFileXml.readFileXml("manner.xml");
        Document docOut = DocumentBuilderFactory.newInstance().
                newDocumentBuilder().newDocument();
        NodeList nList = docIn.getElementsByTagName("document");

        Element root = docOut.createElement("document"),
                subject = docOut.createElement("subject"),
                content = docOut.createElement("content"),
                best_answer = docOut.createElement("best_answer"),
                main_cat = docOut.createElement("main_cat");
        Text textSubject, textContent, textBestAnswer, textMainCat ;

        System.out.println("Parsing...");
        for(int i=0; i< 10;i++){
            Node nNode = nList.item(i);
            Element eElement = (Element)nNode;
////            NodeList child = nNode.getChildNodes();
////            for(int j=0; j<child.getLength();j++) {
////                if(child.item(j).getNodeName().equals("uri") ||
////                        child.item(j).getNodeName().equals("yid")||
////                        child.item(j).getNodeName().equals("best_yid")||
////                        child.item(j).getNodeName().equals("nbestanswers"))
////                    child.item(j).getParentNode().removeChild(child.item(j));
////                if(child.item(j).getNodeName().equals("subject"))
////                    child.item(j).setTextContent(replaceS(child.item(j).getTextContent()));
////            }
////            nNode.removeChild(nNode.getAttributes().getNamedItem("uri"));
////            System.out.println(nNode.);
////            if(i%1000==0) System.out.println("Processing: " + i);



            textSubject = docOut.createTextNode(replaceS(eElement.getElementsByTagName("subject").item(0).
                    getTextContent()));
            subject.appendChild(textSubject);
            root.appendChild(subject);

            if(includeNode(eElement,"content")) {
                textContent = docOut.createTextNode(eElement.getElementsByTagName("content").item(0).getTextContent());
                content.appendChild(textContent);
                root.appendChild(content);
            }
            textBestAnswer = docOut.createTextNode(eElement.getElementsByTagName("bestanswer").item(0).getTextContent());
            best_answer.appendChild(textBestAnswer);
            root.appendChild(best_answer);


            textMainCat = docOut.createTextNode(eElement.getElementsByTagName("maincat").item(0).getTextContent());
            main_cat.appendChild(textMainCat);
            root.appendChild(main_cat);
            docOut.appendChild(root);
        }
        System.out.println("Writing...");
        writeXML(docOut);
    }

    public static boolean includeNode(Element eElement, String nodeName){
        try{
            eElement.getElementsByTagName("content");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String replaceS(String s) {
        for (int i = 0; i < regex.length; i++)
            s = s.replaceAll(regex[i], strReplace[i]);
        return s;
    }

    private static void writeXML(Document doc) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(f);
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
