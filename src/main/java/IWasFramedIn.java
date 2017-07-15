import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Logger;

public class IWasFramedIn extends Application {
    private final static Logger logger = Logger.getLogger(IWasFramedIn.class.getName());
    private static Stage stage;

    public static void main(String[] args) {
        logger.info("started");
        AWSHandler.fetch();
        WhitePane.createScene();
        DirListPane.createScene();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("I Was Framed [In]");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.setScene(setToDirPane());
        stage.show();
    }

    private Scene setToDirPane() {
        logger.info("directory choosing pane");
        Scene dirScene = DirListPane.getScene();

        dirScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    stage.setScene(setToWhitePane());
                    stage.setFullScreen(true);
                    break;
                case RIGHT:
                    stage.setScene(setToImagePane(DirListPane.getSelectedItem()));
                    stage.setFullScreen(true);
                    break;
            }
        });

        return dirScene;
    }

    private Scene setToWhitePane() {
        logger.info("white screen mode");

        Scene whiteScene = WhitePane.getWhiteScene();
        whiteScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    stage.setScene(setToDirPane());
                    stage.setFullScreen(true);
                    break;
                case ESCAPE:
                    System.exit(1);
                    break;
            }
        });

        return whiteScene;
    }

    private Scene setToImagePane(String selectedDir) {
        logger.info("time to display images");

        Scene imageScene = ImagePane.getScene(selectedDir);
        imageScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                case ESCAPE:
                    stage.setScene(setToDirPane());
                    stage.setFullScreen(true);
                    break;
                case LEFT:
                    ImagePane.pressedLeft();
                    break;
                case RIGHT:
                    ImagePane.pressedRight();
                    break;
                case DELETE:
                    ImagePane.deleteSelected();
                    break;
            }
        });

        return imageScene;
    }
}