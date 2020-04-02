package ESharing.Client.Views.WelcomeView;

import ESharing.Client.Views.SignInView.SignInViewController;
import ESharing.Client.Views.SignInView.SignInViewModel;
import ESharing.Client.Views.SignUpView.SignUpViewController;
import ESharing.Client.Views.SignUpView.SignUpViewModel;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.IOException;

public class WelcomeViewController {

    @FXML private Pane transitionPane;

    private WelcomeViewModel welcomeViewModel;
    private SignInViewModel signInViewModel;
    private SignUpViewModel signUpViewModel;

    private Object currentTypeController;

    private Parent fxml;

    private String signInPath;
    private String signUpPath;

    public void init(WelcomeViewModel welcomeViewModel, SignUpViewModel signUpViewModel, SignInViewModel signInViewModel)
    {
        this.welcomeViewModel = welcomeViewModel;
        this.signInViewModel = signInViewModel;
        this.signUpViewModel = signUpViewModel;
        signInPath = "../SignInView/SignIn.fxml";
        signUpPath = "../SignUpView/SignUp.fxml";
        moveTransitionPane(signInPath, 322);

    }

    public void onGoToSignUp(ActionEvent actionEvent) {
        moveTransitionPane(signUpPath, 14);

    }

    public void onGoToSignIn(ActionEvent actionEvent) {
        moveTransitionPane(signInPath, 322);
    }

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
