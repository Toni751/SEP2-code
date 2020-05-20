package ESharing.Client.Views.SignInView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * The controller class used to display the sign in view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class SignInViewController extends ViewController {

    @FXML private JFXPasswordField passwordTextField;
    @FXML private JFXTextField usernameTextField;
    @FXML private Pane warningPane;
    @FXML private Label warningLabel;

    private SignInViewModel signInViewModel;
    private ViewHandler viewHandler;
    /**
     * Initializes and opens the sign in  view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init() {
        this.viewHandler = ViewHandler.getViewHandler();
        this.signInViewModel = ViewModelFactory.getViewModelFactory().getSignInViewModel();
        signInViewModel.defaultView();

        usernameTextField.textProperty().bindBidirectional(signInViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signInViewModel.getPasswordProperty());
        warningLabel.textProperty().bind(signInViewModel.getWarningProperty());
        warningPane.visibleProperty().bindBidirectional(signInViewModel.getWarningVisibleProperty());
        warningPane.styleProperty().bindBidirectional(signInViewModel.getWarningStyleProperty());
    }

    /**
     * Sends a request to the view model layer for signing in
     * Opens the application view regarding the request result
     * ADMIN - sends a request to the view handler for opening the main administrator view
     * USER - sends a request to the view handler for opening the main user view
     */
    public void onSignInButton() {
        String result = signInViewModel.loginRequest();
        if (result != null && result.equals("ADMIN"))
            viewHandler.openAdminMainView();
        else if (result != null && result.equals("USER"))
            viewHandler.openMainAppView();
    }

    /**
     * Goes to the sing in request when the 'enter' key is pressed by a user
     * @param keyEvent the key pressed event
     */
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onSignInButton();
        }
    }
}
