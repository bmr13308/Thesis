package com.fitnest.posts.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitnest.posts.model.Comment;
import com.fitnest.posts.model.CommentReq;
import com.fitnest.posts.model.Comments;
import com.fitnest.posts.model.EditComment;
import com.fitnest.posts.model.Post;
import com.fitnest.posts.model.PostEdit;
import com.fitnest.posts.model.PostResponse;
import com.fitnest.posts.model.Posts;
import com.fitnest.posts.model.UserIdList;
import com.fitnest.posts.model.like.PostLike;
import com.fitnest.posts.repo.CommentRepositroy;
import com.fitnest.posts.repo.LikeRepository;
import com.fitnest.posts.repo.PostRepository;
import com.fitnest.posts.service.JwtUtil;
import com.fitnest.posts.service.FileService;

@RestController
@RequestMapping("/PostService")
public class PostsController {
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	CommentRepositroy commentRepo;
	
	@Autowired
	FileService imgService;

	@Autowired
	JwtUtil jwt;
	
	@Autowired
	LikeRepository likeRepo;

	@PostMapping("/createPost")
	public ResponseEntity<String> createPost(@RequestHeader String Authorization,@RequestParam MultipartFile imageFile,@RequestParam String text) throws Exception{
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		
		String path;
		try {
			path = imgService.saveFile(imageFile);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Service error",HttpStatus.SERVICE_UNAVAILABLE);
		}
		Post post = new Post(userId,text,path);
		postRepo.save(post);
		return ResponseEntity.ok("ok");
	}
	@GetMapping("/getPost/{id}")
	public ResponseEntity<?> getPost(@PathVariable int id) throws IOException{
		try {
			Post post = postRepo.getOne(id);

			return new ResponseEntity<PostResponse>(new PostResponse(post), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
		}
		
	}
	
	@DeleteMapping("/deletePost/{id}")
	public ResponseEntity<String> deletePost(@RequestHeader String Authorization,@PathVariable int postId){
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		Post post = postRepo.getOne(postId);
		if(post.getUserId() == userId) {
			imgService.deleteFile(post.getImageSource());
			postRepo.deleteById(postId);
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
		}
		
		return ResponseEntity.ok("ok");
	}
	@PutMapping("/editPost")
	public ResponseEntity<String> editPost(@RequestHeader String Authorization,@RequestBody PostEdit postEdit){
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token); 
		Post post = postRepo.getOne(postEdit.getId());
		
		if(post.getUserId() == userId) {
			Post editedPost = new Post(post.getId(),userId,postEdit.getText(),post.getImageSource());
			postRepo.save(editedPost);
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
		}
		
		return ResponseEntity.ok("ok");
	}
		
	
	@GetMapping("/getPosts/{userId}")
	public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable int userId){
		return ResponseEntity.ok(postRepo.findByUserIdOrderByTimestampDesc(userId));
		
	}
	
	
	
	@PostMapping("/getposts")
	public ResponseEntity<Posts> getPostsByUserList(@RequestBody UserIdList userIdList){
		
		List<Integer> list = Arrays.asList(0,0,0);
		list = userIdList.getUserIdList();
		
		List<Post> posts = postRepo.findByUserIdInOrderByTimestampDesc(list);
	
		
		return ResponseEntity.ok(new Posts(posts));
		
	}
	
	
	
	@PostMapping("/createComment")
	public ResponseEntity<String> insertComment(@RequestHeader String Authorization,@RequestBody CommentReq commentReq){
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);

		int postId = commentReq.getPostId(); 
		Optional<Post> post = postRepo.findById(postId);
		
		if(!post.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
		
		Comment comment = new Comment();
		comment.setUserId(userId);
		comment.setPostId(postId);
		comment.setText(commentReq.getText());
		
		
		commentRepo.save(comment);

		return ResponseEntity.ok("Success");
		
	}
	
	@PutMapping("/editComment/{commentId}")
	public ResponseEntity<String> updateComment(@RequestHeader String Authorization,@PathVariable int commentId,@RequestBody EditComment commentReq){
		
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		
		Optional<Comment> comment = commentRepo.findById(commentId);
		if(!comment.isPresent())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
		if(comment.get().getUserId() != userId) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not own this comment"); 
		
		Comment editedComment = comment.get();
		editedComment.setText(commentReq.getText());
		
		commentRepo.save(editedComment);
		return ResponseEntity.ok("Success");
		
	}
	
	@DeleteMapping("/removeComment/{commentId}")
	public ResponseEntity<String> deleteComment(@RequestHeader String Authorization,@PathVariable int commentId){
		
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);
		
		Optional<Comment> comment = commentRepo.findById(commentId);
		if(!comment.isPresent())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
		if(comment.get().getUserId() != userId) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not own this comment"); 
		else
		commentRepo.deleteById(commentId);
		return ResponseEntity.ok("Success");
		
	}
	
	@GetMapping("/getComments/{postId}")
	public ResponseEntity<Comments> getCommentsByPostId(@PathVariable int postId){
		
		Post post = new Post();
		post.setId(postId);
		List<Comment> comments = commentRepo.findByPostIdOrderByTimestampDesc(postId);
		return ResponseEntity.ok(new Comments(comments));
		
	}
	
	@GetMapping("/getComment/{commentId}")
	public ResponseEntity<?> getCommentById(@PathVariable int commentId){

		Optional<Comment> comment = commentRepo.findById(commentId);		
		if(!comment.isPresent())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
		return ResponseEntity.ok(comment.get());
		
	}
	
	@GetMapping("/getPostLikes/{postId}")
	public ResponseEntity<?> getPostLikes(@PathVariable int postId){
		Optional<Post> post = postRepo.findById(postId);
		if(!post.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");

		UserIdList userIdList = new UserIdList();
		userIdList.setUserIdList(likeRepo.findAllUserIdByPostId(postId));	
		return ResponseEntity.ok(userIdList);
		
	}
	
	
	
	@PostMapping("/likeToggle/{postId}")
	public ResponseEntity<?> likeComment(@RequestHeader String Authorization,@PathVariable int postId){
		String token = Authorization.split(" ")[1];
		int userId = jwt.extractSubjectId(token);

		Optional<Post> post = postRepo.findById(postId);
		
		if(!post.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
		Post actualPost = post.get();
		
		Optional<PostLike> like = likeRepo.findOneByUserIdAndPostId(userId, postId);
		
		if(like.isPresent()) {
			likeRepo.deleteById(like.get().getId());
			actualPost.setLikes(actualPost.getLikes()-1);
			postRepo.save(actualPost);
		}else {
			PostLike newlike = new PostLike(postId,userId);
			likeRepo.save(newlike);
			actualPost.setLikes(actualPost.getLikes()+1);
			postRepo.save(actualPost);
			

		}
		
		return ResponseEntity.ok("Success");

	}
	
	
	
	
	
	

	
	
}
