package com.sunkang.test;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @author 孙康
 * @date 2017/5/3
 * Describe：全文搜索引擎
 */
public class LuceneTest {

    private static Logger log = Logger.getLogger(LuceneTest.class);

    //索引位置
    private String indexDir = "F:/indexDir";

    //相当于域，我这里指的文章的内容，还可能是标题、属性
    private String fieldName = "content";

    /**
     * 创建索引
     *
     * @param analyzer 分词器
     */
    public void createIndex(Analyzer analyzer) throws IOException {
        //待索引的数据
        String[] contentArr = {
                "考进清华北大是许多人的梦想",
                "清华是中国著名高等学府",
                "清华是世界上最美丽的大学之一"
        };
        //创建或打开索引目录
        Directory directory = FSDirectory.open(new File(indexDir));
        //创建indexwrite
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        //遍历数组创建索引
        for (String content : contentArr) {
            //创建document并添加field
            Document document = new Document();
            document.add(new TextField(fieldName, content, Field.Store.YES));
            //将document添加到索引中
            indexWriter.addDocument(document);
        }
        indexWriter.commit();
        //关闭流
        indexWriter.close();
        directory.close();
    }

    /**
     * 从索引中检索
     *
     * @param sentence 要检索的语句
     * @param analyzer 分词器
     */
    public void searchIndex(String sentence, Analyzer analyzer) throws IOException, ParseException {
        //创建或打开索引
        Directory directory = FSDirectory.open(new File(indexDir));
        //读索引
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        //使用查询解析器创建query
        QueryParser queryParser = new QueryParser(Version.LUCENE_46, fieldName, analyzer);
        Query query = queryParser.parse(sentence);
        //输出查询语句
        log.debug("查询语句为：" + query.toString());
        //从索引中搜索得分排名前十的文档
        TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        log.debug("共找到" + topDocs.totalHits + "条匹配");
        for (ScoreDoc scoreDoc : scoreDocs) {
            //根据id获取document
            Document document = searcher.doc(scoreDoc.doc);
            log.debug(document.get(fieldName) + scoreDoc.score);
            //查看文档得分解析
            log.debug(searcher.explain(query, scoreDoc.doc));
        }
        indexReader.close();
        directory.close();
    }

    public static void main(String[] args) throws Exception {
        //true为智能分词，false为细粒度分词
        Analyzer analyzer = new IKAnalyzer(true);
        LuceneTest luceneTest = new LuceneTest();
        luceneTest.createIndex(analyzer);
        luceneTest.searchIndex("梦想上清华", analyzer);
    }

}
