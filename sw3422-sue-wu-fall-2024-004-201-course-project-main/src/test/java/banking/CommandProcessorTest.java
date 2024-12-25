package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public static String ACCOUNT_TEST_ID = "12345678";
	public static double ACCOUNT_TEST_APR = 1.0;
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void create_checking_account_with_command_processor() {
		commandProcessor.processCommand("create checking 12345678 1.0");
		assertEquals(ACCOUNT_TEST_APR, bank.getAccounts().get(ACCOUNT_TEST_ID).getApr());
		assertTrue(bank.accountExistsByID(ACCOUNT_TEST_ID));
	}

	@Test
	void deposit_100_in_account() {
		commandProcessor.processCommand("create savings 12345678 1.0");
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(100, bank.getAccounts().get(ACCOUNT_TEST_ID).getBalance());
	}

	@Test
	void deposit_100_in_account_with_initial_balance() {
		commandProcessor.processCommand("create cd 12345678 1.0 1000");
		commandProcessor.processCommand("deposit 12345678 100");
		assertEquals(1100, bank.getAccountById(ACCOUNT_TEST_ID).getBalance());
	}

}
