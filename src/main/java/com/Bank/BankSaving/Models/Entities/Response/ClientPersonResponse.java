package com.Bank.BankSaving.Models.Entities.Response;

import lombok.Data;

@Data
public class ClientPersonResponse {
    private String message;
    private String status;
    private ClientPerson data;
}
