package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {
	Account checking;

	@BeforeEach
	public void setUp() {
		checking = new Checking(Account.ACCOUNT_TEST_APR, Account.ACCOUNT_TEST_ID);
	}

	@Test
	public void checking_account_initialize_with_default_balance() {
		double actual = checking.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void get_checking_account_max_deposit_amount() {
		double actual = checking.getMaxDepositAmount();
		assertEquals(1000, actual);
	}

	@Test
	public void get_checking_account_max_withdraw_amount() {
		double actual = checking.getMaxWithdrawAmount();
		assertEquals(400, actual);
	}

	@Test
	public void get_isWithdrawable_in_checking() {
		Checking checking = new Checking(0, "12345672");
		boolean actual = checking.getIsWithdrawable();
		assertEquals(true, actual);
	}
}
