import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.logging.Logger;

class DirListFrame extends JPanel {
    private final static Logger logger = Logger.getLogger(DirListFrame.class.getName());
    private static JList<String> dirList;

    private DirListFrame() {
        logger.info("setting up the Directory List frame");
        setLayout(new BorderLayout());

        File imagesHome = new File(Utils.HOME_DIRECTORY);
        if (!imagesHome.exists()) imagesHome.mkdirs();


        DefaultListModel<String> defaultModel = new DefaultListModel<>();
        dirList = new JList<>(defaultModel);

        for (String subdir : Utils.getSubdirs(imagesHome)) {
            defaultModel.addElement(subdir);
        }

        logger.info("got all directories");

        dirList.setCellRenderer(new DirListCellRenderer());
        dirList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dirList.setSelectedIndex(0);

        if (dirList.getModel().getSize() > 0) {
            dirList.setSelectedIndex(0);
        }

        dirList.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                    String selectedDir = dirList.getSelectedValue();
                    int selectedDirIndex = dirList.getSelectedIndex();
                    AWSHandler.delete(selectedDir);

                    ((DefaultListModel) dirList.getModel()).remove(selectedDirIndex);

                    Utils.deleteFilesIn(selectedDir);
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4){
                    ScreenController.setToWhite();
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6){
                    ScreenController.setToImages(dirList.getSelectedValue());
                } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        JScrollPane scrollPane = new JScrollPane(dirList);
        add(scrollPane);
        logger.info("setup done");
    }

    static JFrame getDirFrame() {
        JFrame dirFrame = new JFrame();
        dirFrame.setContentPane(new DirListFrame());
        dirFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        dirFrame.setUndecorated(true);
        dirFrame.setVisible(true);

        return dirFrame;
    }
}
