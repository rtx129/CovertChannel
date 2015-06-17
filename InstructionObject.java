public class InstructionObject {

	private String instruction;
	private String subject;
	private String object;
	private int value;

	public InstructionObject(String instr) {
		String[] elementList = instr.split(" ");
		String command = elementList[0].toLowerCase();
		int length = elementList.length;

		if (command.equals("run")) {
			this.instruction = "RUN";
			this.subject = elementList[1];
			ReferenceMonitor.runExecute(getInstructionObject());
		} else if ((length != 4 && command.equals("write"))
				|| (length != 3 && command.equals("read"))
				|| (length != 3 && command.equals("create"))
				|| (length != 3 && command.equals("destroy"))
				|| (length != 2 && command.equals("run"))) {
			this.instruction = "BAD";
		} else if (!command.equals("read")
				&& !command.equals("write")
				&& !command.equals("create")
				&& !command.equals("destroy")
				&& !command.equals("run")) {
			this.instruction = "BAD";
		} else if (!SecureSystem.getSubjectManager().containsKey(elementList[1])
			|| !ObjectManager.getObjectManager().containsKey(elementList[2])
				&& !command.equals("create") || elementList[2].equals("write") && 
				!isInt(elementList[3]) || command.equals("destroy")
				&& !ObjectManager.getObjectManager().containsKey(elementList[2])
				|| command.equals("create")
				&& ObjectManager.getObjectManager().containsKey(elementList[2])) {
			this.instruction = "BAD";	
		} else if (command.equals("read")) {
			this.instruction = "READ";
			this.subject = elementList[1];
			this.object = elementList[2];
			ReferenceMonitor.readExecute(getInstructionObject());
		} else if (command.equals("write")) {
			this.instruction = "WRITE";
			this.subject = elementList[1];
			this.object = elementList[2];
			this.value = Integer.parseInt(elementList[3]);
			ReferenceMonitor.writeExecute(getInstructionObject());
		} else if (command.equals("create")) {
			this.instruction = "CREATE";
			this.subject = elementList[1];
			this.object = elementList[2];
			ReferenceMonitor.createExecute(getInstructionObject());
		} else if (command.equals("destroy")) {
			this.instruction = "DESTROY";
			this.subject = elementList[1];
			this.object = elementList[2];
			ReferenceMonitor.destroyExecute(getInstructionObject());
		}
	}

	public InstructionObject getInstructionObject() {
		return this;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getObject() {
		return this.object;
	}

	public int getValue() {
		return this.value;
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}