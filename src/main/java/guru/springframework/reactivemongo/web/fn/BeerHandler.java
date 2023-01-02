package guru.springframework.reactivemongo.web.fn;

import guru.springframework.reactivemongo.model.BeerDTO;
import guru.springframework.reactivemongo.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BeerHandler {
    private final BeerService beerService;
    private final Validator validator;

    private void validate(BeerDTO beerDTO){
        Errors errors = new BeanPropertyBindingResult(beerDTO, "beerDto");
        validator.validate(beerDTO, errors);

        if (errors.hasErrors()){
            throw new ServerWebInputException(errors.toString());
        }
    }

    public Mono<ServerResponse> deleteBeerById(ServerRequest request){
        return beerService.getById(request.pathVariable("beerId"))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(beerDTO -> beerService.deleteBeerById(beerDTO.getId()))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> patchBeerById(ServerRequest request){
        return request.bodyToMono(BeerDTO.class)
                .doOnNext(this::validate)
                .flatMap(beerDTO -> beerService
                        .patchBeer(request.pathVariable("beerId"),beerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateBeerById(ServerRequest request) {
        return request.bodyToMono(BeerDTO.class)
                .doOnNext(this::validate)
                .flatMap(beerDTO -> beerService
                        .updateBeer(request.pathVariable("beerId"), beerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDto -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createNewBeer(ServerRequest request){
        return beerService.saveBeer(request.bodyToMono(BeerDTO.class).doOnNext(this::validate))
                .flatMap(beerDTO -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(BeerRouterConfig.BEER_PATH_ID)
                                .build(beerDTO.getId()))
                        .build());
    }


    public Mono<ServerResponse> getBeerById(ServerRequest request){
        return ServerResponse
                .ok()
                .body(beerService.getById(request.pathVariable("beerId"))
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))),
                        BeerDTO.class);
    }

    public Mono<ServerResponse> listBeers(ServerRequest request){
        Flux<BeerDTO> flux;

        if (request.queryParam("beerStyle").isPresent()){
            flux = beerService.findByBeerStyle(request.queryParam("beerStyle").get());
        } else {
            flux = beerService.listBeers();
        }

        return ServerResponse.ok()
                .body(flux, BeerDTO.class);
    }
}

















