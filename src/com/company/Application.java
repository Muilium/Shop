package com.company;

import com.company.frames.LoginFrame;

import javax.swing.*;


public class Application {

    /**  */
    private Shop shop;
    /** Текущий активный frame */
    private JFrame currentFrame;

    public Application()
    {
        shop = new Shop("Shop.json");
        openFrame(new LoginFrame(this, shop));
    }

    public static void main(String[] args) {
        new Application();

    }

    /** Открываем другой фрейм и делаем его текущим
     * TODO закрывать фреймы, а не просто скрывать их
     */
    public void openFrame(JFrame frame)
    {
        if (currentFrame != null)
            currentFrame.setVisible(false);
        currentFrame = frame;
        currentFrame.setVisible(true);
    }

}
