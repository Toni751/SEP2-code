package ESharing.Client.Core;

import ESharing.Client.Views.SignInView.SignInViewController;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewController;
import ESharing.Client.Views.WelcomeView.WelcomeViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ViewHandler {

    private Stage currentStage;
    private Scene currentScene;

    private ViewModelFactory viewModelFactory;
    private String css;

    private WelcomeViewController welcomeViewController;

    private double xOffset;
    private double yOffset;

    public ViewHandler(ViewModelFactory modelFactory)
    {
        this.viewModelFactory = modelFactory;
        xOffset = 0;
        yOffset = 0;
        css = this.getClass().getResource("../../Addition/Styles/Styles.css").toExternalForm();
    }

    public void start()
    {
        currentStage = new Stage();
        openWelcomeView();
    }

    public void openWelcomeView()
    {
        currentStage.initStyle(StageStyle.TRANSPARENT);
        setViewToOpen(welcomeViewController, "../Views/WelcomeView/WelcomeView.fxml");

    }


    private void setViewToOpen(Object controller, String fxmlPath)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            controller = loader.getController();
            if(controller instanceof WelcomeViewController) {
                ((WelcomeViewController) controller).init(viewModelFactory.getWelcomeViewModel(), viewModelFactory.getSignUpViewModel(), viewModelFactory.getSignInViewModel());
            }
            moveWindowEvents(root);
            currentScene = new Scene(root);
            currentScene.setFill(Color.TRANSPARENT);
            currentScene.getStylesheets().add(css);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage.setScene(currentScene);
        currentStage.show();
    }

    private void moveWindowEvents(Parent root)
    {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentStage.setX(event.getScreenX() - xOffset);
                currentStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
