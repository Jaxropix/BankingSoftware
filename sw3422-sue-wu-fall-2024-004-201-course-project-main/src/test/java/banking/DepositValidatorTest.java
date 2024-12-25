package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	Bank bank;
	DepositValidator depositValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositValidator = new DepositValidator(bank);
		Savings savings = new Savings(1.0, "12345671");
		Checking checking = new Checking(1.0, "12345672");
		CD cd = new CD(1000, 1.0, "12345673");
		bank.addAccount("12345671", savings);
		bank.addAccount("12345672", checking);
		bank.addAccount("12345673", cd);

	}

	@Test
	void deposit_valid_command() {
		boolean actual = depositValidator.validate("deposit 12345671 10");
		assertTrue(actual);
	}

	@Test
	void missing_deposit_in_command() {
		boolean actual = depositValidator.validate("12345671 100");
		assertFalse(actual);
	}

	@Test
	void missing_id_in_command() {
		boolean actual = depositValidator.validate("deposit 100");
		assertFalse(actual);
	}

	@Test
	void missing_amount_in_command() {
		boolean actual = depositValidator.validate("deposit 12345671");
		assertFalse(actual);
	}

	@Test
	void deposit_typo_in_command() {
		boolean actual = depositValidator.validate("deosit 12345671 100");
		assertFalse(actual);
	}

	@Test
	void deposit_account_with_id_with_missing_number() {
		boolean actual = depositValidator.validate("deposit 1234567 100");
		assertFalse(actual);
	}

	@Test
	void deposit_account_with_amount_with_extra_number() {
		boolean actual = depositValidator.validate("deposit 123456711 100");
		assertFalse(actual);
	}

	@Test
	void deposit_account_with_non_numberic_amount() {
		boolean actual = depositValidator.validate("deposit 123456711 hundred");
		assertFalse(actual);
	}

	@Test
	void deposit_account_with_amount_with_non_number_id() {
		boolean actual = depositValidator.validate("deposit 1234567{ 100");
		assertFalse(actual);
	}

	@Test
	void deposit_zero_into_account() {
		boolean actual = depositValidator.validate("deposit 12345671 0");
		assertTrue(actual);
	}

	@Test
	void deposit_within_range_into_savings_account() {
		boolean actual = depositValidator.validate("deposit 12345671 100");
		assertTrue(actual);
	}

	@Test
	void deposit_within_range_into_checking_account() {
		boolean actual = depositValidator.validate("deposit 12345672 100");
		assertTrue(actual);
	}

	@Test
	void deposit_more_than_max_into_savings_account() {
		boolean actual = depositValidator.validate("deposit 12345671 2501");
		assertFalse(actual);
	}

	@Test
	void deposit_more_than_max_into_checking_account() {
		boolean actual = depositValidator.validate("deposit 12345672 1001");
		assertFalse(actual);
	}

	@Test
	void deposit_into_cd_account() {
		boolean actual = depositValidator.validate("deposit 12345673 1");
		assertFalse(actual);
	}

	@Test
	void deposit_zero_into_cd() {
		boolean actual = depositValidator.validate("deposit 12345673 0");
		assertFalse(actual);
	}

	@Test
	void deposit_negative_amount_into_account() {
		boolean actual = depositValidator.validate("deposit 12345671 -1");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_into_account() {
		boolean actual = depositValidator.validate("deposit 12345671 e");
		assertFalse(actual);
	}

	@Test
	void deposit_into_invalid_id_account() {
		boolean actual = depositValidator.validate("deposit 12345674 10");
		assertFalse(actual);
	}

	@Test
	void deposit_command_case_insensitive() {
		boolean actual = depositValidator.validate("DEPosiT 12345671 10");
		assertTrue(actual);
	}

	@Test
	void deposit_command_with_extra_argument() {
		boolean actual = depositValidator.validate("deposit 12345671 100 1.0");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_wrong_order_of_arguments() {
		boolean actual = depositValidator.validate("deposit 100 12345671");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_missing_argument() {
		boolean actual = depositValidator.validate("deposit 12345671");
		assertFalse(actual);
	}

	@Test
	void deposit_twice_into_account() {
		boolean actual = depositValidator.validate("deposit 12345671 100");
		boolean actual2 = depositValidator.validate("deposit 12345671 100");
		assertTrue(actual);
		assertTrue(actual2);
	}

	@Test
	void deposit_twice_into_different_account() {
		boolean actual = depositValidator.validate("deposit 12345671 100");
		boolean actual2 = depositValidator.validate("deposit 12345672 100");
		assertTrue(actual);
		assertTrue(actual2);
	}

	@Test
	void valid_deposit_then_invalid_deposit() {
		boolean actual = depositValidator.validate("deposit 12345671 100");
		boolean actual2 = depositValidator.validate("deposit 12345674 100");
		assertTrue(actual);
		assertFalse(actual2);
	}

	@Test
	void deposit_command_with_extra_space_in_beginning() {
		boolean actual = depositValidator.validate(" deposit 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_extra_space_in_middle() {
		boolean actual = depositValidator.validate("deposit  12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_extra_space_in_end() {
		boolean actual = depositValidator.validate("deposit 12345671 1.0 ");
		assertTrue(actual);
	}

}
