package com.vitu.test.service.impl;

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

import com.vitu.test.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(MultipartFile file, String path) throws IOException {

		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		
		String fileName=UUID.randomUUID().toString();
		String fileNameWithExtension=fileName+extension;
		
		String fullPath=path+fileNameWithExtension;
		
		
		if(extension.equalsIgnoreCase(".jpg")|| extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpeg")) {
			
			//save
			File folder=new File(path);
	
			if(!folder.exists()) {
				folder.mkdirs();//sub folder
			}
			
			//upload
			
			Files.copy(file.getInputStream(), Paths.get(fullPath));
			
			
		}
		else {
			System.out.println("Inavlid File ");
		}
		
		
		
		
		return fileNameWithExtension;
	}

	@Override
	public InputStream serveImage(String name, String path) throws FileNotFoundException {

String fullPath=name+File.separator+path;

InputStream inputStream=new FileInputStream(fullPath);
		
		return inputStream;
	}

}
