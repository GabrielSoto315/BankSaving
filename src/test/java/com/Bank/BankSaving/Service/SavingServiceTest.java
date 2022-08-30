package com.Bank.BankSaving.Service;

import com.Bank.BankSaving.Mock.SavingMock;
import com.Bank.BankSaving.Models.Documents.Saving;
import com.Bank.BankSaving.Models.Entities.ResponseHandler;
import com.Bank.BankSaving.Repository.ISavingRepository;
import com.Bank.BankSaving.Service.Implements.SavingServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(SpringExtension.class)
public class SavingServiceTest {

        @InjectMocks
        private SavingServiceImp savingServiceImp;

        @Mock
        private ISavingRepository savingRepository;

        @Test
        void findAllTest() {
            Saving saving = SavingMock.randomAccount();
            Mockito.when(savingRepository.findAll()).thenReturn(Flux.empty());
            Mono<ResponseHandler> responseHandlerMono = savingServiceImp.findAll();
            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getData() !=null)
                    .verifyComplete();
        }

        @Test
        void createTest() {

            Saving saving = SavingMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(saving);
            Mockito.when(savingRepository.save(saving)).thenReturn(Mono.just(saving));
        }


        @Test
        void findTest() {

            Saving debitCard = SavingMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(debitCard);

            Mockito.when(savingRepository.existsById("10210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(savingRepository.findById("10210000000004"))
                    .thenReturn(Mono.just(debitCard));

            savingServiceImp.find("10210000000004")
                    .map(response -> StepVerifier.create(Mono.just(response))
                            .expectNextMatches(x -> x.getData() != null)
                            .expectComplete()
                            .verify());
        }

        @Test
        void updateTest() {

            Saving client = SavingMock.randomAccount();

            Mockito.when(savingRepository.existsById("10210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(savingRepository.save(client))
                    .thenReturn(Mono.just(client));

            savingServiceImp.update("10210000000004", client)
                            .map(response -> StepVerifier.create(Mono.just(response))
                                    .expectNextMatches(x -> x.getData() != null)
                                    .expectComplete()
                                    .verify());
        }

        @Test
        void deleteTest() {
            ResponseHandler responseHandler2 = new ResponseHandler();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(null);

            Mockito.when(savingRepository.existsById("10210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(savingRepository.deleteById("10210000000004")).thenReturn(Mono.empty());

            savingServiceImp.delete("10210000000004")
                    .map(response -> StepVerifier.create(Mono.just(response))
                            .expectNextMatches(x -> x.getMessage().equals("Done"))
                            .expectComplete()
                            .verify());
        }

        @Test
        void updateFoundTest() {

            Saving saving = SavingMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(saving);

            Mockito.when(savingRepository.existsById("10210000000004"))
                    .thenReturn(Mono.just(false));

            Mono<ResponseHandler> responseHandlerMono = savingServiceImp
                    .update("10210000000004", saving);

            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getMessage().equals("Not found"))
                    .verifyComplete();
        }

        @Test
        void deleteFoundTest() {

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(null);

            Mockito.when(savingRepository.existsById("10210000000001"))
                    .thenReturn(Mono.just(false));

            Mono<ResponseHandler> responseHandlerMono = savingServiceImp
                    .delete("10210000000001");

            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getMessage().equals("Not found"))
                    .verifyComplete();
        }
}
