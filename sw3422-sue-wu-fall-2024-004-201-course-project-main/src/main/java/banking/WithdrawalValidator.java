package banking;

public class WithdrawalValidator extends Validator {
	String[] arguments;

	public WithdrawalValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		arguments = command.split(" ");

		if (arguments.length == 3 && arguments[0].equalsIgnoreCase("withdraw") && isValidID(arguments[1])) {
			if (bank.getAccountById(arguments[1]).isCDAccount()) {
				return isValidCDWithdrawAmount(arguments[2]) && bank.getAccountById(arguments[1]).isWithdrawable;
			} else if (bank.getAccountById(arguments[1]).isSavingsAccount()) {
				return isValidWithdrawAmount(arguments[2]) && bank.getAccountById(arguments[1]).isWithdrawable;
			} else {
				return isValidWithdrawAmount(arguments[2]);
			}
		} else {
			return false;
		}
	}

	public boolean isValidWithdrawAmount(String amount) {
		double maxAmount = bank.getAccountById(arguments[1]).getMaxWithdrawAmount();
		return isWithinRange(amount, 0, maxAmount);
	}

	public boolean isValidCDWithdrawAmount(String amount) {
		Account account = bank.getAccountById(arguments[1]);
		return isWithinRange(amount, account.getBalance(), Double.MAX_VALUE);

	}

}
