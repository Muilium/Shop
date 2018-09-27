package com.company;

import com.company.frames.LoginFrame;

import javax.swing.*;

/** реализация интерфейса пользователя
 * shop магазин в котором будут производиться все покупки и храниться вся информация о предметах и юзерах
 * currentFrame текущий фрейм (окно логина, истории или основное)
 */
public class Application {
    private Shop shop;
    private JFrame currentFrame;

    /**  При создании приложение автоматически создает объект магазина
     * и вызывает метод для создания окна логина */
    public Application()
    {
        shop = new Shop("Shop.json");
        openFrame(new LoginFrame(this, shop));
    }

    /** Без понятия что тут происходит, где-то прочитал что нужно
     * делать так, вроде пока работает, если что уберу */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application();
            }
        });

    }

    /** Открываем другой фрейм и делаем его текущим
     *
     */
    public void openFrame(JFrame frame)
    {
        currentFrame = frame;
        currentFrame.setVisible(true);
    }
}
