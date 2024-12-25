package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
	Account savings;

	@BeforeEach
	void setUp() {
		savings = new Savings(Account.ACCOUNT_TEST_APR, Account.ACCOUNT_TEST_ID);
	}

	@Test
	public void savings_can_be_created_with_default_balance() {
		double actual = savings.getBalance();
		assertEquals(Account.ACCOUNT_DEFAULT_BALANCE, actual);
	}

	@Test
	void is_savings_account() {
		boolean actual = savings.isSavingsAccount();
		assertTrue(actual);
	}

	@Test
	public void get_savings_max_deposit_amount() {
		double actual = savings.getMaxDepositAmount();
		assertEquals(2500, actual);
	}

	@Test
	public void get_savings_max_withdraw_amount() {
		double actual = savings.getMaxWithdrawAmount();
		assertEquals(1000, actual);
	}

	@Test
	public void get_isWithdrawable() {
		boolean actual = savings.getIsWithdrawable();
		Assertions.assertEquals(true, actual);
	}

}
