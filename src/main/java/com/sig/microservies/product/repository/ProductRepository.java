package com.sig.microservies.product.repository;

import com.sig.microservies.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

// two generic arguments - the first is Product and the second is String because it correlates with the @Id field in a Product class
public interface ProductRepository extends MongoRepository<Product, String> {
}
