package banking;

public class Validator {
	public String storedID;
	protected Bank bank;

	public Validator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateCommand(String command) {
		char firstChar = Character.toLowerCase(command.charAt(0));
		if (firstChar == 'c') {
			CreateValidator validator = new CreateValidator(bank);
			return validator.validate(command);
		} else if (firstChar == 'd') {
			DepositValidator validator = new DepositValidator(bank);
			return validator.validate(command);
		} else if (firstChar == 'w') {
			WithdrawalValidator validator = new WithdrawalValidator(bank);
			return validator.validate(command);
		} else if (firstChar == 't') {
			TransferValidator validator = new TransferValidator(bank);
			return validator.validate(command);
		} else if (firstChar == 'p') {
			PassTimeValidator validator = new PassTimeValidator(bank);
			return validator.validate(command);
		} else {
			return false;
		}

	}

	public boolean isValidID(String id) {
		if (id.length() != 8 || !id.matches("\\d+")) {
			return false;
		} else {
			storedID = id;
			return this.bank.accountExistsByID(id);
		}
	}

	public boolean isWithinRange(String commandValue, double minValue, double maxValue) {
		try {
			double value = Double.parseDouble(commandValue);
			return (minValue <= value && value <= maxValue);
		} catch (Exception NumberFormatException) {
			return false;
		}
	}

	public String getValidID() {
		return storedID;
	}

}
