package ESharing.Client.Core;

import ESharing.Client.Views.SignInView.SignInViewController;
import ESharing.Client.Views.SignUpView.SignUpViewController;
import ESharing.Client.Views.WelcomeView.WelcomeViewController;
import ESharing.Shared.Util.FailedConnection.ShowFailedConnectionView;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * The class responsible for managing views
 * @version 1.0
 * @author Group1
 */
public class ViewHandler {

    private Stage currentStage;
    private Scene currentScene;

    private ViewModelFactory viewModelFactory;
    private String css;

    private WelcomeViewController welcomeViewController;
    private SignInViewController signInViewController;
    private SignUpViewController signUpViewController;

    private double xOffset;
    private double yOffset;

    /**
     * One-argument constructor initializes fields and sets class with all view models
     * @param modelFactory the class managing all view models
     */
    public ViewHandler(ViewModelFactory modelFactory)
    {
        this.viewModelFactory = modelFactory;
        xOffset = 0;
        yOffset = 0;
        css = this.getClass().getResource("../../Addition/Styles/Styles.css").toExternalForm();
    }

    /**
     * Sets main stage and opens default view
     */
    public void start()
    {
        ShowFailedConnectionView.closeFailedConnectionView();
        currentStage = new Stage();
        openWelcomeView();
    }

    /**
     * Opens welcome view
     */
    public void openWelcomeView()
    {
        //test app without this line
        currentStage.initStyle(StageStyle.TRANSPARENT);
        setViewToOpen(welcomeViewController, "../Views/WelcomeView/WelcomeView.fxml", null);
    }

    /**
     * Loads signIn view to existing pane in the welcome view
     * @param existingPane the existing pane where signIn view will be loaded
     */
    public void openSignInView(Pane existingPane)
    {
        setViewToOpen(signInViewController, "../Views/SignInView/SignIn.fxml", existingPane);
    }

    /**
     * Loads signUp view to existing pane in the welcome view
     * @param existingPane the existing pane where signUp view will be loaded
     */
    public void openSignUpView(Pane existingPane)
    {
        setViewToOpen(signUpViewController, "../Views/SignUpView/SignUp.fxml", existingPane);
    }

    /**
     * Loads fxml files, initializes controllers and opens new view in main stage or in given pane
     * @param controller the controller class assigned to the given fxml file
     * @param fxmlPath the path to fxml file which will be loaded and open
     * @param existingPane the pane component where new view will be loaded.
     *                     For open new view in main stage this parameter should be set as null.
     */
    private void setViewToOpen(Object controller, String fxmlPath, Pane existingPane)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            controller = loader.getController();
            if(controller instanceof WelcomeViewController)
                ((WelcomeViewController) controller).init(this, viewModelFactory.getWelcomeViewModel());
            else if(controller instanceof SignInViewController)
                ((SignInViewController) controller).init(viewModelFactory.getSignInViewModel());
            else if(controller instanceof SignUpViewController)
                ((SignUpViewController) controller).init(viewModelFactory.getSignUpViewModel());
            if(existingPane == null) {
                moveWindowEvents(root);
                currentScene = new Scene(root);
                currentScene.setFill(Color.TRANSPARENT);
                currentScene.getStylesheets().add(css);
            }
            else {
                existingPane.getChildren().removeAll();
                existingPane.getChildren().addAll(root);
            }
        } catch (IOException e) { e.printStackTrace();}
        if(existingPane == null) {
            currentStage.setScene(currentScene);
            currentStage.show();
        }
    }

    /**
     * Adds options to click and drag the main stage
     * @param root the parent which will be owner of click and drag actions
     */
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
