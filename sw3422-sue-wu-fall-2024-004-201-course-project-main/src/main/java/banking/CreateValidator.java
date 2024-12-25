package banking;

public class CreateValidator extends Validator {
	private String[] arguments;

	public CreateValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		arguments = command.split(" ");
		if (arguments[0].equalsIgnoreCase("create") && isValidAccountTypeCommand(arguments[1])
				&& isValidID(arguments[2]) && isValidApr(arguments[3])) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isValidAccountTypeCommand(String AccountType) {
		if ((AccountType.equalsIgnoreCase("savings") || AccountType.equalsIgnoreCase("checking"))
				&& arguments.length == 4) {
			return true;
		} else if (AccountType.equalsIgnoreCase("cd") && arguments.length == 5 && isValidInitialBalance(arguments[4])) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isValidID(String id) {
		if (id.length() == 8 && id.matches("\\d+")) {
			return !this.bank.accountExistsByID(id);
		} else {
			return false;
		}
	}

	public boolean isValidApr(String commandApr) {
		return isWithinRange(commandApr, 0, 10);
	}

	public boolean isValidInitialBalance(String commandBalance) {
		return isWithinRange(commandBalance, 1000, 10000);
	}

}
