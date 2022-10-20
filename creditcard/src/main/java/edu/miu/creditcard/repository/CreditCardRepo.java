package edu.miu.creditcard.repository;

import edu.miu.creditcard.entity.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepo extends MongoRepository<CreditCard,String> {

}
