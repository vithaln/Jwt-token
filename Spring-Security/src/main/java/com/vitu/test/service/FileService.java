package com.vitu.test.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	
	String uploadImage(MultipartFile file,String path) throws IOException;
	
	InputStream serveImage(String name,String path) throws FileNotFoundException;
}
