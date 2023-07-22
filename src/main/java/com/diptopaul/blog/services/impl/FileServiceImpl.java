package com.diptopaul.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.diptopaul.blog.services.FileService;
import com.diptopaul.blog.exceptions.UnsupportedFileTypeException;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(String uploadDirPath, MultipartFile fileReceived) throws IOException {
		if (!fileReceived.isEmpty()) {
	        if (fileReceived.getContentType().startsWith("image/")) {
	        	//file name
	        	String fileOrginalName = fileReceived.getOriginalFilename();
	        	
	        	//generate a randomID
	    		String randomId = UUID.randomUUID().toString();
	    		//make the filename somewhat unique by attaching the randomId
	    		String newFileName = randomId+fileOrginalName.substring(fileOrginalName.lastIndexOf("."));
	    		
	    		//make the full path
	    		String fullFilePath = uploadDirPath + newFileName;
	    		
	    		//create a directory. we might create a directory.
	    		File f = new File(uploadDirPath);
	    		//make a directory if not created
	    		if(!f.exists()) {
	    			f.mkdirs();
	    		}
	    		
	    		//now copy the data from fileReceived to the new fullFilePath
	    		Files.copy(fileReceived.getInputStream(), Paths.get(fullFilePath));
	    		return newFileName;
	        } else {
	            // Throw an exception for unsupported file types
	        	//this can be handled making an annotation
	            throw new UnsupportedFileTypeException("Only image files are allowed.");
	        }
	    } else {
	    	
	        return "No file selected.";
	    }
	}

	@Override
	public InputStream getFile(String uploadDir, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(uploadDir);
		//make the full path
		String fullFilePath = uploadDir + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullFilePath);
		return inputStream;
	}

}
