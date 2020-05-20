package ESharing.Client.Views.SignUpView;

import ESharing.Client.Core.ViewModelFactory;
import ESharing.Client.Views.ViewController;
import ESharing.Shared.Util.GeneralFunctions;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * The controller class used to display the sign up view with all JavaFX components
 * @version 1.0
 * @author Group1
 */
public class SignUpViewController extends ViewController {

    @FXML private ImageView arrowBack;
    @FXML private JFXTextField streetTextField;
    @FXML private JFXTextField streetNumberTextField;
    @FXML private JFXTextField phoneTextField;
    @FXML private JFXTextField cityTextField;
    @FXML private JFXTextField postalCodeTextField;
    @FXML private ProgressBar creatingProgressBar2;
    @FXML private ProgressBar creatingProgressBar1;
    @FXML private Circle personalCircle;
    @FXML private Circle addressCircle;
    @FXML private Circle signUpCircle;
    @FXML private JFXPasswordField passwordTextField;
    @FXML private JFXTextField usernameTextField;
    @FXML private JFXPasswordField confirmPasswordTextField;
    @FXML private Pane personalPane;
    @FXML private Pane warningPane;
    @FXML private Pane rulesPane;
    @FXML private Pane addressPane;
    @FXML private Label warningLabel;
    @FXML private Button signUpButton;

    private SignUpViewModel signUpViewModel;

    /**
     * Initializes and opens the sign up view with all components,
     * initializes a binding properties of the JavaFX components
     */
    public void init()
    {
        this.signUpViewModel = ViewModelFactory.getViewModelFactory().getSignUpViewModel();
        signUpViewModel.resetView();

        usernameTextField.textProperty().bindBidirectional(signUpViewModel.getUsernameProperty());
        passwordTextField.textProperty().bindBidirectional(signUpViewModel.getPasswordProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(signUpViewModel.getConfirmPasswordProperty());
        phoneTextField.textProperty().bindBidirectional(signUpViewModel.getPhoneProperty());
        warningLabel.textProperty().bind(signUpViewModel.getWarningLabel());

        streetNumberTextField.textProperty().bindBidirectional(signUpViewModel.getNumberProperty());
        streetTextField.textProperty().bindBidirectional(signUpViewModel.getStreetProperty());
        cityTextField.textProperty().bindBidirectional(signUpViewModel.getCityProperty());
        postalCodeTextField.textProperty().bindBidirectional(signUpViewModel.getPostalCodeProperty());

        signUpCircle.styleProperty().bindBidirectional(signUpViewModel.getSignUpCircleStyleProperty());
        addressCircle.styleProperty().bindBidirectional(signUpViewModel.getAddressCircleStyleProperty());
        personalCircle.styleProperty().bindBidirectional(signUpViewModel.getPersonalCircleStyleProperty());
        signUpCircle.radiusProperty().bindBidirectional(signUpViewModel.getSignUpCircleRadiusProperty());
        personalCircle.radiusProperty().bindBidirectional(signUpViewModel.getPersonalCircleRadiusProperty());
        addressCircle.radiusProperty().bindBidirectional(signUpViewModel.getAddressCircleRadiusProperty());

        signUpButton.disableProperty().bindBidirectional(signUpViewModel.getSignUpButtonDisableProperty());
        warningPane.styleProperty().bindBidirectional(signUpViewModel.getWarningStyleProperty());
        warningPane.visibleProperty().bindBidirectional(signUpViewModel.getWarningVisibleProperty());
        arrowBack.disableProperty().bindBidirectional(signUpViewModel.backArrowDisableProperty());
        creatingProgressBar1.progressProperty().bindBidirectional(signUpViewModel.getProgressBar1Property());
        creatingProgressBar2.progressProperty().bindBidirectional(signUpViewModel.getProgressBar2Property());

        personalPane.visibleProperty().bindBidirectional(signUpViewModel.getPersonalPaneVisibleProperty());
        addressPane.visibleProperty().bindBidirectional(signUpViewModel.getAddressPaneVisibleProperty());
        rulesPane.visibleProperty().bindBidirectional(signUpViewModel.getRulesVisibleProperty());

        addOnOutFocusEvent(usernameTextField, creatingProgressBar1);
        addOnOutFocusEvent(passwordTextField, creatingProgressBar1);
        addOnOutFocusEvent(confirmPasswordTextField, creatingProgressBar1);
        addOnOutFocusEvent(phoneTextField, creatingProgressBar1);

        addOnOutFocusEvent(streetTextField, creatingProgressBar2);
        addOnOutFocusEvent(streetNumberTextField, creatingProgressBar2);
        addOnOutFocusEvent(cityTextField, creatingProgressBar2);
        addOnOutFocusEvent(postalCodeTextField, creatingProgressBar2);
    }

    /**
     * Sends a request to the view model layer for signing up a new user
     */
    public void onSignUpButton() {
        signUpViewModel.setSignUpCircleSelectedProperties();
        signUpViewModel.signUpAddressRequest();
    }

    /**
     * Sends a request to the view model layer for setting the visible property of the rule pane object
     */
    public void onRulesClick() {
        rulesPane.toFront();
        signUpViewModel.setRulesPaneSelected();
    }

    /**
     * Sends a request to the view model layer for checking address values and continuing the creation process
     */
    public void onGoToAddressClick() {
        signUpViewModel.signUpPersonalRequest();
    }

    /**
     * Sends a request to the view model layer for setting the visible property of the personal pane object
     */
    public void onGoToPersonalClick() {
        signUpViewModel.setPersonalPaneSelected();
    }

    /**
     * Sends a request to the view model layer for setting the visible property of the warning pane to false
     */
    public void onKeyPressed() {
        signUpViewModel.hideWarningPane();
    }

    /**
     * Creates an focus out event on the given JavaFx component
     * @param node the given JavaFx component
     * @param progressBar the progress bar component used to display changes after focus out event
     */
    private void addOnOutFocusEvent(Node node, ProgressBar progressBar)
    {
        node.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) GeneralFunctions.setFormProgressBar(progressBar, node, 0.25);
        });
    }
}
