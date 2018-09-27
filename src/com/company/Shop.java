package com.company;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Магазин со списками предметов и покупателей
 *   fileName   имя json-файла из которого будет считываться информация
 *   users      список аккаунтов юзеров
 *   items      список предметов в магазине
 */
public class Shop {

    private String fileName;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    public Shop(String jsonFile)
    {
        this.load(jsonFile);
    }

    /** Покупка предмета в магазине
     * @param user пользователь купивший предмет (залогиненный на момент покупки)
     * @param itemNumber номер предмета в списке предметов
     * @return string сообщение о результате покупки
     * */

    public String buyItem(User user, int itemNumber)
    {
        int balance = user.getBalance();
        int itemCost = items.get(itemNumber).getPrice();
        int remaining = items.get(itemNumber).getRemaining();

        if(balance >= itemCost)
            if(remaining > 0) {
                user.itemBought(items.get(itemNumber));
                items.get(itemNumber).setRemaining(remaining - 1);
                user.setBalance(balance - itemCost);
                this.save(this.getUsers(), this.getItems());
                return "Purchase completed!";
            }
            else {
                return "Out of items!";
            }
        else {
            return "Not enough money!";
        }
    }

    /** Пытаемся залогиниться
     * @param name имя юзера
     * @param surname фамилия юзера
     * @return null если не получилось, иначе юзер под которым залогинились
     */
    public User login(String name, String surname)
    {
        for(User user : users)
        {
            if (name.equals(user.getName()) && surname.equals(user.getSurname()))
            {
                return user;
            }
        }
        return null;
    }

    /** грузим данные магазина из json-файла
     * @param fileName имя json-файла
     */
    public void load(String fileName)
    {
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
                ArrayList<Item> itemList = loadHistory(users, i);
                this.users.add(new User(name, surname, balance, itemList));
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
        } catch (JSONException e)
        {
            System.out.printf("Error while loading user: %s", e.getMessage());
        }

        this.fileName = fileName;
    }

    /** отдельно считывание истории покупок, если возникнет ошибка прога
     * продолжит работать
     */
    public ArrayList<Item> loadHistory(JSONArray users, int i)
    {
        ArrayList<Item> itemList = new ArrayList<>();
        try {
            JSONArray history = users.getJSONObject(i).getJSONArray("history");
            for(int j=0;j<history.length();j++)
            {
                String itemName = history.getJSONObject(j).getString("name");
                String itemId = history.getJSONObject(j).getString("id");
                int itemCost = history.getJSONObject(j).getInt("price");
                int itemAmount = history.getJSONObject(j).getInt("amount");
                itemList.add(new Item(itemName, itemId, itemCost, itemAmount));
            }
        }
        catch (JSONException e)
        {
            System.out.printf("Error while loading user history: %s", e.getMessage());
        }
        return itemList;
    }

    /** сохранение списка предметов и юзеров в json-файл
     * @param usersArray список юзеров
     * @param itemsArray список предметов
     */

    public void save(ArrayList<User> usersArray, ArrayList<Item> itemsArray)
    {
        JSONObject obj = new JSONObject();
        JSONArray users = new JSONArray();
        JSONArray items = new JSONArray();

        for(int i=0;i<usersArray.size();i++) {
            JSONArray history = new JSONArray();
            JSONObject user = new JSONObject();
            user.put("name", usersArray.get(i).getName());
            user.put("surname", usersArray.get(i).getSurname());
            user.put("balance", usersArray.get(i).getBalance());
            for(int j=0;j<usersArray.get(i).getItemsBought().size();j++) {
                JSONObject item = new JSONObject();
                item.put("name", usersArray.get(i).getItemsBought().get(j).getName());
                item.put("id", usersArray.get(i).getItemsBought().get(j).getId());
                item.put("price", usersArray.get(i).getItemsBought().get(j).getPrice());
                item.put("amount", usersArray.get(i).getItemsBought().get(j).getRemaining());
                history.put(item);
            }
            user.put("history", history);
            users.put(user);
        }

        for(int i=0;i<itemsArray.size();i++)
        {
            JSONObject item = new JSONObject();
            item.put("name", itemsArray.get(i).getName());
            item.put("id", itemsArray.get(i).getId());
            item.put("remaining", itemsArray.get(i).getRemaining());
            item.put("price", itemsArray.get(i).getPrice());
            items.put(item);
        }
        obj.put("users", users);
        obj.put("items", items);
        try (FileWriter file = new FileWriter(fileName)) {

            file.write(obj.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
