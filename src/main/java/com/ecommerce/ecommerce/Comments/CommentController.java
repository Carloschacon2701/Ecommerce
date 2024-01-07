package com.ecommerce.ecommerce.Comments;

import com.ecommerce.ecommerce.DTO.CommentCreation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

     @PostMapping(path = "/add")
        public ResponseEntity<?> addCommentToProduct(@RequestBody  @Valid CommentCreation request){
             return new ResponseEntity<>(commentService.addCommentToProduct(request.getText(), request.getProduct()), HttpStatus.CREATED);
         }
}
