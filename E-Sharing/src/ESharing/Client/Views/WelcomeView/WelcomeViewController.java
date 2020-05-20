package ESharing.Client.Views.WelcomeView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * The controller class used to display the welcome view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class WelcomeViewController extends ViewController {

    @FXML
    private Pane transitionPane;
    private WelcomeViewModel welcomeViewModel;
    private ViewHandler viewHandler;

    private final String signInPath = "../SignInView/SignIn.fxml";
    private final String signUpPath = "../SignUpView/SignUp.fxml";

    /**
     * Initializes and opens welcome view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init() {
        this.welcomeViewModel = ViewModelFactory.getViewModelFactory().getWelcomeViewModel();
        this.viewHandler = ViewHandler.getViewHandler();
        moveTransitionPane(signInPath, 390);
    }

    /**
     * Initializes and opens a sign in view
     */
    public void onGoToSignUp() {
        moveTransitionPane(signUpPath, 14);
    }

    /**
     * Initializes and opens a sign up view
     */
    public void onGoToSignIn() {
        moveTransitionPane(signInPath, 390);
    }

    /**
     * Closes application
     */
    public void onExitAction() {
        System.exit(0);
    }

    /**
     * Sends a request to the view handler for minimizing the application stage
     */
    public void onMinimizeAction() {
        viewHandler.minimizeWindow();
    }

    /**
     * Starts an animation used to changing the current view and loads fxml file to the transitionPane by the given values
     *
     * @param fxmlName  the path to the fxml file which will be load after an animation
     * @param xPosition the X position of the transitionPane where animation stops
     */
    private void moveTransitionPane(String fxmlName, int xPosition) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), transitionPane);
        transition.setToX(xPosition);
        transition.play();
        transition.setOnFinished((event) -> {
            if (fxmlName.equals(signInPath)) viewHandler.openSignInView(transitionPane);
            else viewHandler.openSignUpView(transitionPane);
        });
    }
}
