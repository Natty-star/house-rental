package cs590.accountservice.repository;

import cs590.accountservice.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AddressRepository extends MongoRepository<Address, String> {
    @Query("SELECT account.address from Account AS account where account.id=?1")
    Address findAddressByOwnerId(String id);
}
