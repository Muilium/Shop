package com.company;
import java.util.ArrayList;
import java.util.Scanner;

class User {
    User(String FName, String SName, int Balance)
    {
        UserFName = FName;
        UserSName = SName;
        UserBalance = Balance;
    }

    String UserFName;
    String UserSName;
    int UserBalance;

    void reduceBalance(int Price)
    {
        UserBalance-=Price;
    }

    int checkBalance()
    {

        return UserBalance;
    }
}

class Item {
    Item(String Name, int Price, int Number)
    {
        ItemName = Name;
        ItemPrice = Price;
        ItemRemaining = Number;
    }

    String ItemName;
    int ItemPrice;
    int ItemRemaining;

    int getCost()
    {
        return ItemPrice;
    }

    int getRemaining()
    {
        return ItemRemaining;
    }

    void itemBought()
    {
        ItemRemaining--;
    }

    boolean canBuy(int amount)
    {
        if(ItemRemaining >= amount) return true;
        else return false;
    }

}



public class Shop {

    User CurrentUser;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();


    public static void main(String[] args) {
        ShopGUI app = new ShopGUI();
        app.setVisible(true);
        Item Bread = new Item("Bread", 100, 12);
        Item Potato = new Item("Potato", 250, 30);
        Item Milk = new Item("Milk", 300, 24);
        Item Dildo = new Item("Dildo", 228, 42);

        User User1 = new User("Stefanio", "Bandera", 2100);
        User User2 = new User("Lu", "Shige", 4800);
        User User3 = new User("Bold", "FromBrazzers", 50000);

        Shop shop = new Shop();

        shop.users.ensureCapacity(3);
        shop.users.add(User1);
        shop.users.add(User2);
        shop.users.add(User3);
        shop.CurrentUser = shop.users.get(0);


        shop.items.ensureCapacity(4);
        shop.items.add(Bread);
        shop.items.add(Potato);
        shop.items.add(Milk);
        shop.items.add(Dildo);

        Scanner sc = new Scanner(System.in);

        byte choose;
        int amount;
        while(true)
        {

            System.out.println("1. Check balance   2. Buy an item   3. Items List");
            choose = sc.nextByte();
            switch(choose)
            {
                case 1:
                {
                    System.out.println(shop.CurrentUser.checkBalance());
                    break;
                }
                case 2:
                {
                    System.out.println("Choose an item: ");
                    choose = sc.nextByte();
                    choose--;
                    System.out.printf("%s: %s%nCost: %s%nBuy: ", shop.items.get(choose).ItemName, shop.items.get(choose).ItemRemaining, shop.items.get(choose).getCost());
                    amount = sc.nextByte();
                    if(shop.items.get(choose).canBuy(amount)==true && shop.CurrentUser.checkBalance()>shop.items.get(choose).getCost()*amount)
                    {
                        while(amount-->0) {
                            shop.items.get(choose).itemBought();
                            shop.CurrentUser.reduceBalance(shop.items.get(choose).getCost());
                        }
                    }

                    break;
                }
                case 3:
                {
                    for(int i=0; i<shop.items.size(); i++) {
                        System.out.println(shop.items.get(i).ItemName);
                    }
                    break;
                }
            }
        }
    }

}
