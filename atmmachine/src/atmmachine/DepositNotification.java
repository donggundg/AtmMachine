package atmmachine;

//Represents a notification specific to deposit transactions.
public class DepositNotification extends Notification {
	//Initializes a deposit notification with a message.
 public DepositNotification(String message) {
     super(message);
 }

 //Prints the message to the console with a "Deposit Notification" prefix. 
 @Override
 public void send() {
     System.out.println("Deposit Notification: " + message);
 }
}
