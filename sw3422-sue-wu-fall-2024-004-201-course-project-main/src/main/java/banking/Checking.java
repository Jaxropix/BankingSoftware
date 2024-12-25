package banking;

public class Checking extends Account {

	public Checking(double apr, String id) {
		super(ACCOUNT_DEFAULT_BALANCE, apr, id);
	}

	@Override
	public double getMaxDepositAmount() {
		return 1000;
	}

	@Override
	public double getMaxWithdrawAmount() {
		return 400;
	}

}
