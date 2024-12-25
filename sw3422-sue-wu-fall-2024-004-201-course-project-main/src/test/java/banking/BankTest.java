package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ID = "11111111";
	public static final String SECOND_ID = "222222222";
	public static final double MONEY_AMOUNT = 40.76;
	public static final double INITIAL_BALANCE = 2000.23;

	Bank bank;
	Account savings;
	Account secondAccount;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		savings = new Savings(Account.ACCOUNT_TEST_APR, ID);
		secondAccount = new CD(INITIAL_BALANCE, Account.ACCOUNT_TEST_APR, SECOND_ID);
	}

	@Test
	void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void add_account_to_bank() {
		bank.addAccount(ID, savings);
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	void add_two_accounts_to_bank() {
		bank.addAccount(ID, savings);
		bank.addAccount(SECOND_ID, secondAccount);
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	void retrieve_account_to_bank() {
		bank.addAccount(ID, savings);
		assertEquals(savings, bank.getAccounts().get(ID));
	}

	@Test
	void deposit_money_through_id() {
		bank.addAccount(ID, savings);
		bank.getAccounts().get(ID).deposit(MONEY_AMOUNT);
		assertEquals(MONEY_AMOUNT, savings.getBalance());
	}

	@Test
	void deposit_money_twice_through_id() {
		bank.addAccount(ID, savings);
		bank.getAccounts().get(ID).deposit(MONEY_AMOUNT);
		bank.getAccounts().get(ID).deposit(MONEY_AMOUNT);
		assertEquals(MONEY_AMOUNT + MONEY_AMOUNT, savings.getBalance());
	}

	@Test
	void withdraw_money_through_id() {
		bank.addAccount(SECOND_ID, secondAccount);
		bank.getAccounts().get(SECOND_ID).withdraw(MONEY_AMOUNT);
		assertEquals(INITIAL_BALANCE - MONEY_AMOUNT, secondAccount.getBalance());
	}

	@Test
	void withdraw_money_twice_through_id() {
		bank.addAccount(SECOND_ID, secondAccount);
		bank.getAccounts().get(SECOND_ID).withdraw(MONEY_AMOUNT);
		bank.getAccounts().get(SECOND_ID).withdraw(MONEY_AMOUNT);
		assertEquals(INITIAL_BALANCE - MONEY_AMOUNT - MONEY_AMOUNT, secondAccount.getBalance());
	}

	@Test
	void get_account_through_id() {
		bank.addAccount(ID, savings);
		Account account = bank.getAccountById(ID);
		assertEquals(savings, account);
	}

}
