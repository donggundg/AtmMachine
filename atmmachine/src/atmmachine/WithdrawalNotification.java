package atmmachine;

//Represents a notification specific to withdrawal transactions.
public class WithdrawalNotification extends Notification {
	//Initializes a withdrawal notification with a message.
 public WithdrawalNotification(String message) {
     super(message);
 }

 //Prints the message to the console with a "Withdrawal Notification" prefix.
 @Override
 public void send() {
     System.out.println("Withdrawal Notification: " + message);
 }
}