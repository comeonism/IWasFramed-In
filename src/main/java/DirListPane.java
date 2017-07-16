import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;

class DirListPane {
    private static Scene dirScene;
    private static ListView<String> listView;

    static void createScene() {
        listView = new ListView<>();
        BorderPane pane = new BorderPane();
        pane.getStylesheets().add("./style.css");
        pane.setPrefSize(Utils.getDimensions().getWidth(), Utils.getDimensions().getHeight());

        File imagesHome = new File(Utils.HOME_DIRECTORY);
        if (!imagesHome.exists()) imagesHome.mkdirs();

        ObservableList<String> dirs = Utils.getSubdirs(imagesHome);
        listView.setItems(dirs);
        listView.setOnKeyPressed(e ->  {
            switch (e.getCode()) {
                case DELETE:
                    String selectedDir = listView.getSelectionModel().getSelectedItem();
                    AWSHandler.delete(selectedDir);
                    listView.getItems().remove(selectedDir);
                    Utils.deleteFilesIn(selectedDir);
                    break;
            }
        });

        if (dirs.size() > 0) {
            listView.getSelectionModel().select(0);
            listView.getFocusModel().focus(0);
        }

        pane.setCenter(listView);

        dirScene = new Scene(pane);
    }

    static Scene getScene() {
        return dirScene;
    }

    static String getSelectedItem() { return listView.getSelectionModel().getSelectedItem(); }
}
