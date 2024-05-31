package atmmachine;

//Base class for notifications
public abstract class Notification {
 protected String message;//The message to be sent in the notification.

 //Initializes the notification with a specific message.
 public Notification(String message) {
     this.message = message;
 }

 //An abstract method to be implemented by subclasses for sending notifications.
 public abstract void send();
}