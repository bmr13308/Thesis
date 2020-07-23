package com.fitnest.fileservice.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	
	final String PHOTOFOLDER = "/FitNest/PostPhotos/";
	final String URL = "http://localhost";
			
	
	public String saveFile(MultipartFile file) throws Exception {
		byte[] bytes = file.getBytes();
		String imageName = "image" + ZonedDateTime.now().toInstant().toEpochMilli() + ".jpg";
		Path path = Paths.get(PHOTOFOLDER + imageName);
		Files.write(path, bytes);
		return URL + ":" + "8095" + "/" + imageName;
	}

	public void deleteFile(String name) throws IOException {
		Path path = Paths.get(PHOTOFOLDER + name);
		Files.deleteIfExists(path);
	}

}
