package edu.miu.property.repository;

import edu.miu.property.model.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo extends MongoRepository<Property,String> {

}
