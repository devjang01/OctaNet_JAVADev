import java.util.Scanner;


class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private String userId;
    private String userPin;
    private double balance;
    private Transaction[] transactionHistory;
    private int transactionCount;

    public BankAccount(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new Transaction[1000]; // Assuming a maximum of 100 transactions
        this.transactionCount = 0;
    }

    public boolean verifyPin(String pin) {
        return userPin.equals(pin);
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposit", amount);
        System.out.println("Deposit successful. Current balance: " + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            addTransaction("Withdraw", amount);
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Current balance: " + balance);
        }
    }

    public void transfer(double amount, BankAccount recipient) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            addTransaction("Transfer to " + recipient.getUserId(), amount);
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient funds. Current balance: " + balance);
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactionHistory[i];
            System.out.println(transaction.getType() + " - Amount: " + transaction.getAmount());
        }
    }

    private void addTransaction(String type, double amount) {
        Transaction transaction = new Transaction(type, amount);
        transactionHistory[transactionCount++] = transaction;
    }

    public String getUserId() {
        return userId;
    }
}

class ATM {
    private BankAccount currentAccount;
    private Scanner scanner;

    public ATM() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");

        // Prompt for user id and pin
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String pin = scanner.nextLine();
        // Verify user id and pin
        currentAccount = verifyUser(userId, pin);
        if (currentAccount != null) {
            System.out.println("Login successful.");
            showMenu();
        } else {
            System.out.println("Invalid credentials. Exiting...");
        }
    }

    private BankAccount verifyUser(String userId, String pin) {
        // Here, you would typically validate the user's credentials against a database
        // or other data source.
        // For simplicity, we will hardcode a single user with id "U21CS029" and pin "asdzxc200127".
        if (userId.equals("devjang") && pin.equals("asdf9200"))   
 {
            return new BankAccount(userId, pin);
        } else if((userId.equals("123") && pin.equals("")))  
 {
              return new BankAccount(userId, pin);
 }

        else {
            return null;
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Show Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    currentAccount.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's user ID: ");
                    String recipientId = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    BankAccount recipient = verifyUser(recipientId, ""); // Empty pin as it's not required for transfer

                    if (recipient != null) {
                        currentAccount.transfer(transferAmount, recipient);
                    } else {
                        System.out.println("Recipient user not found.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
