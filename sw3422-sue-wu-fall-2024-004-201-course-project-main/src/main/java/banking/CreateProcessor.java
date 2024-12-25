package banking;

public class CreateProcessor extends CommandProcessor {
	String[] arguments;
	String accountType;
	String accountNumber;
	double accountApr;
	double accountBalance;

	public CreateProcessor(Bank bank) {
		super(bank);
	}

	public void create(String command) {
		arguments = command.split(" ");
		accountType = arguments[1];
		accountNumber = arguments[2];
		accountApr = Double.parseDouble(arguments[3]);

		if (accountType.equalsIgnoreCase("savings")) {
			Savings savingAccount = new Savings(accountApr, accountNumber);
			this.bank.addAccount(accountNumber, savingAccount);
		} else if (accountType.equalsIgnoreCase("checking")) {
			Checking checkingAccount = new Checking(accountApr, accountNumber);
			this.bank.addAccount(accountNumber, checkingAccount);
		} else {
			accountBalance = Double.parseDouble(arguments[4]);
			CD cd = new CD(accountBalance, accountApr, accountNumber);
			this.bank.addAccount(accountNumber, cd);

		}

	}
}
