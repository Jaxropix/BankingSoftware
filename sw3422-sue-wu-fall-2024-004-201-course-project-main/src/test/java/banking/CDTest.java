package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
	public static double INITIAL_BALANCE = 1000.00;
	Account cd;

	@BeforeEach
	void setUp() {
		cd = new CD(INITIAL_BALANCE, Account.ACCOUNT_TEST_APR, Account.ACCOUNT_TEST_ID);
	}

	@Test
	public void cd_initialize_with_1000_balance() {
		double actual = cd.getBalance();
		assertEquals(INITIAL_BALANCE, actual);
	}

	@Test
	public void cd_get_max_deposit_amount() {
		double actual = cd.getMaxDepositAmount();
		assertEquals(0, actual);
	}

	@Test
	public void cd_get_max_withdraw_amount() {
		double actual = cd.getMaxWithdrawAmount();
		assertEquals(INITIAL_BALANCE, actual);
	}

	@Test
	public void cd_account_get_isWithdrawable() {
		boolean actual = cd.getIsWithdrawable();
		assertFalse(actual);
	}

}
