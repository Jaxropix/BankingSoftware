package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public Account getAccountById(String id) {
		return accounts.get(id);
	}

	public void addAccount(String id, Account account) {
		accounts.put(id, account);
	}

	public boolean accountExistsByID(String ID) {
		if (accounts.get(ID) != null) {
			return true;
		} else {
			return false;
		}
	}
}
