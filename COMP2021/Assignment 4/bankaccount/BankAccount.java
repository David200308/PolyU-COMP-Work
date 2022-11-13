package hk.edu.polyu.comp.comp2021.assignment4.bankaccount;

/** A bank account object. */
public class BankAccount {

    private int balance;

    /** Instantiate an account with 'initialBalance'. */
    public BankAccount(int initialBalance){
        if(initialBalance < 0)
            throw new IllegalArgumentException();

        balance = initialBalance;
    }

    /** Balance of the account. The balance should never be negative. */
    public int getBalance(){
        return balance;
    }

    /** Deposit 'amount' into this account. 'amount' should always be positive. */
    public synchronized void deposit(int amount){
        if(amount <= 0)
            throw new IllegalArgumentException();

        // Add missing code here
        this.balance += amount;
        this.notify();
    }

    /** Withdraw 'amount' from this account. 'amount' should always be positive. */
    public synchronized void withdraw(int amount){
        if(amount <= 0)
            throw new IllegalArgumentException();

        // Add missing code here
        while(balance < amount){
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.balance -= amount;
        this.notify();
    }
}
