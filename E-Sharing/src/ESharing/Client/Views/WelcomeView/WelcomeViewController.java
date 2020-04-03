package ESharing.Client.Views.WelcomeView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Views.SignInView.SignInViewController;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewController;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.IOException;

/**
 * The controller class used to open and service welcome fxml file
 * @version 1.0
 * @author Group1
 */
public class WelcomeViewController {

    @FXML private Pane transitionPane;

    private WelcomeViewModel welcomeViewModel;
    private ViewHandler viewHandler;
    private String signInPath;
    private String signUpPath;


    /**
     * Initializes and opens welcome view with all components
     * @param viewHandler the class responsible for managing views
     * @param welcomeViewModel the class from a view model layer contains all background functionality
     */
    public void init(ViewHandler viewHandler, WelcomeViewModel welcomeViewModel)
    {
        this.welcomeViewModel = welcomeViewModel;
        this.viewHandler = viewHandler;
        signInPath = "../SignInView/SignIn.fxml";
        signUpPath = "../SignUpView/SignUp.fxml";
        moveTransitionPane(signInPath, 322);

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
        moveTransitionPane(signInPath, 322);
    }

    /**
     * Starts an animation used to changing the current view and loads fxml file to the transitionPane by the given values
     * @param fxmlName the path to the fxml file which will be load after an animation
     * @param xPosition the X position of the transitionPane where animation stops
     */
    private void moveTransitionPane(String fxmlName, int xPosition)
    {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1),transitionPane);
        transition.setToX(xPosition);
        transition.play();
        transition.setOnFinished((event) ->{
            if(fxmlName.equals(signInPath)) viewHandler.openSignInView(transitionPane);
            else viewHandler.openSignUpView(transitionPane);
        });
    }
}
