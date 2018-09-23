package com.company.frames;

import com.company.Application;
import com.company.Shop;
import com.company.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame
{
    /** Поле для ввода имени */
    private JTextField nameField;
    /** Поле для ввода фамилии */
    private JTextField surnameField;

    public LoginFrame(Application app, Shop shop)
    {
        super("Log in");

        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 150);
        setLocation(400, 400);

        JLabel name = new JLabel("Name:       ");
        JLabel surname = new JLabel("Surname: ");
        nameField = new JTextField(10);
        surnameField = new JTextField(10);

        JButton logIn = createLoginButton(app, shop);

        add(name);
        add(this.nameField);
        add(surname);
        add(surnameField);
        add(logIn);
        setVisible(true);
    }

    /** */
    private JButton createLoginButton(Application app, Shop shop)
    {
        JButton logIn = new JButton("Log In");
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = LoginFrame.this.nameField.getText();
                String surname = surnameField.getText();

                User user = shop.login(name, surname);
                if(user == null)
                {
                    JOptionPane.showMessageDialog(null, "User is not found!");
                    return;
                }

                app.openFrame(new ShopFrame(app, shop, user));
            }
        });
        return logIn;
    }
}
