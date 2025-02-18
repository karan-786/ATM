package BankAccount;

import java.util.*;

class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
        System.out.println("Deposit successful.");
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrawn: $" + amount);
        System.out.println("Withdrawal successful.");
        return true;
    }

    public void transfer(BankAccount receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + receiver.accountNumber);
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

public class BankAccountT {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        accounts.put("12345", new BankAccount("12345", "1111", 5000));
        accounts.put("67890", new BankAccount("67890", "2222", 3000));

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        BankAccount userAccount = accounts.get(accountNumber);
        if (userAccount == null || !userAccount.authenticate(pin)) {
            System.out.println("Invalid login!");
            return;
        }

        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + userAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter receiver account number: ");
                    String receiverAccountNumber = scanner.next();
                    BankAccount receiver = accounts.get(receiverAccountNumber);
                    if (receiver != null) {
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        userAccount.transfer(receiver, transferAmount);
                    } else {
                        System.out.println("Invalid receiver account.");
                    }
                    break;
                case 5:
                    userAccount.showTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
