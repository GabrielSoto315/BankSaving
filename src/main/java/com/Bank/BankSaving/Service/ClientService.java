package com.Bank.BankSaving.Service;

import com.Bank.BankSaving.Models.Entities.Response.ClientCompanyResponse;
import com.Bank.BankSaving.Models.Entities.Response.ClientPersonResponse;
import com.Bank.BankSaving.Models.Entities.Response.ClientTypeResponse;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientCompanyResponse> FindClientCompanyId(String id);

    Mono<ClientPersonResponse> FindClientPersonId(String id);

    Mono<ClientTypeResponse> FindClientTypeId(String id);
}
