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

import java.io.File;
import java.io.IOException;

public class RunModel {
    private static final Log LOG = AutoLog.getLog();
//    static NodeList nList_bAns;
//    static NodeList nList_sub;
    public static Word2VecModel modelAvai;

    public static void loadModel() throws IOException, TException{

        try (ProfilingTimer timer = ProfilingTimer.create(LOG, "Loading model")) {
            String json = Common.readFileToString(new File("text8CBOW.model"));
            modelAvai = Word2VecModel.fromThrift(ThriftUtils.deserializeJson(new Word2VecModelThrift(), json));
        }
        System.out.println(" Loaded model");
    }
    public static void main(String[] args) throws TException, IOException, Searcher.UnknownWordException {
//        loadModel();
        System.out.println(modelAvai.forSearch().cosineDistance("a","b"));
    }
}
