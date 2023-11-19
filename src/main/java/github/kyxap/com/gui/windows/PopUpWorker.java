package github.kyxap.com.gui.windows;

import github.kyxap.com.utils.CmdWorker;
import github.kyxap.com.gui.tray.DriverUpdateChecker;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PopUpWorker {
    static Image POP_UP_ICON = Toolkit.getDefaultToolkit().getImage(DriverUpdateChecker.class.getClassLoader().getResource("img/icon.png"));

    public PopUpWorker(Image POP_UP_ICON) {
        this.POP_UP_ICON = POP_UP_ICON;
    }
    public PopUpWorker() {
    }

    /**
     * No External dependecy on Swing, X works with extra
     */
    public static void openAboutWindow() {
        // Create and display a simple window for "About"
        Frame aboutFrame = new Frame("About");
        aboutFrame.setSize(300, 200);

        Label label = new Label("Installed driver version: " + CmdWorker.runCmd("nvidia-smi"));
        aboutFrame.add(label);
        aboutFrame.setIconImage(POP_UP_ICON);

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

    public static void popUpError(String error) {
        // Create and display a simple window for "About"
        Frame aboutFrame = new Frame("Error");
        aboutFrame.setSize(300, 200);

        Label label = new Label(error);
        aboutFrame.add(label);
//        aboutFrame.setIconImage(appIcon);

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
        aboutFrame.setIconImage(POP_UP_ICON);

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

    private static void  centerFrameOnScreen(Frame frame) {
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
