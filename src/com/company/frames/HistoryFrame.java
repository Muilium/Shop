package com.company.frames;

import com.company.Item;
import com.company.User;

import javax.swing.*;
import java.awt.*;
/** окно с историей покупок пользователя*/
public class HistoryFrame extends JFrame
{
    public HistoryFrame(User user)
    {
        super("History");
        getContentPane().setLayout(new FlowLayout());
        setSize(480, 320);
        setLocation(750, 150);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        String[] columnNamesHistory = {"ID", "Name", "Cost", "Amount"};
        Object[][] dataHistory = new Object[user.getItemsBought().size()][columnNamesHistory.length];

        int i = 0;
        for(Item item : user.getItemsBought())
        {
            dataHistory[i][0] = item.getName();
            dataHistory[i][1] = item.getId();
            dataHistory[i][2] = item.getPrice();
            dataHistory[i][3] = item.getRemaining();
            i++;
        }

        /** таблица со списком купленных предметов */
        JTable itemsBought = new JTable(dataHistory, columnNamesHistory);
        JScrollPane scrollPane = new JScrollPane(itemsBought);
        itemsBought.setPreferredScrollableViewportSize(new Dimension(450, 250));
        getContentPane().add(scrollPane);
    }
}
