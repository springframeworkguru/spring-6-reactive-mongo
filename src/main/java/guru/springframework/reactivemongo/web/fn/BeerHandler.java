package guru.springframework.reactivemongo.web.fn;

import guru.springframework.reactivemongo.model.BeerDTO;
import guru.springframework.reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BeerHandler {
    private final BeerService beerService;

    public Mono<ServerResponse> createNewBeer(ServerRequest request){
        return beerService.saveBeer(request.bodyToMono(BeerDTO.class))
                .flatMap(beerDTO -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(BeerRouterConfig.BEER_PATH_ID)
                                .build(beerDTO.getId()))
                        .build());
    }


    public Mono<ServerResponse> getBeerById(ServerRequest request){
        return ServerResponse
                .ok()
                .body(beerService.getById(request.pathVariable("beerId")), BeerDTO.class);
    }

    public Mono<ServerResponse> listBeers(ServerRequest request){
        return ServerResponse.ok()
                .body(beerService.listBeers(), BeerDTO.class);
    }
}
