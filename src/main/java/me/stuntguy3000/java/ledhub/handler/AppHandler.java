package me.stuntguy3000.java.ledhub.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDAction;
import me.stuntguy3000.java.ledhub.object.LEDBackground;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.config.MainConfiguration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

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

        ServiceHandler serviceHandler = LEDHub.getInstance().getServiceHandler();
        MainConfiguration mainConfiguration = LEDHub.getInstance().getConfigHandler().getMainConfiguration();

        // Multiplier
        Menu multiplierMenu = new Menu("Multiplier");

        for (int i = 0; i <= 100; i += 10) {
            MenuItem multiplierOption = new MenuItem(String.valueOf(i) + "%");
            multiplierOption.addActionListener(new MultiplierChangeItem(i));
            multiplierMenu.add(multiplierOption);
        }
        trayPopupMenu.add(multiplierMenu);

        // Backgrounds
        Menu backgroundsMenu = new Menu("Backgrounds");

        for (LEDBackground background : mainConfiguration.getBackgrounds()) {
            MenuItem item = new MenuItem(background.getBackgroundName());
            item.addActionListener(new BackgroundMenuItem(background, serviceHandler));
            backgroundsMenu.add(item);
        }
        trayPopupMenu.add(backgroundsMenu);

        // Services
        Menu servicesMenu = new Menu("Services");
        for (LEDService service : serviceHandler.getAllServices()) {
            Menu subMenu = new Menu(service.getServiceName());

            for (Map.Entry<String, LinkedList<LEDAction>> ledServiceAction : service.getServiceActions().entrySet()) {
                LinkedList<LEDAction> serviceAction = ledServiceAction.getValue();
                MenuItem actionButton = new MenuItem(ledServiceAction.getKey());
                actionButton.addActionListener(new ServiceMenuItem(serviceAction, serviceHandler));

                subMenu.add(actionButton);
            }
            servicesMenu.add(subMenu);
        }
        trayPopupMenu.add(servicesMenu);

        // Standard Options
        MenuItem action = new MenuItem("Restart");

        action.addActionListener(e -> LEDHub.getInstance().restart());
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

    @Data
    @AllArgsConstructor
    private class ServiceMenuItem implements ActionListener {
        private LinkedList<LEDAction> ledAction;
        private ServiceHandler serviceHandler;

        @Override
        public void actionPerformed(ActionEvent e) {
            serviceHandler.addToServiceQueue(ledAction);
        }
    }

    @Data
    @AllArgsConstructor
    private class BackgroundMenuItem implements ActionListener {
        private LEDBackground ledBackground;
        private ServiceHandler serviceHandler;

        @Override
        public void actionPerformed(ActionEvent e) {
            serviceHandler.setDefaultBackground(ledBackground);
            serviceHandler.processQueue();
        }
    }

    @Data
    @AllArgsConstructor
    private class MultiplierChangeItem implements ActionListener {
        private double amount;

        @Override
        public void actionPerformed(ActionEvent e) {
            LEDHub.MULTIPLIER = amount / 100;

            LEDHub.getInstance().getConfigHandler().getMainConfiguration().setLedMultiplier(LEDHub.MULTIPLIER);
            LEDHub.getInstance().getConfigHandler().saveConfig();
        }
    }
}
