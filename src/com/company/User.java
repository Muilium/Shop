package com.company;

/** Аккаунт пользователя
 *
 */
public class User {
    private String name;
    private String surname;
    private int balance;

    public User(String name, String surname, int balance)
    {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;


    }
}
