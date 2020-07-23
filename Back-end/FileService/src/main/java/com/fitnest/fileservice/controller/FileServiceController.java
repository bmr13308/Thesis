package com.fitnest.fileservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitnest.fileservice.model.FilePath;
import com.fitnest.fileservice.service.AuthorizeService;
import com.fitnest.fileservice.service.FileService;

@RestController
public class FileServiceController {
	
	@Autowired
	FileService fileService;
	
	@Autowired
	AuthorizeService authService;
	
	@PostMapping("/savefile")
	public ResponseEntity<String> saveFile(@RequestHeader String password,@RequestParam MultipartFile file) throws Exception{
		
		if(!authService.check(password)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
		
		
		String path = fileService.saveFile(file);
		
		return new ResponseEntity<String>(path,HttpStatus.OK);
	}
	
	@PostMapping("/deletefile")
	public ResponseEntity<String> deleteFile(@RequestHeader String password,@RequestBody FilePath filePath) throws Exception{
		
		if(!authService.check(password)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
		
		String[] arr = filePath.getPath().split("/");
		String name = arr[arr.length -1];
		fileService.deleteFile(name);
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
}
