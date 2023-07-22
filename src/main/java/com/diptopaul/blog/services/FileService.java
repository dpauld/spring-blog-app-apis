package com.diptopaul.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	String uploadFile(String uploadDir, MultipartFile fileReceived) throws IOException;
	InputStream getFile(String uploadDir, String fileName) throws FileNotFoundException;
}
