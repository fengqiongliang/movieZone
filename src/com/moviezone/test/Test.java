package com.moviezone.test;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
import org.apache.lucene.facet.FacetsCollector.MatchingDocs;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.LabelAndValue;
import org.apache.lucene.facet.sortedset.DefaultSortedSetDocValuesReaderState;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetCounts;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesFacetField;
import org.apache.lucene.facet.sortedset.SortedSetDocValuesReaderState;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.moviezone.cache.CacheClient;
import com.moviezone.cache.CacheFactory;
import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.HttpUtil;
import com.moviezone.util.ObjectUtil;
import com.moviezone.util.SecurityUtil;

public class Test {
	 private final Directory indexDir   = new RAMDirectory();
	 private final FacetsConfig config = new FacetsConfig();
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		Dictionary.initial(DefaultConfig.getInstance());
		Dictionary dictionary = Dictionary.getSingleton();
		
		List<String> normalWords = new ArrayList<String>();
		List<String> stopWords     = new ArrayList<String>();
		normalWords.add("习近平");
		normalWords.add("习近");
		stopWords.add("冯琼亮");
		dictionary.addWords(normalWords);
		dictionary.disableWords(stopWords);
		
		Directory directory = new RAMDirectory();
		Analyzer analyzer = new IKAnalyzer();
		
	    IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
	    writerConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
	    FacetsConfig         facetsConfig = new FacetsConfig();	
	    IndexWriter iwriter = new IndexWriter(directory, writerConfig);
	    
	    Document doc1 = new Document();
	    doc1.add(new SortedSetDocValuesFacetField("type","480p"));
	    doc1.add(new SortedSetDocValuesFacetField("typeName","480p"));
	    doc1.add(new StringField("id", "1", Field.Store.YES));
	    //doc1.add(new Field("name", "驯龙高手1~3", TextField.TYPE_NOT_STORED));
	    doc1.add(new Field("short_desc", "有没有搞错啊1", TextField.TYPE_NOT_STORED));
	    doc1.add(new Field("long_desc", "习", TextField.TYPE_NOT_STORED));
	    doc1.add(new LongField("create_time",System.currentTimeMillis(),LongField.TYPE_NOT_STORED));
	    
	    Document doc2 = new Document();
	    doc2.add(new SortedSetDocValuesFacetField("type","720p"));
	    doc2.add(new SortedSetDocValuesFacetField("typeName","720"));
	    doc2.add(new StringField("id", "2", Field.Store.YES));
	    doc2.add(new Field("name", "四大名捕3", TextField.TYPE_NOT_STORED));
	    doc2.add(new Field("short_desc", "有没捕搞错啊2", TextField.TYPE_NOT_STORED));
	    doc2.add(new Field("long_desc", "习近", TextField.TYPE_NOT_STORED));
	    doc2.add(new LongField("create_time",System.currentTimeMillis(),LongField.TYPE_NOT_STORED));
	    
	    Document doc3 = new Document();
	    doc3.add(new SortedSetDocValuesFacetField("type","1080p"));
	    doc3.add(new SortedSetDocValuesFacetField("typeName","1080p"));
	    doc3.add(new StringField("id", "3", Field.Store.YES));
	    doc3.add(new Field("name", "四大名捕3", TextField.TYPE_NOT_STORED));
	    doc3.add(new Field("short_desc", "有没有搞错啊3", TextField.TYPE_NOT_STORED));
	    doc3.add(new Field("long_desc", "习近平", TextField.TYPE_NOT_STORED));
	    doc3.add(new LongField("create_time",System.currentTimeMillis()+10,LongField.TYPE_NOT_STORED));
	    
	    Document doc4 = new Document();
	    doc4.add(new SortedSetDocValuesFacetField("type","1080p"));
	    doc4.add(new SortedSetDocValuesFacetField("typeName","1080p"));
	    doc4.add(new StringField("id", "4", Field.Store.YES));
	    doc4.add(new Field("name", "四大名捕4", TextField.TYPE_NOT_STORED));
	    doc4.add(new Field("short_desc", "有没有搞错啊4", TextField.TYPE_NOT_STORED));
	    doc4.add(new Field("long_desc", "习近平", TextField.TYPE_NOT_STORED));
	    doc4.add(new LongField("create_time",System.currentTimeMillis()+10,LongField.TYPE_NOT_STORED));
	    
