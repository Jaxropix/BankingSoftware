package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class APRCalculationTest {
	Bank bank;
	APRCalculation calculation;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		calculation = new APRCalculation();
	}

	@Test
	void savings_with_zero_point_six_apr_and_5000_balance() {
		bank.addAccount("12345671", new Savings(0.6, "12345671"));
		bank.getAccounts().get("12345671").deposit(5000);
		calculation.calcMonthly(bank.getAccounts().get("12345671"));
		double balance = bank.getAccounts().get("12345671").getBalance();
		assertEquals(5002.50, balance);
	}

	@Test
	void cd_with_two_point_one_apr_and_2000_balance() {
		bank.addAccount("12345671", new CD(2000, 2.1, "12345671"));
		calculation.calcCDMonthly(bank.getAccounts().get("12345671"));
		double balance = bank.getAccounts().get("12345671").getBalance();
		assertEquals(2014.036792893758, balance, 0.1);
	}

}
