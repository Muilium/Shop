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
/** Основное окно магазина со списком доступных предметов, их покупкой и текущим балансом,
 * открывается после входа в аккаунт в окне логина
 * table           таблица со списком предметов и кнопками покупки
 * currentbalance  текущий баланс залогиненного юзера
 * shop            магазин со всей информацией о юзерах и предметах
 * currentUser     залогиненный юзер
 */
public class ShopFrame extends JFrame
{
    private JTable table;
    private JTextField currentBalance;
    private Shop shop;
    private User currentUser;

    public ShopFrame(Application app, Shop shop, User user)
    {
        this.shop = shop;
        this.currentUser = user;
        this.getContentPane().setLayout(new FlowLayout());
        this.setSize(500, 220);
        setLocation(250, 150);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        /** Создание столбца с кнопками для покупки
         * с помощью кастомного рендера */
        ButtonColumn button = new ButtonColumn(table, buyButtonAction, 4);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(450, 120));

        JLabel balance = new JLabel("Balance: ");
        currentBalance = new JTextField(8);
        currentBalance.setText(String.valueOf(user.getBalance()));

        /** Кнопка, открывающая фрейм с историей покупок */
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

        /** пытаемся купить предмет */
        @Override
        public void actionPerformed(ActionEvent e) {
            /** получаем номер ряда (и номер предмета заодно) в котором была нажата кнопка*/
            int row = Integer.valueOf(e.getActionCommand());
            /** выводим сообщение о результате покупки в отдельное окошко*/
            JOptionPane.showMessageDialog(null, shop.buyItem(currentUser, row));

            /** обновляем предмет в таблице */
            int remaining = shop.getItems().get(row).getRemaining();
            int balance = currentUser.getBalance();
            table.getModel().setValueAt(remaining, row , 3);
            currentBalance.setText(String.valueOf(balance));
        }
    };
}
