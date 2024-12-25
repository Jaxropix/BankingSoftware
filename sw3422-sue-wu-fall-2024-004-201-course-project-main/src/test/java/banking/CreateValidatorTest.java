package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	Bank bank;
	CreateValidator createValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
	}

	@Test
	void create_valid_empty_savings_account() {
		boolean actual = createValidator.validate("create savings 12345671 1.0");
		assertTrue(actual);
	}

	@Test
	void create_valid_empty_checking_account() {
		boolean actual = createValidator.validate("create checking 12345671 1.0");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd_account_with_initial_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 1000");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd_account_with_float_number() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 1000.01");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd_account_with_minimum_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 1000");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd_account_with_maximum_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 10000");
		assertTrue(actual);
	}

	@Test
	void create_valid_cd_account_with_initial_balance_within_acceptable_range() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 3000");
		assertTrue(actual);
	}

	@Test
	void create_invalid_command_with_non_numeric_argument_for_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 hundred");
		assertFalse(actual);
	}

	@Test
	void create_invalid_command_with_negative_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0 -10000");
		assertFalse(actual);
	}

	@Test
	void create_account_with_an_existing_id() {
		Savings savings = new Savings(0.1, "12345671");
		bank.addAccount("12345671", savings);
		boolean actual = createValidator.validate("create checking 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_two_accounts_with_different_id() {
		Savings savings = new Savings(0.1, "12345671");
		bank.addAccount("12345671", savings);
		boolean actual = createValidator.validate("create checking 12345672 1.0");
		assertTrue(actual);
	}

	@Test
	void create_command_without_create() {
		boolean actual = createValidator.validate("savings 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_without_account_type() {
		boolean actual = createValidator.validate("create 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_without_id() {
		boolean actual = createValidator.validate("create savings 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_without_apr() {
		boolean actual = createValidator.validate("create savings 12345671");
		assertFalse(actual);
	}

	@Test
	void create_account_with_id_with_missing_number() {
		boolean actual = createValidator.validate("create savings 1234567 1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_id_with_extra_number() {
		boolean actual = createValidator.validate("create savings 123456789 1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_id_with_non_number() {
		boolean actual = createValidator.validate("create savings 1234567e 1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_missing_argument() {
		boolean actual = createValidator.validate("create cd 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_extra_argument() {
		boolean actual = createValidator.validate("create savings 12345671 1.0 1000");
		assertFalse(actual);
	}

	@Test
	void create_checking_account_with_initial_balance() {
		boolean actual = createValidator.validate("create checking 12345671 1.0 3000");
		assertFalse(actual);
	}

	@Test
	void create_account_with_wrong_order_of_arguments() {
		boolean actual = createValidator.validate("create cd 2000 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_int_apr() {
		boolean actual = createValidator.validate("create savings 12345671 6");
		assertTrue(actual);
	}

	@Test
	void create_account_with_negative_apr() {
		boolean actual = createValidator.validate("create savings 12345671 -1.0");
		assertFalse(actual);
	}

	@Test
	void create_account_with_out_of_range_apr() {
		boolean actual = createValidator.validate("create savings 12345671 10.1");
		assertFalse(actual);
	}

	@Test
	void create_account_with_lowest_acceptable_apr() {
		boolean actual = createValidator.validate("create savings 12345671 0");
		assertTrue(actual);
	}

	@Test
	void create_account_with_highest_acceptable_apr() {
		boolean actual = createValidator.validate("create savings 12345671 10.0");
		assertTrue(actual);
	}

	@Test
	void create_account_with_lowest_boundary_id() {
		boolean actual = createValidator.validate("create savings 00000000 1.0");
		assertTrue(actual);
	}

	@Test
	void create_account_with_highest_boundary_id_without_range() {
		boolean actual = createValidator.validate("create savings 99999999 1.0");
		assertTrue(actual);
	}

	@Test
	void create_cd_account_with_below_minimum_initial_balance() {
		boolean actual = createValidator.validate("create savings 12345671 1.0 999.99");
		assertFalse(actual);
	}

	@Test
	void create_cd_account_with_above_maximum_initial_balance() {
		boolean actual = createValidator.validate("create savings 12345671 1.0 10000.01");
		assertFalse(actual);
	}

	@Test
	void create_cd_account_without_initial_balance() {
		boolean actual = createValidator.validate("create cd 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_non_offer_account_type_account() {
		boolean actual = createValidator.validate("create moneyMarket 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_case_insensitive() {
		boolean actual = createValidator.validate("CREATE SAVINGS 12345671 1.0");
		assertTrue(actual);
	}

	@Test
	void create_command_with_create_typo() {
		boolean actual = createValidator.validate("ceate savings 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_with_checking_typo() {
		boolean actual = createValidator.validate("create cheking 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_with_savings_typo() {
		boolean actual = createValidator.validate("create cs 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_with_extra_space_in_beginning() {
		boolean actual = createValidator.validate(" create savings 12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_with_extra_space_in_middle() {
		boolean actual = createValidator.validate("create savings  12345671 1.0");
		assertFalse(actual);
	}

	@Test
	void create_command_with_extra_space_in_end() {
		boolean actual = createValidator.validate("create savings 12345671 1.0 ");
		assertTrue(actual);
	}

}
