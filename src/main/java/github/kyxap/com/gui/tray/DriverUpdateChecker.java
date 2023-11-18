package github.kyxap.com.tray;

import github.kyxap.com.cmd.CmdWorker;
import github.kyxap.com.http.HttpWorker;
import github.kyxap.com.utils.StringParser;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.Label;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * https://www.nvidia.com/Download/Find.aspx?lang=en-us
 * https://www.nvidia.com/Download/processFind.aspx?psid=127&pfid=995&osid=135&lid=1&whql=1&lang=en-us&ctk=0&qnfslb=00&dtcid=1
 * <p>
 * linux versions:
 * https://github.com/aaronp24/nvidia-versions
 */
public class DriverUpdateChecker {

    // Load an image for the tray icon
    public Image APP_ICON = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img/icon.png"));
    private String uri = "https://www.nvidia.com/Download/processFind.aspx?psid=127&pfid=995&osid=135&lid=1&whql=1&lang=en-us&ctk=0&qnfslb=00&dtcid=1";

    public static void main(String[] args) {
        if (SystemTray.isSupported()) {
            DriverUpdateChecker trayExample = new DriverUpdateChecker();
            trayExample.createSystemTray();
        } else {
            String msg = "SystemTray is not supported on this platform";
            popUpError(msg);
            System.out.println(msg);
        }
    }

    private void createSystemTray() {
        try {
            // Create a system tray instance
            SystemTray systemTray = SystemTray.getSystemTray();

            // Create a tray icon
            TrayIcon trayIcon = new TrayIcon(APP_ICON, "System Tray Example", setPopupMenu());

            // Set default action for double-click
            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Your action on double-click
                    System.out.println("Tray icon clicked");
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
                System.out.println("Check Driver Updates clicked");
                String latestVer = StringParser.extractVersionFromHtlmString(HttpWorker.get(uri));
                String installedVer = CmdWorker.runCmd("nvidia-smi");
                System.out.println("New version is available: " + !installedVer.equals(latestVer));
                if (!installedVer.equals(latestVer)) popUpInfo("New version is available: " + latestVer);
            }
        });
        popupMenu.add(checkUpdatesItem);

        // Add "About" menu item
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle the "About" action
                System.out.println("About clicked");
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

    /**
     * No External dependecy on Swing, X works with extra
     */
    private void openAboutWindow() {
        // Create and display a simple window for "About"
        Frame aboutFrame = new Frame("About");
        aboutFrame.setSize(300, 200);

        Label label = new Label("Installed driver version: " + CmdWorker.runCmd("nvidia-smi"));
        aboutFrame.add(label);
        aboutFrame.setIconImage(APP_ICON);

        // Allow closing the Frame by clicking the X button
        aboutFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aboutFrame.dispose();
            }
        });

        // Center the Frame on the screen
        centerFrameOnScreen(aboutFrame);

        aboutFrame.setVisible(true);
    }

    private static void popUpError(String error) {
        // Create and display a simple window for "About"
        Frame aboutFrame = new Frame("Error");
        aboutFrame.setSize(300, 200);

        Label label = new Label(error);
        aboutFrame.add(label);
//        aboutFrame.setIconImage(APP_ICON);

        // Allow closing the Frame by clicking the X button
        aboutFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aboutFrame.dispose();
            }
        });

        // Center the Frame on the screen
        centerFrameOnScreen(aboutFrame);
        aboutFrame.setVisible(true);
    }

    public static void popUpInfo(String infoMsg) {
        // Create and display a simple window for "About"
        Frame aboutFrame = new Frame("Info");
        aboutFrame.setSize(300, 200);

        Label label = new Label(infoMsg);
        aboutFrame.add(label);
        aboutFrame.setIconImage(APP_ICON);

        // Allow closing the Frame by clicking the X button
        aboutFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aboutFrame.dispose();
            }
        });

        // Center the Frame on the screen
        centerFrameOnScreen(aboutFrame);
        aboutFrame.setVisible(true);
    }

    private static void centerFrameOnScreen(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    // TODO not sure why to use Swing at this point
//    private void openAboutWindow2() {
//        // Create and display a Swing JFrame for "About"
//        JFrame aboutFrame = new JFrame("About");
//        aboutFrame.setSize(300, 200);
//
//        // Set an icon for the JFrame
//        aboutFrame.setIconImage(APP_ICON);
//
//        JLabel label = new JLabel("This is the About window.");
//        aboutFrame.add(label);
//
//        // Allow closing the JFrame by clicking the X button
//        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        // Center the JFrame on the screen
//        centerFrameOnScreen(aboutFrame);
//
//        aboutFrame.setVisible(true);
//    }

//    private void centerFrameOnScreen(JFrame frame) {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int x = (screenSize.width - frame.getWidth()) / 2;
//        int y = (screenSize.height - frame.getHeight()) / 2;
//        frame.setLocation(x, y);
//    }
}