	    Document doc5 = new Document();
	    doc5.add(new SortedSetDocValuesFacetField("type","1080p"));
	    doc5.add(new SortedSetDocValuesFacetField("typeName","港台"));
	    doc5.add(new StringField("id", "5", Field.Store.YES));
	    doc5.add(new Field("name", "四大名捕5", TextField.TYPE_NOT_STORED));
	    doc5.add(new Field("short_desc", "有没有搞错啊5", TextField.TYPE_NOT_STORED));
	    doc5.add(new Field("long_desc", "习近平", TextField.TYPE_NOT_STORED));
	    doc5.add(new LongField("create_time",System.currentTimeMillis()+10,LongField.TYPE_NOT_STORED));
	    
	    iwriter.addDocument(facetsConfig.build(doc1));
	    iwriter.addDocument(facetsConfig.build(doc2));
	    iwriter.addDocument(facetsConfig.build(doc3));
	    iwriter.addDocument(facetsConfig.build(doc5));
	    iwriter.close();
	    
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]{"name","short_desc","long_desc"},analyzer);
	    SortedSetDocValuesReaderState state = new DefaultSortedSetDocValuesReaderState(ireader);
	    Query facetQuery = parser.parse(HttpUtil.filterSearchForLucene("习近平"));
	    
	    //查出所有facet
	    List<String> allTypes = new ArrayList<String>();
	    List<String> allFacets = new ArrayList<String>();
	    List<Long> data = new ArrayList<Long>();
	    FacetsCollector fc = new FacetsCollector();
	    FacetsCollector.search(isearcher, facetQuery, null, 1, new Sort(SortField.FIELD_SCORE,new SortField("create_time",SortField.Type.LONG,true)),true,true,fc);
	    Facets facets = new SortedSetDocValuesFacetCounts(state, fc);
	    FacetResult  fr = facets.getTopChildren(1000, "type");
	    LabelAndValue[] labelValues = fr.labelValues;
	    System.out.println(labelValues);
	    for(LabelAndValue labelValue:facets.getTopChildren(1000, "type").labelValues){
	    	String facetName =  labelValue.toString();
	    	System.out.println(facetName);
	    	allTypes.add(labelValue.label);
	    	allFacets.add(facetName);
	    }
	    System.out.println("=================");
	    //查找数据值
	    String type = "72012p";
	    int pageNo  = 1;
	    int pageSize = 1;
	    DrillDownQuery dataQuery = new DrillDownQuery(facetsConfig,facetQuery);
	    if(allTypes.size()>0)dataQuery.add("type", allTypes.contains(type)?type:allTypes.get(0));
	    TopFieldDocs topDocs = FacetsCollector.search(isearcher, dataQuery, null, 1, new Sort(SortField.FIELD_SCORE,new SortField("create_time",SortField.Type.LONG,true)),true,true,fc);
	    ScoreDoc[] hitDocs  = topDocs.scoreDocs;
	    System.out.println("total  ： "+topDocs.totalHits);
	    int start = Math.max((pageNo-1)*pageSize, 0);
	    int end  = Math.min(pageNo*pageSize, hitDocs.length);
	    for (int i = start; i < end; i++) {
		      Document hitDoc = isearcher.doc(hitDocs[i].doc);
		      System.out.println(hitDoc.get("id")+" ---> "+hitDocs[i].score);
		      //data.add(hitDoc);
		}
	    
	    
	    ireader.close();
	    directory.close();
		
	}
	
	
	
	
	private String readFile(File fileName) throws Exception{
		FileInputStream fis = new FileInputStream(fileName);
		List<Byte> its = new ArrayList<Byte>();
		while(true){
			byte[] buf = new byte[1024];
			int size = fis.read(buf);
			if(size <= 0)break;
			for(int i=0;i<size;i++)its.add(buf[i]);
		}
		fis.close();
		Object[] tmp = its.toArray();
		byte[] bs  = new byte[tmp.length];
		for(int i=0;i<tmp.length;i++)bs[i]=(Byte)tmp[i];
		return new String(bs,Charset.forName("gbk"));
	}
	
	private void writeFile(File fileName,String content) throws Exception {
		FileWriter writer = new FileWriter(fileName);
		writer.write(content);
		writer.flush();
	}
	
	
	private String toChar(byte[] buf,int size){
		StringBuilder builder = new StringBuilder();
		char hex[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		for(int i=0;i<size;i++){
			byte b = buf[i];
			builder.append(hex[(b&0xf0)>>4]);
			builder.append(hex[b&0x0f]);
		}
		return builder.toString();
	}	
	
}
