import java.io.*;
import java.util.*;

public class SecureSystem {
	ReferenceMonitor refMon = new ReferenceMonitor();
	static InstructionObject instrobj;
	//static HashMap<String, SecurityLevel> subjectManager = new HashMap<String, SecurityLevel>();
	static HashMap<String, Integer> subjectManager = new HashMap<String, Integer>();


	static void passInstructions(String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			// parse the instruction array into instruction object
			instrobj = new InstructionObject(instructions[i]);
		}
	}

	// SecureSystem constructor
	public SecureSystem(String fileName) throws FileNotFoundException {

	}

	// Constructor for a subject manager
	void createSubject(String name, int secLev) {
		subjectManager.put(name, secLev);
	}

	// Returns the subject manager
	public static HashMap<String, Integer> getSubjectManager() {
		return subjectManager;
	}

	// Returns the reference monitor
	public ReferenceMonitor getReferenceMonitor() {
		return refMon;
	}

}
