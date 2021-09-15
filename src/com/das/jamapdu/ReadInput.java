package com.das.jamapdu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



class ReadInput {
	private static final String INSTALL_FOR_LOAD = "INSTALL_FOR_LOAD";
	private static final String LOAD_FOR_LOAD = "LOAD_FOR_LOAD";
	private static final String INSTALL_FOR_INSTALL = "INSTALL_FOR_INSTALL";
	private static final String DeleteAppletCommand = "DeleteAppletCommand";


	static int readFile(String filepath) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filepath)))) {
			String line = null;
			String commandName = null;
			String previousCommand = null;
			int counter = 0;

			while ((line = br.readLine()) != null) {
				if (line.length() < 10) {
					continue;
				}
				line = line.replaceAll("\\W", " ");
				line = line.replace(" ", "");

				if (line.startsWith("84") || line.startsWith("80")) {
					String INS = line.substring(2, 4);
					String P1 = line.substring(4, 6);

					if (INS.equals("E6")) {
						if (P1.equals("02")) {
							commandName = INSTALL_FOR_LOAD;
						} else if (P1.equals("0C")) {
							commandName = INSTALL_FOR_INSTALL;
						}

					} else if (INS.equals("E8")) {
						commandName = LOAD_FOR_LOAD;

					} else if (INS.equals("E4")) {
						commandName = DeleteAppletCommand;
					}

				}
				if (commandName.equals(LOAD_FOR_LOAD)) {
					if (previousCommand.startsWith(LOAD_FOR_LOAD)) {
						counter++;
						commandName = commandName + (counter);
					} else {
						counter = 1;
						commandName = commandName + counter;
					}
				}

				int lengthtoParse = Integer.parseInt(line.substring(8, 10), 16);
				lengthtoParse *= 2;

				String final_OTA_APDU = ParseAPDUs.processCommand(line.substring(10, lengthtoParse+10)); 
				//substring: 5bytes of command excluded from data to be processed, 5 bytes= 10 digits/characters
				final_OTA_APDU = line.substring(0, 10) + final_OTA_APDU;
				int OTA_APDU_len = 2 * final_OTA_APDU.length();
				String final_len = Integer.toHexString(OTA_APDU_len).toUpperCase();
				System.out.println(commandName + ",JAM,22" + final_len + final_OTA_APDU);
				System.out.println("+++++++++++++++++\n");

				previousCommand = commandName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

}
