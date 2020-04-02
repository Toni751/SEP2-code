package ESharing.Client.Views.WelcomeView;

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
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;
    private Object currentTypeController;
    private Parent fxml;
    private String signInPath;
    private String signUpPath;


    /**
     * Initializes and opens welcome view with all components
     * @param welcomeViewModel the class from a view model layer contains all background functionality
     * @param signUpViewModel the class from a view model layer used to set all functions in a sign up view
     * @param signInViewModel the class from a view model layer used to set all functions in a sign in view
     */
    public void init(WelcomeViewModel welcomeViewModel, SignUpViewModel signUpViewModel, SignInViewModel signInViewModel)
    {
        this.welcomeViewModel = welcomeViewModel;
        this.signInViewModel = signInViewModel;
        this.signUpViewModel = signUpViewModel;
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
     * @param XPosition the X position of the transitionPane where animation stops
     */
    private void moveTransitionPane(String fxmlName, int XPosition)
    {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1),transitionPane);
        transition.setToX(XPosition);
        transition.play();
        transition.setOnFinished((event) ->{
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlName));
                fxml = loader.load();
                transitionPane.getChildren().removeAll();
                transitionPane.getChildren().addAll(fxml);
                if(fxmlName.equals(signInPath)) {
                    currentTypeController = loader.getController();
                    ((SignInViewController)currentTypeController).init(signInViewModel);
                }
                else {
                    currentTypeController = loader.getController();
                    ((SignUpViewController)currentTypeController).init(signUpViewModel);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
