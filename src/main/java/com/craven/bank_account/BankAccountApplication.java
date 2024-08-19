package com.craven.bank_account;

import com.craven.bank_account.transaction.CreditProducer;
import com.craven.bank_account.transaction.DebitProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EntityScan("com.craven.bank_account.transaction.model")
@EnableJpaRepositories("com.craven.bank_account.persistence")
public class BankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);

	}

	@Bean
	public CommandLineRunner startProducers(CreditProducer creditProducer, DebitProducer debitProducer) {
		return args -> {
			creditProducer.produceCredits();
			debitProducer.produceDebits();
		};
	}

}
