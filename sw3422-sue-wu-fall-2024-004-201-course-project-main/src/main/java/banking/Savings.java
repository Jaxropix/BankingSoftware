package banking;

public class Savings extends Account {

	public Savings(double apr, String id) {
		super(ACCOUNT_DEFAULT_BALANCE, apr, id);
	}

	@Override
	public boolean isSavingsAccount() {
		return true;
	}

	@Override
	public double getMaxDepositAmount() {
		return 2500;
	}

	@Override
	public double getMaxWithdrawAmount() {
		return 1000;
	}

}
