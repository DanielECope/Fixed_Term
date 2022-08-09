package com.nttdata.Fixed_Term.repository;

import com.nttdata.Fixed_Term.models.Customer;
import com.nttdata.Fixed_Term.models.FixedTerm;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FixedTermRepository extends ReactiveMongoRepository<FixedTerm,String> {
    public Mono<FixedTerm> findByCustomerDocument(String document);
    public Mono<FixedTerm> findByAccountNumber(String accountNumber);
}
