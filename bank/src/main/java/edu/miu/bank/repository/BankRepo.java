package edu.miu.bank.repository;

import edu.miu.bank.entity.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends MongoRepository<Bank,String> {
}
