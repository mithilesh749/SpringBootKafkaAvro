package com.spingboot.kafka.repository;

import com.spingboot.kafka.models.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends MongoRepository<ProductModel, Long> {
}
