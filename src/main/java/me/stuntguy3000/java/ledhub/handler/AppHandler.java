package me.stuntguy3000.java.ledhub.handler;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author stuntguy3000
 */
public class AppHandler {
    public void showUI() {
        SystemTray systemTray = SystemTray.getSystemTray();
        TrayIcon trayIcon;
        try {
            trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        PopupMenu trayPopupMenu = new PopupMenu();
        MenuItem action = new MenuItem("Restart");

        action.addActionListener(e -> JOptionPane.showMessageDialog(null, "Restarting.."));
        trayPopupMenu.add(action);

        MenuItem close = new MenuItem("Close");
        close.addActionListener(e -> System.exit(0));
        trayPopupMenu.add(close);

        trayIcon.setPopupMenu(trayPopupMenu);
        trayIcon.setToolTip("LEDHub");
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e2) {
            e2.printStackTrace();
        }
    }
}
