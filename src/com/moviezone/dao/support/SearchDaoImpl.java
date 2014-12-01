package com.moviezone.dao.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.facet.Facets;
import org.apache.lucene.facet.FacetsCollector;
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
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.moviezone.dao.MovieDao;
import com.moviezone.dao.SearchDao;
import com.moviezone.domain.Movie;
import com.moviezone.domain.MovieIndex;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.service.KeyService;
import com.moviezone.service.SearchService;
import com.moviezone.util.HttpUtil;

public class SearchDaoImpl implements SearchDao{
	private static final Logger logger = LoggerFactory.getLogger(SearchDaoImpl.class);
	private final String FieldType         = "type";
	private final String FieldID             = "id";
	private final String FieldName       = "name";
	private final String FieldShortDesc = "short_desc";
	private final String FieldLongDesc  = "long_desc";
	private final String FieldCreateTime= "create_time";
	
	//IKAnalyzer ...
	private Dictionary dictionary;
	private Analyzer analyzer;
	//lucene
	private Directory directory;
	private IndexWriterConfig writerConfig;
	private IndexWriter iWriter;
	private DirectoryReader iReader;
	private IndexSearcher iSearcher;
	private MultiFieldQueryParser parser;
	private SortedSetDocValuesReaderState readerState;
	private FacetsConfig facetsConfig;
	private Sort sortFields;
	private Document nullDoc = new Document();
	
	private SqlSession session;
	
