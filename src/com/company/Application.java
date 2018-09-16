package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
//import java.util.Scanner;

public class Application extends JFrame {

    JTable table;
    JTextField currentBalance;
    JTextField nameEntered;
    JTextField surnameEntered;

    public Application()
    {
        super("Shop");

        JFrame frame = new JFrame("Shop");

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
                int row = Integer.valueOf(e.getActionCommand());
                int balance = shop.getCurrentUser().getBalance();
                int itemCost = shop.getItems().get(row).getPrice();
                int remaining = shop.getItems().get(row).getRemaining();
                if(shop.buyItem(row))
                {
                    table.getModel().setValueAt(remaining - 1, row , 3);
                    currentBalance.setText(String.valueOf(balance - itemCost));
                }
                else {
                    if(balance < itemCost)
                        JOptionPane.showMessageDialog(null, "Not enough money!");
                    else
                        JOptionPane.showMessageDialog(null, "Out of items!");
                }
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

        JFrame loginFrame = new JFrame("Log in");
        loginFrame.getContentPane().setLayout(new FlowLayout());
        loginFrame.setSize(200, 150);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel name = new JLabel("Name:       ");
        JLabel surname = new JLabel("Surname: ");
        JButton logIn = new JButton("Log In");
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameEntered.getText();
                String surname = surnameEntered.getText();
                boolean userFound = false;

                for(int i = 0; i < shop.getUsers().size(); i++)
                    if(name.equals( shop.getUsers().get(i).getName()) && surname.equals( shop.getUsers().get(i).getSurname())) {
                        userFound = true;
                        shop.setCurrentUser(shop.getUsers().get(i));
                        loginFrame.setVisible(false);
                        frame.setVisible(true);
                        break;
                    }
                if(!userFound)
                {
                    JOptionPane.showMessageDialog(null, "User is not found!");
                }
            }
        });
        nameEntered = new JTextField(10);
        surnameEntered = new JTextField(10);

        loginFrame.add(name);
        loginFrame.add(nameEntered);
        loginFrame.add(surname);
        loginFrame.add(surnameEntered);
        loginFrame.add(logIn);
        loginFrame.setVisible(true);


        frame.getContentPane().setLayout(new FlowLayout());
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);


        String[] columnNames = {"ID", "Name", "Cost", "Кemaining", ""};
        table = new JTable(data, columnNames);

        ButtonColumn button = new ButtonColumn(table, action, 4);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 110));

        JLabel balance = new JLabel("Balance: ");
        currentBalance = new JTextField(8);
        currentBalance.setText(String.valueOf(shop.getCurrentUser().getBalance()));

        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(balance);
        frame.getContentPane().add(currentBalance);
    }

    public static void main(String[] args) {
        Application app = new Application();

    }
}
