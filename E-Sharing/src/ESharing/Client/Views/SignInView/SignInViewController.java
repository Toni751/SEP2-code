package ESharing.Client.Views.SignInView;

import ESharing.Client.Core.ViewHandler;
import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Model.UserActions.LoggedUser;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The controller class used to manage all functions and components from the fxml file
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
     * Initializes and opens signIn view with all components
     */
    public void init()
    {
        this.viewHandler = ViewHandler.getViewHandler();
        this.signInViewModel = ViewModelFactory.getViewModelFactory().getSignInViewModel();
        usernameTextField.textProperty().bindBidirectional(signInViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signInViewModel.getPasswordProperty());
        warningLabel.textProperty().bind(signInViewModel.getWarningProperty());
        warningPane.setVisible(false);
    }

    /**
     * Starts a verification process of the login fields and sends login request to a model layer
     */
    public void onSignInButton() {
        if(!signInViewModel.loginRequest())
        {
            warningPane.setVisible(true);
            GeneralFunctions.fadeNode("FadeIn", warningPane, 400);
        }
        else {
            warningPane.setVisible(false);
            if(!LoggedUser.getLoggedUser().getUser().isAdministrator())
                viewHandler.openMainAppView();
            else
                viewHandler.openAdminMainView();
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
