package com._line.CustomerAccountService;

import com._line.CustomerAccountService.exceptions.BadRequestException;
import com._line.CustomerAccountService.models.Account;
import com._line.CustomerAccountService.models.Customer;
import com._line.CustomerAccountService.models.Transaction;
import com._line.CustomerAccountService.models.enums.AccountType;
import com._line.CustomerAccountService.repository.AccountRepository;
import com._line.CustomerAccountService.repository.CustomerRepository;
import com._line.CustomerAccountService.repository.TransactionRepository;
import com._line.CustomerAccountService.response.CustomerInfoResponse;
import com._line.CustomerAccountService.service.impl.AccountServiceImpl;
import com._line.CustomerAccountService.service.impl.CustomerServiceImpl;
import com._line.CustomerAccountService.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CustomerAccountServiceApplicationTests {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private TransactionServiceImpl transactionService;

	@InjectMocks
	private AccountServiceImpl accountService;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void contextLoads() {
	}

	@Test
	public void createAccount_CustomerNotFound_ShouldThrowBadRequestException() {
		String customerId = "123";
		when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

		Assertions.assertThrows(BadRequestException.class, () -> {
			accountService.createAccount(customerId, 100.0);
		});
	}

	@Test
	public void createAccount_NegativeInitialCredit_ShouldThrowBadRequestException() {
		String customerId = "123";
		Customer mockCustomer = new Customer();
		when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(mockCustomer));

		Assertions.assertThrows(BadRequestException.class, () -> {
			accountService.createAccount(customerId, -10.0);
		});
	}

	@Test
	public void createAccount_CurrentAccountExists_ShouldThrowBadRequestException() {
		String customerId = "123";
		Customer mockCustomer = new Customer();
		Account existingAccount = new Account();
		existingAccount.setAccountType(AccountType.CURRENT);

		mockCustomer.setAccounts(List.of(existingAccount));
		when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(mockCustomer));

		Assertions.assertThrows(BadRequestException.class, () -> {
			accountService.createAccount(customerId, 50.0);
		});
	}

	@Test
	public void createAccount_Success_ShouldCreateAccountAndTransaction() throws BadRequestException {
		String customerId = "123";
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerId(customerId);

		when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(mockCustomer));
		when(accountRepository.save(Mockito.any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account createdAccount = accountService.createAccount(customerId, 100.0);

		// Verify that an account and a transaction were created
		verify(accountRepository, times(1)).save(Mockito.any(Account.class));
		verify(transactionService, times(1)).createAndSaveTransaction(100.0, createdAccount);
		assertEquals(AccountType.CURRENT, createdAccount.getAccountType());
	}


	@Test
	public void getCustomerInfo_CustomerNotFound_ShouldThrowBadRequestException() {
		Long customerId = 1L;
		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		Assertions.assertThrows(BadRequestException.class, () -> {
			customerService.getCustomerInfo(customerId);
		});
	}

	@Test
	public void getCustomerInfo_CustomerExists_ShouldReturnCustomerInfoResponse() throws BadRequestException {
		Long customerId = 1L;
		Customer mockCustomer = new Customer();
		mockCustomer.setId(customerId);
		mockCustomer.setName("John");
		mockCustomer.setSurname("Doe");

		Account account1 = new Account();
		account1.setAccountType(AccountType.SAVINGS);
		account1.setTransactions(List.of(
				new Transaction(100.0),
				new Transaction(50.0)
		));

		Account account2 = new Account();
		account2.setAccountType(AccountType.CURRENT);
		account2.setTransactions(List.of(
				new Transaction(75.0)
		));

		mockCustomer.setAccounts(List.of(account1, account2));

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

		CustomerInfoResponse response = customerService.getCustomerInfo(customerId);

		assertEquals("John", response.getFirstName());
		assertEquals("Doe", response.getSurname());
		assertEquals(225.0, response.getBalance());  // Sum of all transaction amounts
	}


	@Test
	public void createAndSaveTransaction_ShouldCreateTransactionAndUpdateBalance() {
		Account account = new Account();
		account.setId(1L);
		account.setBalance(100.0);

		Transaction newTransaction = Transaction.builder()
				.account(account)
				.amount(50.0)
				.transactionType("CREDIT")
				.narration("Account creation deposit")
				.build();

		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

		transactionService.createAndSaveTransaction(50.0, account);

		assertEquals(100.0, account.getBalance());
	}

}
