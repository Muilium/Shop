package com.company.frames;

import com.company.Application;
import com.company.ButtonColumn;
import com.company.Shop;
import com.company.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class ShopFrame extends JFrame
{
    /** Таблица в которой расположены остальные ui элементы */
    private JTable table;

    private JTextField currentBalance;

    private Shop shop;
    private User currentUser;

    public ShopFrame(Application app, Shop shop, User user)
    {
        this.shop = shop;
        this.currentUser = user;
        this.getContentPane().setLayout(new FlowLayout());
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(false);

        Object[][] data = new Object[shop.getItems().size()][5];
        for(int i = 0; i < shop.getItems().size(); i++)
        {
            data[i][0] = shop.getItems().get(i).getId();
            data[i][1] = shop.getItems().get(i).getName();
            data[i][2] = shop.getItems().get(i).getPrice();
            data[i][3] = shop.getItems().get(i).getRemaining();
            data[i][4] = "Buy";
        }

        String[] columnNames = {"ID", "Name", "Cost", "Remaining", ""};
        table = new JTable(data, columnNames);

        ButtonColumn button = new ButtonColumn(table, buyButtonAction, 4);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 110));

        JLabel balance = new JLabel("Balance: ");
        currentBalance = new JTextField(8);
        currentBalance.setText(String.valueOf(user.getBalance()));

        JButton historyButton = new JButton("History");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.openFrame(new HistoryFrame(user));
            }
        });
        getContentPane().add(scrollPane);
        getContentPane().add(historyButton);
        getContentPane().add(balance);
        getContentPane().add(currentBalance);
    }

    private Action buyButtonAction = new Action() {
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
            int balance = currentUser.getBalance();
            int itemCost = shop.getItems().get(row).getPrice();
            int remaining = shop.getItems().get(row).getRemaining();
            if(shop.buyItem(currentUser, row))
            {
                table.getModel().setValueAt(remaining - 1, row , 3);
                currentBalance.setText(String.valueOf(balance - itemCost));
            }
            else {
                if(balance < itemCost) // TODO перенести эту логику в Shop(т. е. shop должен возвращать сообщение о результате)
                    JOptionPane.showMessageDialog(null, "Not enough money!");
                else
                    JOptionPane.showMessageDialog(null, "Out of items!");
            }
            shop.save(shop.getUsers(), shop.getItems()); // toDO сохранение должно происходить внутри магазина
        }
    };
}
