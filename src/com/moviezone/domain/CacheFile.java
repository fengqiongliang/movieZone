package com.moviezone.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.moviezone.constant.Constants;
import com.moviezone.util.FileUtil;
import com.moviezone.util.FileUtil.FileType;

public class CacheFile implements Serializable{
	private static final long serialVersionUID = -494037651708381235L;
	private String name;
	private long   lastModifyTime;
	private byte[] data;
	private File path;
	private FileType fileType = FileType.unknown;
	public CacheFile(String name,File path) throws Exception{
		this.name = name;
		gainFileData(path);
	}
	public String getName() {
		return name;
	}
	public byte[] getData() throws IOException {
		if(!FileUtil.isFileNewer(path, lastModifyTime))return data;
		System.out.println("going to find new File");
		gainFileData(path);
		return data;
	}
	public boolean fileExist(){
		if(path==null)return false;
		return path.exists();
	}
	public FileType getFileType(){
		return fileType;
	}
	
	public long getLastModify(){
		return path.lastModified();
	}
	
	private void gainFileData(File path) throws IOException{
		this.path     = path;
		this.data     = FileUtil.readFileToByteArray(path);
		byte[] head = new byte[2];
		head[0] = data[0];
		head[1] = data[1];
		this.fileType = FileUtil.getFileType(head);
		this.lastModifyTime = path.lastModified();
	}
	
	
}
