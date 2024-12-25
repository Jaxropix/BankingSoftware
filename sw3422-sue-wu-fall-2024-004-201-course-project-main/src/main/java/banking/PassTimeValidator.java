package banking;

public class PassTimeValidator extends Validator {
	String[] arguments;

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		arguments = command.split(" ");
		return arguments.length == 2 && arguments[0].equalsIgnoreCase("pass") && isValidMonths(arguments[1]);
	}

	public boolean isValidMonths(String months) {
		if (months.matches("-?\\d+")) {
			return isWithinRange(months, 1, 60);
		} else {
			return false;
		}
	}

}
