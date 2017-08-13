import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class IWasFramedIn extends JFrame {
    private final static Logger logger = Logger.getLogger(IWasFramedIn.class.getName());
    private static Stage stage;

    private Container container;
    private static WhitePane whiteFrame = new WhitePane();

    public IWasFramedIn() {
        container = getContentPane();
    }

    public static void main(String[] args) {
        logger.info("started");

        IWasFramedIn app = new IWasFramedIn();

        AWSHandler.fetch();

//        JFrame frame = app.whiteFrame.getWhiteFrame();
        JFrame frame = DirListFrame.getDirFrame();

        frame.setVisible(true);

//        frame.addKeyListener();

//        WhitePane.createScene();
//        DirListPane.createScene();

//        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("I Was Framed [In]");
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//        stage.setFullScreen(true);
//        stage.setScene(setToDirPane());
//        stage.show();
//    }
//
//    private Scene setToDirPane() {
//        logger.info("directory choosing pane");
//        Scene dirScene = DirListPane.getScene();
//
//        dirScene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case LEFT:
//                    stage.setScene(setToWhitePane());
//                    stage.setFullScreen(true);
//                    break;
//                case RIGHT:
//                    stage.setScene(setToImagePane(DirListPane.getSelectedItem()));
//                    stage.setFullScreen(true);
//                    break;
//            }
//        });
//
//        return dirScene;
//    }
//
//    private Scene setToWhitePane() {
//        logger.info("white screen mode");
//
//        Scene whiteScene = WhitePane.getWhiteFrame();
//        whiteScene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case RIGHT:
//                    stage.setScene(setToDirPane());
//                    stage.setFullScreen(true);
//                    break;
//                case END:
//                    System.exit(1);
//                    break;
//            }
//        });
//
//        return whiteScene;
//    }
//
//    private Scene setToImagePane(String selectedDir) {
//        logger.info("time to display images");
//
//        Scene imageScene = ImagePane.getScene(selectedDir);
//        imageScene.setOnKeyPressed(e -> {
//            switch (e.getCode()) {
//                case UP:
//                case HOME:
//                    stage.setScene(setToDirPane());
//                    stage.setFullScreen(true);
//                    break;
//                case LEFT:
//                    ImagePane.pressedLeft();
//                    break;
//                case RIGHT:
//                    ImagePane.pressedRight();
//                    break;
//                case DELETE:
//                    ImagePane.deleteSelected();
//                    break;
//            }
//        });
//
//        return imageScene;
//    }
}