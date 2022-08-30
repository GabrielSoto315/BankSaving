package com.Bank.BankSaving.Repository;

import com.Bank.BankSaving.Models.Documents.Saving;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISavingRepository extends ReactiveMongoRepository<Saving, String> {
}