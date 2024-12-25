package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

	protected Bank bank;
	List<String> invalidCommands;
	List<String> entireHistory;

	public CommandStorage(Bank bank) {
		this.bank = bank;
		invalidCommands = new ArrayList<>();
		entireHistory = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public void addTransaction(String command) {
		entireHistory.add(command);
	}

	public List<String> getAccountHistory(String ID) {
		ArrayList<String> accountHistory = new ArrayList<>();
		for (String transaction : entireHistory) {
			if (transaction.contains(ID) && !transaction.toLowerCase().contains("create")) {
				accountHistory.add(transaction);
			}
		}
		return accountHistory;
	}

	public List<String> createOutput() {
		ArrayList<String> output = new ArrayList<>();
		for (String accountID : bank.getAccounts().keySet()) {
			output.add(getCurrentState(accountID));
			output.addAll(getAccountHistory(accountID));
		}
		output.addAll(getInvalidCommands());
		return output;

	}

	public String getAccountType(String id) {
		if (bank.getAccountById(id).isCDAccount()) {
			return "Cd";
		} else if (bank.getAccountById(id).isSavingsAccount()) {
			return "Savings";
		} else {
			return "Checking";
		}
	}

	public String getCurrentState(String id) {
		Account account = bank.getAccountById(id);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		String balance = decimalFormat.format(account.getBalance());
		String apr = decimalFormat.format(account.getApr());
		return getAccountType(id) + " " + id + " " + balance + " " + apr;
	}

}
