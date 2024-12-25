package banking;

import java.util.List;

public class MasterControl {
	private final Validator validator;
	private final CommandProcessor commandProcessor;
	private final CommandStorage commandStorage;

	public MasterControl(Validator validator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
		this.validator = validator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (validator.validateCommand(command)) {
				commandProcessor.processCommand(command);
				commandStorage.addTransaction(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}
		return commandStorage.createOutput();
	}
}
