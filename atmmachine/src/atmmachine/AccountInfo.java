package atmmachine;

import java.util.List;
//Class to make the information of the account.
import java.io.Serializable;
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;//Unique identifier for the account.
    private String pinNumber;//Personal Identification Number for account access.
    private TransactionHistory transactionHistory;//Stores the transaction history of the account.
    private double currentBalance;//Current balance of the account.
    private String name;  //Name of the account holder.
    private String dateOfBirth;  // Birth of the account holder.
    
    // Constructor
    public AccountInfo(String accountNumber, String pinNumber, String name, String dateOfBirth) {
        this.accountNumber = accountNumber;
        this.pinNumber = pinNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.currentBalance = 0;
        this.transactionHistory = new TransactionHistory();
        this.transactionHistory.addInitialTransaction(accountNumber);
    }

    //getters and setters
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPinNumber() {
        return pinNumber;
    }
    
    public void setPinNumber(String newPin) {
        this.pinNumber = newPin;
    }
    
    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
   
    //Updates the current balance based on the last transaction in the transaction history.
    public void updateCurrentBalance() {
        List<TransactionRecord> transactions = this.transactionHistory.getTransactions(this.accountNumber);
        if (!transactions.isEmpty()) {
            TransactionRecord lastTransaction = transactions.get(transactions.size() - 1);
            this.currentBalance = lastTransaction.getFinalBalance();
        }
    }
}