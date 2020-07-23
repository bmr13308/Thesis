package com.fitnest.posts.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fitnest.posts.model.FilePath;
import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;

@Service
public class FileService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${FILESERVICE_SECRET_KEY}")
	private String servicePassword;
	final String SERVICEURL = "http://FileService";
	
	public String saveFile(MultipartFile file) throws Exception {
		  MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
	      bodyMap.add("file", new FileSystemResource(convert(file)));

	      
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	      headers.add("password",servicePassword );
	      
	      
	      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
	   
	      ResponseEntity<String> path = restTemplate.postForEntity(SERVICEURL + "/savefile",requestEntity, String.class);
		return path.getBody();
	}
	
	
	 public static File convert(MultipartFile file) throws IOException
	  {    
	    File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	 }
	 
	 public void deleteFile(String path) {
		 FilePath filePath = new FilePath(path);

	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.APPLICATION_JSON);
	      headers.add("password",servicePassword );
	      
	      HttpEntity<FilePath>  request = 
	    	      new HttpEntity<>(filePath,headers);
	      
 
		 restTemplate.postForObject(SERVICEURL + "/deletefile" , request, String.class );

	 }

}
