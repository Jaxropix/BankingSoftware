package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	private static final double DEPOSIT_AMOUNT = 20.0;
	public static double WITHDRAW_AMOUNT = 20.0;
	Account savings;

	@BeforeEach
	public void setUp() {
		savings = new Savings(Account.ACCOUNT_TEST_APR, Account.ACCOUNT_TEST_ID);
	}

	@Test
	public void get_apr() {
		double actual = savings.getApr();
		Assertions.assertEquals(Account.ACCOUNT_TEST_APR, actual);
	}

	@Test
	public void add_20_dollars() {
		savings.deposit(DEPOSIT_AMOUNT);
		double actual = savings.getBalance();

		Assertions.assertEquals(Account.ACCOUNT_DEFAULT_BALANCE + DEPOSIT_AMOUNT, actual);
	}

	@Test
	public void add_money_twice() {
		savings.deposit(DEPOSIT_AMOUNT);
		savings.deposit(DEPOSIT_AMOUNT);
		double actual = savings.getBalance();
		Assertions.assertEquals(Account.ACCOUNT_DEFAULT_BALANCE + DEPOSIT_AMOUNT + DEPOSIT_AMOUNT, actual);

	}

	@Test
	public void withdraw_20() {
		savings.deposit(DEPOSIT_AMOUNT);
		savings.withdraw(WITHDRAW_AMOUNT);
		double actual = savings.getBalance();
		assertEquals(DEPOSIT_AMOUNT - WITHDRAW_AMOUNT, actual, .0001);
	}

	@Test
	public void withdraw_20_twice() {
		savings.deposit(DEPOSIT_AMOUNT);
		savings.deposit(DEPOSIT_AMOUNT);
		savings.withdraw(WITHDRAW_AMOUNT);
		savings.withdraw(WITHDRAW_AMOUNT);
		double actual = savings.getBalance();
		assertEquals(DEPOSIT_AMOUNT + DEPOSIT_AMOUNT - WITHDRAW_AMOUNT - WITHDRAW_AMOUNT, actual);
	}

	@Test
	public void withdraw_more_than_available() {
		savings.withdraw(WITHDRAW_AMOUNT);
		double actual = savings.getBalance();
		assertEquals(0.0, actual);
	}

}
