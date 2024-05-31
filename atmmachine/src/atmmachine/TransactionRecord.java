package atmmachine;

import java.io.Serializable;
//Class to represent a transaction record
public class TransactionRecord implements Serializable{
private static final long serialVersionUID = 1L;
private String date;//A String representing the date and time of the transaction.
private double depositAmount;//A double value indicating the amount deposited (if applicable).
private double withdrawalAmount;//A double value indicating the amount withdrawn (if applicable).
private double transferAmount;//A double value indicating the amount transferred (if applicable).
private double finalBalance;//A double value representing the final balance after the transaction.
private String accountNumber;  // A String indicating the account number associated with the transaction.

//Constructor
public TransactionRecord(String date, String type, double depositAmount, double withdrawalAmount,
double transferAmount, double finalBalance, String accountNumber) {
this.date = date;
this.depositAmount = depositAmount;
this.withdrawalAmount = withdrawalAmount;
this.transferAmount = transferAmount;
this.finalBalance = finalBalance;
this.accountNumber = accountNumber;
}

//getter
public double getFinalBalance() {
  return finalBalance;
}

//Converts the transaction record into an array of objects for display purposes 
public Object[] toArray() {
  return new Object[]{
      date, 
      String.format("%,d₩", (long) depositAmount), 
      String.format("%,d₩", (long) withdrawalAmount), 
      String.format("%,d₩", (long) transferAmount), 
      String.format("%,d₩", (long) finalBalance), 
      accountNumber
  };
}

public Object getDate() {
	// TODO Auto-generated method stub
	return this.date;
}

//toString
@Override
public String toString() {
    return "TransactionRecord{" +
           "date='" + date + '\'' +
           ", depositAmount=" + depositAmount +
           ", withdrawalAmount=" + withdrawalAmount +
           ", transferAmount=" + transferAmount +
           ", finalBalance=" + finalBalance +
           '}';
}



}