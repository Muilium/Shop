package com.company;

/** Предмет в магазине
 *   name        название товара
 *   id          код товара
 *   price       цена товара
 *   remaining   количество оставшихся единиц товара
 */
public class Item {
    private String name;
    private String id;
    private int price;
    private int remaining;

    public Item(String name, String id, int price, int number)
    {
        this.name = name;
        this.id = id;
        this.price = price;
        this.remaining = number;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
