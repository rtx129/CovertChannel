import java.io.*;
import java.util.*;

public class SecureSystem {
	ReferenceMonitor monitor = new ReferenceMonitor();
	static InstructionObject instr;
	static HashMap<String, Integer> subjectMap = new HashMap<String, Integer>();


	static void passInstructions(String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			instr = new InstructionObject(instructions[i]);
		}
	}

	public SecureSystem(String fileName) throws FileNotFoundException {}

	void createSubject(String name, int level) {
		subjectMap.put(name, level);
	}

	public static HashMap<String, Integer> getSubjectManager() {
		return subjectMap;
	}

	public ReferenceMonitor getReferenceMonitor() {
		return monitor;
	}
}
