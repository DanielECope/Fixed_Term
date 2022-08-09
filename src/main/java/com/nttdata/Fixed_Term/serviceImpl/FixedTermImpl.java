package com.nttdata.Fixed_Term.serviceImpl;

import com.nttdata.Fixed_Term.models.Customer;
import com.nttdata.Fixed_Term.models.FixedTerm;
import com.nttdata.Fixed_Term.models.TypeCustomer;
import com.nttdata.Fixed_Term.repository.FixedTermRepository;
import com.nttdata.Fixed_Term.service.FixedTermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Component
@Slf4j
public class FixedTermImpl implements FixedTermService {
    private final WebClient webClientCustomer= WebClient.create("http://localhost:8080/customer");

    @Autowired
    private FixedTermRepository repository;

    @Override
    public Mono<FixedTerm> findByCustomerId(String dni) {
        return null;
    }

    @Override
    public Mono<FixedTerm> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<FixedTerm> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<FixedTerm> save(FixedTerm fixedTerm) {
        log.info("FixedTerm: implements save() method : {}",fixedTerm.toString());

        fixedTerm.setRegistration_date(LocalDateTime.now());
        fixedTerm.setMovement_limit(1);
        fixedTerm.setCommission(0);

        Mono<Customer> customer = findByDocument(fixedTerm.getCustomer().getDocument());
        customer.switchIfEmpty(Mono.error(new Exception("customer does not exist"))).subscribe();
        Customer client =new Customer();
        customer.map(c->{

            client.setName(c.getName());
            client.setLastName(c.getLastName());
            client.setId(c.getId());
            client.setTypeCustomer(c.getTypeCustomer());
            client.setDocument(c.getDocument());
            fixedTerm.setCustomer(client);
            return c;
        }).subscribe();
        Mono<FixedTerm> obj=repository.findByCustomerDocument(fixedTerm.getCustomer().getDocument());
        obj
                .map(c->{
                    if (c.getCustomer().getTypeCustomer()== TypeCustomer.PERSONAL){
                        throw new RuntimeException("The customer already has an account. Customer has a "+ TypeCustomer.PERSONAL+" account");
                        //El cliente ya tiene una cuenta. Cliente tiene una cuenta Peronsal
                    } else if (c.getCustomer().getTypeCustomer() == TypeCustomer.EMPRESARIAL) {
                        throw new RuntimeException("Business customers cannot have Fixed Term");
                        //Los clientes empresariales no puede tener cuentas de ahorro
                    }
                    return c;
                })
                .switchIfEmpty(obj=repository.save(fixedTerm))
                .subscribe(c->{
                    log.info(c.toString());
                });
        return obj;
    }

    @Override
    public Mono<FixedTerm> update(FixedTerm fixedTerm) {
        return null;
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return  repository.findById(id)
                .flatMap(obj -> repository.delete(obj)
                        .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Customer> findByDocument(String document) {
        log.info("FixedTerm: implements findByDocument() method : {}",document);
        return webClientCustomer.get().uri("/findDcoument/{document}",document)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Customer.class);
    }
}
