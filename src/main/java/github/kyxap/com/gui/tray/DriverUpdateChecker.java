package github.kyxap.com.gui.tray;

import static github.kyxap.com.config.AppConfig.getProperty;
import static github.kyxap.com.config.AppConfig.loadProperties;
import github.kyxap.com.config.ConfigProps;
import github.kyxap.com.utils.Logger;
import github.kyxap.com.utils.CmdWorker;
import static github.kyxap.com.gui.windows.PopUpWorker.openAboutWindow;
import static github.kyxap.com.gui.windows.PopUpWorker.popUpInfo;
import github.kyxap.com.utils.HttpWorker;
import static github.kyxap.com.utils.Scheduler.scheduleVersionCheck;
import github.kyxap.com.utils.StringParser;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.AWTException;
import java.awt.MenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * https://www.nvidia.com/Download/Find.aspx?lang=en-us
 * https://www.nvidia.com/Download/processFind.aspx?psid=127&pfid=995&osid=135&lid=1&whql=1&lang=en-us&ctk=0&qnfslb=00&dtcid=1
 * <p>
 * linux versions:
 * https://github.com/aaronp24/nvidia-versions
 */
public class DriverUpdateChecker {

    // Load an image for the tray icon
    static Image TRAY_ICON = Toolkit.getDefaultToolkit().getImage(DriverUpdateChecker.class.getClassLoader().getResource("img/icon.png"));

    private static String uri;
    public static void main(String[] args) throws IOException {
        // Load properties
        loadProperties();
        // Access properties
        uri = getProperty(ConfigProps.App.URI_TO_GET_DATA_FROM.getDescription());
        boolean debugEnabled = Boolean.parseBoolean(getProperty(ConfigProps.LoggerSettings.DEBUG.getDescription()));
        boolean devOutputEnabled = Boolean.parseBoolean(getProperty(ConfigProps.LoggerSettings.DEV_OUTPUT.getDescription()));
        Logger.setLoggerOutput(debugEnabled, devOutputEnabled);

        Logger.log("URI which will be using to parse data from: " + uri);

        if (SystemTray.isSupported()) {
            DriverUpdateChecker trayExample = new DriverUpdateChecker();
            trayExample.createSystemTray();
        } else {
            // TODO: make sure scheduler works without tray
            String msg = "SystemTray is not supported on this platform, but scheduler is started";
            popUpInfo(msg);
            Logger.log(msg);
        }
        scheduleVersionCheck();
    }

    private void createSystemTray() {
        try {
            // Create a system tray instance
            SystemTray systemTray = SystemTray.getSystemTray();

            // Create a tray icon
            TrayIcon trayIcon = new TrayIcon(TRAY_ICON, "GPU Update Checker", setPopupMenu());

            // Set default action for double-click
            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Your action on double-click
                    Logger.log("Tray icon clicked");
                }
            });

            // Add the tray icon to the system tray
            systemTray.add(trayIcon);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private PopupMenu setPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();

        // Add "Check Driver Updates" menu item
        MenuItem checkUpdatesItem = new MenuItem("Check Driver Updates");
        checkUpdatesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle the "Check Driver Updates" action
                Logger.log("Check Driver Updates clicked");
                if (isNewUpdateAvailable()) {
                    popUpInfo( "New driver version updated is available");
                } else {
                    popUpInfo( "You are using latest version, no updates available");
                }
            }
        });
        popupMenu.add(checkUpdatesItem);

        // Add "About" menu item
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle the "About" action
                Logger.log("About clicked");
                openAboutWindow();
            }
        });
        popupMenu.add(aboutItem);

        // Add separator
        popupMenu.addSeparator();

        // Add "Exit" menu item
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        popupMenu.add(exitItem);

        return popupMenu;
    }

    public static boolean isNewUpdateAvailable() {
        if (uri != null) {
            String latestVer = StringParser.extractVersionFromHtlmString(HttpWorker.get(uri));
            String installedVer = CmdWorker.runCmd("nvidia-smi");
            String printInfo = "Needs update: ";
            Logger.log(printInfo + !installedVer.equals(latestVer));

            return !installedVer.equals(latestVer);
        } else {
            Logger.log("uri is null at the start");
            return false;
        }
    }
}
