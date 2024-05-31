package atmmachine;

//Represents a notification specific to transfer transactions.
public class TransferNotification extends Notification {
	//Initializes a transfer notification with a message.
 public TransferNotification(String message) {
     super(message);
 }

 // Prints the message to the console with a "Transfer Notification" prefix.
 @Override
 public void send() {
     System.out.println("Transfer Notification: " + message);
 }
}