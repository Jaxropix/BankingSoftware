package banking;

public class TransferProcessor extends CommandProcessor {
	Bank bank;
	String[] arguments;
	Account depositAccount;
	Account withdrawAccount;
	double pendingAmount;

	public TransferProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void transfer(String command) {
		arguments = command.split(" ");

		String withdrawId = arguments[1];
		String depositId = arguments[2];
		pendingAmount = Double.parseDouble(arguments[3]);

		withdrawAccount = this.bank.getAccounts().get(withdrawId);
		depositAccount = this.bank.getAccounts().get(depositId);

		double amount = getWithdrawAmount(withdrawAccount, pendingAmount);

		withdrawAccount.withdraw(amount);
		depositAccount.deposit(amount);
	}

	public double getWithdrawAmount(Account account, double pendingAmount) {
		return Math.min(account.getBalance(), pendingAmount);

	}
}
