package cs590.accountservice.repository;

import cs590.accountservice.entity.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, Integer> {
}
