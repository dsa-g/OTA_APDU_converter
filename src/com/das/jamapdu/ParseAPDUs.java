package com.das.jamapdu;

public class ParseAPDUs {
	private static String currentLine = null;
	private static String tempLine = null;

	static String processCommand(String line) {
		StringBuilder sb = new StringBuilder();

		int lenInDec = line.length();
		if (tempLine == null) {
			if (lenInDec > 496) {
				currentLine = line.substring(0, 496);
				tempLine = line.substring(496, lenInDec); // substring ends +10 after lenInDec because the length
															// represents
			} else {
				currentLine = line.substring(0, lenInDec);
				tempLine = null;
			}
		} else {
			currentLine = line.substring(0, lenInDec);
			int tempLength = tempLine.length();
			currentLine = tempLine + currentLine;
			if (lenInDec + tempLength > 496) {
				tempLine = currentLine.substring(496, lenInDec + tempLength);
			} else {
				tempLine = null;
			}
			currentLine=currentLine.substring(0,496);

		}
		System.out.println("line: " + line);
//		System.out.println("command: " + command);
		System.out.println("len: "  + " : " + lenInDec);
		System.out.println("currentLine: " + currentLine);
		System.out.println("Temp: " + tempLine);
//		sb.append(command);
		sb.append(",");
		sb.append(currentLine);
		return sb.toString();

	}
}
