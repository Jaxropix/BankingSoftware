package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {
	Bank bank;
	TransferProcessor transferProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		transferProcessor = new TransferProcessor(bank);
		bank.addAccount("12345671", new Savings(0.1, "12345671"));
		bank.addAccount("12345672", new Checking(0.2, "12345672"));
	}

	@Test
	public void process_savings_to_savings() {
		bank.addAccount("12345674", new Savings(0.1, "12345674"));
		bank.getAccounts().get("12345671").deposit(400);
		transferProcessor.processCommand("transfer 12345671 12345674 200");
		double first = bank.getAccounts().get("12345671").getBalance();
		double second = bank.getAccounts().get("12345674").getBalance();
		assertEquals(200, first);
		assertEquals(200, second);
	}

	@Test
	public void transfer_from_checking_to_checking() {
		bank.addAccount("12345675", new Checking(0.2, "12345675"));
		bank.getAccounts().get("12345672").deposit(700);
		transferProcessor.processCommand("transfer 12345672 12345675 200");
		double first = bank.getAccounts().get("12345672").getBalance();
		double second = bank.getAccounts().get("12345675").getBalance();
		assertEquals(500, first);
		assertEquals(200, second);
	}

	@Test
	public void process_transfer_from_savings_to_checking() {
		bank.getAccounts().get("12345671").deposit(400);
		transferProcessor.processCommand("transfer 12345671 12345672 200");
		double first = bank.getAccounts().get("12345671").getBalance();
		double second = bank.getAccounts().get("12345672").getBalance();
		assertEquals(200, first);
		assertEquals(200, second);

	}

	@Test
	public void process_transfer_from_checking_to_savings() {
		bank.getAccounts().get("12345672").deposit(500);
		transferProcessor.processCommand("transfer 12345672 12345671 200");
		double first = bank.getAccounts().get("12345672").getBalance();
		double second = bank.getAccounts().get("12345671").getBalance();
		assertEquals(300, first);
		assertEquals(200, second);
	}

	@Test
	public void process_pending_withdrawal_money_that_is_more_than_balance() {
		bank.addAccount("12345674", new Savings(0.1, "12345674"));
		bank.getAccounts().get("12345671").deposit(200);
		transferProcessor.processCommand("transfer 12345671 12345674 300");
		double first = bank.getAccounts().get("12345671").getBalance();
		double second = bank.getAccounts().get("12345674").getBalance();
		assertEquals(0, first);
		assertEquals(200, second);
	}

}
