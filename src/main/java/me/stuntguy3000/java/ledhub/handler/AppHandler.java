package me.stuntguy3000.java.ledhub.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.stuntguy3000.java.ledhub.LEDHub;
import me.stuntguy3000.java.ledhub.object.LEDService;
import me.stuntguy3000.java.ledhub.object.LEDServiceAction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

        for (LEDService service : serviceHandler.getAllServices().values()) {
            Menu subMenu = new Menu(service.getServiceName());

            for (Map.Entry<String, LEDServiceAction> ledServiceAction : service.getServiceActions().entrySet()) {
                MenuItem actionButton = new MenuItem(ledServiceAction.getKey());
                actionButton.addActionListener(new ServiceMenuItem(ledServiceAction.getValue(), serviceHandler));

                subMenu.add(actionButton);
            }
            trayPopupMenu.add(subMenu);
        }


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
        private LEDServiceAction ledServiceAction;
        private ServiceHandler serviceHandler;

        @Override
        public void actionPerformed(ActionEvent e) {
            serviceHandler.addToServiceQueue(ledServiceAction);
            serviceHandler.processQueue();
        }
    }
}
