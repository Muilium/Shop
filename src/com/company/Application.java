package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
//import java.util.Scanner;

public class Application extends JFrame {

    JTable table;

    public Application()
    {
        super("Shop");

        setLayout(new FlowLayout());

        Shop shop = new Shop();
        shop.loadFromJson("Shop.json");

        Object[][] data = new Object[shop.getItems().size()][5];

        /*JButton button = new JButton("Buy");
        ActionListener actionListener = new BuyAction();
        button.addActionListener(actionListener);*/

        Action action = new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("It works!");
            }
        };



        for(int i = 0; i < shop.getItems().size(); i++)
        {
            data[i][0] = shop.getItems().get(i).getId();
            data[i][1] = shop.getItems().get(i).getName();
            data[i][2] = shop.getItems().get(i).getPrice();
            data[i][3] = shop.getItems().get(i).getRemaining();
            data[i][4] = "Buy";
        }



        JFrame frame = new JFrame("Shop");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(400, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] columnNames = {"ID", "Name", "Cost", "Number of remaining", ""};
        table = new JTable(data, columnNames);

        ButtonColumn button = new ButtonColumn(table, action, 4);
        //table.add(button);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 110));
        frame.getContentPane().add(table);
        //frame.getContentPane().add(button);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Application app = new Application();

    }
}
