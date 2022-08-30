package com.Bank.BankSaving.Service;

import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.ResponseHandler;
import reactor.core.publisher.Mono;

public interface SavingService {

    Mono<ResponseHandler> findAll();

    Mono<ResponseHandler> find(String id);

    Mono<ResponseHandler> create(Saving saving, String product);

    Mono<ResponseHandler> update(String id, Saving saving);

    Mono<ResponseHandler> delete(String id);

}
