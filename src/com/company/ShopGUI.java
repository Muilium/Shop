package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;





public class ShopGUI extends JFrame {



    private JButton buy = new JButton("Buy");
    private JTextField input1 = new JTextField("", 5);
    private JLabel name1 = new JLabel("Bread");
    private JLabel amount1 = new JLabel("20");
    private JLabel price1 = new JLabel("100");

    public ShopGUI()
    {
        super("Shop");
        this.setBounds(100,100,400,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cont = this.getContentPane();
        cont.setLayout(new FlowLayout());
        cont.add(name1);
        cont.add(amount1);
        cont.add(price1);
        cont.add(input1);
        cont.add(buy);
    }
}
