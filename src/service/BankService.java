package service;
import dao.UserDAO;
import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.SavingsAccount;
import model.Transaction;

import exception.AccountNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAmountException;

import java.util.Date;
import java.util.List;

public class BankService {

    private AccountDAO accountDAO = new AccountDAO();
    private UserDAO userDAO = new UserDAO();
    public boolean login(String username, String password) {
        return userDAO.validateUser(username, password);
    }
    private TransactionDAO transactionDAO = new TransactionDAO();

    // Create Account
  
    public int createAccount(String name, double balance)
            throws InvalidAmountException {

        if (balance < 0)
            throw new InvalidAmountException("Initial balance cannot be negative!");

        Account account = new SavingsAccount();
        account.setName(name);
        account.setBalance(balance);

        int accountNumber = accountDAO.createAccount(account);

        return accountNumber;
    }

    // Deposit Money
    
    public void deposit(int accNo, double amount)
            throws AccountNotFoundException, InvalidAmountException {

        if (amount <= 0)
            throw new InvalidAmountException("Amount must be greater than zero!");

        Account account = accountDAO.getAccount(accNo);

        if (account == null)
            throw new AccountNotFoundException("Account not found!");

        account.deposit(amount);
        accountDAO.updateBalance(accNo, account.getBalance());

        Transaction txn = new Transaction(
                0,
                accNo,
                amount,
                "DEPOSIT",
                new Date()
        );

        transactionDAO.addTransaction(txn);
    }

    // =========================
    // Withdraw Money
    // =========================
    public void withdraw(int accNo, double amount)
            throws AccountNotFoundException,
                   InvalidAmountException,
                   InsufficientBalanceException {

        if (amount <= 0)
            throw new InvalidAmountException("Invalid withdrawal amount!");

        Account account = accountDAO.getAccount(accNo);

        if (account == null)
            throw new AccountNotFoundException("Account not found!");

        if (account.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance!");

        account.withdraw(amount);
        accountDAO.updateBalance(accNo, account.getBalance());

        Transaction txn = new Transaction(
                0,
                accNo,
                amount,
                "WITHDRAW",
                new Date()
        );

        transactionDAO.addTransaction(txn);
    }

    // =========================
    // Check Balance
    // =========================
    public double checkBalance(int accNo)
            throws AccountNotFoundException {

        Account account = accountDAO.getAccount(accNo);

        if (account == null)
            throw new AccountNotFoundException("Account not found!");

        return account.getBalance();
    }

    // =========================
    // Transfer Money
    // =========================
    public void transfer(int fromAcc, int toAcc, double amount)
            throws AccountNotFoundException,
                   InsufficientBalanceException,
                   InvalidAmountException {

        if (amount <= 0)
            throw new InvalidAmountException("Invalid transfer amount!");

        Account sender = accountDAO.getAccount(fromAcc);
        Account receiver = accountDAO.getAccount(toAcc);

        if (sender == null || receiver == null)
            throw new AccountNotFoundException("Invalid account number!");

        if (sender.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance!");

        sender.withdraw(amount);
        receiver.deposit(amount);

        accountDAO.updateBalance(fromAcc, sender.getBalance());
        accountDAO.updateBalance(toAcc, receiver.getBalance());

        transactionDAO.addTransaction(
                new Transaction(0, fromAcc, amount, "TRANSFER_DEBIT", new Date())
        );

        transactionDAO.addTransaction(
                new Transaction(0, toAcc, amount, "TRANSFER_CREDIT", new Date())
        );
    }

    // =========================
    // View Transactions
    // =========================
    public List<Transaction> viewTransactions(int accNo)
            throws AccountNotFoundException {

        Account account = accountDAO.getAccount(accNo);

        if (account == null)
            throw new AccountNotFoundException("Account not found!");

        return transactionDAO.getTransactions(accNo);
    }
}
