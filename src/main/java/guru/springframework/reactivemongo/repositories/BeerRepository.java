package guru.springframework.reactivemongo.repositories;

import guru.springframework.reactivemongo.domain.Beer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerRepository extends ReactiveMongoRepository<Beer, String> {
}
