package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
	Validator validator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new Validator(bank);
	}

	@Test
	void process_invalid_create_savings_account_command() {
		boolean actual = validator.validateCommand("creat savings 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void process_valid_create_savings_account_command() {
		boolean actual = validator.validateCommand("create savings 12345671 1.0");
		assertTrue(actual);
	}

	@Test
	void process_invalid_deposit_command() {
		boolean actual = validator.validateCommand("deposi 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void process_valid_deposit_command() {
		Savings savings = new Savings(1.0, "12345671");
		bank.addAccount("12345671", savings);
		boolean actual = validator.validateCommand("deposit 12345671 100");
		assertTrue(actual);
	}

	@Test
	void process_valid_transfer_command() {
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		bank.addAccount("12345672", new Savings(2.0, "12345672"));
		bank.getAccountById("12345671").deposit(200);
		boolean actual = validator.validateCommand("transfer 12345671 12345672 100");
		assertTrue(actual);
	}

	@Test
	void process_invalid_transfer_command() {
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		boolean actual = validator.validateCommand("transfer 12345671 12345672 100");
		assertFalse(actual);
	}

	@Test
	void process_valid_pass_time_command() {
		boolean actual = validator.validateCommand("pass 12");
		assertTrue(actual);
	}

	@Test
	void process_invalid_pass_time_command() {
		boolean actual = validator.validateCommand("pass 123");
		assertFalse(actual);
	}

	@Test
	void process_valid_withdraw_command() {
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		bank.getAccountById("12345671").deposit(500);
		boolean actual = validator.validateCommand("withdraw 12345671 200");
		assertTrue(actual);
	}

	@Test
	void process_invalid_withdraw_command() {
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		bank.getAccountById("12345671").deposit(500);
		boolean actual = validator.validateCommand("withdraw 12345671 -100");
		assertFalse(actual);
	}

	@Test
	void process_invalid_action_command() {
		boolean actual = validator.validateCommand("add 12345671 300");
		assertFalse(actual);
	}

	@Test
	void process_invalid_action() {
		boolean actual = validator.validateCommand("close 12345671");
		assertFalse(actual);
	}

	@Test
	void value_in_range() {
		boolean actual = validator.isWithinRange("45", 0, 100);
		assertTrue(actual);
	}

	@Test
	void value_out_of_range() {
		boolean actual = validator.isWithinRange("-45", 0, 100);
		assertFalse(actual);
	}

	@Test
	void value_is_min() {
		boolean actual = validator.isWithinRange("0", 0, 100);
		assertTrue(actual);
	}

	@Test
	void value_is_max() {
		boolean actual = validator.isWithinRange("100", 0, 100);
		assertTrue(actual);
	}

	@Test
	void getting_id() {
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		validator.storedID = "12345671";
		String id = validator.getValidID();
		assertTrue(id.equals("12345671"));
	}

}
