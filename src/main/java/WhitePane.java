import javax.swing.*;

class WhitePane {
    JFrame getWhiteFrame() {
        JFrame whiteFrame = new JFrame();
        whiteFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        whiteFrame.getContentPane().setBackground(java.awt.Color.white);
        whiteFrame.setUndecorated(true);
        whiteFrame.setLayout(null);

        return whiteFrame;
    }
}
