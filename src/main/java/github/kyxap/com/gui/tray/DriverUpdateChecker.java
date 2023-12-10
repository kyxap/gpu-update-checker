package github.kyxap.com.gui.tray;

import github.kyxap.com.config.ConfigProps;
import github.kyxap.com.utils.CmdWorker;
import github.kyxap.com.utils.HttpWorker;
import github.kyxap.com.utils.Logger;
import github.kyxap.com.utils.StringParser;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static github.kyxap.com.config.AppConfig.getProperty;
import static github.kyxap.com.config.AppConfig.loadProperties;
import static github.kyxap.com.gui.windows.PopUpType.ABOUT;
import static github.kyxap.com.gui.windows.PopUpType.INFO;
import static github.kyxap.com.gui.windows.PopUpWorker.openAboutWindow;
import static github.kyxap.com.gui.windows.PopUpWorker.popUpInfo;
import static github.kyxap.com.utils.Scheduler.scheduleVersionCheck;

/**
 * https://www.nvidia.com/Download/Find.aspx?lang=en-us
 * https://www.nvidia.com/Download/processFind.aspx?psid=127&pfid=995&osid=135&lid=1&whql=1&lang=en-us&ctk=0&qnfslb=00&dtcid=1
 * <p>
 * linux versions:
 * https://github.com/aaronp24/nvidia-versions
 */
public class DriverUpdateChecker {

    // Load an image for the tray icon
    static URL TRAY_ICON = DriverUpdateChecker.class.getClassLoader().getResource("img/icon_v2a.png");

    public static BufferedImage TRAY_ICON_IMG;

    private static String uri;

    public static void main(final String[] args) throws IOException {
        // Load properties
        loadProperties();
        // Access properties
        uri = getProperty(ConfigProps.App.URI_TO_GET_DATA_FROM.getDescription());
        final boolean debugEnabled = Boolean.parseBoolean(getProperty(ConfigProps.LoggerSettings.DEBUG.getDescription()));
        final boolean devOutputEnabled = Boolean.parseBoolean(getProperty(ConfigProps.LoggerSettings.DEV_OUTPUT.getDescription()));
        Logger.setLoggerOutput(debugEnabled, devOutputEnabled);

        Logger.log("URI which will be using to parse data from: " + uri);

        if (SystemTray.isSupported()) {
            final DriverUpdateChecker trayExample = new DriverUpdateChecker();
            trayExample.createSystemTray();
        } else {
            // TODO: make sure scheduler works without tray
            final String msg = "SystemTray is not supported on this platform, but scheduler is started";
            popUpInfo(INFO, msg);
            Logger.log(msg);
        }
        scheduleVersionCheck();
    }

    private void createSystemTray() {
        try {
            // Create a system tray instance
            final SystemTray systemTray = SystemTray.getSystemTray();

            // Create a tray icon
            TRAY_ICON_IMG = ImageIO.read(TRAY_ICON);
            final TrayIcon trayIcon = new TrayIcon(TRAY_ICON_IMG, "GPU Update Checker", setPopupMenu());
            trayIcon.setImageAutoSize(true);

            // Set default action for double-click
            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    // Your action on double-click
                    Logger.log("Tray icon clicked");
                }
            });

            // Add the tray icon to the system tray
            systemTray.add(trayIcon);

        } catch (final AWTException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PopupMenu setPopupMenu() {
        final PopupMenu popupMenu = new PopupMenu();

        // Add "Check Driver Updates" menu item
        final MenuItem checkUpdatesItem = new MenuItem("Check Driver Updates");
        checkUpdatesItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // Handle the "Check Driver Updates" action
                Logger.log("Check Driver Updates clicked");
                if (isNewUpdateAvailable()) {
                    popUpInfo(INFO, "New driver version updated is available");
                } else {
                    popUpInfo(INFO, "You are using latest version, no updates available");
                }
            }
        });
        popupMenu.add(checkUpdatesItem);

        // Add "About" menu item
        final MenuItem aboutItem = new MenuItem(ABOUT);
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // Handle the "About" action
                Logger.log(ABOUT + " clicked");
                openAboutWindow();
            }
        });
        popupMenu.add(aboutItem);

        // Add separator
        popupMenu.addSeparator();

        // Add "Exit" menu item
        final MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });

        popupMenu.add(exitItem);

        return popupMenu;
    }

    public static boolean isNewUpdateAvailable() {
        if (uri != null) {
            final String latestVer = StringParser.extractVersionFromHtlmString(HttpWorker.get(uri));
            final String installedVer = CmdWorker.runCmd("nvidia-smi");
            final String printInfo = "Needs update: ";
            Logger.log(printInfo + !installedVer.equals(latestVer));

            return !installedVer.equals(latestVer);
        } else {
            Logger.log("uri is null at the start");
            return false;
        }
    }
}
