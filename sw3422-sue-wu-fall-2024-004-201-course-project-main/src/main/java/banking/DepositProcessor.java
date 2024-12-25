package banking;

public class DepositProcessor extends CommandProcessor {
	private Bank bank;
	private String accountID;
	private double amount;

	public DepositProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
	}

	public void deposit(String command) {
		String[] arguments = command.split(" ");
		accountID = arguments[1];
		amount = Double.parseDouble(arguments[2]);
		bank.getAccounts().get(accountID).deposit(amount);
	}

}
