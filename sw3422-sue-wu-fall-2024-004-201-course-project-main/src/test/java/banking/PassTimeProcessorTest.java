package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	Bank bank;
	PassTimeProcessor passTimeProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		passTimeProcessor = new PassTimeProcessor(bank);
		bank.addAccount("12345671", new Savings(0, "12345671"));
		bank.addAccount("12345672", new Checking(0, "12345672"));
		bank.addAccount("12345673", new CD(1000, 0, "12345673"));
	}

	@Test
	void pass_time_min_months() {
		passTimeProcessor.passTime("pass 1");
		Account account = bank.getAccountById("12345673");
		assertEquals(1, account.months);
	}

	@Test
	void pass_time_within_range_days() {
		passTimeProcessor.passTime("pass 23");
		Account account = bank.getAccountById("12345673");
		assertEquals(23, account.getMonths());
	}

	@Test
	void pass_time_max_months() {
		passTimeProcessor.passTime("pass 60");
		Account account = bank.getAccountById("12345673");
		assertEquals(60, account.getMonths());
	}

	@Test
	void pass_time_two_times() {
		passTimeProcessor.passTime("pass 12");
		passTimeProcessor.passTime("pass 12");
		Account account = bank.getAccountById("12345673");
		assertEquals(24, account.getMonths());
	}

	@Test
	void pass_time_all_account_with_the_same_months() {
		bank.getAccountById("12345671").deposit(100);
		bank.getAccountById("12345672").deposit(100);

		passTimeProcessor.passTime("pass 5");
		assertEquals(5, bank.getAccountById("12345671").getMonths());
		assertEquals(5, bank.getAccountById("12345672").getMonths());
		assertEquals(5, bank.getAccountById("12345673").getMonths());
	}

	@Test
	void does_not_deduct_25_in_an_account_with_100() {
		bank.getAccountById("12345671").deposit(100);
		passTimeProcessor.passTime("pass 1");
		Account account = bank.getAccountById("12345671");
		assertEquals(100, account.getBalance(), 0.1);
	}

	@Test
	void calc_savings_apr_monthly_correctly() {
		bank.addAccount("12345674", new Savings(0.6, "12345674"));
		bank.getAccountById("12345674").deposit(5000);
		passTimeProcessor.passTime("pass 1");
		double balance = bank.getAccountById("12345674").getBalance();
		assertEquals(5002.50, balance);
	}

	@Test
	void calc_checking_apr_monthly_correctly() {
		bank.addAccount("12345675", new Checking(0.6, "12345675"));
		bank.getAccountById("12345675").deposit(5000);
		passTimeProcessor.passTime("pass 1");
		double balance = bank.getAccountById("12345675").getBalance();
		assertEquals(5002.50, balance);
	}

	@Test
	void calc_cd_apr_monthly_correctly() {
		bank.addAccount("12345676", new CD(2000, 2.1, "12345676"));
		passTimeProcessor.passTime("pass 1");
		double balance = bank.getAccountById("12345676").getBalance();
		assertEquals(2014.036792893758, balance, 0.01);
	}

	@Test
	void close_account() {
		bank.getAccountById("12345672").deposit(100);
		passTimeProcessor.passTime("pass 1");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	void close_multiple_account_in_one_month() {
		passTimeProcessor.passTime("pass 1");
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	void close_account_one_month_at_a_time() {
		bank.getAccountById("12345672").deposit(100);
		passTimeProcessor.passTime("pass 1");
		assertEquals(2, bank.getAccounts().size());
		bank.getAccountById("12345672").withdraw(100);
		passTimeProcessor.passTime("pass 2");
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	void close_deduct_25() {
		bank.getAccountById("12345671").deposit(75);
		passTimeProcessor.passTime("pass 1");
		assertEquals(50, bank.getAccountById("12345671").getBalance(), 0.1);
	}

	@Test
	void close_deduct_25_two_months_in_a_row() {
		bank.getAccountById("12345671").deposit(75);
		passTimeProcessor.passTime("pass 2");
		assertEquals(25, bank.getAccountById("12345671").getBalance(), 0.1);
	}

	@Test
	void deduct_25_per_month_until_account_close() {
		bank.getAccountById("12345671").deposit(75);
		passTimeProcessor.passTime("pass 1");
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	void pendingCloseAccount_is_clear_after_a_month() {
		passTimeProcessor.passTime("pass 1");
		assertEquals(0, passTimeProcessor.pendingCloseAccounts.size());
	}

	@Test
	void cd_account_withdrawable_variable_still_false_before_twelve_months() {
		passTimeProcessor.passTime("pass 11");
		assertFalse(bank.getAccountById("12345673").getIsWithdrawable());
	}

	@Test
	void cd_account_withdrawable_variable_is_true_at_twelve_months() {
		passTimeProcessor.passTime("pass 12");
		assertTrue(bank.getAccountById("12345673").getIsWithdrawable());
	}

}
