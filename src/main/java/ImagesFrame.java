import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

class ImagesFrame extends JPanel {
    private final static Logger logger = Logger.getLogger(ImagesFrame.class.getName());

    private JFrame imgFrame;
    private static ArrayList<String> allImages = new ArrayList<>();
    private static String imagesPath = "";
    private static JLabel currentImage = null;

    private int imgIndex = 0;

    ImagesFrame() {}

    private ImagesFrame(String dirName) {
        setLayout(new BorderLayout());

        imgIndex = 0;
        setup(dirName);
        logger.info("preparing to display the first image in the directory");
        setDisplayImage();
    }

    private void setup(String dirName) {
        imagesPath = Utils.HOME_DIRECTORY + dirName + "/";

        File imagesDir = new File(imagesPath);

        logger.info("collecting image paths in the specified directory");
        if (imagesDir.exists() && imagesDir.isDirectory()) allImages = new ArrayList<>(Arrays.asList(imagesDir.list()));
    }

    private void setDisplayImage() {
        String imgPath = Utils.EMPTY_IMAGE;

        if (allImages.size() > 0) imgPath = getImagePath();
        if (currentImage != null) {
            currentImage.setIcon(getScaledImage(imgPath));
        } else {
            currentImage = new JLabel(getScaledImage(imgPath));
            this.add(currentImage);
        }
    }

    private String getImagePath() {
        return imagesPath + allImages.get(imgIndex);
    }

    private ImageIcon getScaledImage(String path){
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage();
        Dimension scaledDimension = Utils.getScaledDimension(new Dimension(image.getWidth(null), image.getHeight(null)));
        Image newImg = image.getScaledInstance((int) scaledDimension.getWidth(), (int) scaledDimension.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    JFrame getImageFrame(String dirName) {
        logger.info("creating an images frame");
        reset();

        imgFrame = new JFrame();
        imgFrame.setContentPane(new ImagesFrame(dirName));
        imgFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        imgFrame.setUndecorated(true);
        imgFrame.setVisible(true);

        imgFrame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                    pressedLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                    pressedRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DECIMAL) {
                    deleteSelected();
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
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

        logger.info("images frame prepared, returning");
        return imgFrame;
    }

    private void reset() {
        logger.info("resetting variables");
        if (imgFrame != null) imgFrame.dispose();

        imgFrame = null;
        allImages = new ArrayList<>();
        imagesPath = "";
        currentImage = null;
    }

    private void pressedLeft() {
        decrementImageIndex();
        setDisplayImage();
    }

    private void decrementImageIndex() {
        if (imgIndex > 0) imgIndex--;
    }

    private void pressedRight() {
        incrementImageIndex();
        setDisplayImage();
    }

    private void incrementImageIndex() { if (imgIndex < allImages.size() - 1) imgIndex++; }

    private void deleteSelected() {
        if (allImages.size() > 0) {
            File file = new File(getImagePath());

            if (file.exists()) {
                logger.info("deleting image: " + allImages.get(imgIndex));
                file.delete();

                allImages.remove(imgIndex);

                if (allImages.size() > 0) {
                    if (imgIndex > allImages.size() - 1) imgIndex = 0;
                }

                setDisplayImage();
            }
        }
    }
}
