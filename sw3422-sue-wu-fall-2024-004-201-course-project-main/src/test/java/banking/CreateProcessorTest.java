package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateProcessorTest {
	CreateProcessor createProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		createProcessor = new CreateProcessor(bank);
	}

	@Test
	void create_a_saving_account() {
		createProcessor.create("create savings 12345671 1.0");
		assertEquals(1, this.bank.getAccounts().size());
	}

	@Test
	void create_a_checking_account() {
		createProcessor.create("create checking 12345671 1.0");
		assertEquals(1, this.bank.getAccounts().size());
	}

	@Test
	void create_a_cd_account() {
		createProcessor.create("create cd 12345671 1.0 2500");
		assertEquals(1, this.bank.getAccounts().size());
	}

	@Test
	void create_a_two_different_valid_accounts() {
		createProcessor.create("create savings 12345671 1.0");
		createProcessor.create("create cd 12345672 1.0 1000");
		assertEquals(2, this.bank.getAccounts().size());
	}

}
