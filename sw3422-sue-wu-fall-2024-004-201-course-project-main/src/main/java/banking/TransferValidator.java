package banking;

public class TransferValidator extends Validator {
	private String[] arguments;

	public TransferValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		arguments = command.split(" ");
		if (arguments.length == 4 && arguments[0].equalsIgnoreCase("transfer")
				&& isValidDifferentAccounts(arguments[1], arguments[2]) && isValidAmount(arguments[3])) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isValidDifferentAccounts(String account1, String account2) {
		if (isValidID(account1) && isValidAccountType(account1) && isValidID(account2)
				&& isValidAccountType(account2)) {
			return !account1.equals(account2);
		}
		return false;
	}

	public boolean isValidAmount(String amount) {
		return isWithinRange(amount, 0, Double.MAX_VALUE);
	}

	public boolean isValidAccountType(String id) {
		Account account = bank.getAccounts().get(id);
		return !account.isCDAccount();
	}

}
