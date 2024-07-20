package org.dreaght.chatstatissue.handler;

import java.awt.Robot;

public class RobotHandler {
    private Robot robot;

    public RobotHandler() throws Exception {
        robot = new Robot();
    }

    public void simulateAltTab() {
        try {
            robot.keyPress(java.awt.event.KeyEvent.VK_ALT);
            robot.keyPress(java.awt.event.KeyEvent.VK_TAB);
            robot.keyRelease(java.awt.event.KeyEvent.VK_TAB);
            robot.keyRelease(java.awt.event.KeyEvent.VK_ALT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
