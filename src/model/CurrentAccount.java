package model;

public class CurrentAccount extends Account {

    private double overdraftLimit = 5000;

    public CurrentAccount() {}

    public CurrentAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    @Override
    public double calculateInterest() {
        return 0; // usually no interest
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
