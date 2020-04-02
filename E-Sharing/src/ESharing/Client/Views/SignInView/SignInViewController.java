package ESharing.Client.Views.SignInView;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SignInViewController {

    @FXML private JFXPasswordField passwordTextField;
    @FXML private JFXTextField usernameTextField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;

    private SignInViewModel signInViewModel;

    public void init(SignInViewModel signInVM)
    {
        this.signInViewModel = signInVM;
        usernameTextField.textProperty().bindBidirectional(signInViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signInViewModel.getPasswordProperty());
        warningLabel.textProperty().bind(signInViewModel.getWarningProperty());
        warningPane.setVisible(false);
    }

    public void onSignInButton(ActionEvent actionEvent) {
        if(!signInViewModel.textFieldsVerification())
        {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), warningPane);
            warningPane.setVisible(true);
            fadeIn.setFromValue(0.1);
            fadeIn.setToValue(10);
            fadeIn.play();
        }
        else {
            warningPane.setVisible(false);
            signInViewModel.onLoginButton();
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if(warningPane.visibleProperty().get())
        {
            warningPane.setVisible(false);
        }
    }
}
