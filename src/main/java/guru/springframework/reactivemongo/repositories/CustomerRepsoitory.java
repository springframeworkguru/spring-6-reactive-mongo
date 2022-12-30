package guru.springframework.reactivemongo.repositories;

import guru.springframework.reactivemongo.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface CustomerRepsoitory extends ReactiveMongoRepository<Customer, String> {
}
