package com.Bank.BankSaving.Controllers;


import com.Bank.BankSaving.Mock.SavingMock;
import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.ResponseHandler;
import com.Bank.BankSaving.Service.SavingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SavingController.class)
public class SavingControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private SavingService savingService;

    @Test
    void findAllTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);
        when(savingService.findAll()).thenReturn(Mono.just(responseHandler));

        webClient
                .get().uri("/api/Saving/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseHandler.class);
    }

    @Test
    void findByIdTest() {
        Saving saving = SavingMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(saving);

        Mockito
                .when(savingService.find("25210000000004"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/Saving/{id}", "25210000000004")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(savingService, times(1)).find("25210000000004");
    }

    @Test
    void createTest() {

        Saving saving = SavingMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(saving);

        Mockito
                .when(savingService.create(saving, "Saving Account")).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/Saving/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(saving))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void createAccountTest() {

        Saving saving = SavingMock.randomCurrent();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(saving);

        Mockito
                .when(savingService.create(saving, "Current Account")).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/Saving/Current/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(saving))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createSavingTest() {

        Saving saving = SavingMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(saving);

        Mockito
                .when(savingService.create(saving, "Saving Account")).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/Saving/Saving/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(saving))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateTest() {

        Saving saving = SavingMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(saving);

        Mockito
                .when(savingService.update("25210000000004",saving)).thenReturn(Mono.just(responseHandler));

        webClient
                .put()
                .uri("/api/Saving/{id}", "25210000000004")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(saving))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito
                .when(savingService.delete("25210000000004"))
                .thenReturn(Mono.just(responseHandler));

        webClient.delete().uri("/api/Saving/{id}", "25210000000004")
                .exchange()
                .expectStatus().isOk();
    }
}



