package banking;

import java.util.ArrayList;

public class PassTimeProcessor extends CommandProcessor {
	Bank bank;
	String[] arguments;
	int months;
	ArrayList<String> pendingCloseAccounts;

	public PassTimeProcessor(Bank bank) {
		super(bank);
		this.bank = bank;
		pendingCloseAccounts = new ArrayList<>();
	}

	public void passTime(String command) {
		arguments = command.split(" ");
		months = Integer.parseInt(arguments[1]);

		for (int i = 0; i < months; i++) {
			for (Account account : this.bank.getAccounts().values()) {
				account.months++;
				monthlyChecks(account);
				calcAPRCalc(account);
			}
			closeAccount(pendingCloseAccounts);
		}
	}

	public void monthlyChecks(Account account) {

		if (account.getBalance() == 0) {
			pendingCloseAccounts.add(account.getId());
		} else if (account.getBalance() < 100) {
			account.withdraw(25);
		}
		updateFlags(account);
	}

	private void calcAPRCalc(Account account) {
		APRCalculation calculation = new APRCalculation();
		if (account.isCDAccount()) {
			calculation.calcCDMonthly(account);
		} else {
			calculation.calcMonthly(account);
		}

	}

	private void closeAccount(ArrayList<String> pendingAccounts) {
		for (String id : pendingAccounts) {
			bank.getAccounts().remove(id);
		}
		pendingCloseAccounts.clear();
	}

	private void updateFlags(Account account) {
		if (account.isCDAccount()) {
			checkCDCanWithdrawFlag((CD) account);
		} else if (account.isSavingsAccount()) {
			checkSavingsCanWithdrawFlag((Savings) account);
		}
	}

	private void checkCDCanWithdrawFlag(CD account) {
		if (account.months >= 12) {
			account.setCanWithdraw();
		}
	}

	private void checkSavingsCanWithdrawFlag(Savings account) {
		if (!account.isWithdrawable) {
			account.setCanWithdraw();
		}
	}

}
