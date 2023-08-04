package com.diptopaul.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.api.exceptions.ApiException;

public interface FileService {
	Object uploadFile(String uploadDir, MultipartFile fileReceived) throws IOException, ApiException;
	InputStream getFile(String uploadDir, String fileName) throws FileNotFoundException;
}
