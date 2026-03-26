package com.softwareanalytics.bank;

/**
 * A simple bank account for Software Analytics course testing demo.
 */
public class BankAccount {

    private final String owner;
    private double balance;
    private boolean frozen;

    public BankAccount(String owner, double initialBalance) {
        if (owner == null) {
            throw new IllegalArgumentException("owner cannot be null");
        }
        if (owner.isBlank()) {
            throw new IllegalArgumentException("owner cannot be blank");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("initial balance cannot be negative");
        }
        this.owner = owner;
        this.balance = initialBalance;
        this.frozen = false;
    }

    public void deposit(double amount) {
        if (frozen) {
            throw new IllegalStateException("account is frozen");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (frozen) {
            throw new IllegalStateException("account is frozen");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("insufficient funds");
        }
        balance -= amount;
    }

    public void transfer(BankAccount target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("target cannot be null");
        }
        if (target == this) {
            throw new IllegalArgumentException("cannot transfer to self");
        }
        withdraw(amount);
        target.deposit(amount);
    }

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

    public void applyMonthlyInterest(double annualRatePercent) {
        if (annualRatePercent < 0) {
            throw new IllegalArgumentException("rate cannot be negative");
        }
        if (balance > 0) {
            balance += balance * (annualRatePercent / 100.0 / 12.0);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
