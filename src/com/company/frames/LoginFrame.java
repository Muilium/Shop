package com.company.frames;

import com.company.Application;
import com.company.Shop;
import com.company.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/** Окно логина, появляется при запуске приложения
 * nameField поле для ввода имени пользователя
 * surnameField поле для ввода фамилии пользователя
 */
public class LoginFrame extends JFrame
{
    private JTextField nameField;
    private JTextField surnameField;

    public LoginFrame(Application app, Shop shop)
    {
        super("Log in");

        /** Устанавливает основные параметры окна */
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 150);
        setLocation(600, 300);

        /** Создание текстовых полей и полей для ввода*/
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

    /** Создает кнопку входа в аккаунт, при нажатии
     * магазин ищет введенного пользователя среди списка пользователей,
     * если находит то создается основное окно магазина и
     * закрывается окно логина, иначе выводит сообщение об ошибке
     */
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
                dispose();
            }
        });
        return logIn;
    }
}
