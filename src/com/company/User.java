package com.company;

import java.util.ArrayList;

/** Аккаунт пользователя
 *
 */
public class User {
    private String name;
    private String surname;
    private int balance;
    ArrayList<Item> itemsBought = new ArrayList<>();
    public User(String name, String surname, int balance, ArrayList<Item> itemList)
    {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.itemsBought = itemList;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItemsBought() {
        return itemsBought;
    }

    public void itemBought(Item item)
        {
            Item obj = new Item(item.getName(), item.getId(), item.getPrice(), 1);
            this.itemsBought.add(obj);
        }
}
