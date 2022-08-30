package com.Bank.BankSaving.Mock;

import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.Response.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class SavingMock {

    public static Saving randomAccount(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000004");
        saving.setIdClient("1910000000003");
        saving.setActive(true);
        saving.setRegisterDate(new Date());
        saving.setBalance(BigDecimal.valueOf(400));
        saving.setLastTransaction(new Date());

        Product product = new Product();
        product.setName("Saving Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }

    public static Saving randomCurrent(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000004");
        saving.setIdClient("1910000000003");
        saving.setActive(true);
        saving.setRegisterDate(new Date());
        saving.setBalance(BigDecimal.valueOf(9000));
        saving.setLastTransaction(new Date());

        Product product = new Product();
        product.setName("Current Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }

    public static Saving randomFixed(){
        Saving saving = new Saving();
        saving.setIdSaving("10210000000004");
        saving.setIdClient("1910000000003");
        saving.setActive(true);
        saving.setRegisterDate(new Date());
        saving.setBalance(BigDecimal.valueOf(15000));
        saving.setLastTransaction(new Date());

        Product product = new Product();
        product.setName("Fixed Terms Account");
        product.setClientType("Person");

        saving.setProduct(product);

        return saving;
    }

}
