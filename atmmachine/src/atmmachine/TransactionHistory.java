package atmmachine;

//Class to manages and record all transactions made by different accounts.
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
public class TransactionHistory implements Serializable {
	private static final long serialVersionUID = 1L;//allowing it to be serialized for storage or data transfer.    
	private Map<String, List<TransactionRecord>> accountTransactions = new HashMap<>();//// Map to store transactions for each account
  private TransactionUpdateListener updateListener;//An instance of TransactionUpdateListener, used for notifying when a transaction is updated.

  //Defines a method onTransactionUpdated that can be implemented to handle actions when a transaction is updated.
  public interface TransactionUpdateListener {
      void onTransactionUpdated();
  }

  //setter
  public void setUpdateListener(TransactionUpdateListener listener) {
      this.updateListener = listener;
  }

  // Add an initial transaction for account creation
  public void addInitialTransaction(String accountNumber) {
      List<TransactionRecord> transactions = accountTransactions.computeIfAbsent(accountNumber, k -> new ArrayList<>());
      transactions.add(new TransactionRecord("Account Creation", accountNumber, 0, 0, 0, 0, accountNumber));
      //System.out.println("Successfully Created");
  }

  // Add a transaction for a specific account
  public synchronized void addTransaction(String accountNumber, String type, double amount, double balance) {
      List<TransactionRecord> transactions = accountTransactions.computeIfAbsent(accountNumber, k -> new ArrayList<>());
      String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      double depositAmount = 0;
      double withdrawalAmount = 0;
      double transferAmount = 0;

      switch (type) {
          case "Deposit":
              depositAmount = amount;
              break;
          case "Withdrawal":
              withdrawalAmount = amount;
              break;
          case "Transfer":
              transferAmount = amount;
              break;
      }
      transactions.add(new TransactionRecord(date, type, depositAmount, withdrawalAmount, transferAmount, balance, accountNumber));
      if (updateListener != null) {
          updateListener.onTransactionUpdated();
      }
  }


  // Get transactions for a specific account
  public List<TransactionRecord> getTransactions(String accountNumber) {
	  //System.out.println(accountTransactions.getOrDefault(accountNumber, new ArrayList<>()));
      return accountTransactions.getOrDefault(accountNumber, new ArrayList<>());
  }
}
