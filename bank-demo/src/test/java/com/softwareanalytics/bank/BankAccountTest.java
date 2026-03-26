package com.softwareanalytics.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for BankAccount with high coverage but weak assertions.
 * Demonstrates that high coverage does not guarantee test effectiveness.
 */
@DisplayName("BankAccount Tests")
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("Alice", 100.0);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("valid arguments")
        void constructorWithValidArguments() {
            BankAccount acc = new BankAccount("Bob", 250.50);
            assertTrue(acc.getBalance() >= 0);
        }

        @Test
        @DisplayName("null owner expects IllegalArgumentException")
        void constructorNullOwner() {
            assertThrows(IllegalArgumentException.class, () -> new BankAccount(null, 100.0));
        }

        @Test
        @DisplayName("blank owner expects IllegalArgumentException")
        void constructorBlankOwner() {
            assertThrows(IllegalArgumentException.class, () -> new BankAccount("   ", 100.0));
        }

        @Test
        @DisplayName("negative initial balance expects IllegalArgumentException")
        void constructorNegativeInitialBalance() {
            assertThrows(IllegalArgumentException.class, () -> new BankAccount("Bob", -50.0));
        }
    }

    @Nested
    @DisplayName("deposit")
    class DepositTests {

        @Test
        @DisplayName("happy path")
        void depositHappyPath() {
            account.deposit(50.0);
            assertTrue(account.getBalance() > 0);
        }

        @Test
        @DisplayName("multiple deposits")
        void depositMultipleTimes() {
            account.deposit(25.0);
            account.deposit(75.0);
            account.deposit(10.0);
            assertTrue(account.getBalance() >= 0);
        }

        @Test
        @DisplayName("zero amount expects IllegalArgumentException")
        void depositZeroAmount() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
        }

        @Test
        @DisplayName("negative amount expects IllegalArgumentException")
        void depositNegativeAmount() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));
        }

        @Test
        @DisplayName("on a frozen account expects IllegalStateException")
        void depositOnFrozenAccount() {
            account.freeze();
            assertThrows(IllegalStateException.class, () -> account.deposit(50.0));
        }
    }

    @Nested
    @DisplayName("withdraw")
    class WithdrawTests {

        @Test
        @DisplayName("happy path")
        void withdrawHappyPath() {
            account.withdraw(30.0);
            assertTrue(true);
        }

        @Test
        @DisplayName("exact balance (withdraw full amount)")
        void withdrawExactBalance() {
            account.withdraw(100.0);
            assertTrue(account.getBalance() >= 0);
        }

        @Test
        @DisplayName("insufficient funds expects IllegalArgumentException")
        void withdrawInsufficientFunds() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(150.0));
        }

        @Test
        @DisplayName("negative amount expects IllegalArgumentException")
        void withdrawNegativeAmount() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.0));
        }

        @Test
        @DisplayName("zero amount expects IllegalArgumentException")
        void withdrawZeroAmount() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
        }

        @Test
        @DisplayName("on a frozen account expects IllegalStateException")
        void withdrawOnFrozenAccount() {
            account.freeze();
            assertThrows(IllegalStateException.class, () -> account.withdraw(50.0));
        }
    }

    @Nested
    @DisplayName("freeze / unfreeze")
    class FreezeUnfreezeTests {

        @Test
        @DisplayName("freeze the account")
        void freezeAccount() {
            account.freeze();
            assertNotNull(account);
        }

        @Test
        @DisplayName("unfreeze the account")
        void unfreezeAccount() {
            account.freeze();
            account.unfreeze();
            assertTrue(true);
        }

        @Test
        @DisplayName("deposit succeeds after unfreezing")
        void depositSucceedsAfterUnfreezing() {
            account.freeze();
            account.unfreeze();
            account.deposit(50.0);
        }
    }

    @Nested
    @DisplayName("transfer")
    class TransferTests {

        @Test
        @DisplayName("happy path (two accounts)")
        void transferHappyPath() {
            BankAccount target = new BankAccount("Bob", 50.0);
            account.transfer(target, 30.0);
            assertTrue(account.getBalance() >= 0);
        }

        @Test
        @DisplayName("null target expects IllegalArgumentException")
        void transferNullTarget() {
            assertThrows(IllegalArgumentException.class, () -> account.transfer(null, 30.0));
        }

        @Test
        @DisplayName("same account as target expects IllegalArgumentException")
        void transferSameAccount() {
            assertThrows(IllegalArgumentException.class, () -> account.transfer(account, 30.0));
        }

        @Test
        @DisplayName("insufficient funds expects IllegalArgumentException")
        void transferInsufficientFunds() {
            BankAccount target = new BankAccount("Bob", 50.0);
            assertThrows(IllegalArgumentException.class, () -> account.transfer(target, 150.0));
        }
    }

    @Nested
    @DisplayName("applyMonthlyInterest")
    class ApplyMonthlyInterestTests {

        @Test
        @DisplayName("positive balance with a non-zero rate")
        void applyMonthlyInterestPositiveBalance() {
            BankAccount acc = new BankAccount("Bob", 1200.0);
            acc.applyMonthlyInterest(12.0);
            assertTrue(acc.getBalance() > 1200.0);
        }

        @Test
        @DisplayName("zero balance (interest should not be applied)")
        void applyMonthlyInterestZeroBalance() {
            BankAccount acc = new BankAccount("Bob", 0.0);
            acc.applyMonthlyInterest(12.0);
            assertTrue(acc.getBalance() >= 0);
        }

        @Test
        @DisplayName("zero rate (balance should not change)")
        void applyMonthlyInterestZeroRate() {
            account.applyMonthlyInterest(0.0);
            assertTrue(account.getBalance() >= 0);
        }

        @Test
        @DisplayName("negative rate expects IllegalArgumentException")
        void applyMonthlyInterestNegativeRate() {
            assertThrows(IllegalArgumentException.class, () -> account.applyMonthlyInterest(-5.0));
        }
    }
}
