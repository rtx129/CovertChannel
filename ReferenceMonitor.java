import java.util.*;

public class ReferenceMonitor {

	static HashMap<String, String> runManager = new HashMap<String, String>();
	static String resultLine;

	public ReferenceMonitor() {
		// Initially add a blank Hal and Lyle to the read manager
		ObjectManager.getReadManager().put("HAL", 0);
		ObjectManager.getReadManager().put("LYLE", 0);
		runManager.put("HAL", "temp");
		runManager.put("LYLE", "temp");
	}

	// Create a new object within the object manager
	static void create(String name, int secLev) {
		ObjectManager.create(name, secLev);
	}

	// Return Hashmap that maintains the RUN call
	public static HashMap<String, String> getRunManager() {
		return runManager;
	}

	// Return a completed byte in a string form
	public static String getResultLine() {
		return resultLine;
	}

	 
	 // Applied *-Property for Write method and Destroy method
	static void writeExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		int subjSeclvl = SecureSystem.getSubjectManager().get(subj);
		int objSeclvl = ObjectManager.getObjectManager().get(obj);

		if (SecurityLevel.canWrite(subjSeclvl,objSeclvl)){
			ObjectManager.write(instr);
		}
	}

	 // Applied Simple Security Property for Read method
	static void readExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		int subjSeclvl = SecureSystem.getSubjectManager().get(subj);
		int objSeclvl = ObjectManager.getObjectManager().get(obj);


		if (SecurityLevel.dominates(subjSeclvl, objSeclvl)){
			ObjectManager.read(instr);
		} else {
			ObjectManager.bad(instr);

		}
	}

	// Create a new object
	static void createExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		int subjSec = SecureSystem.getSubjectManager().get(subj);
		create(instr.getObject(), subjSec);
	}

	// Execute the RUN call.
	static void runExecute(InstructionObject instr) {
		int temp = ObjectManager.getReadManager().get(instr.getSubject());
		String curBit = runManager.get(instr.getSubject());
		// If first bit for the byte
		if (curBit.equals("temp")) {
			if (temp != 0) {
				curBit = "1";
				runManager.put(instr.getSubject(), curBit);
			} else {
				curBit = "0";
				runManager.put(instr.getSubject(), curBit);
			}
		}
		// Otherwise, make sure less than 8 bits
		else if (curBit.length() < 8) {
			if (temp != 0) {
				curBit = curBit.concat("1");
				runManager.put(instr.getSubject(), curBit);
			} else {
				curBit = curBit.concat("0");
				runManager.put(instr.getSubject(), curBit);
			}
		}
		// If 8 bits, we have a byte so make that the result string
		if (curBit.length() == 8) {
			resultLine = curBit;
			int charTemp = Integer.parseInt(resultLine, 2);
			resultLine = new Character((char) charTemp).toString();
		}

	}

	// Execute the DESTROY call. Check to make sure subject has the correct
	// SecurityLevel
	static void destroyExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		int subjSeclvl = SecureSystem.getSubjectManager().get(subj);
		int objSeclvl = ObjectManager.getObjectManager().get(obj);

		if (SecurityLevel.canWrite(subjSeclvl,objSeclvl)) {
			ObjectManager.destroy(instr);
		} else {
			System.out.println("invalid instruction");
		}
	}

}
