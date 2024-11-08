package com._line.CustomerAccountService.config;

import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.models.Transaction;
import com._line.CustomerAccountService.models.enums.AccountType;
import com._line.CustomerAccountService.repository.AccountRepository;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        return args -> {
            // Create the list of customers
            Customer customer1 = Customer.builder()
                    .customerId("CUST001")
                    .name("Tobiloba")
                    .surname("Animasaun")
                    .build();

            Customer customer2 = Customer.builder()
                    .customerId("CUST002")
                    .name("Kolawole")
                    .surname("Emmanuel")
                    .build();

            Customer customer3 = Customer.builder()
                    .customerId("CUST003")
                    .name("Olamilekan")
                    .surname("Odetayo")
                    .build();

            Customer customer4 = Customer.builder()
                    .customerId("CUST004")
                    .name("Chinonso")
                    .surname("Ikwueto")
                    .build();

            Customer customer5 = Customer.builder()
                    .customerId("CUST005")
                    .name("Chinaka")
                    .surname("Akagha")
                    .build();

            Customer customer6 = Customer.builder()
                    .customerId("CUST006")
                    .name("Bolaji")
                    .surname("Oyewumi")
                    .build();

            // Save customers to the database
            List<Customer> customers = customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5, customer6));

            // Create a savings account and 3 transactions for each customer
            for (Customer customer : customers) {
                Account account = Account.builder()
                        .accountId("ACC_" + customer.getCustomerId())
                        .accountType(AccountType.SAVINGS)
                        .balance(1000.0) // Initial balance
                        .customer(customer)
                        .build();
                accountRepository.save(account);

                // Create three transactions for this account
                Transaction transaction1 = new Transaction();
                transaction1.setAmount(200.0);
                transaction1.setTimestamp(LocalDateTime.now().minusDays(3));
                transaction1.setAccount(account);

                Transaction transaction2 = new Transaction();
                transaction2.setAmount(150.0);
                transaction2.setTimestamp(LocalDateTime.now().minusDays(2));
                transaction2.setAccount(account);

                Transaction transaction3 = new Transaction();
                transaction3.setAmount(250.0);
                transaction3.setTimestamp(LocalDateTime.now().minusDays(1));
                transaction3.setAccount(account);

                // Save the transactions and link them to the account
                transactionRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3));

                // Add transactions to account and save
                account.getTransactions().addAll(Arrays.asList(transaction1, transaction2, transaction3));
                accountRepository.save(account);
            }

            System.out.println("Sample data for customers, accounts, and transactions created successfully.");
        };
    }
}
