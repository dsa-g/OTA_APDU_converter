package com.das.jamapdu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ReadInputFromLogs {
	private static final String INSTALL_FOR_LOAD = "INSTALL_FOR_LOAD";
	private static final String LOAD_FOR_LOAD = "LOAD_FOR_LOAD";
	private static final String INSTALL_FOR_INSTALL = "INSTALL_FOR_INSTALL";
	private static final String DeleteAppletCommand = "DeleteAppletCommand";

	static int readFile(String filepath, String out_folderpath) {
		long date = new Date().getTime();
		String out_file = out_folderpath + "/Output" + date + ".txt";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));
				BufferedWriter bw = new BufferedWriter(new FileWriter(out_file))) {
			String commandName = null;
			String line = null;
			String prevCommand = null;
			int counter = 1;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("\\W", "");
				System.out.println("Line " + line);
				if (line.startsWith("8")) {
					if (line.substring(2, 4).equals("E8")) {
						commandName = LOAD_FOR_LOAD;
					} else if (line.substring(2, 4).equals("E4")) {
						commandName = DeleteAppletCommand;
					} else if (line.substring(2, 4).equals("E6")) {
						if (line.substring(4, 6).equals("02")) {
							commandName = INSTALL_FOR_LOAD;
						} else if (line.substring(4, 6).equals("0C")) {
							commandName = INSTALL_FOR_INSTALL;
						}
					} else {
//						System.out.println("!!!!!!!!!!!!!!!!!!");
//						System.out.println("IGNORED: " + line);
//						System.out.println("!!!!!!!!!!!!!!!!!!");
						continue;
					}
					String commandAPDU = line.substring(0, 10);

					String lenByte = line.substring(8, 10);
					int lenData = Integer.parseInt(lenByte, 16);
					lenData *= 2;

					int totalData = 0;
					StringBuilder dataAPDU = new StringBuilder();
					while (totalData < lenData) {
						String dataLine = br.readLine();
						dataLine = dataLine.replaceAll("\\W", "");
						dataAPDU.append(dataLine);
						totalData = dataAPDU.length();
					}
					StringBuilder final_data = new StringBuilder();
					if (commandName.equals(LOAD_FOR_LOAD)) {
						if (prevCommand.equals(LOAD_FOR_LOAD)) {
							counter++;
						}
						final_data.append(LOAD_FOR_LOAD);
						final_data.append(counter);
					}
					else {
						final_data.append(commandName);
					}
					final_data.append(",JAM,22");

					lenData += 10; // adding command and data part
					if (lenData > 254) { // if command+data length is greater than 127 bytes (254 in decimal)
						final_data.append("81");
					}
					final_data.append(Integer.toHexString(lenData).toUpperCase());
					final_data.append(commandAPDU);
					final_data.append(dataAPDU);
					bw.write(final_data.toString());
					bw.write("\n\n");
//					System.out.println("Data is : " + dataAPDU);
//					System.out.println("command is : " + commandName);
//					System.out.println("command apdu: " + commandAPDU);
//					System.out.println(commandName + ",JAM,22");
//					System.out.println("______+++++++++++____________");
				} else {
//					System.out.println("====================");
//					System.out.println("IGNORED: " + line);
//					System.out.println("====================");
					continue;
				}

				prevCommand = commandName;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}
}
