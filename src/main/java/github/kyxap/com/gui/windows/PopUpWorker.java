package github.kyxap.com.gui.windows;

import static github.kyxap.com.gui.tray.DriverUpdateChecker.TRAY_ICON_IMG;
import github.kyxap.com.utils.CmdWorker;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PopUpWorker {
    static Image POP_UP_ICON;

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
        if (POP_UP_ICON == null) {
            aboutFrame.setIconImage(TRAY_ICON_IMG);
        } else aboutFrame.setIconImage(POP_UP_ICON);


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
        Frame popUpInfo = new Frame("Info");
        popUpInfo.setSize(300, 200);

        Label label = new Label(infoMsg);
        popUpInfo.add(label);
        if (POP_UP_ICON == null) {
            popUpInfo.setIconImage(TRAY_ICON_IMG);
        } else popUpInfo.setIconImage(POP_UP_ICON);

        // Allow closing the Frame by clicking the X button
        popUpInfo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                popUpInfo.dispose();
            }
        });

        // Center the Frame on the screen
        centerFrameOnScreen(popUpInfo);
        popUpInfo.setVisible(true);
    }

    private static void centerFrameOnScreen(Frame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}
