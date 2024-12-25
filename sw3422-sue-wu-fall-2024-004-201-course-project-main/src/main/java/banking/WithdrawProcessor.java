package banking;

public class WithdrawProcessor extends CommandProcessor {
	Bank bank;
	String[] arguments;
	Account account;
	Double amount;

	public WithdrawProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void withdraw(String command) {
		arguments = command.split(" ");
		account = bank.getAccountById(arguments[1]);
		amount = Double.parseDouble(arguments[2]);

		if (account.isSavingsAccount()) {
			account.isWithdrawable = false;
		}

		account.withdraw(amount);
	}

}
