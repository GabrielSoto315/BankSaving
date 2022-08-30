package com.Bank.BankSaving.Models.Documents;

import com.Bank.BankSaving.Models.Entities.Response.Product;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(value = "Saving")
@Data
public class Saving {
    @Transient
    public static final String SEQUENCE_NAME = "savingSequence";

    @Id
    private String idSaving;
    private Product product;
    private BigDecimal balance;
    private String idClient;
    private Boolean active;
    private Date registerDate;
    private Date updateDate;
    private Date lastTransaction;
}
