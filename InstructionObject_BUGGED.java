public class InstructionObject {

	private String instruction;
	private String subject;
	private String object;
	private int value;

	// assign & filter the input string line to appropriate field
	public InstructionObject(String input) {
		
		// split the input string line by space and put inton a string array
		String[] elementList = input.split(" ");
		int length = elementList.length;
		String command = elementList[0].toUpperCase();

			
		// check the length of the string list and assign value
		if(length == 3 || length == 4){
			
			// check validity of instructions
			if(!command.equals("WRITE") && !command.equals("READ") && !command.equals("CREATE") && !command.equals("DESTROY")) {
				instruction = "BAD";
			} else {
				subject = elementList[1].toLowerCase();
				object = elementList[2].toLowerCase();

				if(!SecureSystem.getSubjectManager().containsKey(subject) || !ObjectManager.getObjectManager().containsKey(object){
					instruciton = "BAD";
				}
			}

		} else if(length == 2){
			if(!command.eqauls("RUN")) || !SecureSystem.getSubjectManager().containsKey(subject)){
					instruction = "BAD";
			}
		} else  {
			instruction = "BAD";
		}

		// length equals 3, then process instructions of READ, CREATE and DESTROY
		if(length == 3){

			// READ
			if(command.equals("READ")){
				instruction = "READ";
				ReferenceMonitor.readExecute(getInstructionObject());
			}
			// CREATE
			else if(command.equals("CREATE")){
				instruction = "CREATE";
				ReferenceMonitor.createExecute(getInstructionObject());
			}
			// DESTROY
			else if(command.equals("DESTROY")){
				instuction = "DESTROY";
				ReferenceMonitor.destroyExecute(getInstructionObject());
			}
			else{
				instruction = "BAD";
			}

		}

		// length of 4, check the validity of WRITE qualifications
		if(command.equals("WRITE") && length == 4 ){
			
			try {
				// parse string to integer for the 4th string to int value
				value = Integer.parseInt(elementList[3]);
				ReferenceMonitor.writeExecute(getInstructionObject());
			} catch(NumberFormatException e) {
				instruction = "BAD";
			}
						
		} else if(command.equals("WRITE") && length != 4){
			instruction = "BAD";
		}

		if(length == 2 && command.equals("RUN")){
			instruction = "RUN";
			subject = elementList[1].toLowerCase();
			ReferenceMonitor.runExecute(getInstructionObject());
		}
	}
}
