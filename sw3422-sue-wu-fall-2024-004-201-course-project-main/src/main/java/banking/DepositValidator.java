package banking;

public class DepositValidator extends Validator {
	private String[] arguments;

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		arguments = command.split(" ");

		if ((arguments.length == 3) && arguments[0].equalsIgnoreCase("deposit") && isValidID(arguments[1])
				&& isValidDepositAmount(arguments[2])) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isValidDepositAmount(String amount) {
		double maxAmount = bank.getAccounts().get(arguments[1]).getMaxDepositAmount();

		if (maxAmount == 0) {
			return false;
		} else {
			return isWithinRange(amount, 0, maxAmount);
		}

	}

}
