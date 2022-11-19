package hk.edu.polyu.comp.comp2021.assignment4.bankaccount;

public class Depositor implements Runnable{
    private BankAccount ba;
    private int amount;

    public Depositor(BankAccount ba, int amount){
        this.ba = ba;
        this.amount = amount;
    }

    public void run() {
        for(int i = 0; i < 10; i++){
            // A deposit is always allowed.
            ba.deposit(amount);
        }
    }
}

