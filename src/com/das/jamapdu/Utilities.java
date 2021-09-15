package com.das.jamapdu;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

class Utilities {
	static String openInputFile() {
		StringBuilder sb = new StringBuilder();
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int selection = jFileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			sb.append(jFileChooser.getSelectedFile().getAbsolutePath());
		} else {
			sb.append("Error!! Operation aborted");
		}
		return sb.toString();

	}

	static String outputFolder() {
		StringBuilder sb = new StringBuilder();
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int selection = jFileChooser.showOpenDialog(null);
		if (selection == JFileChooser.APPROVE_OPTION) {
			sb.append(jFileChooser.getSelectedFile().getAbsolutePath());
		} else {
			sb.append("Error!! Operation aborted");
		}
		return sb.toString();
	}


}
