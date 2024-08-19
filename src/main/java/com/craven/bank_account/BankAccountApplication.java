package com.craven.bank_account;

import com.craven.bank_account.transaction.procucers.TransactionsProducers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);

	}

	@Bean
	public CommandLineRunner startProducers(TransactionsProducers transactionsProducers) {
		return args -> {
			transactionsProducers.generateCredits();
			transactionsProducers.generateDebits();
		};
	}

}
