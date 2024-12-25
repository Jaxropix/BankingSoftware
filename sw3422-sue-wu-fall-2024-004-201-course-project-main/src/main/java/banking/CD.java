package banking;

public class CD extends Account {

	public CD(double balance, double apr, String id) {
		super(balance, apr, id);
		isWithdrawable = false;
	}

	@Override
	public boolean isCDAccount() {
		return true;
	}

	@Override
	public double getMaxDepositAmount() {
		return 0;
	}

	@Override
	public double getMaxWithdrawAmount() {
		return this.getBalance();
	}
}
