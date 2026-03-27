package com.softwareanalytics.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for BankAccount (~70% coverage).
 * Intentionally leaves some paths uncovered for Software Analytics course exercise.
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
        @DisplayName("valid arguments: asserts owner, balance, and isFrozen")
        void constructorWithValidArguments() {
            BankAccount acc = new BankAccount("Bob", 250.50);

            assertEquals("Bob", acc.getOwner());
            assertEquals(250.50, acc.getBalance());
            assertFalse(acc.isFrozen());
        }

        @Test
        @DisplayName("Invalid arguments: null owner throws exception")
        void constructorWithNullOwner() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount(null, 250.50)
            );

            assertEquals("owner cannot be null", ex.getMessage());
        }

        @Test
        @DisplayName("Invalid arguments: blank owner throws exception")
        void constructorWithBlankOwner() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount("   ", 100.0)
            );

            assertEquals("owner cannot be blank", ex.getMessage());
        }

        @Test
        @DisplayName("Invalid arguments: negative balance throws exception")
        void constructorWithNegativeBalance() {
            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount("Bob", -50.0)
            );

            assertEquals("initial balance cannot be negative", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("deposit")
    class DepositTests {

        @Test
        @DisplayName("happy path: exact balance after deposit")
        void depositHappyPath() {
            account.deposit(50.0);

            assertEquals(150.0, account.getBalance());
        }

        @Test
        @DisplayName("multiple times: exact cumulative balance")
        void depositMultipleTimes() {
            account.deposit(25.0);
            account.deposit(75.0);
            account.deposit(10.0);

            assertEquals(210.0, account.getBalance());
        }

        @Test
        @DisplayName("Deposit to Frozen Account")
        void depositToFrozenPath() {
           account.freeze();
           IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> account.deposit(100)
            );

            assertEquals("account is frozen", ex.getMessage());
        }

        @Test
        @DisplayName("Deposit to Negative Amount")
        void depositNegativePath() {
           
           IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-100)
            );

            assertEquals("amount must be positive", ex.getMessage());
        }

    }

    @Nested
    @DisplayName("withdraw")
    class WithdrawTests {

        @Test
        @DisplayName("happy path: exact balance after withdrawal")
        void withdrawHappyPath() {
            account.withdraw(30.0);

            assertEquals(70.0, account.getBalance());
        }

        @Test
        @DisplayName("exact balance to zero")
        void withdrawExactBalanceToZero() {
            account.withdraw(100.0);

            assertEquals(0.0, account.getBalance());
        }

        @Test
        @DisplayName("insufficient funds: throws IllegalArgumentException")
        void withdrawInsufficientFunds() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.withdraw(150.0)
            );
            assertEquals("insufficient funds", ex.getMessage());
        }

        @Test
        @DisplayName("negative amount: throws IllegalArgumentException")
        void withdrawNegativeAmount() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> account.withdraw(-10.0)
            );
            assertEquals("amount must be positive", ex.getMessage());
        }

        @Test
        @DisplayName("withdraw frozen Account")
        void withdrawFromFrozen() {
            account.freeze();
            IllegalStateException ex = assertThrows(
                    IllegalStateException.class,
                    () -> account.withdraw(50.0)
            );
            assertEquals("account is frozen", ex.getMessage());
        }


    }

    @Nested
    @DisplayName("freeze")
    class FreezeTests {

        @Test
        @DisplayName("deposit to a frozen account")
        void freezeHappyPath() {
            account.freeze();

            IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> account.deposit(100.0)
            );

            assertEquals("account is frozen", ex.getMessage());
            assertTrue(account.isFrozen(), "Account should be frozen anymore");
        }
    }

    @Nested
    @DisplayName("unfreeze")
    class UnFreezeTests {

        @Test
        @DisplayName("Testing unfreeze account")
        void UnfreezeHappyPath() {

            account.freeze();
            IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> account.deposit(100.0)
            );

            assertEquals("account is frozen", ex.getMessage());


            account.unfreeze();
            account.deposit(100.0);
            assertEquals(200.0, account.getBalance(), "Balance should update after unfreezing");
            assertFalse(account.isFrozen(), "Account should not be frozen anymore");
        }
    }


    @Nested
    @DisplayName("transfer")
    class TransferTests {

        @Test
        @DisplayName("Testing transfer to target")
        void TransferNoTargetPath() {

            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.transfer(null, 100)
            );

            assertEquals("target cannot be null", ex.getMessage());
        }

        @Test
        @DisplayName("Testing transfer to self")
        void TransferToSelfPath() {

            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.transfer(account, 100)
            );

            assertEquals("cannot transfer to self", ex.getMessage());
        }

        @Test
        @DisplayName("Testing successfull Transfer")
        void TransferToOtherPath() {

            BankAccount accountOther = new BankAccount("John", 100.0);
            account.transfer(accountOther,100);
            assertEquals(0.0, account.getBalance());
            assertEquals(200.0, accountOther.getBalance());
        }

    }

    @Nested
    @DisplayName("applyMonthlyInterest")
    class ApplyMonthlyInterestTests {

        @Test
        @DisplayName("Testing applyMonthlyInterest negative rate")
        void applyNegativeRatePath() {

            IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> account.applyMonthlyInterest(-20.0)
            );

            assertEquals("rate cannot be negative", ex.getMessage());
        }


        @Test
        @DisplayName("Testing succefull applyMonthlyInterest ")
        void applySuccefullRatePath() {

            account.applyMonthlyInterest(20.0);

            assertEquals(100.0 + 20.0/12.0, account.getBalance());
        }


    }











}
