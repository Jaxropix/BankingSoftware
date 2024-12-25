package banking;

public class CommandProcessor {
	protected Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String command) {
		String action = getCommand(command);
		if (action.equals("create")) {
			new CreateProcessor(bank).create(command);
		} else if (action.equals("deposit")) {
			new DepositProcessor(bank).deposit(command);
		} else if (action.equals("withdraw")) {
			new WithdrawProcessor(bank).withdraw(command);
		} else if (action.equals("transfer")) {
			new TransferProcessor(bank).transfer(command);
		} else if (action.equals("pass")) {
			new PassTimeProcessor(bank).passTime(command);
		}

	}

	public String getCommand(String command) {
		String[] arguments = command.split(" ");
		return arguments[0].toLowerCase();
	}

}