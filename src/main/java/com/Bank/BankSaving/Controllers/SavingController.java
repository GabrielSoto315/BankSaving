package com.Bank.BankSaving.Controllers;

import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.ResponseHandler;
import com.Bank.BankSaving.Service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/Saving/")
public class SavingController {

    @Autowired
    private SavingService savingService;

    /**
     * Lista todos los resultados
     * @return
     */
    @GetMapping()
    public Mono<ResponseHandler> getAll(){
        return savingService.findAll();
    }

    /**
     * Obtener resultado por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseHandler> findbyId(@PathVariable("id") String id){
        return savingService.find(id);
    }

    /**
     * Actualizar datos de ahorro
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id, @RequestBody Saving saving) {
        return savingService.update(id, saving);
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseHandler> deletebyId(@PathVariable("id") String id){
        return savingService.delete(id);
    }

    /**
     * Guardar nueva ahorro
     * @param saving
     * @return
     */
   @PostMapping("Saving/")
    public Mono<ResponseHandler> saveSaving(@RequestBody Saving saving){
       return savingService.create(saving, "Saving Account");
    }

    /**
     * Guardar nueva ahorro
     * @param saving
     * @return
     */
    @PostMapping("Current/")
    public Mono<ResponseHandler> saveCurrent(@RequestBody Saving saving){
        return savingService.create(saving, "Current Account");
    }

    /**
     * Guardar nueva ahorro
     * @param saving
     * @return
     */
    @PostMapping("FixedTerms/")
    public Mono<ResponseHandler> saveFixedTerms(@RequestBody Saving saving){
        return savingService.create(saving, "Fixed Terms Account");
    }


    /**
     * Guardar nuevo ahorro
     * @param saving
     * @return
     */
    @PostMapping()
    public Mono<ResponseHandler> saveAccount(@RequestBody Saving saving){
        return savingService.create(saving, saving.getProduct().getName());
    }
}