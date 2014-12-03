package com.moviezone.service;

import java.util.List;

import com.moviezone.domain.Hotword;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.domain.Unword;
import com.moviezone.domain.Word;



public interface SearchService {
	public SearchResult search(String keyword, String type, int pageNo)throws Exception;
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo) throws Exception;
	public Page<Word> selectWord(Long id, String keyword, int pageNo);
	public Page<Unword> selectUnword(Long id, String keyword, int pageNo);
	public Page<Hotword> selectHotword(Long id, String keyword, int pageNo);
	public void saveWord(String keyword);
	public void saveUnword(String keyword);
	public void saveHotword(String keyword);
	public void deleteWord(long wordid);
	public void deleteUnword(long unwordid);
	public void deleteHotword(long hotwordid);
	public void reCreateIndex();
}
