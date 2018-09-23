package com.company.frames;

import com.company.Item;
import com.company.User;

import javax.swing.*;
import java.awt.*;

public class HistoryFrame extends JFrame
{
    public HistoryFrame(User user)
    {
        super("History");
        getContentPane().setLayout(new FlowLayout());
        setSize(460, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        JTable itemsBought = new JTable(dataHistory, columnNamesHistory);
        JScrollPane scrollPane = new JScrollPane(itemsBought);

        getContentPane().add(scrollPane);
    }
}
