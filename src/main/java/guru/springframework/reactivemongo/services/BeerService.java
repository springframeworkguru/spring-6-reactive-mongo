package guru.springframework.reactivemongo.services;

import guru.springframework.reactivemongo.model.BeerDTO;
import reactor.core.publisher.Mono;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerService {

    Mono<BeerDTO> saveBeer(BeerDTO beerDto);

    Mono<BeerDTO> getById(String beerId);
}
