package com.ecommerce.ecommerce.Comments;

import com.ecommerce.ecommerce.products.Product;
import com.ecommerce.ecommerce.products.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public String addCommentToProduct(String text, Product product){
        var productFromDB = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        var comment = Comment.builder()
                .text(text)
                .product(productFromDB)
                .build();

        commentRepository.save(comment);

        return "Comment added";
    }

}
