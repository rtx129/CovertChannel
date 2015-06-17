public class SecurityLevel {

	static final int HIGH = 2;
	static final int LOW = 1;


	// dominates relationship
	public static boolean dominates(int sublevel, int objlevel){
		if(sublevel >= objlevel){
			return true;
		}else{
			return false;
		}
	}


		// *-property of write access
	public static boolean canWrite(int sublevel, int objlevel){
		if(sublevel <= objlevel){ 
			return true;
		} else{
			return false;
		}
			
	}
	
}
