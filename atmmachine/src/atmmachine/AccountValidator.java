package atmmachine;

//Provides validation methods for account numbers and PINs in a banking application context.
public class AccountValidator {
	//Checks if the provided account number is valid.
 public static boolean isValidAccountNumber(String accountNumber) {
     return accountNumber != null && accountNumber.matches("\\d{10}");
 }
 //Checks if the provided PIN number is valid.
 public static boolean isValidPIN(String pin) {
     return pin != null && pin.matches("\\d{4}");
 }
}
