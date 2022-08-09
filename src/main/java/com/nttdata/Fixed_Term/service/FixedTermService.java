package com.nttdata.Fixed_Term.service;

import com.nttdata.Fixed_Term.models.Customer;
import com.nttdata.Fixed_Term.models.FixedTerm;
import reactor.core.publisher.Mono;

public interface FixedTermService {
    Mono<FixedTerm> findByCustomerId(String dni);
    Mono<FixedTerm> findById(String id);
    Mono<FixedTerm> save(FixedTerm fixedTerm);
    Mono<FixedTerm> update(FixedTerm fixedTerm);
    Mono<Boolean> delete(String id);
    Mono<Customer> findByDocument(String document);
}
