import javax.swing.*;
import java.awt.*;

public class DirListCellRenderer extends JLabel implements ListCellRenderer {
    public DirListCellRenderer() {
        setOpaque(true);
        setPreferredSize(new Dimension(40, 40));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        if (!isSelected) {
            // Alternating colors for cells
            if (index % 2 == 0) setBackground(Utils.ALTERNATING_BACKGROUND_COLOR1);
            else setBackground(Utils.ALTERNATING_BACKGROUND_COLOR2);
        } else {
            setBackground(Utils.DIR_SELECTION_COLOR);
        }

        return this;
    }
}