	public SearchDaoImpl() throws Exception{
		logger.debug("正在初始化lucene字典 . . .");
		Dictionary.initial(DefaultConfig.getInstance());          //IKAnalyzer数据字典初始化
		
		dictionary    = Dictionary.getSingleton();
		analyzer       = new IKAnalyzer();
		directory      = new RAMDirectory();
		//directory      = NIOFSDirectory.open(new File("D:/FullSearchData"));
		writerConfig = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		facetsConfig = new FacetsConfig();
		nullDoc         = new Document();nullDoc.add(new SortedSetDocValuesFacetField(FieldType,"null"));
		iWriter          = new IndexWriter(directory, writerConfig);iWriter.addDocument(facetsConfig.build(nullDoc));iWriter.commit();
		
		
		parser           = new MultiFieldQueryParser(new String[]{FieldName,FieldShortDesc,FieldLongDesc},analyzer);
		
		sortFields      = new Sort(SortField.FIELD_SCORE,new SortField(FieldCreateTime,SortField.Type.LONG,true)); //先按score排序，再按时间排序
		
		dictionary.addWords(Collections.EMPTY_LIST);         //加入主字典（扩展字典）
		dictionary.disableWords(Collections.EMPTY_LIST);    //加入stop字典
		logger.debug("完成初始化lucene字典 . . .");
		
		
		logger.debug("创建后台线程将movie表加入lucene内存索引中...");
		Thread cmmd = new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					int total =0;
					int start = -10000;
					int size  = 10000;
					logger.debug("后台创建lucenu线程开始执行...size："+size);
					while(true){
						Thread.sleep(500);
						start = start + size;
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("start", start);
						param.put("size", size);
						List<MovieIndex> movies = session.selectList("selectMovieIndex", param);
						total = total + movies.size();
						index(movies);
						logger.debug("正在完成lucene索引创建工作...  "+start+"~"+(start+size-1));
						if(movies.size()<size)break;
					}
					logger.debug("全部完成lucene索引创建工作... total："+total);
				}catch(Exception e){
					logger.debug("",e);
				}
			}
		});
		cmmd.setDaemon(true);
		cmmd.start();
		
	}
	
	public void index(Movie movie) throws Exception{
	
	    iWriter.commit();
	    
	}
	
	public void index(List<MovieIndex> movieIndexes) throws Exception{
		for(MovieIndex i:movieIndexes){
			index(i.getModname(),i.getMovieid()+"",i.getName(),i.getCreatetime(),i.getShortdesc(),i.getLongdesc());
		}
	    iWriter.commit();
		//重新打开reader否则新加入的索引不会被搜索到
	    iReader         = DirectoryReader.open(directory);
		iSearcher      = new IndexSearcher(iReader);
		readerState   = new DefaultSortedSetDocValuesReaderState(iReader);
	}
	
	private void index(String type,String movieid,String name,Date createTime,String shortDesc,String longDesc) throws Exception{
		if(StringUtils.isBlank(type))return ;
		if(StringUtils.isBlank(movieid))return ;
		if(StringUtils.isBlank(name))return ;
		if(createTime==null)return;
		Document doc = new Document();
	    doc.add(new SortedSetDocValuesFacetField(FieldType,type));
	    doc.add(new StringField(FieldID, movieid, Field.Store.YES));
	    doc.add(new Field(FieldName, name, TextField.TYPE_NOT_STORED));
	    doc.add(new LongField(FieldCreateTime,createTime.getTime(),LongField.TYPE_NOT_STORED));
	    if(StringUtils.isNotBlank(shortDesc))doc.add(new Field(FieldShortDesc, shortDesc, TextField.TYPE_NOT_STORED));
	    if(StringUtils.isNotBlank(longDesc))doc.add(new Field(FieldLongDesc, longDesc, TextField.TYPE_NOT_STORED));
	    iWriter.addDocument(facetsConfig.build(doc));
	}
	
	@Override
	public SearchResult search(String keyword,String type,int pageNo,int pageSize) throws Exception{
		SearchResult sr = new SearchResult();
		if(StringUtils.isBlank(keyword))return sr;
		
		logger.debug("【Search】 keyoword ："+keyword);
		long before = System.currentTimeMillis();
		
		//查出所有facet
		Query query             = parser.parse(HttpUtil.filterSearchForLucene(keyword));
		List<String[]> facets = getAllFacet(query);
		if(facets.size()<1)return sr;
		String matchType = facets.get(0)[0];
		for(String[] facet:facets){
			if(facet[0].equals(type))matchType = facet[0];
		}
		//查出所有数据
		sr.setKeyword(keyword);
		sr.setMatchType(matchType);
		sr.setTypes(facets);
		sr.setMovies(searchAsPage(query,matchType,pageNo,pageSize));
		
	    long cost = System.currentTimeMillis()-before;
	    logger.debug("【Search Over】cost:"+cost+" keyoword ："+keyword);
		return sr;
	}
	
	@Override
	public Page<Movie> searchAsPage(String keyword,String type,int pageNo,int pageSize) throws Exception{
		if(StringUtils.isBlank(keyword))return new Page<Movie>();
		if(StringUtils.isBlank(type))return new Page<Movie>();
		Query query = parser.parse(HttpUtil.filterSearchForLucene(keyword));
		return searchAsPage(query,type,pageNo,pageSize);
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	private Page<Movie> searchAsPage(Query query,String type,int pageNo,int pageSize) throws Exception{
		Page<Movie> page   = new Page<Movie>();
		Object[] allMovieids    = getAllMovieid(query,type,pageNo,pageSize);
		Set<String> movieIds = (Set)allMovieids[0];
		int totalHits                 = (Integer)allMovieids[1];
		page.setTotal(totalHits);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(selectMovie(StringUtils.join(movieIds, ',')));
		return page;
	}
	
	private List<String[]> getAllFacet(Query query) throws Exception{
		List<String[]> datas = new ArrayList<String[]>();
		FacetsCollector fc = new FacetsCollector();
		FacetsCollector.search(iSearcher, query, null, 1, sortFields,true,true,fc);
		Facets facets = new SortedSetDocValuesFacetCounts(readerState, fc);
		FacetResult  fr = facets.getTopChildren(1000, FieldType);
		if(fr==null)return datas;
		for(LabelAndValue labelValue:facets.getTopChildren(1000, FieldType).labelValues){
				logger.debug("facet match ： "+labelValue );
		    	String[] data = new String[]{labelValue.label,labelValue.value+""};
		    	datas.add(data);
		}
		
		return datas;
	}
	
	private Object[] getAllMovieid(Query query,String type,int pageNo,int pageSize) throws Exception{
		Object[] result = new Object[2];
		Set<String> datas = new LinkedHashSet<String>();
		FacetsCollector fc = new FacetsCollector();
		DrillDownQuery dataQuery = new DrillDownQuery(facetsConfig,query);
		TopFieldDocs topDocs = FacetsCollector.search(iSearcher, dataQuery, null, pageNo*pageSize, sortFields,true,true,fc);
		ScoreDoc[] hitDocs      = topDocs.scoreDocs;
		int start = Math.max((pageNo-1)*pageSize, 0);
		int end  = Math.min(pageNo*pageSize, hitDocs.length);
		logger.debug("total  ： "+topDocs.totalHits);
		for (int i = start; i < end; i++) {
			  Document hitDoc = iSearcher.doc(hitDocs[i].doc);
			  logger.debug("id match ： "+hitDoc.get(FieldID)+" ---> "+hitDocs[i].score);
			  datas.add(hitDoc.get(FieldID));
		}
		result[0]=datas;
		result[1]=topDocs.totalHits;
		return result;
	}
	
	private List<Movie> selectMovie(String movieids) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("movieids", movieids);
		return session.selectList("selectSearchMovie", param);
	}
}
