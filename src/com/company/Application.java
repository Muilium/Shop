package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
//import java.util.Scanner;

public class Application extends JFrame {

    JTable table;
    JTextField currentBalance;

    public Application()
    {
        super("Shop");

        setLayout(new FlowLayout());

        Shop shop = new Shop();
        shop.loadFromJson("Shop.json");
        shop.setCurrentUser(shop.getUsers().get(0));

        Object[][] data = new Object[shop.getItems().size()][5];

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
                if(shop.getCurrentUser().getBalance() >= (int)table.getModel().getValueAt(Integer.valueOf(e.getActionCommand()), 2))
                    if((int)table.getModel().getValueAt(Integer.valueOf(e.getActionCommand()), 3) > 0) {
                        table.getModel().setValueAt((int) table.getModel().getValueAt(Integer.valueOf(e.getActionCommand()), 3) - 1, Integer.valueOf(e.getActionCommand()), 3);
                        shop.getCurrentUser().setBalance(shop.getCurrentUser().getBalance() - (int)table.getModel().getValueAt(Integer.valueOf(e.getActionCommand()), 2));
                        currentBalance.setText(String.valueOf(shop.getCurrentUser().getBalance()));
                    }
                    else
                        System.out.println("Out of items!");
                    else System.out.println("Out of money!");
                System.out.println(shop.getCurrentUser().getBalance());
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
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String[] columnNames = {"ID", "Name", "Cost", "Number of remaining", ""};
        table = new JTable(data, columnNames);

        ButtonColumn button = new ButtonColumn(table, action, 4);
        //table.add(button);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 110));

        JLabel balance = new JLabel("Balance: ");
        currentBalance = new JTextField(8);
        currentBalance.setText(String.valueOf(shop.getCurrentUser().getBalance()));

        frame.getContentPane().add(table);
        frame.getContentPane().add(balance);
        frame.getContentPane().add(currentBalance);

        //frame.getContentPane().add(button);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Application app = new Application();

    }
}
