package atmmachine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

//class to create account
public class CreatePage extends JFrame {

    private JPanel contentPane;//The main panel for the application's GUI
    private JTextField nameField;// field to enter name
    private JTextField birthField;//field to enter birth
    private JTextArea infoField;// area to show the information
    private ArrayList<AccountInfo> accounts; //storing the Accounts list
    private AccountStorage accountStorage; // Account storage for saving data
    private SKKUATM skkuAtm;//this is the skkuatm that stores skkuatm that we runned
    
    //constructor
    public CreatePage(ArrayList<AccountInfo> accounts, AccountStorage accountStorage, SKKUATM skkuAtm) {
    	this.accounts = accounts; 
    	this.skkuAtm = skkuAtm;
        this.accountStorage = accountStorage;

        setTitle("Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 503, 484);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        contentPane.add(imagePanel, BorderLayout.NORTH);
        
        ImageIcon imageIcon = new ImageIcon(SKKUATM.class.getResource("/image/ATM.png"));
        int labelWidth = 500; 
        int labelHeight = 65; 
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(imageIcon);
        imagePanel.add(imageLabel);

        
        JPanel backButtonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Back Button Action Listener. this goes to the runned skkuatm
        backButton.addActionListener(e -> {
            saveAccountsData();
            setVisible(false);
            skkuAtm.setVisible(true);
            skkuAtm.resetInfoArea();
        });
        backButtonPanel.setLayout(new BorderLayout(0, 0));
        backButtonPanel.add(backButton, BorderLayout.WEST);
        contentPane.add(backButtonPanel, BorderLayout.SOUTH);

        JPanel inputPanel = new JPanel();
        GridBagLayout gbl_inputPanel = new GridBagLayout();
        gbl_inputPanel.columnWidths = new int[]{493, 0};
        gbl_inputPanel.rowHeights = new int[] {37, 57, 37, 57, 37, 100, 0};
        gbl_inputPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_inputPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        inputPanel.setLayout(gbl_inputPanel);
        
        contentPane.add(inputPanel, BorderLayout.CENTER);
        infoField = new JTextArea();
        GridBagConstraints gbc_infoField = new GridBagConstraints();
        gbc_infoField.fill = GridBagConstraints.BOTH;
        gbc_infoField.gridx = 0;
        gbc_infoField.gridy = 5;
        inputPanel.add(infoField, gbc_infoField);
        infoField.setColumns(10);
        
             // Confirm Button validates the name, birth text fields and assigns the user account number and pin number.
                JButton confirmButton = new JButton("Confirm");
                confirmButton.addActionListener(e -> {
                	StringBuilder errorMessages = new StringBuilder();
                    String name = nameField.getText().trim();
                    String birth = birthField.getText().trim();

                    // Reset the infoField color for new messages
                    infoField.setForeground(Color.BLACK);

                    // Check for empty name field
                    if (name.isEmpty()) {
                        errorMessages.append("You need to enter a name!\n\n");
                        
                    } else {
                        String[] names = name.split(" ");
                        if (names.length < 2) {
                            errorMessages.append("You forgot to write your name or surname\n\n");
                        
                        }
                        for (String part : names) {
                            if (!part.matches("[A-Z][a-z]*")) {
                                errorMessages.append("Invalid name format (each part must start with an uppercase letter"
                                		+"\n" + "and contain only letters)\n\n");
                                break;
                            }
                        }
                    }

                    // Check for empty birth field
                    if (birth.isEmpty()) {
                        errorMessages.append("You need to enter birth!");
                    }else if(!isValidBirthDate(birth)){
                    	errorMessages.append("Invalid birth format!");
                    }

                    // Check for valid birth format
                    if (errorMessages.length() > 0) {
                        infoField.setText(errorMessages.toString());
                        infoField.setForeground(Color.RED);
                    }
                    else {
                    // If all inputs are valid, proceed with account creation
                    String accountNumber = generateAccountNumber();
                    String tempPIN = generateTempPIN();
                    AccountInfo newAccount = new AccountInfo(accountNumber, tempPIN, name, birth);
                    skkuAtm.getAccounts().add(newAccount);

                    
                    // Attempt to save the accounts
                    try {
                        accountStorage.saveAccounts(accounts);
                        infoField.setText("Account Created! \nAccount Number: " + accountNumber + "\nTEMP PIN: " + tempPIN);
                        nameField.setText("");
                        birthField.setText("");
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error saving account data.");
                    }
                    }
                });
                                GridBagConstraints gbc = new GridBagConstraints();
                                gbc.fill = GridBagConstraints.BOTH;
                                gbc.insets = new Insets(0, 0, 5, 0);
                                gbc.gridx = 0;
                                gbc.gridy = 0;
                                JLabel label = new JLabel("Name:");
                                inputPanel.add(label, gbc);
                        
                                nameField = new JTextField();
                                GridBagConstraints gbc_nameField = new GridBagConstraints();
                                gbc_nameField.fill = GridBagConstraints.BOTH;
                                gbc_nameField.insets = new Insets(0, 0, 5, 0);
                                gbc_nameField.gridx = 0;
                                gbc_nameField.gridy = 1;
                                inputPanel.add(nameField, gbc_nameField);
                                GridBagConstraints gbc_1 = new GridBagConstraints();
                                gbc_1.fill = GridBagConstraints.BOTH;
                                gbc_1.insets = new Insets(0, 0, 5, 0);
                                gbc_1.gridx = 0;
                                gbc_1.gridy = 2;
                                JLabel label_1 = new JLabel("Birthdate (YYYYMMDD):");
                                inputPanel.add(label_1, gbc_1);
                        
                                birthField = new JTextField();
                                GridBagConstraints gbc_birthField = new GridBagConstraints();
                                gbc_birthField.fill = GridBagConstraints.BOTH;
                                gbc_birthField.insets = new Insets(0, 0, 5, 0);
                                gbc_birthField.gridx = 0;
                                gbc_birthField.gridy = 3;
                                inputPanel.add(birthField, gbc_birthField);
                
                        GridBagConstraints gbc_confirmButton = new GridBagConstraints();
                        gbc_confirmButton.fill = GridBagConstraints.BOTH;
                        gbc_confirmButton.insets = new Insets(0, 0, 5, 0);
                        gbc_confirmButton.gridx = 0;
                        gbc_confirmButton.gridy = 4;
                        inputPanel.add(confirmButton, gbc_confirmButton);
        

        
        
        
    }
    
  //Generates a unique 10-digit account number.
    private String generateAccountNumber() {
     String accountNumber;
     do {
         accountNumber = String.format("%05d", (int) (Math.random() * 100000))
              + String.format("%05d", (int) (Math.random() * 100000));
     } while (isAccountNumberExists(accountNumber));
     return accountNumber;
    }

    //Generates a random 4-digit PIN.
    private String generateTempPIN() {
    return String.format("%04d", (int) (Math.random() * 10000));
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
    
  //Validates if the provided birth date is in the correct format and is a valid date.
    private boolean isValidBirthDate(String birth) {
        if (!birth.matches("\\d{8}")) return false;
        int year = Integer.parseInt(birth.substring(0, 4));
        int month = Integer.parseInt(birth.substring(4, 6));
        int day = Integer.parseInt(birth.substring(6, 8));

        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        
        // Check maximum number of days in each month
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year) && month == 2) {
            daysInMonth[1] = 29;
        }

        return day <= daysInMonth[month - 1];
    }

    // Determines if a given year is a leap year.
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
    
    // Save accounts data
    private void saveAccountsData() {
        try {
            accountStorage.saveAccounts(this.accounts); // Use the class member accounts
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving account data.");
        }
    }}


