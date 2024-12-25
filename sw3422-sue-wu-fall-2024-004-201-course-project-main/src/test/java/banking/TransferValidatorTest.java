package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	Bank bank;
	TransferValidator transferValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		transferValidator = new TransferValidator(bank);
		bank.addAccount("12345671", new Savings(1.0, "12345671"));
		bank.addAccount("12345672", new Checking(1.0, "12345672"));
		bank.addAccount("12345673", new CD(500, 1.0, "12345673"));
	}

	@Test
	void valid_transfer_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 500");
		assertTrue(actual);
	}

	@Test
	void invalid_typo_command_not_found() {
		boolean actual = transferValidator.validate("transer 12345671 12345672 500");
		assertFalse(actual);
	}

	@Test
	void missing_action_command() {
		boolean actual = transferValidator.validate("12345671 12345672 500");
		assertFalse(actual);
	}

	@Test
	void missing_one_account_command() {
		boolean actual = transferValidator.validate("transfer 12345672 500");
		assertFalse(actual);
	}

	@Test
	void missing_amount_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672");
		assertFalse(actual);
	}

	@Test
	void two_different_id() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 500");
		assertTrue(actual);
	}

	@Test
	void two_nonexistent_ids() {
		boolean actual = transferValidator.validate("transfer 12345651 12345677 500");
		assertFalse(actual);
	}

	@Test
	void identical_id() {
		boolean actual = transferValidator.validate("transfer 12345671 12345671 500");
		assertFalse(actual);
	}

	@Test
	void one_valid_account_and_one_invalid_account_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345689 500");
		assertFalse(actual);
	}

	@Test
	void nonnumeric_invalid_accounts() {
		boolean actual = transferValidator.validate("transfer fhdsbfsd sjkhalda 500");
		assertFalse(actual);
	}

	@Test
	void transfer_zero_in_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 0");
		assertTrue(actual);
	}

	@Test
	void transfer_negative_in_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 -1");
		assertFalse(actual);
	}

	@Test
	void transfer_amount_within_range() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 500");
		assertTrue(actual);
	}

	@Test
	void transfer_max_amount_within_range() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 " + Double.MAX_VALUE);
		assertTrue(actual);
	}

	@Test
	void transfer_amount_in_string() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 five");
		assertFalse(actual);
	}

	@Test
	void transfer_from_savings_to_savings() {
		bank.addAccount("12345674", new Savings(1.0, "12345674"));
		boolean actual = transferValidator.validate("transfer 12345671 12345674 500");
		assertTrue(actual);
	}

	@Test
	void transfer_from_checking_to_checking() {
		bank.addAccount("12345675", new Checking(1.0, "12345675"));
		boolean actual = transferValidator.validate("transfer 12345672 12345675 500");
		assertTrue(actual);
	}

	@Test
	void transfer_from_cd_to_cd_invalid_command() {
		bank.addAccount("12345676", new CD(500, 1.0, "12345676"));
		boolean actual = transferValidator.validate("transfer 12345673 12345676 500");
		assertFalse(actual);
	}

	@Test
	void transfer_from_savings_to_checking() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 500");
		assertTrue(actual);
	}

	@Test
	void transfer_from_savings_to_cd_invalid_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345673 500");
		assertFalse(actual);
	}

	@Test
	void transfer_from_checking_to_savings() {
		boolean actual = transferValidator.validate("transfer 12345672 12345671 500");
		assertTrue(actual);
	}

	@Test
	void transfer_from_checking_to_cd_invalid_command() {
		boolean actual = transferValidator.validate("transfer 12345672 12345673 500");
		assertFalse(actual);
	}

	@Test
	void transfer_from_cd_to_savings_invalid_command() {
		boolean actual = transferValidator.validate("transfer 12345673 12345671 500");
		assertFalse(actual);
	}

	@Test
	void transfer_from_cd_to_checking_invalid_command() {
		boolean actual = transferValidator.validate("transfer 12345673 12345672 500");
		assertFalse(actual);
	}

	@Test
	void space_in_beginning_command() {
		boolean actual = transferValidator.validate(" transfer 12345671 12345672 500");
		assertFalse(actual);
	}

	@Test
	void extra_space_in_the_middle_command() {
		boolean actual = transferValidator.validate("transfer 12345671  12345672 500");
		assertFalse(actual);
	}

	@Test
	void space_in_end_command() {
		boolean actual = transferValidator.validate("transfer 12345671 12345672 500 ");
		assertTrue(actual);
	}

	@Test
	void transfer_out_of_order_command() {
		boolean actual = transferValidator.validate("12345671 transfer 12345672 500");
		assertFalse(actual);
	}

	@Test
	void transfer_case_insensitive_command() {
		boolean actual = transferValidator.validate("trAnsFer 12345671 12345672 500");
		assertTrue(actual);
	}

}
