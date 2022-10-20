package cs590.accountservice.repository;

import cs590.accountservice.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccountRepository extends MongoRepository<Account, String> {
    public Account findAccountByEmailAndPassword(String email, String password);

    public Account findAccountByEmail(String email);

    public boolean deleteAccountByEmail(String email);
}
