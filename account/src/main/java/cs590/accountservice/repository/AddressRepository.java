package cs590.accountservice.repository;

import cs590.accountservice.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AddressRepository extends MongoRepository<Address, String> {
}
