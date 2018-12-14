package com.medallia.word2vec.test0;

import com.medallia.word2vec.ReadFileXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
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

    public static <regex> void main(String[] args) throws TransformerException {
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
//        System.out.println(xmlString);
//        NodeList nList = doc.getElementsByTagName("document");
////        System.out.println(nList.getLength());
////        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(142000);
////            System.out.println("\nCurrent Element :" + nNode.getNodeName());
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
////                for(int i=0; i<regex.length;i++) {
//////                    eElement.getElementsByTagName("content").item(0).replaceAll(regex[i],replace[i]);
////                }
//                String a = eElement.getElementsByTagName("subject").item(0).getTextContent();
//                System.out.println(a);
////                System.out.println(a);
////                for(String out: arr)
////                         System.out.println(out);
//            }
//        }
//        System.out.println(nList.getLength()+"\n"+count);
        String t0 = "<vespaadd><document type=\"wisdom\">\n" +
                "<uri>56910</uri>\n" +
                "<subject>How to handle a 1.5 year old 's 'll 've  when hitting?</subject>\n" +
                "<content>Hi,&#xd;&lt;br&gt;&lt;br&gt;Our 17 month old has started hitting, we don't believe in physical discipline. Any tips other parents would recommend?&#xd;&lt;br&gt;&lt;br&gt;We currently tell him \"no hitting\", \"hitting hurts\", and he kind of gets it. But sometimes clearly that doesn't help much...particularly when he's frustrated.&#xd;&lt;br&gt;&lt;br&gt;Any tips?</content>\n" +
                "<bestanswer>you're on the right track in understanding that he's frustrated. my viewpoint is that the frustration really comes about in not being able to communicate what he wants to person being hit. the hitting action then becomes his only recourse because he sees that it gets your attention.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;something my daughter's preschool does is a time-with versus a time-out and i found better results with my 3 year old. the first thing discussed is having her empathize with the other person by going beyong the \"hitting hurts\" and asking her \"how do you think hitting makes her feel?\" the second thing we do is get her to communicate her frustration is words rather than action, helping her along the way with getting the words right.</bestanswer>\n" +
                "<nbestanswers><answer_item>you're on the right track in understanding that he's frustrated. my viewpoint is that the frustration really comes about in not being able to communicate what he wants to person being hit. the hitting action then becomes his only recourse because he sees that it gets your attention.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;something my daughter's preschool does is a time-with versus a time-out and i found better results with my 3 year old. the first thing discussed is having her empathize with the other person by going beyong the \"hitting hurts\" and asking her \"how do you think hitting makes her feel?\" the second thing we do is get her to communicate her frustration is words rather than action, helping her along the way with getting the words right.</answer_item>\n" +
                "<answer_item>I believe that a 17 month old toddler understand everything that we say to him/her. Therefore we use the follow method:&lt;br /&gt;&#xa;First we explain why it is not allowed.&lt;br /&gt;&#xa;Then we warn him/her that the next time you do that you will seat near the wall (“Time out”) until we will call you. &lt;br /&gt;&#xa;When the next time comes- we seat them near the wall, close to us. (The time out is calculated as follow- for every year of age, he gets 1 minute near the wall)&lt;br /&gt;&#xa;We make sure that we don’t look at him/her during the event, and if they try to get up, we seat them back and remind them why they are in a “time out”. &lt;br /&gt;&#xa;After the time has past, we go and remind them again what they did was wrong, then we ask them “do you promise not to hit again?”. ONLY when they say that they will not do it again we let them get up, huge them kiss them and let them know that we love them.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;I cannot say that it works all the times. But for the long term it works.</answer_item>\n" +
                "</nbestanswers>\n" +
                "<cat>Parenting</cat>\n" +
                "<maincat>Pregnancy &amp; Parenting</maincat>\n" +
                "<subcat>Parenting</subcat>\n" +
                "<yid>u54433</yid>\n" +
                "<best_yid>u56993</best_yid>\n" +
                "</document></vespaadd>\n" +
                "<vespaadd><document type=\"wisdom\">\n" +
                "<uri>109662</uri>\n" +
                "<subject>How can I avoid getting sick in China?</subject>\n" +
                "<content>I'm planning a trip to China. I'm an adventurous eater and I'd like to think my stomach can handle anything that I encounter that appeals to me.  But I know that's risky! Is there anything in particular precautions I should take or things I should avoid to stay healthy on the trip? &#xd;&lt;br&gt;&lt;br&gt;Thanks.</content>\n" +
                "<bestanswer>Number one cause of digestive complaints is the water. Be especially careful to avoid any beverage that has not been pasteurized and vaccuum sealed, or else boiled (such as tea). Avoid ice like the plague, as well as anything such as raw fruits and vegetables that may have been rinsed prior to serving.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;On the flip side, most Chinese food is cooked at high heat and therefore is safe as long as no one has touched it with their hands (unlikely given the high temperatures!). That goes for your hands too, wash your hands frequently and always use chopsticks.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;Finally, pack a bottle of Pepto Bismol, widely regarded (even in medical studies) as the best treatment for mild traveler's diarrhea. If you meticulously follow the suggestions above you shouldn't encounter any problems Pepto can't handle.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;I've traveled in Central America, North Africa, and Asia following these simple guidelines and never gotten sick. However, for much more detailed info, see the link below:</bestanswer>\n" +
                "<nbestanswers><answer_item>Number one cause of digestive complaints is the water. Be especially careful to avoid any beverage that has not been pasteurized and vaccuum sealed, or else boiled (such as tea). Avoid ice like the plague, as well as anything such as raw fruits and vegetables that may have been rinsed prior to serving.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;On the flip side, most Chinese food is cooked at high heat and therefore is safe as long as no one has touched it with their hands (unlikely given the high temperatures!). That goes for your hands too, wash your hands frequently and always use chopsticks.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;Finally, pack a bottle of Pepto Bismol, widely regarded (even in medical studies) as the best treatment for mild traveler's diarrhea. If you meticulously follow the suggestions above you shouldn't encounter any problems Pepto can't handle.&lt;br /&gt;&#xa;&lt;br /&gt;&#xa;I've traveled in Central America, North Africa, and Asia following these simple guidelines and never gotten sick. However, for much more detailed info, see the link below:</answer_item>\n" +
                "<answer_item>You should make sure you drink out of sealed containers and don't drink anything with ice.  Also brush your teeth with bottled water.</answer_item>\n" +
                "</nbestanswers>";
        String b = "/\b(?!subject)\b\\S+/g";
        String t1 = "<subject>dsfdfsasdf</subject>";
        Pattern p = Pattern.compile("<subject>.*</subject>");
        Matcher m = p.matcher(t0);


//        System.out.println(t0.replaceAll(String.valueOf(p),""));
        while (m.find())
        System.out.println(m.group(0));
    }

}