import java.io.*;
import java.util.*;

public class CovertChannel {

	static long numOfBits = 0;
	static boolean verbose;

	public static void main(String[] args) throws IOException {
		File infile;
		if (args[0].equals("v")) {
			System.out.println("VERBOSE");
			infile = new File(args[1]);
			verbose = true;
		} else {
			infile = new File(args[0]);
			verbose = false;
		}

		SecureSystem sys = new SecureSystem(args[0]);

		int low = SecurityLevel.LOW;
		int high = SecurityLevel.HIGH;

		// Create Lyle and Hal subjecs with appropriate Security levels
		sys.createSubject("LYLE", low);
		sys.createSubject("HAL", high);

		Scanner sc = new Scanner(infile);
		String fileName = infile.getName() + ".out";
		String title = "log.txt";
		byte[] newLine = System.getProperty("line.separator").getBytes();
		FileOutputStream outfile = new FileOutputStream(fileName);
		FileOutputStream logOut = new FileOutputStream(title);
		final long startTime = System.currentTimeMillis();


		// While file has next line
		while (sc.hasNextLine()) {
			String curLine = sc.nextLine();
			byte[] buf = curLine.getBytes();

			ByteArrayInputStream input = new ByteArrayInputStream(buf);
			int numOfBytes = input.available();

			// While there are still bytes in the file to be parsed
			while (numOfBytes > 0) {
				int inputByte = input.read();
				String inputBit = Integer.toBinaryString(inputByte);
				int inputLength = inputBit.length();
				numOfBits += 8;

				// Make all bytes length 8 by appending any needed 0's
				if (inputLength != 8) {
					while (inputLength != 8) {
						String zero = "0";
						inputBit = zero.concat(inputBit);
						inputLength = inputBit.length();
					}
				}
				String[] halCreate = { "CREATE HAL OBJ", "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
				String[] lyleCreate = { "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
				String log = "";
				// Based on bit being 0 or 1, run appropriate instructions

				for (int i = 0; i < inputLength; i++) {

					if (inputBit.charAt(i) == '0') {
						SecureSystem.passInstructions(halCreate);
						if (verbose) 
							log += Arrays.toString(halCreate);				
					} 
					// bit is 1
					else { 
						SecureSystem.passInstructions(lyleCreate);
						if (verbose) 
							log += Arrays.toString(lyleCreate);
					}
					if (verbose){ 
						byte[] logResult = log.getBytes();
						logOut.write(logResult);
						logOut.write(newLine);
					}

				}
				// Write byte to out file, then reset the run manager for the
				// next byte
				numOfBytes--;
				String result = ReferenceMonitor.getResultLine();
				byte[] resultArray = result.getBytes();
				outfile.write(resultArray);
				ReferenceMonitor.getRunManager().put("LYLE", "temp");
			}
			outfile.write(newLine);
		}
		final long endTime = System.currentTimeMillis();

		// Compute bandwidth
		double bytes = infile.length();
		long bandwidth = numOfBits / (endTime - startTime);
		System.out.println(
			"Document: " + infile.getName() + 
			"\nSize: " + bytes + " bytes" +
			"\nBandwidth: " + bandwidth + " bits/ms");
		sc.close();
		outfile.close();
		logOut.close();
	}

}
