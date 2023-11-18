package github.kyxap.com.tray;

import github.kyxap.com.cmd.CmdWorker;
import github.kyxap.com.http.HttpWorker;
import github.kyxap.com.utils.StringParser;

import java.awt.event.ActionEvent;

public class ActionWorker {
    public void actionPerformed(ActionEvent e) {
        // Handle the "Check Driver Updates" action
        System.out.println("Check Driver Updates clicked");
        String latestVer = StringParser.extractVersionFromHtlmString(HttpWorker.get(uri));
        String installedVer = CmdWorker.runCmd("nvidia-smi");
        System.out.println("New version is available: " + !installedVer.equals(latestVer));
        if (!installedVer.equals(latestVer)) popUpInfo("New version is available: " + latestVer);
    }
}
