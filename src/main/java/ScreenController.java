import javax.swing.*;

class ScreenController {
    private static ImagesFrame imgF;
    private static JFrame currentFrame;
    private static JFrame whiteFrame;
    private static JFrame dirFrame;

    static void prepareFrames() {
        imgF =  new ImagesFrame();

        dirFrame = DirListFrame.getDirFrame();
        dirFrame.setVisible(false);

        whiteFrame = WhiteFrame.getWhiteFrame();
        whiteFrame.setVisible(false);

        currentFrame = dirFrame;
        currentFrame.setVisible(true);
    }

    static void setToWhite() {
        currentFrame.setVisible(false);
        currentFrame = whiteFrame;
        currentFrame.setVisible(true);
    }

    static void setToDir() {
        currentFrame.setVisible(false);
        currentFrame = dirFrame;
        currentFrame.setVisible(true);
    }

    static void setToImages(String parent) {
        currentFrame.setVisible(false);
        currentFrame = imgF.getImageFrame(parent);
        currentFrame.setVisible(true);
    }
}
