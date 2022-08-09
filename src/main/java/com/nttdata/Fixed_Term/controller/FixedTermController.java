package com.nttdata.Fixed_Term.controller;

import com.nttdata.Fixed_Term.models.FixedTerm;
import com.nttdata.Fixed_Term.service.FixedTermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/FixedTerm")
public class FixedTermController {
    @Autowired
    FixedTermService service;
    @GetMapping("/list")
    public Flux<FixedTerm> list()
    {
        return service.findAll();
    }

    @GetMapping("/list/{id}")
    public Mono<FixedTerm> findById(@PathVariable String id)
    {
        log.info("Saving_account: controller findById() method ");
        return service.findById(id);
    }

    @PostMapping(path = "/create")
    public Mono<FixedTerm> create(@RequestBody FixedTerm fixedTerm)
    {
        log.info("FixedTerm: controller create() method : {}",fixedTerm.toString());
        return service.save(fixedTerm);
    }
    @PutMapping(path = "/update")
    public Mono<FixedTerm> update(@RequestBody FixedTerm fixedTerm)
    {
        log.info("Saving_account: controller update() method : {}",fixedTerm.toString());
        return service.update(fixedTerm);
    }
    @DeleteMapping(path = "/delete/{id}")
    public Mono<String> delete(@PathVariable String id)
    {
        log.info("Saving_account: controller delete() method : {}",id);
        service.delete(id).map(obj->{
            if (obj){
                return Mono.just("Cuenta de ahorro eliminada");
            }else{
                return Mono.just("La Cuenta de ahorro no puedo ser eliminada");
            }
        });
        return Mono.just("La Cuenta de ahorro no puedo ser eliminada");
    }

}
