package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Hotword;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.domain.Unword;
import com.moviezone.domain.Word;



public interface SearchDao {
	public void reCreateIndex();
	public void saveMoiveIndex(long movieid) throws Exception;
	public SearchResult search(String keyword, String type, int pageNo, int pageSize)throws Exception;
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo,int pageSize) throws Exception;
	public Page<Word> selectWord(Long id, String keyword, int pageNo, int pageSize);
	public Page<Unword> selectUnword(Long id, String keyword, int pageNo, int pageSize);
	public Page<Hotword> selectHotword(Long id, String keyword, int pageNo, int pageSize);
	public void insert(Word word);
	public void insert(Unword unword);
	public void insert(Hotword hotword);
	public void update(Word word);
	public void update(Unword unword);
	public void deleteWord(Word word);
	public void deleteWord(long wordid);
	public void deleteUnword(Unword unword);
	public void deleteUnword(long unwordid);
	public void deleteHotword(Hotword hotword);
	public void deleteHotword(long hotwordid);
	
	
}
