package com.moviezone.service.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.constant.Constants;
import com.moviezone.dao.SearchDao;
import com.moviezone.domain.Hotword;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.domain.Unword;
import com.moviezone.domain.Word;
import com.moviezone.service.SearchService;

public class SearchServiceImpl implements SearchService{
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, String type, int pageNo)throws Exception {
		return searchDao.search(keyword, type, pageNo, 10);
	}

	@Override
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo)throws Exception {
		return searchDao.searchAsPage(keyword, type, pageNo, 10);
	}

	@Override
	public Page<Word> selectWord(Long id, String keyword, int pageNo) {
		keyword = StringUtils.isBlank(keyword)?null:"%"+keyword+"%";
		return searchDao.selectWord(id, keyword, pageNo, 20);
	}

	@Override
	public Page<Unword> selectUnword(Long id, String keyword, int pageNo) {
		keyword = StringUtils.isBlank(keyword)?null:"%"+keyword+"%";
		return searchDao.selectUnword(id, keyword, pageNo, 20);
	}
	
	@Override
	public Page<Hotword> selectHotword(Long id, String keyword, int pageNo) {
		keyword = StringUtils.isBlank(keyword)?null:"%"+keyword+"%";
		return searchDao.selectHotword(id, keyword, pageNo, 20);
	}
	
	@Override
	public void saveWord(String keyword) {
		if(StringUtils.isBlank(keyword))return;
		Page<Word> words = searchDao.selectWord(null, keyword, 1, 1);
		if(words.getTotal()>0){
			//更新
			Word oldWord = words.getData().get(0);
			oldWord.setKeyword(keyword);
			searchDao.update(oldWord);
		}else{
			//增加
			Word newWord = new Word();
			newWord.setKeyword(keyword);
			searchDao.insert(newWord);
		}
	}

	@Override
	public void saveUnword(String keyword) {
		if(StringUtils.isBlank(keyword))return;
		Page<Unword> words = searchDao.selectUnword(null, keyword, 1, 1);
		if(words.getTotal()>0){
			//更新
			Unword oldWord = words.getData().get(0);
			oldWord.setKeyword(keyword);
			searchDao.update(oldWord);
		}else{
			//增加
			Unword newWord = new Unword();
			newWord.setKeyword(keyword);
			searchDao.insert(newWord);
		}
	}
	
	@Override
	public void saveHotword(String keyword) {
		if(StringUtils.isBlank(keyword))return;
		keyword = keyword.trim();
		Page<Hotword> words = searchDao.selectHotword(null, keyword, 1, 1);
		for(Hotword word:words.getData()){
			searchDao.deleteHotword(word);
		}
		Hotword newWord = new Hotword();
		newWord.setKeyword(keyword);
		searchDao.insert(newWord);
	}
	
	@Override
	public void deleteWord(long wordid) {
		searchDao.deleteWord(wordid);
	}

	@Override
	public void deleteUnword(long unwordid) {
		searchDao.deleteUnword(unwordid);
	}
	
	@Override
	public void deleteHotword(long hotwordid) {
		searchDao.deleteHotword(hotwordid);
	}
	
	@Override
	public void reCreateIndex() {
		searchDao.reCreateIndex();
	}

	public void setSearchDao(SearchDao searchDao) {
		this.searchDao = searchDao;
	}

	

	

	
	
	
}
