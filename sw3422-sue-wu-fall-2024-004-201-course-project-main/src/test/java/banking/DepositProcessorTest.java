package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositProcessorTest {
	private final int DEPOSIT_AMOUNT = 100;
	private DepositProcessor depositProcessor;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositProcessor = new DepositProcessor(bank);
	}

	@Test
	public void deposit_into_savings() {
		Savings savings = new Savings(1.0, "12345671");
		bank.addAccount("12345671", savings);
		depositProcessor.deposit("deposit 12345671 100");
		assertEquals(DEPOSIT_AMOUNT, savings.getBalance());
	}

	@Test
	public void deposit_into_checking() {
		Checking checking = new Checking(1.0, "12345671");
		bank.addAccount("12345671", checking);
		depositProcessor.deposit("deposit 12345671 100");
		assertEquals(DEPOSIT_AMOUNT, checking.getBalance());
	}

	@Test
	public void deposit_into_100_into_cd_account_with_initial_balance() {
		CD cd = new CD(2500, 1.0, "12345671");
		bank.addAccount("12345671", cd);
		depositProcessor.deposit("deposit 12345671 100");
		assertEquals(DEPOSIT_AMOUNT + 2500, cd.getBalance());
	}

	@Test
	public void deposit_into_100_twice_into_savings_account() {
		Savings savings = new Savings(1.0, "12345671");
		bank.addAccount("12345671", savings);
		depositProcessor.deposit("deposit 12345671 100");
		depositProcessor.deposit("deposit 12345671 100");
		assertEquals(DEPOSIT_AMOUNT + DEPOSIT_AMOUNT, savings.getBalance());
	}

}
