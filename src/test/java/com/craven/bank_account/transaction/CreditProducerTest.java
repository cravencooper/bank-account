package com.craven.bank_account.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CreditProducerTest {

    @Autowired
    private CreditProducer creditProducer;

    @Test
    void willThrowInterruptedExceptionWhenThreadBeingInterrupted() {
        Thread thread = new Thread(() -> {
           try {
               creditProducer.produceCredits();
           } catch (RuntimeException e){
               throw new RuntimeException(e);
           }
        });

        thread.start();
        thread.interrupt();

        assertThatThrownBy(() -> {
            thread.join();
        }).isInstanceOf(RuntimeException.class).hasCauseInstanceOf(InterruptedException.class);
    }
}