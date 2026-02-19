package model;

public class SavingsAccount extends Account {

    private double interestRate = 0.04;

    public SavingsAccount() {}

    public SavingsAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }
}
