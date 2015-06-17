import java.util.HashMap;

public class ObjectManager {

	static HashMap<String, Integer> objectManager = new HashMap<String, Integer>();
	static HashMap<String, Integer> valueManager = new HashMap<String, Integer>();
	static HashMap<String, Integer> readManager = new HashMap<String, Integer>();

	// Returns the object manager
	public static HashMap<String, Integer> getObjectManager() {
		return objectManager;
	}

	// Returns the value manager
	public static HashMap<String, Integer> getValueManager() {
		return valueManager;
	}

	// Returns the read manager
	public static HashMap<String, Integer> getReadManager() {
		return readManager;
	}

	// Creates a new object by placing it in the object manager
	static void createNewObject(String name, int secLev) {
		objectManager.put(name, secLev);
		valueManager.put(name, 0);
	}

	// Only reaches here after being verified by the Reference Monitor that the
	// instruction is valid, then executes the write instruction
	static void writeExecute(InstructionObject instr) {
		int val = instr.getValue();
		String obj = instr.getObject();

		valueManager.put(obj, val);
	}
	
	static void destroyExecute(InstructionObject instr) {
		String obj = instr.getObject();
		objectManager.remove(obj);
	}

	// Only reaches here after being verified by the Reference Monitor that the
	// instruction is valid, then executes the read instruction
	static void readExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		readManager.put(subj, valueManager.get(obj));

	}

	// If the instruction is an invalid read, sets the recently read object to 0
	static void badReadExecute(InstructionObject instr) {
		String subj = instr.getSubject();

		readManager.put(subj, 0);

	}

}
