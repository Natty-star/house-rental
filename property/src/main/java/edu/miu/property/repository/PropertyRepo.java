package edu.miu.property.repository;

import edu.miu.property.model.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo extends MongoRepository<Property,String> {
    List<Property> findByUserEmail(String userEmail);
}
