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
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
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
import org.apache.lucene.search.TermQuery;
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
		String keyForIndex = "视点之窗,高清影视论坛,高清电影下载,蓝光电影下载";
		String desForIndex = "视点之窗-国内知名的高清影视论坛,提供480p、720p、1080p及蓝光等高清电影下载,打造互联网最优秀的高清影视收集站";
		String titleForIndex = "视点之窗_高清电影下载_打造互联网最优秀的高清影视收集站";
		
		String keyForMv = "480p高清电影,720p高清电影,1080p高清电影,蓝光电影下载";
		String desForMv = "视点之窗电影-网罗互联网480p、720p、1080p等高清电影下载,是影视爱好者的天堂,也是您必不可少的高清管家";
		String titleForMv = "视点之窗电影_您必不可少的高清管家";
		
		String keyForTv = "英美电视剧,日韩电视剧,港台电视剧,内地电视剧";
		String desForTv = "视点之窗电视剧-提供英美、日韩、港台、内地等高清电视剧下载,是您追剧的好帮手";
		String titleForTv = "视点之窗电视剧_您追剧的好帮手";
		
		String keyForContent = "{心花怒放}480p高清下载,心花怒放720p高清下载,心花怒放1080p高清下载";
		String desForContent = "视点之窗内容-提供{心花怒放}480p高清下载,心花怒放720p高清下载,心花怒放1080p高清下载";
		String titleForContent = "视点之窗内容_{心花怒放}高清下载";
		
		String keyForSearch = "搜索:{心花怒放}";
		String desForSearch = "视点之窗搜索-搜索更多有关{心花怒放}高清电影";
		String titleForSearch = "视点之窗搜索_{心花怒放}";
		
		
		System.out.println("keyForIndex :  "+keyForIndex);
		System.out.println("desForIndex :  "+desForIndex);
		System.out.println("titleForIndex :  "+titleForIndex);
		
		System.out.println("keyForMv :  "+keyForMv);
		System.out.println("desForMv :  "+desForMv);
		System.out.println("titleForMv :  "+titleForMv);
		
		System.out.println("keyForTv :  "+keyForTv);
		System.out.println("desForTv :  "+desForTv);
		System.out.println("titleForTv :  "+titleForTv);
		
		System.out.println("keyForContent :  "+keyForContent);
		System.out.println("desForContent :  "+desForContent);
		System.out.println("titleForContent :  "+titleForContent);
		
		System.out.println("keyForSearch :  "+keyForSearch);
		System.out.println("desForSearch :  "+desForSearch);
		System.out.println("titleForSearch :  "+titleForSearch);
		
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
