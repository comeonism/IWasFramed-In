import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class WhiteFrame {
    static JFrame getWhiteFrame() {
        JFrame whiteFrame = new JFrame();
        whiteFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        whiteFrame.getContentPane().setBackground(java.awt.Color.white);
        whiteFrame.setUndecorated(true);
        whiteFrame.setLayout(null);

        whiteFrame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    ScreenController.setToDir();
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        return whiteFrame;
    }
}
