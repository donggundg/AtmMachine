package atmmachine;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

//class to change the pin
public class changePin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;//The main panel for the application's GUI.
	private JTextField currentField;//field to enter the current PIN
	private JTextField newPinField;//field to enter the new PIN
	private JTextField confirmField;//field to enter the confirmed new PIN
	private static AccountInfo accountInfo;//to store the account info (updates the password)
	private static SKKUATM skkuAtm;//to store the runned skkuatm
	private static menu menuFrame;//to store the runned menu

	//The entry point of the program. Initializes and displays the main frame.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					changePin frame = new changePin(accountInfo, skkuAtm, menuFrame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//constructor
	public changePin(AccountInfo accountInfo, SKKUATM skkuAtm, menu menuFrame) {
	    this.accountInfo = accountInfo;
	    this.skkuAtm = skkuAtm;
	    this.menuFrame = menuFrame;
		setTitle("Change PIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Please Write Down your current PIN, new PIN in a sorted order.");
		lblNewLabel.setForeground(SystemColor.inactiveCaptionText);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.gridwidth = 13;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JTextArea errorArea = new JTextArea();
		errorArea.setLineWrap(true);
		errorArea.setWrapStyleWord(true);
		errorArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(errorArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 세로 스크롤바 필요시 나타나도록 설정
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤바는 나타나지 않도록 설정

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		
		
		JLabel currentLabel = new JLabel("Current PIN : ");
		GridBagConstraints gbc_currentLabel = new GridBagConstraints();
		gbc_currentLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentLabel.gridx = 6;
		gbc_currentLabel.gridy = 2;
		contentPane.add(currentLabel, gbc_currentLabel);
		
		currentField = new JTextField();
		GridBagConstraints gbc_currentField = new GridBagConstraints();
		gbc_currentField.gridwidth = 3;
		gbc_currentField.insets = new Insets(0, 0, 5, 5);
		gbc_currentField.fill = GridBagConstraints.HORIZONTAL;
		gbc_currentField.gridx = 8;
		gbc_currentField.gridy = 2;
		contentPane.add(currentField, gbc_currentField);
		currentField.setColumns(10);
		
		JLabel newPinLabel = new JLabel("New PIN : ");
		GridBagConstraints gbc_newPinLabel = new GridBagConstraints();
		gbc_newPinLabel.insets = new Insets(0, 0, 5, 5);
		gbc_newPinLabel.gridx = 6;
		gbc_newPinLabel.gridy = 3;
		contentPane.add(newPinLabel, gbc_newPinLabel);
		
		newPinField = new JTextField();
		GridBagConstraints gbc_newPinField = new GridBagConstraints();
		gbc_newPinField.gridwidth = 3;
		gbc_newPinField.insets = new Insets(0, 0, 5, 5);
		gbc_newPinField.fill = GridBagConstraints.HORIZONTAL;
		gbc_newPinField.gridx = 8;
		gbc_newPinField.gridy = 3;
		contentPane.add(newPinField, gbc_newPinField);
		newPinField.setColumns(10);
		
		JLabel confirmLabel = new JLabel("Confirm PIN : ");
		GridBagConstraints gbc_confirmLabel = new GridBagConstraints();
		gbc_confirmLabel.insets = new Insets(0, 0, 5, 5);
		gbc_confirmLabel.gridx = 6;
		gbc_confirmLabel.gridy = 4;
		contentPane.add(confirmLabel, gbc_confirmLabel);
		
		confirmField = new JTextField();
		GridBagConstraints gbc_confirmField = new GridBagConstraints();
		gbc_confirmField.gridwidth = 3;
		gbc_confirmField.insets = new Insets(0, 0, 5, 5);
		gbc_confirmField.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmField.gridx = 8;
		gbc_confirmField.gridy = 4;
		contentPane.add(confirmField, gbc_confirmField);
		confirmField.setColumns(10);
		
		//button to go back to the menu.java
		JButton btnBack = new JButton("Back");
	    btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            changePin.this.setVisible(false); 
	            menuFrame.setVisible(true); 
	        }
	    });
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.gridwidth = 2;
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 3;
		gbc_btnBack.gridy = 7;
		contentPane.add(btnBack, gbc_btnBack);
		
		//button to confirm changing pin. validates the current, new, confirm pin
		JButton btnChangePin = new JButton("Change PIN");
		btnChangePin.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String currentPin = currentField.getText();
		        String newPin = newPinField.getText();
		        String confirmPin = confirmField.getText();

		        StringBuilder errors = new StringBuilder();

		        if (currentPin.isEmpty()) {
		            errors.append("You should enter current PIN!\n");
		        } else if (!isValidPIN(currentPin)) {
		            errors.append("You should enter valid current PIN number!\n");
		        } else if (!currentPin.equals(accountInfo.getPinNumber())) {
		            errors.append("Incorrect current PIN.\n");
		        }

		        if (newPin.isEmpty()) {
		            errors.append("You should enter new PIN!\n");
		        } else if (!isValidPIN(newPin)) {
		            errors.append("You should enter valid new PIN number!\n");
		        }

		        if (confirmPin.isEmpty()) {
		            errors.append("You should enter confirm PIN!\n");
		        } else if (!isValidPIN(confirmPin)) {
		            errors.append("You should enter valid confirm PIN number!\n");
		        } else if (!confirmPin.equals(newPin)) {
		            errors.append("Confirm PIN does not match the new PIN.\n");
		        }

		        if (errors.length() > 0) {
		            errorArea.setText(errors.toString());
		            errorArea.setForeground(Color.RED);
		        } else {
		            accountInfo.setPinNumber(newPin);
		            skkuAtm.updateAccount(accountInfo); 
		            skkuAtm.saveAccountsData(); 
		            errorArea.setText("PIN successfully changed.");
		            errorArea.setForeground(new Color(0, 100, 0));
		            currentField.setText("");
		            newPinField.setText("");
		            confirmField.setText("");
		        }
		    }
		});




		GridBagConstraints gbc_btnChangePin = new GridBagConstraints();
		gbc_btnChangePin.gridwidth = 4;
		gbc_btnChangePin.insets = new Insets(0, 0, 0, 5);
		gbc_btnChangePin.gridx = 7;
		gbc_btnChangePin.gridy = 7;
		contentPane.add(btnChangePin, gbc_btnChangePin);
	}
	private boolean isValidPIN(String pin) {
	    return pin.matches("\\d{4}");
	}

}
