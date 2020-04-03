package ESharing.Client.Views.SignInView;


import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * The controller class used to open and service signIn fxml file
 * @version 1.0
 * @author Group1
 */
public class SignInViewController {

    @FXML private JFXPasswordField passwordTextField;
    @FXML private JFXTextField usernameTextField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;

    private SignInViewModel signInViewModel;

    /**
     * Initializes and opens signIn view with all components
     * @param signInVM the class from a view model layer contains all background functionality
     */
    public void init(SignInViewModel signInVM)
    {
        this.signInViewModel = signInVM;
        usernameTextField.textProperty().bindBidirectional(signInViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signInViewModel.getPasswordProperty());
        warningLabel.textProperty().bind(signInViewModel.getWarningProperty());
        warningPane.setVisible(false);
    }

    /**
     * Starts a verification process of the login fields and sends login request to a model layer
     */
    public void onSignInButton() {
        if(!signInViewModel.textFieldsVerification())
        {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        }
        else {
            warningPane.setVisible(false);
            signInViewModel.onLoginButton();
        }
    }

    /**
     * Hides warning notification after each keyPressed action in the text fields
     */
    public void onKeyPressed() {
        if(warningPane.visibleProperty().get())
        {
            warningPane.setVisible(false);
        }
    }
}
