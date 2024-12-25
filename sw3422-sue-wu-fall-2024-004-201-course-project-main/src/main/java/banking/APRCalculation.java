package banking;

public class APRCalculation {

	public void calcMonthly(Account account) {
		double apr = account.getApr();
		double balance = account.getBalance();

		apr = (apr / 100) / 12;
		balance = balance * apr;
		account.deposit(balance);
	}

	public void calcCDMonthly(Account account) {
		for (int i = 0; i < 4; i++) {
			calcMonthly(account);
		}
	}
}
