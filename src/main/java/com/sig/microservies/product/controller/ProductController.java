package com.sig.microservies.product.controller;

import com.sig.microservies.product.dto.ProductRequest;
import com.sig.microservies.product.dto.ProductResponse;
import com.sig.microservies.product.model.Product;
import com.sig.microservies.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController annotation in required because we're creating a REST controller
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // here in new versions of Java we can use not class but Record type
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        // here we need to write business logic to create a product
        // we can directly use repository from a controller - but it's not a good practice
        // because usually all the logic should be inside a service class, inside the service layer
        // so let's create a new service class

        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}
