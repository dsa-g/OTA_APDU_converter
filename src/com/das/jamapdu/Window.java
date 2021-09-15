package com.das.jamapdu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Window {

	private JFrame frame;
	private Button buttonBrowse = new Button("Browse");
	private Button buttonGenerate = new Button("Generate");
	private JButton buttonBrowseOutput = new JButton("Save OTA APDUs at");

	private String filepath;
	private String output_folderpath;
	private JTextPane textPane = new JTextPane();
	private final JTextArea txtrSelectScript = new JTextArea();
	private final JLabel lblNewLabel = new JLabel("OTA APDU CONVERTER");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("OTA-APDU-Converter");
		frame.getContentPane().setForeground(Color.BLACK);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);

		buttonBrowse.setFont(new Font("Arial Black", Font.BOLD, 14));
		buttonBrowse.setForeground(Color.WHITE);
		buttonBrowse.setBackground(new Color(0, 191, 255));
		buttonBrowse.setBounds(219, 161, 201, 35);
		frame.getContentPane().add(buttonBrowse);

		textPane.setEditable(false);
		textPane.setBounds(86, 326, 457, 41);
		frame.getContentPane().add(textPane);

		buttonGenerate.setFont(new Font("Arial Black", Font.BOLD, 14));
		buttonGenerate.setForeground(Color.WHITE);
		buttonGenerate.setBackground(new Color(0, 191, 255));
		buttonGenerate.setBounds(219, 269, 201, 35);
		buttonGenerate.setVisible(false);
		frame.getContentPane().add(buttonGenerate);

		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(205, 25, 201, 41);
		frame.getContentPane().add(lblNewLabel);

		txtrSelectScript.setRows(2);
		txtrSelectScript.setEditable(false);
		txtrSelectScript.setText(
				"1. Press Browse button to select APDU Script.\r\n2. Press Save Output to select OTA-APDU location\r\n3. Press Generate button to generate OTA-APDUs");
		txtrSelectScript.setBackground(new Color(255, 255, 255));
		txtrSelectScript.setLineWrap(true);
		txtrSelectScript.setFont(new Font("Arial", Font.PLAIN, 12));
		txtrSelectScript.setBounds(172, 78, 294, 54);

		frame.getContentPane().add(txtrSelectScript);

	

		buttonBrowseOutput.setFont(new Font("Arial Black", Font.BOLD, 14));
		buttonBrowseOutput.setForeground(new Color(255, 255, 255));
		buttonBrowseOutput.setBackground(new Color(0, 191, 255));
		buttonBrowseOutput.setBounds(219, 216, 201, 35);
		buttonBrowseOutput.setVisible(false);
		frame.getContentPane().add(buttonBrowseOutput);

		frame.setBounds(100, 100, 625, 433);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filepath = Utilities.openInputFile();
				System.out.println("Selecting input");
				textPane.setText("Filepath inormation: " + filepath);
				buttonBrowseOutput.setVisible(true);

			}
		});
		buttonBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				output_folderpath = Utilities.outputFolder();
				System.out.println("Selecting Output Location: "+output_folderpath);
				textPane.setText("Output Folder: " + output_folderpath);
				buttonGenerate.setVisible(true);

			}
		});

		buttonGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reading input");
//				ReadInput.readFile(filepath);  
				// for processing scripts from APDUs
				ReadInputFromLogs.readFile(filepath,output_folderpath);
				textPane.setText("Reading input from: " + filepath);
				buttonGenerate.setVisible(false);
				buttonBrowseOutput.setVisible(false);

			}
		});
	}
}
