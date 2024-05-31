package atmmachine;
//this class is for SKKUATM menu. this is the starting point of the GUI.
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class SKKUATM extends JFrame {

private static final long serialVersionUID = 1L;//a unique version identifier for serialization
private String accountNumber = "";//A string to store the account number (seems to be unused in the current context).
private JPanel contentPane;//The main panel for the application's GUI.
private JButton btnCreateAccount;//A button for creating new accounts.
private JButton btnLoginAccount;//A button for logging into an existing account.
private JTextField accountNumberField;//A text field for entering an account number.
private JTextField pinField;//A text field for entering a PIN number.
private JPanel panel;//Panel used for organizing the layout of GUI components.
private JLabel accountLabel;//Label for the account number and PIN input fields.
private JPanel panel_1;//Panel used for organizing the layout of GUI components.
private JLabel pinLabel;//Label for the account number and PIN input fields.
private ArrayList<AccountInfo> accounts = new ArrayList<>();//An ArrayList that stores AccountInfo objects representing individual bank accounts.
private AccountStorage accountStorage = new AccountStorage();//An object to handle file storage of account data.
private JTextArea infoArea; //an area to give you information about errors etc..
private JLabel imageLabel; //an label to put image
private TransactionHistory transactionHistory;//an transaction history to store transaction history about accounts.

//Updates the details of an existing account in the accounts list.
public void updateAccount(AccountInfo updatedAccount) {
    for (int i = 0; i < accounts.size(); i++) {
        if (accounts.get(i).getAccountNumber().equals(updatedAccount.getAccountNumber())) {
            accounts.set(i, updatedAccount); // Update the account in the list
            break;
        }
    }
    TransactionHistory transactionHistory = updatedAccount.getTransactionHistory();
    List<TransactionRecord> transactionRecords = transactionHistory.getTransactions(updatedAccount.getAccountNumber());
    for (TransactionRecord record : transactionRecords) {
        System.out.println(record);
    }
}

//Saves the current account data to a file using accountStorage.
public void saveAccountsData() {
 try {
     accountStorage.saveAccounts(accounts);
 } catch (IOException e) {
     e.printStackTrace();
     JOptionPane.showMessageDialog(null, "Error saving account data.");
 }
}

//The entry point of the program. Initializes and displays the main frame.
public static void main(String[] args) {
	new SKKUATM();
    EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                SKKUATM frame = new SKKUATM();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    
}

//Constructor for the main application frame. Sets up the GUI layout and event listeners.
public SKKUATM() {
	this.transactionHistory = new TransactionHistory();
	this.accounts = new ArrayList<>();
	
	setTitle("SKKU ATM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 503, 391);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(new GridLayout(0, 1, 0, 0));
    
    imageLabel = new JLabel("");
    imageLabel = new JLabel();

    ImageIcon imageIcon = new ImageIcon(SKKUATM.class.getResource("/image/ATM.png"));

    int labelWidth = 500;
    int labelHeight = 65;

   
    Image image = imageIcon.getImage(); 
    Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
    imageIcon = new ImageIcon(scaledImage);

    imageLabel.setIcon(imageIcon);

    


    
 contentPane.add(imageLabel);
    
    panel = new JPanel();
    contentPane.add(panel);
    panel.setLayout(new GridLayout(1, 2, 0, 0));
    
    accountLabel = new JLabel("                                Account Number : ");
    panel.add(accountLabel);

    accountNumberField = new JTextField();
    panel.add(accountNumberField);
    
    panel_1 = new JPanel();
    contentPane.add(panel_1);
    panel_1.setLayout(new GridLayout(1, 0, 0, 0));
    
    pinLabel = new JLabel("                                PIN Number : ");
    panel_1.add(pinLabel);
    pinField = new JTextField();
    panel_1.add(pinField);

   //In the SKKUATM class, multi-threading is implemented using 
   //SwingWorker in the btnLoginAccount action listener;
    //this allows the login process to run in the background, preventing the GUI from freezing during long operations.
    btnLoginAccount = new JButton("Login Account");
    btnLoginAccount.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            SwingWorker<AccountInfo, Void> worker = new SwingWorker<AccountInfo, Void>() {
                @Override
                protected AccountInfo doInBackground() throws Exception {
                    String enteredAccountNumber = accountNumberField.getText();
                    String enteredPIN = pinField.getText();

                    return isValidLogin(enteredAccountNumber, enteredPIN);
                }

                @Override
                protected void done() {
                    try {
                        AccountInfo loggedInAccount = get();
                        if (loggedInAccount != null) {
                            openAccountMenu(loggedInAccount.getTransactionHistory(), loggedInAccount);
                            infoArea.setForeground(Color.BLACK);
                            infoArea.setText("Login successful!");
                        } else {
                            infoArea.setForeground(Color.RED);
                            infoArea.setText("Incorrect account number or PIN. Please try again.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        infoArea.setForeground(Color.RED);
                        infoArea.setText("An error occurred during login.");
                    }
                    accountNumberField.setText("");
                    pinField.setText("");
                }
            };
            worker.execute();
        }
    });

   

    //go to the create account
    btnCreateAccount = new JButton("Create Account");
    btnCreateAccount.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            CreatePage createPage = new CreatePage(accounts, accountStorage, SKKUATM.this);
            createPage.setVisible(true);
            setVisible(false);
        }
    });

            contentPane.add(btnCreateAccount);
    contentPane.add(btnLoginAccount);
    
    infoArea = new JTextArea();
	 // Set the initial message in infoArea
    infoArea = new JTextArea("Welcome to SKKU ATM! Please login or create an account\n");
    infoArea.setForeground(new Color(0, 100, 0)); // Dark green color
    contentPane.add(infoArea);
}

//Checks if an account number already exists in the system.
private boolean isAccountNumberExists(String accountNumber) {
    for (AccountInfo account : accounts) {
        if (accountNumber.equals(account.getAccountNumber())) {
            return true;
        }
    }
    return false;
}

//Validates the login credentials against stored accounts.
private AccountInfo isValidLogin(String enteredAccountNumber, String enteredPIN) {
    for (AccountInfo account : accounts) {
        if (enteredAccountNumber.equals(account.getAccountNumber()) && enteredPIN.equals(account.getPinNumber())) {
            return account;
        }
    }
    return null;
}

//Opens the account menu for a logged-in account.
private void openAccountMenu(TransactionHistory transactionHistory, AccountInfo loggedInAccount) {
    this.setVisible(false);
    EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                menu frame = new menu(SKKUATM.this, loggedInAccount.getAccountNumber(), transactionHistory, loggedInAccount);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}

//Retrieves an AccountInfo object for a given account number.
public AccountInfo getAccount(String accountNumber) {
 for (AccountInfo account : accounts) {
     if (account.getAccountNumber().equals(accountNumber)) {
         return account;
     }
 }
 return null;
}

//getters of the accounts arrayList
public ArrayList<AccountInfo> getAccounts() {
    return this.accounts;
}

//resetting the info area to normal
public void resetInfoArea() {
    infoArea.setText("Welcome to SKKU ATM! Please login or create an account");
    infoArea.setForeground(new Color(0, 100, 0));
}

}
