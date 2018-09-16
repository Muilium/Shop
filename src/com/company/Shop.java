package com.company;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Магазин со списками предметов и покупателей
 *
 */
public class Shop {

    private String fileName;
    private User currentUser;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean buyItem (int itemNumber)
    {
        int balance = this.getCurrentUser().getBalance();
        int itemCost = this.getItems().get(itemNumber).getPrice();
        int remaining = this.getItems().get(itemNumber).getRemaining();

        if(balance >= itemCost)
            if(remaining > 0) {
                this.getItems().get(itemNumber).setRemaining(remaining - 1);
                this.getCurrentUser().setBalance(balance - itemCost);
                return true;
            }
            else {
                return false;
            }
        else {
            return false;
        }
    }

    public void loadFromJson(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line, fullFile = "";
            while((line = bufferReader.readLine()) != null) {
                fullFile += line;
            }
            bufferReader.close();

            JSONObject obj = new JSONObject(fullFile);
            JSONArray users = obj.getJSONArray("users");
            JSONArray items = obj.getJSONArray("items");
            for(int i = 0; i < users.length(); i++)
            {
                String name = users.getJSONObject(i).getString("name");
                String surname = users.getJSONObject(i).getString("surname");
                int balance = users.getJSONObject(i).getInt("balance");
                this.users.add(new User(name, surname, balance));
            }

            for(int i = 0; i < items.length(); i++)
            {
                String name = items.getJSONObject(i).getString("name");
                String id = items.getJSONObject(i).getString("id");
                int price = items.getJSONObject(i).getInt("price");
                int remaining = items.getJSONObject(i).getInt("remaining");
                this.items.add(new Item(name, id, price, remaining));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fileName = fileName;
    }
}
