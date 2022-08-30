package com.Bank.BankSaving.Service.Implements;

import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.Response.Product;
import com.Bank.BankSaving.Service.ClientService;
import com.Bank.BankSaving.Service.SequenceGeneratorService;
import com.Bank.BankSaving.Models.Entities.ResponseHandler;
import com.Bank.BankSaving.Repository.ISavingRepository;
import com.Bank.BankSaving.Service.SavingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;


import static com.Bank.BankSaving.Models.Documents.Saving.SEQUENCE_NAME;

@Service
@RequiredArgsConstructor
public class SavingServiceImp implements SavingService {

    @Autowired
    ISavingRepository savingRepository;
    @Autowired
    SequenceGeneratorService sequenceService;
    @Autowired
    ClientService clientService;

    private static final Logger log = LoggerFactory.getLogger(SavingServiceImp.class);


    @Override
    public Mono<ResponseHandler> findAll() {
        return savingRepository.findAll()
                .doOnNext(n -> log.info(n.toString()))
                .filter(f -> f.getActive().equals(true))
                .collectList()
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        return savingRepository.existsById(id).flatMap(exist -> {
            if (exist){
                return savingRepository.findById(id)
                        .doOnNext(saving -> log.info(saving.toString()))
                        .map(res -> {
                            if (res.getActive()){
                                return new ResponseHandler("Done", HttpStatus.OK, res);
                            } else {
                                return new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null);
                            }
                        })
                        .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            } else {
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
            }
        });
    }

    @Override
    public Mono<ResponseHandler> create(Saving saving, String product) {
        return clientService.FindClientTypeId(saving.getIdClient()).flatMap(client ->{
            log.info("Client" + client.toString());
            if (client.getData() == null){
               return Mono.just(new ResponseHandler("Client not found",HttpStatus.BAD_REQUEST,null));
            }else {
               log.info("Saving product : " + product);
               Product oProduct = new Product();
               oProduct.setName(product);
               oProduct.setClientType(client.getData().getType());
               saving.setActive(true);
               saving.setRegisterDate(new Date());
               saving.setLastTransaction(new Date());

               if (client.getData().getType().equals("Person")) {
                   return savingRepository.findAll()
                           .filter(x -> x.getActive().equals(true) &&
                                   x.getIdClient().equals(saving.getIdClient()))
                           .count()
                           .flatMap(c -> {
                               if (c >= 1) {
                                   return Mono.just(new ResponseHandler("Client have a saving", HttpStatus.BAD_REQUEST, null));
                               } else {
                                   log.info("Prepare saving");
                                   log.info(oProduct.toString());
                                   return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                                       saving.setIdSaving(String.format("1021%010d", s));
                                       saving.setProduct(oProduct);
                                       log.info(saving.toString());
                                       return savingRepository.save(saving).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));
                                   });
                               }
                           });
               } else {
                   if (!product.equals("Saving Account")) {
                       return Mono.just(new ResponseHandler("Product not valid", HttpStatus.BAD_REQUEST, null));
                   } else {
                       log.info("Prepare saving");
                       log.info(oProduct.toString());
                       return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                           saving.setIdSaving(String.format("1021%010d", s));
                           saving.setProduct(oProduct);
                           log.info(saving.toString());
                           return savingRepository.save(saving).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));
                       });
                   }
               }
            }
        });
    }

    @Override
    public Mono<ResponseHandler> update(String id, Saving saving) {
        return savingRepository.existsById(id).flatMap(check -> {
            if (check) {
                return savingRepository.findById(id)
                        .flatMap(x -> {
                            x.setBalance(saving.getBalance());
                            x.setLastTransaction(new Date());
                            x.setUpdateDate(new Date());
                            return savingRepository.save(x)
                                    .map(y -> new ResponseHandler("Done", HttpStatus.OK, y))
                                    .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
                        });
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        });
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {
        return savingRepository.existsById(id).flatMap(check ->{
            if (check){
                return savingRepository.findById(id).flatMap(x ->{
                    x.setActive(false);
                    return savingRepository.save(x)
                            .then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
                });
            }
            else {
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND,null));
            }
        });
    }
}

