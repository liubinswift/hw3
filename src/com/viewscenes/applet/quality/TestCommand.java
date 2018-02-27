package com.viewscenes.applet.quality;

import com.viewscenes.applet.tcp.MonTCPCommand;
import com.viewscenes.applet.quality.GetDataThread;

public class TestCommand {
    public TestCommand() {
        super();
    }

    public static void main(String[] args) {
        GetDataThread dataThread = new GetDataThread();
        byte[] cmd = MonTCPCommand.getVideoQualityCommand("112.25");
        //byte[] cmd = MonTCPCommand.getRadioQualityCommand("97.8");
        dataThread.setCommand(cmd);
        dataThread.ip = "10.1.2.141";
        dataThread.port = 8010;
        dataThread.start();

    }
}
