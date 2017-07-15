import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class WhitePane {
    private static Scene whiteScene;

    static void createScene() {
        Pane pane = new FlowPane();

        Rectangle screenRect = new Rectangle(0, 0, Utils.getDimensions().getWidth(), Utils.getDimensions().getHeight());
        screenRect.setFill(Color.WHITE);
        pane.getChildren().add(screenRect);

        whiteScene = new Scene(pane, Color.WHITE);
    }

    static Scene getWhiteScene() {
        return whiteScene;
    }
}
