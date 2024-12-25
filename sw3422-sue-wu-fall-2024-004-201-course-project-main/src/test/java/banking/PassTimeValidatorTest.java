package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	Bank bank;
	PassTimeValidator passTimeValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		passTimeValidator = new PassTimeValidator(bank);
	}

	@Test
	void pass_time_valid_command() {
		boolean actual = passTimeValidator.validate("pass 12");
		assertTrue(actual);
	}

	@Test
	void pass_time_missing_action_command() {
		boolean actual = passTimeValidator.validate("12");
		assertFalse(actual);
	}

	@Test
	void pass_time_missing_months_command() {
		boolean actual = passTimeValidator.validate("pass");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_with_extra_argument() {
		boolean actual = passTimeValidator.validate("pass 12 now");
		assertFalse(actual);
	}

	@Test
	void pass_time_out_of_order_command() {
		boolean actual = passTimeValidator.validate("12 pass");
		assertFalse(actual);
	}

	@Test
	void pass_typo_in_action_command() {
		boolean actual = passTimeValidator.validate("pss 12 pass");
		assertFalse(actual);
	}

	@Test
	void space_in_beginning_command() {
		boolean actual = passTimeValidator.validate(" pass 12");
		assertFalse(actual);
	}

	@Test
	void extra_space_in_middle_command() {
		boolean actual = passTimeValidator.validate("pass  12");
		assertFalse(actual);
	}

	@Test
	void space_in_end_command() {
		boolean actual = passTimeValidator.validate("pass 12 ");
		assertTrue(actual);
	}

	@Test
	void case_insensitive_command() {
		boolean actual = passTimeValidator.validate("pASs 12");
		assertTrue(actual);
	}

	@Test
	void pass_time_invalid_negative_months_command() {
		boolean actual = passTimeValidator.validate("pass -1");
		assertFalse(actual);
	}

	@Test
	void pass_time_invalid_zero_month_command() {
		boolean actual = passTimeValidator.validate("pass 0");
		assertFalse(actual);
	}

	@Test
	void pass_months_within_range_command() {
		boolean actual = passTimeValidator.validate("pass 59");
		assertTrue(actual);
	}

	@Test
	void pass_max_months_command() {
		boolean actual = passTimeValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	void pass_min_months_command() {
		boolean actual = passTimeValidator.validate("pass 1");
		assertTrue(actual);
	}

	@Test
	void pass_invalid_more_than_max_months_command() {
		boolean actual = passTimeValidator.validate("pass 61");
		assertFalse(actual);
	}

	@Test
	void pass_invalid_double_data_type() {
		boolean actual = passTimeValidator.validate("pass 12.1");
		assertFalse(actual);
	}

	@Test
	void pass_invalid_string_type() {
		boolean actual = passTimeValidator.validate("pass twelve");
		assertFalse(actual);
	}

}
