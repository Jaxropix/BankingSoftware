package banking;

public abstract class Account {
	public static final double ACCOUNT_DEFAULT_BALANCE = 0.0;
	public static final double ACCOUNT_TEST_APR = 0.01;
	public static final String ACCOUNT_TEST_ID = "11111111";

	private final String id;
	private final double apr;
	public boolean isWithdrawable;
	public int months;
	private double balance;

	protected Account(double balance, double apr, String id) {
		this.balance = balance;
		this.apr = apr;
		this.id = id;
		this.months = 0;
		this.isWithdrawable = true;
	}

	public double getBalance() {
		return balance;
	}

	public double getApr() {
		return apr;
	}

	public String getId() {
		return id;
	}

	public int getMonths() {
		return months;
	}

	public boolean getIsWithdrawable() {
		return isWithdrawable;
	}

	public void deposit(double amountToDeposit) {
		this.balance += amountToDeposit;
	}

	public void withdraw(double amountToWithdraw) {
		if (amountToWithdraw >= this.balance) {
			this.balance = 0;
		} else {
			this.balance -= amountToWithdraw;
		}
	}

	public boolean isCDAccount() {
		return false;
	}

	public boolean isSavingsAccount() {
		return false;
	}

	public void setCanWithdraw() {
		this.isWithdrawable = true;
	}

	public abstract double getMaxDepositAmount();

	public abstract double getMaxWithdrawAmount();
}
