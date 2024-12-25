package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {
	Bank bank;
	WithdrawProcessor withdrawProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		withdrawProcessor = new WithdrawProcessor(bank);
		bank.addAccount("12345671", new Savings(0, "12345671"));
		bank.addAccount("12345672", new Checking(0, "12345672"));
		bank.addAccount("12345673", new CD(5000, 0, "12345673"));
	}

	@Test
	void withdraw_from_savings() {
		bank.getAccountById("12345671").deposit(2000);
		withdrawProcessor.withdraw("withdraw 12345671 100");
		double balance = bank.getAccountById("12345671").getBalance();
		assertEquals(1900, balance);
	}

	@Test
	void withdraw_from_checking() {
		bank.getAccountById("12345672").deposit(2000);
		withdrawProcessor.withdraw("withdraw 12345672 100");
		double balance = bank.getAccountById("12345672").getBalance();
		assertEquals(1900, balance);
	}

	@Test
	void withdraw_twice_from_checking() {
		bank.getAccountById("12345672").deposit(2000);
		withdrawProcessor.withdraw("withdraw 12345672 100");
		withdrawProcessor.withdraw("withdraw 12345672 100");
		double balance = bank.getAccountById("12345672").getBalance();
		assertEquals(1800, balance);
	}

	@Test
	void withdraw_from_cd() {
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.passTime("pass 12");
		withdrawProcessor.withdraw("withdraw 12345673 5000");
		double balance = bank.getAccountById("12345673").getBalance();
		assertEquals(0, balance);
	}

	@Test
	void withdraw_more_than_balance_from_cd() {
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.passTime("pass 12");
		withdrawProcessor.withdraw("withdraw 12345673 5500");
		double balance = bank.getAccountById("12345673").getBalance();
		assertEquals(0, balance);
	}

}
