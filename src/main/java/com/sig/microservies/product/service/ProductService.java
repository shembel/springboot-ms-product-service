package com.sig.microservies.product.service;

import com.sig.microservies.product.dto.ProductRequest;
import com.sig.microservies.product.dto.ProductResponse;
import com.sig.microservies.product.model.Product;
import com.sig.microservies.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

//    Standard way without using Lombok @RequiredArgsConstructor
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    // Here instead of returning product themselves, as its our domain class
    // we can create another DTO called ProductResponse
    // And we will map our product object to the product response object
    // So this: public Product createProduct(ProductRequest productRequest) {
    // becomes this:
    public ProductResponse createProduct(ProductRequest productRequest) {
        // logic to create Product object from productRequest
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        // save the Product object to repository
        productRepository.save(product);

        // comes from Slf4j annotation from Lombok
        log.info("Product created successfully");

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
