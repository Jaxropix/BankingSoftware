package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	Bank bank;
	WithdrawalValidator withdrawalValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		withdrawalValidator = new WithdrawalValidator(bank);
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
	}

	@Test
	void withdraw_valid_command() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_typo_in_command() {
		boolean actual = withdrawalValidator.validate("witdraw 12345671 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_in_string_command() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 hundred");
		assertFalse(actual);
	}

	@Test
	void withdraw_case_insensitive_command() {
		boolean actual = withdrawalValidator.validate("witHdRAw 12345671 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_invalid_command_missing_withdraw_word() {
		boolean actual = withdrawalValidator.validate("12345671 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_invalid_command_missing_withdraw_id() {
		boolean actual = withdrawalValidator.validate("withdraw 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_invalid_command_missing_withdraw_amount() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_command_with_extra_arguments() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 1.0 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_account_with_invalid_id() {
		boolean actual = withdrawalValidator.validate("withdraw 12345679 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_account_with_valid_id() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_savings_account_min_amount() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 0");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_savings_account_max_amount() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 1000");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_savings_account_more_than_max_amount() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 1001");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_savings_account_in_range_amount_in_account() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 500");
		assertTrue(actual);
	}

	@Test
	void attempt_to_withdraw_negative_amount() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 -1");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_CD_account_with_valid_amount() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 12");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 1500");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_CD_account_at_the_earliest() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 11");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 1000");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_CD_account() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 45");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 1000");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_CD_account_too_early() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 11");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 1000");
		assertFalse(actual);
	}

	@Test
	void withdraw_less_than_account_balance() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 12");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 999");
		assertFalse(actual);
	}

	@Test
	void withdraw_more_than_account_balance() {
		bank.addAccount("12345673", new CD(1000, 1.0, "12345673"));
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);
		passTimeProcessor.processCommand("pass 12");
		boolean actual = withdrawalValidator.validate("withdraw 12345673 2000");
		assertTrue(actual);
	}

	@Test
	void withdraw_command_with_extra_space_in_beginning() {
		boolean actual = withdrawalValidator.validate(" withdraw 12345671 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_extra_space_in_middle() {
		boolean actual = withdrawalValidator.validate("withdraw  12345671 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_extra_space_in_end() {
		boolean actual = withdrawalValidator.validate("withdraw 12345671 100 ");
		assertTrue(actual);
	}

	@Test
	void withdraw_twice_in_saving_in_a_month() {
		bank.getAccountById("12345671").deposit(1000);
		WithdrawProcessor withdrawProcessor = new WithdrawProcessor(bank);
		withdrawProcessor.processCommand("withdraw 12345671 100");
		boolean actual = withdrawalValidator.validate("withdraw 12345671 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_once_then_once_again_next_month_in_saving_account() {
		bank.getAccountById("12345671").deposit(1000);
		WithdrawProcessor withdrawProcessor = new WithdrawProcessor(bank);
		PassTimeProcessor passTimeProcessor = new PassTimeProcessor(bank);

		withdrawProcessor.processCommand("withdraw 12345671 100");
		passTimeProcessor.processCommand("pass 1");
		boolean actual = withdrawalValidator.validate("withdraw 12345671 100");
		assertTrue(actual);

	}

	@Test
	void withdraw_from_checking() {
		bank.addAccount("12345672", new Checking(0, "12345672"));
		boolean actual = withdrawalValidator.validate("withdraw 12345672 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_checking_twice_in_a_month() {
		bank.addAccount("12345672", new Checking(0, "12345672"));
		WithdrawProcessor withdrawProcessor = new WithdrawProcessor(bank);
		withdrawProcessor.processCommand("withdraw 12345672 100");
		boolean actual = withdrawalValidator.validate("withdraw 12345672 100");
		assertTrue(actual);
	}

}
