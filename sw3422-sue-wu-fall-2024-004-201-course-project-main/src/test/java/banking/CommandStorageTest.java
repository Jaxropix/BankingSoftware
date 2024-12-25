package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	Bank bank;
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	void add_in_valid_command() {
		commandStorage.addInvalidCommand("reate savings 12345671 1.0");
		assertEquals(1, commandStorage.invalidCommands.size());
	}

	@Test
	void add_two_invalid_commands() {
		commandStorage.addInvalidCommand("reate savings 12345671 1.0");
		commandStorage.addInvalidCommand("reate savings 12345671 2.0");
		assertEquals(2, commandStorage.invalidCommands.size());
	}

	@Test
	void create_current_statement() {
		bank.addAccount("12345671", new Savings(0, "12345671"));
		bank.getAccountById("12345671").deposit(1000);
		String actual = commandStorage.getCurrentState("12345671");
		assertEquals("Savings 12345671 1000.00 0.00", actual);
	}

	@Test
	void get_savings_account_type() {
		bank.addAccount("12345671", new Savings(0, "12345671"));
		String actual = commandStorage.getAccountType("12345671");
		assertEquals("Savings", actual);
	}

	@Test
	void get_checking_account_type() {
		bank.addAccount("12345672", new Checking(0, "12345672"));
		String actual = commandStorage.getAccountType("12345672");
		assertEquals("Checking", actual);
	}

	@Test
	void get_cd_account_type() {
		bank.addAccount("12345673", new CD(1000, 0, "12345673"));
		String actual = commandStorage.getAccountType("12345673");
		assertEquals("Cd", actual);
	}

	@Test
	void add_transactions_to_one_account() {
		commandStorage.addTransaction("create savings 12345671 1.0");
		commandStorage.addTransaction("deposit 12345671 500");
		commandStorage.addTransaction("withdraw 12345671 200");
		assertEquals(3, commandStorage.entireHistory.size());
		List<String> output = commandStorage.getAccountHistory("12345671");
		assertEquals(2, output.size());
	}

	@Test
	void add_multiple_transactions_to_two_accounts() {
		commandStorage.addTransaction("create savings 12345671 1.0");
		commandStorage.addTransaction("deposit 12345671 500");
		commandStorage.addTransaction("create checking 12345672 1.0");
		commandStorage.addTransaction("withdraw 12345671 200");
		commandStorage.addTransaction("deposit 12345672 500");
		commandStorage.addTransaction("withdraw 12345672 200");
		commandStorage.addTransaction("withdraw 12345672 200");
		assertEquals(2, commandStorage.getAccountHistory("12345671").size());
		assertEquals(3, commandStorage.getAccountHistory("12345672").size());
	}

	@Test
	void create_account_history() {
		commandStorage.addTransaction("create savings 12345671 1.0");
		bank.addAccount("12345671", new Savings(0, "12345671"));
		commandStorage.addTransaction("deposit 12345671 500");
		commandStorage.addTransaction("withdraw 12345671 200");
		List<String> history = commandStorage.createOutput();
		assertEquals(3, history.size());
		assertTrue(history.contains("deposit 12345671 500"));
		assertTrue(history.contains("withdraw 12345671 200"));
	}

	@Test
	void create_account_history_for_two_accounts() {
		commandStorage.addTransaction("create savings 12345671 1.0");
		bank.addAccount("12345671", new Savings(0, "12345671"));
		commandStorage.addTransaction("deposit 12345671 500");
		commandStorage.addTransaction("withdraw 12345671 200");
		commandStorage.addTransaction("create checking 12345672 1.0");
		bank.addAccount("12345672", new Checking(0, "12345672"));
		commandStorage.addTransaction("withdraw 12345671 200");

		commandStorage.createOutput();
		assertEquals(5, commandStorage.createOutput().size());
	}

}
