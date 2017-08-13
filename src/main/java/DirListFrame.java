import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

class DirListFrame extends JPanel {
    private JList<String> dirList;

    private DirListFrame() {
        setLayout(new BorderLayout());

        File imagesHome = new File(Utils.HOME_DIRECTORY);
        if (!imagesHome.exists()) imagesHome.mkdirs();


        DefaultListModel<String> defaultModel = new DefaultListModel<>();
        dirList = new JList<>(defaultModel);

        for (String subdir : Utils.getSubdirs(imagesHome)) {
            defaultModel.addElement(subdir);
        }

        dirList.setCellRenderer(new DirListCellRenderer());
        dirList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dirList.setSelectedIndex(0);

        dirList.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_DELETE){
                    String selectedDir = dirList.getSelectedValue();
                    int selectedDirIndex = dirList.getSelectedIndex();
                    AWSHandler.delete(selectedDir);

                    ((DefaultListModel) dirList.getModel()).remove(selectedDirIndex);

                    Utils.deleteFilesIn(selectedDir);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        if (dirList.getModel().getSize() > 0) {
            dirList.setSelectedIndex(0);
        }

        JScrollPane scrollPane = new JScrollPane(dirList);

        add(scrollPane);
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
