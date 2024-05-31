package atmmachine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Handles the storage and retrieval of AccountInfo objects to and from a file, specifically accountData.txt. 
//It manages file I/O operations for the banking application's data persistence.
public class AccountStorage {
    private static final String FILE_PATH = "accountData.txt";//A static final String representing the file path of the storage file 
 // Saving accounts in a plain text format
    public void saveAccounts(List<AccountInfo> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AccountInfo account : accounts) {
                writer.write("Account Number: " + account.getAccountNumber() + 
                             ", PIN: " + account.getPinNumber() + 
                             ", Balance: " + account.getCurrentBalance() + 
                             ", Name: " + account.getName() + 
                             ", Birth: " + account.getDateOfBirth() + "\n");
            }
        }
    }
//Reads accountData.txt and converts each line back into an AccountInfo object, 
//accumulating them into a list. It returns this list of accounts.
    public List<AccountInfo> loadAccounts() throws IOException {
        List<AccountInfo> accounts = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    accounts.add(stringToAccount(line));
                }
            }
        }
        return accounts;
    }

    //Converts an AccountInfo object into a formatted string for storage. 
    private String accountToString(AccountInfo account) {
        return "Account Number: " + account.getAccountNumber() + 
               ", PIN: " + account.getPinNumber() + 
               ", Balance: " + account.getCurrentBalance();
    }

    //Parses a string representing an account's data and converts it into an AccountInfo object.
    private AccountInfo stringToAccount(String str) {
        String[] parts = str.split(", ");
        String accountNumber = parts[0].split(": ")[1];
        String pinNumber = parts[1].split(": ")[1];
        double balance = Double.parseDouble(parts[2].split(": ")[1]);
        String name = parts[3].split(": ")[1];
        String dateOfBirth = parts[4].split(": ")[1];
        AccountInfo account = new AccountInfo(accountNumber, pinNumber, name, dateOfBirth);
        account.setCurrentBalance(balance);
        return account;
    }

}
