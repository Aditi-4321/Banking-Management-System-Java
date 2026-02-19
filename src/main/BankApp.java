package main;

import service.BankService;
import model.Transaction;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import java.util.List;
import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	BankService service = new BankService();

    	System.out.println("----- BANK LOGIN -----");

    	System.out.print("Enter Username: ");
    	String username = sc.nextLine();

    	System.out.print("Enter Password: ");
    	String password = sc.nextLine();

    	boolean isValid = service.login(username, password);

    	if (!isValid) {
    	    System.out.println("Invalid Credentials! Exiting...");
    	    sc.close();
    	    return;
    	}

    	System.out.println("Login Successful!");
        int choice = 0;

        System.out.println("==================================");
        System.out.println("   WELCOME TO BANKING SYSTEM");
        System.out.println("==================================");

        do {

            System.out.println("\n--------- MENU ---------");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            try {

                switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();

                    int newAccNo = service.createAccount(name, balance);

                    System.out.println("Account Created Successfully!");
                    System.out.println("Your Account Number is: " + newAccNo);
                    break;


                    case 2:
                        System.out.print("Enter Account Number: ");
                        int accNo1 = sc.nextInt();

                        System.out.print("Enter Amount to Deposit: ");
                        double depAmount = sc.nextDouble();

                        service.deposit(accNo1, depAmount);
                        System.out.println("Deposit successful!");
                        break;

                    case 3:
                        System.out.print("Enter Account Number: ");
                        int accNo2 = sc.nextInt();

                        System.out.print("Enter Amount to Withdraw: ");
                        double withAmount = sc.nextDouble();

                        service.withdraw(accNo2, withAmount);
                        System.out.println("Withdrawal successful!");
                        break;

                    case 4:
                        System.out.print("Enter Account Number: ");
                        int accNo3 = sc.nextInt();

                        double currentBalance = service.checkBalance(accNo3);
                        System.out.println("Current Balance: ₹" + currentBalance);
                        break;

                    case 5:
                        System.out.print("Enter Sender Account Number: ");
                        int fromAcc = sc.nextInt();

                        System.out.print("Enter Receiver Account Number: ");
                        int toAcc = sc.nextInt();

                        System.out.print("Enter Amount to Transfer: ");
                        double transAmount = sc.nextDouble();

                        service.transfer(fromAcc, toAcc, transAmount);
                        System.out.println("Transfer successful!");
                        break;

                    case 6:
                        System.out.print("Enter Account Number: ");
                        int accNo4 = sc.nextInt();

                        List<Transaction> transactions = service.viewTransactions(accNo4);

                        System.out.println("\n--- Transaction History ---");
                        for (Transaction txn : transactions) {
                            System.out.println(
                                    txn.getTransactionId() + " | " +
                                    txn.getTransactionType() + " | ₹" +
                                    txn.getAmount() + " | " +
                                    txn.getDate()
                            );
                        }
                        break;

                    case 7:
                        System.out.println("Thank you for using Banking System!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } catch (AccountNotFoundException |
                     InsufficientBalanceException |
                     InvalidAmountException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Unexpected error occurred!");
            }

        } while (choice != 7);

        sc.close();
    }
}
