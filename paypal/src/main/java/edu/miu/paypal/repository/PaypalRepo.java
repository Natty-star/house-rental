package edu.miu.paypal.repository;

import edu.miu.paypal.entity.Paypal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaypalRepo extends MongoRepository<Paypal,String> {
}
