package atmmachine;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollPane;

public class menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;//The main panel for the application's GUI.
	private JTextField transferAmount;//text field for transfer amount
	private JTextField transferAccount;//text field for transfer account
	private JTextField depositAmount;//text field for deposit amount
	private JTextField withdrawAmount;//text field for withdraw amount
	private static TransactionHistory transactionHistory;//storing the transactionHistory for skkuatm
    private static AccountInfo loggedInAccount;//storing the accountInfo of the logged account
    private static SKKUATM skkuAtm;//storing the runned skkuatm
    private static String accountNumber;//storing the account number
    private JTable table;//table for transaction history
    private JTable tableHeader;//table header for the table. it show date, deposit, withdraw, transfer, and balance
    
  //The entry point of the program. Initializes and displays the main frame.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu(skkuAtm, accountNumber, transactionHistory, loggedInAccount);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//constructor
	public menu(SKKUATM skkuAtm, String accountNumber, TransactionHistory transactionHistory, AccountInfo loggedInAccount) {
		setTitle("SKKUATM");
		this.skkuAtm = skkuAtm;
        this.accountNumber = accountNumber;
        
        //exception handling for not logged in
        if (loggedInAccount == null) {
            JOptionPane.showMessageDialog(this, "No account is currently logged in.");
            return;
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 787, 496);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
        
		
		
		//button for log out. go back to previous skkuatm
		JButton btnLogOUt = new JButton("Log Out");
		btnLogOUt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.this.dispose();
                skkuAtm.resetInfoArea();
                skkuAtm.setVisible(true);
                skkuAtm.resetInfoArea();
            }
        });
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		
		DefaultTableModel tableModel = new DefaultTableModel();
	    tableModel.setColumnIdentifiers(new Object[]{"Date", "Deposit Amount", "Withdraw Amount", "Transfer Amount", "Final Balance"});
	    table.setModel(tableModel); 
		scrollPane.setViewportView(table);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 21;
		gbc_scrollPane.gridheight = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 3;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
	    
		
		tableHeader = new JTable();
		scrollPane.setColumnHeaderView(tableHeader);
		GridBagConstraints gbc_btnLogOUt = new GridBagConstraints();
		gbc_btnLogOUt.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogOUt.gridx = 24;
		gbc_btnLogOUt.gridy = 3;
		contentPane.add(btnLogOUt, gbc_btnLogOUt);
		
		//go to the changePIN.java with current account info
		JButton btnChangePIN = new JButton("Change PIN");
		btnChangePIN.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        changePin changePinFrame = new changePin(loggedInAccount, skkuAtm, menu.this);
		        changePinFrame.setVisible(true);
		        menu.this.setVisible(false); 
		    }
		});


		GridBagConstraints gbc_btnChangePIN = new GridBagConstraints();
		gbc_btnChangePIN.insets = new Insets(0, 0, 5, 5);
		gbc_btnChangePIN.gridx = 24;
		gbc_btnChangePIN.gridy = 5;
		contentPane.add(btnChangePIN, gbc_btnChangePIN);
		
		JTextArea errorArea = new JTextArea();
		errorArea.setLineWrap(true); 
		errorArea.setWrapStyleWord(true);
		errorArea.setEditable(false);
		errorArea.setForeground(Color.RED);

		JScrollPane scrollPaneError = new JScrollPane(errorArea);
		scrollPaneError.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneError.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar

		GridBagConstraints gbc_scrollPaneError = new GridBagConstraints();
		gbc_scrollPaneError.gridheight = 3;
		gbc_scrollPaneError.gridwidth = 12;
		gbc_scrollPaneError.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneError.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneError.gridx = 3;
		gbc_scrollPaneError.gridy = 8;
		contentPane.add(scrollPaneError, gbc_scrollPaneError);

		JLabel depositLabel = new JLabel("Deposit : ");
		GridBagConstraints gbc_depositLabel = new GridBagConstraints();
		gbc_depositLabel.insets = new Insets(0, 0, 5, 5);
		gbc_depositLabel.gridx = 16;
		gbc_depositLabel.gridy = 8;
		contentPane.add(depositLabel, gbc_depositLabel);
		
		depositAmount = new JTextField();
		GridBagConstraints gbc_depositAmount = new GridBagConstraints();
		gbc_depositAmount.gridwidth = 6;
		gbc_depositAmount.insets = new Insets(0, 0, 5, 5);
		gbc_depositAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_depositAmount.gridx = 18;
		gbc_depositAmount.gridy = 8;
		contentPane.add(depositAmount, gbc_depositAmount);
		depositAmount.setColumns(10);
		
		this.transactionHistory = transactionHistory;
        this.loggedInAccount = loggedInAccount;
        if (loggedInAccount != null) {
            updateTable();
        }
        
        //validates the deposit amount and if it is valid, update table and update the info table about how much deposit
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	errorArea.setText("");
		        try {
		            double depositValue = Double.parseDouble(depositAmount.getText());
		            if (depositValue > 0) {
		            	double newBalance = loggedInAccount.getCurrentBalance() + depositValue;
		            	loggedInAccount.setCurrentBalance(newBalance);

		            	transactionHistory.addTransaction(
		            	    loggedInAccount.getAccountNumber(),
		            	    "Deposit",
		            	    depositValue,
		            	    newBalance
		            	);

		            	skkuAtm.updateAccount(loggedInAccount);
		            	skkuAtm.saveAccountsData();
		            	updateTable();
		            	errorArea.setText("");
		                errorArea.setForeground(Color.BLACK);
		                depositAmount.setText("");
		              //schedules the execution of the enclosed code block 
			            //(which creates and sends a TransferNotification and updates errorArea) 
			            //to be executed later on the EDT, ensuring thread safety
		                SwingUtilities.invokeLater(() -> {
		                    DepositNotification depositNotification = new DepositNotification("Deposit successful!");
		                    depositNotification.send();
		                    errorArea.setText(depositNotification.message);
		                    errorArea.setForeground(new Color(0, 100, 0)); 
		                });
		            } else {
		                errorArea.setText("Please enter a positive amount for deposit.");
		                errorArea.setForeground(Color.RED);
		            }
		        } catch (NumberFormatException ex) {
		            errorArea.setText("Invalid deposit amount. Please enter a valid number.");
		            errorArea.setForeground(Color.RED);
		        }
		    }
		    
		});


		GridBagConstraints gbc_btnDeposit = new GridBagConstraints();
		gbc_btnDeposit.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeposit.gridx = 24;
		gbc_btnDeposit.gridy = 8;
		contentPane.add(btnDeposit, gbc_btnDeposit);
		
		JLabel lblNewLabel_1 = new JLabel("Withdraw : ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 16;
		gbc_lblNewLabel_1.gridy = 10;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		withdrawAmount = new JTextField();
		GridBagConstraints gbc_withdrawAmount = new GridBagConstraints();
		gbc_withdrawAmount.gridwidth = 6;
		gbc_withdrawAmount.insets = new Insets(0, 0, 5, 5);
		gbc_withdrawAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_withdrawAmount.gridx = 18;
		gbc_withdrawAmount.gridy = 10;
		contentPane.add(withdrawAmount, gbc_withdrawAmount);
		withdrawAmount.setColumns(10);
		
		//validates the withdraw amount and if it is valid, update table and update the info table about how much withdraw
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	errorArea.setText("");
		        try {
		            double withdrawalValue = Double.parseDouble(withdrawAmount.getText());
		            double currentBalance = loggedInAccount.getCurrentBalance();

		            if (withdrawalValue > 0 && withdrawalValue <= currentBalance) {
		                double newBalance = currentBalance - withdrawalValue;
		                loggedInAccount.setCurrentBalance(newBalance);
		                transactionHistory.addTransaction(
		                    loggedInAccount.getAccountNumber(),
		                    "Withdrawal",
		                    withdrawalValue,
		                    newBalance
		                );
		                skkuAtm.updateAccount(loggedInAccount);
		                skkuAtm.saveAccountsData();

		                updateTable();
		                errorArea.setText("");
		                errorArea.setForeground(Color.BLACK); 
		                withdrawAmount.setText("");
		              //schedules the execution of the enclosed code block 
			            //(which creates and sends a TransferNotification and updates errorArea) 
			            //to be executed later on the EDT, ensuring thread safety
		                SwingUtilities.invokeLater(() -> {
				            WithdrawalNotification withdrawalNotification = new WithdrawalNotification("Withdrawal successful!");
				            withdrawalNotification.send();
				            errorArea.setText(withdrawalNotification.message);
				            errorArea.setForeground(new Color(0, 100, 0)); 
				        });
		            } else {
		                errorArea.setText("Invalid withdrawal amount. Amount must be positive and less than or equal to the current balance.");
		                errorArea.setForeground(Color.RED); 
		            }
		        } catch (NumberFormatException ex) {
		            errorArea.setText("Invalid withdrawal input. Please enter a valid number.");
		            errorArea.setForeground(Color.RED);
		        }
		        
		    }
		});

		GridBagConstraints gbc_btnWithdraw = new GridBagConstraints();
		gbc_btnWithdraw.insets = new Insets(0, 0, 5, 5);
		gbc_btnWithdraw.gridx = 24;
		gbc_btnWithdraw.gridy = 10;
		contentPane.add(btnWithdraw, gbc_btnWithdraw);
		
		JLabel lblNewLabel_2 = new JLabel("Transfer to ?");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridwidth = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 8;
		gbc_lblNewLabel_2.gridy = 11;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		transferAccount = new JTextField();
		GridBagConstraints gbc_transferAccount = new GridBagConstraints();
		gbc_transferAccount.gridwidth = 12;
		gbc_transferAccount.insets = new Insets(0, 0, 5, 5);
		gbc_transferAccount.fill = GridBagConstraints.HORIZONTAL;
		gbc_transferAccount.gridx = 3;
		gbc_transferAccount.gridy = 12;
		contentPane.add(transferAccount, gbc_transferAccount);
		transferAccount.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Transfer : ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 16;
		gbc_lblNewLabel_3.gridy = 12;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		transferAmount = new JTextField();
		GridBagConstraints gbc_transferAmount = new GridBagConstraints();
		gbc_transferAmount.gridwidth = 6;
		gbc_transferAmount.insets = new Insets(0, 0, 5, 5);
		gbc_transferAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_transferAmount.gridx = 18;
		gbc_transferAmount.gridy = 12;
		contentPane.add(transferAmount, gbc_transferAmount);
		transferAmount.setColumns(10);
		
		JButton btnTransfer = new JButton("Transfer");
		//validates the transfer amount and if it is valid, update table and update the info table about how much transfer
		btnTransfer.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	errorArea.setText("");
		        StringBuilder errorMessages = new StringBuilder();

		        try {
		        	double currentBalance = loggedInAccount.getCurrentBalance();
		        	 String targetAccountNumber = transferAccount.getText();
		             String transferValueStr = transferAmount.getText();
		             double transferValue = 0;
		             boolean hasErrors = false;

		             if (targetAccountNumber.isEmpty()) {
		                 errorMessages.append("You need to enter transfer Account!\n");
		                 hasErrors = true;
		             }

		             if (transferValueStr.isEmpty()) {
		                 errorMessages.append("You need to enter transfer Amount!\n");
		                 hasErrors = true;
		             } else {
		                 try {
		                     transferValue = Double.parseDouble(transferValueStr);
		                 } catch (NumberFormatException ex) {
		                     errorMessages.append("Wrong transfer amount form. Please check your transfer amount.\n");
		                     hasErrors = true;
		                 }
		             }

		             if (!targetAccountNumber.isEmpty() && !targetAccountNumber.matches("\\d{10}")) {
		                 errorMessages.append("Invalid account number. Account number should be 10 digit number.\n");
		                 hasErrors = true;
		             }

		             if (!targetAccountNumber.isEmpty() && targetAccountNumber.equals(loggedInAccount.getAccountNumber())) {
		                 errorMessages.append("You cannot transfer to yourself!\n");
		                 hasErrors = true;
		             }

		             if (transferValue > loggedInAccount.getCurrentBalance()) {
		                 errorMessages.append("Invalid transfer amount. Please check your transfer amount.\n");
		                 hasErrors = true;
		             }

		             if (hasErrors) {
		                 errorArea.setText(errorMessages.toString());
		                 errorArea.setForeground(Color.RED);
		                 return;
		             }

		             AccountInfo targetAccount = skkuAtm.getAccount(targetAccountNumber);
		             if (targetAccount == null) {
		                 errorArea.append("Target account not found. Please check the account number.\n");
		                 errorArea.setForeground(Color.RED);
		                 return;
		             }

		            loggedInAccount.setCurrentBalance(currentBalance - transferValue);
		            targetAccount.setCurrentBalance(targetAccount.getCurrentBalance() + transferValue);

		            transactionHistory.addTransaction(
		                loggedInAccount.getAccountNumber(),
		                "Transfer",
		                transferValue,
		                loggedInAccount.getCurrentBalance()
		            );

		            targetAccount.getTransactionHistory().addTransaction(
		                targetAccount.getAccountNumber(),
		                "Deposit",
		                transferValue,
		                targetAccount.getCurrentBalance()
		            );

		            skkuAtm.updateAccount(loggedInAccount);
		            skkuAtm.updateAccount(targetAccount);
		            skkuAtm.saveAccountsData();

		            updateTable();
		            transferAccount.setText("");
		            transferAmount.setText("");
		            //schedules the execution of the enclosed code block 
		            //(which creates and sends a TransferNotification and updates errorArea) 
		            //to be executed later on the EDT, ensuring thread safety
		            SwingUtilities.invokeLater(() -> {
			            TransferNotification transferNotification = new TransferNotification("Transfer successful!");
			            transferNotification.send();
			            errorArea.setText(transferNotification.message);
			            errorArea.setForeground(new Color(0, 100, 0));
			        });

		        } catch (NumberFormatException ex) {
		            errorArea.append("Invalid transfer input. Please enter a valid number.\n");
		            errorArea.setForeground(Color.RED);
		        }
		        
		    }
		});
		GridBagConstraints gbc_btnTransfer = new GridBagConstraints();
		gbc_btnTransfer.insets = new Insets(0, 0, 5, 5);
		gbc_btnTransfer.gridx = 24;
		gbc_btnTransfer.gridy = 12;
		contentPane.add(btnTransfer, gbc_btnTransfer);
		updateTable();

	}

	//update the transaction table and add transaction record
	private void updateTable() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel(); // Correctly obtain the table model
	    model.setRowCount(0);
	    for (TransactionRecord record : transactionHistory.getTransactions(loggedInAccount.getAccountNumber())) {
	        model.addRow(record.toArray());
	    }

	}

}
