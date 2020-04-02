package ESharing.Shared.Util.FailedConnection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowFailedConnectionView {

    private static Scene scene;
    private static Stage stage = new Stage();

    public static void openFailedConnectionView ()
    {
        if (scene == null) {
            try {
                System.out.println("Try to open loading pane");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(FailedConnectionViewController.class.getResource("failedConnectionView.fxml"));
               // FailedConnectionViewController controller = loader.getController();
              //  controller.init();
                Parent root = loader.load();
                scene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void closeFailedConnectionView () {
        stage.close();
        scene = null;
    }

}
