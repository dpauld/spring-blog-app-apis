package com.diptopaul.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.diptopaul.blog.services.FileService;

import lombok.AllArgsConstructor;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import com.diptopaul.blog.exceptions.UnsupportedFileTypeException;

@AllArgsConstructor
@Service
public class FileServiceImpl implements FileService{
	private Cloudinary cloudinary;
	
	@Override
	public Object uploadFile(String folderName, MultipartFile fileReceived) throws IOException, ApiException {
		if (!fileReceived.isEmpty()) {
	        if (fileReceived.getContentType().startsWith("image/")) {
	        	byte[] imageFileByte = null;
				Map response = null;
				
				//imageFileByte = imageFileReceived.getBytes();
				try {
					imageFileByte = StreamUtils.copyToByteArray(fileReceived.getInputStream());
					
					Map params = null;
							        
			        //upload the image, Cloudinary upload will create a folder with the givenName if not exists
					params = ObjectUtils.asMap("return_delete_token", true, "folder", folderName);
					response = cloudinary.uploader().upload(imageFileByte, params);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new ApiException("Something went wrong.");
				}
				return response.get("secure_url");
	        } else {
	            // Throw an exception for unsupported file types
	            throw new UnsupportedFileTypeException("Only image files are allowed.");
	        }
	    } else {
	    	// Throw an exception for unsupported file types
            throw new UnsupportedFileTypeException("Only image files are allowed.");
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
	
	/** Old Image Upload in the File System**/
//	@Deprecated
//	@Override
//	public String uploadFile(String uploadDirPath, MultipartFile fileReceived) throws IOException {
//		if (!fileReceived.isEmpty()) {
//	        if (fileReceived.getContentType().startsWith("image/")) {
//	        	//file name
//	        	String fileOrginalName = fileReceived.getOriginalFilename();
//	        	
//	        	//generate a randomID
//	    		String randomId = UUID.randomUUID().toString();
//	    		//make the filename somewhat unique by attaching the randomId
//	    		String newFileName = randomId+fileOrginalName.substring(fileOrginalName.lastIndexOf("."));
//	    		
//	    		//make the full path
//	    		String fullFilePath = uploadDirPath + newFileName;
//	    		
//	    		//create a directory. we might create a directory.
//	    		File f = new File(uploadDirPath);
//	    		//make a directory if not created
//	    		if(!f.exists()) {
//	    			f.mkdirs();
//	    		}
//	    		
//	    		//now copy the data from fileReceived to the new fullFilePath
//	    		Files.copy(fileReceived.getInputStream(), Paths.get(fullFilePath));
//	    		return newFileName;
//	        } else {
//	            // Throw an exception for unsupported file types
//	        	//this can be handled making an annotation
//	            throw new UnsupportedFileTypeException("Only image files are allowed.");
//	        }
//	    } else {
//	    	
//	        return "No file selected.";
//	    }
//	}
//
//	@Deprecated
//	@Override
//	public InputStream getFile(String uploadDir, String fileName) throws FileNotFoundException {
//		// TODO Auto-generated method stub
//		System.out.println(uploadDir);
//		//make the full path
//		String fullFilePath = uploadDir + File.separator + fileName;
//		InputStream inputStream = new FileInputStream(fullFilePath);
//		return inputStream;
//	}
}
